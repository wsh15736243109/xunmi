package com.pc.mimi.ui.vipmembercenter;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pc.mimi.adapter.VipMemberTcAdapter;
import com.pc.mimi.bean.VXBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.util.pay.OrderStrBean;
import com.pc.mimi.bean.VipChargeBean;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class VipMemberCenterViewModel extends BaseViewModel {
    public VipMemberCenterViewModel(@NonNull Application application) {
        super(application);
    }

    VipMemberTcAdapter mVipMemberTcAdapter;
    List<VipChargeBean> mVipChargeBeans = new ArrayList<>();
    private int mCurrentPayType = 1;
    public String mUnit = "";
    public String orderInfo = "";
    public OrderStrBean mOrderStrBean;
    UIChangeObser uc = new UIChangeObser();

    class UIChangeObser {
        public ObservableBoolean ali = new ObservableBoolean(false);
        public ObservableBoolean vx = new ObservableBoolean(false);
    }

    //充值金额
    public ObservableField<String> payMoney = new ObservableField<>("0元");
    //充值
    public BindingCommand charge = new BindingCommand(() -> {
        if (TextUtils.isEmpty(mUnit)) {
            ToastUtils.showShort("请选择充值规格");
            return;
        }
        switch (mCurrentPayType) {
            case 0://支付宝
                AppUtils.requestData(RetrofitClient.getInstance().create(API.class).goAliChargeVip(AppUtils.getUser().getUser_id(), mUnit, mCurrentPayType)
                        , getLifecycleProvider(), disposable -> showDialog(),
                        new ApiDisposableObserver<String>() {
                            @Override
                            public void onResult(String o) {
                                orderInfo = o;
                                uc.ali.set(!uc.ali.get());
                            }

                            @Override
                            public void dialogDismiss() {
                                dismissDialog();
                            }
                        });
                break;
            case 1:
                AppUtils.requestData(RetrofitClient.getInstance().create(API.class).goVXChargeVip(AppUtils.getUser().getUser_id(), mUnit, mCurrentPayType)
                        , getLifecycleProvider(), disposable -> showDialog(),
                        new ApiDisposableObserver<VXBean>() {
                            @Override
                            public void onResult(VXBean vxBean) {
                                mOrderStrBean = new OrderStrBean();
                                mOrderStrBean.setAppid(vxBean.getAppid());
                                mOrderStrBean.setNoncestr(vxBean.getNonce_str());
                                mOrderStrBean.setPackageX("Sign=WXPay");
                                mOrderStrBean.setPartnerid(vxBean.getMch_id());
                                mOrderStrBean.setPrepayid(vxBean.getPrepay_id());
                                mOrderStrBean.setSign(vxBean.getSign());
                                mOrderStrBean.setTimestamp(vxBean.getTimestamp());
                                uc.vx.set(!uc.vx.get());
                            }

                            @Override
                            public void dialogDismiss() {
                                dismissDialog();
                            }
                        });
                break;
        }
    });

    public ObservableBoolean isAli = new ObservableBoolean(false);
    public ObservableBoolean isVx = new ObservableBoolean(true);
    //选择微信支付
    public BindingCommand vxPay = new BindingCommand(() -> {
        isVx.set(true);
        isAli.set(false);
        mCurrentPayType = 1;
    });
    //选择支付宝支付
    public BindingCommand aliPay = new BindingCommand(() -> {
        isVx.set(false);
        isAli.set(true);
        mCurrentPayType = 0;
    });

    /**
     * 获取充值vip规格
     */
    public void requestGetVipChargeType() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestGetVipChargeType(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(), new ApiDisposableObserver<List<VipChargeBean>>() {
                    @Override
                    public void onResult(List<VipChargeBean> o) {
                        mVipChargeBeans.clear();
                        mVipChargeBeans.addAll(o);
                        mVipMemberTcAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }
}

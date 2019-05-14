package com.pc.mimi.ui.vipmembercenter;

import android.Manifest;
import android.databinding.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.pc.mimi.adapter.VipMemberTcAdapter;
import com.pc.mimi.util.CommonUtil;
import com.pc.mimi.util.DataUtils;
import com.pc.mimi.util.pay.AliPay;
import com.pc.mimi.util.pay.PayUtils;
import com.pc.mimi.util.pay.WeiXinPay;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.VipMemberPowerAdapter;
import com.pc.mimi.app.SwipeBackActivity;
import com.pc.mimi.bean.VipChargeBean;
import com.xlyuns.xunmi.databinding.ActivityVipMemberCenterBinding;
import com.pc.mimi.widget.CustomLinerLayoutManager;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class VipMemberCenterActivity extends SwipeBackActivity<ActivityVipMemberCenterBinding, VipMemberCenterViewModel> {
    private PayUtils mPayUtils;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_vip_member_center;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("会员中心");
        CommonUtil.initTitleBar(this);
        //会员特权
        new InitMemberPower().invoke();

        viewModel.mVipMemberTcAdapter = new VipMemberTcAdapter(viewModel.mVipChargeBeans);
        binding.recyclerTc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        viewModel.mVipMemberTcAdapter.bindToRecyclerView(binding.recyclerTc);
        viewModel.mVipMemberTcAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
            for (VipChargeBean vipChargeBean : viewModel.mVipChargeBeans) {
                vipChargeBean.setSelected(false);
            }
            viewModel.mVipChargeBeans.get(position).setSelected(true);
            viewModel.mUnit = viewModel.mVipChargeBeans.get(position).getVip_price_id();
            viewModel.payMoney.set(viewModel.mVipChargeBeans.get(position).getNow_value() + "元");
            viewModel.mVipMemberTcAdapter.notifyDataSetChanged();
        }));
        viewModel.requestGetVipChargeType();

        viewModel.mVipMemberTcAdapter.notifyDataSetChanged();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.ali.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                RxPermissions rxPermissions = new RxPermissions(VipMemberCenterActivity.this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    callPay();
                                } else {
                                    ToastUtils.showShort("权限被拒绝");
                                }
                            }
                        });
            }
        });
        viewModel.uc.vx.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                weiXinPay();
            }
        });
    }

    /**
     * 微信支付
     */
    private void weiXinPay() {
        KLog.d("---------------------BEGIN--WEIXINPAY------------------------------------");
        if (viewModel.mOrderStrBean == null) {
            return;
        }
        mPayUtils = new WeiXinPay(this, viewModel.mOrderStrBean);
        mPayUtils.toPay();
    }

    Handler mHandler = new Handler();

    //开始支付宝支付
    private void callPay() {
        KLog.d("---------------------BEGIN--ALIPAY------------------------------------");
        String order = viewModel.orderInfo;
        if (TextUtils.isEmpty(order)) {
            ToastUtils.showShort("订单异常,请稍后重试");
            return;
        }
        mPayUtils = new AliPay(this, order, new PayUtils.result() {
            @Override
            public void onSuccess() {
                //todo 支付成功 请求服务端查看订单状态
                ToastUtils.showShort("支付成功");
            }

            @Override
            public void onFail() {
                ToastUtils.showShort("支付失败");
            }
        });
        mPayUtils.toPay();
    }

    private class InitMemberPower {
        public void invoke() {
            VipMemberPowerAdapter memberPowerAdapter = new VipMemberPowerAdapter(DataUtils.getVipMemberPowerData());
            CustomLinerLayoutManager c = new CustomLinerLayoutManager(VipMemberCenterActivity.this);
            c.setScrollEnable(false);
            binding.recycler.setLayoutManager(c);
            memberPowerAdapter.bindToRecyclerView(binding.recycler);
        }
    }
}

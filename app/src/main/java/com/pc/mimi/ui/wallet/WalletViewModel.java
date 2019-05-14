package com.pc.mimi.ui.wallet;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.pc.mimi.adapter.WalletAdapter;
import com.pc.mimi.bean.ChargeUnit;
import com.pc.mimi.bean.VXBean;
import com.pc.mimi.bean.WalletInfoBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.util.inject.FindView;
import com.pc.mimi.util.inject.OnClick;
import com.pc.mimi.util.inject.ViewUtils;
import com.pc.mimi.util.pay.OrderStrBean;
import com.pc.mimi.widget.CustomDialog;
import com.xlyuns.xunmi.R;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class WalletViewModel extends BaseViewModel {
    public WalletViewModel(@NonNull Application application) {
        super(application);
    }

    WalletAdapter mWalletAdapter;
    List<ChargeUnit> mChargeUnits;
    private int mCurrentPayType = 1;
    public String mUnit = "";
    public String orderInfo = "";
    public OrderStrBean mOrderStrBean;
    public ObservableBoolean isAli = new ObservableBoolean(false);
    public ObservableBoolean isVx = new ObservableBoolean(true);
    CustomDialog mBindAliAccountDialog;
    CustomDialog mCashDialog;
    CashDialogEvent mCashDialogEvent;
    BindAliAccountDialogEvent mBindAliAccountDialogEvent;
    UIChangeObser uc = new UIChangeObser();
    boolean isCanCsh = false;
    public ObservableField<String> aliPayAccount = new ObservableField<>("支付宝账号");
    public ObservableField<String> bindAliAccountText = new ObservableField<>("绑定支付宝账号");
    //可提现
    public ObservableField<String> wallet = new ObservableField<>("0");

    /**
     * 获取钱包信息
     */
    public void requestWalletInfo() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestWalletInfo(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<WalletInfoBean>() {
                    @Override
                    public void onResult(WalletInfoBean o) {
                        wallet.set(TextUtils.isEmpty(o.getWallet()) ? "0觅币" : o.getWallet() + "觅币");
                        mChargeUnits.clear();
                        if (o.getList() != null && o.getList().size() > 0) {
                            mChargeUnits.addAll(o.getList());
                            mWalletAdapter.notifyDataSetChanged();
                        }
                        isCanCsh = o.getIs_have_ali() == 1;
                        if (o.getIs_have_ali() == 1) {
                            aliPayAccount.set("支付宝账号:" + o.getAcount_mumber());
                            bindAliAccountText.set("修改绑定");
                        } else {
                            aliPayAccount.set("支付宝账号");
                            bindAliAccountText.set("绑定支付宝账号");
                        }
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                });

    }

    class UIChangeObser {
        public ObservableBoolean ali = new ObservableBoolean(false);
        public ObservableBoolean vx = new ObservableBoolean(false);
    }

    //绑定支付宝
    public BindingCommand bindAliAccount = new BindingCommand(() -> invokeEventAction(context -> {
        if (mBindAliAccountDialog != null) mBindAliAccountDialog = null;
        if (mBindAliAccountDialogEvent != null) mBindAliAccountDialogEvent = null;
        mBindAliAccountDialogEvent = new BindAliAccountDialogEvent();
        mBindAliAccountDialog = new CustomDialog(context, R.layout.dialog_bind_ali_account, true, false,
                Gravity.CENTER, content -> ViewUtils.inject(content, mBindAliAccountDialogEvent), false);
        mBindAliAccountDialog.show();
    }));

    private class BindAliAccountDialogEvent {
        @FindView(R.id.ali_account)
        EditText ali_account;
        @FindView(R.id.ali_name)
        EditText ali_name;

        @OnClick({R.id.confir, R.id.cancel})
        void onclicked(View view) {
            switch (view.getId()) {
                case R.id.confir:
                    if (TextUtils.isEmpty(ali_account.getText().toString())) {
                        ToastUtils.showShort("请输入支付宝账号");
                        return;
                    }
                    if (TextUtils.isEmpty(ali_name.getText().toString())) {
                        ToastUtils.showShort("请输入支付宝姓名");
                        return;
                    }
                    mBindAliAccountDialog.dismiss();
                    doBindAliAccouunt(ali_account.getText().toString().trim(), ali_name.getText().toString().trim());
                    break;
                case R.id.cancel:
                    mBindAliAccountDialog.dismiss();
                    break;
            }

        }
    }

    //提现
    public BindingCommand cash = new BindingCommand(() -> invokeEventAction(context -> {
        if (isCanCsh) {
            if (wallet.get().equals("0元")) {
                ToastUtils.showShort("当前账户无可提现余额");
                return;
            }
            if (mCashDialog != null) mCashDialog = null;
            if (mCashDialogEvent != null) mCashDialogEvent = null;
            mCashDialogEvent = new CashDialogEvent();
            mCashDialog = new CustomDialog(context, R.layout.dialog_cash, true, false,
                    Gravity.CENTER, content -> ViewUtils.inject(content, mCashDialogEvent), false);
            mCashDialog.show();
        } else {
            ToastUtils.showShort("请先绑定支付宝账户后再发起提现");
        }
    }));

    private class CashDialogEvent {
        @FindView(R.id.et_input)
        EditText et_input;

        @OnClick({R.id.confir, R.id.cancel})
        void onclicked(View view) {
            switch (view.getId()) {
                case R.id.confir:
                    if (TextUtils.isEmpty(et_input.getText().toString())) {
                        ToastUtils.showShort("请输入提现金额");
                        return;
                    }
                    mCashDialog.dismiss();
                    doCash(et_input.getText().toString().trim());
                    break;
                case R.id.cancel:
                    mCashDialog.dismiss();
                    break;
            }

        }
    }

    /**
     * 提现
     *
     * @param trim
     */
    private void doCash(String trim) {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).doCash(AppUtils.getUser().getUser_id(), trim),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {
                        ToastUtils.showShort("成功");
                        requestWalletInfo();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                    @Override
                    public void onDataEmpty(String msg) {
                        ToastUtils.showShort("成功");
                        requestWalletInfo();
                    }
                });
    }

    /**
     * 绑定支付宝账号
     *
     * @param trim  支付宝账号
     * @param trim1 支付宝姓名
     */
    private void doBindAliAccouunt(String trim, String trim1) {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).doBindAliAccouunt(AppUtils.getUser().getUser_id(), trim, trim1),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {
                        ToastUtils.showShort("成功");
                        requestWalletInfo();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                });
    }

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
    //确认充值
    public BindingCommand chargeConfir = new BindingCommand(() -> {
        if (TextUtils.isEmpty(mUnit)) {
            ToastUtils.showShort("请选择充值规格");
            return;
        }
        switch (mCurrentPayType) {
            case 0://支付宝
                AppUtils.requestData(RetrofitClient.getInstance().create(API.class).goAliCharge(AppUtils.getUser().getUser_id(), mUnit, mCurrentPayType)
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
            case 1://微信
                AppUtils.requestData(RetrofitClient.getInstance().create(API.class).goVXCharge(AppUtils.getUser().getUser_id(), mUnit, mCurrentPayType)
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

    /**
     * 获取支付规格
     */
    public void requestChargeItem() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestChargeItem(AppUtils.getUser().getUser_id())
                , getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<List<ChargeUnit>>() {
                    @Override
                    public void onResult(List<ChargeUnit> o) {

                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }
}

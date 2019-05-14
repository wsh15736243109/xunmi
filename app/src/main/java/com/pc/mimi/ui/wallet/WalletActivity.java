package com.pc.mimi.ui.wallet;

import android.Manifest;
import android.databinding.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.pc.mimi.adapter.WalletAdapter;
import com.pc.mimi.bean.ChargeUnit;
import com.pc.mimi.util.CommonUtil;
import com.pc.mimi.util.pay.AliPay;
import com.pc.mimi.util.pay.PayUtils;
import com.pc.mimi.util.pay.WeiXinPay;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityWalletBinding;
import com.pc.mimi.widget.CustomGridLayoutManager;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class WalletActivity extends SwipeBackActivity<ActivityWalletBinding, WalletViewModel> {
    private PayUtils mPayUtils;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_wallet;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("钱包");
        CommonUtil.initTitleBar(this);
        initWallerRecycler();
        viewModel.requestWalletInfo();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.ali.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                RxPermissions rxPermissions = new RxPermissions(WalletActivity.this);
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
        mPayUtils = new WeiXinPay(WalletActivity.this, viewModel.mOrderStrBean);
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
        mPayUtils = new AliPay(WalletActivity.this, order, new PayUtils.result() {
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

    private void initWallerRecycler() {
        viewModel.mChargeUnits = new ArrayList<>();
        viewModel.mWalletAdapter = new WalletAdapter(viewModel.mChargeUnits);
        CustomGridLayoutManager c = new CustomGridLayoutManager(this, 2);
        c.setScrollEnable(false);
        binding.recyclerWallet.setLayoutManager(c);
        viewModel.mWalletAdapter.bindToRecyclerView(binding.recyclerWallet);
        viewModel.mWalletAdapter.setOnItemClickListener(((adapter, view, position) -> {
            for (ChargeUnit uIbean :
                    viewModel.mChargeUnits) {
                uIbean.setSelected(false);
            }
            viewModel.mChargeUnits.get(position).setSelected(true);
            viewModel.mUnit = viewModel.mChargeUnits.get(position).getPay_type_id();
            viewModel.mWalletAdapter.notifyDataSetChanged();
        }));
    }
}

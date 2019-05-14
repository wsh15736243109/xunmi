package com.pc.mimi.util.pay;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

import me.goldze.mvvmhabit.utils.ToastUtils;

public class AliPay implements PayUtils {
    private Activity mActivity;
    private String mOrderInfo;
    private int SDK_PAY_FLAG = 1001;
    private PayUtils.result mResult;

    public AliPay(Activity activity, String orderInfo, PayUtils.result result) {
        mActivity = activity;
        mOrderInfo = orderInfo;
        mResult = result;
    }

    @Override
    public void toPay() {
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(mActivity);
            Map result = alipay.payV2(mOrderInfo, true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /**
             * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                mResult.onSuccess();
                ToastUtils.showShort("支付成功");
            } else {
                mResult.onFail();
                ToastUtils.showShort("支付失败,错误码: " + resultStatus);
            }

        }
    };
}

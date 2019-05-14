package com.pc.mimi.util;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;

/**
 * 倒计时处理类
 */
public class CCountDownTimer extends CountDownTimer {
    private ObservableField<String> s;
    private long mSurplusTime;
    private Object mObject;
    private ObservableBoolean canClick;

    public CCountDownTimer(Object object, ObservableField<String> s, ObservableBoolean canClick, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.s = s;
        this.mObject = object;
        this.canClick = canClick;
    }

    @Override
    public void onTick(long l) {
        if (mObject instanceof Activity) {
            if (!((Activity) mObject).isFinishing()) {
                canClick.set(false);
                mSurplusTime = l;
               s.set((mSurplusTime / 1000) + "秒后可重发");
            }
        } else if (mObject instanceof Fragment) {
            if (((Fragment) mObject).getActivity() != null) {
                canClick.set(false);
                mSurplusTime = l;
                s.set((mSurplusTime / 1000) + "秒后可重发");
            }
        }
    }

    @Override
    public void onFinish() {
        canClick.set(true);
        s.set("发送验证码");
    }

    /**
     * 返回剩余倒计时时间
     *
     * @return 单位毫秒
     */
    public long getSurplusTime() {
        return mSurplusTime;
    }
}

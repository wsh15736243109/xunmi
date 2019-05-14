package com.pc.mimi.ui.bindphone;

import android.databinding.Observable;
import android.os.Bundle;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.pc.mimi.config.SpKey;
import com.xlyuns.xunmi.databinding.ActivityBindPhoneBinding;
import com.pc.mimi.util.CCountDownTimer;
import com.pc.mimi.util.CommonUtil;

import me.goldze.mvvmhabit.utils.SPUtils;

public class BindPhoneActivity extends SwipeBackActivity<ActivityBindPhoneBinding, BindPhoneViewModel> {
    CCountDownTimer mCCountDownTimer;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_bind_phone;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("绑定手机号");
        CommonUtil.initTitleBar(this);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.bindPhoneCountDown.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                //开始倒计时
                beginCountDownTimeBind();
            }
        });
    }

    private void beginCountDownTimeBind() {
        //请求获取验证码
        if (mCCountDownTimer == null) {
            mCCountDownTimer = new CCountDownTimer(BindPhoneActivity.this,
                    viewModel.sendCodeText, viewModel.isGetCodeClick, 60000, 1000);
            mCCountDownTimer.start();

        } else {
            mCCountDownTimer.cancel();
            mCCountDownTimer = null;
            mCCountDownTimer = new CCountDownTimer(BindPhoneActivity.this,
                    viewModel.sendCodeText, viewModel.isGetCodeClick, 60000, 1000);
            mCCountDownTimer.start();
        }
    }

    @Override
    protected void onStart() {
        //如果前一次倒计时与这次进入时间差在上次倒计时结束时间内。则继续计时，
        long currentTime = System.currentTimeMillis() / 1000;
        //取出上次倒计时剩余的时间
        long surplusTime = SPUtils.getInstance().getLong(SpKey.EXIT_BIND_PHONE_SURPLUS_TIME, 0l);
        if (surplusTime > 0) {
            //上次退出是的系统时间
            long lastExitTime = SPUtils.getInstance().getLong(SpKey.EXIT_BIND_PHONE_TIME, 0l);
            if (currentTime - lastExitTime < surplusTime) {//继续倒计时
                if (mCCountDownTimer == null) {
                    mCCountDownTimer = new CCountDownTimer(BindPhoneActivity.this,
                            viewModel.sendCodeText, viewModel.isGetCodeClick,
                            (surplusTime - currentTime + lastExitTime) * 1000, 1000);
                    mCCountDownTimer.start();
                } else {
                    mCCountDownTimer.cancel();
                    mCCountDownTimer = null;
                    mCCountDownTimer = new CCountDownTimer(BindPhoneActivity.this,
                            viewModel.sendCodeText, viewModel.isGetCodeClick,
                            currentTime - lastExitTime, 1000);
                    mCCountDownTimer.start();
                }
            } else {//过期不做操作
            }
        }
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        if (mCCountDownTimer != null) {
            if (mCCountDownTimer.getSurplusTime() > 0) {
                SPUtils.getInstance().put(SpKey.EXIT_BIND_PHONE_SURPLUS_TIME, mCCountDownTimer.getSurplusTime() / 1000);
                SPUtils.getInstance().put(SpKey.EXIT_BIND_PHONE_TIME, System.currentTimeMillis() / 1000);
            }
            mCCountDownTimer.cancel();
            mCCountDownTimer = null;
        }
        super.onDestroy();
    }
}

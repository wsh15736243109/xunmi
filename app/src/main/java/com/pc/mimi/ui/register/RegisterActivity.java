package com.pc.mimi.ui.register;

import android.databinding.Observable;
import android.os.Bundle;

import com.pc.mimi.config.SpKey;
import com.pc.mimi.util.CCountDownTimer;
import com.pc.mimi.util.CommonUtil;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityRegisterBinding;

import me.goldze.mvvmhabit.utils.SPUtils;

public class RegisterActivity extends SwipeBackActivity<ActivityRegisterBinding, RegisterViewModel> {
    CCountDownTimer mRegisterDownTimer;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("注册");
        CommonUtil.initTitleBar(this);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.registerCountDown.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                //开始倒计时
                beginCountDownTimeRegister();
            }
        });
    }

    private void beginCountDownTimeRegister() {
        //请求获取验证码
        if (mRegisterDownTimer == null) {
            mRegisterDownTimer = new CCountDownTimer(RegisterActivity.this,
                    viewModel.sendCodeText, viewModel.isGetCodeClick, 60000, 1000);
            mRegisterDownTimer.start();

        } else {
            mRegisterDownTimer.cancel();
            mRegisterDownTimer = null;
            mRegisterDownTimer = new CCountDownTimer(RegisterActivity.this,
                    viewModel.sendCodeText, viewModel.isGetCodeClick, 60000, 1000);
            mRegisterDownTimer.start();
        }
    }

    @Override
    protected void onStart() {
        //如果前一次倒计时与这次进入时间差在上次倒计时结束时间内。则继续计时，
        long currentTime = System.currentTimeMillis() / 1000;
        //取出上次倒计时剩余的时间
        long surplusTime = SPUtils.getInstance().getLong(SpKey.EXIT_REGISTER_ACT_SURPLUS_TIME, 0l);
        if (surplusTime > 0) {
            //上次退出是的系统时间
            long lastExitTime = SPUtils.getInstance().getLong(SpKey.EXIT_REGISTER_ACT_TIME, 0l);
            if (currentTime - lastExitTime < surplusTime) {//继续倒计时
                if (mRegisterDownTimer == null) {
                    mRegisterDownTimer = new CCountDownTimer(RegisterActivity.this,
                            viewModel.sendCodeText, viewModel.isGetCodeClick,
                            (surplusTime - currentTime + lastExitTime) * 1000, 1000);
                    mRegisterDownTimer.start();
                } else {
                    mRegisterDownTimer.cancel();
                    mRegisterDownTimer = null;
                    mRegisterDownTimer = new CCountDownTimer(RegisterActivity.this,
                            viewModel.sendCodeText, viewModel.isGetCodeClick,
                            currentTime - lastExitTime, 1000);
                    mRegisterDownTimer.start();
                }
            } else {//过期不做操作
            }
        }
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        if (mRegisterDownTimer != null) {
            if (mRegisterDownTimer.getSurplusTime() > 0) {
                SPUtils.getInstance().put(SpKey.EXIT_REGISTER_ACT_SURPLUS_TIME, mRegisterDownTimer.getSurplusTime() / 1000);
                SPUtils.getInstance().put(SpKey.EXIT_REGISTER_ACT_TIME, System.currentTimeMillis() / 1000);
            }
            mRegisterDownTimer.cancel();
            mRegisterDownTimer = null;
        }
        super.onDestroy();
    }
}

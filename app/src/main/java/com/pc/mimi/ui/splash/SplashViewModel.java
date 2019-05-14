package com.pc.mimi.ui.splash;

import android.app.Application;
import android.databinding.ObservableField;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import com.xlyuns.xunmi.R;
import com.pc.mimi.ui.login.LoginActivity;
import com.pc.mimi.ui.main.MainActivity;
import com.pc.mimi.util.AppUtils;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.Utils;

public class SplashViewModel extends BaseViewModel {
    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    //倒计时
    private CountDownTimer mTimer;
    //跳过
    public ObservableField<String> skip = new ObservableField<>("");
    //跳过点击
    public BindingCommand skipClick = new BindingCommand(() -> {
        if (mTimer != null) {
            mTimer.cancel();
        }
        judeAndJump();
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    public void initTimer() {
        mTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                skip.set(String.format(Utils.getContext().getString(R.string.skip), (int)
                        (l / 1000 + 0.1)));
            }

            @Override
            public void onFinish() {
                skip.set(String.format(Utils.getContext().getResources().getString(R.string.skip), 0));
                judeAndJump();
            }
        };
        mTimer.start();
    }

    /**
     * 判断后跳转
     */
    private void judeAndJump() {
        if (AppUtils.isLogin()) {
            startActivity(MainActivity.class);
        } else {
            startActivity(LoginActivity.class);
        }
        finish();
    }
}

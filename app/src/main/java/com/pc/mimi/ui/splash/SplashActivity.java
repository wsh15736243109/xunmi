package com.pc.mimi.ui.splash;

import android.os.Bundle;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.xlyuns.xunmi.databinding.ActivitySplashBinding;

import me.goldze.mvvmhabit.base.BaseActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.initTimer();
    }
}

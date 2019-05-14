package com.pc.mimi.ui.login;

import android.os.Bundle;

import com.pc.mimi.util.CommonUtil;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityLoginBinding;

public class LoginActivity extends SwipeBackActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("登录");
        CommonUtil.initTitleBar(this);
    }
}

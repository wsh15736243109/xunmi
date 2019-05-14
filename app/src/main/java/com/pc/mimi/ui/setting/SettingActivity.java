package com.pc.mimi.ui.setting;

import android.os.Bundle;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivitySettingBinding;
import com.pc.mimi.util.CommonUtil;

public class SettingActivity extends SwipeBackActivity<ActivitySettingBinding, SettingViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_setting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("设置");
        CommonUtil.initTitleBar(this);
    }
}

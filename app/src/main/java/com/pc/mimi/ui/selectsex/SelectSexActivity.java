package com.pc.mimi.ui.selectsex;

import android.os.Bundle;

import com.pc.mimi.util.CommonUtil;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivitySelectSexBinding;

public class SelectSexActivity extends SwipeBackActivity<ActivitySelectSexBinding, SelectSexViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_select_sex;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("选择性别");
        CommonUtil.initTitleBar(this);
        viewModel.user_id = getIntent().getExtras().getString("user_id", "");
    }
}

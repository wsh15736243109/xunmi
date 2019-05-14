package com.pc.mimi.ui.modifypassword;

import android.os.Bundle;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityModifyPasswordBinding;
import com.pc.mimi.util.CommonUtil;

public class ModifyPasswordActivity extends SwipeBackActivity<ActivityModifyPasswordBinding, ModifyPasswordViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_modify_password;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("修改密码");
        CommonUtil.initTitleBar(this);
        binding.includeToolbar.tvRight.setText("保存");
        binding.includeToolbar.tvRight.setTextColor(getResources().getColor(R.color.text_F5922F));
        binding.includeToolbar.tvRight.setOnClickListener(v -> viewModel.saveUpdate());
    }
}

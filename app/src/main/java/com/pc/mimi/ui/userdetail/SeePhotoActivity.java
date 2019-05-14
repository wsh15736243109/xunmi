package com.pc.mimi.ui.userdetail;

import android.os.Bundle;

import com.pc.mimi.app.SwipeBackActivity;
import com.pc.mimi.util.CommonUtil;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.xlyuns.xunmi.databinding.ActivitySeePhotoBinding;

public class SeePhotoActivity extends SwipeBackActivity<ActivitySeePhotoBinding, SeePhotoViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_see_photo;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("");
        CommonUtil.initTitleBar(this);
        viewModel.url = getIntent().getExtras().getString("url", "");
        viewModel.see_later_break = getIntent().getExtras().getString("see_later_break", "");
        viewModel.price = getIntent().getExtras().getString("price", "");
        viewModel.initData();
    }
}

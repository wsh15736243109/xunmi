package com.pc.mimi.ui.mine;

import android.os.Bundle;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityPhotoPreviewBinding;
import com.pc.mimi.util.CommonUtil;

public class PhotoPreviewActivity extends SwipeBackActivity<ActivityPhotoPreviewBinding, PhotoPreviewViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_photo_preview;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        setTitle("");
        CommonUtil.initTitleBar(this);
        viewModel.urlID = getIntent().getExtras().getString("url_id", "");
        viewModel.url = getIntent().getExtras().getString("url", "");
        viewModel.see_later_break = getIntent().getExtras().getString("see_later_break", "");
        viewModel.price = getIntent().getExtras().getString("price", "");
        viewModel.setData();
    }
}

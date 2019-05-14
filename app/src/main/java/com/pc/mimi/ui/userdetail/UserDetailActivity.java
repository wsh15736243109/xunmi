package com.pc.mimi.ui.userdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.pc.mimi.adapter.DetailPhotoListAdapter;
import com.pc.mimi.util.CommonUtil;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityUserDetailBinding;
import com.pc.mimi.widget.CustomGridLayoutManager;

import java.util.ArrayList;

public class UserDetailActivity extends SwipeBackActivity<ActivityUserDetailBinding, UserDetailViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_user_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("详情");
        CommonUtil.initTitleBar(this);
        binding.titleBar.tvRight.setText("更多");
        binding.titleBar.tvRight.setOnClickListener(v -> viewModel.more());
        binding.titleBar.tvRight.setTextColor(getResources().getColor(R.color.text_333333));
        Bundle bundle = getIntent().getExtras();
        viewModel.other_user_id = bundle.getString("other_user_id", "");
        initRecycler();
        viewModel.requestOtherInfoListData();
    }

    private void initRecycler() {
        viewModel.mPictureBeans = new ArrayList<>();
        CustomGridLayoutManager c = new CustomGridLayoutManager(this, 4);
        c.setScrollEnable(false);
        binding.recycler.setFocusable(false);
        binding.recycler.setLayoutManager(c);
        viewModel.mDetailPhotoListAdapter = new DetailPhotoListAdapter(viewModel.mPictureBeans);
        binding.recycler.setAdapter(viewModel.mDetailPhotoListAdapter);
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_empty_other_photo_book, null);
        viewModel.mDetailPhotoListAdapter.setEmptyView(inflate);
        viewModel.mDetailPhotoListAdapter.setOnItemClickListener(((adapter, view, position) -> {
//            if (Double.parseDouble(viewModel.mPictureBeans.get(position).getPrice()) > 0) {//红包
//                //TODO
//            } else if (viewModel.mPictureBeans.get(position).getSee_later_break() == 1) {//阅后即焚
//                //TODO
//            } else {//普通
            Bundle bundle = new Bundle();
            bundle.putString("url", viewModel.mPictureBeans.get(position).getPicture_url());
            bundle.putString("see_later_break", viewModel.mPictureBeans.get(position).getSee_later_break() + "");
            bundle.putString("price", viewModel.mPictureBeans.get(position).getPrice() + "");
            startActivity(SeePhotoActivity.class, bundle);
//            }
        }));
    }
}

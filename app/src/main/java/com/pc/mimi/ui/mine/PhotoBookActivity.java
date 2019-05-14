package com.pc.mimi.ui.mine;

import android.os.Bundle;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.PhotoListSelectAdapter;
import com.pc.mimi.app.SwipeBackActivity;
import com.pc.mimi.bean.PictureBean;
import com.xlyuns.xunmi.databinding.ActivityPhotoBookBinding;
import com.pc.mimi.util.CommonUtil;
import com.pc.mimi.widget.CustomGridLayoutManager;

import java.util.List;

public class PhotoBookActivity extends SwipeBackActivity<ActivityPhotoBookBinding, PhotoBookViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_photo_book;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("相册胶卷");
        viewModel.mPictureBeans = (List<PictureBean>) getIntent().getExtras().getSerializable("photos");
        CommonUtil.initTitleBar(this);
        initRecycler();
        viewModel.initData();
    }

    private void initRecycler() {
        binding.recycler.setLayoutManager(new CustomGridLayoutManager(this, 4));
        viewModel.mPhotoListSelectAdapter = new PhotoListSelectAdapter(viewModel.mPictureBeans);
        binding.recycler.setAdapter(viewModel.mPhotoListSelectAdapter);
        viewModel.mPhotoListSelectAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.item:
                    Bundle bundle = new Bundle();
                    bundle.putString("url", viewModel.mPictureBeans.get(position).getPicture_url());
                    bundle.putString("url_id", viewModel.mPictureBeans.get(position).getUser_picture_id());
                    bundle.putString("see_later_break", viewModel.mPictureBeans.get(position).getSee_later_break() + "");
                    bundle.putString("price", viewModel.mPictureBeans.get(position).getPrice() + "");
                    startActivity(PhotoPreviewActivity.class, bundle);
                    break;
                case R.id.check:
                    for (PictureBean p : viewModel.mPictureBeans) {
                        p.setSelected(false);
                    }
                    viewModel.mPictureBeans.get(position).setSelected(true);
                    viewModel.selectPicId = viewModel.mPictureBeans.get(position).getUser_picture_id();
                    viewModel.see_later_break = viewModel.mPictureBeans.get(position).getSee_later_break() + "";
                    viewModel.price = viewModel.mPictureBeans.get(position).getPrice();
                    viewModel.mPhotoListSelectAdapter.notifyDataSetChanged();
                    break;
            }
        }));
    }
}

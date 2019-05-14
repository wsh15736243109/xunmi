package com.pc.mimi.ui.publishmeeting;

import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.pc.mimi.adapter.GridImageAdapter;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.AreaSelectAdapter;
import com.pc.mimi.app.SwipeBackActivity;
import com.pc.mimi.bean.AreaBean;
import com.xlyuns.xunmi.databinding.ActivityPublishMeetingBinding;
import com.pc.mimi.util.CommonUtil;
import com.pc.mimi.util.inject.FindView;
import com.pc.mimi.util.inject.Init;
import com.pc.mimi.util.inject.OnClick;
import com.pc.mimi.util.inject.ViewUtils;
import com.pc.mimi.widget.CustomDialog;

import java.util.ArrayList;

import me.goldze.mvvmhabit.utils.KLog;

public class PublishMeetingActivity extends SwipeBackActivity<ActivityPublishMeetingBinding, PublishMeetingViewModel> {

    public SelectCityEvent mSelectCityEvent;
    public CustomDialog mSelectDialog = null;
    public CustomDialog mSelectPro;
    public static final String DATING_CONTENT = "dating_content";

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_publish_meeting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {

        viewModel.mdatingContent = getIntent().getExtras().getString(DATING_CONTENT, "");
        this.setTitle(viewModel.mdatingContent);
        CommonUtil.initTitleBar(this);
        initRecycler();
    }

    //上传多图的列表
    private void initRecycler() {
        viewModel.mStringList = new ArrayList<>();
        viewModel.mGridImageAdapter = new GridImageAdapter(viewModel.mStringList);
        binding.recycler.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recycler.setAdapter(viewModel.mGridImageAdapter);
        viewModel.mGridImageAdapter.setOnAddPicClickListener(new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                mSelectPro = new CustomDialog(PublishMeetingActivity.this, R.layout.dialog_choose_pic,
                        true, true, Gravity.BOTTOM,
                        content -> {
                            content.findViewById(R.id.camera).setOnClickListener(view -> {  //相机
                                mSelectPro.dismiss();
                                PictureSelector.create(PublishMeetingActivity.this)
                                        .openCamera(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                        .theme(R.style.pic_select_style)
                                        .previewImage(true)// 是否可预览图片 true or false
                                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                                        .setOutputCameraPath("/PicPath")// 自定义拍照保存路径,可不填
                                        .enableCrop(false)// 是否裁剪 true or false
                                        .compress(true)// 是否压缩 true or false
//                                        .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
//                                        .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                                        .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                                        .scaleEnabled(false)// 裁剪是否可放大缩小图片 true or false
//                                        .isDragFrame(true)// 是否可拖动裁剪框(固定)
                                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                            });
                            content.findViewById(R.id.gallery).setOnClickListener(view -> { //相册
                                mSelectPro.dismiss();
                                PictureSelector.create(PublishMeetingActivity.this)
                                        .openGallery(PictureMimeType.ofImage())
                                        .theme(R.style.pic_select_style)
                                        .maxSelectNum(9)
                                        .imageSpanCount(4)
                                        .selectionMode(PictureConfig.MULTIPLE)
                                        .previewImage(true)
                                        .isCamera(false)
                                        .isZoomAnim(true)
                                        .compress(true)
                                        .selectionMedia(viewModel.mStringList)
                                        .forResult(PictureConfig.CHOOSE_REQUEST);
                            });
                            content.findViewById(R.id.cancel).setOnClickListener(view -> { //取消
                                mSelectPro.dismiss();
                            });
                        }, true);
                mSelectPro.show();
            }

            @Override
            public void onItemClick(int position, View v) {

            }
        });
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.mettingCity.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (mSelectDialog != null) mSelectDialog = null;
                if (mSelectCityEvent != null) mSelectCityEvent = null;
                mSelectCityEvent = new SelectCityEvent();
                mSelectDialog = new CustomDialog(PublishMeetingActivity.this, R.layout.dialog_select_city,
                        true, true, Gravity.BOTTOM,
                        content -> ViewUtils.inject(content, mSelectCityEvent), true);
                mSelectDialog.show();
                viewModel.getCityList();
            }
        });
        viewModel.uc.firstdef.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mSelectCityEvent.setDefaultSelect();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    viewModel.dealResult(data);
                    break;
            }
        }
    }

    //选择城市的弹窗中事件
    private class SelectCityEvent {
        @FindView(R.id.rv_city)
        RecyclerView mRvCiry;
        @FindView(R.id.rv_area)
        RecyclerView mRvArea;
        @FindView(R.id.area_select)
        TextView mAreaSelect;
        String mSelectId = "";

        @Init
        private void initViews() {
            //城市
            viewModel.mCityBeans = new ArrayList<>();
            viewModel.mCitySelectAdapter = new AreaSelectAdapter(viewModel.mCityBeans);
            initRecycler(mRvCiry, viewModel.mCitySelectAdapter);
            viewModel.mCitySelectAdapter.setOnItemClickListener((adapter, view, position) -> {
                KLog.d("--------------position-------" + position);
                //清除区域全部选中
                if (viewModel.mAreaBeans.size() > 0) {
                    for (AreaBean areaBean : viewModel.mAreaBeans) {
                        areaBean.setSelected(false);
                    }
                    viewModel.mAreaSelectAdapter.notifyDataSetChanged();
                }
                //设置本选项选中
                if (viewModel.mCityBeans.size() > 0) {
                    for (AreaBean areaBean : viewModel.mCityBeans) {
                        areaBean.setSelected(false);
                    }
                    mAreaSelect.setText(viewModel.mCityBeans.get(position).getCityname());
                    mSelectId = viewModel.mCityBeans.get(position).getCity_id();
                    viewModel.mCityBeans.get(position).setSelected(true);
                    viewModel.mCitySelectAdapter.notifyDataSetChanged();
                }
                viewModel.loadAreaList(viewModel.mCityBeans.get(position).getCity_id());
            });
            //区域
            viewModel.mAreaBeans = new ArrayList<>();
            viewModel.mAreaSelectAdapter = new AreaSelectAdapter(viewModel.mAreaBeans);
            initRecycler(mRvArea, viewModel.mAreaSelectAdapter);
            viewModel.mAreaSelectAdapter.setOnItemClickListener(((adapter, view, position) -> {
                //清除城市全部选中
                if (viewModel.mCityBeans.size() > 0) {
                    for (AreaBean areaBean : viewModel.mCityBeans) {
                        areaBean.setSelected(false);
                    }
                    viewModel.mCitySelectAdapter.notifyDataSetChanged();
                }
                //设置本选项选中
                if (viewModel.mAreaBeans.size() > 0) {
                    for (AreaBean areaBean : viewModel.mAreaBeans) {
                        areaBean.setSelected(false);
                    }
                    mAreaSelect.setText(viewModel.mAreaBeans.get(position).getCityname());
                    mSelectId = viewModel.mAreaBeans.get(position).getCity_id();
                    viewModel.mAreaBeans.get(position).setSelected(true);
                    viewModel.mAreaSelectAdapter.notifyDataSetChanged();
                }
            }));
        }

        @OnClick({R.id.confir})
        private void onClicked(View view) {
            switch (view.getId()) {
                case R.id.confir:
                    mSelectDialog.dismiss();
                    //TODO  选中了城市的操作
//                    ToastUtils.showShort("---name-->" + mAreaSelect.getText().toString() + "--Id-->" + mSelectId);
                    viewModel.meetingCityText.set(mAreaSelect.getText().toString());
                    break;
            }
        }

        private void initRecycler(RecyclerView rvCiry, AreaSelectAdapter citySelectAdapter) {
            rvCiry.setLayoutManager(new LinearLayoutManager(PublishMeetingActivity.this));
            citySelectAdapter.bindToRecyclerView(rvCiry);
        }

        //设置默认选中
        public void setDefaultSelect() {
            mAreaSelect.setText(viewModel.mCityBeans.get(0).getCityname());
        }
    }
}

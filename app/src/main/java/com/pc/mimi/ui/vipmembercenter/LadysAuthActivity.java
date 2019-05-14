package com.pc.mimi.ui.vipmembercenter;

import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityLadysAuthBinding;
import com.pc.mimi.util.CommonUtil;
import com.pc.mimi.widget.CustomDialog;

public class LadysAuthActivity extends SwipeBackActivity<ActivityLadysAuthBinding, LadysAuthViewModel> {
    public CustomDialog mSelectPro;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_ladys_auth;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("认证中心");
        CommonUtil.initTitleBar(this);
        binding.includeToolbar.tvRight.setText("确认上传");
        binding.includeToolbar.tvRight.setTextColor(getResources().getColor(R.color.text_F5922F));
        binding.includeToolbar.tvRight.setOnClickListener(v -> viewModel.uploadAuthPic());
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.pic.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (mSelectPro != null) mSelectPro = null;
                mSelectPro = new CustomDialog(LadysAuthActivity.this, R.layout.dialog_choose_pic,
                        true, true, Gravity.BOTTOM,
                        content -> {
                            content.findViewById(R.id.camera).setOnClickListener(view -> {  //相机
                                mSelectPro.dismiss();
                                PictureSelector.create(LadysAuthActivity.this)
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
                                        .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                                        .isDragFrame(true)// 是否可拖动裁剪框(固定)
                                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                            });
                            content.findViewById(R.id.gallery).setOnClickListener(view -> { //相册
                                mSelectPro.dismiss();
                                PictureSelector.create(LadysAuthActivity.this)
                                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                        .theme(R.style.pic_select_style)
                                        .imageSpanCount(4)// 每行显示个数 int
                                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                        .previewImage(true)// 是否可预览图片 true or false
                                        .isCamera(false)// 是否显示拍照按钮 true or false
                                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                                        .enableCrop(false)// 是否裁剪 true or false
                                        .compress(true)// 是否压缩 true or false
//                                        .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
//                                        .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                                        .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                                        .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                                        .scaleEnabled(false)// 裁剪是否可放大缩小图片 true or false
//                                        .isDragFrame(true)// 是否可拖动裁剪框(固定)
                                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                            });
                            content.findViewById(R.id.cancel).setOnClickListener(view -> { //取消
                                mSelectPro.dismiss();
                            });
                        }, true);
                mSelectPro.show();
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

}

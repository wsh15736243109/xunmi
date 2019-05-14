package com.pc.mimi.ui.mine;

import android.content.Intent;
import android.databinding.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.pc.mimi.adapter.PhotoListAdapter;
import com.pc.mimi.util.CommonUtil;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.BaseLazyFragment;
import com.xlyuns.xunmi.databinding.FragmentMineBinding;
import com.pc.mimi.widget.CustomDialog;
import com.pc.mimi.widget.CustomGridLayoutManager;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MineFragment extends BaseLazyFragment<FragmentMineBinding, MineViewModel> {
    public CustomDialog mSelectPro;

    @Override
    public void onFirstUserInvisible() {
    }

    @Override
    public void onUserVisible() {
    }

    @Override
    public void onUserInvisible() {
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_mine;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.pic.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (mSelectPro != null) mSelectPro = null;
                mSelectPro = new CustomDialog(getActivity(), R.layout.dialog_choose_pic,
                        true, true, Gravity.BOTTOM,
                        content -> {
                            content.findViewById(R.id.camera).setOnClickListener(view -> {  //相机
                                mSelectPro.dismiss();
                                PictureSelector.create(MineFragment.this)
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
                                PictureSelector.create(MineFragment.this)
                                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                        .theme(R.style.pic_select_style)
                                        .imageSpanCount(4)// 每行显示个数 int
                                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                        .maxSelectNum(6)
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
    public void initData() {
        //设置页面滚动监听
        setScrollListen();
        //设置我的相册list
        initPhotoList();
        //加载我的信息
        viewModel.requestUserInfo();
    }

    private void initPhotoList() {
        viewModel.mPictureBooks = new ArrayList<>();
        viewModel.mPhotoListAdapter = new PhotoListAdapter(viewModel.mPictureBooks);
        CustomGridLayoutManager c = new CustomGridLayoutManager(getActivity(), 4);
        c.setScrollEnable(false);
        binding.recyclerPh.setLayoutManager(c);
        viewModel.mPhotoListAdapter.bindToRecyclerView(binding.recyclerPh);
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty_photo_book, null);
        inflate.setOnClickListener(v -> viewModel.uc.pic.set(!viewModel.uc.pic.get()));
        viewModel.mPhotoListAdapter.setEmptyView(inflate);
        viewModel.mPhotoListAdapter.setOnItemClickListener(((adapter, view, position) -> {
            Bundle bundle =  new Bundle();
            bundle.putString("url",viewModel.mPictureBooks.get(position).getPicture_url());
            bundle.putString("url_id",viewModel.mPictureBooks.get(position).getUser_picture_id());
            bundle.putString("see_later_break",viewModel.mPictureBooks.get(position).getSee_later_break()+"");
            bundle.putString("price",viewModel.mPictureBooks.get(position).getPrice()+"");
            startActivity(PhotoPreviewActivity.class,bundle);
        }));
    }

    private void setScrollListen() {
        int height = CommonUtil.getScreenHeidth(getActivity()) / 5;
        CommonUtil.setAlpha(binding.scrollView, height,
                new CommonUtil.onTextColor() {
                    @Override
                    public void change(int alpha) {
                        if (alpha < 150) {
                            binding.includeToolbar.title.setTextColor(Color.argb(255, 255, 255, 255));
                            binding.includeToolbar.tvRight.setTextColor(Color.argb(255, 255, 255, 255));
                            binding.includeToolbar.leftImg.setBackgroundResource(R.drawable.ic_setting);
                            binding.line.setBackgroundColor(Color.argb(0, 0, 0, 0));
                        }
                    }

                    @Override
                    public void changeEnd(int alpha) {
                        binding.includeToolbar.title.setTextColor(Color.argb(alpha, 0, 0, 0));
                        binding.includeToolbar.tvRight.setTextColor(Color.argb(alpha, 27, 38, 138));
                        binding.includeToolbar.leftImg.setBackgroundResource(R.drawable.ic_setting);
                        binding.line.setBackgroundColor(Color.argb(alpha, 225, 225, 225));
                    }
                },
                new Object[]{binding.statusView, binding.includeToolbar.toolbar});
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

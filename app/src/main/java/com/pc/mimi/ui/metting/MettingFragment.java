package com.pc.mimi.ui.metting;

import android.content.Intent;
import android.databinding.Observable;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.pc.mimi.adapter.MeetingAdapter;
import com.pc.mimi.widget.BackgroundDarkPopupWindow;
import com.pc.mimi.widget.dialogfragment.PhotoInfo;
import com.pc.mimi.widget.dialogfragment.ZoomPhotoView;
import com.pc.mimi.widget.mettingview.CircleItemClickListenerCallBack;
import com.pc.mimi.widget.mettingview.CommentConfig;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.BaseLazyFragment;
import com.xlyuns.xunmi.databinding.FragmentMettingBinding;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.ToastUtils;

import static android.app.Activity.RESULT_OK;

public class MettingFragment extends BaseLazyFragment<FragmentMettingBinding, MettingViewModel> {

    BackgroundDarkPopupWindow mSelectMettingPop;
    BackgroundDarkPopupWindow mSelectSexPop;

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
        return R.layout.fragment_metting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.mMettingListBeans = new ArrayList<>();
        viewModel.mMeetingAdapter = new MeetingAdapter(viewModel.mMettingListBeans);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel.mMeetingAdapter.setHasStableIds(true);
        viewModel.mMeetingAdapter.bindToRecyclerView(binding.recycler);
        viewModel.mMeetingAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.comment:
                    if (viewModel.mMettingListBeans.get(position).getNo_comment() == 0) {
                        viewModel.showEditDialog(position, -1, viewModel.mMettingListBeans.get(position).getAll_dating_id(), "", false);
                    } else {
                        ToastUtils.showShort("对方设置了禁止评论");
                    }
                    break;
                case R.id.thumbs_up:
                    if (viewModel.mMettingListBeans.get(position).getIs_good_for_this() == 0) {
                        viewModel.requestThumbUp(viewModel.mMettingListBeans.get(position).getAll_dating_id(), position, true);
                    } else {
                        viewModel.requestThumbUp(viewModel.mMettingListBeans.get(position).getAll_dating_id(), position, false);
                    }
                    break;
                case R.id.sign_up://报名
                    viewModel.signUp(viewModel.mMettingListBeans.get(position).getAll_dating_id());
                    break;
                case R.id.report://举报
                    viewModel.report(view, viewModel.mMettingListBeans.get(position).getUser_id());
                    break;
            }
        }));
        viewModel.mMeetingAdapter.setCallBack(new CircleItemClickListenerCallBack() {
            @Override
            public void deleteCircle(String circleId) {

            }

            @Override
            public void deleteComment(int circlePosition, String commentItemId) {

            }

            @Override
            public void showEditTextBody(CommentConfig config) {
                viewModel.showEditDialog(config.circlePosition, config.commentPosition, viewModel.mMettingListBeans.get(config.circlePosition).getAll_dating_id(),
                        config.agrument_id, true);
            }

            @Override
            public void addFavort(int circlePosition) {

            }

            @Override
            public void deleteFavort(int circlePosition, String favorId) {

            }

            @Override
            public void clickName(String userName, String userId) {
                ToastUtils.showShort(userName);
            }

            @Override
            public void onClickCommentName(String name, String id) {

            }

            @Override
            public void preViewPic(View view, int position, List<PhotoInfo> photoInfos) {
                final ZoomPhotoView zoomPhotoView = ZoomPhotoView
                        .size(12)
                        .bacColor(Color.BLACK)
                        .color(Color.WHITE)
                        .current(position)
                        .build();
                //添加图片数据进去
                List<String> list = new ArrayList<>();
                for (PhotoInfo photoInfo : photoInfos) {
                    list.add(photoInfo.url);
                }
                zoomPhotoView.addImage(list);
                //添加预览中的图片点击事件
                zoomPhotoView.addPicClicListener((viewModel, poi) -> {
                    zoomPhotoView.dismiss();
                });
                //弹出Dialog
                zoomPhotoView.show(getChildFragmentManager(), "tag");
            }
        });
        viewModel.requestData();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.left.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                View inflateView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_pub_layout, null);
//                inflateView.findViewById(R.id.all).setOnClickListener(v -> {//全部
//                    mSelectMettingPop.dismiss();
//                    viewModel.left_s.set("全部");
//                });
                inflateView.findViewById(R.id.new_p).setOnClickListener(v -> {//最新发布
                    mSelectMettingPop.dismiss();
                    viewModel.left_s.set("最新发布");
                    viewModel.mettingType = 0;
                    viewModel.requestData();
                });
                inflateView.findViewById(R.id.near_p).setOnClickListener(v -> {//最近约会
                    mSelectMettingPop.dismiss();
                    viewModel.left_s.set("最近约会");
                    viewModel.mettingType = 1;
                    viewModel.requestData();
                });
                mSelectMettingPop = new BackgroundDarkPopupWindow(inflateView, WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                mSelectMettingPop.setFocusable(true);
                mSelectMettingPop.setBackgroundDrawable(new ColorDrawable());
                mSelectMettingPop.setDarkStyle(R.style.DarkAnimation);
                mSelectMettingPop.setDarkColor(Color.parseColor("#a0000000"));//颜色
                mSelectMettingPop.setOutsideTouchable(true);
                mSelectMettingPop.darkBelow(binding.pop);
                mSelectMettingPop.drakFillView(binding.smart);
                mSelectMettingPop.showAsDropDown(binding.pop);
            }
        });
        viewModel.uc.middle.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                View inflateView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_sex_layout, null);
                inflateView.findViewById(R.id.all).setOnClickListener(v -> {//不限性别
                    mSelectSexPop.dismiss();
                    viewModel.middle_s.set("不限性别");
                    viewModel.mettingSex = 0;
                    viewModel.requestData();
                });
                inflateView.findViewById(R.id.g_s).setOnClickListener(v -> {//女
                    mSelectSexPop.dismiss();
                    viewModel.middle_s.set("女");
                    viewModel.mettingSex = 1;
                    viewModel.requestData();
                });
                inflateView.findViewById(R.id.b_s).setOnClickListener(v -> {//男
                    mSelectSexPop.dismiss();
                    viewModel.middle_s.set("男");
                    viewModel.mettingSex = 2;
                    viewModel.requestData();
                });
                mSelectSexPop = new BackgroundDarkPopupWindow(inflateView, WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                mSelectSexPop.setFocusable(true);
                mSelectSexPop.setBackgroundDrawable(new ColorDrawable());
                mSelectSexPop.setDarkStyle(R.style.DarkAnimation);
                mSelectSexPop.setDarkColor(Color.parseColor("#a0000000"));//颜色
                mSelectSexPop.setOutsideTouchable(true);
                mSelectSexPop.darkBelow(binding.pop);
                mSelectSexPop.drakFillView(binding.smart);
                mSelectSexPop.showAsDropDown(binding.pop);
            }
        });

        viewModel.uc.signUp.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                PictureSelector.create(MettingFragment.this)
                        .openGallery(PictureMimeType.ofImage())
                        .theme(R.style.pic_select_style)
                        .imageSpanCount(4)
                        .selectionMode(PictureConfig.SINGLE)
                        .previewImage(true)
                        .isCamera(false)
                        .isZoomAnim(true)
                        .compress(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
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

package com.pc.mimi.ui.metting;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.MyMeetingAdapter;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityMyMettingBinding;
import com.pc.mimi.ui.mine.SignUpAcountActivity;
import com.pc.mimi.util.CommonUtil;
import com.pc.mimi.widget.dialogfragment.PhotoInfo;
import com.pc.mimi.widget.dialogfragment.ZoomPhotoView;
import com.pc.mimi.widget.mettingview.CircleItemClickListenerCallBack;
import com.pc.mimi.widget.mettingview.CommentConfig;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.ToastUtils;

public class MyMeetingActivity extends SwipeBackActivity<ActivityMyMettingBinding, MyMeetingViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_my_metting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("我的约会");
        CommonUtil.initTitleBar(this);
        initRecycler();
        viewModel.requestMeetings();
    }

    private void initRecycler() {
        viewModel.mMettingListBeans = new ArrayList<>();
        viewModel.mMyMeetingAdapter = new MyMeetingAdapter(viewModel.mMettingListBeans);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        viewModel.mMyMeetingAdapter.bindToRecyclerView(binding.recycler);
        viewModel.mMyMeetingAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
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
                case R.id.sign_up:
                    Bundle bundle = new Bundle();
                    bundle.putString("all_dating_id", viewModel.mMettingListBeans.get(position).getAll_dating_id());
                    startActivity(SignUpAcountActivity.class, bundle);
                    break;
                case R.id.finish_sign_up:
                    viewModel.finishSignUp(position);
                    break;
            }
        }));
        viewModel.mMyMeetingAdapter.setCallBack(new CircleItemClickListenerCallBack() {
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
                zoomPhotoView.show(MyMeetingActivity.this.getSupportFragmentManager(), "tag");
            }
        });

    }
}

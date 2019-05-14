package com.pc.mimi.ui.chat;

import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.gyf.barlibrary.ImmersionBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.pc.mimi.adapter.ChatAdapter;
import com.pc.mimi.util.CommonUtil;
import com.pc.mimi.util.SoftKeyBoardListener;
import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityChatBinding;

import java.util.ArrayList;

import me.goldze.mvvmhabit.utils.KLog;


public class ChatActivity extends SwipeBackActivity<ActivityChatBinding, ChatViewModel> {

    @Override
    public void initParam() {
        ImmersionBar.with(this).statusBarColor(me.goldze.mvvmhabit.R.color.transparent).fitsSystemWindows(false).statusBarDarkFont(true).init();
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_chat;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.mUserId = getIntent().getExtras().getString("user_id", "");
        viewModel.mUserName = getIntent().getExtras().getString("user_name", "");
        this.setTitle(viewModel.mUserName);
        CommonUtil.initTitleBar(this);
        initRecycler();
        SoftKeyBoardListener.setListener(ChatActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                KLog.d("键盘显示 高度" + height);
                binding.recycler.scrollToPosition(viewModel.mMessageBeans.size() - 1);
            }

            @Override
            public void keyBoardHide(int height) {
                KLog.d("键盘隐藏 高度" + height);
            }
        });
    }

    private void initRecycler() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        viewModel.mMessageBeans = new ArrayList<>();
        viewModel.mChatAdapter = new ChatAdapter(viewModel.mMessageBeans);
        binding.recycler.setAdapter(viewModel.mChatAdapter);
        viewModel.mChatAdapter.setUpFetchEnable(true);
        viewModel.mChatAdapter.setStartUpFetchPosition(2);
        viewModel.mChatAdapter.setUpFetchListener(() -> {
            KLog.d("------------UP--LOAD---------------");
            startUpFetch();
        });
        viewModel.getChatRecord(true);
    }

    private void startUpFetch() {
        viewModel.mChatAdapter.setUpFetching(true);
        viewModel.getChatRecord(false);
    }

    @Override
    public void initViewObservable() {
        viewModel.sendfinish.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                binding.recycler.scrollToPosition(viewModel.mMessageBeans.size() - 1);
            }
        });
        viewModel.sendPicMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                PictureSelector.create(ChatActivity.this)
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

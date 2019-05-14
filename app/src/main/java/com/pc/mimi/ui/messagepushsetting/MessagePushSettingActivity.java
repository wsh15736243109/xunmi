package com.pc.mimi.ui.messagepushsetting;

import android.os.Bundle;

import com.xlyuns.xunmi.BR;
import com.xlyuns.xunmi.R;
import com.pc.mimi.app.SwipeBackActivity;
import com.xlyuns.xunmi.databinding.ActivityMessagePushSettingBinding;
import com.pc.mimi.util.CommonUtil;

public class MessagePushSettingActivity extends SwipeBackActivity<ActivityMessagePushSettingBinding, MessagePushSettingViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_message_push_setting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        this.setTitle("消息推送设置");
        CommonUtil.initTitleBar(this);
    }
}

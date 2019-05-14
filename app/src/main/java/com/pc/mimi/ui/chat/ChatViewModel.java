package com.pc.mimi.ui.chat;

import android.app.Application;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.dhh.websocket.SendMsgBean;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.pc.mimi.adapter.ChatAdapter;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.util.DateUtil;
import com.pc.mimi.webSocket.WsManager;
import com.pc.mimi.websocketim.MessageBean;
import com.pc.mimi.config.CommonTags;

import java.io.File;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ChatViewModel extends BaseViewModel {
    public ChatViewModel(@NonNull Application application) {
        super(application);
    }

    public String mUserId = "";
    public String mUserName = "";
    public ChatAdapter mChatAdapter;
    public List<MessageBean> mMessageBeans;
    //发送按钮是否可见
    public ObservableInt sendIsvisible = new ObservableInt(View.GONE);
    public ObservableField<String> edit_text = new ObservableField<>("");
    /**
     * 输入框的变化监听
     */
    public BindingCommand<String> inputTextChanged = new BindingCommand<String>(s -> {
        if (s.length() > 0) {
            sendIsvisible.set(View.VISIBLE);
        } else {
            sendIsvisible.set(View.GONE);
        }
        edit_text.set(s);
    });

    @Override
    public void onCreate() {
        Messenger.getDefault().register(this, CommonTags.RECEIVE_MSG, String.class, message -> {
            KLog.d("MessageBean-->" + message);
            MessageBean messageBean = new Gson().fromJson(message, MessageBean.class);
            messageBean.setIsMe(0);
            if (messageBean.getContent_type() == 1) {
                messageBean.setContent(API.COMMON_PIC_URL + messageBean.getContent());
                mMessageBeans.add(messageBean);
            } else {
                mMessageBeans.add(messageBean);
            }
//            mChatAdapter.notifyDataSetChanged();
            mChatAdapter.notifyItemInserted(mMessageBeans.size() );
            sendfinish.set(!sendfinish.get());
        });
    }

    ObservableBoolean sendfinish = new ObservableBoolean(false);
    ObservableBoolean sendPicMsg = new ObservableBoolean(false);
    /**
     * 发送图片消息
     */
    public BindingCommand sendPic = new BindingCommand(() -> {
        sendPicMsg.set(!sendPicMsg.get());
    });
    /**
     * 发送消息
     */
    public BindingCommand sendMsg = new BindingCommand(() -> {
//        RxWebSocket.sendMsg(CommonTags.WS_URL, new SendMsgBean(mUserId, 0, "hello"));
        WsManager.getInstance().sendMsg(new SendMsgBean(mUserId, 0, edit_text.get()));
        MessageBean messageBean = new MessageBean();
        messageBean.setSend_time(DateUtil.milliseconds2String(System.currentTimeMillis()));
        messageBean.setContent(edit_text.get());
        messageBean.setContent_type(0);
        messageBean.setIsMe(1);
        messageBean.setOut_user_avatar(AppUtils.getUser().getAvatar());
        mMessageBeans.add(messageBean);
        mChatAdapter.notifyItemInserted(mMessageBeans.size() );
//        mChatAdapter.notifyItemChanged(mMessageBeans.size() - 1);
//        mChatAdapter.notifyDataSetChanged();
        edit_text.set("");
        sendfinish.set(!sendfinish.get());
    });
    private int pageNum = 0;

    /**
     * 获取消息的记录
     */
    public void getChatRecord(boolean isfirst) {
        int fromPoi = mMessageBeans.size();
        if (isfirst) {
            pageNum = 0;
            mMessageBeans.clear();
        } else {
            pageNum++;
        }
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).getChatRecord(AppUtils.getUser().getUser_id(), mUserId, pageNum + ""),
                getLifecycleProvider(), disposable -> {
                },
                new ApiDisposableObserver<List<MessageBean>>() {
                    @Override
                    public void onResult(List<MessageBean> o) {
                        if (!isfirst) {
                            if (o.size() == 0) {
                                mChatAdapter.setUpFetchEnable(false);
                                return;
                            }
                            for (MessageBean messageBean : o) {
                                if (messageBean.getContent_type() == 1)
                                    messageBean.setContent(API.COMMON_PIC_URL + messageBean.getContent());
                            }
                            mChatAdapter.addData(0, o);
                            mChatAdapter.setUpFetching(false);
                        } else {
                            for (MessageBean messageBean : o) {
                                if (messageBean.getContent_type() == 1)
                                    messageBean.setContent(API.COMMON_PIC_URL + messageBean.getContent());
                            }
                            mMessageBeans.addAll(o);
                            mChatAdapter.notifyDataSetChanged();
                            sendfinish.set(!sendfinish.get());
                        }
                    }

                    @Override
                    public void dialogDismiss() {
                    }

                });
    }

    /**
     * 处理选择图片接过
     *
     * @param data
     */
    public void dealResult(Intent data) {
        KLog.d("------------------dealResult-------------------");
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        setPicChoose(selectList.get(0).getCompressPath());
    }

    //设置图片上传图片,
    private void setPicChoose(String compressPath) {
        MessageBean messageBean = new MessageBean();
        messageBean.setAddtime(DateUtil.milliseconds2String(System.currentTimeMillis()));
        messageBean.setContent(compressPath);
        messageBean.setContent_type(1);
        messageBean.setIsMe(1);
        messageBean.setOut_user_avatar(AppUtils.getUser().getAvatar());
        mMessageBeans.add(messageBean);
        mChatAdapter.notifyItemChanged(mMessageBeans.size() - 1);
        sendfinish.set(!sendfinish.get());

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
        File file = new File(compressPath);
        RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), file);//表单类型
        KLog.d("-------------getUser_id-----------------" + AppUtils.getUser().getUser_id());
        builder.addFormDataPart("user_id", AppUtils.getUser().getUser_id());//传入服务器需要的key，和相应value值
        builder.addFormDataPart("files", file.getName(), body); //添加图片数据，body创建的请求体
        List<MultipartBody.Part> parts = builder.build().parts();
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).uploadImage(parts)
                , getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<String>() {
                    @Override
                    public void onResult(String o) {
                        WsManager.getInstance().sendMsg(new SendMsgBean(mUserId, 1, o));
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });

    }
}

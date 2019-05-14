package com.pc.mimi.ui.message;

import android.app.Application;
import android.support.annotation.NonNull;

import com.pc.mimi.bean.MessageListBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.pc.mimi.adapter.MessageListAdapter;
import com.pc.mimi.util.AppUtils;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class MessageListViewModel extends BaseViewModel {
    public MessageListViewModel(@NonNull Application application) {
        super(application);
    }
    MessageListAdapter mPersonInfoListAdapter;
    List<MessageListBean> mMessageListBeans;
    //刷新
    public BindingCommand<SmartRefreshLayout> onRefresh = new BindingCommand<SmartRefreshLayout>(s -> {
        requestMessageList();
        s.finishRefresh();
    });

    public void requestMessageList() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestMessageList(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> {
                },
                new ApiDisposableObserver<List<MessageListBean>>() {
                    @Override
                    public void onResult(List<MessageListBean> o) {
                        mMessageListBeans.clear();
                        mMessageListBeans.addAll(o);
                        mPersonInfoListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {

                    }
                });
    }
}

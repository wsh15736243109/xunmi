package com.pc.mimi.ui.mine;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pc.mimi.bean.SignUpAccountListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.pc.mimi.adapter.SignUpAcountListAdapter;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.util.AppUtils;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class SignUpAcountViewModel extends BaseViewModel {
    public String all_dating_id = "";

    public SignUpAcountViewModel(@NonNull Application application) {
        super(application);
    }

    SignUpAcountListAdapter mSignUpAcountListAdapter;
    List<SignUpAccountListBean> mSignUpAccountListBeans;
    //下拉刷新
    public BindingCommand<SmartRefreshLayout> onRefresh = new BindingCommand<SmartRefreshLayout>(smartRefreshLayout -> {
        requestSignUpAccountList();
        smartRefreshLayout.finishRefresh();
    });

    /**
     * 请求报名人员列表
     */
    public void requestSignUpAccountList() {
        if (TextUtils.isEmpty(all_dating_id)) {
            return;
        }
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestSignUpAccountList(AppUtils.getUser().getUser_id(), all_dating_id),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<List<SignUpAccountListBean>>() {
                    @Override
                    public void onResult(List<SignUpAccountListBean> o) {
                        mSignUpAccountListBeans.clear();
                        mSignUpAccountListBeans.addAll(o);
                        mSignUpAcountListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }
}

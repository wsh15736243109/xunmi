package com.pc.mimi.ui.myfavourite;

import android.app.Application;
import android.support.annotation.NonNull;

import com.pc.mimi.bean.PersonBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import com.pc.mimi.adapter.PersonalInfoListAdapter;
import com.pc.mimi.util.AppUtils;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

import java.util.List;

public class MyFavourIteViewModel extends BaseViewModel {

    PersonalInfoListAdapter mPersonInfoListAdapter;
    List<PersonBean> personBeanList;

    public MyFavourIteViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand<SmartRefreshLayout> onRefresh = new BindingCommand<SmartRefreshLayout>(smartRefreshLayout -> {smartRefreshLayout.finishRefresh();});

    public void requestMyFavourListData() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestMyFavourLis(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(), new ApiDisposableObserver<List<PersonBean>>() {
                    @Override
                    public void onResult(List<PersonBean> o) {
                        personBeanList.clear();
                        personBeanList.addAll(o);
                        mPersonInfoListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }
}

package com.pc.mimi.ui.list;

import android.app.Application;
import android.support.annotation.NonNull;

import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.adapter.IncomeRichListAdapter;
import com.pc.mimi.bean.IncomeRechListBean;
import com.pc.mimi.util.AppUtils;
import me.goldze.mvvmhabit.base.BaseViewModel;

import java.util.List;

public class IncomeRichListViewModel extends BaseViewModel {
    public IncomeRichListViewModel(@NonNull Application application) {
        super(application);
    }

    public List<IncomeRechListBean> incomeRechListBeans;
    public  IncomeRichListAdapter incomeRichListAdapter;

    /**
     * 富豪榜
     */
    public void requestRichListData() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestRechList(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(), new ApiDisposableObserver<List<IncomeRechListBean>>() {
                    @Override
                    public void onResult(List<IncomeRechListBean> o) {
                        incomeRechListBeans.clear();
                        incomeRechListBeans.addAll(o);
                        incomeRichListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });

    }

    /**
     * 收入榜
     */
    public void requestIncomeListData() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestIncomeList(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(), new ApiDisposableObserver<List<IncomeRechListBean>>() {
                    @Override
                    public void onResult(List<IncomeRechListBean> o) {
                        incomeRechListBeans.clear();
                        incomeRechListBeans.addAll(o);
                        incomeRichListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }

}

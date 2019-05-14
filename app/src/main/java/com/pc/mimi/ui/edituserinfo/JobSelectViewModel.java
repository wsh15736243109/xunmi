package com.pc.mimi.ui.edituserinfo;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.adapter.JobSelectAdapter;
import com.pc.mimi.bean.JobBean;
import com.pc.mimi.config.CommonTags;
import com.pc.mimi.util.AppUtils;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;

public class JobSelectViewModel extends BaseViewModel {
    public JobSelectViewModel(@NonNull Application application) {
        super(application);
    }

    JobSelectAdapter mJobSelectOneAdapter;
    JobSelectAdapter mJobSelectTwoAdapter;
    JobSelectAdapter mJobSelectThreeAdapter;

    List<JobBean> mJobOneBeans;
    List<JobBean> mJobTwoBeans;
    List<JobBean> mJobThreeBeans;
    //被选中项的id
    public String mSelectJob = "";
    //选择的区域
    public ObservableField<String> jobText = new ObservableField<>("");

    //确定
    public BindingCommand confir = new BindingCommand(() -> {
        Messenger.getDefault().send(jobText.get(), CommonTags.SELECT_JOB);
        finish();
    });

    UIChangeObser uc = new UIChangeObser();


    class UIChangeObser {
        public ObservableBoolean firstSelect = new ObservableBoolean(false);
    }

    /**
     * 请求区域列表
     */
    public void getJobList(int index, String city_id) {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).getJobList(AppUtils.getUser().getUser_id(), city_id),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<List<JobBean>>() {
                    @Override
                    public void onResult(List<JobBean> o) {
                        if (index == 2) {
                            mJobTwoBeans.clear();
                            mJobTwoBeans.addAll(o);
                            mJobSelectTwoAdapter.notifyDataSetChanged();
                        } else {
                            mJobThreeBeans.clear();
                            mJobThreeBeans.addAll(o);
                            mJobSelectThreeAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }

    /**
     * 请求城市列表
     */
    public void getJobList() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).getJobList(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<List<JobBean>>() {
                    @Override
                    public void onResult(List<JobBean> o) {
                        mJobOneBeans.clear();
                        mJobOneBeans.addAll(o);
                        mJobSelectOneAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }
}

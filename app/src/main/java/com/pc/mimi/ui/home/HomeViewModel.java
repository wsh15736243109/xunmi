package com.pc.mimi.ui.home;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.pc.mimi.adapter.AreaSelectAdapter;
import com.pc.mimi.bean.AreaBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.config.CommonTags;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HomeViewModel extends BaseViewModel {
    public int mPageType = 0;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    UIChangeObser uc = new UIChangeObser();


    class UIChangeObser {
        public ObservableBoolean nearBy = new ObservableBoolean(false);
        public ObservableBoolean firstSelect = new ObservableBoolean(false);
        public ObservableBoolean refreshPage = new ObservableBoolean(false);
    }

    AreaSelectAdapter mCitySelectAdapter;
    AreaSelectAdapter mAreaSelectAdapter;

    List<AreaBean> mCityBeans;
    List<AreaBean> mAreaBeans;
    //男女切换
    public ObservableBoolean isGirl = new ObservableBoolean(false);
    public ObservableBoolean isBoy = new ObservableBoolean(false);
    private boolean tag = false;

    /**
     * 请求区域列表
     */
    public void loadAreaList(String city_id) {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).getAreaList(AppUtils.getUser().getUser_id(), city_id),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<List<AreaBean>>() {
                    @Override
                    public void onResult(List<AreaBean> o) {
                        mAreaBeans.clear();
                        mAreaBeans.addAll(o);
                        mAreaSelectAdapter.notifyDataSetChanged();
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
    public void getCityList() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).getCityList(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<List<AreaBean>>() {
                    @Override
                    public void onResult(List<AreaBean> o) {
                        mCityBeans.clear();
                        mCityBeans.addAll(o);
                        //默认选中第一项
                        if (mCityBeans.size() > 0)
                            mCityBeans.get(0).setSelected(true);
                        uc.firstSelect.set(!uc.firstSelect.get());
                        mCitySelectAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }

    //搜索
    public BindingCommand search = new BindingCommand(() -> {
        ToastUtils.showShort("开发中...");
    });
    //切换男女
    public BindingCommand sexType = new BindingCommand(() -> {
        isBoy.set(tag);
        isGirl.set(!tag);
        mPageType = tag ? 1 : 0;
        Messenger.getDefault().send(tag ? "1" : "0", CommonTags.SEX_SELECT);
        tag = !tag;
    });
    //中部的筛选条件
    public BindingCommand condition = new BindingCommand(() -> {
        uc.nearBy.set(!uc.nearBy.get());
    });
}

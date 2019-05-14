package com.pc.mimi.ui.edituserinfo;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.adapter.AreaSelectAdapter;
import com.pc.mimi.bean.AreaBean;
import com.pc.mimi.config.CommonTags;
import com.pc.mimi.event.AreaEvent;
import com.pc.mimi.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;

public class MettingAreaSelectViewModel extends BaseViewModel {
    public MettingAreaSelectViewModel(@NonNull Application application) {
        super(application);
    }

    AreaSelectAdapter mCitySelectAdapter;
    AreaSelectAdapter mAreaSelectAdapter;


    List<AreaBean> mCityBeans;
    List<AreaBean> mAreaBeans;
    //被选中项的id
    public String[] mSelectId;
    List<String> mLabelsDatas = new ArrayList<>();
    //标签数据
    public ObservableField<List<String>> labelsData = new ObservableField<>(mLabelsDatas);
    //确定
    public BindingCommand confir = new BindingCommand(() -> {
        Messenger.getDefault().send(new AreaEvent(mLabelsDatas), CommonTags.SELECT_AREA);
        finish();
    });
    UIChangeObser uc = new UIChangeObser();
    class UIChangeObser {
        public ObservableBoolean firstSelect = new ObservableBoolean(false);
        public ObservableBoolean labels = new ObservableBoolean(false);
    }
    //处理数据选中
    public void updateDataSelected() {
        mLabelsDatas.clear();
        for (AreaBean areaBean : mCityBeans) {
            if (areaBean.isSelected()) {
                mLabelsDatas.add(areaBean.getCityname());
            }
        }
        for (AreaBean areaBean : mAreaBeans) {
            if (areaBean.isSelected()) {
                mLabelsDatas.add(areaBean.getCityname());
            }
        }
        KLog.d("mLabelsDatas--------->"+mLabelsDatas.size());
//        labelsData.set(mLabelsDatas);
        uc.labels.set(!uc.labels.get());
    }




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
}

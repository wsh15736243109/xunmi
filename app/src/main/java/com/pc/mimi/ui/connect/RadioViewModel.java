package com.pc.mimi.ui.connect;

import android.app.Application;
import android.support.annotation.NonNull;

import com.pc.mimi.bean.RadioBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.adapter.PersonalConnListAdapter;
import com.pc.mimi.config.CommonTags;
import com.pc.mimi.util.AppUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class RadioViewModel extends BaseViewModel {
    public RadioViewModel(@NonNull Application application) {
        super(application);
    }

    PersonalConnListAdapter mPersonalConnList_AllAdapter;
    PersonalConnListAdapter mPersonalConnList_HOTAdapter;
    PersonalConnListAdapter mPersonalConnList_NORMALAdapter;
    PersonalConnListAdapter mPersonalConnList_NEARBYAdapter;
    List<RadioBean.DataBean> mRadio_AllBeans;
    List<RadioBean.DataBean> mRadio_HOTBeans;
    List<RadioBean.DataBean> mRadio_NORMALBeans;
    List<RadioBean.DataBean> mRadio_NEARBYBeans;

    public void requestList(String type) {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("user_id", AppUtils.getUser().getUser_id());
            put("order_type", type);
        }};
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestSeeVoice(map),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<RadioBean>() {
                    @Override
                    public void onResult(RadioBean o) {
                        switch (type) {
                            case CommonTags.RADIO_TAB_TYPE.RADIO_TAB_ALL:
                                mRadio_AllBeans.clear();
                                mRadio_AllBeans.addAll(o.getData());
                                mPersonalConnList_AllAdapter.notifyDataSetChanged();
                                break;
                            case CommonTags.RADIO_TAB_TYPE.RADIO_TAB_HOT:
                                mRadio_HOTBeans.clear();
                                mRadio_HOTBeans.addAll(o.getData());
                                mPersonalConnList_HOTAdapter.notifyDataSetChanged();
                                break;
                            case CommonTags.RADIO_TAB_TYPE.RADIO_TAB_NORMAL:
                                mRadio_NORMALBeans.clear();
                                mRadio_NORMALBeans.addAll(o.getData());
                                mPersonalConnList_NORMALAdapter.notifyDataSetChanged();
                                break;
                            case CommonTags.RADIO_TAB_TYPE.RADIO_TAB_NEARBY:
                                mRadio_NEARBYBeans.clear();
                                mRadio_NEARBYBeans.addAll(o.getData());
                                mPersonalConnList_NEARBYAdapter.notifyDataSetChanged();
                                break;
                        }
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }
}

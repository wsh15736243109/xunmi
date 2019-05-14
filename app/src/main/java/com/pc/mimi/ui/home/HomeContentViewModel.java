package com.pc.mimi.ui.home;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pc.mimi.bean.PersonBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.pc.mimi.adapter.PersonalInfoListAdapter;
import com.pc.mimi.config.CommonTags;
import com.pc.mimi.util.AppUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HomeContentViewModel extends BaseViewModel {
    public HomeContentViewModel(@NonNull Application application) {
        super(application);
    }

    public PersonalInfoListAdapter mPersonInfoListAdapter;

    public List<PersonBean> personBeanList;
    public String type = "";
    public String mCity = "";
    public String mSex = "";

    int pageNnum = 0;
    public ObservableBoolean isRefresh = new ObservableBoolean(false);
    //刷新
    public BindingCommand<SmartRefreshLayout> onRefresh = new BindingCommand<SmartRefreshLayout>(s -> {
        requestNearPersonListData(true);
        s.finishRefresh();
    });

    @Override
    public void onCreate() {
        Messenger.getDefault().register(this, CommonTags.SELECT_CITY, String.class, city -> {
            this.mCity = city;
            isRefresh.set(!isRefresh.get());
        });
        Messenger.getDefault().register(this, CommonTags.SEX_SELECT, String.class, sex -> {
            this.mSex = sex;
            isRefresh.set(!isRefresh.get());
        });
    }

    /**
     * 附近的人
     */
    public void requestNearPersonListData(boolean isRefresh) {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("user_id", AppUtils.getUser().getUser_id());
            put("longitude", AppUtils.getLongitude());
            put("latitude", AppUtils.getLatitude());
        }};
        if (isRefresh) {
            pageNnum = 0;
        } else {
            pageNnum++;
        }
        map.put("page", pageNnum);
        switch (type) {
            case CommonTags.HOME_PAGE_TYPE.NEARBY:

                break;
            case CommonTags.HOME_PAGE_TYPE.VIP:
                map.put("is_vip", 1);
                break;
            case CommonTags.HOME_PAGE_TYPE.NEW_REG:
                map.put("is_new", 1);
                break;
            case CommonTags.HOME_PAGE_TYPE.AUTH:
                map.put("is_real", 1);
                break;
        }
        if (!TextUtils.isEmpty(mCity)) {
            map.put("city", mCity);
        }
        if (!TextUtils.isEmpty(mSex)) {
            map.put("gender", mSex);
        }
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestPersonList(map),
                getLifecycleProvider(), disposable -> showDialog(), new ApiDisposableObserver<List<PersonBean>>() {
                    @Override
                    public void onResult(List<PersonBean> o) {
                        if (isRefresh) {
                            personBeanList.clear();
                        }
                        if (o.size() == 0) {
                            mPersonInfoListAdapter.notifyDataSetChanged();
                            mPersonInfoListAdapter.loadMoreEnd();
                            return;
                        } else {
                            personBeanList.addAll(o);
                            mPersonInfoListAdapter.notifyDataSetChanged();
                            mPersonInfoListAdapter.loadMoreComplete();
                        }
                        mPersonInfoListAdapter.setEnableLoadMore(true);
                        mPersonInfoListAdapter.disableLoadMoreIfNotFullPage();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }


    /**
     * 会员
     */
    public void requestVipPersonListData() {

        Map<String, Object> map = new HashMap<String, Object>() {{
            put("user_id", AppUtils.getUser().getUser_id());
            put("page", 0);
            put("is_vip", 1);
        }};

        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestPersonList(map),
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

    /**
     * 添加到喜欢
     *
     * @param position
     */
    public void requestLikeIt(int position) {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestLikeIt(AppUtils.getUser().getUser_id(), personBeanList.get(position).getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<Object>() {
                    @Override
                    public void onResult(Object o) {

                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                    @Override
                    public void onDataEmpty(String msg) {
                        personBeanList.get(position).setIsLike(1);
                        mPersonInfoListAdapter.notifyItemChanged(position);
                        ToastUtils.showShort("关注成功");
                    }
                });
    }

    /**
     * 取消喜欢
     *
     * @param position
     */
    public void requestDelLikeIt(int position) {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestDelLikeIt(AppUtils.getUser().getUser_id(), personBeanList.get(position).getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<Object>() {
                    @Override
                    public void onResult(Object o) {

                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                    @Override
                    public void onDataEmpty(String msg) {
                        personBeanList.get(position).setIsLike(0);
                        mPersonInfoListAdapter.notifyItemChanged(position);
                        ToastUtils.showShort("取消关注");
                    }
                });
    }
}

package com.pc.mimi.ui.selectsex;

import android.app.Application;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.ui.edituserinfo.EditUserInfoXActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class SelectSexViewModel extends BaseViewModel {
    public SelectSexViewModel(@NonNull Application application) {
        super(application);
    }

    public String user_id = "";
    private int mSex = -1;
    //下一步
    public BindingCommand confir = new BindingCommand(() -> {
        if (mSex == -1) {
            ToastUtils.showShort("请选择性别");
            return;
        }
        if (TextUtils.isEmpty(user_id)) {
            ToastUtils.showShort("数据异常,请重试");
            return;
        }

        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).setSex(user_id, mSex), getLifecycleProvider(),
                disposable -> showDialog(), new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {

                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                    @Override
                    public void onDataEmpty(String msg) {
                        ToastUtils.showShort("设置成功");
                        Bundle bundle = new Bundle();
                        bundle.putInt("sex", mSex);
                        bundle.putString("user_id", user_id);
                        startActivity(EditUserInfoXActivity.class, bundle);
                    }
                });
    });
    //选中男
    public ObservableInt isBoySelect = new ObservableInt(View.GONE);
    //选中女
    public ObservableInt isGirlSelect = new ObservableInt(View.GONE);
    //选中男点击
    public BindingCommand selectBoy = new BindingCommand(() -> {
        mSex = 1;
        isBoySelect.set(View.VISIBLE);
        isGirlSelect.set(View.GONE);
    });
    //选中女点击
    public BindingCommand selectGirl = new BindingCommand(() -> {
        mSex = 0;
        isBoySelect.set(View.GONE);
        isGirlSelect.set(View.VISIBLE);
    });
}

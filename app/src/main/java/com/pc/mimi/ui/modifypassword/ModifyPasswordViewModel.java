package com.pc.mimi.ui.modifypassword;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.util.AppUtils;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ModifyPasswordViewModel extends BaseViewModel {
    public ModifyPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    //原密码
    public ObservableField<String> old_pwd = new ObservableField<>("");
    //新密码
    public ObservableField<String> new_pwd = new ObservableField<>("");

    //保存修改密码
    public void saveUpdate() {
        if (TextUtils.isEmpty(old_pwd.get()) || TextUtils.isEmpty(new_pwd.get())) {
            ToastUtils.showShort("请填写完整");
            return;
        }
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).
                        modifyPassword(AppUtils.getUser().getUser_id(), old_pwd.get(), new_pwd.get()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {

                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                    @Override
                    public void onDataEmpty(String msg) {
                        ToastUtils.showShort("修改成功");
                    }
                });
    }
}

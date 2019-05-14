package com.pc.mimi.ui.bindphone;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.ui.commonmodel.SIMCodeViewModel;
import com.pc.mimi.util.AppUtils;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class BindPhoneViewModel extends SIMCodeViewModel {
    public BindPhoneViewModel(@NonNull Application application) {
        super(application);
    }

    //密码
    public ObservableField<String> password = new ObservableField<>("");
    //发送验证码
    public BindingCommand sendCode = new BindingCommand(() -> {
        super.requestSmsCode(TYPE_BIND_PHONE);
    });
    //确定
    public BindingCommand confir = new BindingCommand(() -> {
        if (TextUtils.isEmpty(userPhone.get()) || TextUtils.isEmpty(smscode.get()) || TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请填写完整");
            return;
        }
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).setPhone(
                userPhone.get(), AppUtils.getUser().getUser_id(), smscode.get(), password.get()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {

                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    });

}

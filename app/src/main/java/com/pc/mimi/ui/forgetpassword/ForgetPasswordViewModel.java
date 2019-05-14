package com.pc.mimi.ui.forgetpassword;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.ui.commonmodel.SIMCodeViewModel;
import com.pc.mimi.util.AppUtils;

import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ForgetPasswordViewModel extends SIMCodeViewModel {
    public ForgetPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    //获取验证码
    public BindingCommand sendCode = new BindingCommand(() -> super.requestSmsCode(TYPE_FORGET_PASSWORD));

    //密码
    public ObservableField<String> textPwd = new ObservableField<>("");

    //设置密码按钮
    public BindingCommand nextStep = new BindingCommand(() -> {
        if (TextUtils.isEmpty(userPhone.get())) {
            ToastUtils.showShort("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(smscode.get())) {
            ToastUtils.showShort("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(textPwd.get())) {
            ToastUtils.showShort("请输入新密码");
            return;
        }
        Map<String,Object>map = new HashMap<String,Object>(){{
            put("phone", userPhone.get());
            put("code", smscode.get());
            put("new_pwd", textPwd.get());
        }};
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).updateNewPwd(map),
                getLifecycleProvider(), disposable -> showDialog(), new ApiDisposableObserver() {
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
    });
}

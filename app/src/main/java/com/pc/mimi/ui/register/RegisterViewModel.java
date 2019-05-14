package com.pc.mimi.ui.register;

import android.app.Application;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.ui.commonmodel.SIMCodeViewModel;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.ui.selectsex.SelectSexActivity;

import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class RegisterViewModel extends SIMCodeViewModel {
    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }


    //获取验证码
    public BindingCommand sendCode = new BindingCommand(() -> super.requestSmsCode(TYPE_REGISTER));

    //密码
    public ObservableField<String> textPwd = new ObservableField<>("");
    //邀请码
    public ObservableField<String> inviteCode = new ObservableField<>("");

    /**
     * 下一步操作
     */
    public BindingCommand nextStep = new BindingCommand(() -> {
        if (TextUtils.isEmpty(userPhone.get())) {
            ToastUtils.showShort("请输入电话号码");
            return;
        }
        if (TextUtils.isEmpty(smscode.get())) {
            ToastUtils.showShort("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(textPwd.get())) {
            ToastUtils.showShort("请输入密码");
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("phone", userPhone.get());
            put("pwd", textPwd.get());
            put("code", smscode.get());
            put("gender", -1);
        }};
        if (!TextUtils.isEmpty(inviteCode.get())) {
            map.put("inviter", inviteCode.get());
        }
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestReg(map),
                getLifecycleProvider(), disposable -> showDialog(), new ApiDisposableObserver<String>() {
                    @Override
                    public void onResult(String o) {
                        ToastUtils.showShort("注册成功");
                        Bundle bundle = new Bundle();
                        bundle.putString("user_id", o);
                        startActivity(SelectSexActivity.class, bundle);
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                    @Override
                    public void onDataEmpty(String msg) {
                    }
                });
    });
}

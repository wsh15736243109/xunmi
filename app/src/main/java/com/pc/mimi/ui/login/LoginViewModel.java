package com.pc.mimi.ui.login;

import android.app.Application;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pc.mimi.bean.UserInfo;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.ui.commonmodel.SIMCodeViewModel;
import com.pc.mimi.ui.forgetpassword.ForgetPasswordActivity;
import com.pc.mimi.ui.main.MainActivity;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.ui.edituserinfo.EditUserInfoXActivity;
import com.pc.mimi.ui.register.RegisterActivity;
import com.pc.mimi.ui.selectsex.SelectSexActivity;

import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class LoginViewModel extends SIMCodeViewModel {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    //密码
    public ObservableField<String> passWord = new ObservableField<>("");
    //登录
    public BindingCommand login = new BindingCommand(() -> {
        requestLogin();
    });
    //qq登录
    public BindingCommand qqLogin = new BindingCommand(() -> {
        ToastUtils.showShort("qq登录");
    });
    //微信登录
    public BindingCommand wxLogin = new BindingCommand(() -> {
        ToastUtils.showShort("vx登录");
    });
    //忘记密码
    public BindingCommand goForget = new BindingCommand(() -> {
        startActivity(ForgetPasswordActivity.class);
    });

    /**
     * 登录
     */
    private void requestLogin() {
        if (TextUtils.isEmpty(userPhone.get())) {
            ToastUtils.showShort("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(passWord.get())) {
            ToastUtils.showShort("请输入密码");
            return;
        }

        Map<String, Object> map = new HashMap<String, Object>() {{
            put("phone", userPhone.get());
            put("pwd", passWord.get());
        }};

        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestLogin(map), getLifecycleProvider(),
                disposable -> showDialog(), new ApiDisposableObserver<UserInfo>() {
                    @Override
                    public void onResult(UserInfo o) {
                        ToastUtils.showShort("登录成功");
                        AppUtils.saveUser(o);
                        AppManager.getAppManager().finishActivity(RegisterActivity.class);
                        AppManager.getAppManager().finishActivity(ForgetPasswordActivity.class);
                        AppManager.getAppManager().finishActivity(SelectSexActivity.class);
                        if (o.getGender() == -1) {
                            Bundle bundle = new Bundle();
                            bundle.putString("user_id", o.getUser_id());
                            startActivity(SelectSexActivity.class, bundle);
                            return;
                        }
                        if (o.getIs_finish_info() == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("sex", o.getGender());
                            bundle.putString("user_id", o.getUser_id());
                            startActivity(EditUserInfoXActivity.class, bundle);
                            return;
                        }
                        startActivity(MainActivity.class);
                        finish();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }

    //去注册
    public BindingCommand goReg = new BindingCommand(() -> startActivity(RegisterActivity.class));
}

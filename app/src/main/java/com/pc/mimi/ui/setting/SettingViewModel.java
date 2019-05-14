package com.pc.mimi.ui.setting;

import android.app.Application;
import android.support.annotation.NonNull;

import com.pc.mimi.ui.bindphone.BindPhoneActivity;
import com.pc.mimi.ui.login.LoginActivity;
import com.pc.mimi.ui.modifypassword.ModifyPasswordActivity;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.webSocket.WsManager;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class SettingViewModel extends BaseViewModel {
    public SettingViewModel(@NonNull Application application) {
        super(application);
    }

    //修改密码
    public BindingCommand modifyPassword = new BindingCommand(() -> startActivity(ModifyPasswordActivity.class));
    //绑定手机
    public BindingCommand bindPhone = new BindingCommand(() -> startActivity(BindPhoneActivity.class));
    //退出
    public BindingCommand exit = new BindingCommand(() -> {
        AppUtils.clearUser();
        WsManager.getInstance().disconnect();
        startActivity(LoginActivity.class);
        AppManager.getAppManager().finishAllActivity();
    });
}

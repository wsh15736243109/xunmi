package com.pc.mimi.ui.message;

import android.app.Application;
import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MessageViewModel extends BaseViewModel {

    public MessageViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand setting = new BindingCommand(() -> {
        ToastUtils.showShort("没图标.qaq,setting");
    });
}

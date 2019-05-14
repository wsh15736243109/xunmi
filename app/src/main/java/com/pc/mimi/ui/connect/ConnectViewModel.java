package com.pc.mimi.ui.connect;

import android.app.Application;
import android.support.annotation.NonNull;

import com.pc.mimi.ui.list.IncomeRichListActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ConnectViewModel extends BaseViewModel {
    public ConnectViewModel(@NonNull Application application) {
        super(application);
    }

    //我要开麦
    public BindingCommand openMick = new BindingCommand(() -> {
        ToastUtils.showShort("开发中。。。");
    });
    //排行榜
    public BindingCommand list = new BindingCommand(() -> {
        startActivity(IncomeRichListActivity.class);
    });
    //切换男女
    public BindingCommand sexType = new BindingCommand(() -> {
        ToastUtils.showShort("没图标.qaq,切换男女");
    });
}

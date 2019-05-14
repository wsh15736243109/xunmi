package com.pc.mimi.ui.setting;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.pc.mimi.bean.SecretSettingBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.util.AppUtils;

import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class SecretSettingViewModel extends BaseViewModel {
    public SecretSettingViewModel(@NonNull Application application) {
        super(application);
    }

    private int hiding_all_info = 0;
    private int is_pay_picture = 0;
    private int is_need_check = 0;
    private int hiding_myself = 0;
    private int hiding_distance = 0;
    private int hiding_social_account = 0;
    //公开（推荐）
    public ObservableBoolean isTuijian = new ObservableBoolean(false);
    public BindingCommand tuijian = new BindingCommand(() -> {
        isTuijian.set(!isTuijian.get());
        if (isTuijian.get()) {
            hiding_all_info = 1;
        } else {
            hiding_all_info = 0;
        }
        setSecretSetting();
    });
    //相册付费查看
    public ObservableBoolean isphotoBookPay = new ObservableBoolean(false);

    public BindingCommand photoBookPay = new BindingCommand(() -> {
        isphotoBookPay.set(!isphotoBookPay.get());
        if (isphotoBookPay.get()) {
            is_pay_picture = 1;
        } else {
            is_pay_picture = 0;
        }
        setSecretSetting();
    });
    //查看前需通过我的验证
    public ObservableBoolean isseeBefore = new ObservableBoolean(false);

    public BindingCommand seeBefore = new BindingCommand(() -> {
        isseeBefore.set(!isseeBefore.get());
        if (isseeBefore.get()) {
            is_need_check = 1;
        } else {
            is_need_check = 0;
        }
        setSecretSetting();
    });
    //隐身
    public ObservableBoolean ishideself = new ObservableBoolean(false);

    public BindingCommand hideself = new BindingCommand(() -> {
        ishideself.set(!ishideself.get());
        if (ishideself.get()) {
            hiding_myself = 1;
        } else {
            hiding_myself = 0;
        }
        setSecretSetting();
    });
    //对他人隐藏我的距离
    public ObservableBoolean ishideDistanceCheck = new ObservableBoolean(false);

    public BindingCommand<Boolean> ishideDistance = new BindingCommand<Boolean>(aBoolean -> {
        hiding_distance = aBoolean ? 1 : 0;
        KLog.d("hiding_to_gender", hiding_distance + "");
        setSecretSetting();
    });
    //对他人隐藏我的社交账号
    public ObservableBoolean ishideAccountCheck = new ObservableBoolean(false);
    public BindingCommand<Boolean> ishideAccount = new BindingCommand<Boolean>(aBoolean -> {
        hiding_social_account = aBoolean ? 1 : 0;
        KLog.d("hiding_to_gender", hiding_social_account + "");
        setSecretSetting();
    });

    /**
     * 请求隐私设置详情
     */
    public void requestSecretSettingDetail() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestSecretSettingDetail(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<SecretSettingBean>() {
                    @Override
                    public void onResult(SecretSettingBean o) {
                        isTuijian.set(o.getHiding_all_info() == 0 ? false : true);
                        isphotoBookPay.set(o.getIs_pay_picture() == 0 ? false : true);
                        isseeBefore.set(o.getIs_need_check() == 0 ? false : true);
                        ishideself.set(o.getHiding_myself() == 0 ? false : true);
                        ishideDistanceCheck.set(o.getHiding_distance() == 0 ? false : true);
                        ishideAccountCheck.set(o.getHiding_social_account() == 0 ? false : true);
                        hiding_all_info = o.getHiding_all_info();
                        is_pay_picture = o.getIs_pay_picture();
                        is_need_check = o.getIs_need_check();
                        hiding_myself = o.getHiding_myself();
                        hiding_distance = o.getHiding_distance();
                        hiding_social_account = o.getHiding_social_account();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }

    /**
     * 设置隐私设置
     */
    public void setSecretSetting() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("user_id", AppUtils.getUser().getUser_id());
            put("hiding_all_info", hiding_all_info);
            put("is_pay_picture", is_pay_picture);
            put("is_need_check", is_need_check);
            put("hiding_myself", hiding_myself);
            put("hiding_distance", hiding_distance);
            put("hiding_social_account", hiding_social_account);
        }};

        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).setSecretSetting(map),
                getLifecycleProvider(), disposable -> {
                },
                new ApiDisposableObserver<Object>() {
                    @Override
                    public void onResult(Object o) {

                    }

                    @Override
                    public void dialogDismiss() {
                    }

                    @Override
                    public void onDataEmpty(String msg) {
                        ToastUtils.showShort("设置成功");
                    }
                });
    }
}

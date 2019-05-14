package com.pc.mimi.ui.commonmodel;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.util.CommonUtil;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.ToastUtils;

public abstract class SIMCodeViewModel extends BaseViewModel {
    public SIMCodeViewModel(@NonNull Application application) {
        super(application);
    }

    //验证码
    public ObservableField<String> smscode = new ObservableField<>("");
    //注册获取验证码按钮文字
    public ObservableField<String> sendCodeText = new ObservableField<>("发送验证码");
    //注册获取验证码按钮是否可点击
    public ObservableBoolean isGetCodeClick = new ObservableBoolean(true);
    //手机号
    public ObservableField<String> userPhone = new ObservableField<>("");
    public static final int TYPE_UPDATE = 3;
    public static final int TYPE_REGISTER = 1;
    public static final int TYPE_FORGET_PASSWORD = 2;
    public static final int TYPE_BIND_PHONE = 4;

    private boolean checkGetCode() {
        if (TextUtils.isEmpty(userPhone.get())) {
            ToastUtils.showShort("请填写手机号码");
            return true;
        }
        if (!CommonUtil.checkMobileNumber(userPhone.get())) {
            ToastUtils.showShort("请输入正确的手机号码");
            return true;
        }
        return false;
    }

    public UIChangeObser uc = new UIChangeObser();

    public class UIChangeObser {
        public ObservableBoolean registerCountDown = new ObservableBoolean(false);
        public ObservableBoolean forgetPasswordCountDown = new ObservableBoolean(false);
        public ObservableBoolean bindPhoneCountDown = new ObservableBoolean(false);
    }

    //获取验证码
    public void requestSmsCode(int type) {
        if (checkGetCode()) return;
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).getSMSCode(userPhone.get()),
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
                        ToastUtils.showShort("发送成功");
                        switch (type) {
                            case TYPE_REGISTER:
                                uc.registerCountDown.set(!uc.registerCountDown.get());
                                break;
                            case TYPE_FORGET_PASSWORD:
                                uc.forgetPasswordCountDown.set(!uc.forgetPasswordCountDown.get());
                                break;
                            case TYPE_BIND_PHONE:
                                uc.bindPhoneCountDown.set(!uc.bindPhoneCountDown.get());
                                break;
                        }
                    }
                });
    }

}

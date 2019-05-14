package com.pc.mimi.ui.mine;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.pc.mimi.config.CommonTags;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.ui.commonmodel.CommonRemindDialogModel;
import com.pc.mimi.util.AppUtils;

import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class PhotoPreviewViewModel extends CommonRemindDialogModel {
    public String url = "";
    public String urlID = "";
    public String see_later_break = "";
    public String price = "";

    public PhotoPreviewViewModel(@NonNull Application application) {
        super(application);
    }

    //是否是红包照片
    public ObservableInt isShowRed = new ObservableInt(View.GONE);
    public ObservableBoolean isSelected = new ObservableBoolean(false);
    //图片url
    public ObservableField<String> photoUrl = new ObservableField<>("");

    public void setData() {
        photoUrl.set(API.COMMON_PIC_URL + url);
        isSelected.set(see_later_break.equals("1"));
        if (Double.parseDouble(price) > 0d) {
            isShowRed.set(View.VISIBLE);
        } else {
            isShowRed.set(View.GONE);
        }
    }

    //阅后即焚
    public BindingCommand seeDelete = new BindingCommand(() -> {
        if (Double.parseDouble(price) > 0d){
            ToastUtils.showShort("该照片为红包照片,您不能将其设置为阅后即焚");
            return;
        }
        isSelected.set(!isSelected.get());
        if (isSelected.get()) {
            requestSeeDelete(1);
        } else {
            requestSeeDelete(0);
        }
    });
    //删除照片
    public BindingCommand deletePhoto = new BindingCommand(() -> {
        super.showDialog("取消", "确认", "确认删除这张照片吗?");
    });

    @Override
    protected void remindConfir() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("user_id", AppUtils.getUser().getUser_id());
            put("user_picture_id", urlID);
        }};
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).deletePhoto(map),
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
                        ToastUtils.showShort("删除成功");
                        Messenger.getDefault().sendNoMsg(CommonTags.REFRESH_MINE);
                        finish();
                    }
                });
    }

    //do阅后即焚--request-->
    private void requestSeeDelete(int tag) {
        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(urlID)) {
            Map<String, Object> map = new HashMap<String, Object>() {{
                put("user_id", AppUtils.getUser().getUser_id());
                put("user_picture_id", urlID);
                put("see_later_break", tag);
            }};

            AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestSeeDelete(map),
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
                            if (tag == 0) {
                                ToastUtils.showShort("取消成功");
                                Messenger.getDefault().sendNoMsg(CommonTags.REFRESH_MINE);
                            } else {
                                ToastUtils.showShort("设置成功");
                                Messenger.getDefault().sendNoMsg(CommonTags.REFRESH_MINE);
                            }
                        }
                    });
        } else {
            ToastUtils.showShort("数据异常,请稍后再试");
            return;
        }
    }
}

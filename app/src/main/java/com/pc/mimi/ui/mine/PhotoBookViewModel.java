package com.pc.mimi.ui.mine;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.pc.mimi.adapter.PhotoListSelectAdapter;
import com.pc.mimi.bean.PictureBean;
import com.pc.mimi.config.CommonTags;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class PhotoBookViewModel extends BaseViewModel {
    public PhotoBookViewModel(@NonNull Application application) {
        super(application);
    }

    PhotoListSelectAdapter mPhotoListSelectAdapter;
    List<PictureBean> mPictureBeans;
    public String selectPicId = "";
    public String see_later_break = "";
    public String price = "";
    //完成
    public BindingCommand confir = new BindingCommand(() -> {
        if (TextUtils.isEmpty(selectPicId)) {
            ToastUtils.showShort("请选择一张照片");
            return;
        }
        if (Double.parseDouble(price) > 0d) {
            ToastUtils.showShort("该照片已设置为红包照片,不需要再次设置");
            return;
        }
        if (see_later_break.equals("1")) {
            ToastUtils.showShort("该照片已设置为阅后即焚,您不能将其设置为红包照片了");
            return;
        }
        Messenger.getDefault().send(selectPicId, CommonTags.SET_RED_PIC);
        finish();
    });

    @Override
    public void onCreate() {
        Messenger.getDefault().register(this, CommonTags.REFRESH_PHOTO_BOOK, ArrayList.class, pictureBean -> {
            KLog.d("pictureBean"+new Gson().toJson(pictureBean));
            this.mPictureBeans.clear();
            this.mPictureBeans.addAll(pictureBean);
            mPhotoListSelectAdapter.notifyDataSetChanged();
        });
    }

    public void initData() {
        for (PictureBean p : mPictureBeans) {
            p.setSelected(Double.parseDouble(p.getPrice()) > 0d);
        }
        mPhotoListSelectAdapter.notifyDataSetChanged();
    }
}

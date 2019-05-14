package com.pc.mimi.ui.userdetail;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class SeePhotoViewModel extends BaseViewModel {
    public SeePhotoViewModel(@NonNull Application application) {
        super(application);
    }

    public String url = "";
    public String see_later_break = "";
    public String price = "";

    public ObservableField<String> photoUrl = new ObservableField<>("");
    public ObservableBoolean isGs = new ObservableBoolean(false);

    public void initData() {
        if (Double.parseDouble(price) > 0 || see_later_break.equals("1")) {
            isGs.set(true);
        } else {
            isGs.set(false);
        }
        photoUrl.set(url);
    }
}

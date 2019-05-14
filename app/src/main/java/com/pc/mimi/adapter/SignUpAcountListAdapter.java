package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pc.mimi.bean.SignUpAccountListBean;
import com.xlyuns.xunmi.R;
import com.pc.mimi.util.ImageLoaderUtils;

import java.util.List;

public class SignUpAcountListAdapter extends BaseQuickAdapter<SignUpAccountListBean, BaseViewHolder> {
    public SignUpAcountListAdapter(@Nullable List<SignUpAccountListBean> data) {
        super(R.layout.adapter_sign_up_acount, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignUpAccountListBean item) {
        ImageLoaderUtils.loadImage(helper.getView(R.id.img), item.getThis_avatar());
        helper.setText(R.id.user_name, item.getThis_nick_name());
        helper.setText(R.id.time, item.getAddtime());
        helper.setText(R.id.age, item.getThis_age() + "");
        helper.setBackgroundRes(R.id.img_sex, item.getThis_gender() == 0 ? R.drawable.ic_sex_g : R.drawable.ic_sex_b);
        ImageLoaderUtils.loadImage(helper.getView(R.id.img_pick), item.getImg_url());
        helper.addOnClickListener(R.id.connect_her);
    }
}

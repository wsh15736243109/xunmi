package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xlyuns.xunmi.R;
import com.pc.mimi.bean.MettingListBean;
import com.pc.mimi.util.ImageLoaderUtils;

import java.util.List;

public class ZanListAdapter extends BaseQuickAdapter<MettingListBean.GoodUserBean, BaseViewHolder> {
    public ZanListAdapter(@Nullable List<MettingListBean.GoodUserBean> data) {
        super(R.layout.adapter_zan_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MettingListBean.GoodUserBean item) {
        ImageLoaderUtils.loadImage(helper.getView(R.id.img), item.getThis_user_avatar());
    }
}

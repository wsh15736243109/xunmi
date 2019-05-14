package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pc.mimi.bean.PublishMettingBean;
import com.xlyuns.xunmi.R;

import java.util.List;

public class PublishMettingAdapter extends BaseQuickAdapter<PublishMettingBean, BaseViewHolder> {
    public PublishMettingAdapter(@Nullable List<PublishMettingBean> data) {
        super(R.layout.adapter_item_publish_metting, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PublishMettingBean item) {
        helper.setText(R.id.name, item.getName());
        helper.setImageResource(R.id.img, item.getResouseId());
        helper.addOnClickListener(R.id.item);
    }
}

package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xlyuns.xunmi.R;
import com.pc.mimi.bean.UIbean;

import java.util.List;

public class BlackListAdapter extends BaseQuickAdapter<UIbean, BaseViewHolder> {
    public BlackListAdapter(@Nullable List<UIbean> data) {
        super(R.layout.adapter_item_black_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UIbean item) {
        helper.addOnClickListener(R.id.item);
    }
}

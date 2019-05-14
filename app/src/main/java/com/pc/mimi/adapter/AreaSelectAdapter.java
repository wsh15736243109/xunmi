package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pc.mimi.bean.AreaBean;
import com.xlyuns.xunmi.R;

import java.util.List;

public class AreaSelectAdapter extends BaseQuickAdapter<AreaBean, BaseViewHolder> {
    public AreaSelectAdapter(@Nullable List<AreaBean> data) {
        super(R.layout.adapter_item_area_select, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreaBean item) {
        helper.setText(R.id.area, item.getCityname());
        helper.getView(R.id.area).setSelected(item.isSelected());
    }
}

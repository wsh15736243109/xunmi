package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xlyuns.xunmi.R;
import com.pc.mimi.bean.WishProgramBean;

import java.util.List;

public class MettingSelectAdapter extends BaseQuickAdapter<WishProgramBean, BaseViewHolder> {
    public MettingSelectAdapter(@Nullable List<WishProgramBean> data) {
        super(R.layout.adapter_item_area_select, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WishProgramBean item) {
        helper.setText(R.id.area, item.getName());
        helper.getView(R.id.area).setSelected(item.isSelected());
    }
}

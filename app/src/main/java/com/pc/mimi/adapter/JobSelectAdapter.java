package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pc.mimi.bean.JobBean;
import com.xlyuns.xunmi.R;

import java.util.List;

public class JobSelectAdapter extends BaseQuickAdapter<JobBean, BaseViewHolder> {
    public JobSelectAdapter(@Nullable List<JobBean> data) {
        super(R.layout.adapter_item_area_select, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JobBean item) {
        helper.setText(R.id.area, item.getIndustry_name());
        helper.getView(R.id.area).setSelected(item.isSelected());
    }
}

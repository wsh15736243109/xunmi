package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pc.mimi.bean.MyAssessBean;
import com.xlyuns.xunmi.R;

import java.util.List;

public class OtherAssessAdapter extends BaseQuickAdapter<MyAssessBean, BaseViewHolder> {
    public OtherAssessAdapter(@Nullable List<MyAssessBean> data) {
        super(R.layout.adapter_other_assess_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyAssessBean item) {
        helper.setText(R.id.text_count, item.isSelected() ? "1" : item.getCount() + "");
        helper.setText(R.id.text_tag, item.getAssessName());
        helper.getView(R.id.text_count).setSelected(item.isSelected());
        helper.getView(R.id.text_tag).setSelected(item.isSelected());
    }
}

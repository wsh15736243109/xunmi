package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xlyuns.xunmi.R;
import com.pc.mimi.bean.MyAssessBean;

import java.util.List;

public class MyAssessAdapter extends BaseQuickAdapter<MyAssessBean, BaseViewHolder> {
    public MyAssessAdapter(@Nullable List<MyAssessBean> data) {
        super(R.layout.adapter_my_assess_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyAssessBean item) {
        helper.setText(R.id.text_count, item.getCount()+"");
        helper.setText(R.id.text_tag, item.getAssessName());
        if (item.getCount() > 0) {
            helper.getView(R.id.text_count).setSelected(true);
            helper.getView(R.id.text_tag).setSelected(true);
        } else {
            helper.getView(R.id.text_count).setSelected(false);
            helper.getView(R.id.text_tag).setSelected(false);
        }
    }
}

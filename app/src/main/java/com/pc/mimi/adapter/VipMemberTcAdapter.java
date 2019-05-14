package com.pc.mimi.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xlyuns.xunmi.R;
import com.pc.mimi.bean.VipChargeBean;

import java.util.List;

public class VipMemberTcAdapter extends BaseQuickAdapter<VipChargeBean, BaseViewHolder> {

    public VipMemberTcAdapter(@Nullable List<VipChargeBean> data) {
        super(R.layout.adapter_layout_item_vip_member_tc, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipChargeBean item) {
        helper.addOnClickListener(R.id.item);
        helper.getView(R.id.item).setSelected(item.isSelected());
        helper.setText(R.id.type, item.getType().equals("0.5") ? "半个月" : item.getType() + "个月");
        helper.setText(R.id.before_value, item.getBefore_value() + "元/月");
        ((TextView) helper.getView(R.id.before_value)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.now_value, item.getNow_value() + "元/月");
    }
}

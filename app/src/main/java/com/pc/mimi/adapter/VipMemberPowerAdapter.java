package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xlyuns.xunmi.R;
import com.pc.mimi.bean.VipMemberBean;

import java.util.List;

public class VipMemberPowerAdapter extends BaseQuickAdapter<VipMemberBean, BaseViewHolder> {

    public VipMemberPowerAdapter(@Nullable List<VipMemberBean> data) {
        super(R.layout.adapter_layout_item_vip_member_power, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipMemberBean item) {
        helper.setText(R.id.tag, item.tag);
        helper.setText(R.id.content, item.content);
    }
}

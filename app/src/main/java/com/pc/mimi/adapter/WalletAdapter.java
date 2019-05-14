package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pc.mimi.bean.ChargeUnit;
import com.xlyuns.xunmi.R;

import java.util.List;

public class WalletAdapter extends BaseQuickAdapter<ChargeUnit, BaseViewHolder> {
    public WalletAdapter(@Nullable List<ChargeUnit> data) {
        super(R.layout.adapter_item_wallet_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChargeUnit item) {
        helper.getView(R.id.item).setSelected(item.isSelected());
        helper.setText(R.id.text, item.getRmb() + "元(" + item.getMoney() + "觅币)");
    }
}

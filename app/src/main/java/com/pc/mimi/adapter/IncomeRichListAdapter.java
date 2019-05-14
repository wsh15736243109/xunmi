package com.pc.mimi.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xlyuns.xunmi.R;
import com.pc.mimi.bean.IncomeRechListBean;
import com.pc.mimi.util.ImageLoaderUtils;

import java.util.List;

public class IncomeRichListAdapter extends BaseQuickAdapter<IncomeRechListBean, BaseViewHolder> {
    public IncomeRichListAdapter(@Nullable List<IncomeRechListBean> data) {
        super(R.layout.adapter_item_income_richlist, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeRechListBean item) {
        //头像
        ImageLoaderUtils.loadImage(helper.getView(R.id.img),item.getAvatar());
        helper.setText(R.id.user_name,item.getNick_name());
        helper.setText(R.id.des,item.getMoney());

    }
}

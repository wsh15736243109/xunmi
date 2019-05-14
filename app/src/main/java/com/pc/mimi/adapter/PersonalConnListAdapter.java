package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pc.mimi.bean.RadioBean;
import com.pc.mimi.util.ImageLoaderUtils;
import com.xlyuns.xunmi.R;

import java.util.List;

public class PersonalConnListAdapter extends BaseQuickAdapter<RadioBean.DataBean, BaseViewHolder> {

    public PersonalConnListAdapter(@Nullable List<RadioBean.DataBean> data) {
        super(R.layout.adapter_layout_item_conn_info_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RadioBean.DataBean item) {
        helper.setText(R.id.username, item.getNick_name());
        helper.setText(R.id.age, item.getAge() + "");
        helper.setText(R.id.dating_range, item.getDating_range());
        helper.setText(R.id.job, item.getJob());
        helper.setText(R.id.that_voice, item.getThat_voice());
        helper.setText(R.id.that_info, item.getThat_info());
        helper.setText(R.id.price, item.getPrice() + "摩轮币/分钟");
        helper.setImageResource(R.id.img_sex, item.getGender() == 0 ? R.drawable.ic_girl_simbal : R.drawable.ic_boy_simbal);
        ImageLoaderUtils.loadImage(helper.getView(R.id.img), item.getAvatar());
        helper.addOnClickListener(R.id.go_chat);
    }
}

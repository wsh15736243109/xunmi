package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pc.mimi.bean.MessageListBean;
import com.pc.mimi.util.ImageLoaderUtils;
import com.xlyuns.xunmi.R;

import java.util.List;

public class MessageListAdapter extends BaseQuickAdapter<MessageListBean, BaseViewHolder> {
    public MessageListAdapter(@Nullable List<MessageListBean> data) {
        super(R.layout.adapter_layout_item_message_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean item) {
        helper.setText(R.id.name, item.getOut_user_nick_name());
        ImageLoaderUtils.loadImage(helper.getView(R.id.img), item.getOut_user_avatar());
        helper.setText(R.id.time, item.getSend_time());
        helper.setImageResource(R.id.img_sex, item.getGender() == 0 ? R.drawable.ic_girl_simbal : R.drawable.ic_boy_simbal);
        helper.setText(R.id.age, item.getAge() + "");
        helper.setText(R.id.type, item.getAge() + "");
        if (item.getContent_type() == 0) {//wenzi
            helper.setText(R.id.content, item.getContent());
        } else {
            helper.setText(R.id.content, "图片");
        }
    }
}

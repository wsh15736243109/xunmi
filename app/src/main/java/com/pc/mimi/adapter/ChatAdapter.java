package com.pc.mimi.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xlyuns.xunmi.R;
import com.pc.mimi.util.ImageLoaderUtils;
import com.pc.mimi.websocketim.MessageBean;

import java.util.List;

public class ChatAdapter extends BaseMultiItemQuickAdapter<MessageBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ChatAdapter(List<MessageBean> data) {
        super(data);
        addItemType(MessageBean.SEND, R.layout.message_send_layout);
        addItemType(MessageBean.RECEIVE, R.layout.message_received_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        switch (helper.getItemViewType()) {
            case MessageBean.SEND:
                if (item.getContent_type() == 0) {
                    helper.setText(R.id.time, item.getSend_time());
                    helper.setGone(R.id.img_content, false);
                    helper.setGone(R.id.text_content, true);
                    helper.setText(R.id.text_content, item.getContent());
                    ImageLoaderUtils.loadImage(helper.getView(R.id.img), item.getOut_user_avatar());
                } else {
                    helper.setGone(R.id.text_content, false);
                    helper.setGone(R.id.img_content, true);
                    helper.setText(R.id.time, item.getAddtime());
                    ImageLoaderUtils.loadLocalImage(helper.getView(R.id.img_content), item.getContent());
                    ImageLoaderUtils.loadImage(helper.getView(R.id.img), item.getOut_user_avatar());
                }
                break;
            case MessageBean.RECEIVE:
                switch (item.getContent_type()) {
                    case 0://文字
                        helper.setText(R.id.time, item.getSend_time());
                        helper.setGone(R.id.img_content, false);
                        helper.setGone(R.id.text_content, true);
                        helper.setText(R.id.text_content, item.getContent());
                        ImageLoaderUtils.loadImage(helper.getView(R.id.img), item.getOut_user_avatar());
                        break;
                    case 1://图片
                        helper.setGone(R.id.text_content, false);
                        helper.setGone(R.id.img_content, true);
                        helper.setText(R.id.time, item.getAddtime());
                        ImageLoaderUtils.loadLocalImage(helper.getView(R.id.img_content), item.getContent());
                        ImageLoaderUtils.loadImage(helper.getView(R.id.img), item.getOut_user_avatar());
                        break;
                }
                break;
        }

    }
}

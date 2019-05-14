package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pc.mimi.bean.PictureBean;
import com.xlyuns.xunmi.R;
import com.pc.mimi.util.ImageLoaderUtils;

import java.util.List;

public class PhotoListAdapter extends BaseQuickAdapter<PictureBean, BaseViewHolder> {
    public PhotoListAdapter(@Nullable List<PictureBean> data) {
        super(R.layout.adapter_item_photo_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PictureBean item) {
        ImageLoaderUtils.loadImage(helper.getView(R.id.img), item.getPicture_url());
        helper.setGone(R.id.see_delete, item.getSee_later_break() == 1);
        helper.setGone(R.id.red_pic, Double.parseDouble(item.getPrice()) > 0);
    }
}

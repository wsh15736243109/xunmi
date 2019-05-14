package com.pc.mimi.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pc.mimi.bean.PersonBean;
import com.pc.mimi.util.ImageLoaderUtils;
import com.xlyuns.xunmi.R;

import java.util.List;

public class PersonalInfoListAdapter extends BaseQuickAdapter<PersonBean, BaseViewHolder> {
    public PersonalInfoListAdapter(@Nullable List<PersonBean> data) {
        super(R.layout.adapter_layout_item_person_info_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonBean item) {
        ImageLoaderUtils.loadImage(helper.getView(R.id.img), item.getAvatar());
        helper.setText(R.id.user_name, item.getNick_name());

        helper.setText(R.id.age, item.getAge() + "岁");
        helper.setText(R.id.distance, item.getDistance() + "km");
        helper.setText(R.id.last_login_time, item.getLast_login_time());
        helper.setText(R.id.dating_range, item.getDating_range());
        helper.setText(R.id.job, item.getJob());
        if (item.getGender() == 0) {
            helper.setImageResource(R.id.is_vip, R.drawable.ic_real);
            helper.setGone(R.id.is_vip, item.getIs_authenticated() == 1);
        } else {
            helper.setImageResource(R.id.is_vip, R.drawable.ic_vip);
            helper.setGone(R.id.is_vip, item.getIs_vip() == 1);
        }
        helper.setImageResource(R.id.isLike, item.getIsLike() == 0 ? R.drawable.ic_unlike : R.drawable.ic_like);
        helper.addOnClickListener(R.id.item);
        helper.addOnClickListener(R.id.isLike);
    }
}

package com.pc.mimi.util;

import com.pc.mimi.bean.VipMemberBean;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    private DataUtils() {
        super();
    }

    public static List<VipMemberBean> getVipMemberPowerData() {
        return new ArrayList<VipMemberBean>() {{
            add(new VipMemberBean("看的更多","每天不限次数查看用户"));
            add(new VipMemberBean("看的更省","每天10次免费机会查看付费相册或社交账号"));
            add(new VipMemberBean("看的更久","查看阅后即焚毁照片的时间从2秒提升到6秒"));
            add(new VipMemberBean("约得更爽","免费发布约会广播"));
        }};
    }
}

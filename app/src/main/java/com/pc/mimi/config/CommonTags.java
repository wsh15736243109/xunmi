package com.pc.mimi.config;

import com.pc.mimi.util.AppUtils;

public class CommonTags {
    //websocket 主机
    public static final String WS_URL = "ws://47.97.184.107:8085/motianlun/room?" + AppUtils.getUser().getUser_id();//正式服默认地址
    //aes加密密码
    public static final String AES_PWD = "ASDCIOPLKMCNJDKL";
    //选择的约会范围
    public static final String SELECT_AREA = "select_area";
    //刷新mine页面
    public static final String REFRESH_MINE = "refresh_mine";
    //刷新相册
    public static final String REFRESH_PHOTO_BOOK = "refresh_photo_book";
    //选择的职业
    public static final String SELECT_JOB = "select_job";
    //选择图片的"+"号
    public static final String PIC_ADD = "file:///android_asset/ic_add.png";
    //联系客服
    public static final String CUSTOM = "file:///android_asset/dist/index.html";
    //收到消息
    public static final String RECEIVE_MSG = "receive_msg";
    //经度
    public static final String LONGITUDE = "LONGITUDE";
    //纬度
    public static final String LATITUDE = "LONGITUDE";
    //首页选择城市messanger发送消息tag
    public static final String SELECT_CITY = "select_city";
    //首页选择性别切换messanger发送消息tag
    public static final String SEX_SELECT = "sex_select";
    //设置红包照片
    public static final String SET_RED_PIC = "set_red_pic";
    public static String WX_APP_ID = "";

    private CommonTags() {
        super();
    }

    //首页选项卡文字
    public static final String[] HOME_TAB_G = {"附近", "会员"};
    public static final String[] HOME_TAB_B = {"附近", "新注册", "认证"};
    //消息选项卡文字
    public static final String[] MESSAGE_TAB = {"聊天", "系统消息"};
    //电台选项卡文字
    public static final String[] RADIO_TAB = {"全部", "活跃", "人气", "附近"};

    //电台类型
    public static class RADIO_TAB_TYPE {
        public static final String RADIO_TAB_ALL = "0";
        public static final String RADIO_TAB_HOT = "1";
        public static final String RADIO_TAB_NORMAL = "2";
        public static final String RADIO_TAB_NEARBY = "3";
    }

    //富豪榜.收入榜
    public static class LIST_TYPE {
        public static final String[] LIST_TITLE = {"收入榜", "富豪榜"};
        public static final String IN_COME_LIST = "in_come_list";
        public static final String RICH_LIST = "rich_list";
    }

    //消息发送--接收
    public static class MESSAGE_TYPE {
        public static final int SEND = 1;
        public static final int RECEIVE = 0;
    }

    //首页page
    public static class HOME_PAGE_TYPE {
        public static final String NEARBY = "nearby";
        public static final String VIP = "vip";
        public static final String NEW_REG = "new_reg";
        public static final String AUTH = "auth";
    }
}

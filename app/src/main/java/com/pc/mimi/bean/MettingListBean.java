package com.pc.mimi.bean;

import java.util.List;

public class MettingListBean {

    /**
     * all_dating_id : afaggag45463gsgbhys3
     * user_id : 2
     * addtime : 2019-04-21 13:55:20
     * nick_name : 疾风前行
     * avatar : https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2995150965,2572603854&fm=27 &gp=0.jpg
     * is_vip : 0
     * gender : 1
     * age : 23
     * dating_type : 唱歌
     * start_time : 2019-04-15 20:34:15
     * end_time : 2019-04-15 20:34:15
     * dating_citys : 北京
     * dating_hope : 富婆
     * dating_remarks : 周末约哦，
     * imgs : this an img url
     * no_comment : 0
     * good_count : 0
     * sign_up_count : 0
     * no_sign : 0
     * chat_list : []
     */

    private String all_dating_id;
    private String user_id;
    private String addtime;
    private String nick_name;
    private String avatar;
    private int is_vip;
    private int gender;
    private int age;
    private String dating_type;
    private String start_time;
    private String end_time;
    private String dating_citys;
    private String dating_hope;
    private String dating_remarks;
    private int no_comment;
    private int good_count;
    private int sign_up_count;
    private int no_sign;
    private List<ChatList> chat_list;
    private int is_good_for_this;

    /**
     * good_user : [{"this_user_id":"a2ecafy70b599a916a91d14f98","this_user_avatar":"/img/z60cb6p7b9338b916a924776aa.jpeg"}]
     * is_authenticated : 0
     */

    private int is_authenticated;
    private List<GoodUserBean> good_user;
    private List<UserPictureBean> user_picture;

    public int getIs_good_for_this() {
        return is_good_for_this;
    }

    public void setIs_good_for_this(int is_good_for_this) {
        this.is_good_for_this = is_good_for_this;
    }

    public boolean hasComments() {
        return chat_list != null && chat_list.size() > 0;
    }

    public boolean hasPictures() {
        return user_picture != null && user_picture.size() > 0;
    }

    public boolean hasGoodUsers() {
        return good_user != null && good_user.size() > 0;
    }

    public int getIs_authenticated() {
        return is_authenticated;
    }

    public void setIs_authenticated(int is_authenticated) {
        this.is_authenticated = is_authenticated;
    }

    public List<GoodUserBean> getGood_user() {
        return good_user;
    }

    public void setGood_user(List<GoodUserBean> good_user) {
        this.good_user = good_user;
    }

    public List<UserPictureBean> getUser_picture() {
        return user_picture;
    }

    public void setUser_picture(List<UserPictureBean> user_picture) {
        this.user_picture = user_picture;
    }

    public static class ChatList {
        private String all_dating_argument_id;
        private int is_single;
        private String out_nick_name;
        private String in_nick_name;
        private String content;

        public String getAll_dating_argument_id() {
            return all_dating_argument_id;
        }

        public void setAll_dating_argument_id(String all_dating_argument_id) {
            this.all_dating_argument_id = all_dating_argument_id;
        }

        public int getIs_single() {
            return is_single;
        }

        public void setIs_single(int is_single) {
            this.is_single = is_single;
        }

        public String getOut_nick_name() {
            return out_nick_name;
        }

        public void setOut_nick_name(String out_nick_name) {
            this.out_nick_name = out_nick_name;
        }

        public String getIn_nick_name() {
            return in_nick_name;
        }

        public void setIn_nick_name(String in_nick_name) {
            this.in_nick_name = in_nick_name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public String getAll_dating_id() {
        return all_dating_id;
    }

    public void setAll_dating_id(String all_dating_id) {
        this.all_dating_id = all_dating_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDating_type() {
        return dating_type;
    }

    public void setDating_type(String dating_type) {
        this.dating_type = dating_type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDating_citys() {
        return dating_citys;
    }

    public void setDating_citys(String dating_citys) {
        this.dating_citys = dating_citys;
    }

    public String getDating_hope() {
        return dating_hope;
    }

    public void setDating_hope(String dating_hope) {
        this.dating_hope = dating_hope;
    }

    public String getDating_remarks() {
        return dating_remarks;
    }

    public void setDating_remarks(String dating_remarks) {
        this.dating_remarks = dating_remarks;
    }

    public int getNo_comment() {
        return no_comment;
    }

    public void setNo_comment(int no_comment) {
        this.no_comment = no_comment;
    }

    public int getGood_count() {
        return good_count;
    }

    public void setGood_count(int good_count) {
        this.good_count = good_count;
    }

    public int getSign_up_count() {
        return sign_up_count;
    }

    public void setSign_up_count(int sign_up_count) {
        this.sign_up_count = sign_up_count;
    }

    public int getNo_sign() {
        return no_sign;
    }

    public void setNo_sign(int no_sign) {
        this.no_sign = no_sign;
    }

    public List<ChatList> getChat_list() {
        return chat_list;
    }

    public void setChat_list(List<ChatList> chat_list) {
        this.chat_list = chat_list;
    }

    public static class GoodUserBean {
        public GoodUserBean() {
            super();
        }

        public GoodUserBean(String this_user_id, String this_user_avatar) {
            this.this_user_id = this_user_id;
            this.this_user_avatar = this_user_avatar;
        }

        /**
         * this_user_id : a2ecafy70b599a916a91d14f98
         * this_user_avatar : /img/z60cb6p7b9338b916a924776aa.jpeg
         */


        private String this_user_id;
        private String this_user_avatar;

        public String getThis_user_id() {
            return this_user_id;
        }

        public void setThis_user_id(String this_user_id) {
            this.this_user_id = this_user_id;
        }

        public String getThis_user_avatar() {
            return this_user_avatar;
        }

        public void setThis_user_avatar(String this_user_avatar) {
            this.this_user_avatar = this_user_avatar;
        }
    }

    public static class UserPictureBean {
        /**
         * all_dating_img_id : d65cfew527b05fa16a96fcbadb
         * img_url : /img/scbcfed5279c3c016a96fcbac6.jpg
         */

        private String all_dating_img_id;
        private String img_url;

        public String getAll_dating_img_id() {
            return all_dating_img_id;
        }

        public void setAll_dating_img_id(String all_dating_img_id) {
            this.all_dating_img_id = all_dating_img_id;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }
}

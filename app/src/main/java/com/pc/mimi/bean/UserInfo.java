package com.pc.mimi.bean;

import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable {

    /**
     * user_id : lec989a5cc7e7f016a5cfd919c
     * gender : -1
     */

    private String user_id;
    private int gender;
    private int is_finish_info;
    /**
     * age : 18
     * dating_show : [旅行]
     * dating_expectations : 有趣,
     * qq : 1232133123213
     * wechat : 3213213
     * hiding_social_account : 0
     * height : 175
     * weight : 55
     * bust : 70C
     * my_info : 大萨达撒多撒打手
     */

    private int age;
    private String dating_show;
    private String dating_expectations;
    private String qq;
    private String wechat;
    private String hiding_social_account;
    private int height;
    private int weight;
    private String bust;
    private String my_info;

    public int getIs_finish_info() {
        return is_finish_info;
    }

    public void setIs_finish_info(int is_finish_info) {
        this.is_finish_info = is_finish_info;
    }

    /**
     * nick_name : null
     * avatar : /img/ge39f5mb72cdbbd16a64176ea5.jpg
     * is_vip : 0
     * job : null
     * dating_range : null
     * is_authenticated : 0
     * wallet : 0
     * picture : []
     * history_count : 0
     * see_and_break : 0
     */

    private String nick_name;
    private String avatar;
    private int is_vip;
    private String job;
    private String dating_range;
    private int is_authenticated;
    private String wallet;
    private int history_count;
    private int see_and_break;
    private List<PictureBean> picture;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDating_show() {
        return dating_show;
    }

    public void setDating_show(String dating_show) {
        this.dating_show = dating_show;
    }

    public String getDating_expectations() {
        return dating_expectations;
    }

    public void setDating_expectations(String dating_expectations) {
        this.dating_expectations = dating_expectations;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getHiding_social_account() {
        return hiding_social_account;
    }

    public void setHiding_social_account(String hiding_social_account) {
        this.hiding_social_account = hiding_social_account;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public String getMy_info() {
        return my_info;
    }

    public void setMy_info(String my_info) {
        this.my_info = my_info;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDating_range() {
        return dating_range;
    }

    public void setDating_range(String dating_range) {
        this.dating_range = dating_range;
    }

    public int getIs_authenticated() {
        return is_authenticated;
    }

    public void setIs_authenticated(int is_authenticated) {
        this.is_authenticated = is_authenticated;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public int getHistory_count() {
        return history_count;
    }

    public void setHistory_count(int history_count) {
        this.history_count = history_count;
    }

    public int getSee_and_break() {
        return see_and_break;
    }

    public void setSee_and_break(int see_and_break) {
        this.see_and_break = see_and_break;
    }

    public List<PictureBean> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureBean> picture) {
        this.picture = picture;
    }
}

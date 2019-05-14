package com.pc.mimi.bean;

import java.util.List;

public class OtherPersonBean extends PersonBean {

    private String other_user_id;
    private String my_info;
    private String dating_expectations;
    private  String dating_show;
    private  String bust;
    private String weight;
    private  String height;
    /**
     * is_dating : 0
     * age : 54
     * distance : null
     * longitude : 30.301969
     * latitude : 120.108616
     * picture : [{"user_picture_id":"bddcf0k4ce00ac516a96117b46","picture_url":"/img/oi8cf0y4cddii0016a96117b14.jpeg","see_later_break":0,"price":0}]
     * height : 202
     * weight : 203
     * qq : 123456789
     * wechat :
     * hiding_social_account : 0
     * is_need_check : 0
     * is_pay_picture : 1
     * is_like : 0
     * is_in_black_mumber : 0
     */

    private int is_dating;
    private double longitude;
    private double latitude;
    private String qq;
    private String wechat;
    private int hiding_social_account;
    private int is_need_check;
    private int is_pay_picture;
    private int is_like;
    private int is_in_black_mumber;
    private List<PictureBean> picture;

    public String getMy_info() {
        return my_info;
    }

    public void setMy_info(String my_info) {
        this.my_info = my_info;
    }

    public String getDating_expectations() {
        return dating_expectations;
    }

    public void setDating_expectations(String dating_expectations) {
        this.dating_expectations = dating_expectations;
    }

    public String getDating_show() {
        return dating_show;
    }

    public void setDating_show(String dating_show) {
        this.dating_show = dating_show;
    }

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getOther_user_id() {
        return other_user_id;
    }

    public void setOther_user_id(String other_user_id) {
        this.other_user_id = other_user_id;
    }

    public int getIs_dating() {
        return is_dating;
    }

    public void setIs_dating(int is_dating) {
        this.is_dating = is_dating;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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

    public int getHiding_social_account() {
        return hiding_social_account;
    }

    public void setHiding_social_account(int hiding_social_account) {
        this.hiding_social_account = hiding_social_account;
    }

    public int getIs_need_check() {
        return is_need_check;
    }

    public void setIs_need_check(int is_need_check) {
        this.is_need_check = is_need_check;
    }

    public int getIs_pay_picture() {
        return is_pay_picture;
    }

    public void setIs_pay_picture(int is_pay_picture) {
        this.is_pay_picture = is_pay_picture;
    }

    public int getIs_like() {
        return is_like;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }

    public int getIs_in_black_mumber() {
        return is_in_black_mumber;
    }

    public void setIs_in_black_mumber(int is_in_black_mumber) {
        this.is_in_black_mumber = is_in_black_mumber;
    }

    public List<PictureBean> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureBean> picture) {
        this.picture = picture;
    }


}

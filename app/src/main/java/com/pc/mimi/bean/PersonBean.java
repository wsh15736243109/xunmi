package com.pc.mimi.bean;

public class PersonBean {
    private String user_id;
    private String nick_name;
    private String avatar;
    private String age;
    private int is_vip;
    private String distance;
    private String last_login_time;
    private int isLike;
    private String dating_range;
    private String job;
    /**
     * gender : 1
     * age : 19
     * is_authenticated : 0
     * distance : 9263.91
     */

    private int gender;
    private int is_authenticated;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public String getDating_range() {
        return dating_range;
    }

    public void setDating_range(String dating_range) {
        this.dating_range = dating_range;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getIs_authenticated() {
        return is_authenticated;
    }

    public void setIs_authenticated(int is_authenticated) {
        this.is_authenticated = is_authenticated;
    }
}

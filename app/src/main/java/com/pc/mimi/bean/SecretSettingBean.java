package com.pc.mimi.bean;

public class SecretSettingBean {

    /**
     * hiding_all_info : 0
     * is_pay_picture : 0
     * is_need_check : 0
     * hiding_myself : 0
     * hiding_distance : 0
     * hiding_social_account : 0
     */

    private int hiding_all_info;
    private int is_pay_picture;
    private int is_need_check;
    private int hiding_myself;
    private int hiding_distance;
    private int hiding_social_account;

    public int getHiding_all_info() {
        return hiding_all_info;
    }

    public void setHiding_all_info(int hiding_all_info) {
        this.hiding_all_info = hiding_all_info;
    }

    public int getIs_pay_picture() {
        return is_pay_picture;
    }

    public void setIs_pay_picture(int is_pay_picture) {
        this.is_pay_picture = is_pay_picture;
    }

    public int getIs_need_check() {
        return is_need_check;
    }

    public void setIs_need_check(int is_need_check) {
        this.is_need_check = is_need_check;
    }

    public int getHiding_myself() {
        return hiding_myself;
    }

    public void setHiding_myself(int hiding_myself) {
        this.hiding_myself = hiding_myself;
    }

    public int getHiding_distance() {
        return hiding_distance;
    }

    public void setHiding_distance(int hiding_distance) {
        this.hiding_distance = hiding_distance;
    }

    public int getHiding_social_account() {
        return hiding_social_account;
    }

    public void setHiding_social_account(int hiding_social_account) {
        this.hiding_social_account = hiding_social_account;
    }
}

package com.pc.mimi.bean;

public class MessageListBean {

    /**
     * out_user_id : sb7ce7c92dc26ee16a957f12b7
     * out_user_nick_name : qwert
     * out_user_avatar : /img/i3ed3dl8a3i2a5b16a9b21587b.jpeg
     * age : 18
     * gender : 1
     * content_type : 0
     * content : hello
     * count_no_read : 9
     * send_time : 2019-05-11 18:42:41
     */

    private String out_user_id;
    private String out_user_nick_name;
    private String out_user_avatar;
    private int age;
    private int gender;
    private int content_type;
    private String content;
    private int count_no_read;
    private String send_time;

    public String getOut_user_id() {
        return out_user_id;
    }

    public void setOut_user_id(String out_user_id) {
        this.out_user_id = out_user_id;
    }

    public String getOut_user_nick_name() {
        return out_user_nick_name;
    }

    public void setOut_user_nick_name(String out_user_nick_name) {
        this.out_user_nick_name = out_user_nick_name;
    }

    public String getOut_user_avatar() {
        return out_user_avatar;
    }

    public void setOut_user_avatar(String out_user_avatar) {
        this.out_user_avatar = out_user_avatar;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getContent_type() {
        return content_type;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount_no_read() {
        return count_no_read;
    }

    public void setCount_no_read(int count_no_read) {
        this.count_no_read = count_no_read;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }
}

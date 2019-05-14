package com.pc.mimi.websocketim;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.pc.mimi.config.CommonTags;

public class MessageBean implements MultiItemEntity {
    public static final int SEND = CommonTags.MESSAGE_TYPE.SEND;
    public static final int RECEIVE = CommonTags.MESSAGE_TYPE.RECEIVE ;

    private String in_user_avatar;
    private String in_user_nick_name;
    private String in_user_id;

    @Override
    public String toString() {
        return "MessageBean{" +
                "message_type=" + message_type +
                ", is_voice=" + is_voice +
                ", out_user_id='" + out_user_id + '\'' +
                ", out_user_nick_name='" + out_user_nick_name + '\'' +
                ", out_user_avatar='" + out_user_avatar + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", content_type=" + content_type +
                ", content='" + content + '\'' +
                ", send_time='" + send_time + '\'' +
                ", system_type=" + system_type +
                ", addtime='" + addtime + '\'' +
                '}';
    }

    /**
     * message_type : 0
     * is_voice : 1
     * out_user_id : c5ca7ioaci47e0516a6c371471
     * out_user_nick_name : 小猫咪
     * out_user_avatar : /img/cd8a49m558d5fi216a699252ue.jpg
     * age : 22
     * gender : 0
     * content_type : 0
     * content : 在吗？
     * send_time : 2019-05-01 18:48:07
     */
        private int isMe;
    private int message_type;
    private int is_voice;
    private String out_user_id;
    private String out_user_nick_name;
    private String out_user_avatar;
    private int age;
    private int gender;
    private int content_type;
    private String content;
    private String send_time;

    public int getIsMe() {
        return isMe;
    }

    public void setIsMe(int isMe) {
        this.isMe = isMe;
    }

    /**
     * system_type : 1
     * addtime : 2019-04-30 11:39:55
     */

    private int system_type;
    private String addtime;

    public int getMessage_type() {
        return message_type;
    }

    public void setMessage_type(int message_type) {
        this.message_type = message_type;
    }

    public int getIs_voice() {
        return is_voice;
    }

    public void setIs_voice(int is_voice) {
        this.is_voice = is_voice;
    }

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

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public int getSystem_type() {
        return system_type;
    }

    public void setSystem_type(int system_type) {
        this.system_type = system_type;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    @Override
    public int getItemType() {
        return isMe;
    }

    public String getIn_user_avatar() {
        return in_user_avatar;
    }

    public void setIn_user_avatar(String in_user_avatar) {
        this.in_user_avatar = in_user_avatar;
    }

    public String getIn_user_nick_name() {
        return in_user_nick_name;
    }

    public void setIn_user_nick_name(String in_user_nick_name) {
        this.in_user_nick_name = in_user_nick_name;
    }

    public String getIn_user_id() {
        return in_user_id;
    }

    public void setIn_user_id(String in_user_id) {
        this.in_user_id = in_user_id;
    }
}

package com.pc.mimi.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.pc.mimi.config.CommonTags;

/**
 * Created by Administrator on 2018/5/10.
 */

public class MsgBean implements MultiItemEntity {
    public static final int SEND = CommonTags.MESSAGE_TYPE.SEND;
    public static final int RECEIVE = CommonTags.MESSAGE_TYPE.RECEIVE ;
    private String from_account;
    private String to_account;
    private String body;
    private String status;
    private int type;
    private String time;
    private String session_account;

    public MsgBean(int type) {
        this.type = type;
    }

    public MsgBean(String from_account, String to_account, String body, String status, int type, String time, String session_account) {
        this.from_account = from_account;
        this.to_account = to_account;
        this.body = body;
        this.status = status;
        this.type = type;
        this.time = time;
        this.session_account = session_account;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public String getFrom_account() {
        return from_account;
    }

    public void setFrom_account(String from_account) {
        this.from_account = from_account;
    }

    public String getTo_account() {
        return to_account;
    }

    public void setTo_account(String to_account) {
        this.to_account = to_account;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSession_account() {
        return session_account;
    }

    public void setSession_account(String session_account) {
        this.session_account = session_account;
    }
}

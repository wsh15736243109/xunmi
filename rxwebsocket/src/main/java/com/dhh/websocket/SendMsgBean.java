package com.dhh.websocket;

public class SendMsgBean {
    public SendMsgBean(String in_user_id, int content_type, String content) {
        this.in_user_id = in_user_id;
        this.content_type = content_type;
        this.content = content;
    }

    @Override
    public String toString() {
        return "SendMsgBean{" +
                "in_user_id='" + in_user_id + '\'' +
                ", content_type=" + content_type +
                ", content='" + content + '\'' +
                '}';
    }

    /**
     * in_user_id : 用户id
     * content_type : 0
     * content : 你好吗?
     */

    private String in_user_id;
    private int content_type;
    private String content;

    public String getIn_user_id() {
        return in_user_id;
    }

    public void setIn_user_id(String in_user_id) {
        this.in_user_id = in_user_id;
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
}

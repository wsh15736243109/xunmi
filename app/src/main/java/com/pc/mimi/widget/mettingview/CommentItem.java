package com.pc.mimi.widget.mettingview;


public class CommentItem {
    private String id;
    private int is_single;
    private String userName;
    private String toReplyUserName;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIs_single() {
        return is_single;
    }

    public void setIs_single(int is_single) {
        this.is_single = is_single;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToReplyUserName() {
        return toReplyUserName;
    }

    public void setToReplyUserName(String toReplyUserName) {
        this.toReplyUserName = toReplyUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

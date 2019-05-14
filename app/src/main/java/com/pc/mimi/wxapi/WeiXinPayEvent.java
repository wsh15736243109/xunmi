package com.pc.mimi.wxapi;

public class WeiXinPayEvent {
    private boolean isSuccess;

    public WeiXinPayEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}

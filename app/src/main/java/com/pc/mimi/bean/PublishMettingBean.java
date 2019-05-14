package com.pc.mimi.bean;

public class PublishMettingBean {
    private String name;
    private int resouseId;

    public PublishMettingBean(String name, int resouseId) {
        this.name = name;
        this.resouseId = resouseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResouseId() {
        return resouseId;
    }

    public void setResouseId(int resouseId) {
        this.resouseId = resouseId;
    }
}

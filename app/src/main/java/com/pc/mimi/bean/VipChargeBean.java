package com.pc.mimi.bean;

public class VipChargeBean {
    private String vip_price_id;
    private String type;
    private String before_value;
    private String now_value;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getVip_price_id() {
        return vip_price_id;
    }

    public void setVip_price_id(String vip_price_id) {
        this.vip_price_id = vip_price_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBefore_value() {
        return before_value;
    }

    public void setBefore_value(String before_value) {
        this.before_value = before_value;
    }

    public String getNow_value() {
        return now_value;
    }

    public void setNow_value(String now_value) {
        this.now_value = now_value;
    }
}

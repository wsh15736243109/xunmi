package com.pc.mimi.bean;

public class ChargeUnit {
    private String pay_type_id;
    private String rmb;
    private String money;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPay_type_id() {
        return pay_type_id;
    }

    public void setPay_type_id(String pay_type_id) {
        this.pay_type_id = pay_type_id;
    }

    public String getRmb() {
        return rmb;
    }

    public void setRmb(String rmb) {
        this.rmb = rmb;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}

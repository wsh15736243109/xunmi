package com.pc.mimi.bean;

public class WishProgramBean {

    public WishProgramBean(String name) {
        this.name = name;
    }

    private String name;
    private boolean isSelected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

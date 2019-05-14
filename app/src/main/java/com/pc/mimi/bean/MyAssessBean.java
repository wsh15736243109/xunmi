package com.pc.mimi.bean;

public class MyAssessBean {
    private boolean isSelected;
   private String assessName;
    private int count;
    private int id ;

    public int getId() {
        return id;
    }

    public MyAssessBean(String assessName, int count, int id) {
        this.assessName = assessName;
        this.count = count;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public MyAssessBean(String assessName, int count) {
        this.assessName = assessName;
        this.count = count;
    }

    public String getAssessName() {
        return assessName;
    }

    public void setAssessName(String assessName) {
        this.assessName = assessName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

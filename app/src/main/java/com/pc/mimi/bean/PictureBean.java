package com.pc.mimi.bean;

import java.io.Serializable;

public class PictureBean implements Serializable {
    /**
     * user_picture_id : bddcf0k4ce00ac516a96117b46
     * picture_url : /img/oi8cf0y4cddii0016a96117b14.jpeg
     * see_later_break : 0
     * price : 0
     */

    private boolean isSelected;
    private String user_picture_id;
    private String picture_url;
    private int see_later_break;
    private String price;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getUser_picture_id() {
        return user_picture_id;
    }

    public void setUser_picture_id(String user_picture_id) {
        this.user_picture_id = user_picture_id;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public int getSee_later_break() {
        return see_later_break;
    }

    public void setSee_later_break(int see_later_break) {
        this.see_later_break = see_later_break;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

package com.pc.mimi.bean;

import java.util.List;

public class RadioBean {

    /**
     * is_open : 1
     * data : [{"user_id":"2","gender":0,"age":18,"nick_name":"2号大美咖","avatar":"/img/f4cd4akb0e3e3ec16a9bfdfc4d.jpeg","job":"通讯","dating_range":"深圳","that_voice":"/img/73948.mp3","that_info":"约会啦","price":2}]
     */

    private int is_open;
    private List<DataBean> data;

    public int getIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 2
         * gender : 0
         * age : 18
         * nick_name : 2号大美咖
         * avatar : /img/f4cd4akb0e3e3ec16a9bfdfc4d.jpeg
         * job : 通讯
         * dating_range : 深圳
         * that_voice : /img/73948.mp3
         * that_info : 约会啦
         * price : 2
         */

        private String user_id;
        private int gender;
        private int age;
        private String nick_name;
        private String avatar;
        private String job;
        private String dating_range;
        private String that_voice;
        private String that_info;
        private int price;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getDating_range() {
            return dating_range;
        }

        public void setDating_range(String dating_range) {
            this.dating_range = dating_range;
        }

        public String getThat_voice() {
            return that_voice;
        }

        public void setThat_voice(String that_voice) {
            this.that_voice = that_voice;
        }

        public String getThat_info() {
            return that_info;
        }

        public void setThat_info(String that_info) {
            this.that_info = that_info;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}

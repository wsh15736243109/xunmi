package com.pc.mimi.bean;

import java.util.List;

public class SignDataBean {

    /**
     * today_is_sign : 0
     * num_day : 1
     * signList : [{"sign_time":"2019-05-07 20:04:55","how_much_earn":"5.00"}]
     */

    private int today_is_sign;
    private int num_day;
    private List<SignListBean> signList;

    public int getToday_is_sign() {
        return today_is_sign;
    }

    public void setToday_is_sign(int today_is_sign) {
        this.today_is_sign = today_is_sign;
    }

    public int getNum_day() {
        return num_day;
    }

    public void setNum_day(int num_day) {
        this.num_day = num_day;
    }

    public List<SignListBean> getSignList() {
        return signList;
    }

    public void setSignList(List<SignListBean> signList) {
        this.signList = signList;
    }

    public static class SignListBean {
        /**
         * sign_time : 2019-05-07 20:04:55
         * how_much_earn : 5.00
         */

        private String sign_time;
        private String how_much_earn;

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public String getHow_much_earn() {
            return how_much_earn;
        }

        public void setHow_much_earn(String how_much_earn) {
            this.how_much_earn = how_much_earn;
        }
    }
}

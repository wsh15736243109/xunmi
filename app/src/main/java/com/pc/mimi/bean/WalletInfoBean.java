package com.pc.mimi.bean;

import java.util.List;

public class WalletInfoBean {

    /**
     * wallet : 20
     * is_have_ali : 0
     * list : [{"pay_type_id":"fadggafadfa1g6a1df","rmb":0.01,"money":1000},{"pay_type_id":"fFFFdfagah2t9929191g9gsh","rmb":39,"money":390},{"pay_type_id":"faf91h9s4g981g919sh","rmb":59,"money":590},{"pay_type_id":"g1a91g9a8191b9a19ah","rmb":89,"money":1000},{"pay_type_id":"ga91bha9er89g","rmb":159,"money":2000}]
     * acount_mumber : 绑定支付宝账号
     */

    private String wallet;
    private int is_have_ali;
    private String acount_mumber;
    private List<ChargeUnit> list;

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public int getIs_have_ali() {
        return is_have_ali;
    }

    public void setIs_have_ali(int is_have_ali) {
        this.is_have_ali = is_have_ali;
    }

    public String getAcount_mumber() {
        return acount_mumber;
    }

    public void setAcount_mumber(String acount_mumber) {
        this.acount_mumber = acount_mumber;
    }

    public List<ChargeUnit> getList() {
        return list;
    }

    public void setList(List<ChargeUnit> list) {
        this.list = list;
    }

}

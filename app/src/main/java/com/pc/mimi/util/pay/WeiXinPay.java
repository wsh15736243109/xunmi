package com.pc.mimi.util.pay;

import android.app.Activity;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.pc.mimi.config.CommonTags;


public class WeiXinPay implements PayUtils {
    private OrderStrBean mOrderStrBean;
    private Activity mActivity;


    public WeiXinPay(Activity activity, OrderStrBean orderStrBean) {
        mOrderStrBean = orderStrBean;
        mActivity = activity;
    }

    @Override
    public void toPay() {
        CommonTags.WX_APP_ID = mOrderStrBean.getAppid();
        final IWXAPI mWxApi = WXAPIFactory.createWXAPI(mActivity, CommonTags.WX_APP_ID, true);
        // 将该app注册到微信
        mWxApi.registerApp(CommonTags.WX_APP_ID);
        if (mWxApi != null) {
            PayReq req = new PayReq();
            req.appId = CommonTags.WX_APP_ID;// 微信开放平台审核通过的应用APPID
            req.partnerId = mOrderStrBean.getPartnerid();// 微信支付分配的商户号
            req.prepayId = mOrderStrBean.getPrepayid();// 预支付订单号，app服务器调用“统一下单”接口获取
            req.nonceStr = mOrderStrBean.getNoncestr();// 随机字符串，不长于32位
            req.timeStamp = mOrderStrBean.getTimestamp();// 时间戳
            req.packageValue = mOrderStrBean.getPackageX();// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
            req.sign = mOrderStrBean.getSign();// 签名，
            // 调用微信SDK，发起支付，回调WxPayEntryActivity
            mWxApi.sendReq(req);
        }
    }
}
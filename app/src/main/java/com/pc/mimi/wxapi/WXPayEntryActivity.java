package com.pc.mimi.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.pc.mimi.config.CommonTags;

import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.KLog;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, CommonTags.WX_APP_ID, true);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
		/*
		  0 支付成功
		 -1 发生错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
		 -2 用户取消 发生场景：用户不支付了，点击取消，返回APP。
		 */

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//             根据返回码
            int code = resp.errCode;
            switch (code) {
                case 0:
                    // 去本地确认支付结果
                    KLog.d("---------------------------微信支付成功----------------------------------------------");
                    RxBus.getDefault().post(new WeiXinPayEvent(true));
                    finish();
                    break;
                case -2:
                    RxBus.getDefault().post(new WeiXinPayEvent(false));
                    finish();
                    break;
                default:
                    RxBus.getDefault().post(new WeiXinPayEvent(false));
                    finish();
                    break;
            }
        }
    }
}
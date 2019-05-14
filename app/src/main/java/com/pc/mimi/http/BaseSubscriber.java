package com.pc.mimi.http;

import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.http.NetworkUtil;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;

/**
 * Created by goldze on 2017/5/10.
 * 该类仅供参考，实际业务Code, 根据需求来定义，
 */
@Deprecated
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {
    public abstract void onResult(T t);

    public abstract void onDissmissDialog();

    @Override
    public void onComplete() {
        onDissmissDialog();
    }

    public void onDataEmpty(String msg) {
    }

    public void onCodeFailEmpty() {
    }

    public void onTokenExceed() {
    }

    @Override
    public void onError(Throwable e) {
        onDissmissDialog();
        e.printStackTrace();
        KLog.e(e.getMessage());
        if (e instanceof ResponseThrowable) {
            ResponseThrowable rError = (ResponseThrowable) e;
            ToastUtils.showShort(rError.getMessage());
            return;
        }
        //其他全部甩锅网络异常
        ToastUtils.showShort("网络异常");
    }

    @Override
    public void onStart() {
        super.onStart();
//        ToastUtils.showShort("http is start");
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(Utils.getContext())) {
            ToastUtils.showShort("无网络，读取缓存数据");
            onComplete();
        }
    }

    @Override
    public void onNext(Object o) {
        onDissmissDialog();
        BaseResponse baseResponse = (BaseResponse) o;
        switch (baseResponse.getMeta().getCode()) {
            case CodeRule.CODE_200:
                //请求成功, 正确的操作方式
                if ((T) baseResponse.getData() != null) {
                    onResult((T) baseResponse.getData());
                } else {
                    onDataEmpty(baseResponse.getMeta().getMsg());
                }
                break;
            case CodeRule.CODE_220:
                // 请求成功, 正确的操作方式, 并消息提示
                onResult((T) baseResponse.getData());
                break;
            case CodeRule.CODE_300:
                //请求失败，不打印Message
                KLog.e("请求失败");
                ToastUtils.showShort("错误代码:", baseResponse.getMeta().getMsg());
                break;
            case CodeRule.CODE_999999:
                //请求失败，打印Message
                onCodeFailEmpty();
                ToastUtils.showShort(baseResponse.getMeta().getMsg());
                break;
            case CodeRule.CODE_500:
                //服务器内部异常
                ToastUtils.showShort("错误代码:", baseResponse.getMeta().getCode());
                break;
            case CodeRule.CODE_503:
                //参数为空
                KLog.e("参数为空");
                break;
            case CodeRule.CODE_502:
                //没有数据
                KLog.e("没有数据");
                break;
            case CodeRule.CODE_900001:
                //无效的Token，提示跳入登录页
                //清除token
                onTokenExceed();
//                AppUtils.clearJpAlias(Utils.getContext());
//                ToastUtils.showShort(baseResponse.getMessage());
//                SaveUtil.clearUserToken();
//                SaveUtil.clearStoreInfo();
                //跳入登录界面
//                Intent intent = new Intent(Utils.getContext(), LogRegActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Messenger.getDefault().sendNoMsg(Contacts.UPDATE_STROE_INFO);
//                Utils.getContext().startActivity(intent);
                //*****该类仅供参考，实际业务Code, 根据需求来定义，******//
                break;
            case CodeRule.CODE_530:
                ToastUtils.showShort("请先登录");
                break;
            case CodeRule.CODE_551:
                ToastUtils.showShort("错误代码:", baseResponse.getMeta().getCode());
                break;
            default:
                ToastUtils.showShort("错误代码:", baseResponse.getMeta().getCode());
                break;
        }
    }

    public static final class CodeRule {
        //请求成功, 正确的操作方式
        static final int CODE_200 = 200;
        //请求成功, 消息提示
        static final int CODE_220 = 220;
        //请求失败，不打印Message
        static final int CODE_300 = 300;
        //请求失败，打印Message
        static final int CODE_999999 = 999999;
        //服务器内部异常
        static final int CODE_500 = 500;
        //参数为空
        static final int CODE_503 = 503;
        //没有数据
        static final int CODE_502 = 502;
        //无效的Token
        static final int CODE_900001 = 900001;
        //未登录
        static final int CODE_530 = 530;
        //请求的操作异常终止：未知的页面类型
        static final int CODE_551 = 551;
    }
}

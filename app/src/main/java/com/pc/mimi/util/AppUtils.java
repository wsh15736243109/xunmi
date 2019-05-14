package com.pc.mimi.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.pc.mimi.bean.MyAssessBean;
import com.pc.mimi.bean.UserInfo;
import com.pc.mimi.bean.WishProgramBean;
import com.pc.mimi.config.SpKey;
import com.pc.mimi.http.ApiDisposableObserver;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.xlyuns.xunmi.R;
import com.pc.mimi.bean.PublishMettingBean;
import com.pc.mimi.config.CommonTags;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.SPUtils;

/**
 * Created by Liyao on 2019/1/12.
 */

public class AppUtils {


    /**
     * 请求网络,便捷写法
     *
     * @param observable
     * @param provider
     * @param onSubscribe
     * @param onNext
     * @param onError
     * @param onComplete
     * @param <T>
     */
    public static <T> void requestData(Observable<T> observable, LifecycleProvider provider
            , Consumer<? super Disposable> onSubscribe
            , Consumer<? super T> onNext
            , Consumer<? super Throwable> onError
            , Action onComplete) {
        observable.compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(provider))
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(onSubscribe)
                .subscribe(onNext, onError, onComplete);
    }

    /**
     * @param observable
     * @param provider
     * @param onSubscribe
     * @param apiDisposableObserver
     * @param <T>
     */
    public static <T> void requestData(Observable<T> observable, LifecycleProvider provider
            , Consumer<? super Disposable> onSubscribe
            , ApiDisposableObserver apiDisposableObserver) {
        observable.compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(provider))
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(onSubscribe)
                .subscribe(apiDisposableObserver);
    }

    //判断是否登入
    public static boolean isLogin() {
        return AppUtils.getUser() != null;
    }

    //设置隐藏状态栏,白色字体
    public static void setStatusBar(Activity activity) {
        CommonUtil.setStatusBarTransparent(activity, 0);
        CommonUtil.setStatusBarMode(activity, false);
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 获取版本名
     *
     * @param context
     * @return
     */
    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    /**
     * 极光推送设置别名
     *
     * @param applicationContext
     */
    public static void setJpAlias(Context applicationContext) {
//        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
//        tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
//        tagAliasBean.isAliasAction = true;
//        if (AppUtils.isLogin()) {
//            tagAliasBean.alias = SaveUtil.getStoreInfo().getContactNumber();
////        Set<String> set = new HashSet<>();
////        tagAliasBean.tags = set;
////        set.add(SaveUtil.getStoreInfo().getContactNumber());//名字任意，可多添加几个,能区别就好了
//            KLog.d("----set---sequence---->",TagAliasOperatorHelper.sequence);
//            TagAliasOperatorHelper.getInstance().handleAction(applicationContext.getApplicationContext(), TagAliasOperatorHelper.sequence, tagAliasBean);
//        }
    }

    /**
     * 清除极光推送设置别名
     *
     * @param context
     */
    public static void clearJpAlias(Context context) {
//        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
//        tagAliasBean.action = TagAliasOperatorHelper.ACTION_DELETE;
//        tagAliasBean.isAliasAction = true;
//        if (AppUtils.isLogin()) {
//            tagAliasBean.alias = SaveUtil.getStoreInfo().getContactNumber();
//            KLog.d("---clear----sequence---->",TagAliasOperatorHelper.sequence);
//            TagAliasOperatorHelper.getInstance().handleAction(context.getApplicationContext(), TagAliasOperatorHelper.sequence, tagAliasBean);
//        }
    }

    /**
     * 保存用户ID
     *
     * @param user 用户
     */
    public static synchronized void saveUser(UserInfo user) {
        String str = "";
        try {
            str = SerializableUtil.obj2Str(user);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SPUtils.getInstance().put(SpKey.USER_BEAN, AESUtil.enctrypt(str, CommonTags.AES_PWD));
    }

    /**
     * 读取用户
     *
     * @return 用户
     */
    public static synchronized UserInfo getUser() {
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(SpKey.USER_BEAN, ""))) {
            String str = AESUtil.decrypt(SPUtils.getInstance().getString(SpKey.USER_BEAN, ""), CommonTags.AES_PWD);
            UserInfo user = null;
            try {
                Object obj = SerializableUtil.str2Obj(str);
                if (obj != null) {
                    user = (UserInfo) obj;
                }

            } catch (StreamCorruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return user;
        } else
            return null;
    }

    /**
     * 清除用户信息
     */
    public static void clearUser() {
        SPUtils.getInstance().remove(SpKey.USER_BEAN);
    }

    /**
     * 获取约会类型
     *
     * @return
     */
    public static ArrayList<PublishMettingBean> getPartyType() {
        return new ArrayList<PublishMettingBean>() {{
            add(new PublishMettingBean("旅行", R.drawable.ic_travel));
            add(new PublishMettingBean("去唱歌", R.drawable.ic_sing));
            add(new PublishMettingBean("看电影", R.drawable.ic_film));
            add(new PublishMettingBean("吃吃喝喝", R.drawable.ic_eat));
            add(new PublishMettingBean("运动", R.drawable.ic_sport));
            add(new PublishMettingBean("玩游戏", R.drawable.ic_game));
            add(new PublishMettingBean("夜蒲聚会", R.drawable.ic_party));
            add(new PublishMettingBean("其他", R.drawable.ic_other));
        }};
    }

    /**
     * 获取约会期望的数据
     *
     * @return 约会期望
     */
    public static List<WishProgramBean> getMettingWishListData() {
        return new ArrayList<WishProgramBean>() {{
            add(new WishProgramBean("看脸"));
            add(new WishProgramBean("有趣"));
            add(new WishProgramBean("土豪"));
            add(new WishProgramBean("关爱我"));
            add(new WishProgramBean("看感觉"));
            add(new WishProgramBean("无所谓"));
        }};
    }

    /**
     * 获取约会节目的数据
     *
     * @return 约会节目
     */
    public static List<WishProgramBean> getMettingProgramListData() {
        return new ArrayList<WishProgramBean>() {{
            add(new WishProgramBean("旅行"));
            add(new WishProgramBean("去唱歌"));
            add(new WishProgramBean("看电影"));
            add(new WishProgramBean("吃吃喝喝"));
            add(new WishProgramBean("运动"));
            add(new WishProgramBean("玩游戏"));
            add(new WishProgramBean("夜蒲聚会"));
            add(new WishProgramBean("其他"));
        }};
    }

    /**
     * 评价标签
     *
     * @return
     */
    public static List<MyAssessBean> createMyAssessList() {
        return new ArrayList<MyAssessBean>() {{
            add(new MyAssessBean("礼貌", 0, 1));
            add(new MyAssessBean("有趣", 0, 2));
            add(new MyAssessBean("大方", 0, 3));
            add(new MyAssessBean("爽快", 0, 4));
            add(new MyAssessBean("口嗨", 0, 5));
            add(new MyAssessBean("不友好", 0, 6));
        }};
    }

    //存经度
    public static void saveLongitude(String longitude) {
        SPUtils.getInstance().put(CommonTags.LONGITUDE, longitude);
    }

    //取经度
    public static String getLongitude() {
        return SPUtils.getInstance().getString(CommonTags.LONGITUDE, "120.32214889");
    }

    //存纬度
    public static void saveLatitude(String latitude) {
        SPUtils.getInstance().put(CommonTags.LATITUDE, latitude);
    }

    //取纬度
    public static String getLatitude() {
        return SPUtils.getInstance().getString(CommonTags.LATITUDE, "30.31927868");
    }
}

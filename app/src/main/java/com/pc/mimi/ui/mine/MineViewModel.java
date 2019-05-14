package com.pc.mimi.ui.mine;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.pc.mimi.adapter.MyAssessAdapter;
import com.pc.mimi.adapter.PhotoListAdapter;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.ui.blacklist.BlackListActivity;
import com.pc.mimi.ui.commonmodel.CommonRemindDialogModel;
import com.pc.mimi.ui.edituserinfo.EditUserInfoActivity;
import com.pc.mimi.ui.metting.MyMeetingActivity;
import com.pc.mimi.ui.myfavourite.MyFavourIteActivity;
import com.pc.mimi.ui.setting.SecretSettingActivity;
import com.pc.mimi.ui.setting.SettingActivity;
import com.pc.mimi.ui.vipmembercenter.LadysAuthActivity;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.util.CommonUtil;
import com.pc.mimi.util.inject.FindView;
import com.pc.mimi.util.inject.Init;
import com.pc.mimi.util.inject.OnClick;
import com.pc.mimi.util.inject.ViewUtils;
import com.xlyuns.xunmi.R;
import com.pc.mimi.bean.AssessBean;
import com.pc.mimi.bean.MyAssessBean;
import com.pc.mimi.bean.PictureBean;
import com.pc.mimi.bean.SignDataBean;
import com.pc.mimi.bean.UserInfo;
import com.pc.mimi.config.CommonTags;
import com.pc.mimi.ui.vipmembercenter.VipMemberCenterActivity;
import com.pc.mimi.ui.wallet.WalletActivity;
import com.pc.mimi.widget.CustomDialog;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MineViewModel extends CommonRemindDialogModel {
    public MineViewModel(@NonNull Application application) {
        super(application);
    }

    List<PictureBean> mPictureBooks;
    PhotoListAdapter mPhotoListAdapter;

    CustomDialog mSignDialog;
    SignDialogEvent mSignDialogEvent;
    AssessDialogEvent mAssessDialogEvent;
    RedPicDialogEvent mRedPicDialogEvent;
    private int gender = -1;
    public List<String> mPictureBookLists = new ArrayList<>();
    //姓名
    public ObservableField<String> userName = new ObservableField<>("");
    //男->会员,女->认证
    public ObservableField<String> Member = new ObservableField<>("会员");
    public ObservableField<String> MemberDes = new ObservableField<>("升级会员尊享特权");
    //钱包金额
    public ObservableField<String> wallet = new ObservableField<>("0.00觅币");
    //是否是会员
    public ObservableInt isShowVip = new ObservableInt(View.GONE);
    public ObservableInt imgVip = new ObservableInt(R.drawable.ic_vip);
    //头像
    public ObservableField<String> avatar = new ObservableField<>("");
    //行业
    public ObservableField<String> job = new ObservableField<>("");
    //约会范围
    public ObservableField<String> datingRange = new ObservableField<>("");
    //历史访客数量
    public ObservableField<String> historyCount = new ObservableField<>("有0个人看过你");
    //阅后即焚数量
    public ObservableField<String> seeAndBreak = new ObservableField<>("有0个人焚了你的照片");
    //是否通过认证,,你已经通过了知心摩天轮对你的安全审核
    public ObservableField<String> isAuth = new ObservableField<>("未认证");
    //是否通过认证
    public ObservableBoolean isAuthenticated = new ObservableBoolean(false);

    //钱包
    public BindingCommand goWallet = new BindingCommand(() -> startActivity(WalletActivity.class));
    //我的约会
    public BindingCommand myMeeting = new BindingCommand(() -> startActivity(MyMeetingActivity.class));
    //男->会员中心,女->认证中心
    public BindingCommand goMember = new BindingCommand(() -> {
        switch (gender) {
            case 0:
                startActivity(LadysAuthActivity.class);
                break;
            case 1:
                startActivity(VipMemberCenterActivity.class);
                break;
            case -1:
                ToastUtils.showShort("数据异常.请联系客服");
                break;
        }
    });
    //设置红包照片
    public BindingCommand setRedPhoto = new BindingCommand(() -> {
        super.showDialog("取消", "继续", "最多可选择1张照片作为红包照片,费用由你定价");
    });

    @Override
    protected void remindConfir() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("photos", (Serializable) mPictureBooks);
        startActivity(PhotoBookActivity.class, bundle);
    }

    public UIChangeObser uc = new UIChangeObser();

    public class UIChangeObser {
        public ObservableBoolean pic = new ObservableBoolean(false);
    }

    @Override
    public void onCreate() {
        Messenger.getDefault().register(this, CommonTags.REFRESH_MINE, () -> {
            requestUserInfo();
        });

        Messenger.getDefault().register(this, CommonTags.SET_RED_PIC, String.class, s -> invokeEventAction(context -> {
            if (mSignDialog != null) mSignDialog = null;
            if (mSignDialogEvent != null) mSignDialogEvent = null;
            mRedPicDialogEvent = new RedPicDialogEvent(s);
            mSignDialog = new CustomDialog(context, R.layout.dialog_red_pic_set, true, false,
                    Gravity.CENTER, content -> ViewUtils.inject(content, mRedPicDialogEvent), false);
            mSignDialog.show();
        }));
    }

    class RedPicDialogEvent {
        @FindView(R.id.et_input)
        EditText et_input;
        private String user_picture_id = "";

        public RedPicDialogEvent(String user_picture_id) {
            this.user_picture_id = user_picture_id;
        }

        @OnClick({R.id.confir, R.id.cancel})
        void onclicked(View view) {
            switch (view.getId()) {
                case R.id.confir:
                    if (TextUtils.isEmpty(et_input.getText().toString())) {
                        ToastUtils.showShort("请输入查看金额");
                        return;
                    }
                    if (Double.parseDouble(et_input.getText().toString()) == 0f) {
                        ToastUtils.showShort("金额必须大于0");
                        return;
                    }
                    mSignDialog.dismiss();
                    Map<String, Object> map = new HashMap<String, Object>() {{
                        put("user_id", AppUtils.getUser().getUser_id());
                        put("price", et_input.getText().toString());
                        put("user_picture_id", user_picture_id);
                    }};
                    KLog.d("map-->", map.toString());
                    AppUtils.requestData(RetrofitClient.getInstance().create(API.class).set_picture_pay(map),
                            getLifecycleProvider(), disposable -> showDialog(),
                            new ApiDisposableObserver() {
                                @Override
                                public void onResult(Object o) {

                                }

                                @Override
                                public void dialogDismiss() {
                                    dismissDialog();
                                }

                                @Override
                                public void onDataEmpty(String msg) {
                                    ToastUtils.showShort("设置成功");
                                    requestUserInfo();
                                }
                            });
                    break;
                case R.id.cancel:
                    mSignDialog.dismiss();
                    break;
            }

        }
    }

    /**
     * 处理选择照片后的结果
     *
     * @param data
     */
    public void dealResult(Intent data) {
        KLog.d("------------------dealResult-------------------");
        // 图片、视频、音频选择结果回调
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        // 例如 LocalMedia 里面返回三种path
        // 1.media.getPath(); 为原图path
        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
        //上传图片
        List<String> list = new ArrayList<>();
        for (LocalMedia localMedia : selectList) {
            list.add(localMedia.getCompressPath());
        }
        setPicChoose(list);
    }

    private void setPicChoose(List<String> list) {
        uploadImg(list);
    }

    //上传图片
    private void uploadImg(List<String> list) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
        KLog.d("-------------getUser_id-----------------" + AppUtils.getUser().getUser_id());
        builder.addFormDataPart("user_id", AppUtils.getUser().getUser_id());//传入服务器需要的key，和相应value值
        for (String path : list) {
            File file = new File(path);
            RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), file);//表单类型
            builder.addFormDataPart("files", file.getName(), body); //添加图片数据，body创建的请求体
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).uploadImage(parts)
                .map(stringBaseResponse -> stringBaseResponse.getData())
                .flatMap(s -> {
                    Map<String, Object> map = new HashMap<String, Object>() {{
                        put("picture_url", s);
                        put("user_id", AppUtils.getUser().getUser_id());
                    }};
                    return RetrofitClient.getInstance().create(API.class).addPhotoBook(map);
                }), getLifecycleProvider(), disposable -> showDialog(), new ApiDisposableObserver() {
            @Override
            public void onResult(Object o) {

            }

            @Override
            public void dialogDismiss() {
                dismissDialog();
            }

            @Override
            public void onDataEmpty(String msg) {
                requestUserInfo();
                ToastUtils.showShort("上传成功");
            }
        });
    }

    //设置
    public BindingCommand setting = new BindingCommand(() -> startActivity(SettingActivity.class));
    //我的关注
    public BindingCommand favourite = new BindingCommand(() -> {
        startActivity(MyFavourIteActivity.class);
    });
    //编辑个人信息
    public BindingCommand editInfo = new BindingCommand(() -> {
        startActivity(EditUserInfoActivity.class);
    });
    //隐私设置
    public BindingCommand secretSetting = new BindingCommand(() -> {
        startActivity(SecretSettingActivity.class);
    });
    //黑名单
    public BindingCommand blackList = new BindingCommand(() -> {
        startActivity(BlackListActivity.class);
    });
    //上传照片
    public BindingCommand uploadPhoto = new BindingCommand(() -> {
        uc.pic.set(!uc.pic.get());
    });
    //我的评价
    public BindingCommand myAssess = new BindingCommand(() -> invokeEventAction(context -> {
        if (mSignDialog != null) mSignDialog = null;
        if (mAssessDialogEvent != null) mAssessDialogEvent = null;
        mAssessDialogEvent = new AssessDialogEvent(context);
        mSignDialog = new CustomDialog(context, R.layout.dialog_my_assess, true, true, Gravity.CENTER,
                content -> ViewUtils.inject(content, mAssessDialogEvent)
                , false);
        mSignDialog.show();
        requestMyAssess();
    }));

    /**
     * 评价
     */
    private void requestMyAssess() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestMyAssess(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<AssessBean>() {
                    @Override
                    public void onResult(AssessBean o) {
                        if (o != null) {
                            mAssessDialogEvent.update(o);
                        }
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                    @Override
                    public void onDataEmpty(String msg) {
                        ToastUtils.showShort("评价数据异常!!!请联系客服");
                        mSignDialog.dismiss();
                    }
                });
    }

    //签到
    public BindingCommand sign = new BindingCommand(() -> invokeEventAction(context -> {
        if (mSignDialog != null) mSignDialog = null;
        if (mSignDialogEvent != null) mSignDialogEvent = null;
        mSignDialogEvent = new SignDialogEvent(context);
        mSignDialog = new CustomDialog(context, R.layout.dialog_sign, true, true, Gravity.CENTER,
                content -> ViewUtils.inject(content, mSignDialogEvent)
                , false);
        mSignDialog.show();
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).see_sign_info(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<SignDataBean>() {
                    @Override
                    public void onResult(SignDataBean o) {
                        if (o.getNum_day() > 7) {
                            mSignDialogEvent.updateSelect(7);
                        } else {
                            mSignDialogEvent.updateSelect(o.getNum_day());
                        }
                        mSignDialogEvent.updateIsSign(o.getToday_is_sign());
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }));

    /**
     * 我的评价dialog中事件
     */
    private class AssessDialogEvent {
        @FindView(R.id.recycler)
        RecyclerView mRecyclerView;
        MyAssessAdapter mMyAssessAdapter;
        List<MyAssessBean> mMyAssessBeans;
        private Context mContext;

        public AssessDialogEvent(Context context) {
            mContext = context;
        }

        @Init
        void initViews() {
            mMyAssessBeans = AppUtils.createMyAssessList();
            mMyAssessAdapter = new MyAssessAdapter(mMyAssessBeans);
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            mMyAssessAdapter.bindToRecyclerView(mRecyclerView);
        }

        public void update(AssessBean o) {
            int[] count = {o.getType_1_count(), o.getType_2_count(), o.getType_3_count(), o.getType_4_count(), o.getType_5_count(), o.getType_6_count()};
            for (int i = 0; i < count.length; i++) {
                mMyAssessBeans.get(i).setCount(count[i]);
            }
            mMyAssessAdapter.notifyDataSetChanged();
        }

        @OnClick({R.id.cancel})
        void onClicked(View view) {
            switch (view.getId()) {
                case R.id.cancel:
                    mSignDialog.dismiss();
                    break;
            }
        }

    }

    /**
     * 个人信息
     */
    public void requestUserInfo() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestUserInfo(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<UserInfo>() {
                    @Override
                    public void onResult(UserInfo o) {
                        UserInfo userInfo = AppUtils.getUser();
                        userInfo.setAvatar(o.getAvatar());
                        AppUtils.saveUser(userInfo);
                        userName.set(o.getNick_name());
                        wallet.set(o.getWallet() + "觅币");
                        gender = o.getGender();
//                        gender = 0;
                        switch (gender) {
                            case 0://女
                                imgVip.set(R.drawable.ic_real);
                                Member.set("实名认证");
                                MemberDes.set("去认证");
                                if (o.getIs_authenticated() == 1) {
                                    isAuthenticated.set(true);
                                    isAuth.set("你已经通过了知心摩天轮对你的安全审核");
                                }
                                isShowVip.set(o.getIs_authenticated() == 1 ? View.VISIBLE : View.GONE);
                                break;
                            case 1://男
                                imgVip.set(R.drawable.ic_vip);
                                Member.set("会员");
                                MemberDes.set("升级会员尊享特权");
                                isAuthenticated.set(true);
                                isAuth.set("你已经通过了知心摩天轮对你的安全审核");
                                isShowVip.set(o.getIs_vip() == 1 ? View.VISIBLE : View.GONE);
                                break;
                        }
                        avatar.set(API.BASEURL + o.getAvatar());
                        datingRange.set(o.getDating_range());
                        historyCount.set("有" + o.getHistory_count() + "个人看过你");
                        seeAndBreak.set("有" + o.getSee_and_break() + "个人焚了你的照片");
                        job.set(o.getJob());
                        isAuthenticated.set(o.getIs_authenticated() == 0 ? false : true);
                        mPictureBooks.clear();
                        mPictureBookLists.clear();
                        for (PictureBean pictureBook : o.getPicture()) {
                            mPictureBookLists.add(pictureBook.getPicture_url());
                        }
                        if (o.getPicture().size() > 8) {
                            mPictureBooks.addAll(o.getPicture().subList(0, 8));
                        } else {
                            mPictureBooks.addAll(o.getPicture());
                        }
                        Messenger.getDefault().send(mPictureBooks, CommonTags.REFRESH_PHOTO_BOOK);
                        mPhotoListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }

    /**
     * 签到弹框中的事件
     */
    private class SignDialogEvent {
        @FindView(R.id.content)
        LinearLayout content;
        @FindView(R.id.text_des)
        TextView text_des;
        @FindView(R.id.sign)
        TextView mSign;
        List<TextView> mTextViews;

        Context mContext;
        private static final String FOUNT = "<font color=\"#FF1616\">";
        private static final String BACK = "</font>";
        private static final String BR = "<br>";
        private int signCount = 10;
        private int signMaxCount = 100;
        String rules = "每天签到送" + FOUNT + signCount + BACK + "觅币" + BR + "连续签到满7天送" + FOUNT + signMaxCount + BACK + "觅币";

        public SignDialogEvent(Context context) {
            mContext = context;
        }

        @OnClick({R.id.sign, R.id.cancel})
        public void onClicked(View view) {
            mSignDialog.dismiss();
            switch (view.getId()) {
                case R.id.sign:
                    if (mSign.getText().equals("签到")) {
                        goSignNow();
                    } else {
                        ToastUtils.showShort("已签到过了哦");
                    }
                    break;
                case R.id.cancel:
                    break;
            }
        }

        @Init
        public void initViews() {
            if (mTextViews == null)
                mTextViews = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.circle_text_sign_day, null);
                textView.setText((i + 1) + "天");
                if (i == 6) {
                    content.addView(textView);
                } else {
                    content.addView(textView);
                    View view = LayoutInflater.from(mContext).inflate(R.layout.include_line_hr_match, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(CommonUtil.dip2px(mContext, 10),
                            CommonUtil.dip2px(mContext, 1));
                    view.setLayoutParams(params);
                    content.addView(view);
                }
            }
            text_des.setText(Html.fromHtml(rules));
            getTextContent();
        }

        public void getTextContent() {
            mTextViews.clear();
            int childCount = content.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = content.getChildAt(i);
                if (view instanceof TextView) {
                    mTextViews.add((TextView) view);
                }
            }
        }

        /**
         * 设置选中
         *
         * @param select
         */
        public void updateSelect(int select) {
            for (int i = 0; i < select; i++) {
                mTextViews.get(i).setSelected(true);
            }
        }

        /**
         * 今天是否已签到
         *
         * @param today_is_sign
         */
        public void updateIsSign(int today_is_sign) {
            mSign.setText(today_is_sign == 0 ? "签到" : "已签到");
        }
    }

    //客服
    public BindingCommand custom = new BindingCommand(() -> {
        Bundle bundle = new Bundle();
        bundle.putString("title", "联系客服");
        bundle.putString("url", CommonTags.CUSTOM);
        startActivity(WebActivity.class, bundle);
    });

    /**
     * 去签到
     */
    private void goSignNow() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestSign(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {
                        requestUserInfo();
                        ToastUtils.showShort("签到成功");
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                    @Override
                    public void onDataEmpty(String msg) {
                    }
                });
    }
}

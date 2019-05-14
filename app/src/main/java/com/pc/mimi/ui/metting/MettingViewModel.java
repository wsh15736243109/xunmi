package com.pc.mimi.ui.metting;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.pc.mimi.adapter.MeetingAdapter;
import com.pc.mimi.adapter.PublishMettingAdapter;
import com.pc.mimi.bean.CommentReturnBean;
import com.pc.mimi.bean.MettingListBean;
import com.pc.mimi.bean.PublishMettingBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.ui.publishmeeting.PublishMeetingActivity;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.util.CommonUtil;
import com.pc.mimi.util.ImageLoaderUtils;
import com.pc.mimi.util.inject.FindView;
import com.pc.mimi.util.inject.Init;
import com.pc.mimi.util.inject.OnClick;
import com.pc.mimi.util.inject.ViewUtils;
import com.pc.mimi.widget.CustomDialog;
import com.pc.mimi.widget.SquareImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xlyuns.xunmi.R;
import com.pc.mimi.widget.CustomGridLayoutManager;
import com.pc.mimi.widget.CustomPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MettingViewModel extends CommonMeetingViewModel {
    public MettingViewModel(@NonNull Application application) {
        super(application);
    }

    CustomPopupWindow mPublishWindow;
    PublishWindowListener mPublishWindowListener;
    MeetingAdapter mMeetingAdapter;
    CustomPopupWindow mReportPop;//举报
    CustomDialog mSignUpDialog;//报名
    SignUpDialogEvent mSignUpDialogEvent;//报名
    ReportPopEvent mReportPopEvent;
    //发布约会
    public BindingCommand publishMeeting = new BindingCommand(() -> showPublishMettingWindow());
    UIChangeObser uc = new UIChangeObser();
    public int mettingType = 0;
    public int mettingSex = 0;

    //选择的报名图片
    public void dealResult(Intent data) {
        KLog.d("------------------dealResult-------------------");
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        mSignUpDialogEvent.setSelectPic(selectList.get(0).getCompressPath());
    }

    class UIChangeObser {
        public ObservableBoolean left = new ObservableBoolean(false);
        public ObservableBoolean middle = new ObservableBoolean(false);
        public ObservableBoolean right = new ObservableBoolean(false);
        public ObservableBoolean signUp = new ObservableBoolean(false);
    }

    @Override
    protected void dealThumbUp(int position, boolean isUp) {
        ToastUtils.showShort(isUp ? "点赞成功" : "取消点赞");
        if (isUp) {
            mMettingListBeans.get(position).setGood_count(mMettingListBeans.get(position).getGood_count() + 1);
            mMettingListBeans.get(position).setIs_good_for_this(1);
            mMeetingAdapter.notifyItemChanged(position, "zan");
        } else {
            mMettingListBeans.get(position).setGood_count(mMettingListBeans.get(position).getGood_count() - 1);
            mMettingListBeans.get(position).setIs_good_for_this(0);
            mMeetingAdapter.notifyItemChanged(position, "zan");
        }
    }

    @Override
    protected void dealPubslishComment(int position, String content, CommentReturnBean o) {
        MettingListBean.ChatList chatList = new MettingListBean.ChatList();
        chatList.setIs_single(1);
        chatList.setAll_dating_argument_id(o.getAll_dating_argument_id());
        chatList.setContent(content);
        chatList.setOut_nick_name(o.getMy_nick_name());
        mMettingListBeans.get(position).getChat_list().add(chatList);
        mMeetingAdapter.notifyItemChanged(position, "comment");
        ToastUtils.showShort("发布评论成功");
    }

    @Override
    protected void dealReComment(int position, int commentPoi, String content, CommentReturnBean o) {
        MettingListBean.ChatList chatList = new MettingListBean.ChatList();
        chatList.setIs_single(0);
        chatList.setAll_dating_argument_id(o.getAll_dating_argument_id());
        chatList.setContent(content);
        chatList.setOut_nick_name(o.getMy_nick_name());
        chatList.setIn_nick_name(mMettingListBeans.get(position).getChat_list().get(commentPoi).getOut_nick_name());
        mMettingListBeans.get(position).getChat_list().add(chatList);
        mMeetingAdapter.notifyItemChanged(position, "comment");
        ToastUtils.showShort("回复评论成功");
    }

    /**
     * 举报
     */
    public void report(View view, String r_user_id) {
        invokeEventAction(context -> {
            View inflateView = LayoutInflater.from(context).inflate(R.layout.pop_report_layout, null);
            if (mReportPop != null) mReportPop = null;
            if (mReportPopEvent != null) mReportPopEvent = null;
            mReportPopEvent = new ReportPopEvent(r_user_id);
            mReportPop = CustomPopupWindow.builder()
                    .contentView(inflateView)
                    .backgroundDrawable(new ColorDrawable())
                    .animationStyle(R.style.popwin_anim_style_right_in)
                    .customListener(contentView -> ViewUtils.inject(contentView, mReportPopEvent))
                    .isOutsideTouch(true)
                    .isWrap(true)
                    .build();
            int windowPos[] = CommonUtil.calculatePopWindowPos(view, inflateView);
            int xOff = 20;// 可以自己调整偏移
            windowPos[0] -= xOff;
            mReportPop.showAtLocation(view, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
        });
    }

    private class ReportPopEvent {
        private String r_user_id;

        public ReportPopEvent(String r_user_id) {
            this.r_user_id = r_user_id;
        }

        @OnClick({R.id.report, R.id.cancel})
        void onclicked(View view) {
            mReportPop.dismiss();
            switch (view.getId()) {
                case R.id.report:
                    goReport(this.r_user_id);
                    break;
                case R.id.cancel:
                    break;
            }
        }
    }

    //报名
    public void signUp(String all_dating_id) {
        invokeEventAction(context -> {
            if (mSignUpDialog != null) mSignUpDialog = null;
            if (mSignUpDialogEvent != null) mSignUpDialogEvent = null;
            mSignUpDialogEvent = new SignUpDialogEvent(all_dating_id);
            mSignUpDialog = new CustomDialog(context, R.layout.dialog_sing_up, false, true, Gravity.CENTER
                    , content -> ViewUtils.inject(content, mSignUpDialogEvent), false);
            mSignUpDialog.show();
        });
    }

    private class SignUpDialogEvent {
        @FindView(R.id.img)
        SquareImageView mImageView;
        private String url = "";
        private String allDatingId = "";

        public SignUpDialogEvent(String allDatingId) {
            this.allDatingId = allDatingId;
        }

        @OnClick({R.id.img, R.id.sign_up, R.id.cancel})
        void onClicked(View view) {
            switch (view.getId()) {
                case R.id.img:
                    uc.signUp.set(!uc.signUp.get());
                    break;
                case R.id.sign_up:
                    if (TextUtils.isEmpty(url)) {
                        ToastUtils.showShort("请选择照片哦");
                        return;
                    }
                    mSignUpDialog.dismiss();
                    requestSignUp(allDatingId, url);
                    break;
                case R.id.cancel:
                    mSignUpDialog.dismiss();
                    break;
            }
        }

        public void setSelectPic(String compressPath) {
            url = compressPath;
            ImageLoaderUtils.loadLocalImage(mImageView, compressPath);
        }
    }

    /**
     * do 匿名举报
     */
    private void goReport(String r_user_id) {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestReport(AppUtils.getUser().getUser_id(), r_user_id),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<Object>() {
                    @Override
                    public void onResult(Object o) {

                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                    @Override
                    public void onDataEmpty(String msg) {
                        ToastUtils.showShort("举报成功");
                    }
                });
    }


    public ObservableField<String> left_s = new ObservableField<>("最新发布");
    public ObservableField<String> middle_s = new ObservableField<>("不限性别");
    public ObservableField<String> right_s = new ObservableField<>("约会范围");
    //最新发布
    public BindingCommand selectPub = new BindingCommand(() -> {
        uc.left.set(!uc.left.get());
    });
    //不限性别
    public BindingCommand selectSex = new BindingCommand(() -> {
        uc.middle.set(!uc.middle.get());
    });
    //约会范围
    public BindingCommand selectArea = new BindingCommand(() -> {
        uc.right.set(!uc.right.get());
    });
    //刷新事件
    public BindingCommand<SmartRefreshLayout> onRefresh = new BindingCommand<SmartRefreshLayout>(smartRefreshLayout -> {
        requestData();
        smartRefreshLayout.finishRefresh();
    });

    /**
     * 请求网络数据
     */
    public void requestData() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("user_id", AppUtils.getUser().getUser_id());
            put("order_type", mettingType);//0最新1最近
            put("gender", mettingSex);//0所有1女2男
//                put("city", );//城市（可不传）
            put("longitude", AppUtils.getLongitude());//经度
            put("latitude", AppUtils.getLatitude());//纬度
        }};
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestDatingList(map),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<List<MettingListBean>>() {
                    @Override
                    public void onResult(List<MettingListBean> o) {
                        mMettingListBeans.clear();
                        mMettingListBeans.addAll(o);
                        mMeetingAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }

    /**
     * 发布约会
     */
    private void showPublishMettingWindow() {
        invokeEventAction(context -> {
            View inflateView = LayoutInflater.from(context).inflate(R.layout.pop_publish_metting_layout, null);
            mPublishWindowListener = new PublishWindowListener(context);
            mPublishWindow = CustomPopupWindow.builder()
                    .contentView(inflateView)
//                    .backgroundDrawable()
                    .animationStyle(R.style.popwin_anim_style_top_in)
                    .customListener(mPublishWindowListener)
                    .isOutsideTouch(true)
                    .isWrap(false)
                    .build();
            mPublishWindow.show();
        });
    }

    class PublishWindowListener implements CustomPopupWindow.CustomPopupWindowListener {
        Context mContext;

        public PublishWindowListener(Context context) {
            super();
            this.mContext = context;
        }

        @FindView(R.id.recycler)
        RecyclerView mRecyclerView;
        PublishMettingAdapter mPublishMettingAdapter;
        List<PublishMettingBean> mPublishMettingBeans;

        @Override
        public void initPopupView(View contentView) {
            ViewUtils.inject(contentView, this);
        }

        @Init
        public void initViews() {
            mPublishMettingBeans = new ArrayList<>();
            mPublishMettingBeans.addAll(AppUtils.getPartyType());
            mPublishMettingAdapter = new PublishMettingAdapter(mPublishMettingBeans);
            CustomGridLayoutManager c = new CustomGridLayoutManager(mContext, 4);
            c.setScrollEnable(false);
            mRecyclerView.setLayoutManager(c);
            mPublishMettingAdapter.bindToRecyclerView(mRecyclerView);
            mPublishMettingAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
                mPublishWindow.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString(PublishMeetingActivity.DATING_CONTENT, mPublishMettingBeans.get(position).getName());
                startActivity(PublishMeetingActivity.class, bundle);
            }));
        }

        @OnClick({R.id.close})
        public void onClicked(View view) {
            switch (view.getId()) {
                case R.id.close:
                    mPublishWindow.dismiss();
                    break;
            }
        }
    }

}

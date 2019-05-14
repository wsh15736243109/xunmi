package com.pc.mimi.ui.userdetail;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.pc.mimi.adapter.DetailPhotoListAdapter;
import com.pc.mimi.bean.MyAssessBean;
import com.pc.mimi.bean.OtherPersonBean;
import com.pc.mimi.bean.PictureBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.ui.chat.ChatActivity;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.util.inject.FindView;
import com.pc.mimi.util.inject.Init;
import com.pc.mimi.util.inject.OnClick;
import com.pc.mimi.util.inject.ViewUtils;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.OtherAssessAdapter;
import com.pc.mimi.widget.CustomDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;


public class UserDetailViewModel extends BaseViewModel {
    public String other_user_id = "";
    public String other_user_nickName = "";
    public DetailPhotoListAdapter mDetailPhotoListAdapter;
    public List<PictureBean> mPictureBeans;
    public ObservableField<OtherPersonBean> personBean = new ObservableField<>();
    public ObservableField<String> clickToLike = new ObservableField<>("未认证");
    public BindingCommand addMyLike = new BindingCommand(() -> {
        requestAddMyLike(personBean.get().getIs_like());
    });
    public ObservableBoolean isLike = new ObservableBoolean(false);
    public ObservableInt imgVip = new ObservableInt(R.drawable.ic_vip);
    public ObservableInt isShowVip = new ObservableInt(View.GONE);
    private CustomDialog mSignDialog;
    private AssessDialogEvent mAssessDialogEvent;
    private MoreEvent mMoreEvent;
    //是否已拉黑
    private boolean isHasBlackList = false;

    public void setPersonBean(OtherPersonBean entity) {
        this.personBean.set(entity);
    }

    public ObservableField<String> isAuth = new ObservableField<>("");
    public ObservableBoolean isAuthSelect = new ObservableBoolean(false);

    public UserDetailViewModel(@NonNull Application application) {
        super(application);
    }

    //是否显示胸围
    public ObservableInt isShowInfo = new ObservableInt(View.VISIBLE);
    public ObservableInt isCanLook = new ObservableInt(View.GONE);
    public ObservableInt isBustShow = new ObservableInt(View.GONE);
    public ObservableField<String> isAuthText = new ObservableField<>("");
    public ObservableInt isShow = new ObservableInt(View.GONE);
    //私聊
    public BindingCommand toChat = new BindingCommand(() -> {
        Bundle bundle = new Bundle();
        bundle.putString("user_id", other_user_id);
        bundle.putString("user_name", other_user_nickName);
        startActivity(ChatActivity.class, bundle);
    });
    //评价
    public BindingCommand asses = new BindingCommand(() -> invokeEventAction(context -> {
        if (mSignDialog != null) mSignDialog = null;
        if (mAssessDialogEvent != null) mAssessDialogEvent = null;
        mAssessDialogEvent = new AssessDialogEvent(context);
        mSignDialog = new CustomDialog(context, R.layout.dialog_go_assess, true, true, Gravity.CENTER,
                content -> ViewUtils.inject(content, mAssessDialogEvent)
                , false);
        mSignDialog.show();
    }));

    //更多
    public void more() {
        invokeEventAction(context -> {
            if (mSignDialog != null) mSignDialog = null;
            if (mMoreEvent != null) mMoreEvent = null;
            mMoreEvent = new MoreEvent();
            mSignDialog = new CustomDialog(context, R.layout.dialog_detail_more, true, true, Gravity.BOTTOM,
                    content -> ViewUtils.inject(content, mMoreEvent)
                    , true);
            mSignDialog.show();

        });
    }

    private class MoreEvent {
        @FindView(R.id.blacklist)
        TextView blacklist;
        @FindView(R.id.report)
        TextView report;
        @FindView(R.id.cancel)
        TextView cancel;

        @Init
        void initViews() {
            if (isHasBlackList) {
                blacklist.setText("移除拉黑（不再屏蔽双方）");
            }
        }

        @OnClick({R.id.blacklist, R.id.report, R.id.cancel})
        void onclicked(View view) {
            switch (view.getId()) {
                case R.id.blacklist://拉黑
                    mSignDialog.dismiss();
                    if (isHasBlackList) {//取消拉黑
                        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).remove_black_number(AppUtils.getUser().getUser_id(), other_user_id),
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
                                        ToastUtils.showShort("移除黑名单成功");
                                        isHasBlackList = false;
                                    }
                                });
                    } else {//拉黑
                        mSignDialog.dismiss();
                        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).add_black_number(AppUtils.getUser().getUser_id(), other_user_id),
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
                                        ToastUtils.showShort("已加入黑名单");
                                        isHasBlackList = true;
                                    }
                                });
                    }
                    break;
                case R.id.report://举报
                    mSignDialog.dismiss();
                    AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestReport(AppUtils.getUser().getUser_id(), other_user_id),
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
                    break;
                case R.id.cancel:
                    mSignDialog.dismiss();
                    break;
            }
        }
    }

    /**
     * 我的评价dialog中事件
     */
    private class AssessDialogEvent {
        @FindView(R.id.recycler)
        RecyclerView mRecyclerView;
        OtherAssessAdapter mOtherAssessAdapter;
        List<MyAssessBean> mMyAssessBeans;
        private Context mContext;
        private String pj = "";

        public AssessDialogEvent(Context context) {
            mContext = context;
        }

        @Init
        void initViews() {
            mMyAssessBeans = AppUtils.createMyAssessList();
            mOtherAssessAdapter = new OtherAssessAdapter(mMyAssessBeans);
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            mOtherAssessAdapter.bindToRecyclerView(mRecyclerView);
            mOtherAssessAdapter.setOnItemClickListener(((adapter, view, position) -> {
                for (MyAssessBean m : mMyAssessBeans) {
                    m.setSelected(false);
                }
                mMyAssessBeans.get(position).setSelected(true);
                pj = mMyAssessBeans.get(position).getId() + "";
                mOtherAssessAdapter.notifyDataSetChanged();
            }));
        }

        @OnClick({R.id.cancel, R.id.confir})
        void onClicked(View view) {
            switch (view.getId()) {
                case R.id.cancel:
                    mSignDialog.dismiss();
                    break;
                case R.id.confir:
//                    StringBuffer sb = new StringBuffer();
//                    for (int i = 0; i < mMyAssessBeans.size(); i++) {
//                        if (i == mMyAssessBeans.size() - 1) {
//                            sb.append(mMyAssessBeans.get(i).getId() + "");
//                        } else {
//                            sb.append(mMyAssessBeans.get(i).getId() + "");
//                            sb.append(",");
//                        }
//                    }
                    goAssess(pj);
                    break;
            }
        }

    }

    /**
     * 给别人评价
     */
    private void goAssess(String assess) {
        if (TextUtils.isEmpty(assess)) {
            ToastUtils.showShort("请选择评价");
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("user_id", AppUtils.getUser().getUser_id());
            put("in_user_id", other_user_id);
            put("type", assess);
        }};
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).goAssess(map),
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

                    }
                });
    }

    //社交账号
    public BindingCommand account = new BindingCommand(() -> {

    });
    //申请查看资料
    public BindingCommand requestLook = new BindingCommand(() -> {

    });

    //detail
    public void requestOtherInfoListData() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestSeeOtherInfo(AppUtils.getUser().getUser_id(), other_user_id),
                getLifecycleProvider(), disposable -> showDialog(), new ApiDisposableObserver<OtherPersonBean>() {
                    @Override
                    public void onResult(OtherPersonBean o) {
                        other_user_nickName = o.getNick_name();
                        clickToLike.set(o.getIs_like() == 1 ? "取消关注" : "点击关注");
                        isHasBlackList = o.getIs_in_black_mumber() == 1;
                        if (o.getIs_need_check() == 0) {
                            isShowInfo.set(View.VISIBLE);
                            isCanLook.set(View.GONE);
                        } else {
                            isShowInfo.set(View.GONE);
                            isCanLook.set(View.VISIBLE);
                        }
                        if (o.getIs_dating() == 0) {
                            isShow.set(View.VISIBLE);
                        } else {
                            isShow.set(View.GONE);
                        }
                        isAuthText.set(o.getGender() == 0 ? "她正在发起约会" : "他正在发起约会");
                        o.setHeight(o.getHeight() + "cm");
                        o.setWeight(o.getWeight() + "kg");
                        o.setAvatar(API.BASEURL + o.getAvatar());
                        if (o.getGender() == 0) {
                            isBustShow.set(View.VISIBLE);
                            if (o.getIs_authenticated() == 1) {
                                isAuth.set("她已通过视频认证真实性");
                                isAuthSelect.set(true);
                                imgVip.set(R.drawable.ic_real);
                                isShowVip.set(View.VISIBLE);
                            } else {
                                isAuthSelect.set(false);
                                isShowVip.set(View.GONE);
                                isAuth.set("未认证");
                            }
                        } else {
                            isBustShow.set(View.GONE);
                            isAuthSelect.set(true);
                            isAuth.set("已认证");
                            if (o.getIs_vip() == 1) {
                                imgVip.set(R.drawable.ic_vip);
                                isShowVip.set(View.VISIBLE);
                            } else {
                                isShowVip.set(View.GONE);
                            }
                        }
                        isLike.set(o.getIs_like() != 0);
                        if (o.getPicture() != null) {
                            mPictureBeans.clear();
                            mPictureBeans.addAll(o.getPicture());
                            mDetailPhotoListAdapter.notifyDataSetChanged();
                        }
                        setPersonBean(o);
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }

    //关注-->取消关注
    public void requestAddMyLike(int is_like) {
        if (is_like == 0) {
            AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestLikeIt(AppUtils.getUser().getUser_id(), personBean.get().getOther_user_id()),
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
                            personBean.get().setIs_like(1);
                            personBean.set(personBean.get());
                            clickToLike.set("取消关注");
                            isLike.set(true);
                            ToastUtils.showShort("关注成功");
                        }
                    });
        } else {
            AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestDelLikeIt(AppUtils.getUser().getUser_id(), personBean.get().getOther_user_id()),
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
                            personBean.get().setIs_like(0);
                            personBean.set(personBean.get());
                            clickToLike.set("点击关注");
                            isLike.set(false);
                            ToastUtils.showShort("取消关注");
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        personBean = null;
    }
}

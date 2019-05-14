package com.pc.mimi.ui.metting;

import android.app.Application;
import android.support.annotation.NonNull;

import com.pc.mimi.bean.CommentReturnBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.pc.mimi.adapter.MyMeetingAdapter;
import com.pc.mimi.bean.MettingListBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.util.AppUtils;

import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MyMeetingViewModel extends CommonMeetingViewModel {
    public MyMeetingViewModel(@NonNull Application application) {
        super(application);
    }

    MyMeetingAdapter mMyMeetingAdapter;
    //下拉刷新
    public BindingCommand<SmartRefreshLayout> onRefresh = new BindingCommand<SmartRefreshLayout>(smartRefreshLayout -> {
        requestMeetings();
        smartRefreshLayout.finishRefresh();
    });

    @Override
    protected void dealThumbUp(int position, boolean isUp) {
        ToastUtils.showShort(isUp ? "点赞成功" : "取消点赞");
        if (isUp) {
            mMettingListBeans.get(position).getGood_user().add(new MettingListBean.GoodUserBean(AppUtils.getUser().getUser_id(), AppUtils.getUser().getAvatar()));
            mMettingListBeans.get(position).setGood_count(mMettingListBeans.get(position).getGood_count() + 1);
            mMettingListBeans.get(position).setIs_good_for_this(1);
            mMyMeetingAdapter.notifyItemChanged(position, "zan");
        } else {
            for (int i = 0; i < mMettingListBeans.get(position).getGood_user().size(); i++) {
                if (mMettingListBeans.get(position).getGood_user().get(i).getThis_user_id().equals(AppUtils.getUser().getUser_id())) {
                    mMettingListBeans.get(position).getGood_user().remove(mMettingListBeans.get(position).getGood_user().get(i));
                }
            }
            mMettingListBeans.get(position).setGood_count(mMettingListBeans.get(position).getGood_count() - 1);
            mMettingListBeans.get(position).setIs_good_for_this(0);
            mMyMeetingAdapter.notifyItemChanged(position, "zan");
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
        mMyMeetingAdapter.notifyItemChanged(position, "comment");
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
        mMyMeetingAdapter.notifyItemChanged(position, "comment");
        ToastUtils.showShort("回复评论成功");
    }

    /**
     * 获取我的约会列表
     */
    public void requestMeetings() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).see_my_dating(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<List<MettingListBean>>() {
                    @Override
                    public void onResult(List<MettingListBean> o) {
                        mMettingListBeans.clear();
                        mMettingListBeans.addAll(o);
                        mMyMeetingAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }

    /**
     * 停止报名约会
     *
     * @param position 位置
     */
    public void finishSignUp(int position) {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).finishSignUp(AppUtils.getUser().getUser_id(), mMettingListBeans.get(position).getAll_dating_id()),
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
                        mMettingListBeans.get(position).setNo_sign(1);
                        mMyMeetingAdapter.notifyItemChanged(position, "sign");
                        ToastUtils.showShort(msg);
                    }
                });
    }
}

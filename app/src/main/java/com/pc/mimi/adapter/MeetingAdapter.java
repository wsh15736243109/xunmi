package com.pc.mimi.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pc.mimi.bean.MettingListBean;
import com.pc.mimi.util.ImageLoaderUtils;
import com.pc.mimi.widget.dialogfragment.PhotoInfo;
import com.xlyuns.xunmi.R;
import com.pc.mimi.widget.mettingview.CircleItemClickListenerCallBack;
import com.pc.mimi.widget.mettingview.CommentConfig;
import com.pc.mimi.widget.mettingview.CommentDialog;
import com.pc.mimi.widget.mettingview.CommentItem;
import com.pc.mimi.widget.mettingview.CommentListView;
import com.pc.mimi.widget.mettingview.MultiImageView;

import java.util.ArrayList;
import java.util.List;

public class MeetingAdapter extends BaseQuickAdapter<MettingListBean, BaseViewHolder> {

    private CircleItemClickListenerCallBack mCallBack;

    public MeetingAdapter(@Nullable List<MettingListBean> data) {
        super(R.layout.adapter_layout_item_metting, data);
    }

    public CircleItemClickListenerCallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(CircleItemClickListenerCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder helper, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(helper, position);
        } else {
            helper.setGone(R.id.comment_layout, getItem(position - getHeaderLayoutCount()).hasComments());
            helper.setGone(R.id.multiply_view, getItem(position - getHeaderLayoutCount()).hasPictures());
            helper.setImageResource(R.id.img_zan,
                    getItem(position - getHeaderLayoutCount()).getIs_good_for_this() == 0 ? R.drawable.ic_zan : R.drawable.ic_zan_true);
            helper.setText(R.id.good_count, getItem(position - getHeaderLayoutCount()).getGood_count() + "");
            helper.setText(R.id.sign_up_count, "报名(" + getItem(position - getHeaderLayoutCount()).getSign_up_count() + ")");
            if (getItem(position - getHeaderLayoutCount()).hasComments()) {
                CommentListView commentListView = helper.getView(R.id.commentList);
                List<CommentItem> commentItemList = new ArrayList<>();
                for (MettingListBean.ChatList chatList : getItem(position - getHeaderLayoutCount()).getChat_list()) {
                    CommentItem c = new CommentItem();
                    c.setId(chatList.getAll_dating_argument_id());
                    c.setIs_single(chatList.getIs_single());
                    c.setToReplyUserName(chatList.getIn_nick_name());
                    c.setUserName(chatList.getOut_nick_name());
                    c.setContent(chatList.getContent());
                    commentItemList.add(c);
                }
                commentListView.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int commentPosition) {
                        CommentItem commentItem = commentItemList.get(commentPosition);
//                    if (DatasUtil.curUser.getId().equals(commentItem.getUser().getId())) {//复制或者删除自己的评论
//                        CommentDialog dialog = new CommentDialog(mContext, mCallBack,
//                                commentItem,
//                                helper.getLayoutPosition());
//                        dialog.show();
//                    } else {//回复别人的评论
//                        if (mCallBack != null) {
//                            CommentConfig config = new CommentConfig();
//                            config.circlePosition = helper.getLayoutPosition();
//                            config.commentPosition = commentPosition;
//                            config.commentType = CommentConfig.Type.REPLY;
//                            config.replyUserName = commentItem.getToReplyUserName();
//                            mCallBack.showEditTextBody(config);
//                        }
//                    }
                        if (mCallBack != null) {
                            CommentConfig config = new CommentConfig();
                            config.circlePosition = helper.getLayoutPosition();
                            config.commentPosition = commentPosition;
                            config.commentType = CommentConfig.Type.REPLY;
                            config.replyUserName = commentItem.getToReplyUserName();
                            config.agrument_id = commentItem.getId();
                            mCallBack.showEditTextBody(config);
                        }
                    }

                    @Override
                    public void onClickCommentName(String name, String id) {
                        if (mCallBack != null) {
                            mCallBack.onClickCommentName(name, id);
                        }
                    }
                });
                commentListView.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(int commentPosition) {
                        //长按进行复制或者删除
                        CommentItem commentItem = commentItemList.get(commentPosition);
                        CommentDialog dialog = new CommentDialog(mContext, mCallBack, commentItem,
                                helper.getLayoutPosition());
                        dialog.show();
                    }
                });
                commentListView.setDatas(commentItemList);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected void convert(BaseViewHolder helper, MettingListBean item) {

        ImageLoaderUtils.loadImage(helper.getView(R.id.img), item.getAvatar());
        helper.setText(R.id.addtime, item.getAddtime());
        helper.setText(R.id.nick_name, item.getNick_name());
        helper.setText(R.id.age, item.getAge() + "");
        helper.setText(R.id.dating_type, item.getDating_type());
        helper.setText(R.id.dating_remarks, item.getDating_remarks());
        helper.setText(R.id.good_count, item.getGood_count() + "");
        helper.setText(R.id.sign_up_count, "报名(" + item.getSign_up_count() + ")");
        helper.setText(R.id.good_count, item.getGood_count() + "");
        helper.setText(R.id.dating_hope, "约会期望:" + item.getDating_hope());
        helper.setText(R.id.start_time, item.getStart_time() + "/" + item.getEnd_time() + ", " + item.getDating_citys());
        helper.setImageResource(R.id.img_sex, item.getGender() == 0 ? R.drawable.ic_girl_simbal : R.drawable.ic_boy_simbal);
        if (item.getGender() == 0) {//女
            if (item.getIs_authenticated() == 1) {
                helper.setImageResource(R.id.is_vip, R.drawable.ic_real);
            } else {
                helper.setGone(R.id.is_vip, false);
            }
        } else {//男
            if (item.getIs_vip() == 1) {
                helper.setImageResource(R.id.is_vip, R.drawable.ic_vip);
            } else {
                helper.setGone(R.id.is_vip, false);
            }
        }
        helper.setImageResource(R.id.img_zan, item.getIs_good_for_this() == 0 ? R.drawable.ic_zan : R.drawable.ic_zan_true);
        helper.addOnClickListener(R.id.thumbs_up);
        helper.addOnClickListener(R.id.comment);
        helper.addOnClickListener(R.id.sign_up);
        helper.addOnClickListener(R.id.report);
        helper.setGone(R.id.comment_layout, item.hasComments());
        helper.setGone(R.id.multiply_view, item.hasPictures());
        if (item.hasComments()) {
            CommentListView commentListView = helper.getView(R.id.commentList);
            List<CommentItem> commentItemList = new ArrayList<>();
            for (MettingListBean.ChatList chatList : item.getChat_list()) {
                CommentItem c = new CommentItem();
                c.setId(chatList.getAll_dating_argument_id());
                c.setIs_single(chatList.getIs_single());
                c.setToReplyUserName(chatList.getIn_nick_name());
                c.setUserName(chatList.getOut_nick_name());
                c.setContent(chatList.getContent());
                commentItemList.add(c);
            }
            commentListView.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                @Override
                public void onItemClick(int commentPosition) {
                    CommentItem commentItem = commentItemList.get(commentPosition);
//                    if (DatasUtil.curUser.getId().equals(commentItem.getUser().getId())) {//复制或者删除自己的评论
//                        CommentDialog dialog = new CommentDialog(mContext, mCallBack,
//                                commentItem,
//                                helper.getLayoutPosition());
//                        dialog.show();
//                    } else {//回复别人的评论
//                        if (mCallBack != null) {
//                            CommentConfig config = new CommentConfig();
//                            config.circlePosition = helper.getLayoutPosition();
//                            config.commentPosition = commentPosition;
//                            config.commentType = CommentConfig.Type.REPLY;
//                            config.replyUserName = commentItem.getToReplyUserName();
//                            mCallBack.showEditTextBody(config);
//                        }
//                    }
                    if (mCallBack != null) {
                        CommentConfig config = new CommentConfig();
                        config.circlePosition = helper.getLayoutPosition();
                        config.commentPosition = commentPosition;
                        config.commentType = CommentConfig.Type.REPLY;
                        config.replyUserName = commentItem.getToReplyUserName();
                        config.agrument_id = commentItem.getId();
                        mCallBack.showEditTextBody(config);
                    }
                }

                @Override
                public void onClickCommentName(String name, String id) {
                    if (mCallBack != null) {
                        mCallBack.onClickCommentName(name, id);
                    }
                }
            });
            commentListView.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
                @Override
                public void onItemLongClick(int commentPosition) {
                    //长按进行复制或者删除
                    CommentItem commentItem = commentItemList.get(commentPosition);
                    CommentDialog dialog = new CommentDialog(mContext, mCallBack, commentItem,
                            helper.getLayoutPosition());
                    dialog.show();
                }
            });
            commentListView.setDatas(commentItemList);
        }
        if (item.hasPictures()) {
            MultiImageView multiImageView = helper.getView(R.id.multiply_view);
            List<PhotoInfo> photoInfos = new ArrayList<>();
            for (MettingListBean.UserPictureBean userPictureBean : item.getUser_picture()) {
                PhotoInfo ph = new PhotoInfo();
                ph.url = userPictureBean.getImg_url();
                photoInfos.add(ph);
            }
            multiImageView.setList(photoInfos);
            multiImageView.setOnItemClickListener(((view, position) -> {
                mCallBack.preViewPic(view, position, photoInfos);
            }));
        }
    }

}

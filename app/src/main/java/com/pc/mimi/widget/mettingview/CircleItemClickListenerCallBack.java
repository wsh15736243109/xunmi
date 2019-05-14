package com.pc.mimi.widget.mettingview;

import android.view.View;

import com.pc.mimi.widget.dialogfragment.PhotoInfo;

import java.util.List;

public interface CircleItemClickListenerCallBack {
    void deleteCircle(String circleId);

    void deleteComment(int circlePosition, String commentItemId);

    void showEditTextBody(CommentConfig config);

    void addFavort(int circlePosition);

    void deleteFavort(int circlePosition, String favorId);

    void clickName(String userName, String userId);

    void onClickCommentName(String name, String id);

    void preViewPic(View view, int position, List<PhotoInfo> photoInfos);
}

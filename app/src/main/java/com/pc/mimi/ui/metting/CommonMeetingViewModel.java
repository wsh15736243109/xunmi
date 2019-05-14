package com.pc.mimi.ui.metting;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.pc.mimi.bean.CommentReturnBean;
import com.xlyuns.xunmi.R;
import com.pc.mimi.bean.MettingListBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.util.KeyboardUtil;
import com.pc.mimi.widget.CustomDialog;

import java.io.File;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CommonMeetingViewModel extends BaseViewModel {
    public CommonMeetingViewModel(@NonNull Application application) {
        super(application);
    }

    //编辑信息的弹出框
    CustomDialog mEditDialog;
    public List<MettingListBean> mMettingListBeans;

    /**
     * 点赞
     *
     * @param all_dating_id 约会id
     * @param isUp          点赞,false取消
     */
    public void requestThumbUp(String all_dating_id, int position, boolean isUp) {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestThumbUp(AppUtils.getUser().getUser_id(), all_dating_id),
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
                        dealThumbUp(position, isUp);
                    }
                });
    }

    EditText editText;
    Handler mHandler = new Handler();

    //弹出品论框
    public void showEditDialog(int position, int commentPoi, String all_dating_id, String all_dating_id2, boolean isReply) {
        invokeEventAction(context -> {
            if (mEditDialog != null) {
                mEditDialog = null;
            }
            mEditDialog = new CustomDialog(R.style.FullScreenWaitDialog, context, R.layout.dialog_edit_text, true, true,
                    Gravity.BOTTOM, content -> {
                editText = content.findViewById(R.id.et_content);
                if (isReply) {
                    editText.setHint("回复:" + mMettingListBeans.get(position).getChat_list().get(commentPoi).getOut_nick_name());
                } else {
                    editText.setHint("评论:");
                }
                content.findViewById(R.id.btn_commit).setOnClickListener(view -> {
                    if (TextUtils.isEmpty(editText.getText().toString())) {
                        ToastUtils.showShort("请输入");
                        return;
                    }
                    //TODO 发布评论
                    mEditDialog.dismiss();
                    doPublishComment(position, commentPoi, all_dating_id, editText.getText().toString().trim(), all_dating_id2, isReply);
                });
            }, true);
            mEditDialog.setOnDismissListener(dialog -> {
                if (KeyboardUtil.isSoftShowing((Activity) context))
                    KeyboardUtil.hideKeyBoard((Activity) context);
            });
            mEditDialog.show();
            mHandler.postDelayed(() -> openInputWindow(context), 450);
        });

    }

    /**
     * 报名
     *
     * @param all_dating_id 约会id
     * @param url           图片链接.
     */
    public void requestSignUp(String all_dating_id, String url) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
        File file = new File(url);
        RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), file);//表单类型
        KLog.d("-------------getUser_id-----------------" + AppUtils.getUser().getUser_id());
        builder.addFormDataPart("user_id", AppUtils.getUser().getUser_id());//传入服务器需要的key，和相应value值
        builder.addFormDataPart("files", file.getName(), body); //添加图片数据，body创建的请求体
        List<MultipartBody.Part> parts = builder.build().parts();
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).uploadImage(parts)
                        .map(stringBaseResponse -> stringBaseResponse.getData())
                        .flatMap(s -> RetrofitClient.getInstance().create(API.class).requestSignUp(AppUtils.getUser().getUser_id(), all_dating_id, s)),
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
                        ToastUtils.showShort(msg);
                    }
                });
    }

    /**
     * 发布约会评论
     *
     * @param all_dating_id 约会id
     * @param content       评论内容
     * @param isReply       是否是回复
     */
    private void doPublishComment(int position, int commentPoi, String all_dating_id, String content, String all_dating_id2, boolean isReply) {
        if (isReply) {
            AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestReComment(AppUtils.getUser().getUser_id(),
                    all_dating_id,
                    all_dating_id2,
                    content),
                    getLifecycleProvider(), disposable -> {
                    },
                    new ApiDisposableObserver<CommentReturnBean>() {
                        @Override
                        public void onResult(CommentReturnBean o) {
                            dealReComment(position, commentPoi, content, o);

                        }

                        @Override
                        public void dialogDismiss() {
                        }

                    });
        } else {
            AppUtils.requestData(RetrofitClient.getInstance().create(API.class).requestComment(AppUtils.getUser().getUser_id(), all_dating_id, content),
                    getLifecycleProvider(), disposable -> {
                    },
                    new ApiDisposableObserver<CommentReturnBean>() {
                        @Override
                        public void onResult(CommentReturnBean o) {
                            dealPubslishComment(position, content, o);

                        }

                        @Override
                        public void dialogDismiss() {
                        }

                    });
        }
    }

    /**
     * 处理回复评论
     *
     * @param position
     * @param commentPoi
     * @param content
     * @param o
     */
    protected void dealReComment(int position, int commentPoi, String content, CommentReturnBean o) {

    }

    /**
     * 处理发布评论
     *
     * @param position
     * @param content
     * @param commentReturnBean
     */
    protected void dealPubslishComment(int position, String content, CommentReturnBean commentReturnBean) {

    }

    private void openInputWindow(Context context) {
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    /**
     * 处理点赞
     *
     * @param position
     * @param isUp
     */
    protected void dealThumbUp(int position, boolean isUp) {

    }
}

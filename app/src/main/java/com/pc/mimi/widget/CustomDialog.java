package com.pc.mimi.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xlyuns.xunmi.R;

/**
 * mSelectPro = new CustomDialog(EditUserInfoActivity.this, R.layout.dialog_choose_pic,
 * true, true, Gravity.BOTTOM,
 * content -> {},true);
 * mSelectPro.show();
 */
public class CustomDialog extends Dialog {
    private boolean isCancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private boolean isMaxWidth;//是否是屏幕最大宽度
    private View view;
    private Context mContext;
    private int mPoi;


    /**
     * @param context           上下文
     * @param view              内容区
     * @param iscancelable      点击外部不可dismiss
     * @param isBackCancelable  返回键取消
     * @param poi               位于屏幕的位置  Gravity.CENTER
     * @param contentClickEvent 事件回调
     */
    public CustomDialog(@NonNull Context context, View view, boolean iscancelable, boolean isBackCancelable,
                        int poi, ContentClickEvent contentClickEvent) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.view = view;
        this.isCancelable = iscancelable;
        this.isBackCancelable = isBackCancelable;
        this.mPoi = poi;
        this.mContentClickEvent = contentClickEvent;
        this.isMaxWidth = false;
    }

    public CustomDialog(@NonNull Context context, int viewId, boolean iscancelable, boolean isOnTouchOutsideCancelable,
                        int poi, ContentClickEvent contentClickEvent, boolean isMaxWidth) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.view = View.inflate(context, viewId, null);
        this.isCancelable = iscancelable;
        this.isBackCancelable = isOnTouchOutsideCancelable;
        this.mPoi = poi;
        this.mContentClickEvent = contentClickEvent;
        this.isMaxWidth = isMaxWidth;
    }

    public CustomDialog(int style, @NonNull Context context, int viewId, boolean iscancelable, boolean isOnTouchOutsideCancelable,
                        int poi, ContentClickEvent contentClickEvent, boolean isMaxWidth) {
        super(context, style);
        this.mContext = context;
        this.view = View.inflate(context, viewId, null);
        this.isCancelable = iscancelable;
        this.isBackCancelable = isOnTouchOutsideCancelable;
        this.mPoi = poi;
        this.mContentClickEvent = contentClickEvent;
        this.isMaxWidth = isMaxWidth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(view);//这行一定要写在前面
        if (null != mContentClickEvent) {
            mContentClickEvent.contentClick(view);
        }
        setCanceledOnTouchOutside(isBackCancelable);
        setCancelable(isCancelable);
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        WindowManager.LayoutParams params = window.getAttributes();
        if (isMaxWidth) {
            params.width = dm.widthPixels;
        } else {
            params.width = dm.widthPixels * 4 / 5;
        }
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(mPoi);
        if (mPoi == Gravity.TOP) {
            window.setWindowAnimations(R.style.CustomDialogAnima_top);//设置动画效果
        } else
            window.setWindowAnimations(R.style.CustomDialogAnima);//设置动画效果
    }

    private void closeStatusbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    public interface ContentClickEvent {
        void contentClick(View content);

    }

    public ContentClickEvent mContentClickEvent;

}

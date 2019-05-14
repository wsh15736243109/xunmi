package com.pc.mimi.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.pc.mimi.util.CommonUtil;
import com.xlyuns.xunmi.R;

/**
 * create by cm
 * date 2018/12/21 0021
 * desc 透明状态栏<19高度修正
 */
public class StatusView extends LinearLayout {


    public StatusView(Context context) {
        super(context);
        init(context);
    }

    public StatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (Build.VERSION.SDK_INT > 18) {
            LayoutInflater.from(context).inflate(R.layout.view_status, this, true);
            View view = findViewById(R.id.v);
//            view.setBackgroundResource(R.drawable.bg_rect_theme_bar);
            int result;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            } else {
                result = CommonUtil.pix2dip(context, 25);
            }
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.width = CommonUtil.getScreenWidth(context);
            lp.height = result;
            view.setLayoutParams(lp);
        }
    }
}

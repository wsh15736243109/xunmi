package com.pc.mimi.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.pc.mimi.widget.roundedimageview.RoundedImageView;



public class ChatSquareImageView extends RoundedImageView {
    public ChatSquareImageView(Context context) {
        super(context);
    }

    public ChatSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatSquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * 1.5);
        setMeasuredDimension(width, height);
    }
}

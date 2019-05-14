package com.pc.mimi.binding.textview;

import android.databinding.BindingAdapter;
import android.widget.TextView;


public class ViewAdapter {


    @SuppressWarnings("unchecked")
    @BindingAdapter({"setBackground"})
    public static void setBackground(final TextView textView, final int resId) {
        textView.setBackgroundResource(resId);
    }
}
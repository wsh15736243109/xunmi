package com.pc.mimi.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class CustomLinerLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnable = true;

    public CustomLinerLayoutManager(Context context) {
        super(context);
    }

    public CustomLinerLayoutManager(Context context, int orientation) {
        super(context, orientation,false);
    }

    @Override
    public boolean canScrollHorizontally() {
        return isScrollEnable && super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnable && super.canScrollVertically();
    }

    public void setScrollEnable(boolean scrollEnable) {
        isScrollEnable = scrollEnable;
    }
}

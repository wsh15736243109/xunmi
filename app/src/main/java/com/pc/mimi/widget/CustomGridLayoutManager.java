package com.pc.mimi.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

public class CustomGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnable = true;

    public CustomGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public CustomGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
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

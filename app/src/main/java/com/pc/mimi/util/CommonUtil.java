package com.pc.mimi.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.TextView;

import com.luck.picture.lib.tools.ScreenUtils;
import com.xlyuns.xunmi.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class CommonUtil {
    public static void initTitleBar(Activity activity) {
//        if (activity.findViewById(R.id.toolbar) != null) {
//            if (activity instanceof AppCompatActivity) {
//                ((AppCompatActivity) activity).setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
//                ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
//            } else {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
//                    activity.getActionBar().setDisplayShowTitleEnabled(false);
//                }
//            }
//        }
        if (activity.findViewById(R.id.toolbar_title) != null) {
            ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
        }
        if (activity.findViewById(R.id.toolbar_back) != null) {
            activity.findViewById(R.id.toolbar_back).setOnClickListener(view -> activity.onBackPressed());
        }
    }

    /**
     * @param nestedScrollView NestedScrollView
     * @param height           滚动发生变化的高度 CommonUtil.getScreenHeidth(this) / 5;
     * @param views            随滑动变化的view数组
     */
    public static void setAlpha(NestedScrollView nestedScrollView, int height, onTextColor onTextColor, Object... views) {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            // 将透明度声明成局部变量用于判断
            int alpha = 0;
            int count = 0;
            float scale = 0;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY <= height) {
                    scale = (float) scrollY / height;
                    alpha = (int) (255 * scale);
                    // 随着滑动距离改变透明度
                    // KLog.e("al=","="+alpha);
                    if (onTextColor != null)
                        onTextColor.change(alpha);
                    for (Object object :
                            views) {
                        if (object instanceof ViewGroup) {
                            ((ViewGroup) object).setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                        } else {
                            ((View) object).setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                        }
                    }
                } else {
                    if (alpha < 255) {
//                        KLog.e("执行次数", "=" + (++count));
                        // 防止频繁重复设置相同的值影响性能
                        alpha = 255;
                        for (Object object : views) {
                            if (object instanceof View) {
                                ((View) object).setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                            } else {
                                ((ViewGroup) object).setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                            }
                        }
                        if (onTextColor != null)
                            onTextColor.changeEnd(alpha);
                    }
                }

            }
        });
    }

    public interface onTextColor {
        void change(int alpha);

        void changeEnd(int alpha);
    }

    public interface onSlide {
        void onSlideUp();

        void onSlideDown();
    }

    /**
     * @param recyclerView  recyclerView
     * @param layoutManager recyclerView管理器
     * @param headerHeight  recyclerView的header高度 CommonUtil.getViewHeight(view,true)
     * @param height        recyclerView滑动变化距离 CommonUtil.getScreenHeidth(this) / 5;
     * @param onSlide       上下滑回调
     * @param views         颜色变化的view数组
     */
    public static void setRecyclerAlph(RecyclerView recyclerView, LinearLayoutManager layoutManager,
                                       int headerHeight, int height, onSlide onSlide, Object... views) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            float scale = 0;
            int alpha = 0;
            int count = 0;
            int distance = 0;
            int event = 0;

            /**
             * 获取滑动的总距离
             *
             * @return
             */
            private int getScollYDistance(int headerHeight) {
                int position = layoutManager.findFirstVisibleItemPosition();
                View firstVisiableChildView = layoutManager.findViewByPosition(position);
                int firstVisiableChildViewTop = 0;
                firstVisiableChildViewTop = firstVisiableChildView.getTop();
                if (position > 0) {
                    firstVisiableChildViewTop = firstVisiableChildViewTop - headerHeight;
                }
//                KLog.e("firstVisiableChildViewTop--->", firstVisiableChildViewTop);
                int itemHeight = firstVisiableChildView.getHeight();
                //第一个可见的item*item高度-最顶端位置
                return (position) * itemHeight - firstVisiableChildViewTop;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int newEvent;
                if (getScollYDistance(headerHeight) > distance) {
                    newEvent = 0;
                } else {
                    newEvent = 1;
                }
                if (newEvent < event) {
                    onSlide.onSlideUp();
                } else if (newEvent > event) {
                    onSlide.onSlideDown();
                }
                event = newEvent;
                distance = getScollYDistance(headerHeight);
//                KLog.e("getScollYDistance", getScollYDistance(headerHeight) + "----" + headerHeight);
                if (getScollYDistance(headerHeight) <= height) {
                    scale = (float) getScollYDistance(headerHeight) / height;
                    alpha = (int) (255 * scale);
                    // 随着滑动距离改变透明度
                    // KLog.e("al=","="+alpha);
                    for (Object object :
                            views) {
                        if (object instanceof View) {
                            ((View) object).setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                        } else {
                            ((ViewGroup) object).setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                        }
                    }
                } else {
                    if (alpha < 255) {
//                        KLog.e("执行次数", "=" + (++count));
                        // 防止频繁重复设置相同的值影响性能
                        alpha = 255;
                        for (Object object :
                                views) {
                            if (object instanceof View) {
                                ((View) object).setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                            } else {
                                ((ViewGroup) object).setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                            }
                        }
                    }
                }
            }
        });

    }

    /**
     * Flag只有在使用了FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
     * 并且没有使用 FLAG_TRANSLUCENT_STATUS的时候才有效，也就是只有在状态栏全透明的时候才有效。
     *
     * @param activity
     * @param bDark
     */
    public static void setStatusBarMode(Activity activity, boolean bDark) {
        //6.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    /**
     * 把状态栏设成透明
     */
    public static void setStatusBarTransparent(Activity activity, int top) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setOnApplyWindowInsetsListener((v, insets) -> {
                WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                return defaultInsets.replaceSystemWindowInsets(
                        defaultInsets.getSystemWindowInsetLeft(),
                        top,
                        defaultInsets.getSystemWindowInsetRight(),
                        defaultInsets.getSystemWindowInsetBottom());
            });
            ViewCompat.requestApplyInsets(decorView);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, android.R.color.transparent));
        }
    }

    /**
     * 验证手机号码
     *
     * @param mobileNumber 电话号码
     * @return true表是电话号码
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * dp 转 px
     *
     * @param context {@link Context}
     * @param dpValue {@code dpValue}
     * @return {@code pxValue}
     */
    public static int dip2px(@NonNull Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param context {@link Context}
     * @param pxValue {@code pxValue}
     * @return {@code dpValue}
     */
    public static int pix2dip(@NonNull Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param context {@link Context}
     * @param spValue {@code spValue}
     * @return {@code pxValue}
     */
    public static int sp2px(@NonNull Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param context {@link Context}
     * @param pxValue {@code pxValue}
     * @return {@code spValue}
     */
    public static int px2sp(@NonNull Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    public static int getScreenHeidth(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取控件的高度或者宽度  isHeight=true则为测量该控件的高度，isHeight=false则为测量该控件的宽度
     *
     * @param view
     * @param isHeight
     * @return
     */
    public static int getViewHeight(View view, boolean isHeight) {
        int result;
        if (view == null) return 0;
        if (isHeight) {
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(h, 0);
            result = view.getMeasuredHeight();
        } else {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(0, w);
            result = view.getMeasuredWidth();
        }
        return result;
    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    public static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }
}

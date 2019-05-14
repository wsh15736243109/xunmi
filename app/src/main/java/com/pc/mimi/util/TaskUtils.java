package com.pc.mimi.util;

import android.os.Handler;
import android.util.Log;

/**
 * 定时任务
 * mTaskUtils = new TaskUtils(2000, () -> {
 * ToastUtils.showShort("do something");
 * });
 * mTaskUtils.start();
 * -----------------------------------------------------
 * mTaskUtils.remove();
 */
public class TaskUtils {
    private int timer;
    private CallBack mCallBack;

    public TaskUtils(int timer, CallBack callBack) {
        this.timer = timer;
        mCallBack = callBack;
    }

    Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //TODO 刷新
            Log.d("------TIME--TASK-----", "-------------TIME--TASK--------DO---------");
            if (mCallBack != null) {
                mCallBack.todoSomeThing();
            }
            mHandler.postDelayed(this, timer); //timer时间刷新一次
        }
    };

    public void start() {
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, timer);
    }

    public void remove() {
        mHandler.removeCallbacks(mRunnable);
    }

    public interface CallBack {
        void todoSomeThing();
    }

}

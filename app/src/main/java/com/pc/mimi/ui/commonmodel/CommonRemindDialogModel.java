package com.pc.mimi.ui.commonmodel;

import android.app.Application;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.xlyuns.xunmi.R;
import com.pc.mimi.util.inject.FindView;
import com.pc.mimi.util.inject.Init;
import com.pc.mimi.util.inject.OnClick;
import com.pc.mimi.util.inject.ViewUtils;
import com.pc.mimi.widget.CustomDialog;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class CommonRemindDialogModel extends BaseViewModel {
    public CommonRemindDialogModel(@NonNull Application application) {
        super(application);
    }

    public CustomDialog mRemindDialog;
    public RemindDialogEvent mRemindDialogEvent;

    public void showDialog(String cancel, String confir, String content) {
        invokeEventAction(context -> {
            if (mRemindDialog != null) mRemindDialog = null;
            if (mRemindDialogEvent != null) mRemindDialogEvent = null;
            mRemindDialogEvent = new RemindDialogEvent(cancel, confir, content);
            mRemindDialog = new CustomDialog(context, R.layout.dialog_remind, true, true, Gravity.CENTER,
                    c -> ViewUtils.inject(c, mRemindDialogEvent), false);
            mRemindDialog.show();
        });
    }

    private class RemindDialogEvent {
        @FindView(R.id.text_content)
        TextView text_content;
        @FindView(R.id.cancel)
        TextView mCancel;
        @FindView(R.id.confir)
        TextView mConfir;
        String cancel = "取消";
        String confir = "确认";
        String content = "";

        private RemindDialogEvent(String cancel, String confir, String content) {
            this.cancel = cancel;
            this.confir = confir;
            this.content = content;
        }

        @Init
        void initViews() {
            mCancel.setText(cancel);
            mConfir.setText(confir);
            text_content.setText(content);
        }

        @OnClick({R.id.cancel, R.id.confir})
        void onClicked(View view) {
            switch (view.getId()) {
                case R.id.cancel:
                    mRemindDialog.dismiss();
                    break;
                case R.id.confir:
                    mRemindDialog.dismiss();
                    remindConfir();
                    break;
            }
        }
    }

    protected void remindConfir() {
    }

}

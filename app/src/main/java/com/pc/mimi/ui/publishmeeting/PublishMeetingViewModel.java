package com.pc.mimi.ui.publishmeeting;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.pc.mimi.adapter.GridImageAdapter;
import com.pc.mimi.adapter.MettingSelectAdapter;
import com.pc.mimi.bean.WishProgramBean;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.util.DateUtil;
import com.pc.mimi.util.KeyboardUtil;
import com.xlyuns.xunmi.R;
import com.pc.mimi.adapter.AreaSelectAdapter;
import com.pc.mimi.bean.AreaBean;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.util.inject.FindView;
import com.pc.mimi.util.inject.Init;
import com.pc.mimi.util.inject.OnClick;
import com.pc.mimi.util.inject.ViewUtils;
import com.pc.mimi.widget.CustomDialog;
import com.pc.mimi.widget.LabelsView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PublishMeetingViewModel extends BaseViewModel {
    public PublishMeetingViewModel(@NonNull Application application) {
        super(application);
    }

    GridImageAdapter mGridImageAdapter;
    List<LocalMedia> mStringList;
    //    约会形式
    public String mdatingContent = "";

    //约会期望-->值
    public ObservableField<List<String>> meetingWishData = new ObservableField<>();
    //约会城市-->值
    public ObservableField<String> meetingCityText = new ObservableField<>("请选择");
    //约会日期
    public ObservableField<String> meetingDateText = new ObservableField<>("约会日期");
    //约会时间
    public ObservableField<String> meetingTimeText = new ObservableField<>("约会时间");
    //补充说明
    public ObservableField<String> otherSome = new ObservableField<>("");

    public CustomDialog mSelectDialog = null;
    public MettingSelectDialogEvent mMettingSelectDialogEvent = null;

    AreaSelectAdapter mCitySelectAdapter;
    AreaSelectAdapter mAreaSelectAdapter;

    List<AreaBean> mCityBeans;
    List<AreaBean> mAreaBeans;

    UIChangeObser uc = new UIChangeObser();

    class UIChangeObser {
        public ObservableBoolean mettingCity = new ObservableBoolean(false);
        public ObservableBoolean firstdef = new ObservableBoolean(false);
    }

    private int hiding_to_gender = 0;
    private int isCandiscuss = 0;

    //处理图片选择后的结果
    public void dealResult(Intent data) {
        KLog.d("------------------dealResult-------------------");
        // 图片、视频、音频选择结果回调
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        // 例如 LocalMedia 里面返回三种path
        // 1.media.getPath(); 为原图path
        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
        //上传图片
        setPicChoose(selectList);
    }

    private void setPicChoose(List<LocalMedia> localMedias) {
        mStringList.clear();
        mStringList.addAll(localMedias);
        mGridImageAdapter.notifyDataSetChanged();
    }


    //约会期望
    public BindingCommand mettingWishSelect = new BindingCommand(() ->
            invokeEventAction(context -> {
                if (mSelectDialog != null) mSelectDialog = null;
                if (mMettingSelectDialogEvent != null) mMettingSelectDialogEvent = null;
                mMettingSelectDialogEvent = new MettingSelectDialogEvent(context);
                mSelectDialog = new CustomDialog(context, R.layout.dialog_metting_select, true, true, Gravity.BOTTOM,
                        content -> ViewUtils.inject(content, mMettingSelectDialogEvent), true);
                mSelectDialog.show();
            })
    );

    class MettingSelectDialogEvent {
        private Context mContext;

        public MettingSelectDialogEvent(Context context) {
            mContext = context;
        }

        @FindView(R.id.recycler)
        RecyclerView mRecyclerView;
        @FindView(R.id.flow)
        LabelsView mLabelsView;
        @FindView(R.id.text_middle)
        TextView text_middle;
        MettingSelectAdapter mMettingSelectAdapter;
        List<WishProgramBean> mStringList;
        List<String> mLabels = new ArrayList<>();

        @Init
        private void initViews() {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mStringList = new ArrayList<>();
            text_middle.setText("约会期望");
            mStringList.addAll(AppUtils.getMettingWishListData());
            mMettingSelectAdapter = new MettingSelectAdapter(mStringList);
            mMettingSelectAdapter.bindToRecyclerView(mRecyclerView);
            mMettingSelectAdapter.setOnItemClickListener(((adapter, view, position) -> {
                mStringList.get(position).setSelected(!mStringList.get(position).isSelected());
                mMettingSelectAdapter.notifyDataSetChanged();
                mLabels.clear();
                for (WishProgramBean w : mStringList) {
                    if (w.isSelected()) {
                        mLabels.add(w.getName());
                    }
                }
                mLabelsView.setLabels(mLabels);
            }));
        }

        @OnClick({R.id.cancel, R.id.confir})
        public void onClicked(View view) {
            mSelectDialog.dismiss();
            switch (view.getId()) {
                case R.id.cancel:
                    break;
                case R.id.confir:
                    meetingWishData.set(mLabels);
                    break;
            }
        }
    }

    //约会城市
    public BindingCommand mettingCity = new BindingCommand(() -> uc.mettingCity.set(!uc.mettingCity.get()));
    //对通性别用户隐藏
    public BindingCommand<Boolean> isMissSelf = new BindingCommand<Boolean>(aBoolean -> {
        hiding_to_gender = aBoolean ? 1 : 0;
        KLog.d("hiding_to_gender", hiding_to_gender + "");
    });
    //禁止评论
    public BindingCommand<Boolean> isCanDisscuss = new BindingCommand<Boolean>(aBoolean -> {
        isCandiscuss = aBoolean ? 1 : 0;
        KLog.d("isCandiscuss", isCandiscuss + "");
    });

    /**
     * 请求区域列表
     */
    public void loadAreaList(String city_id) {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).getAreaList(AppUtils.getUser().getUser_id(), city_id),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<List<AreaBean>>() {
                    @Override
                    public void onResult(List<AreaBean> o) {
                        mAreaBeans.clear();
                        mAreaBeans.addAll(o);
                        mAreaSelectAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }

    /**
     * 请求城市列表
     */
    public void getCityList() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).getCityList(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<List<AreaBean>>() {
                    @Override
                    public void onResult(List<AreaBean> o) {
                        mCityBeans.clear();
                        mCityBeans.addAll(o);
                        //默认选中第一项
                        if (mCityBeans.size() > 0)
                            mCityBeans.get(0).setSelected(true);
                        uc.firstdef.set(!uc.firstdef.get());
                        mCitySelectAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }
                });
    }

    //选择日期
    public BindingCommand mettingDate = new BindingCommand(() -> {
        invokeEventAction(context -> {
            if (KeyboardUtil.isSoftShowing((Activity) context)) {
                KeyboardUtil.hideKeyBoard((Activity) context);
            }
            TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    meetingDateText.set(DateUtil.DateToStringYMD(date) + "");
                }
            }).setType(new boolean[]{true, true, true, false, false, false}).build();
            pvTime.show();
        });
    });
    //选择时间
    public BindingCommand mettingTime = new BindingCommand(() -> {
        invokeEventAction(context -> {
            if (KeyboardUtil.isSoftShowing((Activity) context)) {
                KeyboardUtil.hideKeyBoard((Activity) context);
            }
            TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    meetingTimeText.set(DateUtil.DateToStringHMS(date) + "");
                }
            }).setType(new boolean[]{false, false, false, true, true, false}).build();
            pvTime.show();
        });
    });

    //发布
    public BindingCommand publish = new BindingCommand(() -> {
        if (meetingWishData.get() == null || meetingWishData.get().size() == 0) {
            ToastUtils.showShort("请选择约会期望");
            return;
        }
        if (meetingCityText.get().equals("请选择")) {
            ToastUtils.showShort("请选择约会城市");
            return;
        }
        if (meetingDateText.get().equals("约会日期")) {
            ToastUtils.showShort("请选择开始日期");
            return;
        }
        if (meetingTimeText.get().equals("约会时间")) {
            ToastUtils.showShort("请选择开始时间");
            return;
        }
        if (TextUtils.isEmpty(otherSome.get())) {
            ToastUtils.showShort("请填写补充说明");
            return;
        }
        if (mStringList.size() > 0) {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
            KLog.d("-------------getUser_id-----------------" + AppUtils.getUser().getUser_id());
            builder.addFormDataPart("user_id", AppUtils.getUser().getUser_id());//传入服务器需要的key，和相应value值
            for (LocalMedia path : mStringList) {
                File file = new File(path.getCompressPath());
                RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), file);//表单类型
                builder.addFormDataPart("files", file.getName(), body); //添加图片数据，body创建的请求体
            }
            List<MultipartBody.Part> parts = builder.build().parts();
            AppUtils.requestData(RetrofitClient.getInstance().create(API.class).uploadImage(parts)
                            .map(stringBaseResponse -> stringBaseResponse.getData())
                            .flatMap(s -> {
                                KLog.d("picPath-->" + s);
                                StringBuffer stringBuffer = new StringBuffer();
                                for (String str : meetingWishData.get()) {
                                    stringBuffer.append(str);
                                    stringBuffer.append(",");
                                }
                                Map<String, Object> map = new HashMap<String, Object>() {{
                                    put("user_id", AppUtils.getUser().getUser_id());//user_id
                                    put("img_array", s);//图片地址
                                    put("dating_content", mdatingContent);//约会形式
                                    put("dating_hope", stringBuffer.toString());//约会期望
                                    put("dating_citys", meetingCityText.get());//约会城市
                                    put("start_time", meetingDateText.get() + " " + meetingTimeText.get());//开始日期时间
                                    put("dating_remarks", otherSome.get());//备注
                                    put("hiding_to_gender", hiding_to_gender);//对同性隐藏0关1开
                                    put("no_comment", isCandiscuss);//禁止评论0关1开
                                }};
                                return RetrofitClient.getInstance().create(API.class).publishMetting(map);
                            }), getLifecycleProvider(),
                    disposable -> showDialog(), new ApiDisposableObserver() {
                        @Override
                        public void onResult(Object o) {

                        }

                        @Override
                        public void dialogDismiss() {
                            dismissDialog();
                        }

                        @Override
                        public void onDataEmpty(String msg) {
                            ToastUtils.showShort("发布成功");
                            finish();
                        }
                    });
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            for (String str : meetingWishData.get()) {
                stringBuffer.append(str);
                stringBuffer.append(",");
            }
            Map<String, Object> map = new HashMap<String, Object>() {{
                put("user_id", AppUtils.getUser().getUser_id());//user_id
                put("dating_content", mdatingContent);//约会形式
                put("dating_hope", stringBuffer.toString());//约会期望
                put("dating_citys", meetingCityText.get());//约会城市
                put("start_time", meetingDateText.get() + " " + meetingTimeText.get());//开始日期时间
                put("dating_remarks", otherSome.get());//备注
                put("hiding_to_gender", hiding_to_gender);//对同性隐藏0关1开
                put("no_comment", isCandiscuss);//禁止评论0关1开
            }};
            AppUtils.requestData(RetrofitClient.getInstance().create(API.class).publishMetting(map), getLifecycleProvider(),
                    disposable -> showDialog(), new ApiDisposableObserver() {
                        @Override
                        public void onResult(Object o) {

                        }

                        @Override
                        public void dialogDismiss() {
                            dismissDialog();
                        }

                        @Override
                        public void onDataEmpty(String msg) {
                            ToastUtils.showShort("发布成功");
                            finish();
                        }
                    });
        }
    });

}

package com.pc.mimi.ui.edituserinfo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.pc.mimi.adapter.MettingSelectAdapter;
import com.pc.mimi.bean.UserInfo;
import com.pc.mimi.bean.WishProgramBean;
import com.pc.mimi.event.AreaEvent;
import com.pc.mimi.http.API;
import com.pc.mimi.http.ApiDisposableObserver;
import com.pc.mimi.http.RetrofitClient;
import com.pc.mimi.ui.forgetpassword.ForgetPasswordActivity;
import com.pc.mimi.util.AppUtils;
import com.pc.mimi.util.inject.FindView;
import com.pc.mimi.util.inject.Init;
import com.pc.mimi.util.inject.OnClick;
import com.pc.mimi.util.inject.ViewUtils;
import com.pc.mimi.widget.LabelsView;
import com.xlyuns.xunmi.R;
import com.pc.mimi.config.CommonTags;
import com.pc.mimi.ui.main.MainActivity;
import com.pc.mimi.ui.register.RegisterActivity;
import com.pc.mimi.ui.selectsex.SelectSexActivity;
import com.pc.mimi.widget.CustomDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditUserInfoViewModel extends BaseViewModel {
    public EditUserInfoViewModel(@NonNull Application application) {
        super(application);
    }

    public int mType = -1;
    public String user_id = "";

    private static final int SELECT_TYPE_AGE = 0;//年龄
    private static final int SELECT_TYPE_HIGHT = 1;//身高
    private static final int SELECT_TYPE_WEIGHT = 2;//体重
    private static final int SELECT_TYPE_BUST = 3;//胸围
    private static final int SELECT_TYPE_WISH = 4;//约会期望
    private static final int SELECT_TYPE_PROGRAM = 5;//约会节目
    private int hidingAccount = 0;

    /**
     * 右上角保存用户修改按钮点击事件
     */
    public void onSaveUserInfo() {

    }

    //是否隐藏社交账号
    public ObservableBoolean isHiddAccount = new ObservableBoolean(false);
    //是否显示胸围
    public ObservableInt isShowBust = new ObservableInt(View.VISIBLE);
    //用户头像
    public ObservableField<String> picProta = new ObservableField<>("");
    //用户昵称
    public ObservableField<String> userName = new ObservableField<>("");
    //用户年龄
    public ObservableField<String> ageText = new ObservableField<>("请选择");
    //用户约会范围
    public ObservableField<List<String>> areaLabelsData = new ObservableField<>();
    //用户职业
    public ObservableField<String> jobText = new ObservableField<>("请选择");
    //约会节目
    public ObservableField<List<String>> mettingProgram = new ObservableField<>();
    //约会期望
    public ObservableField<List<String>> mettingWish = new ObservableField<>();
    //qq
    public ObservableField<String> qqNum = new ObservableField<>("");
    //vx
    public ObservableField<String> vxNum = new ObservableField<>("");
    //身高
    public ObservableField<String> bodyHight = new ObservableField<>("请选择");
    //体重
    public ObservableField<String> bodyWeight = new ObservableField<>("请选择");
    //胸围
    public ObservableField<String> bodychest = new ObservableField<>("请选择");
    //个人介绍
    public ObservableField<String> personIntrduce = new ObservableField<>("");
    private CustomDialog mSelectDialog = null;
    private SelectDialogEvent mSelectDialogEvent = null;
    private MettingSelectDialogEvent mMettingSelectDialogEvent = null;
    UIChangeObser uc = new UIChangeObser();

    class UIChangeObser {
        public ObservableBoolean updateProta = new ObservableBoolean(false);
    }

    /**
     * 请求用户信息
     */
    public void requestUserInfo() {
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).see_before_info(AppUtils.getUser().getUser_id()),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<UserInfo>() {
                    @Override
                    public void onResult(UserInfo o) {
                        picProta.set(API.BASEURL + o.getAvatar());
                        userName.set(o.getNick_name());
                        mType = o.getGender();
                        isShowBust.set(o.getGender() == 0 ? View.VISIBLE : View.GONE);
                        ageText.set(o.getAge() + "岁");
                        List<String> list = new ArrayList<>();
                        if (o.getDating_range().contains(",")) {
                            list.addAll(Arrays.asList(o.getDating_range().split(",")));
                        } else {
                            list.add(o.getDating_range());
                        }
                        areaLabelsData.set(list);

                        jobText.set(o.getJob());
                        List<String> list2 = new ArrayList<>();
                        if (o.getDating_show().contains(",")) {
                            list2.addAll(Arrays.asList(o.getDating_show().split(",")));
                        } else {
                            list2.add(o.getDating_show());
                        }
                        mettingProgram.set(list2);
                        List<String> list3 = new ArrayList<>();
                        if (o.getDating_expectations().contains(",")) {
                            list3.addAll(Arrays.asList(o.getDating_expectations().split(",")));
                        } else {
                            list3.add(o.getDating_expectations());
                        }
                        mettingWish.set(list3);
                        qqNum.set(o.getQq());
                        vxNum.set(o.getWechat());
                        bodyHight.set(o.getHeight() + "cm");
                        bodyWeight.set(o.getWeight() + "kg");
                        if (o.getBust() != null) {
                            bodychest.set(o.getBust());
                        }
                        personIntrduce.set(o.getMy_info());
                        isHiddAccount.set(o.getHiding_social_account().equals("0") ? false : true);
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                });
    }

    @Override
    public void onCreate() {
        Messenger.getDefault().register(this, CommonTags.SELECT_AREA, AreaEvent.class, areaEvent -> areaLabelsData.set(areaEvent.mStrings));
        Messenger.getDefault().register(this, CommonTags.SELECT_JOB, String.class, s -> jobText.set(s));
    }

    //选择职业
    public BindingCommand jobSelect = new BindingCommand(() -> {
        startActivity(JobSelectActivity.class);
    });
    //选择身高
    public BindingCommand heightSelect = new BindingCommand(() -> invokeEventAction(context -> {
        if (mSelectDialog != null) mSelectDialog = null;
        if (mSelectDialogEvent != null) mSelectDialogEvent = null;
        mSelectDialogEvent = new SelectDialogEvent(SELECT_TYPE_HIGHT);
        mSelectDialog = new CustomDialog(context, R.layout.dialog_age_select, true, true, Gravity.BOTTOM,
                content -> ViewUtils.inject(content, mSelectDialogEvent), true);
        mSelectDialog.show();
    }));
    //选择体重
    public BindingCommand weightSelect = new BindingCommand(() -> invokeEventAction(context -> {
        if (mSelectDialog != null) mSelectDialog = null;
        if (mSelectDialogEvent != null) mSelectDialogEvent = null;
        mSelectDialogEvent = new SelectDialogEvent(SELECT_TYPE_WEIGHT);
        mSelectDialog = new CustomDialog(context, R.layout.dialog_age_select, true, true, Gravity.BOTTOM,
                content -> ViewUtils.inject(content, mSelectDialogEvent), true);
        mSelectDialog.show();
    }));

    //胸围
    public BindingCommand bustSelect = new BindingCommand(() -> invokeEventAction(context -> {
        if (mSelectDialog != null) mSelectDialog = null;
        if (mSelectDialogEvent != null) mSelectDialogEvent = null;
        mSelectDialogEvent = new SelectDialogEvent(SELECT_TYPE_BUST);
        mSelectDialog = new CustomDialog(context, R.layout.dialog_age_select, true, true, Gravity.BOTTOM,
                content -> ViewUtils.inject(content, mSelectDialogEvent), true);
        mSelectDialog.show();
    }));
    //约会期望
    public BindingCommand wishSelect = new BindingCommand(() ->
            invokeEventAction(context -> {
                if (mSelectDialog != null) mSelectDialog = null;
                if (mMettingSelectDialogEvent != null)
                    mMettingSelectDialogEvent = null;
                mMettingSelectDialogEvent = new MettingSelectDialogEvent(SELECT_TYPE_WISH, context);
                mSelectDialog = new CustomDialog(context, R.layout.dialog_metting_select, true, true, Gravity.BOTTOM,
                        content -> ViewUtils.inject(content, mMettingSelectDialogEvent), true);
                mSelectDialog.show();
            })
    );
    //约会节目
    public BindingCommand programSelect = new BindingCommand(() ->
            invokeEventAction(context -> {
                        if (mSelectDialog != null) mSelectDialog = null;
                        if (mMettingSelectDialogEvent != null)
                            mMettingSelectDialogEvent = null;
                        mMettingSelectDialogEvent = new MettingSelectDialogEvent(SELECT_TYPE_PROGRAM, context);
                        mSelectDialog = new CustomDialog(context, R.layout.dialog_metting_select, true, true, Gravity.BOTTOM,
                                content -> ViewUtils.inject(content, mMettingSelectDialogEvent), true);
                        mSelectDialog.show();
                    }
            ));

    class MettingSelectDialogEvent {
        private int mType = -1;
        private Context mContext;

        public MettingSelectDialogEvent(int type, Context context) {
            mType = type;
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
            switch (mType) {
                case SELECT_TYPE_WISH:
                    text_middle.setText("约会期望");
                    mStringList.addAll(AppUtils.getMettingWishListData());
                    break;
                case SELECT_TYPE_PROGRAM:
                    text_middle.setText("约会节目");
                    mStringList.addAll(AppUtils.getMettingProgramListData());
                    break;
            }
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
                    switch (mType) {
                        case SELECT_TYPE_WISH:
                            mettingWish.set(mLabels);
                            break;
                        case SELECT_TYPE_PROGRAM:
                            mettingProgram.set(mLabels);
                            break;
                    }
                    break;
            }
        }
    }

    /**
     * 处理选择图片后的结果
     *
     * @param data
     */
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
        setPicChoose(selectList.get(0).getCutPath());
    }

    /**
     * 设置选中后
     *
     * @param compressPath 压缩后的路径
     */
    private void setPicChoose(String compressPath) {
        picProta.set(compressPath);
        uploadImg();
    }

    //我的用户资料隐藏社交账号
    public BindingCommand<Boolean> isMissSelf = new BindingCommand<Boolean>(aBoolean -> {
        hidingAccount = aBoolean ? 1 : 0;
        KLog.d("hiding_to_gender", hidingAccount + "");
    });
    /**
     * 提交
     */
    public BindingCommand submit = new BindingCommand(() -> {
        StringBuffer stringBuffer = new StringBuffer();
        if (areaLabelsData.get() != null && areaLabelsData.get().size() > 0) {
            for (String str : areaLabelsData.get()) {
                stringBuffer.append(str);
                stringBuffer.append(",");
            }
        }
        StringBuffer stringBuffer1 = new StringBuffer();
        if (mettingWish.get() != null && mettingWish.get().size() > 0) {
            for (String str : mettingWish.get()) {
                stringBuffer1.append(str);
                stringBuffer1.append(",");
            }
        }
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("user_id", AppUtils.getUser().getUser_id());//用户id
            put("nick_name", userName.get());//昵称
            put("dating_range", stringBuffer.toString());//约会范围
            put("age", ageText.get().substring(0, ageText.get().length() - 1));//年龄
            put("dating_expectations", stringBuffer1.toString());//约会期望
            put("qq", qqNum.get());//QQ
            put("wechat", vxNum.get());//微信
            put("hiding_social_account", hidingAccount);//是1否0隐藏社交账号
            put("job", jobText.get());//行业
            put("dating_show", mettingProgram.get());//约会节目
        }};
        if (!bodyHight.get().equals("请选择")) {
            map.put("height", bodyHight.get().substring(0, bodyHight.get().length() - 2));//身高
        }
        if (!bodyWeight.get().equals("请选择")) {
            map.put("weight", bodyWeight.get().substring(0, bodyWeight.get().length() - 2));//体重
        }
        if (mType == 0) {
            if (bodychest.get().equals("请选择")) {
                map.put("bust", bodychest.get());//胸围
            }
        }
        if (!TextUtils.isEmpty(personIntrduce.get())) {
            map.put("my_info", personIntrduce.get());//简介
        }
        KLog.d("request->", map.toString() + map.size());

        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).modifyMyInfo(map),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<UserInfo>() {
                    @Override
                    public void onResult(UserInfo o) {

                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                    @Override
                    public void onDataEmpty(String msg) {
                        ToastUtils.showShort("成功");
                        finish();
                    }
                });
    });

    /**
     * 提交
     */
    public BindingCommand submitX = new BindingCommand(() -> {
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入昵称");
        }
        if (null == areaLabelsData.get()) {
            ToastUtils.showShort("请选择约会范围");
        }
        if (TextUtils.isEmpty(ageText.get()) || ageText.get().equals("请选择")) {
            ToastUtils.showShort("请选择年龄");
        }

        if (null == mettingWish.get()) {
            ToastUtils.showShort("请输入约会期望");
        }
        if (TextUtils.isEmpty(qqNum.get())) {
            ToastUtils.showShort("请输入QQ号");
        }
        if (TextUtils.isEmpty(vxNum.get())) {
            ToastUtils.showShort("请输入微信号");
        }

        StringBuffer stringBuffer = new StringBuffer();
        if (areaLabelsData.get() != null && areaLabelsData.get().size() > 0) {
            for (String str : areaLabelsData.get()) {
                stringBuffer.append(str);
                stringBuffer.append(",");
            }
        }
        StringBuffer stringBuffer1 = new StringBuffer();
        if (mettingWish.get() != null && mettingWish.get().size() > 0) {
            for (String str : mettingWish.get()) {
                stringBuffer1.append(str);
                stringBuffer1.append(",");
            }
        }
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("user_id", user_id);//用户id
            put("nick_name", userName.get());//昵称
            put("dating_range", stringBuffer.toString());//约会范围
            put("age", ageText.get().substring(0, ageText.get().length() - 1));//年龄
            put("dating_expectations", stringBuffer1.toString());//约会期望
            put("qq", qqNum.get());//QQ
            put("wechat", vxNum.get());//微信
            put("hiding_social_account", 0);//是1否0隐藏社交账号

            put("job", jobText.get());//行业
            put("dating_show", mettingProgram.get());//约会节目
        }};
        if (!bodyHight.get().equals("请选择")) {
            map.put("height", bodyHight.get().substring(0, bodyHight.get().length() - 2));//身高
        }
        if (!bodyWeight.get().equals("请选择")) {
            map.put("weight", bodyWeight.get().substring(0, bodyWeight.get().length() - 2));//体重
        }
        if (mType == 0) {
            if (bodychest.get().equals("请选择")) {
                map.put("bust", bodychest.get());//胸围
            }
        }
        if (TextUtils.isEmpty(personIntrduce.get())) {
            map.put("my_info", personIntrduce.get());//简介
        }
        KLog.d("request->", map.toString() + map.size());
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).modifyMyInfo(map),
                getLifecycleProvider(), disposable -> showDialog(),
                new ApiDisposableObserver<UserInfo>() {
                    @Override
                    public void onResult(UserInfo o) {
                        ToastUtils.showShort("完善资料成功");
                        AppUtils.saveUser(o);
                        AppManager.getAppManager().finishActivity(RegisterActivity.class);
                        AppManager.getAppManager().finishActivity(ForgetPasswordActivity.class);
                        AppManager.getAppManager().finishActivity(SelectSexActivity.class);
                        startActivity(MainActivity.class);
                        finish();
                    }

                    @Override
                    public void dialogDismiss() {
                        dismissDialog();
                    }

                    @Override
                    public void onDataEmpty(String msg) {
                        ToastUtils.showShort("成功");
                        finish();
                    }
                });
    });

    /**
     * 上传图片
     */
    private void uploadImg() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
        File file = new File(picProta.get());
        RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), file);//表单类型
        KLog.d("-------------getUser_id-----------------" + AppUtils.getUser().getUser_id());
        builder.addFormDataPart("user_id", AppUtils.getUser().getUser_id());//传入服务器需要的key，和相应value值
        builder.addFormDataPart("files", file.getName(), body); //添加图片数据，body创建的请求体
        List<MultipartBody.Part> parts = builder.build().parts();
        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).uploadImage(parts)
                .map(stringBaseResponse -> stringBaseResponse.getData())
                .flatMap(s -> {
                    Map<String, Object> map = new HashMap<String, Object>() {{
                        put("avatar", s);
                        put("user_id", AppUtils.getUser().getUser_id());
                    }};
                    return RetrofitClient.getInstance().create(API.class).modifyAvatar(map);
                }), getLifecycleProvider(), disposable -> showDialog(), new ApiDisposableObserver() {
            @Override
            public void onResult(Object o) {

            }

            @Override
            public void dialogDismiss() {
                dismissDialog();
            }

            @Override
            public void onDataEmpty(String msg) {
                ToastUtils.showShort("修改成功");
                finish();
            }
        });
    }

    //修改头像
    public BindingCommand updateProta = new BindingCommand(() -> invokeEventAction(context -> uc.updateProta.set(!uc.updateProta.get())));

    class SelectDialogEvent {
        @FindView(R.id.wheelview)
        WheelView mWheelView;
        @FindView(R.id.age_select)
        TextView mSelect;
        @FindView(R.id.text_middle)
        TextView text_middle;
        final List<String> mOptionsItems = new ArrayList<>();
        private int mType = -1;

        public SelectDialogEvent(int tyte) {
            mType = tyte;
        }

        @Init
        private void initViews() {
            switch (mType) {
                case SELECT_TYPE_AGE:
                    mWheelView.setCyclic(true);
                    mSelect.setText("18岁");
                    text_middle.setText("年龄");
                    for (int i = 1; i < 120; i++) {
                        mOptionsItems.add(i + "岁");
                    }
                    mWheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));
                    mWheelView.setCurrentItem(17);
                    mWheelView.setOnItemSelectedListener(index -> mSelect.setText(mOptionsItems.get(index)));
                    break;
                case SELECT_TYPE_HIGHT:
                    mWheelView.setCyclic(true);
                    mSelect.setText("175cm");
                    text_middle.setText("身高");
                    for (int i = 100; i < 220; i++) {
                        mOptionsItems.add(i + "cm");
                    }

                    mWheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));
                    mWheelView.setCurrentItem(75);
                    mWheelView.setOnItemSelectedListener(index -> mSelect.setText(mOptionsItems.get(index)));
                    break;
                case SELECT_TYPE_WEIGHT:
                    mWheelView.setCyclic(true);
                    mSelect.setText("55kg");
                    text_middle.setText("体重");
                    for (int i = 25; i < 350; i++) {
                        mOptionsItems.add(i + "kg");
                    }

                    mWheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));
                    mWheelView.setCurrentItem(30);
                    mWheelView.setOnItemSelectedListener(index -> mSelect.setText(mOptionsItems.get(index)));
                    break;
                case SELECT_TYPE_BUST:
                    mWheelView.setCyclic(true);
                    mSelect.setText("70C");
                    text_middle.setText("胸围");
                    mOptionsItems.add("70A");
                    mOptionsItems.add("70B");
                    mOptionsItems.add("70C");
                    mOptionsItems.add("75A");
                    mOptionsItems.add("75B");
                    mOptionsItems.add("75C");
                    mOptionsItems.add("80A");
                    mOptionsItems.add("80B");
                    mOptionsItems.add("80C");
                    mOptionsItems.add("85A");
                    mOptionsItems.add("85B");
                    mOptionsItems.add("85C");
                    mOptionsItems.add("90A");
                    mOptionsItems.add("90B");
                    mOptionsItems.add("90C");

                    mWheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));
                    mWheelView.setCurrentItem(2);
                    mWheelView.setOnItemSelectedListener(index -> mSelect.setText(mOptionsItems.get(index)));
                    break;
            }
        }

        @OnClick({R.id.cancel, R.id.confir})
        public void onClicked(View view) {
            mSelectDialog.dismiss();
            switch (view.getId()) {
                case R.id.cancel:
                    break;
                case R.id.confir:
                    switch (mType) {
                        case SELECT_TYPE_AGE:
                            ageText.set(mSelect.getText().toString());
                            break;
                        case SELECT_TYPE_HIGHT:
                            bodyHight.set(mSelect.getText().toString());
                            break;
                        case SELECT_TYPE_WEIGHT:
                            bodyWeight.set(mSelect.getText().toString());
                            break;
                        case SELECT_TYPE_BUST:
                            bodychest.set(mSelect.getText().toString());
                            break;
                    }
                    break;
            }
        }
    }

    /**
     * 选择年龄
     */
    public BindingCommand ageSelect = new BindingCommand(() -> {
        invokeEventAction(context -> {
            if (mSelectDialog != null) mSelectDialog = null;
            if (mSelectDialogEvent != null) mSelectDialogEvent = null;
            mSelectDialogEvent = new SelectDialogEvent(SELECT_TYPE_AGE);
            mSelectDialog = new CustomDialog(context, R.layout.dialog_age_select, true, true, Gravity.BOTTOM,
                    content -> ViewUtils.inject(content, mSelectDialogEvent), true);
            mSelectDialog.show();
        });
    });
    //约会范围
    public BindingCommand areaSelect = new BindingCommand(() -> startActivity(MettingAreaSelectActivity.class));
}


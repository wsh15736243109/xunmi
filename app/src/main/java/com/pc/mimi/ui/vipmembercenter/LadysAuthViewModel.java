package com.pc.mimi.ui.vipmembercenter;

import android.app.Application;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class LadysAuthViewModel extends BaseViewModel {
    public LadysAuthViewModel(@NonNull Application application) {
        super(application);
    }

    UIChangeObser uc = new UIChangeObser();

    //上传认证照片
    public void uploadAuthPic() {
        ToastUtils.showShort("认证接口未知");
//        uploadImg();
    }

    public class UIChangeObser {
        public ObservableBoolean pic = new ObservableBoolean(false);
    }

    //图片
    public ObservableField<String> picProta = new ObservableField<>("");

    /**
     * 处理选择图片的结果
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
        setPicChoose(selectList.get(0).getCompressPath());
    }

    private void setPicChoose(String compressPath) {
        picProta.set(compressPath);
    }

    //上传图片
    private void uploadImg() {
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
//        File file = new File(picProta.get());
//        RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), file);//表单类型
//        KLog.d("-------------getUser_id-----------------" + AppUtils.getUser().getUser_id());
//        builder.addFormDataPart("user_id", AppUtils.getUser().getUser_id());//传入服务器需要的key，和相应value值
//        builder.addFormDataPart("files", file.getName(), body); //添加图片数据，body创建的请求体
//        List<MultipartBody.Part> parts = builder.build().parts();
//        AppUtils.requestData(RetrofitClient.getInstance().create(API.class).uploadImage(parts)
//                .map(stringBaseResponse -> stringBaseResponse.getData())
//                .flatMap(s -> {
//                    Map<String, Object> map = new HashMap<String, Object>() {{
//                        put("avatar", s);
//                        put("user_id", AppUtils.getUser().getUser_id());
//                    }};
//                    return RetrofitClient.getInstance().create(API.class).modifyAvatar(map);
//                }), getLifecycleProvider(), disposable -> showDialog(), new ApiDisposableObserver() {
//            @Override
//            public void onResult(Object o) {
//
//            }
//
//            @Override
//            public void dialogDismiss() {
//                dismissDialog();
//            }
//
//            @Override
//            public void onDataEmpty(String msg) {
//                ToastUtils.showShort("修改成功");
//                finish();
//            }
//        });
    }


    //选择照片
    public BindingCommand pickPhoto = new BindingCommand(() -> {
        uc.pic.set(!uc.pic.get());
    });
}

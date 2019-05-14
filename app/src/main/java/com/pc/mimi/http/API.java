package com.pc.mimi.http;

import com.pc.mimi.bean.AreaBean;
import com.pc.mimi.bean.AssessBean;
import com.pc.mimi.bean.ChargeUnit;
import com.pc.mimi.bean.CommentReturnBean;
import com.pc.mimi.bean.IncomeRechListBean;
import com.pc.mimi.bean.JobBean;
import com.pc.mimi.bean.MessageListBean;
import com.pc.mimi.bean.MettingListBean;
import com.pc.mimi.bean.OtherPersonBean;
import com.pc.mimi.bean.PersonBean;
import com.pc.mimi.bean.RadioBean;
import com.pc.mimi.bean.SecretSettingBean;
import com.pc.mimi.bean.SignDataBean;
import com.pc.mimi.bean.SignUpAccountListBean;
import com.pc.mimi.bean.UserInfo;
import com.pc.mimi.bean.VXBean;
import com.pc.mimi.bean.VipChargeBean;
import com.pc.mimi.bean.WalletInfoBean;
import com.pc.mimi.websocketim.MessageBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    //主机
    String BASE_AUTH = "http://47.97.184.107:8085/";
    String COMMON_URL = "motianlun/";

    String BASEURL = BASE_AUTH + COMMON_URL;
    //图片链接主机
    String COMMON_PIC_URL = BASE_AUTH + "motianlun";

    /**
     * @param phone
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/get_phone_code")
    Observable<BaseResponse<Object>> getSMSCode(@Field("phone") String phone);

    /**
     * 注册
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/reg")
    Observable<BaseResponse<String>> requestReg(@FieldMap Map<String, Object> map);

    /**
     * 登录
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseResponse<UserInfo>> requestLogin(@FieldMap Map<String, Object> map);

    /**
     *
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/forget_pwd")
    Observable<BaseResponse<Object>> updateNewPwd(@FieldMap Map<String, Object> map);

    /**
     * 图片上传接口
     *
     * @param parts 图片file
     * @return 链接
     */
//    @Headers("Content-Type:application/x-www-form-urlencoded")
    @Multipart
    @POST("user/upload_files")
    Observable<BaseResponse<String>> uploadImage(@Part List<MultipartBody.Part> parts);

    /**
     * 修改头像
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/modify_avatar")
    Observable<BaseResponse<Object>> modifyAvatar(@FieldMap Map<String, Object> map);

    /**
     * 修改密码
     *
     * @param user_id 用户id
     * @param s       旧密码
     * @param s1      新密码
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/set_pwd")
    Observable<BaseResponse<Object>> modifyPassword(@Field("user_id") String user_id, @Field("old_pwd") String s, @Field("new_pwd") String s1);

    /**
     * 绑定手机号
     *
     * @param s
     * @param user_id
     * @param s1
     * @param s2
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/set_phone")
    Observable<BaseResponse<Object>> setPhone(@Field("phone") String s, @Field("user_id") String user_id, @Field("code") String s1, @Field("code") String s2);

    /**
     * 城市列表
     *
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/city")
    Observable<BaseResponse<List<AreaBean>>> getCityList(@Field("user_id") String userId);

    /**
     * 区域列表
     *
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/city")
    Observable<BaseResponse<List<AreaBean>>> getAreaList(@Field("user_id") String user_id, @Field("city_id") String city_id);

    /**
     * 查看个人信息
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_my_info")
    Observable<BaseResponse<UserInfo>> requestUserInfo(@Field("user_id") String user_id);

    /**
     * 行业3级联动(目录不传参)
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/industry")
    Observable<BaseResponse<List<JobBean>>> getJobList(@Field("user_id") String user_id);

    /**
     * 行业3级联动(目录不传参)
     *
     * @param user_id
     * @param industry_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/industry")
    Observable<BaseResponse<List<JobBean>>> getJobList(@Field("user_id") String user_id, @Field("industry_id") String industry_id);

    /**
     * 修改资料
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/modify_my_info")
    Observable<BaseResponse<UserInfo>> modifyMyInfo(@FieldMap Map<String, Object> map);

    /**
     * 发布约会
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/add_dating")
    Observable<BaseResponse<Object>> publishMetting(@FieldMap Map<String, Object> map);

    /**
     * 电台
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_voice")
    Observable<BaseResponse<RadioBean>> requestSeeVoice(@FieldMap Map<String, Object> map);

    /**
     * 查看富豪榜
     *
     * @param user_id
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_most_spend")
    Observable<BaseResponse<List<IncomeRechListBean>>> requestRechList(@Field("user_id") String user_id);

    /**
     * 查看收入榜
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_most_earn")
    Observable<BaseResponse<List<IncomeRechListBean>>> requestIncomeList(@Field("user_id") String user_id);

    /**
     * 用户查看
     *
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_first_page")
    Observable<BaseResponse<List<PersonBean>>> requestPersonList(@FieldMap Map<String, Object> map);

    /**
     * 约会列表
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/dating_list")
    Observable<BaseResponse<List<MettingListBean>>> requestDatingList(@FieldMap Map<String, Object> map);


    /**
     * 我喜欢的
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_my_like")
    Observable<BaseResponse<List<PersonBean>>> requestMyFavourLis(@Field("user_id") String user_id);


    /**
     * 黑名单
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_black_number")
    Observable<BaseResponse<List<PersonBean>>> requestBlackPeopleList(@Field("user_id") String user_id);


    /**
     * 查看别人信息
     *
     * @param user_id
     * @param other_user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_other_info")
    Observable<BaseResponse<OtherPersonBean>> requestSeeOtherInfo(@Field("user_id") String user_id, @Field("other_user_id") String other_user_id);


    /**
     * 获取充值vip规格
     *
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/get_vip")
    Observable<BaseResponse<List<VipChargeBean>>> requestGetVipChargeType(@Field("user_id") String user_id);

    /**
     * 发布约会评论
     *
     * @param user_id
     * @param all_dating_id
     * @param content
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/agrument_dating")
    Observable<BaseResponse<CommentReturnBean>> requestComment(@Field("user_id") String user_id, @Field("all_dating_id") String all_dating_id, @Field("content") String content);

    /**
     * 回复约会评论
     *
     * @param user_id
     * @param all_dating_id
     * @param content
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/agrument_dating_agrument")
    Observable<BaseResponse<CommentReturnBean>> requestReComment(@Field("user_id") String user_id,
                                                                 @Field("all_dating_id") String all_dating_id,
                                                                 @Field("agrument_id") String agrument_id,
                                                                 @Field("content") String content);

    /**
     * 给约会点赞
     *
     * @param user_id
     * @param all_dating_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/good_for_dating")
    Observable<BaseResponse<Object>> requestThumbUp(@Field("user_id") String user_id, @Field("all_dating_id") String all_dating_id);

    /**
     * 报名约会
     *
     * @param user_id
     * @param all_dating_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/sign_up_dating")
    Observable<BaseResponse<Object>> requestSignUp(@Field("user_id") String user_id, @Field("all_dating_id") String all_dating_id, @Field("img_url") String url);

    /**
     * 获取支付规格
     *
     * @param user_id
     * @return
     */
    @Deprecated
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_pay_type")
    Observable<BaseResponse<List<ChargeUnit>>> requestChargeItem(@Field("user_id") String user_id);

    /**
     * 支付宝充值
     *
     * @param user_id
     * @param unit
     * @param currentPayType
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/pay_money")
    Observable<BaseResponse<String>> goAliCharge(@Field("user_id") String user_id, @Field("pay_type_id") String unit, @Field("pay_type") int currentPayType);

    /**
     * 微信充值
     *
     * @param user_id
     * @param unit
     * @param currentPayType
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/pay_money")
    Observable<BaseResponse<VXBean>> goVXCharge(@Field("user_id") String user_id, @Field("pay_type_id") String unit, @Field("pay_type") int currentPayType);

    /**
     * 充值会员-支付宝
     *
     * @param user_id
     * @param unit
     * @param currentPayType
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/pay_vip")
    Observable<BaseResponse<String>> goAliChargeVip(@Field("user_id") String user_id, @Field("vip_price_id") String unit, @Field("pay_type") int currentPayType);

    /**
     * 充值会员-微信
     *
     * @param user_id
     * @param unit
     * @param currentPayType
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/pay_vip")
    Observable<BaseResponse<VXBean>> goVXChargeVip(@Field("user_id") String user_id, @Field("vip_price_id") String unit, @Field("pay_type") int currentPayType);

    /**
     * 设置性别
     *
     * @param user_id
     * @param sex
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/set_gender")
    Observable<BaseResponse<Object>> setSex(@Field("user_id") String user_id, @Field("gender") int sex);

    /**
     * 添加相册
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/add_picture")
    Observable<BaseResponse<Object>> addPhotoBook(@FieldMap Map<String, Object> map);

    /**
     * 查看签到信息
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_sign_info")
    Observable<BaseResponse<SignDataBean>> see_sign_info(@Field("user_id") String user_id);

    /**
     * 签到
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/sign")
    Observable<BaseResponse<Object>> requestSign(@Field("user_id") String user_id);

    /**
     * 我的约会列表
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_my_dating")
    Observable<BaseResponse<List<MettingListBean>>> see_my_dating(@Field("user_id") String user_id);

    /**
     * 匿名举报
     *
     * @param user_id
     * @param r_user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/report")
    Observable<BaseResponse<Object>> requestReport(@Field("user_id") String user_id, @Field("down_user_id") String r_user_id);

    /**
     * 查看报名人员
     *
     * @param user_id       用户ID
     * @param all_dating_id 约会id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_dating_sign_user")
    Observable<BaseResponse<List<SignUpAccountListBean>>> requestSignUpAccountList(@Field("user_id") String user_id, @Field("all_dating_id") String all_dating_id);

    /**
     * 查看之前的信息
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_before_info")
    Observable<BaseResponse<UserInfo>> see_before_info(@Field("user_id") String user_id);

    /**
     * 隐私设置详情
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_my_hiding_set")
    Observable<BaseResponse<SecretSettingBean>> requestSecretSettingDetail(@Field("user_id") String user_id);

    /**
     * 设置隐私详情
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/set_hiding_set")
    Observable<BaseResponse<Object>> setSecretSetting(@FieldMap Map<String, Object> map);

    /**
     * 停止报名约会
     *
     * @param all_dating_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/stop_sign")
    Observable<BaseResponse<Object>> finishSignUp(@Field("user_id") String user_id, @Field("all_dating_id") String all_dating_id);

    /**
     * 绑定/修改支付宝账号
     *
     * @param user_id
     * @param trim
     * @param trim1
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/add_aliaccount")
    Observable<BaseResponse<Object>> doBindAliAccouunt(@Field("user_id") String user_id, @Field("acount_mumber") String trim, @Field("account_name") String trim1);

    /**
     * 钱包
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/wallet")
    Observable<BaseResponse<WalletInfoBean>> requestWalletInfo(@Field("user_id") String user_id);

    /**
     * 发起提现
     *
     * @param user_id
     * @param trim
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/withdraw")
    Observable<BaseResponse<Object>> doCash(@Field("user_id") String user_id, @Field("values") String trim);

    /**
     * 查看我的评价
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_my_evaluate")
    Observable<BaseResponse<AssessBean>> requestMyAssess(@Field("user_id") String user_id);

    /**
     * 查看聊天列表
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_chat_list")
    Observable<BaseResponse<List<MessageListBean>>> requestMessageList(@Field("user_id") String user_id);

    /**
     * 查看聊天记录
     *
     * @param user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/see_chat")
    Observable<BaseResponse<List<MessageBean>>> getChatRecord(@Field("user_id") String user_id,
                                                              @Field("out_user_id") String out_user_id,
                                                              @Field("page") String page);

    /**
     * 添加到我的喜欢
     *
     * @param user_id
     * @param user_id1
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/add_my_like")
    Observable<BaseResponse<Object>> requestLikeIt(@Field("user_id") String user_id, @Field("like_user_id") String user_id1);

    /**
     * 取消喜欢
     *
     * @param user_id
     * @param user_id1
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/del_my_like")
    Observable<BaseResponse<Object>> requestDelLikeIt(@Field("user_id") String user_id, @Field("my_like_user_id") String user_id1);

    /**
     * 评价他人
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/add_evaluate")
    Observable<BaseResponse<Object>> goAssess(@FieldMap Map<String, Object> map);

    /**
     * 设置相册阅后即焚
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/set_see_later_break")
    Observable<BaseResponse<Object>> requestSeeDelete(@FieldMap Map<String, Object> map);

    /**
     * 删除相册照片
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/del_picture")
    Observable<BaseResponse<Object>> deletePhoto(@FieldMap Map<String, Object> map);

    /**
     * 设置红包照片
     *
     * @param map
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/set_picture_pay")
    Observable<BaseResponse<Object>> set_picture_pay(@FieldMap Map<String, Object> map);

    /**
     * 移除拉黑
     *
     * @param user_id
     * @param other_user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/remove_black_number")
    Observable<BaseResponse<Object>> remove_black_number(@Field("user_id") String user_id, @Field("b_m_user_id") String other_user_id);

    /**
     * 拉黑
     *
     * @param user_id
     * @param other_user_id
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/add_black_number")
    Observable<BaseResponse<Object>> add_black_number(@Field("user_id") String user_id, @Field("b_m_user_id") String other_user_id);

}

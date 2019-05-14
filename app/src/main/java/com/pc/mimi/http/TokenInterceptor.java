package com.pc.mimi.http;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import me.goldze.mvvmhabit.utils.KLog;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


public class TokenInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        // try the request
        Response originalResponse = chain.proceed(request);


        /**通过如下的办法曲线取到请求完成的数据 ** 原本想通过 originalResponse.body().string()
         * 去取到请求完成的数据,但是一直报错,不知道是okhttp的bug还是操作不当
         *  然后去看了okhttp的源码,找到了这个曲线方法,取到请求完成的数据后,根据特定的判断条件去判断token过期
         * */
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        String bodyString = buffer.clone().readString(charset);
        KLog.d("body---------->" + bodyString);
        try {
            JSONObject jsonObject = new JSONObject(bodyString);
            if (jsonObject.getInt("code") == 1004 || jsonObject.getInt("code") == 206) {
//                if (TextUtils.isEmpty(SaveUtil.getToken())) {
//                    KLog.d("此处有问题。到本页面应该先判断用户是否登陆过，请修改业务逻辑代码");
//                } else {
//                    final Request[] newRequest = new Request[1];
//                    LoginRequest loginRequest = new LoginRequest(SaveUtil.getUserBean().getTelephone(), SaveUtil.getUserBean().getPassword());
//                    RetrofitClient.getInstance().create(API.class).requestLogin(loginRequest).subscribe(new Consumer<LoginResponse>() {
//                        @Override
//                        public void accept(LoginResponse loginResponse) throws Exception {
//                            SaveUtil.saveToken(loginResponse.getResult().getToken());
//                            String newToken = loginResponse.getResult().getToken();
//                            newRequest[0] = request.newBuilder().header("TOKEN", newToken)
//                                    .build();
//
//                            originalResponse.body().close();
//
//                        }
//                    }, throwable -> {
//                    }, () -> {
//                    });
//                    return chain.proceed(newRequest[0]);
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return originalResponse;
    }
}

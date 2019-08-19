package com.cyt.appsdk.okhttp;

import com.cyt.appsdk.okhttp.https.HttpsUtils;
import com.cyt.appsdk.okhttp.response.CommonJsonCallback;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class CommonOkHttpClient {

    private static final int TIME_OUT = 30;//超市参数
    private static OkHttpClient mOkHttpClient;

    // 为client配置参数
    static {

        // 创建Client对象的构建者
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        // 为构建者填充超时时间
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIME_OUT,TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIME_OUT,TimeUnit.SECONDS);

        okHttpBuilder.followRedirects(true);

        // https支持
        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        okHttpBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());
        mOkHttpClient = okHttpBuilder.build();
    }

    /**
     * 发送具体的Http/Https请求
     * @param request
     * @param commCallback
     * @return Call
     */
    public static Call sendRequest(Request request, CommonJsonCallback commCallback){

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(commCallback);

        return call;
    }

}

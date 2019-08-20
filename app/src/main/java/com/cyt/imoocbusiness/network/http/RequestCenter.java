package com.cyt.imoocbusiness.network.http;

import com.cyt.appsdk.okhttp.CommonOkHttpClient;
import com.cyt.appsdk.okhttp.listener.DisposeDataHandle;
import com.cyt.appsdk.okhttp.listener.DisposeDataListener;
import com.cyt.appsdk.okhttp.request.CommonRequest;
import com.cyt.appsdk.okhttp.request.RequestParams;
import com.cyt.imoocbusiness.model.recommand.BaseRecommandModel;

public class RequestCenter {

    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }


    /**
     * 真正的发送首页请求
     * @param listener
     */
    public static void requestRecommandData(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.HOME_RECOMMAND, null, listener, BaseRecommandModel.class);
    }

}

package com.cyt.appsdk.okhttp.listener;

/**
 * 自定义事件监听
 */
public interface DisposeDataListener {

    /**
     * 请求成功回调事件处理
     * @param responseObj
     */
    void onSuccess(Object responseObj);

    /**
     * 请求失败回调事件处理
     * @param reasonObj
     */
    void onFailure(Object reasonObj);
}

package com.cyt.appsdk.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import com.cyt.appsdk.okhttp.adutil.ResponseEntityToModule;
import com.cyt.appsdk.okhttp.exception.OkHttpException;
import com.cyt.appsdk.okhttp.listener.DisposeDataHandle;
import com.cyt.appsdk.okhttp.listener.DisposeDataListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommonJsonCallback implements Callback {

    /**
     * the logic layer exception, may alter in different app
     */
    protected final String RESULT_CODE = "ecode"; // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";
    protected final String COOKIE_STORE = "Set-Cookie"; // decide the server it
    // can has the value of
    // set-cookie2

    /**
     * the java layer exception, do not same to the logic error
     */
    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int JSON_ERROR = -2; // the JSON relative error
    protected final int OTHER_ERROR = -3; // the unknow error

    /**
     * 将其它线程的数据转发到UI线程
     */
    private Handler mDeliveryHandler;// 进行消息的转发
    private DisposeDataListener mListener;
    private Class<?> mClass;


    public CommonJsonCallback(DisposeDataHandle handle) {
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    // 请求失败处理
    @Override
    public void onFailure(@NotNull Call call, @NotNull final IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    // 真正的响应处理函数
    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

    }

    /**
     * 处理服务器返回的响应数据
     *
     * @param responseObj
     */
    private void handleResponse(Object responseObj) {
        // 为了保证代码的健壮性
        if (responseObj == null && responseObj.toString().trim().equals("")) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }

        try {
            JSONObject result = new JSONObject(responseObj.toString());
            if (result.has(RESULT_CODE)) {
                // 从JSON对象中取出响应码，若为0，则是正确的响应
                if (result.getInt(RESULT_CODE) == RESULT_CODE_VALUE) {

                    if (mClass == null) {
                        mListener.onSuccess(responseObj);
                    }else {
                        // 需要将JSON对象转化为实体对象
                        Object obj = ResponseEntityToModule.parseJsonObjectToModule(result,mClass);
                        if (obj != null){
                            mListener.onSuccess(obj);
                        }else {
                            mListener.onFailure(new OkHttpException(JSON_ERROR,EMPTY_MSG));
                        }
                    }
                }
            }else {
                mListener.onFailure(new OkHttpException(OTHER_ERROR,result.get(RESULT_CODE)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mListener.onFailure(new OkHttpException(OTHER_ERROR,e.getMessage()));
        }
    }
}

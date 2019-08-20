package com.cyt.imoocbusiness.view.fragment.home;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cyt.appsdk.okhttp.listener.DisposeDataListener;
import com.cyt.imoocbusiness.R;
import com.cyt.imoocbusiness.adapter.CourseAdapter;
import com.cyt.imoocbusiness.model.recommand.BaseRecommandModel;
import com.cyt.imoocbusiness.network.http.RequestCenter;
import com.cyt.imoocbusiness.view.fragment.BaseFragment;

public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    /**
     * UI
     */
    private View mContentView;
    private ListView mListView;
    private TextView mQRCodeView;
    private TextView mCategoryView;
    private TextView mSearchView;
    private ImageView mLoadingView;
    /**
     * data
     */
    private CourseAdapter mAdapter;
    private BaseRecommandModel mRecommandData;

    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestRecommandData();
    }

    /**
     * 发送首页列表数据请求
     */
    private void requestRecommandData() {
        RequestCenter.requestRecommandData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.e("TAG","onSuccess:"+responseObj.toString());
                
                mRecommandData = (BaseRecommandModel) responseObj;
                showSuccessView();
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.e("TAG","onFailure:"+reasonObj.toString());

            }
        });
    }

    /**
     * 请求成功后执行的方法
     */
    private void showSuccessView() {

        // 判断数据是否为空
        if (mRecommandData.data.list != null && mRecommandData.data.list.size() > 0){

            mLoadingView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            // 创建Adapter
            mAdapter = new CourseAdapter(mContext,mRecommandData.data.list);
            mListView.setAdapter(mAdapter);


        }else {
            showErrorView();
        }
    }

    /**
     * 请求失败后执行的方法
     */
    private void showErrorView() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_home_layout,container,false);
        initView();
        return mContentView;
    }

    private void initView() {
        mQRCodeView = mContentView.findViewById(R.id.qrcode_view);
        mQRCodeView.setOnClickListener(this);
        mCategoryView = mContentView.findViewById(R.id.category_view);
        mCategoryView.setOnClickListener(this);
        mSearchView = mContentView.findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);
        mListView = mContentView.findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        mLoadingView = mContentView.findViewById(R.id.loading_view);
        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();
        anim.start();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

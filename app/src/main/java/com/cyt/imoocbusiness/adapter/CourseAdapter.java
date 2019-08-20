package com.cyt.imoocbusiness.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.cyt.appsdk.adutil.Utils;
import com.cyt.appsdk.imageloader.ImageLoaderManager;
import com.cyt.imoocbusiness.R;
import com.cyt.imoocbusiness.model.recommand.RecommandBodyValue;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseAdapter extends BaseAdapter {

    /**
     * Common
     */
    private static final int CARD_COUNT = 4;
    private static final int VIDOE_TYPE = 0x00;
    private static final int CARD_TYPE_ONE = 0x01;
    private static final int CARD_TYPE_TWO = 0x02;
    private static final int CARD_TYPE_THREE = 0x03;

    private LayoutInflater mInflate;
    private Context mContext;
    private ViewHolder mViewHolder;

    private ArrayList<RecommandBodyValue> mData;
    /**
     * 异步图片加载
     */
    private ImageLoaderManager mImagerLoader;

    public CourseAdapter(Context context,ArrayList<RecommandBodyValue> data){
        mContext = context;
        mData = data;
        mInflate = LayoutInflater.from(mContext);
        mImagerLoader = ImageLoaderManager.getInstance(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return CARD_COUNT;
    }

    @Override
    public int getItemViewType(int position) {

        RecommandBodyValue value = (RecommandBodyValue) getItem(position);
        return value.type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // 1、获取数据的Type类型
        int type = getItemViewType(position);
        final RecommandBodyValue value = (RecommandBodyValue) getItem(position);

        // 当convertView为空时表明当前没有可使用的缓存View
        if (convertView == null){
            switch (type){
                case CARD_TYPE_ONE:
                    mViewHolder = new ViewHolder();
                    convertView = mInflate.inflate(R.layout.item_product_card_one_layout,parent,false);
                    mViewHolder.mLogoView = convertView.findViewById(R.id.item_logo_view);
                    mViewHolder.mTitleView = convertView.findViewById(R.id.item_title_view);
                    mViewHolder.mInfoView = convertView.findViewById(R.id.item_info_view);
                    mViewHolder.mFooterView = convertView.findViewById(R.id.item_footer_view);
                    mViewHolder.mPriceView = convertView.findViewById(R.id.item_price_view);
                    mViewHolder.mFromView = convertView.findViewById(R.id.item_from_view);
                    mViewHolder.mZanView = convertView.findViewById(R.id.item_zan_view);
                    mViewHolder.mProductLayout = convertView.findViewById(R.id.product_photo_layout);
                    break;

                case CARD_TYPE_TWO:
                    mViewHolder = new ViewHolder();
                    convertView = mInflate.inflate(R.layout.item_product_card_two_layout, parent, false);
                    mViewHolder.mLogoView = convertView.findViewById(R.id.item_logo_view);
                    mViewHolder.mTitleView = convertView.findViewById(R.id.item_title_view);
                    mViewHolder.mInfoView = convertView.findViewById(R.id.item_info_view);
                    mViewHolder.mFooterView = convertView.findViewById(R.id.item_footer_view);
                    mViewHolder.mProductView = convertView.findViewById(R.id.product_photo_view);
                    mViewHolder.mPriceView = convertView.findViewById(R.id.item_price_view);
                    mViewHolder.mFromView = convertView.findViewById(R.id.item_from_view);
                    mViewHolder.mZanView = convertView.findViewById(R.id.item_zan_view);
                    break;
            }

            convertView.setTag(mViewHolder);
        }
        // 有可用的ConvertView
        else {

            mViewHolder = (ViewHolder) convertView.getTag();

        }

        // 开始绑定数据
        switch (type){
            case CARD_TYPE_ONE:
                mImagerLoader.displayImage(mViewHolder.mLogoView, value.logo);
                mViewHolder.mTitleView.setText(value.title);
                mViewHolder.mInfoView.setText(value.info.concat(mContext.getString(R.string.tian_qian)));
                mViewHolder.mFooterView.setText(value.text);
                mViewHolder.mPriceView.setText(value.price);
                mViewHolder.mFromView.setText(value.from);
                mViewHolder.mZanView.setText(mContext.getString(R.string.dian_zan).concat(value.zan));
//                mViewHolder.mProductLayout.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext, PhotoViewActivity.class);
//                        intent.putStringArrayListExtra(PhotoViewActivity.PHOTO_LIST, value.url);
//                        mContext.startActivity(intent);
//                    }
//                });
                // 删除已有的图片
                mViewHolder.mProductLayout.removeAllViews();
                //动态添加多个imageview
                for (String url : value.url) {
                    mViewHolder.mProductLayout.addView(createImageView(url));
                }
                break;
            case CARD_TYPE_TWO:
                mImagerLoader.displayImage(mViewHolder.mLogoView, value.logo);
                mViewHolder.mTitleView.setText(value.title);
                mViewHolder.mInfoView.setText(value.info.concat(mContext.getString(R.string.tian_qian)));
                mViewHolder.mFooterView.setText(value.text);
                mViewHolder.mPriceView.setText(value.price);
                mViewHolder.mFromView.setText(value.from);
                mViewHolder.mZanView.setText(mContext.getString(R.string.dian_zan).concat(value.zan));
                //为单个ImageView加载远程图片
                mImagerLoader.displayImage(mViewHolder.mProductView, value.url.get(0));
                break;
        }

        return convertView;
    }

    private View createImageView(String url) {
        ImageView photoView = new ImageView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.
                LayoutParams(Utils.dip2px(mContext, 100),
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.leftMargin = Utils.dip2px(mContext, 5);
        photoView.setLayoutParams(params);
        mImagerLoader.displayImage(photoView, url);
        return photoView;

    }

    private static class ViewHolder {
        //所有Card共有属性
        private CircleImageView mLogoView;
        private TextView mTitleView;
        private TextView mInfoView;
        private TextView mFooterView;
        //Video Card特有属性
        private RelativeLayout mVieoContentLayout;
        private ImageView mShareView;

        //Video Card外所有Card具有属性
        private TextView mPriceView;
        private TextView mFromView;
        private TextView mZanView;
        //Card One特有属性
        private LinearLayout mProductLayout;
        //Card Two特有属性
        private ImageView mProductView;
        //Card Three特有属性
        private ViewPager mViewPager;
    }
}

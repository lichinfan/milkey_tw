package com.kentec.milkbox.coupon.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;
import com.kentec.milkbox.comm.CacheBaseAdapter;
import com.kentec.milkbox.coupon.data.Coupon;


public class MyCouponAdapter extends CacheBaseAdapter {

	private static final String TAG = "MyCouponAdapter";
	
	private BaseActivity mactivity;
	
	private int mBaseWidth;
	private int mImageWidth;
	
	private ArrayList<Coupon> mList;
	private LayoutInflater mLayoutInflater;
		
	public MyCouponAdapter(BaseActivity activity, ArrayList<Coupon> list, int baseWidth) {
		mactivity = activity;
		mList = list;		
		mBaseWidth = baseWidth;
		mImageWidth = (int)(baseWidth*1.8);
		mLayoutInflater = (LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup vg) {
		
		ViewHolder vh;		
		if (convertView == null) {
			
			vh = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.row_coupon_my, vg, false);
			
			vh.productIv = (ImageView) convertView.findViewById(R.id.productIv);
			vh.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
			vh.shortDescTv = (TextView) convertView.findViewById(R.id.shortDescTv);
			vh.expDateTv = (TextView) convertView.findViewById(R.id.expDateTv);
			vh.useIv = (ImageView) convertView.findViewById(R.id.useIv);
									
			reLayout(vh.nameTv,2);
			reLayout(vh.shortDescTv,3);
			reLayout(vh.expDateTv,2);
			reLayout(vh.useIv,1);
			
			convertView.setTag(vh);		
			
		} else {			
			vh =  (ViewHolder) convertView.getTag();			
		}
		
		try {
			
			Coupon row = mList.get(position);			
			vh.nameTv.setText(row.getName());			
			vh.shortDescTv.setText(row.getShortDesc());
			vh.expDateTv.setText(row.getExpDate());
			
			if( row.getStatus() == Coupon.USE ) {
				vh.useIv.setVisibility(View.VISIBLE);
			} else {
				vh.useIv.setVisibility(View.INVISIBLE);
			}
			
			LoadImageAsyncTask task = new LoadImageAsyncTask(row.getImage(), vh.productIv, mImageWidth, mImageWidth);
			DownloadedDrawable drawable = new DownloadedDrawable(task);
			vh.productIv.setImageDrawable(drawable);
			task.execute();
			
		} catch (Exception e) {
			Log.e(TAG,e.toString());
		}
		
		return convertView;
	}
	
	class ViewHolder {
		ImageView productIv;
		TextView nameTv;
		TextView shortDescTv;
		TextView expDateTv;
		ImageView useIv;		
	}	
	
	public void setData(ArrayList<Coupon> list) {
		mList = list;
	}	
	
	private void reLayout(TextView tv, int base) {
		LayoutParams params = tv.getLayoutParams();
		params.width = mBaseWidth * base;
		tv.setLayoutParams(params);		
	}

	private void reLayout(ImageView iv, int base) {
		LayoutParams params = iv.getLayoutParams();
		params.width = mBaseWidth * base;
		iv.setLayoutParams(params);		
	}

}

package com.kentec.milkbox.coupon.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;
import com.kentec.milkbox.comm.CacheBaseAdapter;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;

public class CouponMainAdapter extends CacheBaseAdapter {

	private static final String TAG = "CouponMainAdapter";

	protected ArrayList<Coupon> mList;
	protected LayoutInflater mLayoutInflater;

	protected int mCellWidth;
	protected int mCellHeight;

	protected int mImageWidth;
	protected int mImageHeight;

	protected int mBorderWidth;
	protected int mBorderHeight;

	protected ViewGroup.LayoutParams mLayoutParams;
	protected ViewGroup.LayoutParams mImageLayoutParams;

	private ReLayoutUtil mReLayoutUtil;

	public CouponMainAdapter(BaseActivity activity, ArrayList<Coupon> list) {
		mList = list;
		mReLayoutUtil = activity.reLayout();
		mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			convertView = mLayoutInflater.inflate(R.layout.row_coupon_main, vg, false);
			vh.mainRl = (RelativeLayout) convertView.findViewById(R.id.mainRl);
			vh.mainIv = (ImageView) convertView.findViewById(R.id.mainIv);
			vh.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
			vh.shortDescTv = (TextView) convertView.findViewById(R.id.shortDescTv);
			vh.expDateTv = (TextView) convertView.findViewById(R.id.expDateTv);

			mReLayoutUtil.linear(vh.mainRl, 375, 756, 0, 0);			
			mReLayoutUtil.relative(vh.mainIv, 317, 260, 42, 22);
			mReLayoutUtil.relative(vh.nameTv, 300, 100, 42, 320);			
			mReLayoutUtil.relative(vh.shortDescTv, 300, 200, 42, 370);			
			mReLayoutUtil.relative(vh.expDateTv, 255, 100, 42, 580);			
			convertView.setTag(vh);
			
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		try {

			Coupon row = mList.get(position);
			vh.nameTv.setText(row.getName());
			vh.shortDescTv.setText(row.getShortDesc());
			vh.expDateTv.setText("Exp."+row.getExpDate());

			LoadImageAsyncTask task = new LoadImageAsyncTask(row.getImage(), vh.mainIv, 275, 230);
			DownloadedDrawable drawable = new DownloadedDrawable(task);
			vh.mainIv.setImageDrawable(drawable);
			task.execute();

		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

		return convertView;
	}

	class ViewHolder {
		RelativeLayout mainRl;
		ImageView mainIv;
		TextView nameTv;
		TextView shortDescTv;
		TextView expDateTv;
	}

	public void setData(ArrayList<Coupon> list) {
		mList = list;
	}

	public Bitmap getImage(String name) {
		return mImageCache.get(name).get();
	}

}

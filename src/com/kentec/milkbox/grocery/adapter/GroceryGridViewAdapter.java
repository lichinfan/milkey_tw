package com.kentec.milkbox.grocery.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;
import com.kentec.milkbox.comm.CacheBaseAdapter;
import com.kentec.milkbox.grocery.data.Category;
import com.kentec.milkbox.grocery.data.GroceryCellView;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;
import com.kentec.milkbox.utils.DEBUG;

public class GroceryGridViewAdapter extends CacheBaseAdapter {

	private static final String TAG = "GroceryGridViewAdapter";

	private Activity mActivity;

	protected ArrayList<GroceryCellView> mList;
	protected LayoutInflater mLayoutInflater;

	protected int mImageWidth;
	protected int mImageHeight;

	protected ReLayoutUtil mReLayoutUtil;

	private int mAppendNum;

	public GroceryGridViewAdapter(BaseActivity activity, ArrayList<GroceryCellView> list) {
		this.mActivity = activity;
		mList = list;
		mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mReLayoutUtil = activity.reLayout();

		mImageWidth = (int) ((activity.getDisW() / 5) * 0.85);
		mImageHeight = mImageWidth;

		mAppendNum = 0;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	public int getNoAppendCount() {
		return getCount() - mAppendNum;
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
			convertView = mLayoutInflater.inflate(R.layout.row_grocery_main, vg, false);
			vh.mainRl = (RelativeLayout) convertView.findViewById(R.id.mainRl);
			vh.mainIv = (ImageView) convertView.findViewById(R.id.mainIv);
			vh.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
			vh.countTv = (TextView) convertView.findViewById(R.id.countTv);
			vh.couponIv = (ImageView) convertView.findViewById(R.id.couponIv);
			vh.basketNumIv = (ImageView) convertView.findViewById(R.id.basketNumIv);

			// mReLayoutUtil.linear(vh.mainRl, 365, 425, 0, 0);
			// mReLayoutUtil.relative(vh.mainIv, 275, 230, 42, 22);
			// mReLayoutUtil.relative(vh.nameTv, 324, 50, 15, 292);

			mReLayoutUtil.linear(vh.mainRl, 365, 405, 0, 0);
			mReLayoutUtil.relative(vh.mainIv, 275, 220, 42, 20);
			mReLayoutUtil.relative(vh.nameTv, 324, 50, 15, 282);

			convertView.setTag(vh);

		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		try {

			GroceryCellView row = mList.get(position);
			vh.nameTv.setText(row.getName());

			if (row instanceof Category) {
				vh.couponIv.setVisibility(View.INVISIBLE);
				vh.countTv.setVisibility(View.VISIBLE);
				vh.countTv.setText(Integer.toString(((Category) row).getProductCount()));
//				vh.countTv.setText("999");
				vh.basketNumIv.setVisibility(View.VISIBLE);

				vh.basketNumIv.setImageResource(getBasketNumResource(position));
				vh.mainRl.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.grocery_basket));
			} else if (row instanceof Product) {
				Product p = (Product) row;
				if (p.isCoupon()) {
					vh.couponIv.setVisibility(View.VISIBLE);
				} else {
					vh.couponIv.setVisibility(View.GONE);
				}
				vh.countTv.setVisibility(View.GONE);
				vh.basketNumIv.setVisibility(View.VISIBLE);

				vh.basketNumIv.setImageResource(getBasketNumResource(position));
				vh.mainRl.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.grocery_basket));
			} else {
				vh.couponIv.setVisibility(View.GONE);
				vh.countTv.setVisibility(View.GONE);
				vh.basketNumIv.setVisibility(View.GONE);

				vh.mainRl.setBackgroundDrawable(null);
			}

			String imageUrl = row.getImage();
			/**
			* @author andywu
			* @date 2014.03.03
			* 把判斷加回來，防止跳出錯誤訊息
			*/
			if (imageUrl!=null && imageUrl.length()>0)
			{
				LoadImageAsyncTask task = new LoadImageAsyncTask(imageUrl, vh.mainIv, mImageWidth, mImageHeight);
				DownloadedDrawable drawable = new DownloadedDrawable(task);
				vh.mainIv.setImageDrawable(drawable);
				task.execute();
			}
			else
				vh.mainIv.setImageDrawable(null);
			
		} catch (Exception e) {
			DEBUG.e(TAG, e.toString());
		}

		return convertView;
	}

	private int getBasketNumResource(int position) {
		int pos = (position + 1) % 10;
		if (pos == 1)
			return R.drawable.basket_number_1;
		else if (pos == 2)
			return R.drawable.basket_number_2;
		else if (pos == 3)
			return R.drawable.basket_number_3;
		else if (pos == 4)
			return R.drawable.basket_number_4;
		else if (pos == 5)
			return R.drawable.basket_number_5;
		else if (pos == 6)
			return R.drawable.basket_number_6;
		else if (pos == 7)
			return R.drawable.basket_number_7;
		else if (pos == 8)
			return R.drawable.basket_number_8;
		else if (pos == 9)
			return R.drawable.basket_number_9;
		else
			return R.drawable.basket_number_10;
	}

	class ViewHolder {
		RelativeLayout mainRl;
		ImageView mainIv;
		TextView nameTv;
		TextView countTv;
		ImageView couponIv;
		ImageView basketNumIv;
	}

	public void setData(ArrayList<GroceryCellView> list) {
		mList = list;
	}

	public ArrayList<GroceryCellView> getData() {
		return mList;
	}

	public void setAppendNum(int appendNum) {
		mAppendNum = appendNum;
	}

	public int getAppendNum() {
		return mAppendNum;
	}
}

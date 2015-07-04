package com.kentec.milkbox.homedine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.data.FoodItem;

public class FoodAdapter extends BaseAdapter {

	private HomeDineDeliveryActivity mActivity;
	private LayoutInflater mInflater;
	private int nowItem = 0;

	public FoodAdapter(HomeDineDeliveryActivity activity) {
		this.mActivity = activity;
		this.mInflater = LayoutInflater.from(mActivity);
	}

	public int getNowItem() {
		return nowItem;
	}

	public void setNowItem(int nowItem) {
		this.nowItem = nowItem;
	}

	@Override
	public int getCount() {
		return mActivity.getCache().getFoodData().getFoodList().size();
	}

	@Override
	public FoodItem getItem(int position) {
		return mActivity.getCache().getFoodData().getFoodList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_homedine_food, null);

			viewHolder = new ViewHolder();
			viewHolder.rowLinear = (LinearLayout) convertView.findViewById(R.id.rowLinear);
			viewHolder.couponImg = (ImageView) convertView.findViewById(R.id.couponImg);
			viewHolder.foodImg = (ImageView) convertView.findViewById(R.id.foodImg);
			viewHolder.foodName = (TextView) convertView.findViewById(R.id.foodName);

			mActivity.getViewGroup().getRelayout().foodItem(viewHolder);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		FoodItem data = mActivity.getCache().getFoodData().getFoodList().get(position);

		viewHolder.foodName.setText(data.getName());

		Coupon sku = mActivity.getCache().getCouponMap().get(data.getSKU());
		if (sku != null) {
			viewHolder.couponImg.setVisibility(View.VISIBLE);
		} else {
			viewHolder.couponImg.setVisibility(View.GONE);
		}

		mActivity.getCache().showfoodThumb(data.getId(), data.getThumbnail(), viewHolder.foodImg);

		return convertView;
	}

	public final class ViewHolder {
		public LinearLayout rowLinear;
		public ImageView foodImg;
		public ImageView couponImg;
		public TextView foodName;
	}

}
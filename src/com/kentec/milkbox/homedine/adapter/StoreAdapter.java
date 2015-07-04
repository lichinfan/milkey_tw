package com.kentec.milkbox.homedine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.data.StoreData;

public class StoreAdapter extends BaseAdapter {

	private HomeDineDeliveryActivity mActivity;
	private LayoutInflater mInflater;

	public StoreAdapter(HomeDineDeliveryActivity context) {
		this.mActivity = context;
		this.mInflater = LayoutInflater.from(mActivity);
	}

	@Override
	public int getCount() {
		return mActivity.getCache().getStoreList().size();
	}

	@Override
	public StoreData getItem(int position) {
		return mActivity.getCache().getStoreList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_homedine_store, null);

			viewHolder = new ViewHolder();
			viewHolder.rowLinear = (LinearLayout) convertView.findViewById(R.id.rowLinear);
			viewHolder.storeBg = (RelativeLayout) convertView.findViewById(R.id.storeBg);
			viewHolder.storeImg = (ImageView) convertView.findViewById(R.id.storeImg);
			viewHolder.storeName = (TextView) convertView.findViewById(R.id.storeName);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		mActivity.getViewGroup().getRelayout().storeitem(viewHolder);

		StoreData data = mActivity.getCache().getStoreList().get(position);
		viewHolder.storeName.setText(data.getStoreName());
		mActivity.getCache().showStoreThumb(data.getStoreID(), data.getStoreThumbnail(), viewHolder.storeImg);

		return convertView;
	}

	public class ViewHolder {
		public LinearLayout rowLinear;
		public RelativeLayout storeBg;
		public ImageView storeImg;
		public TextView storeName;
	}
}
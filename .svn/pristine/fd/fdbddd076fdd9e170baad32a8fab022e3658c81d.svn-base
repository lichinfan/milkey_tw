package com.kentec.milkbox.homedine.view.homedineprogress;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.data.OrderInfoItems;

public class HomeDineProgressFoodAdapter extends BaseAdapter {

	private HomeDineDeliveryActivity mActivity;
	private LayoutInflater mInflater;
	private ArrayList<OrderInfoItems> foodList;

	public HomeDineProgressFoodAdapter(HomeDineDeliveryActivity activity, ArrayList<OrderInfoItems> itemList) {
		this.mActivity = activity;
		this.mInflater = LayoutInflater.from(mActivity);
		this.foodList = itemList;
	}

	public void upFoodList(ArrayList<OrderInfoItems> foodList) {
		this.foodList = foodList;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return foodList.size();
	}

	@Override
	public OrderInfoItems getItem(int position) {
		return foodList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_homedine_progress_food, null);

			viewHolder = new ViewHolder();
			viewHolder.foodImg = (ImageView) convertView.findViewById(R.id.foodImg);

//			mActivity.getViewGroup().getRelayout().foodItem(viewHolder);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		mActivity.getCache().showfoodProgressThumb(foodList.get(position).getProductId(), foodList.get(position).getImgThumbnail(), viewHolder.foodImg);

		return convertView;
	}

	public final class ViewHolder {
		public ImageView foodImg;
	}

}
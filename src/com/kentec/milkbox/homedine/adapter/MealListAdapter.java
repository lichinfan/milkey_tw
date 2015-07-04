package com.kentec.milkbox.homedine.adapter;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.data.MealsFood;
import com.kentec.milkbox.utils.Format;

public class MealListAdapter extends BaseAdapter {

	private HomeDineDeliveryActivity mActivity;
	private LayoutInflater mInflater;
	private ArrayList<MealsFood> list;
	private int showDel = 0;

	public MealListAdapter(HomeDineDeliveryActivity context, ArrayList<MealsFood> list) {
		this.mActivity = context;
		this.mInflater = LayoutInflater.from(mActivity);
		this.list = list;
	}

	public void setShowDel(int showDel) {
		this.showDel = showDel;
	}

	public int getCaloriesCount() {
		int calories = 0;
		for (int i = 0; i < list.size(); i++) {
			calories += (list.get(i).getFoodItem().getCalorie() * list.get(i).getFoodQuantity());
		}
		return calories;
	}

	public void upList(ArrayList<MealsFood> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public MealsFood getItem(int index) {
		return list.get(index);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		MealListViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_homedine_mealslist, null);

			viewHolder = new MealListViewHolder();
			viewHolder.rowImg = (ImageView) convertView.findViewById(R.id.rowImg);
			viewHolder.rowItems = (TextView) convertView.findViewById(R.id.rowItems);
			viewHolder.rowQuantity = (TextView) convertView.findViewById(R.id.rowQuantity);
			viewHolder.rowCalories = (TextView) convertView.findViewById(R.id.rowCalories);
			viewHolder.rowPrice = (TextView) convertView.findViewById(R.id.rowPrice);

			mActivity.getViewGroup().getRelayout().MealListRow(viewHolder);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MealListViewHolder) convertView.getTag();
		}

		String name = " " + String.valueOf(position + 1) + " " + list.get(position).getFoodItem().getName();
		String quantity = String.valueOf(list.get(position).getFoodQuantity());
		String calories = String.valueOf(list.get(position).getFoodQuantity() * list.get(position).getFoodItem().getCalorie());
		String price = Format.price(list.get(position).getFoodQuantity() * list.get(position).getFoodItem().getPrice());

		viewHolder.rowItems.setText(name);
		viewHolder.rowQuantity.setText(quantity);
		viewHolder.rowCalories.setText(calories);
		viewHolder.rowPrice.setText(price);

		mActivity.getCache().showCartThumb(list.get(position).getFoodItem().getId(), list.get(position).getFoodItem().getThumbnail(), viewHolder.rowImg);

		// if (showDel == position) {
		// viewHolder.rowItems.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete,
		// 0, 0, 0);
		// } else {
		// viewHolder.rowItems.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
		// 0);
		// }

		return convertView;
	}

	public final class MealListViewHolder {
		public ImageView rowImg;
		public TextView rowItems;
		public TextView rowQuantity;
		public TextView rowCalories;
		public TextView rowPrice;
	}

}
package com.kentec.milkbox.checkout.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.data.Country;

public class CountryAdapter extends BaseAdapter implements SpinnerAdapter {

	private ArrayList<Country> countryList;
	private LayoutInflater mInflater;
	private int nowItem = 0;

	public CountryAdapter(Context context, ArrayList<Country> countryList) {
		this.mInflater = LayoutInflater.from(context);
		this.countryList = countryList;

	}

	public int getNowItem() {
		return nowItem;
	}

	public void setNowItem(int nowItem) {
		this.nowItem = nowItem;
	}

	@Override
	public int getCount() {
		return countryList.size();
	}

	@Override
	public Country getItem(int position) {
		return countryList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_country, null);

			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		String text = countryList.get(position).getCountryId() + " " + countryList.get(position).getName();

		viewHolder.name.setText(text);

		return convertView;
	}

	public final class ViewHolder {
		public TextView name;
	}

}
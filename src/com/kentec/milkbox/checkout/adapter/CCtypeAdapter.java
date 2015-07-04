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
import com.kentec.milkbox.checkout.data.CCType;

public class CCtypeAdapter extends BaseAdapter implements SpinnerAdapter {

	private ArrayList<CCType> ccTypeList;
	private LayoutInflater mInflater;
	private int nowItem = 0;

	public CCtypeAdapter(Context context, ArrayList<CCType> ccTypeList) {
		this.mInflater = LayoutInflater.from(context);
		this.ccTypeList = ccTypeList;

	}

	public int getNowItem() {
		return nowItem;
	}

	public void setNowItem(int nowItem) {
		this.nowItem = nowItem;
	}

	@Override
	public int getCount() {
		return ccTypeList.size();
	}

	@Override
	public CCType getItem(int position) {
		return ccTypeList.get(position);
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

		String text = ccTypeList.get(position).getName();

		viewHolder.name.setText(text);

		return convertView;
	}

	public final class ViewHolder {
		public TextView name;
	}

}
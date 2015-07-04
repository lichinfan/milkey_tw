package com.kentec.milkbox.homedine.view.homedineprogress;

import java.text.ParseException;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.data.OrderInfoStatusHistory;
import com.kentec.milkbox.utils.DateUtil;

public class HomeDineProgressStatusAdapter extends BaseAdapter {

	private HomeDineDeliveryActivity mActivity;
	private LayoutInflater mInflater;
	private ArrayList<OrderInfoStatusHistory> list;

	public HomeDineProgressStatusAdapter(HomeDineDeliveryActivity activity, ArrayList<OrderInfoStatusHistory> list) {
		this.mActivity = activity;
		this.mInflater = LayoutInflater.from(mActivity);
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public OrderInfoStatusHistory getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_homedine_progress_status, null);

			viewHolder = new ViewHolder();
			viewHolder.time = (TextView) convertView.findViewById(R.id.time);
			viewHolder.status = (TextView) convertView.findViewById(R.id.status);
			viewHolder.comment = (TextView) convertView.findViewById(R.id.comment);

			// mActivity.getViewGroup().getRelayout().foodItem(viewHolder);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		try {
			String localTime = DateUtil.toLocalTime(list.get(position).getCreatedAt());
			viewHolder.time.setText(DateUtil.getAgo(mActivity, localTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		mActivity.getMethod().showProgressColor(viewHolder.status, list.get(position).getStatus());
		viewHolder.comment.setText(list.get(position).getComment());

		return convertView;
	}

	public final class ViewHolder {
		public TextView time;
		public TextView status;
		public TextView comment;
	}

}
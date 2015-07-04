package com.kentec.milkbox.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.utils.Format;

public class OrderInfoOverviewAdapter extends BaseAdapter {
	
	private int mBaseWidth;
	private LayoutInflater mLayoutInflater;
	
	private ArrayList<Product> mList;
		
	OrderInfoOverviewAdapter(Context context, ArrayList<Product> list, int width, int height) {
		mList = list;
		mBaseWidth = width / 10;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
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
			convertView = mLayoutInflater.inflate(R.layout.view_order_info_overview_row, vg, false);
			
			vh.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
			vh.priceTv = (TextView) convertView.findViewById(R.id.priceTv);
			vh.qtyTv = (TextView) convertView.findViewById(R.id.qtyTv);			
			vh.subtotalTv = (TextView) convertView.findViewById(R.id.subtotalTv);

			relayout(vh.nameTv,3);
			relayout(vh.priceTv,1);
			relayout(vh.qtyTv,1);
			relayout(vh.subtotalTv,2);
						
			convertView.setTag(vh);
			
		} else {			
			vh =  (ViewHolder) convertView.getTag();			
		}
		
		Product p = mList.get(position);
		
		vh.nameTv.setText(p.getName());
		vh.priceTv.setText(Double.toString(p.getPrice()));
		vh.qtyTv.setText(Double.toString(p.getQty()));
		vh.subtotalTv.setText(Format.price(p.getSubtotal()));
		
		return convertView;
	}
		
	class ViewHolder {
		TextView nameTv;
		TextView priceTv;
		TextView qtyTv;
		TextView subtotalTv;
	}		
	

	private void relayout(TextView tv, int base) {
		LayoutParams params = tv.getLayoutParams();
		params.width = mBaseWidth * base;
		tv.setLayoutParams(params);		
	}
	
}

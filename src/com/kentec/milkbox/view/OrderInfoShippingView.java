package com.kentec.milkbox.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.data.OrderInfo;
import com.kentec.milkbox.data.ShippingAddress;

public class OrderInfoShippingView extends LinearLayout {
	
	public OrderInfoShippingView(Context context) {
		super(context);
		View.inflate(context, R.layout.view_order_info_shipping, this);
	}
	
	public void setOrderInfo(OrderInfo oi) {
		
		TextView nameTv = (TextView) findViewById(R.id.nameTv);
		TextView addrTv = (TextView) findViewById(R.id.addrTv);
		TextView shippingMethodTv = (TextView) findViewById(R.id.shippingMethodTv);
		
		ShippingAddress sa = oi.getShippAddr();
		nameTv.setText(sa.getFirstname()+sa.getLastname());		
		addrTv.setText(sa.getStreet()+sa.getCity()+sa.getPostCode());		
		shippingMethodTv.setText(oi.getShippingMethod());
		
	}
	
}

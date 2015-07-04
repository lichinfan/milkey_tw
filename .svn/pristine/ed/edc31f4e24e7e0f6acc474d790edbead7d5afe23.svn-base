package com.kentec.milkbox.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.data.OrderInfo;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.utils.Format;


public class OrderInfoOverviewView extends LinearLayout {
	
	private Context mContext;
	
	private int mWidth;
	private int mHeight;
	private int mBaseWidth;
	
	public OrderInfoOverviewView(Context context, int width, int height ) {
		super(context);
		mContext = context;
		mWidth = width;
		mHeight = height;
		mBaseWidth = width / 10;
		View.inflate(context, R.layout.view_order_info_overview, this);				
	}
	
	public void setOrderInfo(OrderInfo oi) {
		ArrayList<Product>  productList = oi.getProductList();
		
		TextView nameTv = (TextView) findViewById(R.id.nameTv);
		TextView priceTv = (TextView) findViewById(R.id.priceTv);
		TextView qtyTv = (TextView) findViewById(R.id.qtyTv);
		TextView rowSubtotalTv = (TextView) findViewById(R.id.rowSubtotalTv);
		
		relayout(nameTv,3);
		relayout(priceTv,1);
		relayout(qtyTv,1);
		relayout(rowSubtotalTv,2);
		
		ListView mainLv = (ListView) findViewById(R.id.mainLv);
		OrderInfoOverviewAdapter adapter = new OrderInfoOverviewAdapter(mContext,productList,mWidth,mHeight);
		mainLv.setAdapter(adapter);
		
		TextView subtotalTv = (TextView) findViewById(R.id.subtotalTv);
		TextView shippingPirceTv = (TextView) findViewById(R.id.shippingPirceTv);
		TextView totalTv = (TextView) findViewById(R.id.totalTv);
		
		subtotalTv.setText(Format.price(oi.getSubtotal()));
		shippingPirceTv.setText(Format.price(oi.getShippingPrice()));
		totalTv.setText(Format.price(oi.getTotal()));		
		
		mainLv.requestFocus();
	}


	private void relayout(TextView tv, int base) {
		ViewGroup.LayoutParams params = tv.getLayoutParams();
		params.width = mBaseWidth * base;
		tv.setLayoutParams(params);		
	}	
}

package com.kentec.milkbox.checkout.view;

import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.data.ShippingMethod;
import com.kentec.milkbox.checkout.task.CheckoutTaskSetShippingMethod;

public class ShippingMethodView extends LinearLayout {

	protected CheckoutActivity mActivity;
	protected TextView titleTextView;
	protected TextView currentTextView;
	protected ListView shippingMethodListView;

	public ShippingMethodView(CheckoutActivity activity) {
		super(activity);
		this.mActivity = activity;
		View.inflate(mActivity, R.layout.view_checkout_shipping_method, this);
		titleTextView = (TextView) findViewById(R.id.titleTextView);
		currentTextView = (TextView) findViewById(R.id.currentTextView);
		shippingMethodListView = (ListView) findViewById(R.id.shippingMethodListView);
	}

	public void show() {
		String[] keys = { "shippingMethod" };
		int[] rids = { R.id.textView };
		List<HashMap<String, String>> shippingMethodList = mActivity.getData().getShippingMethodSimpleList();
		SimpleAdapter adapter = new SimpleAdapter(mActivity, shippingMethodList, R.layout.row_checkout_shippingmothed, keys, rids);
		shippingMethodListView.setAdapter(adapter);
		shippingMethodListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				setOrderShippingMethod(mActivity.getData().getShippingMethodList().get(arg2));
				mActivity.reTimeOut();
			}
		});
		shippingMethodListView.requestFocus();
		showCurrent();
	}

	private void setOrderShippingMethod(ShippingMethod shippingMethod) {
		new CheckoutTaskSetShippingMethod(mActivity, shippingMethod).execute();
	}

	private void showCurrent() {
		ShippingMethod current = mActivity.getOrder().getShippingMethod();
		if (current != null) {
			String tmpText = current.getCarrierTitle() + " " + current.getMethodTitle() + " " + current.getPrice();
			currentTextView.setText(tmpText);
			mActivity.getViewGroup().getNextButton().setVisibility(View.VISIBLE);
			mActivity.getViewGroup().getNextButton().requestFocus();
		} else {
			mActivity.getViewGroup().getNextButton().setVisibility(View.GONE);
		}
	}
}

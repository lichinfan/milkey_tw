package com.kentec.milkbox.checkout.view;

import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.data.Address;
import com.kentec.milkbox.checkout.task.CheckoutTaskDeleteAddress;
import com.kentec.milkbox.checkout.task.CheckoutTaskDeleteAddress.OnDeleteAddressListener;
import com.kentec.milkbox.checkout.task.CheckoutTaskLoadAddress;
import com.kentec.milkbox.checkout.task.CheckoutTaskLoadAddress.OnLoadAddressListener;
import com.kentec.milkbox.checkout.task.CheckoutTaskSetBillingAddress;
import com.kentec.milkbox.checkout.view.NewAddressView.OnAddressSaveListener;
import com.kentec.milkbox.homedine.view.HomeDineMealsDeleteDioLog;
import com.kentec.milkbox.homedine.view.HomeDineMealsDeleteDioLog.OnDeleteListener;

public class BillingAddressView extends LinearLayout {

	protected CheckoutActivity mActivity;

	protected TextView currentTextView;

	protected TextView titleTextView;
	private Button addAddressButton;
	protected ListView billingListView;
	private NewAddressView mNewAddressView;

	public BillingAddressView(CheckoutActivity activity) {
		super(activity);
		this.mActivity = activity;
		View.inflate(activity, R.layout.view_checkout_billing_address, this);

		currentTextView = (TextView) findViewById(R.id.currentTextView);

		titleTextView = (TextView) findViewById(R.id.titleTextView);
		addAddressButton = (Button) findViewById(R.id.addAddressButton);
		billingListView = (ListView) findViewById(R.id.billingListView);
		mNewAddressView = new NewAddressView(mActivity, this, addressSaveListener);

		addAddressButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (billingListView.getVisibility() == View.VISIBLE) {
					showAddressView();
				} else {
					closeAddAddressView();
				}
			}
		});
	}

	public void show() {
		String[] keys = { "address" };
		int[] rids = { R.id.textView };
		final List<HashMap<String, String>> addressList = mActivity.getData().getAddressSimpleList();
		SimpleAdapter adapter = new SimpleAdapter(mActivity, addressList, R.layout.row_checkout_billingaddress, keys, rids);
		billingListView.setAdapter(adapter);
		billingListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (!mActivity.getString(R.string.no_data).equals(addressList.get(0).get("address"))) {
					setOrderAddress(mActivity.getData().getAddrList().get(arg2));
					mActivity.reTimeOut();
				}
			}
		});
		billingListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (!mActivity.getString(R.string.no_data).equals(addressList.get(0).get("address"))) {
					showDeleteAddress(mActivity.getData().getAddrList().get(arg2));
					mActivity.reTimeOut();
				}
				return true;
			}
		});
		billingListView.requestFocus();
		showCurrent();
	}

	protected void setOrderAddress(Address address) {
		new CheckoutTaskSetBillingAddress(mActivity, address).execute();
	}

	/***************************************
	 ************* 讀取現有的地址 ************
	 ***************************************/
	private void loadAddress() {
		new CheckoutTaskLoadAddress(mActivity, new OnLoadAddressListener() {
			@Override
			public void OK() {
				show();
				showChoose();
			}

			@Override
			public void Error(String msg) {
				mActivity.showMsg(msg);
				showChoose();
			}
		});
	}

	/***************************************
	 ************* 刪除地址確認 *************
	 ***************************************/
	private void showDeleteAddress(final Address address) {
		HomeDineMealsDeleteDioLog dd = new HomeDineMealsDeleteDioLog(mActivity);
		dd.setMsg(address.getStreet1() + " " + address.getStreet2());
		dd.setListener(new OnDeleteListener() {

			@Override
			public void rightButton() {

			}

			@Override
			public void leftButton() {
				new CheckoutTaskDeleteAddress(mActivity, address.getId(), new OnDeleteAddressListener() {
					@Override
					public void OK() {
						loadAddress();
					}

					@Override
					public void Error(String msg) {
						mActivity.showMsg(msg);
					}
				});
			}
		});
		dd.show();
	}

	/***************************************
	 ************* 顯示當前設定 *************
	 ***************************************/
	protected void showCurrent() {
		Address current = mActivity.getOrder().getBillingAddress();
		if (current != null) {
			String tmpText = current.getStreet1() + " " + current.getStreet2() + " " + current.getCity() + " " + current.getPostCode();
			currentTextView.setText(tmpText);
			mActivity.getViewGroup().getNextButton().setVisibility(View.VISIBLE);
			mActivity.getViewGroup().getNextButton().requestFocus();
		} else {
			mActivity.getViewGroup().getNextButton().setVisibility(View.GONE);
		}
	}

	/***************************************
	 ************* 監聽新增完成 *************
	 ***************************************/
	private OnAddressSaveListener addressSaveListener = new OnAddressSaveListener() {
		@Override
		public void complete() {
			loadAddress();
		}

		@Override
		public void error(String msg) {
			showChoose();
		}
	};

	/***************************************
	 ************* 顯示新增地址 *************
	 ***************************************/
	private void showAddressView() {
		TranslateAnimation topOut = new TranslateAnimation(0, 0, 0, -mActivity.getDisH());
		topOut.setDuration(500);
		topOut.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				billingListView.setVisibility(View.GONE);
				titleTextView.setText(R.string.checkout_addaddress_new_address);
				addAddressButton.setText(R.string.checkout_select_address);
				mNewAddressView.openAddAddressView();
			}
		});
		billingListView.startAnimation(topOut);
	}

	/***************************************
	 ************* 關閉新增地址 *************
	 ***************************************/
	public void closeAddAddressView() {
		mNewAddressView.closeAddAddressView(true);
	}

	/***************************************
	 ************* 顯示選擇地址 *************
	 ***************************************/
	private void showChoose() {
		if (billingListView.getVisibility() != View.VISIBLE) {
			mActivity.getViewGroup().getAnimUtil().TopIn(billingListView, 500);
			billingListView.setVisibility(View.VISIBLE);
			titleTextView.setText(R.string.checkout_select_billing_address);
			addAddressButton.setText(R.string.checkout_add_address);
		}
	}
}
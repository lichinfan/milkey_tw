package com.kentec.milkbox.checkout.view;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.adapter.CountryAdapter;
import com.kentec.milkbox.checkout.data.Address;
import com.kentec.milkbox.checkout.data.Country;
import com.kentec.milkbox.checkout.task.CheckoutTaskAddAddress;
import com.kentec.milkbox.checkout.task.CheckoutTaskAddAddress.OnAddAddressOkListener;
import com.kentec.milkbox.checkout.task.CheckoutTaskLoadCountry;
import com.kentec.milkbox.checkout.task.CheckoutTaskLoadCountry.OnLoadCountryListener;

public class NewAddressView {

	private CheckoutActivity mActivity;
	private View mView;

	private EditText firstnameEditText;
	private EditText lastnameEditText;
	private EditText street1EditText;
	private EditText street2EditText;
	private EditText cityEditText;
	private Spinner countrySpinner;
	private EditText postcodeEditText;
	private EditText telephoneEditText;
	private Button addButton;
	private ArrayList<Country> countryList;

	public NewAddressView(CheckoutActivity activity, BillingAddressView billingAddressView, OnAddressSaveListener listener) {
		this.mActivity = activity;
		this.mView = (View) billingAddressView.findViewById(R.id.addAddressView);
		this.listener = listener;

		this.firstnameEditText = (EditText) mView.findViewById(R.id.firstnameEditText);
		this.lastnameEditText = (EditText) mView.findViewById(R.id.lastnameEditText);
		this.street1EditText = (EditText) mView.findViewById(R.id.street1EditText);
		this.street2EditText = (EditText) mView.findViewById(R.id.street2EditText);
		this.cityEditText = (EditText) mView.findViewById(R.id.cityEditText);
		this.countrySpinner = (Spinner) mView.findViewById(R.id.countrySpinner);
		this.postcodeEditText = (EditText) mView.findViewById(R.id.postcodeEditText);
		this.telephoneEditText = (EditText) mView.findViewById(R.id.telephoneEditText);
		this.addButton = (Button) mView.findViewById(R.id.addButton);
	}

	/***************************************
	 *********** 新增地址到Server ***********
	 ***************************************/
	private void addAddressToServer() {
		Address address = new Address();

		address.setFirstName(firstnameEditText.getText().toString().trim());
		address.setLastName(lastnameEditText.getText().toString().trim());
		address.setStreet1(street1EditText.getText().toString().trim());
		address.setStreet2(street2EditText.getText().toString().trim());
		address.setCity(cityEditText.getText().toString().trim());
		address.setCountryId(((Country) countrySpinner.getSelectedItem()).getCountryId());
		address.setPostCode(postcodeEditText.getText().toString().trim());
		address.setTelePhone(telephoneEditText.getText().toString().trim());

		new CheckoutTaskAddAddress(mActivity, address, new OnAddAddressOkListener() {
			@Override
			public void OK() {
				firstnameEditText.setText("");
				lastnameEditText.setText("");
				street1EditText.setText("");
				street2EditText.setText("");
				cityEditText.setText("");
				countrySpinner.setSelection(0);
				postcodeEditText.setText("");
				telephoneEditText.setText("");

				closeAddAddressView(true);
			}

			@Override
			public void Error(String msg) {
				mActivity.showMsg(msg);
			}
		});
	}

	/***************************************
	 ************* 打開新增地址 *************
	 ***************************************/
	public void openAddAddressView() {
		new CheckoutTaskLoadCountry(mActivity, new OnLoadCountryListener() {
			@Override
			public void OK(ArrayList<Country> list) {
				countryList = list;

				if (mView.getVisibility() != View.VISIBLE) {
					mActivity.getViewGroup().getAnimUtil().TopIn(mView, 500);
					mView.setVisibility(View.VISIBLE);
					mView.requestFocus();
				}

				countrySpinner.setAdapter(new CountryAdapter(mActivity, countryList));

				addButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						addAddressToServer();
					}
				});
			}

			@Override
			public void Error(String msg) {
				mActivity.showMsg(msg);
				closeAddAddressView(false);
			}
		});
	}

	/***************************************
	 ************* 關閉新增地址 *************
	 ***************************************/
	public void closeAddAddressView(final boolean success) {
		if (mView.getVisibility() == View.VISIBLE) {
			TranslateAnimation topOut = new TranslateAnimation(0, 0, 0, -mActivity.getDisH());
			topOut.setDuration(500);
			topOut.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation arg0) {

				}

				@Override
				public void onAnimationRepeat(Animation arg0) {

				}

				@Override
				public void onAnimationEnd(Animation arg0) {
					mView.setVisibility(View.GONE);
					if (listener != null) {
						listener.complete();
					}
				}
			});
			mView.startAnimation(topOut);
		}
	}

	private OnAddressSaveListener listener;

	public interface OnAddressSaveListener {
		public void complete();

		public void error(String msg);
	}
}
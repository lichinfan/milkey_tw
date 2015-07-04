package com.kentec.milkbox.checkout.view;

import java.util.ArrayList;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.api.OrderInfoApiParser;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.adapter.CCtypeAdapter;
import com.kentec.milkbox.checkout.data.CCType;
import com.kentec.milkbox.checkout.data.CreditCard;
import com.kentec.milkbox.checkout.data.PaymentMethod;
import com.kentec.milkbox.checkout.task.CheckoutTaskDeleteCreditCard;
import com.kentec.milkbox.checkout.task.CheckoutTaskDeleteCreditCard.OnDeleteCreditCardOkListener;
import com.kentec.milkbox.checkout.task.CheckoutTaskLoadPaymentMethod;
import com.kentec.milkbox.checkout.task.CheckoutTaskLoadPaymentMethod.OnLoadPaymentMethodListener;
import com.kentec.milkbox.homedine.view.HomeDineMealsDeleteDioLog;
import com.kentec.milkbox.homedine.view.HomeDineMealsDeleteDioLog.OnDeleteListener;
import com.kentec.milkbox.utils.DEBUG;

public class PaymentMethodView extends LinearLayout {

	private CheckoutActivity mActivity;

	private TextView currentTextView;

	private Spinner mMainSpinner;

	private View ccView;
	private Spinner ccSpinner;
	private Button ccDelButton;
	private EditText ccNameEditText;
	private EditText ccNumberEtEditText;
	private Spinner ccTypeSpinner;
	private EditText ccExpiryMMEditText;
	private EditText ccExpiryYYEditText;
	private EditText ccCidEditText;

	private int mPmSelectedIdx;

	private ArrayList<CCType> ccTypeList = new ArrayList<CCType>();

	// private ArrayList<PaymentMethod> mPmList;

	public PaymentMethodView(CheckoutActivity activity) {
		super(activity);
		this.mActivity = activity;

		View.inflate(mActivity, R.layout.view_checkout_payment_method, this);
		currentTextView = (TextView) findViewById(R.id.currentTextView);
		mMainSpinner = (Spinner) findViewById(R.id.mainSpinner);

		ccView = findViewById(R.id.ccView);
		ccSpinner = (Spinner) ccView.findViewById(R.id.ccSpinner);
		ccDelButton = (Button) ccView.findViewById(R.id.ccDelButton);
		ccNameEditText = (EditText) ccView.findViewById(R.id.CCnameEditText);
		ccTypeSpinner = (Spinner) ccView.findViewById(R.id.CCTypeSpinner);
		ccNumberEtEditText = (EditText) ccView.findViewById(R.id.CCNumberEditText);
		ccExpiryMMEditText = (EditText) ccView.findViewById(R.id.CCExpiryMMEditText);
		ccExpiryYYEditText = (EditText) ccView.findViewById(R.id.CCExpiryYYEditText);
		ccCidEditText = (EditText) ccView.findViewById(R.id.ccCidEt);

		ccNameEditText.setEnabled(false);
		ccNumberEtEditText.setEnabled(false);
		ccTypeSpinner.setEnabled(false);
		ccExpiryMMEditText.setEnabled(false);
		ccExpiryYYEditText.setEnabled(false);
		ccCidEditText.setEnabled(false);

		ccNameEditText.setClickable(false);
		ccNumberEtEditText.setClickable(false);
		ccTypeSpinner.setClickable(false);
		ccExpiryMMEditText.setClickable(false);
		ccExpiryYYEditText.setClickable(false);
		ccCidEditText.setClickable(false);

		ccNameEditText.setFocusable(false);
		ccNumberEtEditText.setFocusable(false);
		ccTypeSpinner.setFocusable(false);
		ccExpiryMMEditText.setFocusable(false);
		ccExpiryYYEditText.setFocusable(false);
		ccCidEditText.setFocusable(false);
	}

	public void show() {
		((LinearLayout) findViewById(R.id.paymentMethodLinearLayout)).setVisibility(View.GONE);
		new CheckoutTaskLoadPaymentMethod(mActivity, new OnLoadPaymentMethodListener() {

			@Override
			public void OK() {
				ArrayAdapter<String> pmAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, mActivity.getData().getPaymentMethodStringArrary());
				pmAdapter.setDropDownViewResource(R.layout.row_checkout_paymentmethod);
				mMainSpinner.setAdapter(pmAdapter);
				mMainSpinner.setOnItemSelectedListener(pmSelectedListener);

				((LinearLayout) findViewById(R.id.paymentMethodLinearLayout)).setVisibility(View.VISIBLE);
				showCurrent();
			}

			@Override
			public void Error(String msg) {
				mActivity.showOkMsg(msg);
			}
		});

	}

	/***************************************
	 ************* 顯示當前設定 *************
	 ***************************************/
	private void showCurrent() {
		PaymentMethod current = mActivity.getOrder().getPaymentMethod();
		if (current != null) {
			currentTextView.setText(current.getTitle());
			mActivity.getViewGroup().getNextButton().setVisibility(View.VISIBLE);
			mActivity.getViewGroup().getNextButton().requestFocus();
		}
	}

	/***************************************
	 ************ 監聽付款方式選擇 ************
	 ***************************************/
	private OnItemSelectedListener pmSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int idx, long arg3) {
			mPmSelectedIdx = idx;
			PaymentMethod pm = mActivity.getData().getPaymentMethodList().get(idx);
			if (OrderInfoApiParser.CC_SAVE.equals(pm.getCode())) {
				ccTypeList = pm.getCcType();
				DEBUG.e("ccTypeListSize", String.valueOf(ccTypeList.size()));
				showCreditCard();
			} else {
				mActivity.getViewGroup().getAnimUtil().TopOut(ccView, 1000);
				mMainSpinner.setNextFocusDownId(R.id.nextButton);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			mPmSelectedIdx = 0;
		}
	};

	/***************************************
	 ************ 顯示信用卡清單 ***********
	 ***************************************/
	public void showCreditCard() {
		ccTypeSpinner.setAdapter(new CCtypeAdapter(mActivity, ccTypeList));
		ArrayAdapter<String> ccAdapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, mActivity.getData().getCreditCardStringArrary());
		ccAdapter.setDropDownViewResource(R.layout.row_checkout_paymentmethod);
		ccSpinner.setAdapter(ccAdapter);
		ccSpinner.setOnItemSelectedListener(ccSelectedListener);
		ccDelButton.setOnClickListener(CCDelClickListener);

		ArrayList<CreditCard> tmpCcList = mActivity.getData().getCreditCardList();
		CreditCard tmpCc = mActivity.getOrder().getCreditCard();
		if (tmpCc != null && tmpCcList.size() > 0) {
			for (int i = 0; i < tmpCcList.size(); i++) {
				if (tmpCc.getNumber() != null && tmpCcList.get(i).getNumber().equals(tmpCc.getNumber())) {
					ccSpinner.setSelection(i);
					break;
				}
			}
		}
		mActivity.getViewGroup().getAnimUtil().TopIn(ccView, 1000);
	}

	/***************************************
	 ************* 監聽信用卡選擇 *************
	 ***************************************/
	private OnItemSelectedListener ccSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int idx, long arg3) {
			if (mActivity.getData().getCreditCardList().size() > 0) {
				setCreditCard(mActivity.getData().getCreditCardList().get(idx));
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	/***************************************
	 ************ 監聽信用卡刪除 ***********
	 ***************************************/
	private OnClickListener CCDelClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if (mActivity.getData().getCreditCardList().size() > 0) {
				HomeDineMealsDeleteDioLog dd = new HomeDineMealsDeleteDioLog(mActivity);
				dd.setImg(BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.credit_card));
				dd.setTitle(mActivity.getString(R.string.checkout_cc).toString());
				dd.setMsg(mActivity.getString(R.string.checkout_cc_do_you_delete).toString() + "\n" + ccSpinner.getSelectedItem().toString());
				dd.setleftButtonText(mActivity.getString(R.string.no).toString());
				dd.setrightButtonText(mActivity.getString(R.string.yes).toString());
				dd.setListener(new OnDeleteListener() {
					@Override
					public void rightButton() {
						delCreditCard(ccSpinner.getSelectedItemPosition());
					}

					@Override
					public void leftButton() {

					}
				});
				dd.show();
				dd.getleftButtonLinear().requestFocus();
				mActivity.reLayout().linear(dd.getMainLinear(), 720, 480);
			}
		}
	};

	/***************************************
	 ************* 刪除信用卡 ***************
	 ***************************************/
	private void delCreditCard(final int listId) {
		DEBUG.e("listId", String.valueOf(listId));
		String id = mActivity.getData().getCreditCardList().get(listId).getId();
		new CheckoutTaskDeleteCreditCard(mActivity, id, new OnDeleteCreditCardOkListener() {
			@Override
			public void OK() {
				mActivity.showMsg(R.string.ok);
				mActivity.getData().getCreditCardList().remove(listId);
				ccNameEditText.setText("");
				ccNumberEtEditText.setText("");
				ccTypeSpinner.setAdapter(new CCtypeAdapter(mActivity, ccTypeList));
				ccTypeSpinner.setSelection(0);
				ccExpiryYYEditText.setText("");
				ccExpiryMMEditText.setText("");
				ccCidEditText.setText("");
				showCreditCard();
			}

			@Override
			public void Error(String msg) {
				mActivity.showMsg(msg);
			}
		});
	}

	/***************************************
	 ************* 顯示信用卡詳細 *************
	 ***************************************/
	public void setCreditCard(CreditCard cc) {
		ccNameEditText.setText(cc.getOwner());
		ccNumberEtEditText.setText(cc.getNumber());
		for (int i = 0; i < ccTypeList.size(); i++) {
			if (ccTypeList.get(i).getId().equals(cc.getType())) {
				ccTypeSpinner.setSelection(i);
				break;
			}
		}
		ccExpiryYYEditText.setText(cc.getExpiryYear());
		ccExpiryMMEditText.setText(cc.getExpiryMonth());
		ccCidEditText.setText(cc.getCid());
	}

	// private OnItemSelectedListener ccTypeSelectedListener = new
	// OnItemSelectedListener() {
	// @Override
	// public void onItemSelected(AdapterView<?> arg0, View arg1, int idx, long
	// arg3) {
	// CCType type = mCCTypeList.get(idx);
	// mCreditCard.setType(type.getId());
	// }
	//
	// @Override
	// public void onNothingSelected(AdapterView<?> arg0) {
	// CCType type = mCCTypeList.get(0);
	// mCreditCard.setType(type.getId());
	// }
	// };

	// private String[] getCCType(ArrayList<CCType> list) {
	// String[] itemList;
	// if (list.size() == 0) {
	// itemList = new String[1];
	// itemList[0] = mActivity.getString(R.string.no_data);
	// return itemList;
	// }
	// itemList = new String[list.size()];
	// CCType type;
	// for (int i = 0; i < list.size(); i++) {
	// type = list.get(i);
	// itemList[i] = type.getName();
	// }
	// return itemList;
	// }
	//
	// private void setCCExpirySpinner() {
	//
	// mMMArr = new String[12];
	// for (int i = 0; i < 12; i++) {
	// mMMArr[i] = Integer.toString(i + 1);
	// }
	// ArrayAdapter<String> mmAdapter = new ArrayAdapter<String>(mActivity,
	// android.R.layout.simple_spinner_item, mMMArr);
	// mCcExpiryMMSpinner.setAdapter(mmAdapter);
	// mCcExpiryMMSpinner.setSelection(2);
	//
	// mYYArr = new String[8];
	// int year = Calendar.getInstance().get(Calendar.YEAR);
	// for (int i = 0; i < 8; i++) {
	// mYYArr[i] = Integer.toString(i + year);
	// }
	// ArrayAdapter<String> yyAdapter = new ArrayAdapter<String>(mActivity,
	// android.R.layout.simple_spinner_item, mYYArr);
	// mCcExpiryYYSpinner.setAdapter(yyAdapter);
	// mCcExpiryYYSpinner.setSelection(3);
	// }

	public PaymentMethod getPaymentMethod() {
		return mActivity.getData().getPaymentMethodList().get(mPmSelectedIdx);
	}

	public CreditCard getCreditCard() {
		CreditCard cc = new CreditCard();
		cc.setOwner(ccNameEditText.getText().toString());
		cc.setNumber(ccNumberEtEditText.getText().toString());
		cc.setType(((CCType) ccTypeSpinner.getSelectedItem()).getId());
		cc.setCid(ccCidEditText.getText().toString());
		cc.setExpiryYear(ccExpiryYYEditText.getText().toString());
		cc.setExpiryMonth(ccExpiryMMEditText.getText().toString());
		return cc;
	}

}
package com.kentec.milkbox.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.api.OrderInfoApiParser;
import com.kentec.milkbox.data.BillingAddress;
import com.kentec.milkbox.data.OrderInfo;
import com.kentec.milkbox.data.OrderInfoCreditCard;


public class OrderInfoPaymentView extends LinearLayout {
	
	public OrderInfoPaymentView(Context context) {
		super(context);
		View.inflate(context, R.layout.view_order_info_payment, this);				
	}
	
	public void setOrderInfo(OrderInfo oi) {

		TextView nameTv = (TextView) findViewById(R.id.nameTv);
		TextView addrTv = (TextView) findViewById(R.id.addrTv);
		
		BillingAddress ba = oi.getBillingAddr();
		nameTv.setText(ba.getFirstname()+ba.getLastname());
		addrTv.setText(ba.getStreet()+ba.getCity()+ba.getPostCode());
				
		String payment = oi.getPayment();		
		
		TextView paymentMethodTv = (TextView) findViewById(R.id.paymentMethodTv);		
		LinearLayout creditCardLl =  (LinearLayout) findViewById(R.id.creditCardLl);		
		if(payment.equals(OrderInfoApiParser.CC_SAVE)) {						
			TextView ccTypeTv = (TextView) findViewById(R.id.ccTypeTv);
			TextView ccNumTv = (TextView) findViewById(R.id.ccNumTv);
			TextView ccNameTv = (TextView) findViewById(R.id.ccNameTv);
			OrderInfoCreditCard oicc = oi.getCreditCard();
			paymentMethodTv.setText("Credit Card");
			ccTypeTv.setText(oicc.getCcType());
			ccNumTv.setText("xxxx-"+oicc.getLast4Num());
			ccNameTv.setText(oicc.getCc_owner());
			
		} else {
			paymentMethodTv.setText(payment);
			creditCardLl.setVisibility(View.INVISIBLE);
		}
		
		
	}
}

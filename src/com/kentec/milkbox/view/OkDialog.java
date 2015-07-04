package com.kentec.milkbox.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;


public class OkDialog extends Dialog implements OnShowListener {
	
	private String mMessage;
	private TextView mMsgTv;
	private BaseActivity mActivity;
	
	private CountDownTimer mCountDownTimer = new CountDownTimer(CFG.TimeOutToCloseDialog, CFG.TimeOutToCloseDialog){
		@Override
		public void onFinish() {
			try {
				OkDialog.this.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
	};
	
	public OkDialog(BaseActivity activity) {
		super(activity, R.style.Dialog_Full);
		this.mActivity = activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_ok);
		
		mMsgTv = (TextView) findViewById(R.id.msgTv);
		
		((LinearLayout) findViewById(R.id.okLinear))
		.setOnClickListener(new LinearLayout.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				mActivity.reTimeOut();
			}
		});
		
		setOnShowListener(this);
	}

	public void show(String msg) {
		this.mMessage = msg;
		super.show();
		
		mCountDownTimer.start();
	}	

	@Override
	public void onShow(DialogInterface arg0) {		
		mMsgTv.setText(mMessage);
	}
}
package com.kentec.milkbox.grocery.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.grocery.GroceryMainActivity;

public class CheckoutThanksDialog extends Dialog implements OnShowListener {

	private boolean isAniOver = false;

	private GroceryMainActivity mActivity;

	private CountDownTimer mCountDownTimer = new CountDownTimer(CFG.TimeOutToCloseDialog, CFG.TimeOutToCloseDialog) {
		@Override
		public void onFinish() {
			try {
				CheckoutThanksDialog.this.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
	};

	public void show() {
		super.show();

		mCountDownTimer.start();
	}

	public CheckoutThanksDialog(GroceryMainActivity groceryMainActivity) {
		super(groceryMainActivity, R.style.Dialog_Full);
		this.mActivity = groceryMainActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_checkout_thanks);

		final Button okBtn = (Button) findViewById(R.id.okBtn);
		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				noAniDismiss();
				mActivity.reTimeOut();
			}
		});
				
		setOnShowListener(this);
	}

	@Override
	public void onShow(DialogInterface arg0) {
		starrtAni();
	}

	@Override
	public void dismiss() {
		if (this.isShowing()) {
			if (isAniOver) {
				isAniOver = false;
				super.dismiss();
			} else {
				// AnimationExit ani = new AnimationExit();
				// ani.setAnimationListener(new AnimationListener() {
				// @Override
				// public void onAnimationStart(Animation animation) {
				// }
				//
				// @Override
				// public void onAnimationRepeat(Animation animation) {
				// }
				//
				// @Override
				// public void onAnimationEnd(Animation animation) {
				// isAniOver = true;
				// dismiss();
				// }
				// });
				// dialodRelativeLayout.startAnimation(ani);
				super.dismiss();
			}
		}

	}

	public void noAniDismiss() {
		if (this.isShowing()) {
			super.dismiss();
		}
	}

	private void starrtAni() {
		//mActivity.getMethod().getAnimUtil().LeftIn(iconImageView, 500);
		// mActivity.getMethod().getAnimUtil().AlphaIn(titleTextView, 500);
		// mActivity.getMethod().getAnimUtil().AlphaIn(msgTextView, 500);
		// mActivity.getMethod().getAnimUtil().AlphaIn(okLinear, 500);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			noAniDismiss();
		}
		return false;
	}

}
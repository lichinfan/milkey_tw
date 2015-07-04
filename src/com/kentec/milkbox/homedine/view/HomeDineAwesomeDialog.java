package com.kentec.milkbox.homedine.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;

public class HomeDineAwesomeDialog extends Dialog implements OnShowListener {

	private boolean isAniOver = false;

	private HomeDineDeliveryActivity mActivity;

	private ImageView iconImageView;
	private TextView msgTextView;
	private LinearLayout okLinear;

	private String msg = "";

	private CountDownTimer mCountDownTimer = new CountDownTimer(CFG.TimeOutToCloseDialog, CFG.TimeOutToCloseDialog) {
		@Override
		public void onFinish() {
			try {
				HomeDineAwesomeDialog.this.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
	};

	public void show(String msg) {
		this.msg = msg;
		super.show();

		mCountDownTimer.start();
	}

	public HomeDineAwesomeDialog(HomeDineDeliveryActivity context) {
		super(context, R.style.Dialog_Full);
		this.mActivity = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_homedine_awesome);

		iconImageView = (ImageView) findViewById(R.id.iconImageView);
		msgTextView = (TextView) findViewById(R.id.msgTextView);

		okLinear = (LinearLayout) findViewById(R.id.okLinear);

		okLinear.setOnClickListener(new LinearLayout.OnClickListener() {
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
		msgTextView.setText(msg);
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
		mActivity.getMethod().getAnimUtil().LeftIn(iconImageView, 500);
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
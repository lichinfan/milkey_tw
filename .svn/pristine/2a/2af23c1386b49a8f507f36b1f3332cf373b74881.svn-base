package com.kentec.milkbox.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.utils.DEBUG;

public class NoNetworkMessageDialog extends Dialog implements OnShowListener {

	private boolean isAniOver = false;

	private Context mContext;

	private LinearLayout mainLinear;
	private ImageView iconImageView;
	private TextView msgTextView;
	private LinearLayout okLinear;

	private boolean isImg;
	private String msg = "";

	public void show(String msg) {
		try {
			this.msg = msg;
			super.show();
		} catch (Exception e) {
			DEBUG.c(e);
		}
	}

	public void show(int resId) {
		try {
			this.msg = mContext.getString(resId).toString();
			super.show();
		} catch (Exception e) {
			DEBUG.c(e);
		}
	}

	public NoNetworkMessageDialog(Context context) {
		super(context, R.style.Dialog_Full);
		this.mContext = context;
		this.isImg = true;
	}

	public NoNetworkMessageDialog(Context context, boolean isImg) {
		super(context, R.style.Dialog_Full);
		this.mContext = context;
		this.isImg = isImg;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_msg);

		mainLinear = (LinearLayout) findViewById(R.id.mainLinear);

		iconImageView = (ImageView) findViewById(R.id.iconImageView);
		if (!isImg) {
			iconImageView.setVisibility(View.GONE);
		}

		msgTextView = (TextView) findViewById(R.id.msgTextView);

		okLinear = (LinearLayout) findViewById(R.id.okLinear);
		okLinear.setOnClickListener(new LinearLayout.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				noAniDismiss();
			}
		});

		setOnShowListener(this);
	}

	@Override
	public void onShow(DialogInterface arg0) {
		msgTextView.setText(msg);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			noAniDismiss();
		}
		return false;
	}

}
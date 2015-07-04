package com.kentec.milkbox.homedine.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;
import com.kentec.milkbox.utils.DEBUG;

public class HomeDineMealsDeleteDioLog extends Dialog implements OnShowListener {

	private boolean isAniOver = false;

	private BaseActivity mActivity;

	private LinearLayout mainLinear;
	private ImageView iconImageView;
	private TextView msgTextView;

	private LinearLayout leftButtonLinear;
	private LinearLayout rightButtonLinear;
	private TextView titleTextView;
	private TextView leftButtonTextView;
	private TextView rightButtonTextView;

	private Bitmap img = null;
	private String leftButtonText = "";
	private String rightButtonText = "";
	private String title = "";
	private String msg = "";

	public HomeDineMealsDeleteDioLog(BaseActivity activity) {
		super(activity, R.style.Dialog_Full);
		this.mActivity = activity;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setImg(Bitmap bmp) {
		img = bmp;
	}

	public void setleftButtonText(String text) {
		leftButtonText = text;
	}

	public void setrightButtonText(String text) {
		rightButtonText = text;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_homedine_delete);

		mainLinear = (LinearLayout) findViewById(R.id.mainLinear);

		iconImageView = (ImageView) findViewById(R.id.iconImageView);

		titleTextView = (TextView) findViewById(R.id.titleTextView);
		msgTextView = (TextView) findViewById(R.id.msgTextView);

		leftButtonLinear = (LinearLayout) findViewById(R.id.leftButtonLinear);
		rightButtonLinear = (LinearLayout) findViewById(R.id.rightButtonLinear);

		leftButtonTextView = (TextView) findViewById(R.id.leftButtonTextView);
		rightButtonTextView = (TextView) findViewById(R.id.rightButtonTextView);

		leftButtonLinear.setOnClickListener(new LinearLayout.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				noAniDismiss();
				if (listener != null)
					listener.leftButton();
				mActivity.reTimeOut();
			}
		});

		rightButtonLinear.setOnClickListener(new LinearLayout.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				noAniDismiss();
				if (listener != null)
					listener.rightButton();
				mActivity.reTimeOut();
			}
		});

		if (!"".equals(msg)) {
			msgTextView.setText(msg);
		}

		if (img != null) {
			ImageView iconImageView = (ImageView) findViewById(R.id.iconImageView);
			iconImageView.setImageBitmap(img);
		}

		if (!"".equals(leftButtonText)) {
			leftButtonTextView.setText(leftButtonText);
		}

		if (!"".equals(rightButtonText)) {
			rightButtonTextView.setText(rightButtonText);
		}

		if (!"".equals(title)) {
			titleTextView.setText(title);
		}

		setOnShowListener(this);

		if (DEBUG.MOBILEPHONE) {
			mActivity.reLayout().linear(mainLinear, 1920, 1080);
		} else {
			mActivity.reLayout().linear(mainLinear, 720, 480);
		}

		mActivity.reLayout().relative(leftButtonLinear, 300, 100, 0, 50);
		mActivity.reLayout().relative(rightButtonLinear, 300, 100, 0, 50);

	}

	@Override
	public void onShow(DialogInterface arg0) {

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

	public LinearLayout getMainLinear() {
		return mainLinear;
	}

	private OnDeleteListener listener;

	public void setListener(OnDeleteListener listener) {
		this.listener = listener;
	}

	public interface OnDeleteListener {
		public void leftButton();

		public void rightButton();
	}

	public ImageView getIconImageView() {
		return iconImageView;
	}

	public LinearLayout getleftButtonLinear() {
		return leftButtonLinear;
	}

	public LinearLayout getrightButtonLinear() {
		return rightButtonLinear;
	}

	public TextView getMsgTextView() {
		return msgTextView;
	}
}
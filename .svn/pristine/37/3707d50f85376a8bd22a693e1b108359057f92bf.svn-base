package com.kentec.milkbox.grocery.view;

import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;
import com.kentec.milkbox.view.BaseDialog;

public class SetQuantityDialog extends BaseDialog implements OnShowListener {

	private TextView mAboutTv;
	private LinearLayout mQuantityLl;
	private LinearLayout mButtonLl;
	private TextView mQuantityTv;
	private LinearLayout mDeleteLl;
	public LinearLayout mChangeLl;
	private ReLayoutUtil mRelayoutUtil;

	private String qty;
	private int mQuantity = 1;

	public SetQuantityDialog(BaseActivity activity, int quantity) {
		super(activity, R.style.Dialog_Full);
		mQuantity = quantity;
		mRelayoutUtil = activity.reLayout();
		qty = activity.getString(R.string.dialog_quantity);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_set_quantity);

		mAboutTv = (TextView) findViewById(R.id.aboutTextView);
		mQuantityLl = (LinearLayout) findViewById(R.id.quantityLiner);
		mButtonLl = (LinearLayout) findViewById(R.id.buttonLinear);

		mQuantityTv = (TextView) findViewById(R.id.quantityTextView);
		
		mDeleteLl = (LinearLayout) findViewById(R.id.deleteLinear);
		mChangeLl = (LinearLayout) findViewById(R.id.changeLinear);
		
		mAboutTv.setText(mActivity.getString(R.string.homedine_meals_about));

		mDeleteLl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				listener.delete();
			}
		});

		mChangeLl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				listener.set(mQuantity);
			}
		});

		relayout();

		setOnShowListener(this);

		mAboutTv.setText(Html.fromHtml(mActivity.getString(R.string.homedine_meals_about)));
	}

	@Override
	public void dismiss() {
		if (isShowing()) {
			super.dismiss();
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
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			if (mQuantity < 99) {
				mQuantity++;
				mQuantityTv.setText(qty + String.valueOf(mQuantity));
			}
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			if (mQuantity > 1) {
				mQuantity--;
				mQuantityTv.setText(qty + String.valueOf(mQuantity));
			}
		}
		return false;
	}

	@Override
	public void onShow(DialogInterface arg0) {
		mQuantityTv.setText(qty + String.valueOf(mQuantity));
	}

	private void relayout() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		Window window = getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.dimAmount = (float) 0.5;
		lp.x = 0;
		lp.y = 0;
		lp.width = dm.widthPixels;
		lp.height = dm.heightPixels;
		window.setAttributes(lp);
		mRelayoutUtil.absolute(mAboutTv, 1110, 120, 400, 80);
		mRelayoutUtil.absolute(mQuantityLl, 800, 140, 558, 280);
		mRelayoutUtil.absolute(mButtonLl, 540, 400, 682, 447);
	}

	private OnSetClickListener listener;

	public void setOnSetClickListener(OnSetClickListener listener) {
		this.listener = listener;
	}

	public interface OnSetClickListener {
		public void set(int quantity);
		public void delete();
	}

}

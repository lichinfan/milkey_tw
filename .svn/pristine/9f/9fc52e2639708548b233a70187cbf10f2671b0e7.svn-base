package com.kentec.milkbox.view;

import android.app.Dialog;
import android.content.Context;
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

public class AddQuantityDialog extends Dialog implements OnShowListener {

	private Context mContext;
	private TextView mAboutTv;
	private TextView mAddTv;
	private LinearLayout mQuantityLl;
	private LinearLayout mButtonLl;
	private TextView mQuantityTv;
	private LinearLayout mCancelLl;
	private LinearLayout mAddLl;
	private ReLayoutUtil mRelayoutUtil;

	private String qty;
	private String mAboutText;
	private int mQuantity = 1;

	private BaseActivity mActivity;

	public AddQuantityDialog(BaseActivity activity, String actionBtnText) {
		this(activity, actionBtnText, 1);
		this.mActivity = activity;
	}

	public AddQuantityDialog(Context context, String aboutText, int quantity) {
		super(context, R.style.Dialog_Full);
		mContext = context;
		mQuantity = quantity;
		mAboutText = aboutText;
		mRelayoutUtil = ((BaseActivity) context).reLayout();
		qty = context.getString(R.string.dialog_quantity);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_add_quantity);

		mAboutTv = (TextView) findViewById(R.id.aboutTextView);
		mQuantityLl = (LinearLayout) findViewById(R.id.quantityLiner);
		mButtonLl = (LinearLayout) findViewById(R.id.buttonLinear);

		mQuantityTv = (TextView) findViewById(R.id.quantityTextView);
		mCancelLl = (LinearLayout) findViewById(R.id.cancelLinear);
		mAddLl = (LinearLayout) findViewById(R.id.addLinear);
		mAboutTv.setText(mContext.getString(R.string.homedine_meals_about));

		mAddTv = (TextView) findViewById(R.id.addTv);
		mAddTv.setText(mAboutText);

		mCancelLl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				mActivity.reTimeOut();
			}
		});

		mAddLl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				listener.add(mQuantity);
				mActivity.reTimeOut();
			}
		});

		relayout();

		setOnShowListener(this);

		mAboutTv.setText(Html.fromHtml(mContext.getString(R.string.homedine_meals_about)));
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

	private OnAddClickListener listener;

	public void setOnAddClickListener(OnAddClickListener listener) {
		this.listener = listener;
	}

	public interface OnAddClickListener {
		public void add(int quantity);
	}

}

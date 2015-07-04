package com.kentec.milkbox.asr;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;

public class ASRSelectorDialog extends Dialog implements OnShowListener, OnItemClickListener {

	private boolean isAniOver = false;

	private BaseActivity mActivity;

	private ImageView iconImageView;
	private LinearLayout okLinear;

	private boolean isImg;

	private String[] msgs;

	private ListView msgListView;

	private EditText mEditText;

	public void showASRSelector(ArrayList<String> msgsArrayList, EditText editText) {
		this.msgs = new String[msgsArrayList.size()];
		msgsArrayList.toArray(msgs);
		
		mEditText = editText;
		
		super.show();
	}

	public ASRSelectorDialog(BaseActivity activity) {
		super(activity, R.style.Dialog_Full);
		this.mActivity = activity;
		this.isImg = true;
	}

	public ASRSelectorDialog(BaseActivity activity, boolean isImg) {
		super(activity, R.style.Dialog_Full);
		this.mActivity = activity;
		this.isImg = isImg;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_asr);

		iconImageView = (ImageView) findViewById(R.id.iconImageView);
		if (!isImg) {
			iconImageView.setVisibility(View.GONE);
		}

		msgListView = (ListView) findViewById(R.id.msgListView);
		msgListView.setOnItemClickListener(this);

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
		ArrayAdapter<String> listAdapter = 
				new ArrayAdapter<String>(mActivity, R.layout.row_asr, msgs);
		msgListView.setAdapter(listAdapter);
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

	@Override
	public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
		mEditText.append(msgs[position]);	
		
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				View view = mEditText.focusSearch(View.FOCUS_RIGHT);
				if (view != null)
					view.requestFocus();
				else {
					view = mEditText.focusSearch(View.FOCUS_DOWN);
					if (view != null)
						view.requestFocus();
				}
			}
		});
		
		noAniDismiss();
	}

}
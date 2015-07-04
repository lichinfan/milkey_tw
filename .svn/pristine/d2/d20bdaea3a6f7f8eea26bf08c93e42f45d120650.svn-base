package com.kentec.milkbox.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;

public class DeleteConfirmDialog extends Dialog {

	private Button mCancelBtn;
	private Button mDeleteBtn;
	private BaseActivity mActivity;

	public DeleteConfirmDialog(BaseActivity activity) {
		super(activity, R.style.Dialog_Full);
		this.mActivity = activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_delete);

		mDeleteBtn = (Button) findViewById(R.id.deleteBtn);
		mDeleteBtn.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				listener.delete();
				mActivity.reTimeOut();
			}
		});

		mCancelBtn = (Button) findViewById(R.id.cancelBtn);
		mCancelBtn.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				mActivity.reTimeOut();
			}
		});

	}

	private OnDeleteClickListener listener;

	public void setOnDeleteClickListener(OnDeleteClickListener listener) {
		this.listener = listener;
	}

	public interface OnDeleteClickListener {
		public void delete();
	}

}
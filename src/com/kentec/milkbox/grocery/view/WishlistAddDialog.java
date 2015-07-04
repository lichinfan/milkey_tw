package com.kentec.milkbox.grocery.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;
import com.kentec.milkbox.view.BaseDialog;

public class WishlistAddDialog extends BaseDialog {
	
	private LinearLayout mAddLl;
	private LinearLayout mDeleteLl;
	
	public WishlistAddDialog(BaseActivity activity) {
		super(activity, R.style.Dialog_Full);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_wishlist_add);

		mAddLl = (LinearLayout) findViewById(R.id.addLl);
		mDeleteLl = (LinearLayout) findViewById(R.id.deleteLl);
		
		mAddLl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				addListener.add();
				mActivity.reTimeOut();
			}
		});

		mDeleteLl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				deleteListener.delete();
				mActivity.reTimeOut();
			}
		});		
	}	
	
	private OnAddClickListener addListener;

	public void setOnAddClickListener(OnAddClickListener listener) {
		this.addListener = listener;
	}

	public interface OnAddClickListener {
		public void add();
	}	
	

	private OnDeleteClickListener deleteListener;

	public void setOnDeleteClickListener(OnDeleteClickListener listener) {
		this.deleteListener = listener;
	}

	public interface OnDeleteClickListener {
		public void delete();
	}	

}

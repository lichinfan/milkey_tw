package com.kentec.milkbox.grocery.view;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;
import com.kentec.milkbox.grocery.data.Category;
import com.kentec.milkbox.view.BaseDialog;

public class SearchDialog extends BaseDialog {

	private static final String TAG = "ProductDialog";

	private BaseActivity mActivity;
	private int mNaviSelectIdx;
	private ArrayList<Category> mNaviCategory;
	
	public SearchDialog(BaseActivity activity, int naviSelectIdx, ArrayList<Category> naviCategory) {
		super(activity, R.style.Dialog_Full);
		
		mActivity = activity;
		
		this.mNaviSelectIdx = naviSelectIdx;
		this.mNaviCategory = naviCategory;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.grocery_search_dialog);

		final EditText et = (EditText) findViewById(R.id.searchEt);
		et.setText("");
		
		et.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
					search(et, SearchDialog.this);
				}
				return false;
			}
		});

		final Button goBtn = (Button) findViewById(R.id.goBtn);
		goBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				search(et, SearchDialog.this);
			}
		});
		
		goBtn.setFocusableInTouchMode(true);
	}

	private void search(EditText et, Dialog dialog) {

		String keyword = et.getText().toString();
		if (keyword.length() < 2) {
			mActivity.showMsg(getContext().getString(R.string.msg_data_length_2));
			return;
		}

		dialog.dismiss();
		((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE))
			.hideSoftInputFromWindow(et.getWindowToken(), 0);

		if(mListener!=null) {
			mListener.search(keyword);			
		}		
	}
	
	private OnSearchListener mListener;	
	public interface OnSearchListener {
		public void search(String keywrod);
	}
	
	public void setOnSearchListener(OnSearchListener listener) {
		mListener = listener;
	}

	
}

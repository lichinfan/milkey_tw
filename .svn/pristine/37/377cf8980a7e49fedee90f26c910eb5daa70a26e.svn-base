package com.kentec.milkbox.view;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.Spinner;

import com.kentec.milkbox.homedine.utils.ActionBoardKeyHandler;

public class LoneSpinner extends Spinner {

	private ActionBoardKeyHandler mActionBoardKeyHandler;

	public LoneSpinner(Context context) {
		super(context);
		init();
	}

	private void init() {
		mActionBoardKeyHandler = new ActionBoardKeyHandler();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (mActionBoardKeyHandler.handleKey(keyCode)) {
//			if (mGroceryMainActivity != null)
//				mGroceryMainActivity.gotoActionBoard();
			return true;
		}

		return super.onKeyUp(keyCode, event);
	}
	
	private OnLongActionBoardListener listener;

	public interface OnLongActionBoardListener {
		public void longActionBoard();
	}
}
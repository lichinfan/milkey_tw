package com.kentec.milkbox.grocery.view;

import com.kentec.milkbox.R;
import com.kentec.milkbox.grocery.GroceryMainActivity;
import com.kentec.milkbox.grocery.adapter.GroceryGridViewAdapter;
import com.kentec.milkbox.homedine.utils.ActionBoardKeyHandler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.GridView;

public class ProductGridView extends GridView {

	private ActionBoardKeyHandler mActionBoardKeyHandler;
	private GroceryMainActivity mGroceryMainActivity;

	private boolean isMoveUp = false;
	private boolean isMoveDown = false;
	private boolean isMoveUpAgain = false;
	private boolean isMoveDownAgain = false;

	public ProductGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		init();
	}

	public ProductGridView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	public ProductGridView(Context context) {
		super(context);

		init();
	}

	private void init() {
		mActionBoardKeyHandler = new ActionBoardKeyHandler();
	}

	public void setGroceryMainActivity(GroceryMainActivity groceryMainActivity) {
		mGroceryMainActivity = groceryMainActivity;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		final int position = super.getSelectedItemPosition();
		final GroceryGridViewAdapter adapter = (GroceryGridViewAdapter) super.getAdapter();

		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			if (position>=(adapter.getNoAppendCount()-1)) {
				mGroceryMainActivity.gotoActionBoard();
				return true;
			}
			
			int newPos = position + 1;
			setSelection(newPos);
			if (position % 10 == 9 && !(position >= (adapter.getCount() - 10))) {
				isMoveUpAgain = true;
				pageDown();
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			if (position==0) {
				mGroceryMainActivity.gotoActionBoard();
				return true;
			}
			
			if (position % 5 == 0) {
				int newPos = position - 1;
				if (newPos >= 0) {
					setSelection(newPos);
					if (position % 10 == 0) {
						isMoveDownAgain = true;
						if (newPos==9)
							setSelection(newPos-5);
						else
							pageUp();
					}

					return true;
				} else {
					mGroceryMainActivity.findViewById(R.id.mainHsv).requestFocus();
				}
			}
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			if (position == (adapter.getNoAppendCount()-1)) {
				mGroceryMainActivity.gotoActionBoard();
				return true;
			}
			
			if ((position + 5) >= adapter.getNoAppendCount()) {
				//if (((position%10>=5) && (adapter.getNoAppendCount()%5 >= 5))
				//		|| ((position%10<5) && (adapter.getNoAppendCount()%5 < 5))) {
					mGroceryMainActivity.gotoActionBoard();
				//} else {
				//	setSelection(adapter.getNoAppendCount()-1);
					//isMoveUpAgain = true;
				//	pageDown();
				//}
				return true;
			} else {
				if ((position % 10) >= 5) {
					pageDown();
					return true;
				}
			}
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			if (position<=4) {
				mGroceryMainActivity.gotoActionBoard();
				return true;
			}
			if ((position % 10) <= 4) {
				pageUp();
				return true;
			}
		}

		return super.onKeyUp(keyCode, event);
	}

//	@Override
//	public void setSelection(int position) {
//		int rellyCount = ((GroceryGridViewAdapter) super.getAdapter()).getNoAppendCount();
//		if (position > rellyCount && !isAgainMove()) {
//			super.setSelection(rellyCount);
//		} else if (position < 0) {
//			super.setSelection(0);
//		} else {
//			super.setSelection(position);
//		}
//	}

	private boolean isAgainMove() {
		return isMoveUp && isMoveDown && isMoveUpAgain && isMoveDownAgain;
	}
	
	private void pageUp() {
		final int position = super.getSelectedItemPosition();

		if (position <= 9)
			return;

		// mGroceryMainActivity.runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		isMoveDown = true;
		setSelection(position - 10);
		// }
		// });
		// mGroceryMainActivity.runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		// setSelection(position - 5);
		mGroceryMainActivity.updateMoreORLessIv();
		// }
		// });
	}

	private void pageDown() {
		final int position = super.getSelectedItemPosition();
		final GroceryGridViewAdapter adapter = (GroceryGridViewAdapter) super.getAdapter();

		if (position > (adapter.getCount() - 10))
			return;

		// mGroceryMainActivity.runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		isMoveUp = true;
		setSelection(position + 10);
		// }
		// });

		// mGroceryMainActivity.runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		// setSelection(position + 5);
		mGroceryMainActivity.updateMoreORLessIv();
		// }
		// });
	}

	public boolean isMoveUp() {
		return isMoveUp;
	}

	public void setMoveUp(boolean isMoveUp) {
		this.isMoveUp = isMoveUp;
	}

	public boolean isMoveDown() {
		return isMoveDown;
	}

	public void setMoveDown(boolean isMoveDown) {
		this.isMoveDown = isMoveDown;
	}

	public ActionBoardKeyHandler getmActionBoardKeyHandler() {
		return mActionBoardKeyHandler;
	}

	public void setmActionBoardKeyHandler(ActionBoardKeyHandler mActionBoardKeyHandler) {
		this.mActionBoardKeyHandler = mActionBoardKeyHandler;
	}

	public GroceryMainActivity getmGroceryMainActivity() {
		return mGroceryMainActivity;
	}

	public void setmGroceryMainActivity(GroceryMainActivity mGroceryMainActivity) {
		this.mGroceryMainActivity = mGroceryMainActivity;
	}

	public boolean isMoveUpAgain() {
		return isMoveUpAgain;
	}

	public void setMoveUpAgain(boolean isMoveUpAgain) {
		this.isMoveUpAgain = isMoveUpAgain;
	}

	public boolean isMoveDownAgain() {
		return isMoveDownAgain;
	}

	public void setMoveDownAgain(boolean isMoveDownAgain) {
		this.isMoveDownAgain = isMoveDownAgain;
	}

}

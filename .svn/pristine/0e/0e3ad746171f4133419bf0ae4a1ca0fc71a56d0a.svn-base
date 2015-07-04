package com.kentec.milkbox.homedine.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.utils.DEBUG;

public class ReLayoutUtil {

	public static final int srcDisW = 1920;
	public static final int srcDisH = 1080;

	private BaseActivity mBaseActivity;
	private AnimUtil mAnimUtil;

	public ReLayoutUtil(BaseActivity baseActivity) {
		this.mBaseActivity = baseActivity;
		this.mAnimUtil = new AnimUtil(baseActivity);
	}

	// ________________________________________________________________________________
	public void frame(View view, int w, int h) {
		int newW = (int) ((double) w * mBaseActivity.getDisWsc());
		int newH = (int) ((double) h * mBaseActivity.getDisHsc());

		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		params.height = newH;

		view.setLayoutParams(params);
	}

	public void absolute(View view, int w, int h, int x, int y) {
		int newW = (int) ((double) w * mBaseActivity.getDisWsc());
		int newH = (int) ((double) h * mBaseActivity.getDisHsc());
		int newX = (int) ((double) x * mBaseActivity.getDisWsc());
		int newY = (int) ((double) y * mBaseActivity.getDisHsc());

		AbsoluteLayout.LayoutParams params = (AbsoluteLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		params.height = newH;
		params.x = newX;
		params.y = newY;

		view.setLayoutParams(params);
	}

	public void grid(View view, int w, int h) {
		int newW = (int) ((double) w * mBaseActivity.getDisWsc());
		int newH = (int) ((double) h * mBaseActivity.getDisHsc());

		LayoutParams params = view.getLayoutParams();
		params.width = newW;
		params.height = newH;

		view.setLayoutParams(params);
	}

	public void padding(View view, int left, int top, int right, int bottom) {
		int newL = (int) ((double) left * mBaseActivity.getDisWsc());
		int newT = (int) ((double) top * mBaseActivity.getDisHsc());
		int newR = (int) ((double) right * mBaseActivity.getDisWsc());
		int newB = (int) ((double) bottom * mBaseActivity.getDisHsc());

		view.setPadding(newL, newT, newR, newB);
	}

	public void margins(View view, int left, int top, int right, int bottom) {
		int newL = (int) ((double) left * mBaseActivity.getDisWsc());
		int newT = (int) ((double) top * mBaseActivity.getDisHsc());
		int newR = (int) ((double) right * mBaseActivity.getDisWsc());
		int newB = (int) ((double) bottom * mBaseActivity.getDisHsc());

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
		params.setMargins(newL, newT, newR, newB);
		view.setLayoutParams(params);
	}

	public void linear(View view, int w, int h) {
		int newW = (int) ((double) w * mBaseActivity.getDisWsc());
		int newH = (int) ((double) h * mBaseActivity.getDisHsc());

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		params.height = newH;

		view.setLayoutParams(params);
	}

	public void linear(View view, int w, int h, int x, int y) {
		int newW = (int) ((double) w * mBaseActivity.getDisWsc());
		int newH = (int) ((double) h * mBaseActivity.getDisHsc());
		int newX = (int) ((double) x * mBaseActivity.getDisWsc());
		int newY = (int) ((double) y * mBaseActivity.getDisHsc());

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		params.height = newH;
		params.setMargins(newX, newY, 0, 0);

		view.setLayoutParams(params);
	}

	public void relative(View view, int w, int h) {
		int newW = (int) ((double) w * mBaseActivity.getDisWsc());
		int newH = (int) ((double) h * mBaseActivity.getDisHsc());

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		params.height = newH;

		view.setLayoutParams(params);
	}

	public void relative(View view, int w, int h, int x, int y) {
		int newW = (int) ((double) w * mBaseActivity.getDisWsc());
		int newH = (int) ((double) h * mBaseActivity.getDisHsc());
		int newX = (int) ((double) x * mBaseActivity.getDisWsc());
		int newY = (int) ((double) y * mBaseActivity.getDisHsc());

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		params.height = newH;
		params.setMargins(newX, newY, 0, 0);

		view.setLayoutParams(params);
	}
	public void relative(View view, float scale, int x, int y) {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		
		int newW = (int) ((double) params.width * scale * mBaseActivity.getDisWsc());
		int newH = (int) ((double) params.height * scale * mBaseActivity.getDisHsc());
		int newX = (int) ((double) x * mBaseActivity.getDisWsc());
		int newY = (int) ((double) y * mBaseActivity.getDisHsc());

		params.width = newW;
		params.height = newH;
		params.setMargins(newX, newY, 0, 0);

		view.setLayoutParams(params);
	}	
	public void relative(int x, int y,View view) {
		int newX = (int) ((double) x * mBaseActivity.getDisWsc());
		int newY = (int) ((double) y * mBaseActivity.getDisHsc());

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.setMargins(newX, newY, 0, 0);

		view.setLayoutParams(params);
	}
	
	/**
	 * 只重新計算 X軸
	 * 
	 * @param x
	 * @param y
	 * @param view
	 *
	 * @author Alvin Huang
	 * @date 2014/3/20 下午4:32:18
	 */
	public void relativeXY(View view, int x, int y)
	{
		int newX = x; // (int) ((double) x * mBaseActivity.getDisWsc());
		int newY = y; // (int) ((double) y * mBaseActivity.getDisHsc());

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.setMargins(newX, newY, 0, 0);

		view.setLayoutParams(params);
	}
	
	public void relativeMargins(View view, int left, int top, int right, int bottom) {
		int newL = (int) ((double) left * mBaseActivity.getDisWsc());
		int newT = (int) ((double) top * mBaseActivity.getDisHsc());
		int newR = (int) ((double) right * mBaseActivity.getDisWsc());
		int newB = (int) ((double) bottom * mBaseActivity.getDisHsc());

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.setMargins(newL, newT, newR, newB);
		view.setLayoutParams(params);
	}

	public void updateImageView(ImageView view, int imgResId, int w, int h, int x, int y) {
		if (view.getVisibility() == View.VISIBLE) {
			mAnimUtil.AlphaOut(view, 500);
		}

		view.setImageResource(imgResId);

		int newW = (int) ((double) w * mBaseActivity.getDisWsc());
		int newH = (int) ((double) h * mBaseActivity.getDisHsc());
		int newX = (int) ((double) x * mBaseActivity.getDisWsc());
		int newY = (int) ((double) y * mBaseActivity.getDisHsc());

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		params.height = newH;
		params.setMargins(newX, newY, 0, 0);

		view.setLayoutParams(params);

		mAnimUtil.AlphaIn(view, 500);
		view.setVisibility(View.VISIBLE);
	}

	public void view(View view, int w, int h) {
		int newW = (int) ((double) w * mBaseActivity.getDisWsc());
		int newH = (int) ((double) h * mBaseActivity.getDisHsc());

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
		params.width = newW;
		params.height = newH;

		view.setLayoutParams(params);
	}

	public static void listview(ListView listView, int itemCount) {
		try {
			ListAdapter adapter = listView.getAdapter();
			if (adapter == null) {
				return;
			}

			itemCount = itemCount < adapter.getCount() ? itemCount : adapter.getCount();

			int height = 0;
			for (int i = 0; i < itemCount; i++) {
				View listItem = adapter.getView(i, null, listView);
				listItem.measure(0, 0);
				height += listItem.getMeasuredHeight();
				DEBUG.e("high", String.valueOf(height));
			}

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = height + (listView.getDividerHeight() * (itemCount - 1));
			listView.setLayoutParams(params);
		} catch (Exception e) {
			DEBUG.c(e);
		}
	}

	public static void gridView(GridView gridView, int xCount, int yCount) {
		try {
			ListAdapter adapter = gridView.getAdapter();
			if (adapter == null) {
				return;
			}

			yCount = yCount < (adapter.getCount() / xCount) + 1 ? yCount : (adapter.getCount() / xCount) + 1;

			int height = 0;
			for (int i = 0; i < yCount; i++) {
				View listItem = adapter.getView(i * xCount, null, gridView);
				listItem.measure(0, 0);
				height += listItem.getMeasuredHeight();
				DEBUG.e("high", String.valueOf(height));
			}

			ViewGroup.LayoutParams params = gridView.getLayoutParams();
			// params.height = height + (gridView.getVerticalSpacing() *
			// (itemCount - 1));
			params.height = height;
			gridView.setLayoutParams(params);
		} catch (Exception e) {
			DEBUG.c(e);
		}
	}
	public int newX(int x)
	{
		return (int) ((double) x * mBaseActivity.getDisWsc()) ;
	}
	public int newY(int y)
	{
		return (int) ((double) y * mBaseActivity.getDisHsc()) ;
	}
}
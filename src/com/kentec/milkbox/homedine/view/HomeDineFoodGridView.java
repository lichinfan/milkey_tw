package com.kentec.milkbox.homedine.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class HomeDineFoodGridView extends GridView {
	boolean isScrollbar = true;

	public HomeDineFoodGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public HomeDineFoodGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public HomeDineFoodGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void setScrollbar(boolean isScrollbar) {
		this.isScrollbar = isScrollbar;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (!isScrollbar) {
			int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
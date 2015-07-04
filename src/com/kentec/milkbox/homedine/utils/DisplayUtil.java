package com.kentec.milkbox.homedine.utils;

import android.content.Context;

import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;

public class DisplayUtil {

	public static int DipToPx(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int PxToDip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int srcWPxToPx(HomeDineDeliveryActivity context, int srcWPx) {
		int newW = (int) ((double) srcWPx * context.getProperty().getDisWsc());
		return newW;
	}

	public static int srcHPxToPx(HomeDineDeliveryActivity context, int srcHPx) {
		int newH = (int) ((double) srcHPx * context.getProperty().getDisHsc());
		return newH;
	}

}

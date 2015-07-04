package com.kentec.milkbox.utils;

import android.util.Log;

public class DEBUG {
	// release:
	 public static final boolean MODE = false;
	 public static final boolean MOBILEPHONE = false;
	 public static final boolean TESTMAGENTO = false;

	// test setupbox:
//	 public static final boolean MODE = true;
//	 public static final boolean MOBILEPHONE = false;
//	 public static final boolean TESTMAGENTO = true;
	 
	// test phone:
//	public static final boolean MODE = true;
//	public static final boolean MOBILEPHONE = true;
//	public static final boolean TESTMAGENTO = false;

	public static void e(String tag, String text) {
		if (MODE) {
			if (text == null) {
				text = "null";
			}
			Log.e(tag, text);
		}
	}

	public static void c(Exception e) {
		if (MODE)
			e.printStackTrace();
	}
}
package com.kentec.milkbox.asr;

import android.content.res.Resources;

public class ASRString {
	
	public static String[] getResourceString(Resources res, int resString) {
		String[] groceryResourceString= res.getStringArray(resString);
//		ArrayList<String> groceryArrayList = new ArrayList<String>();
//		Collections.addAll(groceryArrayList, groceryResourceString);
		
		return groceryResourceString;
	}
	
	

}

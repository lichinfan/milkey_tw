package com.kentec.milkbox.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import android.annotation.SuppressLint;
import android.content.res.Resources;

public class ASRCompare
{
	// for 字串部分比對，用來記錄比較後的值
	static public String compareValue  = "";

	// static public boolean match(List<String> ar1, List<String> ar2) {
	// HashSet<String> ar3 = new HashSet<String>();
	// ar3.clear();
	// ar3.addAll(ar1);
	// ar3.retainAll(ar2);
	//
	// if (ar3.size() > 0) {
	// return true;
	// } else {
	// return false;
	// }
	// }
	//
	// static public boolean match(String ar1, List<String> ar2) {
	// HashSet<String> ar3 = new HashSet<String>();
	// ar3.clear();
	// ar3.add(ar1);
	// ar3.retainAll(ar2);
	//
	// if (ar3.size() > 0) {
	// return true;
	// } else {
	// return false;
	// }
	// }

	@SuppressLint("DefaultLocale")
	static public boolean matchResourceArray(Resources res, int resString, String str)
	{

		String[] resourceString = res.getStringArray(resString);
		ArrayList<String> arrayList = new ArrayList<String>();
		Collections.addAll(arrayList, resourceString);

		// 將 resource 的字串全改為大寫
		ArrayList<String> upcaseArrayList = new ArrayList<String>();
		for (String s : arrayList)
		{
			upcaseArrayList.add(s.toUpperCase());
		}

		HashSet<String> result = new HashSet<String>();
		result.clear();
		// 將要比對的字串改為大寫
		result.add(str.toUpperCase());
		result.retainAll(upcaseArrayList);

		if (result.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 檢查 str 是否存在於 resString array 中，如果存在就回傳 resString array 中的字串值
	 * 
	 * @param res
	 * @param resString
	 * @param str
	 * @return
	 * 
	 * @author Alvin Huang
	 * @date 2014/2/6 下午2:28:14
	 *               2014/2/24 ... 修正語音辨識的比對方式，讓兩邊斥串都相符(長度一樣)也視為比對符合
	 */
	static public boolean compareResourceArray(Resources res, int resString, String str)
	{
		String[] resourceString = res.getStringArray(resString);
		ArrayList<String> arrayList = new ArrayList<String>();
		Collections.addAll(arrayList, resourceString);

		for (String s : arrayList)
		{
			if ((str.length() >= s.length()) && (str.substring(0, s.length()).equalsIgnoreCase(s)))
			{
				if (str.compareToIgnoreCase(s) >= 0)
				{
					compareValue = s;
					return true;
				}
			}
		}
		
		compareValue = "";
		return false;
	}
}

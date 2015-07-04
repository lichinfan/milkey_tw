package com.kentec.milkbox.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.Context;

import com.kentec.milkbox.R;

public class DateUtil {

	@SuppressLint("SimpleDateFormat")
	public static String getAgoDate(int year, int month, int day, int hour, int min, int sec) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.YEAR, year);
		calendar.add(Calendar.MONTH, month);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		calendar.add(Calendar.HOUR, hour);
		calendar.add(Calendar.MINUTE, min);
		calendar.add(Calendar.SECOND, sec);
		Date newDate = calendar.getTime();
		return sdf.format(newDate);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getAgo(Context context, String agoTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long nd = 1000 * 24 * 60 * 60;
			long nh = 1000 * 60 * 60;
			long nm = 1000 * 60;
			long ns = 1000;
			long diff;

			diff = sdf.parse(sdf.format(new Date())).getTime() - sdf.parse(agoTime).getTime();
			long day = diff / nd;
			long hour = diff % nd / nh;
			long min = diff % nd % nh / nm;
			long sec = diff % nd % nh % nm / ns;

			String text = "";

			if (day > 0) {
				//text += String.valueOf(day) + context.getText(R.string.day).toString() + " ";
				return agoTime;
			}

			if (hour > 0) {
				text += String.valueOf(hour) + " " + context.getText(R.string.hour).toString() + (hour>1?"s ":" ");
			}

			if (min > 0) {
				text += String.valueOf(min) + " " + context.getText(R.string.min).toString() + (min>1?"s ":" ");
			}

			if (sec > 0) {
				text += String.valueOf(day) + " " + context.getText(R.string.sec).toString() + (sec>1?"s ":" ");
			}

			text += context.getText(R.string.ago).toString();

			return text;
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return context.getText(R.string.unknown).toString();
	}

	public static String localDateTime(String dateTime) {
		return dateTime;
	}

	public static boolean homeDineCartTime(String localDateTime) {
		return true;
	}

	@SuppressLint("SimpleDateFormat")
	public static String toLocalTime(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = sdf.parse(dateString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.SECOND, +(TimeZone.getDefault().getRawOffset() / 1000));
		Date newDate = calendar.getTime();
		return sdf.format(newDate);
	}

	@SuppressLint("SimpleDateFormat")
	public static String toGmtTime(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = sdf.parse(dateString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.SECOND, -(TimeZone.getDefault().getRawOffset() / 1000));
		Date newDate = calendar.getTime();
		return sdf.format(newDate);
	}
}

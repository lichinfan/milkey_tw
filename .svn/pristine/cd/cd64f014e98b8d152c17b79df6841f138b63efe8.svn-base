package com.kentec.milkbox.homedine.utils;

import android.util.Log;
import android.view.KeyEvent;

public class ActionBoardKeyHandler {

	private long lastTime = 0;
	private long actionTime = 0;
	private int count = 0;

	public boolean handleKey(int keyCode) {
		boolean handled = false;
		long now = System.currentTimeMillis();

		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			Log.d(">>>", "count = " + count);
			Log.d(">>>", "now - lastTime = " + (now - lastTime));
			
			if ((now - actionTime) < 1000) {
				lastTime = now;
				return false;
			}
			
			if ((lastTime == 0) || ((now - lastTime) < 1000)) {
				count++;
				
				if (count > 1) {
					count = 0;
					actionTime = now;
					handled = true;
				}
			} else {
				count = 1;
			}
			
			lastTime = now;
		}

		return handled;
	}

}

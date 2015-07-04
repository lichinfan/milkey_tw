package com.kentec.milkbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * 開機後自動啟動的事件Receiver
 * @author andywu
 * @date 2014.03.17
 */
public class BootReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED"))
		{
	        Intent bootIntent = new Intent(context,BootActivity.class);
	        bootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        context.startActivity(bootIntent);		
		}
	}

}

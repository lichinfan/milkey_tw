package com.kentec.milkbox.asr;

import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;

/**
* @author andywu
* @date 2014.03.19
* Grocery中Checkout頁面的語音，就是結帳流程 
*/

public class GroceryCheckoutASRCreater
{
	private CheckoutActivity mCheckoutActivity ;
	
	public void init (CheckoutActivity Activity)
	{
		mCheckoutActivity = Activity ;
		initASRCMDAdapter();
	}
	
	private void initASRCMDAdapter()
	{
		mCheckoutActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mCheckoutActivity.getResources(), R.array.next))
		{
			@Override
			public void action()
			{
				mCheckoutActivity.checknext() ;
			}
			
			@Override
			public void action(String strValue)
			{}
		});
	}
}

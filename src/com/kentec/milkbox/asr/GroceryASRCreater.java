package com.kentec.milkbox.asr;

import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import com.kentec.milkbox.grocery.GroceryMainActivity;

public class GroceryASRCreater
{

	private GroceryMainActivity mGroceryMainActivity;

	public void init(GroceryMainActivity groceryMainActivity)
	{
		mGroceryMainActivity = groceryMainActivity;

		initASRCMDAdapter();
	}

	private void initASRCMDAdapter()
	{
		/**
		 * @author Wesley
		 * @date 2014.03.03
		 * 避免在 Grocery 再呼叫 grocery後，又再起一個 Grocery activity
		*/
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.grocery))
		{
			@Override
			public void action()
			{
				MilkBoxTTS.speak("您已經在生鮮購物", "echoAlreadyInGrocery");
			}
			
			@Override
			public void action(String strValue)
			{
				
			}
		});
		
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.HelpMainMenu))
		{
			@Override
			public void action()
			{
				mGroceryMainActivity.doGroceryHelpMenu();
			}
			
			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.checkout))
		{
			@Override
			public void action()
			{
				mGroceryMainActivity.openCart();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub

			}
		});

		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.wishlist))
		{
			@Override
			public void action()
			{
				mGroceryMainActivity.openWishList();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.history))
		{
			@Override
			public void action()
			{
				mGroceryMainActivity.openOrderHistory();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.search))
		{
			@Override
			public void action()
			{
				mGroceryMainActivity.openSearch();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		/**
		 * @author Wesley
		 * @date 2014.02.19
		 * Grocery 目錄 page up
		*/
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.pageup))
		{
			@Override
			public void action()
			{
				mGroceryMainActivity.openGroceryPageUp();
			}
			
			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		/**
		 * @author Wesley
		 * @date 2014.02.19
		 * Grocery 目錄 page down
		*/
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.pagedown))
		{
			@Override
			public void action()
			{
				mGroceryMainActivity.openGroceryPageDown();
			}
			
			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		/**
		 * @author Wesley
		 * @date 2014.02.19
		 * Grocery 目錄 item 1 to 10
		 */
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.item))
		{
			@Override
			public void action()
			{
			}
			
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openGroceryItem(strValue);
			}
		});
		
		/**
		 * @author Wesley
		 * @date 2014.03.18
		 * 直接用語音說商品名稱加入購物車
		 */
		// 加入語意可辦識數量，一 ~ 十
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.buytenproduct))
		{
			@Override
			public void action()
			{
			}
					
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openQuantityProductToCart(strValue, 10);
			}
		});
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.buynineproduct))
		{
			@Override
			public void action()
			{
			}
					
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openQuantityProductToCart(strValue, 9);
			}
		});
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.buyeightproduct))
		{
			@Override
			public void action()
			{
			}
					
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openQuantityProductToCart(strValue, 8);
			}
		});
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.buysevenproduct))
		{
			@Override
			public void action()
			{
			}
					
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openQuantityProductToCart(strValue, 7);
			}
		});
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.buysixproduct))
		{
			@Override
			public void action()
			{
			}
					
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openQuantityProductToCart(strValue, 6);
			}
		});
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.buyfiveproduct))
		{
			@Override
			public void action()
			{
			}
					
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openQuantityProductToCart(strValue, 5);
			}
		});
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.buyfourproduct))
		{
			@Override
			public void action()
			{
			}
					
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openQuantityProductToCart(strValue, 4);
			}
		});
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.buythreeproduct))
		{
			@Override
			public void action()
			{
			}
					
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openQuantityProductToCart(strValue, 3);
			}
		});
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.buytwoproduct))
		{
			@Override
			public void action()
			{
			}
					
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openQuantityProductToCart(strValue, 2);
			}
		});
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.buyoneproduct))
		{
			@Override
			public void action()
			{
			}
					
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openQuantityProductToCart(strValue, 1);
			}
		});
		// 說 "我想買 XX，會有數量 dialog 可選擇
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.buyproductwithnumber))
		{
			@Override
			public void action()
			{
			}
			
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openProductToCardWithNumber(strValue);
			}
		});
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.buyproduct))
		{
			@Override
			public void action()
			{
			}
			
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openProductToCart(strValue);
			}
		});
		
		
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.deleteproduct))
		{
			@Override
			public void action()
			{
			}
			
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openDeleteProductFromCart(strValue);
			}
		});

		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.menu))
		{
			@Override
			public void action()
			{
				/**
				* @author andywu
				* @date 2014.02.26
				* 加入語音
				*/
				String strMsg = "好的，回到主畫面";
				// 唸出資訊
				MilkBoxTTS.speak(strMsg, "GotoMainMenu");
				mGroceryMainActivity.gotoMainActivity();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		/**
		 * @author andywu
		 * @date 2014.02.19
		 * payment指令
		*/
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.payment))
		{
			@Override
			public void action()
			{
				mGroceryMainActivity.payment();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		/**
		 * @author andywu
		 * @date 2014.02.19
		 * add all to cart指令
		*/
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.addalltocart))
		{
			@Override
			public void action()
			{
				mGroceryMainActivity.addAllToCart();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		/**
		 * @author andywu
		 * @date 2014.02.20
		 * add to wishlist指令
		*/
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.addtowishlist))
		{
			@Override
			public void action()
			{
				mGroceryMainActivity.addToWishlist();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		/**
		 * @author andywu
		 * @date 2014.02.20
		 * add to cart指令
		*/
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.addToCart))
		{
			@Override
			public void action()
			{
				mGroceryMainActivity.addToCart();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		/**
		 * @author andywu
		 * @date 2014.03.21
		 * page * 指令
		*/
		mGroceryMainActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mGroceryMainActivity.getResources(), R.array.page))
		{
			@Override
			public void action()
			{
			}
			
			@Override
			public void action(String strValue)
			{
				mGroceryMainActivity.openGroceryPagenumber(strValue);
			}
		});
	}
}

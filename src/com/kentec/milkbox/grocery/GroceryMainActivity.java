package com.kentec.milkbox.grocery;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import com.kentec.milkbox.asr.ASRActivity;
import com.kentec.milkbox.asr.ASRCMDAdapter;
import com.kentec.milkbox.asr.GroceryASRCreater;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.coupon.task.MyCouponTask;
import com.kentec.milkbox.data.ActivityResultCode;
import com.kentec.milkbox.grocery.adapter.GroceryGridViewAdapter;
import com.kentec.milkbox.grocery.data.Category;
import com.kentec.milkbox.grocery.data.GroceryCellView;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.grocery.data.ProductResult;
import com.kentec.milkbox.grocery.task.ChangeCartTask;
import com.kentec.milkbox.grocery.task.ChangeCartTask.OnSucessListener;
import com.kentec.milkbox.grocery.task.GroceryCategoryProductTask;
import com.kentec.milkbox.grocery.task.GroceryCategoryTask;
import com.kentec.milkbox.grocery.task.GroceryInitialTask;
import com.kentec.milkbox.grocery.task.GroceryMoreProductTask;
import com.kentec.milkbox.grocery.task.GrocerySearchAddToCartTask;
import com.kentec.milkbox.grocery.task.GrocerySearchDeleteFromCartTask;
import com.kentec.milkbox.grocery.task.GrocerySearchTask;
import com.kentec.milkbox.grocery.view.CheckoutThanksDialog;
import com.kentec.milkbox.grocery.view.ProductDialog;
import com.kentec.milkbox.grocery.view.ProductGridView;
import com.kentec.milkbox.grocery.view.SearchDialog;
import com.kentec.milkbox.grocery.view.ShoppingCartDialog;
import com.kentec.milkbox.grocery.view.WishlistDialog;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;
import com.kentec.milkbox.utils.Format;
import com.kentec.milkbox.view.OrderHistoryDialog;

public class GroceryMainActivity extends ASRActivity
{
	private static final String TAG = "GroceryMaiActivity";
	public static final int RESULT_CHECKOUT = 1;

	protected LinearLayout mMainLl;
	protected ProductGridView mMainGv;
	protected HorizontalScrollView mMainHsv;
	protected TextView mCategroyTv ;

	protected GroceryGridViewAdapter mAdapter;
	protected ImageView mSearchIv;
	protected ImageView mLessIv;
	protected ImageView mMoreIv;
	protected TextView mPageTv;
	private TextView mcartTextView;
	private TextView msearchTextView ;
	protected Button mNaviBtn;

	protected String mCartId;
	protected String mStoreId;
	protected String mCouponCartId;
	protected String mCustomerId;
	protected String mKeyword;

	protected int mNaviBtnWidth;
	protected int mNaviBtnMarginRight;

	protected int mPageSize;
	protected int mCurrentPage;
	protected int mCurrentSelect;

	protected int mGridViewCellNum;
	protected int mGridViewPageNum;

	protected int mNaviSelectIdx = 0;
	protected ArrayList<Category> mNaviCategory;
	protected Stack<ArrayList<Category>> mNaviStack;
	protected Stack<Integer> mNaviIndexStack;

	protected ArrayList<Coupon> mCouponList;
	protected ArrayList<Category> mRootCategory;

	private LinearLayout cartBtn, searchBtn, wishBtn, historyBtn;
	
	public Dialog dialog ;
	
	private Boolean flagForGreetingCommand = true;
	private Boolean flagForASR=true;
	
	// 記錄使用者進入第幾層 grocery
	private int groceryLevel=2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grocery_navi);
		
		// view
		cartBtn = (LinearLayout) findViewById(R.id.cartBtn);
		searchBtn = (LinearLayout) findViewById(R.id.searchBtn);
		wishBtn = (LinearLayout) findViewById(R.id.wishBtn);
		historyBtn = (LinearLayout) findViewById(R.id.historyBtn);

		mMainHsv = (HorizontalScrollView) findViewById(R.id.mainHsv);
		mMainGv = (ProductGridView) findViewById(R.id.mainGv);
		mMainLl = (LinearLayout) findViewById(R.id.mainLl);
		mSearchIv = (ImageView) findViewById(R.id.searchIv);
		mCategroyTv = (TextView) findViewById(R.id.categroyTv);
		msearchTextView = (TextView) findViewById(R.id.searchTextView) ;
		
		mLessIv = (ImageView) findViewById(R.id.lessIv);
		mMoreIv = (ImageView) findViewById(R.id.moreIv);
		mPageTv = (TextView) findViewById(R.id.pageTv);
		mNaviBtn = (Button) findViewById(R.id.naviBtn);

		// event
		cartBtn.setOnClickListener(cartClickListener);
		wishBtn.setOnClickListener(wishClickListener);
		searchBtn.setOnClickListener(searchClickListener);
		historyBtn.setOnClickListener(historyClickListener);

		// time out
		doCheckTimeOut();

		// init Navi Bar
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mNaviBtnWidth = metrics.widthPixels / 4;
		mNaviBtnMarginRight = (int) (mNaviBtnWidth * 0.07);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mNaviBtnWidth, LayoutParams.MATCH_PARENT);
		params.setMargins(0, 0, mNaviBtnMarginRight, 0);
		mNaviBtn.setLayoutParams(params);
		mNaviBtn.setText("");
		mNaviBtn.setTextSize(18);

		mMainHsv.setVisibility(View.INVISIBLE);

		// initial vale
		mNaviStack = new Stack<ArrayList<Category>>();
		mNaviIndexStack = new Stack<Integer>();

		mCustomerId = getCustomerId();
		if (mCustomerId == null)
		{
			showMsg(R.string.msg_illegal_client);
			return;
		}

		mMainGv.setOnFocusChangeListener(mMainGvOnFocusChangeListener);
		mMainGv.setGroceryMainActivity(this);
		
		new GroceryASRCreater().init(this);
		
		mMainGv.requestFocus();
		
		mcartTextView = (TextView) findViewById(R.id.cartTextView) ;
		//new LoadCartTask(mCartId).execute();
		
	}

	@Override
	public void onStart()
	{
		super.onStart();
		mCouponCartId = getCouponCartId();
		if (mCouponCartId != null)
		{
			new MyCouponTask(mActivity, myCouponSuccessListener, mCouponCartId);
		}
		else
		{
			new GroceryInitialTask(mActivity, initSuccessListener, CFG.IMEI);
		}
	}

	@Override
	public void onBackPressed()
	{
		System.out.println("GroceryMainGrocery onBackPressed() run.");
		// 按 back 時，移除該層的語音命令
		removeASRCMDAdapter();
		flagForASR = false;
		
		if (mNaviCategory == null)
		{
			finish();
			return;
		}

		if (mNaviStack.empty())
		{
			mNaviCategory = null;
			mMainHsv.setVisibility(View.INVISIBLE);
			mCategroyTv.setText(R.string.grocery ) ;
			new GroceryCategoryTask(mActivity, categorySuccessListener, null, mKeyword);
			return;
		}

		ArrayList<Category> cList = mNaviStack.pop();
		Integer naviIndex = mNaviIndexStack.pop();

		Category category = cList.get(naviIndex);
		setNaviButton(naviIndex, cList);
		mCategroyTv.setText(category.getName() ) ; 
		new GroceryCategoryProductTask(mActivity, categoryProductSuccessListener, category.getId(), mKeyword);

		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == GroceryMainActivity.RESULT_CHECKOUT)
		{
			switch (resultCode)
			{
			case ActivityResultCode.OK:
				/**
				* @author andywu
				* @date 2014.03.19
				* @category 加入更新購物車數量動作
				*
				*/
				updatecartnumber();
				CheckoutThanksDialog dialog = new CheckoutThanksDialog(this);
				dialog.show();
				MilkBoxTTS.speak(getResources().getString(R.string.checkout_thanks_for_shopping),"showMsg"); // andywu 2014.02.20 唸出對話框的文字
				break;
			case ActivityResultCode.ERROR:
				showMsg(R.string.error);
				break;
			case ActivityResultCode.CANCEL:
				showMsg(R.string.cancel);
				break;
			}
		}
	}

	OnClickListener wishClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View view)
		{
			openWishList();
		}
	};

	OnClickListener cartClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View view)
		{
			openCart();
		}
	};

	OnClickListener searchClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View view)
		{
			openSearch();
		}
	};

	OnClickListener historyClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View view)
		{
			openOrderHistory();
		}
	};

	public void openCart()
	{
		reTimeOut();
		if (dialog != null && dialog.isShowing()) // andy 20140221 防止已有dialog時再用語音呼叫dialog
			return ;
//		Dialog dialog = new ShoppingCartDialog(mActivity, mCartId);
		dialog = new ShoppingCartDialog(mActivity, mCartId); //andy 20140219 為了語音呼叫改成用變數
		dialog.show();
	}

	public void openWishList()
	{
		//Log.d("andy","openWishList");
		if (dialog != null && dialog.isShowing()) // andy 20140221 防止已有dialog時再用語音呼叫dialog
			return ;
		if (mCustomerId == null)
		{
			showMsg(R.string.msg_illegal_client);
			return;
		}
		dialog = new WishlistDialog(GroceryMainActivity.this, mCustomerId, mCartId); //andy 20140219 為了語音呼叫改成用變數
		dialog.show();
		/**
		* @author andywu
		* @date 20140306
		* 加入互動
		* @date 2014.03.07
		* pending
		*/
//		int indexNo = (int) (Math.random() * 2) + 1; // 亂數得到 1 - 2 的值
//		moderatorDialog( "You can select an item to cancel or add to cart. \n" +
//				"Or say <Cancel> or <Add all to cart>.", indexNo, 5000);
//		MilkBoxTTS.speak("You can select an item to cancel or add to cart. ", "openWishList");
		reTimeOut();
	}

	// Alvin : 2014/03/03 ... 增加對於 compFunFlage 的處理
	public void openSearch()
	{
		if (dialog != null && dialog.isShowing()) // andy 20140221 防止已有dialog時再用語音呼叫dialog
			return ;
		if (mKeyword == null)
		{
			ArrayList<Category> searchCategory;
			if (mNaviCategory == null)
			{
				searchCategory = mRootCategory;
			}
			else
			{
				searchCategory = mNaviCategory;
			}
			SearchDialog searchDialog = new SearchDialog(mActivity, mNaviSelectIdx, searchCategory);
			searchDialog.setOnCancelListener(new OnCancelListener()
			{
				@Override
				public void onCancel(DialogInterface dialog)
				{
					ASRActivity.compFunFlage = false;
				}	
			});
			
			searchDialog.setOnDismissListener(new OnDismissListener()
			{
				@Override
				public void onDismiss(DialogInterface dialog)
				{
					ASRActivity.compFunFlage = false;
				}
			});
			
			searchDialog.setOnShowListener(new OnShowListener()
			{
				@Override
				public void onShow(DialogInterface dialog)
				{
					ASRActivity.compFunFlage = true;
				}				
			});			
			
			searchDialog.show();
			searchDialog.setOnSearchListener(searchListener);			
			/**
			* @author andywu
			* @date 20140306
			* 加入互動
			*/
			int indexNo = (int) (Math.random() * 2) + 1; // 亂數得到 1 - 2 的值
//			moderatorDialog( "請說出您要的商品", indexNo, 5000);
//			MilkBoxTTS.speak("請說出您要的商品", "searchDialog");	
//			MilkBoxTTS.speak("Please say a product name "
//					+"or type in the blank.", "searchDialog");
			moderatorInteractiveDialog("請說出您要的商品"
					, indexNo
					,"請說出您要的商品",
					"searchDialog", 9);
			}
		else
		{
			mKeyword = null;
			refreshGridView();
			mSearchIv.setBackgroundResource(R.drawable.ic_grocery_search);
			msearchTextView.setText( getResources().getText(R.string.search) );
		}

		reTimeOut();
	}

	public void openOrderHistory()
	{
		if (dialog != null && dialog.isShowing()) // andy 20140221 防止已有dialog時再用語音呼叫dialog
			return ;
		if (mCustomerId == null)
		{
			showMsg(R.string.msg_illegal_client);
			return;
		}
		if (mStoreId == null)
		{
			showMsg(R.string.msg_illegal_store);
			return;
		}
		dialog = new OrderHistoryDialog(GroceryMainActivity.this, mCustomerId, mCartId, mStoreId); //andy 20140219 為了語音呼叫改成用變數
		dialog.show();
		reTimeOut();
	}
	
	/**
	 * @author Wesley
	 * 2014.02.20
	 * 在 Grocery 語音呼叫 help
	 */
	public void doGroceryHelpMenu()
	{
		int indexNo = (int) (Math.random() * 2) + 1; // 亂數得到 1 - 2 的值

		/*
		 You can say : "Help"、"Command List"、"Checkout"、"Main Menu"、
        "Order History"、"Wish List"、"Number 1 ~ 10"、
        "Top Level"、"Search"
		 */

		// Show message
//		String strMsg[] = { "What can I say", 
//				                          ", Help", 
//				                          ", Command List", 
//				                          ", Checkout", 
//				                          ", Main Menu", 
//				                          ", Order History", 
//				                          ", Wish List", 
//				                          ", Number 1 to 10", 
//				                          ", Search"};
		String strMsg[] = { "Number 1 to 10", 
                ", Search", 
                ", Wish List", 
                ", Order History", 
                ", Checkout", 
                ", Main Menu", };
		
		/**
		* @author andywu
		* @date 2014.02.21
		* 修改呈現方式
		* 
		* 利用HTML排版
		* <big> 放大字型
		* <sub> 下標
		* <small> 縮小字型
		* <b> 粗體
		* &nbsp; 空白字元
		* 
		*/
		
//		String dialogStr = "<big>● What can I say</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>Help</b>&nbsp;&nbsp;or&nbsp;&nbsp;<b>Command List</b></small></small></sub><br>"
//						 + "<big>● Help</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
//						 + "<big>● Command List</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
//						 + "<big>● Checkout</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
//						 + "<big>● Main Menu</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
//						 + "<big>● Order History</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
//						 + "<big>● Wish List</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
//						 + "<big>● Number 1 to 10</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
//						 + "<big>● Search</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>" ;
		
		String dialogStr = "You can say :<sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 + "<big>● Number 1 to 10</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 + "<big>● Search</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 + "<big>● Wish List</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 + "<big>● Order History</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 + "<big>● Checkout</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 + "<big>● Main Menu</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				  ;		
		moderatorDialog(dialogStr, indexNo + 2, 8000);
		
		// 唸出資訊
		ArrayList<String> speakStrs = new ArrayList<String>();
		speakStrs.add("You can say:");
		Collections.addAll(speakStrs, strMsg);
		MilkBoxTTS.speak(speakStrs, "echoWhatCanISay");		
	}
	
	/**
	 * @author Wesley
	 * 2014.02.19
	 * 在 Grocery 直接用語音呼叫 page down
	 */
	public void openGroceryPageDown() {
		if (cartBtn.hasFocus()
				|| searchBtn.hasFocus()
				|| wishBtn.hasFocus()
				|| historyBtn.hasFocus()
				|| mMainHsv.hasFocus()) {
			mMainGv.requestFocus();
		}
		int position = mMainGv.getSelectedItemPosition();
		int newPos = position + 10;
		if (newPos/10 < mAdapter.getCount()/10) {
			mMainGv.setSelection((position/10+1)*10);
		}
		Log.d("pageDown", "org position: " + position);
		Log.d("pageDown", "new position: " + newPos);
	}
	
	/**
	 * @author Wesley
	 * 2014.02.19
	 * 在 Grocery 直接用語音呼叫 page up
	 */
	public void openGroceryPageUp() {
		if (cartBtn.hasFocus()
				|| searchBtn.hasFocus()
				|| wishBtn.hasFocus()
				|| historyBtn.hasFocus()
				|| mMainHsv.hasFocus()) {
			mMainGv.requestFocus();
		}
		int position = mMainGv.getSelectedItemPosition();
		int newPos = position - 10;
		if (newPos >= 0) {
			mMainGv.setSelection((position/10-1)*10);
		}
		Log.d("pageDown", "org position: " + position);
		Log.d("pageDown", "new position: " + newPos);
	}
	/**
	* @author andywu
	* @date 2014.03.21
	* @category 加入語音 "page *" 指令
	*/
	public void openGroceryPagenumber(String strValue)
	{
		Log.d("andy","openGroceryPagenumber:"+ strValue);
		int page = Integer.parseInt(strValue);
		if (page < 1)
			return ;
		if (cartBtn.hasFocus()
				|| searchBtn.hasFocus()
				|| wishBtn.hasFocus()
				|| historyBtn.hasFocus()
				|| mMainHsv.hasFocus()) {
			mMainGv.requestFocus();
		}
		int position = mMainGv.getSelectedItemPosition();
		int newPos = (page-1)*10 ;
		if ((newPos+ position%10) < mAdapter.getCount())
		{
			mMainGv.setSelection(newPos);
		}
//		Log.d("andy", "org position: " + position);
//		Log.d("andy", "new position: " + newPos);
	}	
	/**
	 * @author Wesley
	 * 2014.02.19
	 * 在 Grocery 直接用語音呼叫 item 1~10，focus會直接移動到該 item
	 */
	public void openGroceryItem(String strValue) {
		if (cartBtn.hasFocus()
				|| searchBtn.hasFocus()
				|| wishBtn.hasFocus()
				|| historyBtn.hasFocus()
				|| mMainHsv.hasFocus()) {
			mMainGv.requestFocus();
		}
		
		int position = mMainGv.getSelectedItemPosition();
		int itemNumber = parseNumber(strValue);//Integer.parseInt(strValue);
		
		int minItemNumber = 1;
		int maxItemNumber = (mRootCategory.size()%10);
		int positionPage = position/10;
		int newPos = 0;
		if ((position + 10)/10 < mAdapter.getCount()/10) {
			if(itemNumber >= minItemNumber && itemNumber <= 10) {
				newPos = positionPage*10 + itemNumber -1;
				mMainGv.setSelection(newPos);
				super.sendKey(KeyEvent.KEYCODE_ENTER);
				Log.d("pageDown", "item 1 to 10");				
			}
		} else {
			if(itemNumber >= minItemNumber && itemNumber <= maxItemNumber) {
				newPos = positionPage*10 + itemNumber -1;
				mMainGv.setSelection(newPos);
				super.sendKey(KeyEvent.KEYCODE_ENTER);
				Log.d("pageDown", "item 1 to maxitemnumber");
			}			
		}
		
		
	}
	
	/**
	 * @author Wesley
	 * 2014.02.27
	 * 在 Grocery 直接用語音呼叫 item name，focus會直接移動到該 item
	 */
	public void openGroceryItemByName(String[] categoryNameList, String strValue) {
		
		if (cartBtn.hasFocus()
				|| searchBtn.hasFocus()
				|| wishBtn.hasFocus()
				|| historyBtn.hasFocus()
				|| mMainHsv.hasFocus()) {
			mMainGv.requestFocus();
		}
		
		int position = mMainGv.getSelectedItemPosition();
		int minItemNumber = 1;
		int maxItemNumber = (mRootCategory.size()%10);
		int positionPage = position/10;
		int newPos = -1;
		for(int i=0; i<categoryNameList.length; i++) {
			if(strValue.equalsIgnoreCase(categoryNameList[i])) {
				newPos=i;
				continue;
			}
		}
		System.out.println("strValue: "+strValue);
		System.out.println("strValue: "+strValue.length());
		System.out.println("categoryNameList[0]: "+categoryNameList[0]);
		System.out.println("categoryNameList[0]: "+categoryNameList[0].length());
		System.out.println("newPos: "+newPos);
		if(newPos!=-1) {
			mMainGv.setSelection(newPos);
			super.sendKey(KeyEvent.KEYCODE_ENTER);
		}
//		int itemNumber=(newPos%10);
//		if ((position + 10)/10 < mAdapter.getCount()/10) {
//			if(itemNumber >= minItemNumber && itemNumber <= 10) {
//				mMainGv.setSelection(newPos);
//				super.sendKey(KeyEvent.KEYCODE_ENTER);
//				Log.d("pageDown", "item 1 to 10");				
//			}
//		} else {
//			if(itemNumber >= minItemNumber && itemNumber <= maxItemNumber) {
//				mMainGv.setSelection(newPos);
//				super.sendKey(KeyEvent.KEYCODE_ENTER);
//				Log.d("pageDown", "item 1 to maxitemnumber");
//			}			
//		}
		
		
	}
	
	/**
	 * @author Wesley
	 * 2014.02.20
	 * show moderator dialog and TTS speak root category name and product count
	 */
	private void moderatorTTSByCategoryNameAndProductCount(String name, int count) {
		/* Number XX 後的語音回應
		 * This is  %DB_NAME.
			There are %N of items.
			You can say <Page Down> & <Page Up> to change pages.
			Which Number of items do you want to browse?
		 */
		int indexNo = (int) (Math.random() * 2) + 1; // 亂數得到 1 - 2 的值
		String categoryName=name;
		int categoryProductCount=count;
//		moderatorDialog( "This is " + categoryName + ".\n" +
//				"There are "+categoryProductCount+" items.\n" +
//				"You can say <Page Down> & <Page Up> to change pages.\n" +
//				"Which Number of items do you want to browse?", indexNo, 6000);
		
//		MilkBoxTTS.speak("This is " + categoryName + "." +
//				"There are "+categoryProductCount+" items to choose from." +
//				"Which number of item do you want to browse?", "echoGroceryStoreCategory");
		MilkBoxTTS.speak("請選擇您要的類別名稱或編號", "echoGroceryStoreCategory");
	}

	/**
	 * @author Wesley Li
	 * 2014.03.18
	 * 用語音將產品加入 cart
	 */
	public void openProductToCardWithNumber(String strValue) {
		// search.php product
		new GrocerySearchTask(mActivity, productSearchSuccessListener, strValue);
	}
	
	public void openProductToCart(String strValue) {
		new GrocerySearchAddToCartTask(mActivity, addProductSearchSuccessListener,mCartId, strValue);
	}
	
	GrocerySearchTask.OnSucessListener productSearchSuccessListener = new GrocerySearchTask.OnSucessListener() {
		
		@Override
		public void success(ProductResult mProductResult) {
			ArrayList<Product> pList = mProductResult.getList();
			if(pList.size() > 0) {
				System.out.println("Product Search result: "+pList.get(0).getName());
				ProductDialog dialog = new ProductDialog(mActivity, mCartId, mCustomerId, pList.get(0));
				dialog.show();
				dialog.mAddCartLl.performClick();
			}
		}
		
		@Override
		public void fail(int failCode) {
			// TODO Auto-generated method stub
			
		}
	};
	
	GrocerySearchAddToCartTask.OnSucessListener addProductSearchSuccessListener = new GrocerySearchAddToCartTask.OnSucessListener() {
		
		@Override
		public void success(ProductResult mProductResult) {
			ArrayList<Product> pList = mProductResult.getList();
			int indexNo = (int) (Math.random() * 2) + 1;
			if(pList.size() > 0) {
				moderatorDialog( " ["+pList.get(0).getName()+"] 加入購物車內"
								, indexNo, 5000);
				MilkBoxTTS.speak("我已經幫您把 "+pList.get(0).getName()+" 加入購物車內"
						, "voicesearchaddtocart");	
				// andywu 2014.03.28 加入更新購物車數量
				updatecartnumber() ; 
			}
		}
		
		@Override
		public void fail(int failCode) {
			int indexNo = (int) (Math.random() * 2) + 1;
			moderatorDialog( "找不到您要的商品"
					, indexNo, 4000);
			MilkBoxTTS.speak("我找不到您要的商品"
					, "voicesearchaddtocart");
			
		}
	};

	public void openDeleteProductFromCart(String strValue) {
		new GrocerySearchDeleteFromCartTask(mActivity, deleteProductSearchSuccessListener, mCartId, strValue);
	}
	
	GrocerySearchDeleteFromCartTask.OnSucessListener deleteProductSearchSuccessListener = new GrocerySearchDeleteFromCartTask.OnSucessListener() {
		
		@Override
		public void success(String productName) {
			int indexNo = (int) (Math.random() * 2) + 1;
			moderatorDialog( " ["+productName+"] 已從購物車刪除"
					, indexNo, 5000);
			MilkBoxTTS.speak("我已經幫您把 "+productName+"  從購物車刪除"
					, "voicesearchdeletefromcart");
			// andywu 2014.03.28 加入更新購物車數量
			updatecartnumber() ; 
		}
		
		@Override
		public void fail(int failCode) {
			int indexNo = (int) (Math.random() * 2) + 1;
			moderatorDialog( "它不在購物車裡"
					, indexNo, 4000);
			MilkBoxTTS.speak("它不在購物車裡"
					, "voicesearchdeletefromcart");
			
		}
	};
	
	/**
	 * @author andywu
	 * 2014.02.19
	 * 在checkout的對話框時，語音操作呼叫的功能
	 */
	public void payment()
	{
//		Log.d("andy","payment");
		if (dialog != null && dialog instanceof ShoppingCartDialog && dialog.isShowing() )
		{
			((ShoppingCartDialog)dialog).mCheckoutLl.performClick();
		}
	}
	/**
	 * @author andywu
	 * @date 2014.02.19
	 * 在wishlist的對話框時，語音操作呼叫的功能
	 */
	public void addAllToCart()
	{
//		Log.d("andy","addAllToCart");
		if (dialog != null && dialog instanceof WishlistDialog && dialog.isShowing() )
		{
			((WishlistDialog)dialog).mAddCartLl.performClick();
		}
	}
	/**
	 * @author andywu
	 * @date 2014.02.20
	 * 在商品介紹的對話框時，語音操作呼叫的功能
	 */	
	public void addToWishlist()
	{
		Log.d("andy","addToWishlist");	
		if (dialog != null && dialog instanceof ProductDialog && dialog.isShowing() )
		{
			((ProductDialog)dialog).mAddWishlistLl.performClick();
		}
	}
	/**
	 * @author andywu
	 * @date 2014.02.20
	 * 在商品介紹的對話框時，語音操作呼叫的功能
	 */	
	public void addToCart()
	{
		Log.d("andy","addToCart");	
		if (dialog != null && dialog instanceof ProductDialog && dialog.isShowing() )
		{
			((ProductDialog)dialog).mAddCartLl.performClick();
		}
	}
	
	SearchDialog.OnSearchListener searchListener = new SearchDialog.OnSearchListener()
	{
		@Override
		public void search(String keyword)
		{
			msearchTextView.setText( getResources().getText(R.string.search_cancel) );
			mSearchIv.setBackgroundResource(R.drawable.ic_delete);
			mKeyword = URLEncoder.encode(keyword);
			refreshGridView();
		}
	};

	GroceryInitialTask.OnSucessListener initSuccessListener = new GroceryInitialTask.OnSucessListener()
	{
		@Override
		public void success(String cartId, String storeId)
		{
			if (cartId == null)
			{
				mActivity.showMsg(R.string.msg_no_shopping_cart_id);
				return;
			}
			if (storeId == null)
			{
				mActivity.showMsg(R.string.msg_illegal_store);
				return;
			}
			mCartId = cartId;
			mStoreId = storeId;
			new GroceryCategoryTask(mActivity, categorySuccessListener, null, mKeyword);
			updatecartnumber(); // andywu  2014.03.19 加入讀取購物車項目
		}
	};

	GroceryCategoryTask.OnSucessListener categorySuccessListener = new GroceryCategoryTask.OnSucessListener()
	{
		@Override
		public void success(final ArrayList<Category> cList)
		{
			groceryLevel=0;
			mRootCategory = cList;
			mCurrentPage = 1;
			mGridViewCellNum = cList.size();
			mGridViewPageNum = (mGridViewCellNum / 10) + ((mGridViewCellNum - (mGridViewCellNum / 10) * 10) > 0 ? 1 : 0);

			ArrayList<GroceryCellView> list = getList(cList, null);
			mAdapter = new GroceryGridViewAdapter(mActivity, list);
			mMainGv.setAdapter(mAdapter);
			ReLayoutUtil.gridView(mMainGv, 5, 2);
			pageAppend();

			mMainGv.setOnItemClickListener(itemClickListener);
			mMainGv.setOnItemSelectedListener(itemSelectedListener);
			mMainGv.setLongClickable(true);
			mMainGv.setOnItemLongClickListener(itemLongClickListener);
			mMainGv.requestFocus();

			updateGridViewPageView();
			updateMoreORLessIv();
			
			// Greeting
			// from Wesley
			int indexNo = (int) (Math.random() * 2) + 1; // 亂數得到 1 - 2 的值
			if(flagForGreetingCommand == true) {
				moderatorDialog( "歡迎來到生鮮購物,\n" +
						"共有 "+cList.size()+" 種類別供您選擇,\n" +
						"請選擇您要的類別名稱或編號", indexNo, 9000);
//				MilkBoxTTS.speak("Welcome to Grocery store." +
//							"There are "+cList.size()+" categories to choose from."+
//							"Please select a category or a number.", "echoGroceryStoreGretting");
				MilkBoxTTS.speak("歡迎來到生鮮購物," +
							"共有 "+cList.size()+" 種類別供您選擇,"+
							"請選擇您要的類別名稱或編號", "echoGroceryStoreGretting");
				
				// 動態加入 Category item name 至 ASR 語音辨識，可以直接用 name 到該 item
				final String[] category = new String[cList.size()];
				for(int i=0; i<cList.size(); i++) {
					category[i]=cList.get(i).getName().trim().replaceAll("&", "and").toLowerCase();
					System.out.println("category[i]: "+category[i]);
				}
				
				addASRCMDAdapter(new ASRCMDAdapter(category)
				{
					@Override
					public void action()
					{
						if(groceryLevel==0) {
							openGroceryItemByName(category,getMatchWord());
						} else {
							MilkBoxTTS.speak("沒有這樣東西", "echoGroceryNoTheseItem");
						}
					}
					
					@Override
					public void action(String strValue)
					{	
					}
				});
				
				flagForGreetingCommand = false;
			}

			if(flagForASR == false) {
				flagForASR = true;
			}
		}

		@Override
		public void fail(int failCode)
		{
			mGridViewCellNum = 0;
			mGridViewPageNum = 0;
			mPageSize = 0;

			updateGridViewPageView();
			updateMoreORLessIv();
		}
	};

	OnItemClickListener itemClickListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3)
		{
			reTimeOut();
			try
			{
				ArrayList<GroceryCellView> list = mAdapter.getData();
				GroceryCellView row = list.get(index);

				if (row instanceof Category)
				{

					ArrayList<Category> cList = getList(list);

					if (mNaviCategory != null)
					{
						mNaviStack.push(mNaviCategory);
						mNaviIndexStack.push(mNaviSelectIdx);
						
					}
					mNaviSelectIdx = index ;
					mCategroyTv.setText(row.getName() ) ; 
					
					// update navi bar
					int idx = getCategoryIndex(cList, row.getId());
					setNaviButton(idx, cList);

					// clear grid view
					clearGridViewData();

					// load data
					new GroceryCategoryProductTask(mActivity, categoryProductSuccessListener, row.getId(), mKeyword);
				
					// 呼叫 moderator dialog 與 TTS 念出 Category Name 和 product count
					moderatorTTSByCategoryNameAndProductCount(row.getName(), ((Category) row).getProductCount());
			
				}

				if (row instanceof Product)
				{
					dialog = new ProductDialog(mActivity, mCartId, mCustomerId, (Product) row); //andy 20140220 為了語音呼叫改成用變數
					dialog.show();
					/**
					* @author andywu
					* @date 20140306
					* 加入互動
					* @date 2014.03.07
					* pending
					*/
//					int indexNo = (int) (Math.random() * 2) + 1; // 亂數得到 1 - 2 的值
//					moderatorDialog( "You can say \n"
//									+"<Add to cart> or <Add to wishlist> \n"
//									+"or <Back> to last page", indexNo, 9000);
//					MilkBoxTTS.speak("You can say <Add to cart> or <Add to wishlist>"
//							+"or <Back> to last page", "ProductDialog");					
				}

			}
			catch (Exception e)
			{
				Log.e(TAG, e.toString());
			}
		}
	};

	protected void setNaviButton(int focusIdx, ArrayList<Category> list)
	{
		mNaviCategory = list;
//		mMainHsv.setVisibility(View.VISIBLE); //andy 暫時先拿掉
		mMainLl.removeAllViews();

		Category row;
		Button naviBtn;
		for (int i = 0; i < list.size(); i++)
		{

			row = list.get(i);

			naviBtn = new Button(mActivity);
			naviBtn.setTag(Integer.valueOf(i));
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mNaviBtnWidth, LayoutParams.MATCH_PARENT);
			params.setMargins(0, 0, mNaviBtnMarginRight, 0);
			naviBtn.setLayoutParams(params);
			naviBtn.setText(row.getName());
			naviBtn.setTextSize(18);
			naviBtn.setTextColor(Color.parseColor("#1c350c"));
			naviBtn.setBackgroundResource(R.drawable.grocery_category_btn);
			naviBtn.setOnClickListener(naviClickListener);
			naviBtn.setOnFocusChangeListener(new OnFocusChangeListener()
			{
				@Override
				public void onFocusChange(View view, boolean isFocused)
				{
					if (isFocused)
					{
						((Button) view).setTextColor(Color.WHITE);
					}
					else
					{
						((Button) view).setTextColor(Color.parseColor("#1c350c"));
					}
				}
			});

			if (i == focusIdx)
			{
				naviBtn.requestFocus();
			}

			mMainLl.addView(naviBtn);
		}

	}

	OnClickListener naviClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View view)
		{
			reTimeOut();
			view.requestFocus();
			clearGridViewData();
			mNaviSelectIdx = (Integer) view.getTag();
			refreshGridView();
		}
	};

	OnFocusChangeListener mMainGvOnFocusChangeListener = new OnFocusChangeListener()
	{

		@Override
		public void onFocusChange(View v, boolean hasFocus)
		{
			if (hasFocus)
			{
				if (mAdapter != null)
				{
					int selectedPos = mMainGv.getSelectedItemPosition();
					int count = mAdapter.getNoAppendCount();
					if (selectedPos >= count) mMainGv.setSelection(count - 1);
				}
			}
		}

	};

	GroceryCategoryProductTask.OnSucessListener categoryProductSuccessListener = new GroceryCategoryProductTask.OnSucessListener()
	{
		@Override
		public void success(ProductResult pr, ArrayList<Category> cList)
		{
			groceryLevel=1;
			mGridViewCellNum = cList.size() + pr.getTotalNum();
			mGridViewPageNum = (mGridViewCellNum / 10) + ((mGridViewCellNum - (mGridViewCellNum / 10) * 10) > 0 ? 1 : 0);
 
			mPageSize = pr.getPageSize();
			ArrayList<Product> pList = pr.getList();
			pList = checkCoupon(pList, mCouponList);
			ArrayList<GroceryCellView> list = getList(cList, pList);
			mAdapter.setAppendNum(0);
			mAdapter.setData(list);
			pageAppend();

			mAdapter.notifyDataSetChanged();
			mMainGv.requestFocus();

			updateGridViewPageView();
			updateMoreORLessIv();
			
			// 動態加入 Category item name 至 ASR 語音辨識，可以直接用 name 到該 item
			final String[] category = new String[cList.size()];
			for(int i=0; i<cList.size(); i++) {
				category[i]=cList.get(i).getName().trim().replaceAll("&", "and").toLowerCase();
				System.out.println("category[i]: "+category[i]);
			}
			
			System.out.println("flagForASR: "+flagForASR);
			if(cList.size() > 0) {
				if(flagForASR == true) {
					addASRCMDAdapter(new ASRCMDAdapter(category)
					{
						@Override
						public void action()
						{
							if(groceryLevel==1) {
								openGroceryItemByName(category,getMatchWord());
							}else {
								MilkBoxTTS.speak("There is no these item.", "echoGroceryNoTheseItem");							
							}
						}
						
						@Override
						public void action(String strValue)
						{	
						}
						
					});					
				} else if(flagForASR == false) {
					flagForASR = true;
				}
				
			}
			
		}

		@Override
		public void fail(int failCode)
		{
			mGridViewCellNum = 0;
			mGridViewPageNum = 0;
			mPageSize = 0;

			updateGridViewPageView();
			updateMoreORLessIv();
		}
	};

	MyCouponTask.OnSucessListener myCouponSuccessListener = new MyCouponTask.OnSucessListener()
	{
		@Override
		public void success(ArrayList<Coupon> list)
		{
			mCouponList = list;
			new GroceryInitialTask(mActivity, initSuccessListener, CFG.IMEI);
		}

		@Override
		public void noData()
		{
			new GroceryInitialTask(mActivity, initSuccessListener, CFG.IMEI);
		}
	};

	OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3)
		{
			Log.d(">>>", " onItemSelected position: " + position);

			moreProduct(position);
			updateGridViewPageView();

			if (mMainGv.isMoveUp())
			{
				mMainGv.setSelection(mMainGv.getSelectedItemPosition() - 5);
				mMainGv.setMoveUp(false);
			}
			else if (mMainGv.isMoveDown())
			{
				mMainGv.setSelection(mMainGv.getSelectedItemPosition() + 5);
				mMainGv.setMoveDown(false);
			}

			if (mMainGv.isMoveUpAgain())
			{
				mMainGv.setSelection(mMainGv.getSelectedItemPosition() - 5);
				mMainGv.setMoveUpAgain(false);
			}
			else if (mMainGv.isMoveDownAgain())
			{
				mMainGv.setSelection(mMainGv.getSelectedItemPosition() + 5);
				mMainGv.setMoveDownAgain(false);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{
			Log.d(">>>", " onNothingSelected");
		}

		private void test(int position)
		{
			moreProduct(position);
			updateGridViewPageView();

			int noAppendCount = mAdapter.getNoAppendCount();
			int count = mAdapter.getCount();

			int page = (position / 10) + ((position - (position / 10) * 10) > 0 ? 1 : 0);

			if (mMainGv.isMoveUp())
			{
				if (position % 10 >= 5)
				{
					if ((position - 5) >= 0)
					{
						mMainGv.setSelection(position - 5);
						mMainGv.setSelection(position);
					}
				}

				mMainGv.setMoveUp(false);
			}
			else if (mMainGv.isMoveDown())
			{
				if (position % 10 <= 4)
				{
					if ((position + 5) < count)
					{
						mMainGv.setSelection(position + 5);
						mMainGv.setSelection(position);
					}
				}

				mMainGv.setMoveDown(false);
			}
		}
	};

	OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener()
	{
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long arg3)
		{
			gotoActionBoard();
			return true;
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d(">>>", "onKeyDown: " + keyCode);

		boolean handled = handleFocusdItem(keyCode);
		if (handled) return true;

		return super.onKeyDown(keyCode, event);
	}

	public void gotoActionBoard()
	{
		cartBtn.requestFocus();
	}

	private boolean handleFocusdItem(int keyCode)
	{
		if (cartBtn.hasFocus() || searchBtn.hasFocus() || wishBtn.hasFocus() || historyBtn.hasFocus())
		{
			if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
			{
				mMainGv.requestFocus();
				// mMainGv.setSelection(mAdapter.getCount()-1);
				return true;
			}
		}

		return false;
	}

	private void pageAppend()
	{
		if ((mGridViewCellNum == mAdapter.getCount()) && ((mGridViewCellNum % 10) != 0))
		{
			int appendNum = 10 - (mGridViewCellNum % 10);

			ArrayList<GroceryCellView> list = mAdapter.getData();
			for (int i = 0; i < appendNum; i++)
			{
				GroceryCellView groceryCellView = new GroceryCellView();
				list.add(groceryCellView);
			}
			mAdapter.setAppendNum(appendNum);
		}
	}

	GroceryMoreProductTask.OnSucessListener moreSuccessListener = new GroceryMoreProductTask.OnSucessListener()
	{
		@Override
		public void success(ArrayList<Product> list)
		{
			ArrayList<GroceryCellView> gvList = mAdapter.getData();
			for (Product p : list)
			{
				gvList.add(p);
			}
			mAdapter.setData(gvList);
			pageAppend();

			mAdapter.notifyDataSetChanged();
			mMainGv.setSelection(mCurrentSelect);
		}
	};

	private void moreProduct(int position)
	{
		mCurrentSelect = position;
		int lastNum = mAdapter.getCount() % 5;
		if (lastNum == 0)
		{
			lastNum = 5;
		}
		if (position >= (mAdapter.getCount() - lastNum))
		{
			if (mCurrentPage < mPageSize)
			{
				mCurrentPage++;
				if (mNaviCategory == null)
				{
					Category c = mRootCategory.get(mNaviSelectIdx);
					new GroceryMoreProductTask(mActivity, moreSuccessListener, c.getId(), mCurrentPage);
				}
				else
				{
					Category c = mNaviCategory.get(mNaviSelectIdx);
					new GroceryMoreProductTask(mActivity, moreSuccessListener, c.getId(), mCurrentPage);
				}
			}
		}

		updateMoreORLessIv();
	}

	public void updateMoreORLessIv()
	{
		if (mGridViewPageNum == 0)
		{
			mMoreIv.setVisibility(View.INVISIBLE);
			mLessIv.setVisibility(View.INVISIBLE);
			return;
		}

		final int position = mMainGv.getSelectedItemPosition();
		int positionN = position + 1;
		int page = (positionN / 10) + ((positionN - (positionN / 10) * 10) > 0 ? 1 : 0);
		if (page < mGridViewPageNum)
		{
			mMoreIv.setVisibility(View.VISIBLE);
		}
		else
		{
			mMoreIv.setVisibility(View.INVISIBLE);
		}

		if (position >= 10)
			mLessIv.setVisibility(View.VISIBLE);
		else
			mLessIv.setVisibility(View.INVISIBLE);
	}

	private ArrayList<GroceryCellView> getList(ArrayList<Category> cList, ArrayList<Product> pList)
	{
		ArrayList<GroceryCellView> list = new ArrayList<GroceryCellView>();
		if (cList != null)
		{
			for (Category c : cList)
			{
				if (mKeyword != null)
				{
					if (c.getProductCount() > 0) list.add(c);
				}
				else
				{
					list.add(c);
				}
			}
		}
		

		if (pList != null)
		{
			/**
			 * @author Wesley
			 * @date 2014.03.03
			 * Grocery 最後一層的 voice command, 直接念品名即可
			*/
			final String[] product=new String[pList.size()];
			int pcount=0;
			
			for (Product p : pList)
			{
				list.add(p);
				// for ASR
				product[pcount++]=p.getName().trim().replaceAll("&", "and").toLowerCase();
				System.out.println("pList: "+product[pcount-1]);
			}
			
			System.out.println("flagForASR: "+flagForASR);
			// for ASR by item name
			if(pList.size() > 0) {
				groceryLevel=2;
				if(flagForASR == true) {
					addASRCMDAdapter(new ASRCMDAdapter(product)
					{
						@Override
						public void action()
						{
							if(groceryLevel==2)
								openGroceryItemByName(product,getMatchWord());
							else
								MilkBoxTTS.speak("There is no these item.", "echoGroceryNoTheseItem");
						}
						
						@Override
						public void action(String strValue)
						{	
						}
						
					});
					
				}else if(flagForASR == false) {
					flagForASR = true;
				}
			} 
		}
		return list;
	}

	private ArrayList<Category> getList(ArrayList<GroceryCellView> list)
	{
		ArrayList<Category> cList = new ArrayList<Category>();
		for (GroceryCellView cell : list)
		{
			if (cell instanceof Category)
			{
				cList.add((Category) cell);
			}
		}
		return cList;
	}

	private ArrayList<Product> checkCoupon(ArrayList<Product> pList, ArrayList<Coupon> cList)
	{
		if (cList == null)
		{
			return pList;
		}
		Product p;
		for (int i = 0; i < pList.size(); i++)
		{
			p = pList.get(i);
			for (Coupon c : cList)
			{
				if (p.getSku().equals(c.getLinkSku()) && c.getStatus() != Coupon.USE)
				{
					p.setCoupon(true);
					pList.set(i, p);
				}
			}
		}
		return pList;
	}

	private int getCategoryIndex(ArrayList<Category> list, String findId)
	{
		Category c;
		for (int i = 0; i < list.size(); i++)
		{
			c = list.get(i);
			if (c.getId().equals(findId)) return i;
		}
		return 0;
	}

	private void refreshGridView()
	{
		clearGridViewData();
		if (mNaviCategory == null)
		{
			new GroceryCategoryProductTask(mActivity, categoryProductSuccessListener, null, mKeyword);
			mCategroyTv.setText(R.string.grocery ) ; 
		}
		else
		{
			Category c = mNaviCategory.get(mNaviSelectIdx);
			mCategroyTv.setText(c.getName() ) ; 
			new GroceryCategoryProductTask(mActivity, categoryProductSuccessListener, c.getId(), mKeyword);
		}
	}

	private void clearGridViewData()
	{
		ArrayList<GroceryCellView> emptyList = new ArrayList<GroceryCellView>();
		mAdapter.setData(emptyList);
		mAdapter.notifyDataSetChanged();
	}

	private void updateGridViewPageView()
	{
		if (mGridViewPageNum == 0)
		{
			mPageTv.setText("");
			mPageTv.setVisibility(View.INVISIBLE);
		}
		else
		{
			int pos = mMainGv.getSelectedItemPosition();
			if (pos != AdapterView.INVALID_POSITION)
			{
				int num = pos + 1;
				int currentGridViewPage = (num / 10) + ((num - (num / 10) * 10) > 0 ? 1 : 0);
//				mPageTv.setText("Page " + currentGridViewPage + " of " + mGridViewPageNum);
				mPageTv.setText("(" + currentGridViewPage + "/" + mGridViewPageNum + ")");
				mPageTv.setVisibility(View.VISIBLE);
			}
			else
			{
				mPageTv.setText("");
				mPageTv.setVisibility(View.INVISIBLE);
			}
		}
	}
	
	@Override
	protected void onResume() {
		doCheckTimeOut();
		super.onResume();
	}
	
	@Override
	protected void onRestart() {
		doCheckTimeOut();
		super.onRestart();
	}
	/**
	* @author andywu
	* @date 2014.03.19
	* @category 讀取購物車有多少項目
	*
	*/
	public void updatecartnumber()
	{
		new LoadCartTask(mCartId).execute();
	}
	/**
	* @author andywu
	* @date 2014.03.19
	* @category 讀取購物車有多少項目
	*
	*/
	class LoadCartTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		String cartId;
		int mListLenght = 0 ;
		LoadCartTask(String id) {
			cartId = id;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {

				RpcClient client = CFG.getRpcClient();
				HashMap<?,?> hm = (HashMap<?,?>) client.cartInfo(cartId);
				Object[] list = (Object[]) hm.get("items");
				
//				Log.d("andy","" + list.length );
				mListLenght = list.length ;
				
				if(list.length==0) {
					return NO_DATA;
				}
				return OK;

			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
			return ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (mListLenght == 0)
				mcartTextView.setText( getResources().getString(R.string.checkoutcart) );
			else
				mcartTextView.setText(
					getResources().getString(R.string.checkoutcart) + "(" + mListLenght + ")" );
			
		}
	}
	/**
	* @author andywu
	* @date 2014.03.28
	* @category 簡易國字轉換數字
	*
	*/
	private int parseNumber(String strValue)
	{
		if (strValue == null || ("").equals(strValue))
		{
			//milky speak : ???
			return -1;			
		}
		int itemNumber ;
		
		if ("一".equals(strValue))
			itemNumber = 1 ;
		else if ("二".equals(strValue))
			itemNumber = 2 ;
		else if ("三".equals(strValue))
			itemNumber = 3 ;
		else if ("四".equals(strValue))
			itemNumber = 4 ;
		else if ("五".equals(strValue))
			itemNumber = 5 ;
		else if ("六".equals(strValue))
			itemNumber = 6 ;
		else if ("七".equals(strValue))
			itemNumber = 7 ;
		else if ("八".equals(strValue))
			itemNumber = 8 ;
		else if ("九".equals(strValue))
			itemNumber = 9 ;
		else if ("零".equals(strValue))
			itemNumber = 0 ;
		else
			itemNumber = Integer.parseInt(strValue);

		return itemNumber;
	}
}

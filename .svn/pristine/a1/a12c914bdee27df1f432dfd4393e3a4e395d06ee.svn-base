package com.kentec.milkbox.grocery.view;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.GroceryMainActivity;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.homedine.utils.BitmapUtil;
import com.kentec.milkbox.utils.Format;
import com.kentec.milkbox.view.AddQuantityDialog;
import com.kentec.milkbox.view.AddQuantityDialog.OnAddClickListener;
import com.kentec.milkbox.view.BaseDialog;

public class ProductDialog extends BaseDialog {

	private static final String TAG = "ProductDialog";

	private ProgressDialog mProgressDialog;

	private int mImageWidth;

	private LinearLayout mMainLl;

	public LinearLayout mAddWishlistLl;
	public LinearLayout mAddCartLl;

	private ImageView mMainIv;
	private TextView mNameTv;
	private TextView mPriceTv;
	private TextView mDescTv;

	private String mCartId;
	private String mCustomerId;
	private Product mProduct;

	private BaseActivity mBaseActivity ;
	
	public ProductDialog(BaseActivity activity, String cartId, String customerId, Product product) {
		super(activity, R.style.Dialog_Full);
		mProgressDialog = new ProgressDialog(activity);
		
		mBaseActivity = activity ;
		
		mImageWidth = (int) (mWidth * 0.4);
		mCartId = cartId;
		mProduct = product;
		mCustomerId = customerId;
		
		Display display = getWindow().getWindowManager().getDefaultDisplay();		
		mHeight = (int) (display.getHeight() * 0.8);
		mWidth = (int) (display.getWidth() * 0.6);
		mBaseWidth = mWidth / 10;		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.grocery_product_dialog);

		mMainLl = (LinearLayout) findViewById(R.id.mainLl);
		LayoutParams params = mMainLl.getLayoutParams();
		params.width = mWidth;
		params.height = mHeight;
		mMainLl.setLayoutParams(params);

		mMainIv = (ImageView) findViewById(R.id.mainIv);
		mNameTv = (TextView) findViewById(R.id.nameTv);
		mPriceTv = (TextView) findViewById(R.id.priceTv);
		mDescTv = (TextView) findViewById(R.id.descTv);

		mAddWishlistLl = (LinearLayout) findViewById(R.id.addWishlistLl);
		mAddCartLl = (LinearLayout) findViewById(R.id.addToCartLl);

		reLayout(mAddWishlistLl, 3);
		reLayout(mAddCartLl, 3);

		mNameTv.setText(mProduct.getName());
		mDescTv.setText(mProduct.getDescription());
		mPriceTv.setText("$ " + Format.price(mProduct.getPrice()));

		mAddWishlistLl.setOnClickListener(new LinearLayout.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new AddWishlistTask(mCustomerId, mProduct.getId()).execute();
				
				ProductDialog.this.dismiss();
				mActivity.reTimeOut();
			}
		});

		mAddCartLl.setOnClickListener(new LinearLayout.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AddQuantityDialog dialog = new AddQuantityDialog(mActivity, mActivity.getString(R.string.grocery_add_to_cart)) {
					@Override
					public void dismiss() {
						super.dismiss();
						
						ProductDialog.this.dismiss();
					}
				};
				
				dialog.setOnAddClickListener(new OnAddClickListener() {
					@Override
					public void add(int quantity) {
						new AddCartTask(mCartId, mProduct.getId(), quantity).execute();
						mActivity.reTimeOut();
					}
				});
				
				dialog.show();
			}
		});

		new DownloadTask(mMainIv, mProduct.getImage(), mImageWidth, mImageWidth).execute();

	}

	class DownloadTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		String url;
		int width;
		int height;
		Bitmap bitmap;
		ImageView imageView;

		DownloadTask(ImageView imageView, String url, int width, int height) {
			this.imageView = imageView;
			this.url = url;
			this.width = width;
			this.height = height;
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {
				InputStream in = new URL(url).openStream();
				bitmap = BitmapFactory.decodeStream(in);
				bitmap = BitmapUtil.zoomImage(bitmap, width, height);
				return OK;
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
			return ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (isCancelled()) {
				return;
			}
			if (result == OK) {
				imageView.setImageBitmap(bitmap);
			}
		}
	}

	class AddWishlistTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		String customerId;
		String productId;

		AddWishlistTask(String customerId, String productId) {
			this.customerId = customerId;
			this.productId = productId;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog.show();
		}

		@Override
		protected Integer doInBackground(String... params) {
			try {

				RpcClient client = CFG.getRpcClient();
				String wishId = client.checkoutexdGetWishId(customerId);
				client.checkoutexdAddProductToWishList(wishId, productId);
				return OK;

			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
			return ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			mProgressDialog.dismiss();
			if (result == OK)
			{
				/**
				* @author andywu
				* @date 2014.03.05
				* 加入隨機語句
				*/
//				int StrNo = (int) (Math.random() * 2); // 亂數得到 0 - 2 的值，決定講什麼話
//				switch(StrNo)
//				{
//				default:
//				case 0:
					mActivity.showOkMsg(R.string.msg_add_wishlist_success);   
//					break;
//				case 1:
//					mActivity.showOkMsg(R.string.msg_item_added);
//					break;
//				}
			}
			if (result == ERROR) {
				mActivity.showMsg(R.string.fail);
			}
		}
	}

	class AddCartTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		String cartId;
		String productId;
		int quantity;

		AddCartTask(String cartId, String productId, int quantity) {
			this.cartId = cartId;
			this.productId = productId;
			this.quantity = quantity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog.show();
		}

		@Override
		protected Integer doInBackground(String... params) {
			
			try {
				String response = RestClient.get(CFG.API_GROCERY_PATH + "productInfo.php?id=" + productId);
				RestResult rr = GroceryApiParser.parserRestResult(response);
				if (rr.getCode() != OK) {
					return ERROR;
				}
				
				Product product = GroceryApiParser.parserProduct(new JSONObject(rr.getData()));				
				if(product.getId()==null) {
					return PRODUCT_IS_NOT_EXIST;
				}
				
			}catch(Exception e) {
				return PRODUCT_IS_NOT_EXIST;
			}
			
			try {
				
				RpcClient client = CFG.getRpcClient();
				Map<String, String> products = new HashMap<String, String>();
				products.put("product_id", productId);
				products.put("qty", Integer.toString(quantity));
				Boolean success = client.cartProductAdd(cartId, products);
				if (false == success) {
					return ERROR;
				}

				return OK;

			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
			return ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			mProgressDialog.dismiss();
			if (result == OK)
			{
				/**
				* @author andywu
				* @date 2014.03.19
				* @category 加入更新購物車數量動作
				*
				*/
				if (mBaseActivity instanceof GroceryMainActivity)
					((GroceryMainActivity)mBaseActivity).updatecartnumber() ;
				/**
				* @author andywu
				* @date 2014.03.05
				* 加入隨機語句
				*/
//				int StrNo = (int) (Math.random() * 2); // 亂數得到 0 - 2 的值，決定講什麼話
//				switch(StrNo)
//				{
//				default:
//				case 0:
					mActivity.showOkMsg(R.string.msg_add_cart_success);
//					break;
//				case 1:
//					mActivity.showOkMsg(R.string.msg_item_added);
//					break;
//				}
			}
			if (result == ERROR) {
				mActivity.showMsg(R.string.fail);
			}
			if(result==PRODUCT_IS_NOT_EXIST) {
				mActivity.showMsg(R.string.msg_product_is_not_exist);
			}
		}
	}

}

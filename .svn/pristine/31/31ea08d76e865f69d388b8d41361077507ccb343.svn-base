package com.kentec.milkbox.homedine.api;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.xmlrpc.android.XMLRPCException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.homedine.data.MealsFood;
import com.kentec.milkbox.homedine.data.OrderInfoItems;
import com.kentec.milkbox.homedine.network.HttpConnection;
import com.kentec.milkbox.homedine.utils.BitmapUtil;
import com.kentec.milkbox.utils.DEBUG;
import com.kentec.milkbox.utils.DateUtil;

public class API {

	/***************************************
	 ************** 取得店家清單 *************
	 ***************************************/
	public static String getStore() {
		try {
			return new String(HttpConnection.getAPIresult(CFG.API_DINE_PATH + "dineStore.php", new MultipartEntity()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***************************************
	 ************** 取得餐點清單 *************
	 ***************************************/
	public static String getFoods(String storeCategoryId, boolean isUnder200Calores, int nowPage) {
		try {
			DEBUG.e("storeCategoryId", storeCategoryId);
			DEBUG.e("isUnder200Calores", String.valueOf(isUnder200Calores));

			MultipartEntity mEntity = new MultipartEntity();
			mEntity.addPart("storeCategoryId", new StringBody(storeCategoryId, Charset.forName("UTF-8")));
			mEntity.addPart("pageSize", new StringBody(String.valueOf(CFG.FOOD_PAGE_SIZE), Charset.forName("UTF-8")));
			mEntity.addPart("page", new StringBody(String.valueOf(nowPage), Charset.forName("UTF-8")));
			if (isUnder200Calores) {
				mEntity.addPart("underCalores", new StringBody("200", Charset.forName("UTF-8")));
			}

			return new String(HttpConnection.getAPIresult(CFG.API_DINE_PATH + "dineFood.php", mEntity));
		} catch (Exception e) {
			DEBUG.c(e);
			return null;
		}
	}

	/***************************************
	 ************** 取得縮圖 *************
	 ***************************************/
	public static Bitmap getPic(String url, int w, int h) {
		try {
			byte[] imgResult = HttpConnection.getAPIresult(url, null);
			return BitmapFactory.decodeByteArray(imgResult, 0, imgResult.length, BitmapUtil.getBestOption(imgResult, w, h));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Bitmap getPicById(String id, int w, int h) {
		try {
			MultipartEntity mEntity = new MultipartEntity();
			mEntity.addPart("productId", new StringBody(id, Charset.forName("UTF-8")));
			byte[] imgResult = HttpConnection.getAPIresult(CFG.API_DINE_PATH + "dineFoodImg.php", mEntity);
			return BitmapFactory.decodeByteArray(imgResult, 0, imgResult.length, BitmapUtil.getBestOption(imgResult, w, h));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***************************************
	 ************** 取得縮圖清單 *************
	 ***************************************/
	public static String getFoodsThumbnail(ArrayList<OrderInfoItems> itemList) {
		try {
			MultipartEntity mEntity = new MultipartEntity();
			for (int i = 0; i < itemList.size(); i++) {
				mEntity.addPart("products[]", new StringBody(itemList.get(i).getProductId(), Charset.forName("UTF-8")));
			}
			return new String(HttpConnection.getAPIresult(CFG.API_DINE_PATH + "dineFoodThumbnailUrl.php", mEntity));
		} catch (Exception e) {
			DEBUG.c(e);
			return null;
		}
	}

	/***************************************
	 ******** 取得Server購物車商品 ********
	 ***************************************/
	public static String getServerShopCart(String cartId) {
		try {
			String startDate = DateUtil.getAgoDate(0, 0, 0, CFG.HOMEDINE_SHOPPING_CART_TIME, 0, 0);
			startDate = DateUtil.toGmtTime(startDate);

			String endDate = DateUtil.getAgoDate(0, 0, 0, 0, 0, 0);
			endDate = DateUtil.toGmtTime(endDate);

			MultipartEntity mEntity = new MultipartEntity();
			mEntity.addPart("imei", new StringBody(CFG.IMEI, Charset.forName("UTF-8")));
			mEntity.addPart("cartId", new StringBody(cartId, Charset.forName("UTF-8")));
			mEntity.addPart("startDate", new StringBody(startDate, Charset.forName("UTF-8")));
			mEntity.addPart("endDate", new StringBody(endDate, Charset.forName("UTF-8")));

			return new String(HttpConnection.getAPIresult(CFG.API_DINE_PATH + "dineShopCartFood.php", mEntity));
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return null;
	}

	/***************************************
	 ********** 清空Server購物車清單 **********
	 ***************************************/
	public static boolean clearServerShopCart(String cartId) {
		try {
			RpcClient client = CFG.getRpcClient();
			DEBUG.e("getServerShopCart", "start");

			HashMap<?, ?> tmp = (HashMap<?, ?>) client.cartInfo(cartId);
			Object[] tmplist = (Object[]) tmp.get("items");

			// ShopCartData tmp = APIParser.parserShopCart((HashMap<String,
			// Object>) client.cartInfo(cartId));

			//
			// Object[] products = new Object[tmp.getFoodList().size()];
			// t
			//
			// for (int i = 0; i < products.length; i++) {
			// HashMap<String, String> food = new HashMap<String, String>();
			// food.put("product_id", list.get(i).getFoodItem().getId());
			// food.put("qty", String.valueOf(list.get(i).getFoodQuantity()));
			// products[i] = food;
			// }
			//
			if (tmplist.length == 0) {
				return true;
			} else {
				return client.cartProductsRemove(cartId, tmplist);
			}

		} catch (Exception e) {
			DEBUG.c(e);
		}
		return false;
	}

	/***************************************
	 ********** 增加Server購物車清單 \ **********
	 ***************************************/
	public static boolean addServerShopCart(String cartId, ArrayList<MealsFood> list) throws XMLRPCException {
		Object[] products = new Object[list.size()];

		for (int i = 0; i < products.length; i++) {
			HashMap<String, String> food = new HashMap<String, String>();
			food.put("product_id", list.get(i).getFoodItem().getId());
			food.put("qty", String.valueOf(list.get(i).getFoodQuantity()));
			products[i] = food;
		}
		return CFG.getRpcClient().cartProductsAdd(cartId, products);
	}

	/***************************************
	 ********** 增加客戶到購物車清單 **********
	 ***************************************/
	public static boolean addServerCustomer(String customerId, String cartId, String storeId) {
		try {
			@SuppressWarnings("unchecked")
			HashMap<String, String> customer = CFG.getRpcClient().customerInfo(customerId);
			customer.put("mode", "customer");
			return CFG.getRpcClient().cartCustomerSet(cartId, customer, storeId);
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return false;
	}

	/***************************************
	 ********** 取得Server 訂單清單 *********
	 ***************************************/
	public static Object[] getServerOrder(String customerId, String storeId) {
		try {
			RpcClient client = CFG.getRpcClient();
			DEBUG.e("getServerOrder", "start");

			String startDate = DateUtil.getAgoDate(0, 0, CFG.HOMEDINE_PROGRESS_DAY, 0, 0, 0);
			startDate = DateUtil.toGmtTime(startDate);

			String endDate = DateUtil.getAgoDate(0, 0, 0, 0, 0, 0);
			endDate = DateUtil.toGmtTime(endDate);

			return client.salesOrderList(customerId, storeId, startDate, endDate);
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return null;
	}

	/***************************************
	 ************ 取得Server 訂單 ***********
	 ***************************************/
	public static HashMap<?, ?> getServerOrderInfo(String orderId) {
		try {
			RpcClient client = CFG.getRpcClient();
			DEBUG.e("getServerOrderInfo", "start");

			return client.salesOrderInfo(orderId);
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return null;
	}
}
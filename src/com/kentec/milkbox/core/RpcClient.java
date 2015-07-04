package com.kentec.milkbox.core;

import java.net.UnknownHostException;
import java.util.HashMap;

import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.utils.DEBUG;

public class RpcClient {

	private int TIME_OUT = 5000;
	private long sessionTime;

	private String rpcUrl = CFG.HOST + "/index.php/api/xmlrpc";
	private String apiUser = "client";
	private String apiKey = "a123456";

	private String sessionId;

	private XMLRPCClient client;

	public RpcClient() {
		rpcUrl = CFG.HOST + "/index.php/api/xmlrpc";
		apiUser = "client";
		apiKey = "a123456";
		DEBUG.e("RPC Host", rpcUrl);

		client = new XMLRPCClient(rpcUrl);
	}

	public Object[] storeList() throws XMLRPCException {
		return (Object[]) callEx("store.list");
	}

	public Object cartCreate() throws XMLRPCException {
		return callEx("cart.create");
	}

	public String checkoutexdGetCartId(String customerId) throws XMLRPCException {
		return (String) callEx("checkoutexd.getCartQuoteIdByCustomerId", new Object[] { customerId });
	}

	public Object cartCreate(String storeId) throws XMLRPCException {
		return callEx("cart.create", new Object[] { storeId });
	}

	public Object[] catalogProductList() throws XMLRPCException {
		return (Object[]) callEx("catalog_product.list");
	}

	public Object catalogProductInfo(String productId) throws XMLRPCException {
		return callEx("catalog_product.info", new Object[] { productId });
	}

	public Boolean cartProductAdd(String quoteId, Object product) throws XMLRPCException {
		return (Boolean) callEx("cart_product.add", new Object[] { quoteId, new Object[] { product } });
	}

	public Boolean cartProductsAdd(String quoteId, Object[] products) throws XMLRPCException {
		return (Boolean) callEx("cart_product.add", new Object[] { quoteId, products });
	}

	public Boolean cartProductUpdate(String quoteId, Object product) throws XMLRPCException {
		return (Boolean) callEx("cart_product.update", new Object[] { quoteId, new Object[] { product } });
	}

	public Boolean cartProductRemove(String quoteId, Object product) throws XMLRPCException {
		return (Boolean) callEx("cart_product.remove", new Object[] { quoteId, new Object[] { product } });
	}

	public Boolean cartProductsRemove(String quoteId, Object[] products) throws XMLRPCException {
		return (Boolean) callEx("cart_product.remove", new Object[] { quoteId, products });
	}

	public Object[] cartProductList(String quoteId) throws XMLRPCException {
		return (Object[]) callEx("cart_product.list", new Object[] { quoteId });
	}

	public Boolean cartCouponAdd(String quoteId, String code) throws XMLRPCException {
		return (Boolean) callEx("cart_coupon.add", new Object[] { quoteId, code });
	}

	public Boolean cartCouponRemove(String quoteId) throws XMLRPCException {
		return (Boolean) callEx("cart_coupon.remove", new Object[] { quoteId });
	}

	public Object cartInfo(String quoteId) throws XMLRPCException {
		return callEx("cart.info", new Object[] { quoteId });
	}

	public Object[] cartTotals(String quoteId) throws XMLRPCException {
		return (Object[]) callEx("cart.totals", new Object[] { quoteId });
	}

	public Object[] cartShippingList(String quoteId) throws XMLRPCException {
		return (Object[]) callEx("cart_shipping.list", new Object[] { quoteId });
	}

	public Boolean cartShippingMethod(String quoteId, String methodCode) throws XMLRPCException {
		return (Boolean) callEx("cart_shipping.method", new Object[] { quoteId, methodCode });
	}

	public Object[] cartPaymentList(String quoteId) throws XMLRPCException {
		return (Object[]) callEx("cart_payment.list", new Object[] { quoteId });
	}

	public Boolean cartPaymentMethod(String quoteId, HashMap<?, ?> paymentMethod) throws XMLRPCException {
		return (Boolean) callEx("cart_payment.method", new Object[] { quoteId, paymentMethod });
	}

	public String cartOrder(String quoteId) throws XMLRPCException {
		return (String) callEx("cart.order", new Object[] { quoteId });
	}

	public Boolean cartCustomerAddress(String customerId, Object addressdata) throws XMLRPCException {
		return (Boolean) callEx("cart_customer.addresses", new Object[] { customerId, new Object[] { addressdata } });
	}

	public Object customerAddressCreate(String customerId, Object addressdata) throws XMLRPCException {
		return (Object) callEx("customer_address.create", new Object[] { customerId, addressdata });
	}

	public Boolean customerAddressDelete(String addressId) throws XMLRPCException {
		return (Boolean) callEx("customer_address.delete", new Object[] { addressId });
	}

	public Object[] customerList() throws XMLRPCException {
		return (Object[]) callEx("customer.list");
	}

	public HashMap customerInfo(String customerId) throws XMLRPCException {
		return (HashMap) callEx("customer.info", new Object[] { customerId });
	}

	public Object[] customerAddressList(String customerId) throws XMLRPCException {
		return (Object[]) callEx("customer_address.list", new Object[] { customerId });
	}

	public HashMap<?,?> boxconfigCustomerInfo(String imei) throws XMLRPCException, UnknownHostException {
		return (HashMap<?,?>) callEx("boxconfig.customerinfo", new Object[] { imei });
	}
	
	public HashMap<?,?> boxconfigInfoByImei(String imei) throws XMLRPCException, UnknownHostException {
		return (HashMap<?,?>) callEx("boxconfig.infobyimei", new Object[] { imei });
	}
	
	public HashMap<?,?> boxconfigInfoByCustomerId(String customerid) throws XMLRPCException, UnknownHostException {
		return (HashMap<?,?>) callEx("boxconfig.infobycustomerid", new Object[] { customerid });
	}

	public Boolean cartCustomerSet(String quoteId, HashMap<?,?> customer) throws XMLRPCException {
		return (Boolean) callEx("cart_customer.set", new Object[] { quoteId, customer });
	}

	public Boolean cartCustomerSet(String quoteId, HashMap<?,?> customer, String storeId) throws XMLRPCException {
		return (Boolean) callEx("cart_customer.set", new Object[] { quoteId, customer, storeId });
	}

	public Object[] checkoutexdGetCards(String customerId) throws XMLRPCException {
		return (Object[]) callEx("checkoutexd_creditcard.getCardsByCustomerId", new Object[] { customerId });
	}

	public String checkoutexdCreditCardCreate(HashMap<?,?> cc) throws XMLRPCException {
		return (String) callEx("checkoutexd_creditcard.create", new Object[] { cc });
	}

	public Boolean checkoutexdCreditCardDelete(String id) throws XMLRPCException {
		return (Boolean) callEx("checkoutexd_creditcard.delete", new Object[] { id });
	}

	public String checkoutexdGetWishId(String customerId) throws XMLRPCException {
		return (String) callEx("checkoutexd.getWishlistIdByCustomerId", new Object[] { customerId });
	}

	public Object[] checkoutexdGetWishList(String wishId) throws XMLRPCException {
		return (Object[]) callEx("checkoutexd.getWishlistItemByWishlistId", new Object[] { wishId });
	}

	public String checkoutexdAddProductToWishList(String wishId, String productId) throws XMLRPCException {
		return (String) callEx("checkoutexd.addProductToWishlist", new Object[] { wishId, productId });
	}

	public Boolean checkoutexdRemoveWishList(String wishId, String productId) throws XMLRPCException {
		return (Boolean) callEx("checkoutexd.removeProductFromWishlistById", new Object[] { wishId, productId });
	}

	public Object[] getCountryList() throws XMLRPCException {
		return (Object[]) callEx("country.list");
	}

	public Object[] salesOrderList(String email) throws XMLRPCException {
		HashMap<String, String> where = new HashMap<String, String>();
		where.put("key", "in");
		where.put("value", email);
		HashMap filter = new HashMap();
		filter.put("customer_id", where);
		return (Object[]) callEx("sales_order.list", new Object[] { filter });
	}

	@SuppressWarnings("unchecked")
	public Object[] salesOrderList(String customerId, String storeId) throws XMLRPCException {
		@SuppressWarnings("rawtypes")
		HashMap filters = new HashMap();
		HashMap<String, String> where;

		where = new HashMap<String, String>();
		where.put("eq", customerId);
		filters.put("customer_id", where);

		where = new HashMap<String, String>();
		where.put("eq", storeId);
		filters.put("store_id", where);

		return (Object[]) callEx("sales_order.list", new Object[] { filters });
	}

	@SuppressWarnings("unchecked")
	public Object[] salesOrderList(String customerId, String storeId, String startTime, String endTime) throws XMLRPCException {
		@SuppressWarnings("rawtypes")
		HashMap filters = new HashMap();
		HashMap<String, String> where;

		where = new HashMap<String, String>();
		where.put("eq", customerId);
		filters.put("customer_id", where);

		where = new HashMap<String, String>();
		where.put("eq", storeId);
		filters.put("store_id", where);

		where = new HashMap<String, String>();
		where.put("from", startTime);
		where.put("to", endTime);
		filters.put("created_at", where);

		return (Object[]) callEx("sales_order.list", new Object[] { filters });
	}

	public HashMap<?,?> salesOrderInfo(String orderId) throws XMLRPCException {
		return (HashMap<?,?>) callEx("sales_order.info", new Object[] { orderId });
	}

	private Object callEx(String resource) throws XMLRPCException {
		checkSession();
		return client.call("call", sessionId, resource);
	}

	private Object callEx(String resource, Object arguments) throws XMLRPCException {
		checkSession();
		return client.call("call", sessionId, resource, arguments);
	}

	private void checkSession() throws XMLRPCException {
		if ((System.currentTimeMillis() - sessionTime) > TIME_OUT) {
			sessionId = (String) client.call("login", apiUser, apiKey);
			sessionTime = System.currentTimeMillis();
		}
	}

}

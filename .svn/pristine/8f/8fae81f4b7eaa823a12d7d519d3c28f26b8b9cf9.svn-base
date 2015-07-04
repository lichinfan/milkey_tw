package com.kentec.milkbox.checkout.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.xmlrpc.android.XMLRPCFault;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.api.APIMake;
import com.kentec.milkbox.checkout.api.APIParser;
import com.kentec.milkbox.checkout.data.CreditCard;
import com.kentec.milkbox.checkout.data.PaymentMethod;
import com.kentec.milkbox.checkout.task.CheckoutTaskAddCreditCard.OnAddCreditCardOkListener;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.coupon.api.CouponApiParser;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.homedine.view.HomeDineMealsDeleteDioLog;
import com.kentec.milkbox.homedine.view.HomeDineMealsDeleteDioLog.OnDeleteListener;
import com.kentec.milkbox.utils.DEBUG;

public class CheckoutTaskSetPaymentMethod extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

	private CheckoutActivity mActivity;
	private PaymentMethod paymentMethod;
	private CreditCard creditCard = null;
	private String msg = "";

	public CheckoutTaskSetPaymentMethod(CheckoutActivity activity, PaymentMethod pm) {
		this.mActivity = activity;
		this.paymentMethod = pm;
	}

	public CheckoutTaskSetPaymentMethod(CheckoutActivity activity, PaymentMethod pm, CreditCard cc) {
		this.mActivity = activity;
		this.paymentMethod = pm;
		this.creditCard = cc;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mActivity.getViewGroup().getProgressDialog().show();
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		RpcClient client = CFG.getRpcClient();
		try {
			String cartID = mActivity.getData().getCartId();
			String couponCartId = mActivity.getData().getCouponCartId();

			Boolean result;

			if (creditCard != null) {
				result = client.cartPaymentMethod(cartID, APIMake.paymentMethod(paymentMethod, creditCard));
			} else {
				result = client.cartPaymentMethod(cartID, APIMake.paymentMethod(paymentMethod));
			}

			if (false == result) {
				return ERROR;
			}

			mActivity.getOrder().setPaymentMethod(paymentMethod);
			mActivity.getOrder().setCreditCard(creditCard);

			if (couponCartId == null) {
				mActivity.getData().setCouponList(getNoCouponList());
				return OK;
			}

			HashMap<?, ?> hm = (HashMap<?, ?>) client.cartInfo(couponCartId);
			String couponIdStr = CouponApiParser.getCouponIdStr(hm);

			String response = RestClient.get(CFG.API_COUPON_PATH + "productInfo.php?id=" + couponIdStr);
			RestResult rr = CouponApiParser.parserRestResult(response);
			if (rr.getCode() != OK) {
				return ERROR;
			}

			ArrayList<Coupon> couponList = CouponApiParser.getCouponList(rr.getData());
			if (couponList.size() == 0) {
				mActivity.getData().setCouponList(getNoCouponList());
				return OK;
			}

			HashMap<?, ?> map = (HashMap<?, ?>) client.cartInfo(cartID);
			Object[] productArrary = (Object[]) map.get("items");
			ArrayList<Product> productList = APIParser.parserProduct(productArrary);
			
			HashMap<String, Double> qtyHm = CouponApiParser.getCouponQty(hm);

			double qty;
			Coupon coupon;
			Product product;
			ArrayList<Coupon> avaiableCouponList = getNoCouponList();

			Date expDate;
			long today = System.currentTimeMillis();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
						
			for (int i = 0; i < couponList.size(); i++) {
				coupon = couponList.get(i);
				qty = qtyHm.get(coupon.getId());
				if (qty > 1) {					
					for(int j=0; j<productList.size(); j++) {
						product = productList.get(j);
						if( product.getSku().equals(coupon.getLinkSku()) ) {							
							expDate = sdf.parse(coupon.getExpDate());
							cal.setTime(expDate);
							cal.add(Calendar.DATE,1);
							if(today<cal.getTimeInMillis()) {
								avaiableCouponList.add(coupon);
								break;
							}
						}						
					}
				}
			}

			if (avaiableCouponList.size() == 0) {
				mActivity.getData().setCouponList(getNoCouponList());
			} else {
				mActivity.getData().setCouponList(avaiableCouponList);
			}

			return OK;

		} catch (XMLRPCFault e) {
			DEBUG.c(e);
			msg = e.getFaultString();
		} catch (Exception e) {
			DEBUG.c(e);
			msg = mActivity.getText(R.string.error).toString();
		}
		return ERROR;
	}

	@Override
	protected void onPostExecute(Integer result) {
		mActivity.getViewGroup().getProgressDialog().dismiss();
		if (result == OK) {
			showSaveCard();
		} else if (result == ERROR) {
			mActivity.showMsg(msg);
		}
	}

	private void showSaveCard() {
		if (creditCard != null) {
			boolean isSame = checkCardIsSame(mActivity.getData().getCreditCardList(), creditCard);
			DEBUG.e("isSame", String.valueOf(isSame));
			if (!isSame) {
				HomeDineMealsDeleteDioLog dd = new HomeDineMealsDeleteDioLog(mActivity);
				dd.setImg(BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.credit_card));
				dd.setTitle(mActivity.getString(R.string.checkout_cc).toString());
				dd.setMsg(mActivity.getString(R.string.checkout_cc_do_you_add).toString());
				dd.setleftButtonText(mActivity.getString(R.string.no).toString());
				dd.setrightButtonText(mActivity.getString(R.string.yes).toString());
				dd.setListener(new OnDeleteListener() {
					@Override
					public void rightButton() {
						addCreditCard();
					}

					@Override
					public void leftButton() {
						mActivity.getMethod().nextPage();
					}
				});
				dd.show();
//				mActivity.reLayout().linear(dd.getMainLinear(), 720, 480);
			} else {
				mActivity.getMethod().nextPage();
			}
		} else {
			mActivity.getMethod().nextPage();
		}
	}

	private void addCreditCard() {
		new CheckoutTaskAddCreditCard(mActivity, mActivity.getData().getCustomerId(), creditCard, new OnAddCreditCardOkListener() {

			@Override
			public void OK() {
				mActivity.showMsg(R.string.ok);
				mActivity.getMethod().nextPage();
			}

			@Override
			public void Error(String msg) {
				mActivity.showMsg(msg);
			}
		});
	}

	private boolean checkCardIsSame(ArrayList<CreditCard> cardList, CreditCard creditCard) {
		for (CreditCard cc : cardList) {
			if (!creditCard.getCid().equals(cc.getCid())) {
				continue;
			}

			if (!creditCard.getExpiryMonth().equals(cc.getExpiryMonth())) {
				continue;
			}

			if (!creditCard.getExpiryYear().equals(cc.getExpiryYear())) {
				continue;
			}

			if (!creditCard.getNumber().equals(cc.getNumber())) {
				continue;
			}

			if (!creditCard.getOwner().equals(cc.getOwner())) {
				continue;
			}

			if (!creditCard.getType().equals(cc.getType())) {
				continue;
			}
			return true;
		}
		return false;
	}

	private ArrayList<Coupon> getNoCouponList() {
		ArrayList<Coupon> list = new ArrayList<Coupon>();
		Coupon c = new Coupon();
		c.setName("無");
		list.add(c);
		return list;
	}

}

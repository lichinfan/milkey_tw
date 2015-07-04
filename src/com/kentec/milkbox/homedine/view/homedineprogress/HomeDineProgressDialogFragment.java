package com.kentec.milkbox.homedine.view.homedineprogress;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;
import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.data.OrderData;
import com.kentec.milkbox.homedine.data.OrderInfo;
import com.kentec.milkbox.homedine.task.HomeDineTaskGetOrderInfo;
import com.kentec.milkbox.homedine.task.HomeDineTaskGetOrderInfo.OnOrderOkListener;
import com.kentec.milkbox.thirdparty.SpeedScroller;
import com.kentec.milkbox.utils.DEBUG;

@SuppressLint("ValidFragment")
public class HomeDineProgressDialogFragment extends DialogFragment {

	private HomeDineDeliveryActivity mActivity;

	private ViewPager mainViewPager;

	private TextView aboutTextView;
	private TextView nameTextView;
	private TextView pageTextView;
	private TextView loadingTextView;

	private HomeDineProgressFragmentPagerAdapter mAdapter;
	private ArrayList<HomeDineProgressFragment> fragmentsList = new ArrayList<HomeDineProgressFragment>();
	private List<String> titleList = new ArrayList<String>();
	private ArrayList<OrderData> orderList;
	private String storeId;

	public HomeDineProgressDialogFragment(HomeDineDeliveryActivity activity) {
		super();
		this.mActivity = activity;
	}

	public void setStore(String storeId) {
		this.storeId = storeId;
	}

	public void upPtogressList() {
		this.orderList = mActivity.getOrderList().get(storeId);
		this.fragmentsList = new ArrayList<HomeDineProgressFragment>();
		this.titleList = new ArrayList<String>();
		for (int i = 0; i < orderList.size(); i++) {
			fragmentsList.add(new HomeDineProgressFragment(mActivity, orderList.get(i)));
			titleList.add(orderList.get(i).getIncrementId());
		}
		mAdapter.setFragmentList(fragmentsList);
		mAdapter.setTitleList(titleList);
		mAdapter.notifyDataSetChanged();
		upInfo(0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.dialog_homedine_progress, container);

		loadingTextView = (TextView) view.findViewById(R.id.loadingTextView);
		aboutTextView = (TextView) view.findViewById(R.id.aboutTextView);
		nameTextView = (TextView) view.findViewById(R.id.nameTextView);
		pageTextView = (TextView) view.findViewById(R.id.pageTextView);
		mainViewPager = (ViewPager) view.findViewById(R.id.mainViewPager);

		mAdapter = new HomeDineProgressFragmentPagerAdapter(this.getChildFragmentManager(), fragmentsList, titleList);
		mainViewPager.setAdapter(mAdapter);
		try {
			SpeedScroller scroller = new SpeedScroller(mainViewPager.getContext(), new AccelerateInterpolator());
			scroller.setmDuration(500);
			Field mField = ViewPager.class.getDeclaredField("mScroller");
			mField.setAccessible(true);
			mField.set(mainViewPager, scroller);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainViewPager.setOnPageChangeListener(PageChangeListener);

		aboutTextView.setText(Html.fromHtml(mActivity.getText(R.string.homedine_progress_about).toString()));
		this.nameTextView.setText(mActivity.getCache().getStore(storeId).getStoreName());
		return view;
	}

	public void isLoading(boolean isloading) {
		DEBUG.e("isLoading", String.valueOf(isVisible()));
		if (isVisible()) {
			if (isloading) {
				loadingTextView.setVisibility(View.VISIBLE);
			} else {
				loadingTextView.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private OnPageChangeListener PageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(final int arg0) {
			upInfo(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	private void upInfo(final int idx) {
		String page = String.valueOf(idx + 1) + "/" + String.valueOf(fragmentsList.size());
		pageTextView.setText(page);

		fragmentsList.get(idx).isLoading(true);
		new HomeDineTaskGetOrderInfo(mActivity, orderList.get(idx).getIncrementId(), new OnOrderOkListener() {
			@Override
			public void OK(OrderInfo orderInfo) {
				fragmentsList.get(idx).isLoading(false);
				fragmentsList.get(idx).showFood(orderInfo.getItemList());
				fragmentsList.get(idx).showStatus(orderInfo.getStatusHistoryList());
			}

			@Override
			public void Error(String msg) {
				fragmentsList.get(idx).isLoading(false);
				mActivity.showMsg(msg);
			}
		});
	}

	public ViewPager getMainViewPager() {
		return mainViewPager;
	}

	public TextView getAboutTextView() {
		return aboutTextView;
	}

	public ArrayList<HomeDineProgressFragment> getFragmentsList() {
		return fragmentsList;
	}

	public List<String> getTitleList() {
		return titleList;
	}

	public void setMainViewPager(ViewPager mainViewPager) {
		this.mainViewPager = mainViewPager;
	}
}
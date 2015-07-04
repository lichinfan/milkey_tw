package com.kentec.milkbox.homedine.view.homedineprogress;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomeDineProgressFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<HomeDineProgressFragment> fragmentList;
	private List<String> titleList;

	public HomeDineProgressFragmentPagerAdapter(FragmentManager fragmentManager, List<HomeDineProgressFragment> fragmentList, List<String> titleList) {
		super(fragmentManager);
		this.fragmentList = fragmentList;
		this.titleList = titleList;
	}

	@Override
	public Fragment getItem(int arg0) {
		return (fragmentList == null || fragmentList.size() == 0) ? null : fragmentList.get(arg0);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return (titleList.size() > position) ? titleList.get(position) : "";
	}

	@Override
	public int getCount() {
		return fragmentList == null ? 0 : fragmentList.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void setFragmentList(List<HomeDineProgressFragment> fragmentList) {
		this.fragmentList = fragmentList;
	}

	public void setTitleList(List<String> titleList) {
		this.titleList = titleList;
	}
}

package com.madaex.exchange.ui.market.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.madaex.exchange.ui.market.fragment.CoinInfoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  CustomLrcPagerAdapter.java
 * 时间：  2018/12/4 11:23
 * 描述：  ${TODO}
 */
public class CustomLrcPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragmentList;

    public CustomLrcPagerAdapter(FragmentManager fm, List<String> types) {
        super(fm);
        updateData(types);
    }


    public void updateData(List<String> dataList) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0, size = dataList.size(); i < size; i++) {
            Log.e("FPagerAdapter1", dataList.get(i).toString());
            fragments.add(CoinInfoFragment.newInstance(dataList.get(i)));
        }
        setFragmentList(fragments);
    }

    private void setFragmentList(ArrayList<Fragment> fragmentList) {
        if(this.mFragmentList != null){
            mFragmentList.clear();
        }
        this.mFragmentList = fragmentList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.mFragmentList.size();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
}

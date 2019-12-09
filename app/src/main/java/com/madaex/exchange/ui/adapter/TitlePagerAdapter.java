package com.madaex.exchange.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 项目：  LocalService
 * 包名：  cn.ezhibang.common.widget
 * 类名：  TitlePagerAdapter.java
 * 作者：  彭决
 * 时间：  2017/7/6 10:06
 * 描述：  ${TODO}
 */

public class TitlePagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list;//ViewPager要填充的fragment列表
    List<String> title;//tab中的title文字列表

    //使用构造方法来将数据传进去
    public TitlePagerAdapter(FragmentManager fm, List<Fragment> list, List<String> title) {
        super(fm);
        this.list = list;
        this.title = title;
    }


    @Override
    public Fragment getItem(int position) {//获得position中的fragment来填充
        return list.get(position);
    }

    @Override
    public int getCount() {//返回FragmentPager的个数
        return list.size();
    }

    //FragmentPager的标题,如果重写这个方法就显示不出tab的标题内容
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}

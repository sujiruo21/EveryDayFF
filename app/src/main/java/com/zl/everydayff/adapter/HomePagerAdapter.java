package com.zl.everydayff.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Boom on 2017/6/21.
 * 要用V4的Fragment
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    //内容fragment集合
    private ArrayList<Fragment> mFragments;

    public HomePagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

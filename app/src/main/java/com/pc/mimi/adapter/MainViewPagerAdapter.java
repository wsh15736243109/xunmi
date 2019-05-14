package com.pc.mimi.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private int size;
    private List<Fragment> mFragments;
    public MainViewPagerAdapter(FragmentManager fm, int size, List<Fragment> fragments) {
        super(fm);
        this.size = size;
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return size;
    }
}

package com.hxh19950701.qqimessager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hxh19950701 on 2016/3/27.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments = null;

    public MainViewPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
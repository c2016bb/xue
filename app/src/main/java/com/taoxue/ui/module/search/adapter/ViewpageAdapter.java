package com.taoxue.ui.module.search.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.taoxue.base.BaseFragment;
import com.taoxue.ui.module.search.fragment.ClassifyFragment;
import com.taoxue.ui.module.search.fragment.LibraryHomeFragment;
import com.taoxue.ui.module.search.fragment.ResourceManagementFragment;

public class ViewpageAdapter extends FragmentPagerAdapter {

    private final String[] mTitles;

    public ViewpageAdapter(FragmentManager fm, String[] strings) {
        super(fm);
        mTitles = strings;
    }

    @Override
    public Fragment getItem(int position) {

        BaseFragment fragment = null;
        if (position == 0) {
            fragment = new LibraryHomeFragment();
        } else if (position == 1) {
            fragment = new ResourceManagementFragment();
        } else if (position == 2) {
            fragment = new ClassifyFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
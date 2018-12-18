package com.gofar.library.base;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author lcf
 * @date 18/12/2018 下午 5:33
 * @since 1.0
 */
public class BasePageAdapter<T extends CharSequence> extends FragmentPagerAdapter {
    private List<T> mTitles;
    private List<Fragment> mFragments;

    public BasePageAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm, null, fragments);
    }

    public BasePageAdapter(FragmentManager fm, List<T> titles, List<Fragment> fragments) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        if (mFragments != null) {
            if (i < mFragments.size()) {
                return mFragments.get(i);
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            if (position < mTitles.size()) {
                return mTitles.get(position);
            }
        }
        return null;
    }
}

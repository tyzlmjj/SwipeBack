package me.majiajie.swipebacktest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentViewPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> mFragmentList;

    public FragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragList)
    {
        super(fm);
        mFragmentList=fragList;
    }

    @Override
    public Fragment getItem(int arg0)
    {
        return mFragmentList.get(arg0);
    }

    @Override
    public int getCount()
    {
        return mFragmentList.size();
    }
}

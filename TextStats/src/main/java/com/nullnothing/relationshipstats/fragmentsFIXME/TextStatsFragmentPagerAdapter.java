package com.nullnothing.relationshipstats.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class TextStatsFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Graph", "Card" }; //Change Raw Data to Graph when ready for that part
    private Context context;

    public TextStatsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new GraphFragment();
            case 1:
                return new CardFragment();
            default:
                //should never get to this code
                android.os.Process.killProcess(android.os.Process.myPid());
        }
        return null;
//        return PageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
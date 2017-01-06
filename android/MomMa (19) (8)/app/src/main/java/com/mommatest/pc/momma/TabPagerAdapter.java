package com.mommatest.pc.momma;


import android.app.Activity;
import android.support.v13.app.FragmentPagerAdapter;

/**
 * Created by Junyoung on 2016-06-23.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    // Count number of tabs
    private int tabCount;
    private Activity activity;


    public TabPagerAdapter(android.app.FragmentManager fm, int tabCount, Activity activity) {
        super(fm);
        this.tabCount = tabCount;
        this.activity = activity;
    }

    @Override
    public android.app.Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                HomeActivity home_tab = new HomeActivity(activity);
                return home_tab;
            case 1:
                ReviewActivity review_tab = new ReviewActivity(activity);
                return review_tab;
            case 2:
                IngredientActivity ingedient_tab = new IngredientActivity(activity);
                return ingedient_tab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

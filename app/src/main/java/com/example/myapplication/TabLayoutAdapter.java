package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabLayoutAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public TabLayoutAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        //Returning the current tabs
        switch (position) {
            case 0:
                PickCropFragment pickCropFragment = new PickCropFragment();
                return pickCropFragment;
            case 2:
                NewsFragment resourcesFragment = new NewsFragment();
                return resourcesFragment;

            case 1:
                NewsFragment vendorsFragment = new NewsFragment();
                return vendorsFragment;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs

    @Override
    public int getCount() {
        return tabCount;
    }
}
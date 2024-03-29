package com.pickle.ourgames.ui.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pickle.ourgames.ui.GamesListFragment;
import com.pickle.ourgames.ui.PopularFragment;
import com.pickle.ourgames.ui.PopularRootFragment;
import com.pickle.ourgames.ui.ReviewsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new PopularRootFragment();
        }
        else if (position == 1)
        {
            fragment = new ReviewsFragment();
        }
        else if (position == 2)
        {
            fragment = new GamesListFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Popular";
        }
        else if (position == 1)
        {
            title = "ReviewsFragment";
        }
        else if (position == 2)
        {
            title = "Lists";
        }
        return title;
    }
}


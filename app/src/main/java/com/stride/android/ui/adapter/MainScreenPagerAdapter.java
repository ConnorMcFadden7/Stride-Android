package com.stride.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.stride.android.ui.fragment.DashboardFragment;
import com.stride.android.ui.fragment.AchievementFragment;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class MainScreenPagerAdapter extends FragmentStatePagerAdapter {

  private static final int PAGES = 2;

  public MainScreenPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new DashboardFragment();
      case 1:
        return new AchievementFragment();
    }
    return null;
  }

  @Override public CharSequence getPageTitle(int position) {
    return "";
  }

  @Override public int getCount() {
    return PAGES;
  }
}

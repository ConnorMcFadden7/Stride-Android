package com.stride.android.ui.presenter.view;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.auto.factory.AutoFactory;
import com.stride.android.R;
import com.stride.android.ui.activity.ActivityFacade;
import com.stride.android.ui.adapter.MainScreenPagerAdapter;

/**
 * Created by connormcfadden on 27/05/16.
 */
@AutoFactory public class MainView {

  @BindView(R.id.main_pager) ViewPager mainPager;

  private final ActivityFacade mActivityFacade;
  private TabLayout mTabLayout;

  MainView(@NonNull View parent, ActivityFacade activityFacade) {
    ButterKnife.bind(this, parent);
    this.mActivityFacade = activityFacade;

    mTabLayout = (TabLayout) LayoutInflater.from(mActivityFacade.asActivity())
        .inflate(R.layout.toolbar_tabs, (ViewGroup) parent, false);
    setupActionbar(parent, mActivityFacade.asActivity());
    setAdapter(new MainScreenPagerAdapter(mActivityFacade.getFragmentManager()));
  }

  private void setupActionbar(View view, AppCompatActivity activity) {
    activity.getSupportActionBar().setDisplayShowCustomEnabled(true);
    activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
    activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    final ActionBar.LayoutParams params =
        new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    params.setMargins(view.getResources().getDimensionPixelSize(R.dimen.generic_margin_medium), 0,
        0, 0);
    params.gravity = Gravity.FILL_VERTICAL;
    activity.getSupportActionBar().setCustomView(mTabLayout, params);
  }

  private void setAdapter(PagerAdapter adapter) {
    mainPager.setAdapter(adapter);
    mainPager.setOffscreenPageLimit(3);
    if (ViewCompat.isLaidOut(mTabLayout)) {
      setupTabs();
    } else {
      mTabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
            int oldTop, int oldRight, int oldBottom) {
          setupTabs();
          mTabLayout.removeOnLayoutChangeListener(this);
        }
      });
    }
  }

  private void setupTabs() {
    mTabLayout.removeAllTabs();
    mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.selector_dashboard));
    mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.selector_trophy));
    mainPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout) {
      int mScrollState;
      int mScrollPosition;
      float mScrollOffset;

      @Override public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
        mScrollState = state;
      }

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        mScrollPosition = position;
        mScrollOffset = positionOffset;
      }

      @Override public void onPageSelected(int position) {
        super.onPageSelected(position);
        if (mScrollState != ViewPager.SCROLL_STATE_IDLE) {
          mTabLayout.setScrollPosition(mScrollPosition, mScrollOffset, true);
        }
      }
    });
    mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mainPager));
  }
}

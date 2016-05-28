package com.stride.android.ui.presenter.view;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
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

  private static final int[] TABS = {
      R.drawable.selector_dashboard, R.drawable.selector_trophy
  };

  @BindView(R.id.main_pager) ViewPager mainPager;

  private final ActivityFacade mActivityFacade;
  private TabLayout mTabLayout;

  MainView(@NonNull View parent, ActivityFacade activityFacade) {
    ButterKnife.bind(this, parent);
    this.mActivityFacade = activityFacade;

    mTabLayout = (TabLayout) LayoutInflater.from(mActivityFacade.asActivity())
        .inflate(R.layout.toolbar_tabs, (ViewGroup) parent, false);
    setupActionbar(parent, mActivityFacade);
    setAdapter(new MainScreenPagerAdapter(mActivityFacade.getFragmentManager()));
  }

  protected void setupActionbar(View view, ActivityFacade activity) {
    activity.getActionbar().setDisplayShowCustomEnabled(true);
    activity.getActionbar().setDisplayShowHomeEnabled(false);
    activity.getActionbar().setDisplayShowTitleEnabled(false);
    final ActionBar.LayoutParams params =
        new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    params.setMargins(view.getResources().getDimensionPixelSize(R.dimen.generic_margin_medium), 0,
        0, 0);
    params.gravity = Gravity.FILL_VERTICAL;
    activity.getActionbar().setCustomView(mTabLayout, params);
  }

  public void setAdapter(final PagerAdapter adapter) {
    if (ViewCompat.isLaidOut(mTabLayout)) {
      setupTabs(adapter);
    } else {
      mTabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
            int oldTop, int oldRight, int oldBottom) {
          setupTabs(adapter);
          mTabLayout.removeOnLayoutChangeListener(this);
        }
      });
    }
  }

  protected void setupTabs(PagerAdapter adapter) {
    mainPager.setAdapter(adapter);
    int count = adapter.getCount();
    for (int i = 0; i < count; i++) {
      final TabLayout.Tab tab = mTabLayout.newTab();
      mTabLayout.addTab(tab, false);
      tab.setCustomView(R.layout.layout_tab_view).setIcon(TABS[i]);
    }

    final TabLayout.TabLayoutOnPageChangeListener tabLayoutPageListener =
        new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);
    mainPager.addOnPageChangeListener(tabLayoutPageListener);
    mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mainPager));
    if (mainPager.getCurrentItem() > 0) {
      mainPager.post(new Runnable() {
        @Override public void run() {
          tabLayoutPageListener.onPageSelected(mainPager.getCurrentItem());
          mTabLayout.getTabAt(mainPager.getCurrentItem()).select();
        }
      });
    } else {
      mTabLayout.getTabAt(mainPager.getCurrentItem()).select();
    }
  }
}

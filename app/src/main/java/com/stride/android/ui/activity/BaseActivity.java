package com.stride.android.ui.activity;

import android.annotation.TargetApi;
import android.support.v4.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.squareup.otto.Bus;
import com.stride.android.StrideApplication;
import com.stride.android.ioc.ActivityComponent;
import com.stride.android.ioc.ApplicationComponent;
import com.stride.android.ioc.module.ActivityModule;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 23/05/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

  @Inject Bus mBus;
  private boolean mDestroyed = false;
  private ActivityComponent mActivityComponent;
  private boolean isResumed = false;
  private Unbinder unbinder = Unbinder.EMPTY;

  public abstract boolean isHomeAsUpEnabled();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivityComponent = getApplicationComponent().from(new ActivityModule(this));
    injectActivity(mActivityComponent);
    if (needsEventBus()) {
      StrideApplication.get().getBus().register(this);
    }

    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(isHomeAsUpEnabled());
      actionBar.setHomeButtonEnabled(isHomeAsUpEnabled());
      actionBar.setDisplayShowTitleEnabled(false);
    }
  }

  protected void injectActivity(ActivityComponent activityComponent) {
    mActivityComponent.inject(this);
  }

  @Override protected void onResume() {
    super.onResume();
    isResumed = true;
  }

  @Override public void setContentView(int layoutResID) {
    if (!attachToolbar()) {
      super.setContentView(layoutResID);
    }
    unbinder = ButterKnife.bind(this);
  }

  @Override protected void onDestroy() {
    if (needsEventBus()) {
      StrideApplication.get().getBus().unregister(this);
    }
    unbinder.unbind();
    mDestroyed = true;
    super.onDestroy();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
          fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
          finish();
        }
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public boolean isDestroyed() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      return jellyBeanIsDestroyed();
    } else {
      return mDestroyed;
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1) public boolean jellyBeanIsDestroyed() {
    return super.isDestroyed();
  }

  protected boolean attachToolbar() {
    return false;
  }

  protected boolean needsEventBus() {
    return false;
  }

  protected void postEvent(Object object) {
    mBus.post(object);
  }

  ApplicationComponent getApplicationComponent() {
    return ((StrideApplication) getApplication()).getComponent();
  }

  public boolean isActivityDestroyed() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      return jellyBeanIsDestroyed();
    } else {
      return mDestroyed;
    }
  }

  public boolean canCommitFragmentTransaction() {
    return isSafe() && !getSupportFragmentManager().isDestroyed() && isResumed;
  }

  protected boolean isSafe() {
    return !isActivityDestroyed() && !isFinishing();
  }

  public ActivityComponent getActivityComponent() {
    return mActivityComponent;
  }
}
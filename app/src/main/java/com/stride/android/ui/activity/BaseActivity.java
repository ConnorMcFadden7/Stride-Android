package com.stride.android.ui.activity;

import android.annotation.TargetApi;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.squareup.otto.Bus;
import com.stride.android.StrideApplication;
import com.stride.android.ioc.ActivityComponent;
import com.stride.android.ioc.ApplicationComponent;
import com.stride.android.ioc.module.ActivityModule;

public abstract class BaseActivity extends AppCompatActivity {

  private boolean mDestroyed = false;
  private Bus mBus = StrideApplication.get().getBus();
  private Toolbar mToolbar;
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

  private void initToolbar() {
    //mToolbar = (Toolbar) findViewById(R.id.snaps_toolbar);

    if (mToolbar != null) {
      setSupportActionBar(mToolbar);

      final ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
        actionBar.setDisplayHomeAsUpEnabled(isHomeAsUpEnabled());
        actionBar.setHomeButtonEnabled(isHomeAsUpEnabled());
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);
      }
    }
  }

  protected void injectActivity(ActivityComponent activityComponent) {
    mActivityComponent.inject(this);
  }

  public void setToolbarCustomView(int resourceId) {
    resetToolbarContentInset();
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayShowCustomEnabled(true);
      actionBar.setCustomView(resourceId);
    }
  }

  public void setToolbarCustomView(ActionBar actionBar) {
    resetToolbarContentInset();
    actionBar.setDisplayShowCustomEnabled(true);
  }

  public void resetToolbarContentInset() {
    getToolbar().setContentInsetsRelative(0, 0);
  }

  @Override protected void onResume() {
    super.onResume();
    isResumed = true;
  }

  @Override public void setContentView(int layoutResID) {
    if (!attachToolbar()) {
      super.setContentView(layoutResID);
    } else {
      //  new ToolbarAttacher().attach(this, layoutResID);
    }
    unbinder = ButterKnife.bind(this);
    initToolbar();
  }

  public final void parentSetContentView(int layoutResID) {
    super.setContentView(layoutResID);
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

  public Toolbar getToolbar() {
    return mToolbar;
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

  protected void showSuccessSnackBar(View parent, int textResourceId) {
    if (!isDestroyed()) {
      Snackbar snack = Snackbar.make(parent, textResourceId, Snackbar.LENGTH_SHORT)
          .setActionTextColor(getResources().getColor(android.R.color.white));
      // snack.getView().setBackgroundColor(getResources().getColor(R.color.quip_black));
      snack.show();
    }
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

  protected void showErrorSnackbar(View parent, @StringRes int textResourceId) {
    if (!isDestroyed()) {
      Snackbar snack = Snackbar.make(parent, textResourceId, Snackbar.LENGTH_SHORT)
          .setActionTextColor(getResources().getColor(android.R.color.white));
      //snack.getView().setBackgroundColor(getResources().getColor(R.color.validation_error_red));
      snack.show();
    }
  }

  protected void showErrorSnackbar(View parent, String errorMessage) {
    if (!isDestroyed()) {
      Snackbar snack = Snackbar.make(parent, errorMessage, Snackbar.LENGTH_SHORT)
          .setActionTextColor(getResources().getColor(android.R.color.white));
      //  snack.getView().setBackgroundColor(getResources().getColor(R.color.validation_error_red));
      snack.show();
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
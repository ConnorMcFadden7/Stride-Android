package com.stride.android.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.View;
import butterknife.Unbinder;
import com.stride.android.StrideApplication;
import com.stride.android.ioc.FragmentComponent;
import com.stride.android.ioc.module.FragmentModule;
import com.stride.android.ui.activity.BaseActivity;

/**
 * Created by connormcfadden on 04/07/15.
 */
public class BaseFragment extends Fragment {

  private boolean mIsViewReset = false;
  private boolean mBackPressedListenerModified = false;
  private Unbinder unbinder = Unbinder.EMPTY;
  private FragmentComponent mFragmentComponent;

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    if (needsEventBus()) {
      StrideApplication.get().getBus().register(this);
    }
    mFragmentComponent = getBaseActivity().getActivityComponent().from(new FragmentModule(this));
    injectFragment(mFragmentComponent);
    super.onViewCreated(view, savedInstanceState);
  }

  @Override public void onDestroyView() {
    mIsViewReset = true;
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override public void onStart() {
    super.onStart();
    if (needsEventBus()) {
      StrideApplication.get().getBus().register(this);
    }
  }

  @Override public void onDetach() {
    if (mBackPressedListenerModified && hasActivity()) {
      // getBaseActivity().setBackPressedListener(BaseActivity.BackPressedListener.NULL);
    }
    super.onDetach();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (needsEventBus()) {
      StrideApplication.get().getBus().unregister(this);
    }
  }

  protected void injectFragment(FragmentComponent fragmentComponent) {
    mFragmentComponent.inject(this);
  }

  //protected void setBackPressedListener(BaseActivity.BackPressedListener backPressedListener) {
  //  if (hasActivity()) {
  //    mBackPressedListenerModified = true;
  //    getBaseActivity().setBackPressedListener(backPressedListener);
  //  }
  //}

  protected boolean needsEventBus() {
    return false;
  }

  protected void setTitle(CharSequence title) {
    getBaseActivity().setTitle(title);
  }

  protected void setTitle(int titleID) {
    getBaseActivity().setTitle(titleID);
  }

  protected ActionBar getActionBar() {
    return ((ActionBarActivity) getActivity()).getSupportActionBar();
  }

  protected float getActionBarHeight() {
    TypedValue tv = new TypedValue();
    if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      return TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
    }
    return 0;
  }

  protected BaseActivity getBaseActivity() {
    return (BaseActivity) getActivity();
  }

  protected void setActionBarAlpha(float percentage) {
    final Drawable background = getBaseActivity().getToolbar().getBackground();
    background.setAlpha(Math.round(percentage * 255));
    getBaseActivity().getToolbar().setBackgroundDrawable(background);
  }

  protected boolean isViewReset() {
    return mIsViewReset;
  }

  protected void showErrorSnackbar(@StringRes int textResourceId) {
    if (isVisible()) {
      Snackbar snack = Snackbar.make(getView(), textResourceId, Snackbar.LENGTH_SHORT)
          .setActionTextColor(getResources().getColor(android.R.color.white));
      // snack.getView().setBackgroundColor(getResources().getColor(R.color.error_text_red));
      snack.show();
    }
  }

  protected void showSuccessSnackBar(@StringRes int textResourceId, @ColorRes int colorResourceId) {
    if (isVisible()) {
      Snackbar snack = Snackbar.make(getView(), textResourceId, Snackbar.LENGTH_SHORT);
      snack.getView().setBackgroundColor(getResources().getColor(colorResourceId));
      snack.show();
    }
  }

  protected void postEvent(Object event) {
    StrideApplication.get().getBus().post(event);
  }

  protected boolean hasActivity() {
    return getActivity() != null
        && !getActivity().isFinishing()
        && !getBaseActivity().isDestroyed();
  }

  protected boolean canCommitFragmentTransaction() {
    return isSafe()/* && getBaseActivity().canCommitFragmentTransaction();*/;
  }

  protected boolean hasConnection() {
    return hasActivity() /*NetworkUtil.handleNoNetwork(getActivity())*/;
  }

  public boolean isSafe() {
    return getView() != null && hasActivity() && !isDetached() && isAdded();
  }



  /*protected void runOnUiThreadSafely(Runnable runnable) {
    if (isSafe()) {
      getActivity().runOnUiThread(new SafeRunnable(this, runnable));
    }
  }

  protected void runOnUiThreadSafely(Runnable runnable, int delay) {
    if (isSafe()) {
      getView().postDelayed(new SafeRunnable(this, runnable), delay);
    }
  }*/
}

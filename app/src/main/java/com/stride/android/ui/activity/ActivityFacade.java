package com.stride.android.ui.activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

/**
 * Created by connormcfadden on 06/05/16.
 */
public abstract class ActivityFacade {

  public static ActivityFacade wrap(BaseActivity baseActivity) {
    return new ActivityFacadeImpl(baseActivity);
  }

  public static ActivityFacade wrap(Context context) {
    if (context instanceof BaseActivity) {
      return wrap((BaseActivity) context);
    }

    return new ContextFacadeImpl(context);
  }

  public abstract ActionBar getActionbar();

  public abstract FragmentManager getFragmentManager();

  public abstract void setToolbar(Toolbar toolbar);

  public abstract AppCompatActivity asActivity();

  public abstract Resources getResources();

  public abstract boolean canCommitFragmentTransaction();

  public abstract LayoutInflater getLayoutInflater();

  static class ActivityFacadeImpl extends ActivityFacade {

    private final BaseActivity baseActivity;

    ActivityFacadeImpl(BaseActivity baseActivity) {
      this.baseActivity = baseActivity;
    }

    @Override public ActionBar getActionbar() {
      return baseActivity.getSupportActionBar();
    }

    @Override public FragmentManager getFragmentManager() {
      return baseActivity.getSupportFragmentManager();
    }

    @Override public void setToolbar(Toolbar toolbar) {
      baseActivity.setSupportActionBar(toolbar);
    }

    @Override public AppCompatActivity asActivity() {
      return baseActivity;
    }

    @Override public Resources getResources() {
      return baseActivity.getResources();
    }

    @Override public boolean canCommitFragmentTransaction() {
      return baseActivity.canCommitFragmentTransaction();
    }

    @Override public LayoutInflater getLayoutInflater() {
      return asActivity().getLayoutInflater();
    }
  }

  static class ContextFacadeImpl extends ActivityFacade {

    final Context context;

    ContextFacadeImpl(Context context) {
      this.context = context;
    }

    @Override public ActionBar getActionbar() {
      return null;
    }

    @Override public FragmentManager getFragmentManager() {
      return null;
    }

    @Override public void setToolbar(Toolbar toolbar) {
      // Empty
    }

    @Override public AppCompatActivity asActivity() {
      return null;
    }

    @Override public Resources getResources() {
      return context.getResources();
    }

    @Override public boolean canCommitFragmentTransaction() {
      return false;
    }

    @Override public LayoutInflater getLayoutInflater() {
      return LayoutInflater.from(context);
    }
  }
}


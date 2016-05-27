package com.stride.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.stride.android.R;

/**
 * Created by connormcfadden on 06/05/16.
 */
public abstract class ActivityFacade {

  public static ActivityFacade wrap(BaseActivity baseActivity) {
    return new ActivityFacadeImpl(baseActivity);
  }

  public static ActivityFacade wrap(View view) {
    return wrap(view.getContext());
  }

  public static ActivityFacade wrap(Context context) {
    if (context instanceof BaseActivity) {
      return wrap((BaseActivity) context);
    }

    return new ContextFacadeImpl(context);
  }

  public abstract ActionBar getActionbar();

  public abstract FragmentManager getFragmentManager();

  public abstract void startActivity(Intent intent);

  public abstract void showSnackbar(CharSequence text);

  public abstract void setToolbar(Toolbar toolbar);

  public abstract AppCompatActivity asActivity();

  public abstract Resources getResources();

  public abstract ViewGroup getParentView();

  public abstract boolean canCommitFragmentTransaction();

  public abstract Intent makeIntent(Class<?> clazz);

  public abstract void finish();

  public abstract void startActivityForResult(Intent startingIntent, int requestCode);

  public abstract void showSuccessSnackBar(@StringRes int textResourceId, @ColorRes int color);

  public abstract void showErrorSnackbar(@StringRes int textResourceId);

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

    @Override public void startActivity(Intent intent) {
      baseActivity.startActivity(intent);
    }

    @Override public void showSnackbar(CharSequence text) {
      Snackbar snackbar = Snackbar.make(baseActivity.findViewById(android.R.id.content), text,
          Snackbar.LENGTH_LONG);
      View view = snackbar.getView();
      view.setBackgroundColor(baseActivity.getResources().getColor(R.color.colorPrimary));
      FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
      params.gravity = Gravity.TOP;
      view.setLayoutParams(params);
      snackbar.show();
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

    @Override public ViewGroup getParentView() {
      return (ViewGroup) asActivity().findViewById(android.R.id.content);
    }

    @Override public boolean canCommitFragmentTransaction() {
      return baseActivity.canCommitFragmentTransaction();
    }

    private boolean isDestroyed() {
      return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
          && baseActivity.isActivityDestroyed();
    }

    @Override public Intent makeIntent(Class<?> clazz) {
      return new Intent(baseActivity, clazz);
    }

    @Override public void finish() {
      baseActivity.finish();
    }

    @Override public void startActivityForResult(Intent startingIntent, int requestCode) {
      baseActivity.startActivityForResult(startingIntent, requestCode);
    }

    @Override
    public void showSuccessSnackBar(@StringRes int textResourceId, @ColorRes int colorResourceId) {
      Snackbar snack =
          Snackbar.make(baseActivity.findViewById(android.R.id.content), textResourceId,
              Snackbar.LENGTH_SHORT);
      snack.getView().setBackgroundColor(getResources().getColor(colorResourceId));
      snack.show();
    }

    @Override public void showErrorSnackbar(@StringRes int textResourceId) {
      Snackbar snack =
          Snackbar.make(baseActivity.findViewById(android.R.id.content), textResourceId,
              Snackbar.LENGTH_SHORT)
              .setActionTextColor(getResources().getColor(android.R.color.white));
      snack.getView().setBackgroundColor(getResources().getColor(R.color.error_text_red));
      snack.show();
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

    @Override public void startActivity(Intent intent) {
      context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override public void showSnackbar(CharSequence text) {
      // Empty
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

    @Override public ViewGroup getParentView() {
      return null;
    }

    @Override public boolean canCommitFragmentTransaction() {
      return false;
    }

    @Override public Intent makeIntent(Class<?> clazz) {
      return new Intent(context, clazz);
    }

    @Override public void finish() {
      // Empty
    }

    @Override public void startActivityForResult(Intent startingIntent, int requestCode) {
      context.startActivity(startingIntent);
    }

    @Override public void showSuccessSnackBar(@StringRes int textResourceId, @ColorRes int color) {
      // Empty
    }

    @Override public void showErrorSnackbar(@StringRes int textResourceId) {
      // Empty
    }

    @Override public LayoutInflater getLayoutInflater() {
      return LayoutInflater.from(context);
    }
  }
}


package com.stride.android.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import butterknife.Unbinder;
import com.squareup.otto.Bus;
import com.stride.android.StrideApplication;
import com.stride.android.ioc.FragmentComponent;
import com.stride.android.ioc.module.FragmentModule;
import com.stride.android.ui.activity.BaseActivity;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 23/05/16.
 */
public class BaseFragment extends Fragment {

  @Inject Bus bus;
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
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override public void onStart() {
    super.onStart();
    if (needsEventBus()) {
      bus.register(this);
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (needsEventBus()) {
      bus.unregister(this);
    }
  }

  protected void injectFragment(FragmentComponent fragmentComponent) {
    mFragmentComponent.inject(this);
  }

  protected boolean needsEventBus() {
    return false;
  }

  protected void setTitle(CharSequence title) {
    getBaseActivity().setTitle(title);
  }

  protected void setTitle(int titleID) {
    getBaseActivity().setTitle(titleID);
  }

  protected BaseActivity getBaseActivity() {
    return (BaseActivity) getActivity();
  }

  protected void postEvent(Object event) {
    bus.post(event);
  }

  protected boolean hasActivity() {
    return getActivity() != null
        && !getActivity().isFinishing()
        && !getBaseActivity().isDestroyed();
  }
}

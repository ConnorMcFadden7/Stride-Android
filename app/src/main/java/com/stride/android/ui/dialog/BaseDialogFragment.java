package com.stride.android.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import butterknife.ButterKnife;
import com.stride.android.R;
import com.stride.android.ioc.FragmentComponent;
import com.stride.android.ioc.module.FragmentModule;
import com.stride.android.ui.activity.BaseActivity;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class BaseDialogFragment extends DialogFragment {

  private FragmentComponent mFragmentComponent;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mFragmentComponent = getBaseActivity().getActivityComponent().from(new FragmentModule(this));
    injectFragment(mFragmentComponent);
  }

  protected void injectFragment(FragmentComponent fragmentComponent) {
    fragmentComponent.inject(this);
  }

  private BaseActivity getBaseActivity() {
    return (BaseActivity) getActivity();
  }
}

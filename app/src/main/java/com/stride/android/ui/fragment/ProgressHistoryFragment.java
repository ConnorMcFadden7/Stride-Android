package com.stride.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.stride.android.R;
import com.stride.android.ioc.FragmentComponent;
import com.stride.android.ui.presenter.ProgressHistoryPresenter;
import com.stride.android.ui.presenter.view.ProgressHistoryViewModelFactory;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 29/05/16.
 */
public class ProgressHistoryFragment extends BaseFragment {

  @BindView(R.id.previous_steps_container) View mParent;

  @Inject ProgressHistoryPresenter progressHistoryPresenter;
  @Inject ProgressHistoryViewModelFactory progressHistoryViewModelFactory;

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);
    presentPreviousSteps();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_previous_steps, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  @Override protected void injectFragment(FragmentComponent fragmentComponent) {
    fragmentComponent.inject(this);
  }

  private void presentPreviousSteps() {
    progressHistoryPresenter.present(progressHistoryViewModelFactory.create(mParent));
  }
}

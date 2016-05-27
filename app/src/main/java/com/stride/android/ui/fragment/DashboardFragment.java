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
import com.stride.android.ui.presenter.DashboardPresenter;
import com.stride.android.ui.presenter.view.DashboardViewFactory;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class DashboardFragment extends BaseFragment {

  @BindView(R.id.dashboard_container) View mParent;

  @Inject DashboardPresenter dashboardPresenter;
  @Inject DashboardViewFactory dashboardViewFactory;

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);
    presentDashboard();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_dashboard, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  @Override protected void injectFragment(FragmentComponent fragmentComponent) {
    fragmentComponent.inject(this);
  }

  private void presentDashboard() {
    dashboardPresenter.present(dashboardViewFactory.create(mParent, getContext()));
  }
}

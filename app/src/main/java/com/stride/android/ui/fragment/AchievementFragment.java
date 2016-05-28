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
import com.stride.android.ui.presenter.AchievementPresenter;
import com.stride.android.ui.presenter.view.AchievementViewFactory;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class AchievementFragment extends BaseFragment {

  @BindView(R.id.trophy_container) View mParent;

  @Inject AchievementPresenter achievementPresenter;
  @Inject AchievementViewFactory achievementViewFactory;

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_achievement, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  @Override public void onResume() {
    super.onResume();
    if (hasActivity()) {
      presentTrophies();
    }
  }

  @Override protected void injectFragment(FragmentComponent fragmentComponent) {
    fragmentComponent.inject(this);
  }

  private void presentTrophies() {
    achievementPresenter.present(achievementViewFactory.create(mParent));
  }
}

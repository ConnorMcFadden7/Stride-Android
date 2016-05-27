package com.stride.android.ui.presenter;

import com.stride.android.data.local.PreferencesHelper;
import com.stride.android.repository.DashboardRespository;
import com.stride.android.ui.presenter.model.DashboardModel;
import com.stride.android.ui.presenter.view.DashboardView;
import com.stride.android.util.CaloriesHelper;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class DashboardPresenter {

  // should go in some goal helper?
  private static final int USER_DEFAULT_GOAL = 10000;

  private final PreferencesHelper mPreferencesHelper;
  private final CaloriesHelper mCaloriesHelper;
  private final DashboardRespository mDashboardRepository;

  @Inject DashboardPresenter(PreferencesHelper preferencesHelper, CaloriesHelper caloriesHelper,
      DashboardRespository dashboardRepository) {
    this.mPreferencesHelper = preferencesHelper;
    this.mCaloriesHelper = caloriesHelper;
    this.mDashboardRepository = dashboardRepository;
  }

  public void present(DashboardView view) {
    DashboardModel model = mDashboardRepository.getDashboardModel();
    int userGoal = mPreferencesHelper.getUserGoal();
    view.setGoalText(userGoal > -1 ? userGoal : USER_DEFAULT_GOAL);
    view.setProgress(model.getTodaysSteps());
    view.setCaloriesText(mCaloriesHelper.getCalories(10));
    view.setStepsYesterdayText(3585);
  }
}

package com.stride.android.ui.presenter;

import com.stride.android.data.local.PreferencesHelper;
import com.stride.android.repository.DashboardRespository;
import com.stride.android.ui.presenter.model.DashboardModel;
import com.stride.android.ui.presenter.view.DashboardView;
import com.stride.android.ui.widget.DailyGoalPanel;
import com.stride.android.util.AchievementGenerator;
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

  private DashboardView dashboardView;

  @Inject DashboardPresenter(PreferencesHelper preferencesHelper, CaloriesHelper caloriesHelper,
      DashboardRespository dashboardRepository) {
    this.mPreferencesHelper = preferencesHelper;
    this.mCaloriesHelper = caloriesHelper;
    this.mDashboardRepository = dashboardRepository;
  }

  public void present(final DashboardView view) {
    this.dashboardView = view;
    DashboardModel model = mDashboardRepository.getDashboardModel();
    int userGoal = mPreferencesHelper.getUserGoal();
    view.setGoalText(userGoal > -1 ? userGoal : USER_DEFAULT_GOAL);
    initToggleListener();
    view.setProgress(model.getTodaysSteps());
    view.setCaloriesText(mCaloriesHelper.getCalories(10));
    view.setStepsYesterdayText(3585);
  }

  private void initToggleListener() {
    dashboardView.setGoalToggleListener(new DailyGoalPanel.ToggleListener() {
      @Override public void onSetGoal(int goal) {
        if (!AchievementGenerator.Achievements.SET_GOAL.isReached()) {
          AchievementGenerator.Achievements.SET_GOAL.setReached();
        }
        saveAndSetGoal(goal);
      }

      @Override public void onCurrentGoalSelected() {
        dashboardView.hidePanel();
      }
    });
  }

  private void saveAndSetGoal(int goal) {
    mPreferencesHelper.setUserGoal(goal);
    dashboardView.setGoalText(
        mPreferencesHelper.getUserGoal() > -1 ? mPreferencesHelper.getUserGoal()
            : USER_DEFAULT_GOAL);
    dashboardView.hidePanel();
  }
}

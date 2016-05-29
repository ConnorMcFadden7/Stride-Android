package com.stride.android.ui.presenter;

import com.stride.android.data.local.PreferencesHelper;
import com.stride.android.repository.DashboardRespository;
import com.stride.android.ui.BackButtonListener;
import com.stride.android.ui.activity.ActivityFacade;
import com.stride.android.ui.activity.MainActivity;
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
  private final ActivityFacade mActivityFacade;

  private DashboardView dashboardView;

  @Inject DashboardPresenter(PreferencesHelper preferencesHelper, CaloriesHelper caloriesHelper,
      DashboardRespository dashboardRepository, ActivityFacade activityFacade) {
    this.mPreferencesHelper = preferencesHelper;
    this.mCaloriesHelper = caloriesHelper;
    this.mDashboardRepository = dashboardRepository;
    this.mActivityFacade = activityFacade;
  }

  public void present(final DashboardView view) {
    this.dashboardView = view;
    DashboardModel model = mDashboardRepository.getDashboardModel();
    int userGoal = mPreferencesHelper.getUserGoal();
    view.setGoalText(userGoal > -1 ? userGoal : USER_DEFAULT_GOAL);
    initToggleListener();
    view.setProgress(model.getTodaysSteps());
    view.setCaloriesText(mCaloriesHelper.getCalories(10));
    view.setStepsYesterdayText(model.getYesterdaysSteps());
    view.setAverageSteps(model.getTotalAverage());

    if (model.getTodaysSteps() >= userGoal && userGoal > -1) {
      view.setGoalComplete();
    } else {
      view.setStepsToday(model.getTodaysSteps());
    }
    handleBackPressed();
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

      @Override public void onCustomGoal() {
        if (!AchievementGenerator.Achievements.CUSTOM_GOAL.isReached()) {
          AchievementGenerator.Achievements.CUSTOM_GOAL.setReached();
        }
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

  private void handleBackPressed() {
    dashboardView.setListener(new DashboardView.Listener() {
      @Override public void onPanelShown() {
        ((MainActivity) mActivityFacade.asActivity()).setBackPressedListener(
            new BackButtonListener() {
              @Override public void onBackPressed() {
                dashboardView.hidePanel();
              }
            });
      }

      @Override public void onPanelHidden() {
        ((MainActivity) mActivityFacade.asActivity()).setBackPressedListener(
            BackButtonListener.NULL);
      }
    });
  }
}

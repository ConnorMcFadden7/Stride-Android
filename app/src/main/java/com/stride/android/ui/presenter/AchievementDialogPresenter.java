package com.stride.android.ui.presenter;

import com.stride.android.R;
import com.stride.android.data.model.Achievement;
import com.stride.android.ui.activity.ActivityFacade;
import com.stride.android.ui.presenter.view.AchievementDialogView;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class AchievementDialogPresenter {

  private final ActivityFacade activityFacade;
  private AchievementDialogView achievementDialogView;

  @Inject AchievementDialogPresenter(ActivityFacade activityFacade) {
    this.activityFacade = activityFacade;
  }

  public void present(AchievementDialogView view, Achievement achievement) {
    this.achievementDialogView = view;
    view.setAchievementIcon(
        achievement.progress == 0 ? achievement.empty_icon_big : achievement.icon_big);
    view.setTitle(achievement.title);
    view.setDescription(achievement.description);
    view.setDateAchievedText(achievement.is_achieved ? achievement.achieved_date
        : activityFacade.getResources().getString(R.string.achievements_not_achieved));
  }
}

package com.stride.android.ui.presenter;

import com.stride.android.data.model.Achievement;
import com.stride.android.ui.presenter.view.AchievementDialogView;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class AchievementDialogPresenter {

  private AchievementDialogView achievementDialogView;

  @Inject AchievementDialogPresenter() {
    //
  }

  public void present(AchievementDialogView view, Achievement achievement) {
    this.achievementDialogView = view;
    view.setAchievementIcon(achievement.progress == 0 ? achievement.empty_icon : achievement.icon);
    view.setTitle(achievement.title);
    view.setDescription(achievement.description);
  }
}

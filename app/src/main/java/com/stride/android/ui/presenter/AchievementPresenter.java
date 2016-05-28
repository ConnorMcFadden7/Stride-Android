package com.stride.android.ui.presenter;

import com.stride.android.R;
import com.stride.android.data.model.Achievement;
import com.stride.android.ui.activity.ActivityFacade;
import com.stride.android.ui.presenter.view.AchievementView;
import com.stride.android.util.AchievementGenerator;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class AchievementPresenter {

  private final ActivityFacade mActivityFacade;
  private final AchievementGenerator mAchievementGenerator;
  private AchievementView mAchievementView;

  @Inject AchievementPresenter(ActivityFacade activityFacade,
      AchievementGenerator achievementGenerator) {
    this.mActivityFacade = activityFacade;
    this.mAchievementGenerator = achievementGenerator;
  }

  public void present(AchievementView view) {
    this.mAchievementView = view;
    view.setAchievementAdapter(mAchievementGenerator.getAchievements());
    setAchievementCount();
  }

  private void setAchievementCount() {
    int achievementsCompleted = 0;
    for (Achievement achievement : mAchievementGenerator.getAchievements()) {
      if (achievement.is_achieved) {
        ++achievementsCompleted;
      }
    }
    mAchievementView.setAchievementCount(mActivityFacade.getResources()
        .getString(R.string.achievements_count, achievementsCompleted,
            mAchievementGenerator.getAchievements().size()));
  }
}

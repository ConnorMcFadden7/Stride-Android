package com.stride.android.ui.presenter.view;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import com.stride.android.R;
import com.stride.android.ui.activity.ActivityFacade;

/**
 * Created by connormcfadden on 28/05/16.
 */
@AutoFactory public class AchievementDialogView {

  @BindView(R.id.achievement_icon) ImageView achievementIcon;
  @BindView(R.id.tv_achievement_title) TextView achievementTitle;
  @BindView(R.id.tv_achievement_description) TextView achievementDescription;
  @BindView(R.id.tv_unlocked_date) TextView dateAchievedText;

  private final ActivityFacade activityFacade;

  AchievementDialogView(@NonNull View parent, @Provided ActivityFacade activityFacade) {
    ButterKnife.bind(this, parent);
    this.activityFacade = activityFacade;
  }

  public void setAchievementIcon(@DrawableRes int icon) {
    achievementIcon.setImageResource(icon);
  }

  public void setTitle(@StringRes int title) {
    achievementTitle.setText(activityFacade.getResources().getString(title));
  }

  public void setDescription(@StringRes int description) {
    achievementDescription.setText(activityFacade.getResources().getString(description));
  }

  public void setDateAchievedText(String dateAchievedText) {
    this.dateAchievedText.setText(dateAchievedText);
  }
}

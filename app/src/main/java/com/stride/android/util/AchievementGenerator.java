package com.stride.android.util;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import com.stride.android.R;
import com.stride.android.data.model.Achievement;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class AchievementGenerator {

  public enum Achievements {

    WALK_ONE(R.string.achievements_walk1_title, R.string.achievements_walk1_description,
        R.drawable.ic_achievement_walk1_complete, R.drawable.ic_achievement_walk1_incomplete),

    BURN_ONE(R.string.achievements_burn1_title, R.string.achievements_burn1_description,
        R.drawable.ic_achievement_burn1_complete, R.drawable.ic_achievement_burn1_incomplete),

    REMOVE_ADS(R.string.achievements_ads_title, R.string.achievements_ads_description,
        R.drawable.ic_achievement_ads_complete, R.drawable.ic_achievement_ads_incomplete);

    private final @StringRes int title;
    private final @StringRes int description;
    private final @DrawableRes int achievedIcon;
    private final @DrawableRes int unachievedIcon;

    Achievements(@StringRes int title, @StringRes int description, @DrawableRes int achievedIcon,
        @DrawableRes int unachievedIcon) {
      this.title = title;
      this.description = description;
      this.achievedIcon = achievedIcon;
      this.unachievedIcon = unachievedIcon;
    }

    public @StringRes int getTitle() {
      return title;
    }

    public @StringRes int getDescription() {
      return description;
    }

    public @DrawableRes int getAchievedIcon() {
      return achievedIcon;
    }

    public @DrawableRes int getUnachievedIcon() {
      return unachievedIcon;
    }
  }

  @Inject AchievementGenerator() {
    //
  }

  public List<Achievement> getAchievements() {
    List<Achievement> achievements = new ArrayList<>();
    for (int i = 0; i < Achievements.values().length; i++) {
      Achievement achievement = new Achievement();

      achievement.title = Achievements.values()[i].getTitle();
      achievement.description = Achievements.values()[i].getDescription();
      achievement.icon = Achievements.values()[i].getAchievedIcon();
      achievement.empty_icon = Achievements.values()[i].getUnachievedIcon();
      achievements.add(achievement);
    }
    return achievements;
  }
}

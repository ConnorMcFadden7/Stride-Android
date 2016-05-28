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
        R.drawable.ic_achievement_walk1_complete, R.drawable.ic_achievement_walk1_incomplete,
        R.drawable.ic_achievement_walk1_complete_big,
        R.drawable.ic_achievement_walk1_incomplete_big),

    WALK_TWO(R.string.achievements_walk2_title, R.string.achievements_walk2_description,
        R.drawable.ic_achievement_walk2_complete, R.drawable.ic_achievement_walk2_incomplete,
        R.drawable.ic_achievement_walk2_complete_big,
        R.drawable.ic_achievement_walk2_incomplete_big),

    BURN_ONE(R.string.achievements_burn1_title, R.string.achievements_burn1_description,
        R.drawable.ic_achievement_burn1_complete, R.drawable.ic_achievement_burn1_incomplete,
        R.drawable.ic_achievement_burn1_complete_big,
        R.drawable.ic_achievement_burn1_incomplete_big),

    BURN_TWO(R.string.achievements_burn2_title, R.string.achievements_burn2_description,
        R.drawable.ic_achievement_burn2_complete, R.drawable.ic_achievement_burn2_incomplete,
        R.drawable.ic_achievement_burn2_complete_big,
        R.drawable.ic_achievement_burn2_incomplete_big),

    REMOVE_ADS(R.string.achievements_ads_title, R.string.achievements_ads_description,
        R.drawable.ic_achievement_ads_complete, R.drawable.ic_achievement_ads_incomplete,
        R.drawable.ic_achievement_ads_complete_big, R.drawable.ic_achievement_ads_incomplete_big),

    SET_GOAL(R.string.achievements_set_goal_title, R.string.achievements_set_goal_description,
        R.drawable.ic_achievement_set_goal_complete, R.drawable.ic_achievement_set_goal_incomplete,
        R.drawable.ic_achievement_set_goal_complete_big,
        R.drawable.ic_achievement_set_goal_incomplete_big),

    REACH_GOAL(R.string.achievements_reach_goal_title, R.string.achievements_reach_goal_description,
        R.drawable.ic_achievement_reach_goal_complete,
        R.drawable.ic_achievement_reach_goal_incomplete,
        R.drawable.ic_achievement_reach_goal_complete_big,
        R.drawable.ic_achievement_reach_goal_incomplete_big),

    CUSTOM_GOAL(R.string.achievements_custom_goal_title,
        R.string.achievements_custom_goal_description,
        R.drawable.ic_achievement_custom_goal_complete,
        R.drawable.ic_achievement_custom_goal_incomplete,
        R.drawable.ic_achievement_custom_goal_complete_big,
        R.drawable.ic_achievement_custom_goal_incomplete_big);

    private final @StringRes int title;
    private final @StringRes int description;
    private final @DrawableRes int achievedIcon;
    private final @DrawableRes int achievedIconBig;
    private final @DrawableRes int unachievedIcon;
    private final @DrawableRes int unachievedIconBig;

    Achievements(@StringRes int title, @StringRes int description, @DrawableRes int achievedIcon,
        @DrawableRes int unachievedIcon, @DrawableRes int achievedIconBig,
        @DrawableRes int unachievedIconBig) {
      this.title = title;
      this.description = description;
      this.achievedIcon = achievedIcon;
      this.achievedIconBig = achievedIconBig;
      this.unachievedIcon = unachievedIcon;
      this.unachievedIconBig = unachievedIconBig;
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

    public @DrawableRes int getAchievedIconBig() {
      return achievedIconBig;
    }

    public @DrawableRes int getUnachievedIcon() {
      return unachievedIcon;
    }

    public @DrawableRes int getUnachievedIconBig() {
      return unachievedIconBig;
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
      achievement.icon_big = Achievements.values()[i].getAchievedIconBig();
      achievement.empty_icon = Achievements.values()[i].getUnachievedIcon();
      achievement.empty_icon_big = Achievements.values()[i].getUnachievedIconBig();
      achievements.add(achievement);
    }
    return achievements;
  }
}

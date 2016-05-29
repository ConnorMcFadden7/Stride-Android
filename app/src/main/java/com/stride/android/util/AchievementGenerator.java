package com.stride.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import com.stride.android.R;
import com.stride.android.data.model.Achievement;
import com.stride.android.ioc.ServiceLocator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    /*BURN_ONE(R.string.achievements_burn1_title, R.string.achievements_burn1_description,
        R.drawable.ic_achievement_burn1_complete, R.drawable.ic_achievement_burn1_incomplete,
        R.drawable.ic_achievement_burn1_complete_big,
        R.drawable.ic_achievement_burn1_incomplete_big),

    BURN_TWO(R.string.achievements_burn2_title, R.string.achievements_burn2_description,
        R.drawable.ic_achievement_burn2_complete, R.drawable.ic_achievement_burn2_incomplete,
        R.drawable.ic_achievement_burn2_complete_big,
        R.drawable.ic_achievement_burn2_incomplete_big),*/

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

    static {
      Helper.getInstance().load();
    }

    private final @StringRes int title;
    private final @StringRes int description;
    private final @DrawableRes int achievedIcon;
    private final @DrawableRes int achievedIconBig;
    private final @DrawableRes int unachievedIcon;
    private final @DrawableRes int unachievedIconBig;
    private boolean isReached;
    private String dateReached;

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

    public boolean isReached() {
      return isReached;
    }

    public String getDateReached() {
      return dateReached;
    }

    public void setReached() {
      this.isReached = true;
      Helper.getInstance().save(this);
    }

    public static class Helper {

      private SharedPreferences mSharedPreferences;

      Helper() {
        //
      }

      static Helper getInstance() {
        return Singleton.INSTANCE;
      }

      Helper init(Context context) {
        mSharedPreferences =
            context.getSharedPreferences("achievement_prefs", Context.MODE_PRIVATE);
        load();
        return this;
      }

      void load() {
        for (final Achievements achievements : Achievements.values()) {
          achievements.isReached = mSharedPreferences.getBoolean(achievements.name(), false);
          achievements.dateReached =
              mSharedPreferences.getString(achievements.name() + "_date", "");
        }
      }

      void save(Achievements achievements) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(achievements.name(), achievements.isReached);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        editor.putString(achievements.name() + "_date", date);
        editor.apply();
      }

      void clear() {
        mSharedPreferences.edit().clear().apply();
        load();
      }

      static class Singleton {
        static final Helper INSTANCE = new Helper();

        static {
          INSTANCE.init(ServiceLocator.get().getContext());
        }
      }
    }
  }

  public static final int WALK_ONE_STEP_COUNT = 5000;
  public static final int WALK_TWO_STEP_COUNT = 15000;

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
      achievement.is_achieved = Achievements.values()[i].isReached();
      achievement.achieved_date = Achievements.values()[i].getDateReached();
      achievements.add(achievement);
    }
    return achievements;
  }

  public void setWalkOneReached(int steps) {
    if (steps >= WALK_ONE_STEP_COUNT) {
      if (!Achievements.WALK_ONE.isReached()) {
        Achievements.WALK_ONE.setReached();
      }
    }
  }

  public void setWalkTwoReached(int steps) {
    if (steps >= WALK_TWO_STEP_COUNT) {
      if (!Achievements.WALK_TWO.isReached()) {
        Achievements.WALK_TWO.setReached();
      }
    }
  }

  public void achievePremium() {
    if (!Achievements.REMOVE_ADS.isReached()) {
      Achievements.REMOVE_ADS.setReached();
    }
  }
}

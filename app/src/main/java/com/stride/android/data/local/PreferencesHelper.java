package com.stride.android.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by connormcfadden on 02/05/16.
 */
@Singleton public class PreferencesHelper {

  private SharedPreferences mPrefs;

  public static final String PREF_FILE_NAME_USER = "stride_user_prefs";
  public static final String PREF_KEY_PAUSED_STATE = "key_paused_state";
  public static final String PREF_KEY_GOAL = "key_goal";
  public static final String PREF_KEY_HAS_UPGRADED = "key_has_upgraded";

  @Inject PreferencesHelper(Context context) {
    mPrefs = context.getSharedPreferences(PREF_FILE_NAME_USER, Context.MODE_PRIVATE);
  }

  public boolean isPaused() {
    return mPrefs.getBoolean(PREF_KEY_PAUSED_STATE, false);
  }

  public void setUserGoal(int goal) {
    mPrefs.edit().putInt(PREF_KEY_GOAL, goal).apply();
  }

  public int getUserGoal() {
    return mPrefs.getInt(PREF_KEY_GOAL, -1);
  }

  public void setHasUpgraded(boolean hasUpgraded) {
    mPrefs.edit().putBoolean(PREF_KEY_HAS_UPGRADED, hasUpgraded).apply();
  }

  public boolean hasUpgraded() {
    return mPrefs.getBoolean(PREF_KEY_HAS_UPGRADED, false);
  }
}

package com.stride.android.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.stride.android.data.model.User;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by connormcfadden on 02/05/16.
 */
@Singleton public class PreferencesHelper {

  private SharedPreferences mPrefs;

  public static final String PREF_FILE_NAME_USER = "hairapp_user_prefs";
  public static final String PREF_KEY_FIRST_SIGN_IN = "key_first_sign_in";
  public static final String PREF_KEY_USER_OBJECT = "key_user";

  private final Gson mGson = new Gson();

  @Inject PreferencesHelper(Context context) {
    mPrefs = context.getSharedPreferences(PREF_FILE_NAME_USER, Context.MODE_PRIVATE);
  }

  public void setHasSignedInUp() {
    mPrefs.edit().putBoolean(PREF_KEY_FIRST_SIGN_IN, true).apply();
  }

  public boolean isSignedIn() {
    return mPrefs.getBoolean(PREF_KEY_FIRST_SIGN_IN, false);
  }

  public void saveUserData(User userData) {
    if (userData != null) {
      String string = mGson.toJson(userData);
      mPrefs.edit().putString(PREF_KEY_USER_OBJECT, string).apply();
    } else {
      mPrefs.edit().remove(PREF_KEY_USER_OBJECT).apply();
    }
  }

  public void removeUserData() {
    mPrefs.edit().remove(PREF_KEY_USER_OBJECT).apply();
  }

  public User getUserData() {
    String loyaltyStringData = mPrefs.getString(PREF_KEY_USER_OBJECT, null);
    if (loyaltyStringData != null) {
      return mGson.fromJson(loyaltyStringData, User.class);
    } else {
      return null;
    }
  }

  public void clearPreferences() {
    mPrefs.edit().clear().apply();
  }
}

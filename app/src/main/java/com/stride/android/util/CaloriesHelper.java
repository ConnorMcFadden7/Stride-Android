package com.stride.android.util;

import javax.inject.Inject;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class CaloriesHelper {

  @Inject CaloriesHelper() {
    //
  }

  public int getCalories(int steps) {
    // actual algorithm
    return steps + 30;
  }
}

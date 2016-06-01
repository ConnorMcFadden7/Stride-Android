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
    // // TODO: 01/06/16 unfinished; a real algorithm
    return steps + 30;
  }
}

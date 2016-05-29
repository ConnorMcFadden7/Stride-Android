package com.stride.android.data.provider;

import com.stride.android.data.persistence.DatabaseHelper;
import javax.inject.Inject;

/**
 * Rather than communicating directly with the DatabaseHelper in different parts of the app,
 * we create this StepsProvider which acts as the middle man.
 *
 * Created by connormcfadden on 29/05/16.
 */
public class StepsProvider {

  private final DatabaseHelper databaseHelper;

  @Inject StepsProvider(DatabaseHelper databaseHelper) {
    this.databaseHelper = databaseHelper;
  }

  public int getSteps(String date) {
    return databaseHelper.getStepsForDate(date);
  }

  public int getAverageSteps() {
    return databaseHelper.getAverageSteps();
  }

  public void insertSteps(String date, int steps) {
    databaseHelper.insertOrUpdateSteps(date, steps);
  }
}

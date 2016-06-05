package com.stride.android.data.provider;

import com.stride.android.data.model.ProgressHistory;
import com.stride.android.data.persistence.DatabaseHelper;
import com.stride.android.util.TimeUtils;
import javax.inject.Inject;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.functions.Action1;

/**
 * Rather than communicating directly with the DatabaseHelper in different parts of the app,
 * we create this StepsProvider which acts as the middle man.
 *
 * Created by connormcfadden on 29/05/16.
 */
public class StepsProvider {

  private final DatabaseHelper databaseHelper;
  private int average = 0;
  private int stepsForDate = 0;

  @Inject StepsProvider(DatabaseHelper databaseHelper) {
    this.databaseHelper = databaseHelper;
  }

  public int getSteps(String date) {
    databaseHelper.getStepsForDateRx(date).subscribe(new Observer<Integer>() {
      @Override public void onCompleted() {
        //
      }

      @Override public void onError(Throwable e) {
        //
      }

      @Override public void onNext(Integer integer) {
        stepsForDate = integer;
      }
    });
    return stepsForDate;
  }

  public int getAverageSteps() {
    databaseHelper.getAverageStepsRx().subscribe(new Observer<Integer>() {
      @Override public void onCompleted() {
        //
      }

      @Override public void onError(Throwable e) {
        //
      }

      @Override public void onNext(Integer integer) {
        average = integer;
      }
    });

    return average;
  }

  public void insertSteps(String date, int steps) {
    databaseHelper.insertOrUpdateSteps(date, steps);
  }

  public int getTotalStepsApartFromToday() {
    int totalSteps = 0;
    for (ProgressHistory progressHistory : databaseHelper.getProgressHistory()) {
      if (!progressHistory.date.equals(TimeUtils.getToday())) {
        totalSteps += progressHistory.steps;
      }
    }

    return totalSteps;
  }
}

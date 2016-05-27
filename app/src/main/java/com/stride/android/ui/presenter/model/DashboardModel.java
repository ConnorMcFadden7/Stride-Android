package com.stride.android.ui.presenter.model;

import javax.inject.Inject;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class DashboardModel {

  public int todaysSteps;
  public int yesterdaysSteps;
  public int caloriesBurnt;

  public DashboardModel(int todaysSteps, int yesterdaysSteps, int caloriesBurnt) {
    this.todaysSteps = todaysSteps;
    this.yesterdaysSteps = yesterdaysSteps;
    this.caloriesBurnt = caloriesBurnt;
  }

  public int getTodaysSteps() {
    return todaysSteps;
  }

  public int getYesterdaysSteps() {
    return yesterdaysSteps;
  }

  public int getCaloriesBurnt() {
    return caloriesBurnt;
  }

  public static class DashboardMapper {

    @Inject DashboardMapper() {
      //
    }

    public DashboardModel map(int todaysSteps, int yesterdaysSteps, int caloriesBurnt) {
      return new DashboardModel(todaysSteps, yesterdaysSteps, caloriesBurnt);
    }
  }
}

package com.stride.android.ui.presenter.model;

import javax.inject.Inject;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class DashboardModel {

  public int todaysSteps;
  public int yesterdaysSteps;
  public int caloriesBurnt;
  public int totalAverage;

  public DashboardModel(int todaysSteps, int yesterdaysSteps, int caloriesBurnt, int totalAverage) {
    this.todaysSteps = todaysSteps;
    this.yesterdaysSteps = yesterdaysSteps;
    this.caloriesBurnt = caloriesBurnt;
    this.totalAverage = totalAverage;
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

  public int getTotalAverage() {
    return totalAverage;
  }

  public static class DashboardMapper {

    @Inject DashboardMapper() {
      //
    }

    public DashboardModel map(int todaysSteps, int yesterdaysSteps, int caloriesBurnt,
        int totalAverage) {
      return new DashboardModel(todaysSteps, yesterdaysSteps, caloriesBurnt, totalAverage);
    }
  }
}

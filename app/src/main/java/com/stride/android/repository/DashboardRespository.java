package com.stride.android.repository;

import com.stride.android.data.provider.StepsProvider;
import com.stride.android.ui.presenter.model.DashboardModel;
import com.stride.android.util.TimeUtils;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class DashboardRespository {

  private final DashboardModel.DashboardMapper dashboardMapper;
  private final StepsProvider stepsProvider;

  @Inject DashboardRespository(DashboardModel.DashboardMapper dashboardMapper,
      StepsProvider stepsProvider) {
    this.dashboardMapper = dashboardMapper;
    this.stepsProvider = stepsProvider;
  }

  public DashboardModel getDashboardModel() {
    return dashboardMapper.map(getTodaysSteps(), getYesterdaysSteps(), 332, getAverageSteps());
  }

  public int getTodaysSteps() {
    return stepsProvider.getSteps(TimeUtils.getToday());
  }

  public int getYesterdaysSteps() {
    return stepsProvider.getSteps(TimeUtils.getYesterday());
  }

  public int getAverageSteps() {
    return stepsProvider.getAverageSteps();
  }
}

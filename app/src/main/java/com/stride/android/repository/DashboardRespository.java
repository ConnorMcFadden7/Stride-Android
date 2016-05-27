package com.stride.android.repository;

import com.stride.android.data.persistence.DatabaseHelper;
import com.stride.android.ui.presenter.model.DashboardModel;
import com.stride.android.util.TimeUtils;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class DashboardRespository {

  private final DashboardModel.DashboardMapper dashboardMapper;
  private final DatabaseHelper databaseHelper;

  @Inject DashboardRespository(DashboardModel.DashboardMapper dashboardMapper,
      DatabaseHelper databaseHelper) {
    this.dashboardMapper = dashboardMapper;
    this.databaseHelper = databaseHelper;
  }

  public DashboardModel getDashboardModel() {
    return dashboardMapper.map(getTodaysSteps(), 3214, 332);
  }

  public int getTodaysSteps() {
    return databaseHelper.getSteps(TimeUtils.getToday());
  }
}

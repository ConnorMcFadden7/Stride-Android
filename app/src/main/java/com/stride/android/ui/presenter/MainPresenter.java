package com.stride.android.ui.presenter;

import com.stride.android.ui.activity.ActivityFacade;
import com.stride.android.ui.presenter.view.MainView;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class MainPresenter {

  private final ActivityFacade mActivityFacade;

  @Inject MainPresenter(ActivityFacade activityFacade) {
    this.mActivityFacade = activityFacade;
  }

  public void present(MainView mainView) {
    //
  }
}

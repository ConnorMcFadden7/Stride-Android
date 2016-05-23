package com.stride.android;

import android.app.Application;
import com.google.common.annotations.VisibleForTesting;
import com.squareup.otto.Bus;
import com.stride.android.ioc.ApplicationComponent;
import com.stride.android.ioc.DaggerApplicationComponent;
import com.stride.android.ioc.module.ApplicationModule;
import javax.inject.Inject;

public class StrideApplication extends Application {

  private static StrideApplication sStrideApplication;
  @Inject Bus mBus;
  private ApplicationComponent mComponent;

  @Override public void onCreate() {
    super.onCreate();
    sStrideApplication = this;
    initComponent();
    mComponent.inject(this);
    mBus.register(this);
  }

  @Override public void onTerminate() {
    super.onTerminate();
  }

  private void initComponent() {
    if (mComponent == null) {
      mComponent = DaggerApplicationComponent.builder()
          .applicationModule(new ApplicationModule(this))
          .build();
    }
  }

  public ApplicationComponent getComponent() {
    return mComponent;
  }

  @VisibleForTesting public void setComponent(ApplicationComponent applicationComponent) {
    this.mComponent = applicationComponent;
  }

  public static StrideApplication get() {
    return sStrideApplication;
  }

  public Bus getBus() {
    return mBus;
  }
}

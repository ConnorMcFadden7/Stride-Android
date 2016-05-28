package com.stride.android;

import android.app.Application;
import com.google.common.annotations.VisibleForTesting;
import com.squareup.otto.Bus;
import com.stride.android.ioc.ApplicationComponent;
import com.stride.android.ioc.DaggerApplicationComponent;
import com.stride.android.ioc.ServiceLocator;
import com.stride.android.ioc.module.ApplicationModule;
import com.stride.android.util.StethoUtil;
import javax.inject.Inject;

public class StrideApplication extends Application {

  private static StrideApplication sStrideApplication;
  @Inject ServiceLocator mServiceLocator;
  @Inject Bus mBus;
  @Inject StethoUtil mStethoUtil;
  private ApplicationComponent mComponent;

  @Override public void onCreate() {
    super.onCreate();
    sStrideApplication = this;
    initComponent();
    mComponent.inject(this);
    mBus.register(this);

    //// TODO: 27/05/16 only if debug build
    mStethoUtil.initStetho(this);
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

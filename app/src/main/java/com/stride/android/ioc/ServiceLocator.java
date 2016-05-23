package com.stride.android.ioc;

import android.content.Context;
import com.squareup.otto.Bus;
import com.stride.android.data.local.PreferencesHelper;
import dagger.Lazy;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by connormcfadden on 06/05/16.
 */
@Singleton public class ServiceLocator {

  private static ServiceLocator serviceLocator;

  public static ServiceLocator get() {
    return serviceLocator;
  }

  @Inject Lazy<PreferencesHelper> preferencesHelperLazy;
  @Inject Lazy<Bus> busLazy;
  @Inject Lazy<Context> contextLazy;

  @Inject ServiceLocator() {
    serviceLocator = this;
  }

  public Bus getBus() {
    return busLazy.get();
  }

  public PreferencesHelper getPreferences() {
    return preferencesHelperLazy.get();
  }

  public Context getContext() {
    return contextLazy.get();
  }
}


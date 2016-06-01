package com.stride.android.ioc.module;

import android.content.Context;
import com.squareup.otto.Bus;
import com.stride.android.StrideApplication;
import com.stride.android.service.ApplicationBus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by connormcfadden on 02/05/16.
 */
@Module public class ApplicationModule {

  private final StrideApplication application;

  public ApplicationModule(StrideApplication application) {
    this.application = application;
  }

  @Provides Context provideContext() {
    return application.getApplicationContext();
  }

  @Provides @Singleton Bus provideApplicationBus(ApplicationBus applicationBus) {
    return applicationBus;
  }
}

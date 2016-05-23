package com.stride.android.ioc.module;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import com.stride.android.ioc.scope.ActivityScope;
import com.stride.android.ui.activity.ActivityFacade;
import dagger.Module;
import dagger.Provides;

/**
 * Created by connormcfadden on 02/05/16.
 */
@Module public class ActivityModule {

  private final AppCompatActivity activity;

  public ActivityModule(AppCompatActivity activity) {
    this.activity = activity;
  }

  @Provides @ActivityScope public Activity provideActivity() {
    return activity;
  }

  @Provides @ActivityScope public ActivityFacade provideActivityFacade() {
    return ActivityFacade.wrap(activity);
  }
}

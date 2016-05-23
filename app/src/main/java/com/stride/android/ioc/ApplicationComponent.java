package com.stride.android.ioc;

import com.stride.android.StrideApplication;
import com.stride.android.ioc.module.ActivityModule;
import com.stride.android.ioc.module.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by connormcfadden on 02/05/16.
 */
@Singleton @Component(modules = {
    ApplicationModule.class
}) public interface ApplicationComponent {

  ActivityComponent from(ActivityModule activityModule);

  void inject(StrideApplication application);
}

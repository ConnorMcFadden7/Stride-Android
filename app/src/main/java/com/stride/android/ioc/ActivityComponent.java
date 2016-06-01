package com.stride.android.ioc;

import android.app.Activity;
import com.stride.android.ioc.module.ActivityModule;
import com.stride.android.ioc.module.FragmentModule;
import com.stride.android.ioc.scope.ActivityScope;
import com.stride.android.ui.activity.ActivityFacade;
import com.stride.android.ui.activity.BaseActivity;
import com.stride.android.ui.activity.MainActivity;
import dagger.Subcomponent;

/**
 * Created by connormcfadden on 02/05/16.
 */
@ActivityScope @Subcomponent(modules = ActivityModule.class) public interface ActivityComponent {
  Activity activity();

  ActivityFacade activityFacade();

  FragmentComponent from(FragmentModule fragmentModule);

  void inject(BaseActivity activity);

  void inject(MainActivity activity);
}


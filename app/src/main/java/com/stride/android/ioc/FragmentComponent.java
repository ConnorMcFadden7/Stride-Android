package com.stride.android.ioc;

import android.support.v4.app.Fragment;
import com.stride.android.ioc.module.FragmentModule;
import com.stride.android.ioc.scope.FragmentScope;
import com.stride.android.ui.fragment.BaseFragment;
import dagger.Subcomponent;

/**
 * Created by connormcfadden on 02/05/16.
 */
@FragmentScope @Subcomponent(modules = FragmentModule.class) public interface FragmentComponent {
  Fragment fragment();

  void inject(BaseFragment baseFragment);

  //void inject(OnboardingWalkthroughFragment onboardingWalkthroughFragment);
}

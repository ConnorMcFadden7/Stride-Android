package com.stride.android.ioc.module;

import android.support.v4.app.Fragment;
import com.stride.android.ioc.scope.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by connormcfadden on 02/05/16.
 */
@Module public class FragmentModule {

  private final Fragment fragment;

  public FragmentModule(Fragment fragment) {
    this.fragment = fragment;
  }

  @Provides @FragmentScope Fragment provideFragment() {
    return fragment;
  }
}


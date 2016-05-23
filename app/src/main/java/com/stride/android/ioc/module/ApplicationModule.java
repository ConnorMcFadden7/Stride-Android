package com.stride.android.ioc.module;

import android.content.Context;
import com.hairapp.android.service.ApplicationBus;
import com.squareup.otto.Bus;
import com.stride.android.StrideApplication;
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

 /* @Provides @Singleton CrashlyticsCore provideCrashlytics() {
    return Crashlytics.getInstance().core;
  }

  @Provides ContentResolver provideContentResolver() {
    return application.getContentResolver();
  }

  @Provides ConnectivityManager provideConnectivityManager(Context context) {
    return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
  }

  @Provides Resources provideResources(Context context) {
    return context.getResources();
  }

  @Nullable @Provides AccessToken provideAccessToken() {
    return AccessToken.getCurrentAccessToken();
  }

  @Provides LoginManager provideLoginManager() {
    return LoginManager.getInstance();
  }

  @Provides ExoPlayer provideExoPlayer() {
    return ExoPlayer.Factory.newInstance(2);
  }

  @Provides ImagePipelineConfig provideImagePipelineConfig(Context context,
      @Shared OkHttpClient okHttpClient) {
    return ImagePipelineConfig.newBuilder(context)
        .setNetworkFetcher(new OkHttp3NetworkFetcher(okHttpClient))
        .build();
  }*/
}

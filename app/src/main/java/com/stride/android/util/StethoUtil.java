package com.stride.android.util;

import android.app.Application;
import com.facebook.stetho.Stetho;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class StethoUtil {

  @Inject StethoUtil() {

  }

  public void initStetho(Application application) {
    Stetho.initialize(Stetho.newInitializerBuilder(application)
        .enableDumpapp(Stetho.defaultDumperPluginsProvider(application))
        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(application))
        .build());
  }
}

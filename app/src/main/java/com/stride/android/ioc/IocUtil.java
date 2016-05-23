package com.stride.android.ioc;

import android.app.Application;
import android.content.Context;
import com.stride.android.StrideApplication;

/**
 * Created by connormcfadden on 23/05/16.
 */
public class IocUtil {

  public static ApplicationComponent getApplicationComponent(Context context) {
    return getApplicationComponent((Application) context.getApplicationContext());
  }

  public static ApplicationComponent getApplicationComponent(Application application) {
    return ((StrideApplication) application).getComponent();
  }
}

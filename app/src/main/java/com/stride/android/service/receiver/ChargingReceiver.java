package com.stride.android.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.stride.android.data.local.PreferencesHelper;
import com.stride.android.ioc.IocUtil;
import com.stride.android.service.SensorListener;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 23/05/16.
 */
public class ChargingReceiver extends BroadcastReceiver {

  @Inject PreferencesHelper mPreferencesHelper;

  @Override public void onReceive(final Context context, final Intent intent) {
    IocUtil.getApplicationComponent(context).inject(this);
    if (Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())
        && !mPreferencesHelper.isPaused()) {
      context.startService(new Intent(context, SensorListener.class).putExtra("action",
          SensorListener.ACTION_PAUSE));
    } else if (Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())
        && mPreferencesHelper.isPaused()) {
      context.startService(new Intent(context, SensorListener.class).putExtra("action",
          SensorListener.ACTION_RESUME));
    }
  }
}

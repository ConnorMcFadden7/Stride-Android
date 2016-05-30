package com.stride.android.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import com.stride.android.data.local.PreferencesHelper;
import com.stride.android.data.provider.StepsProvider;
import com.stride.android.ioc.IocUtil;
import com.stride.android.util.AchievementGenerator;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 23/05/16.
 */
public class SensorListener extends Service implements SensorEventListener {

  @Inject StepsProvider stepsProvider;
  @Inject PreferencesHelper preferencesHelper;
  @Inject AchievementGenerator achievementGenerator;

  private final static int NOTIFICATION_ID = 1;

  public final static String ACTION_PAUSE = "pause";
  public final static String ACTION_RESUME = "resume";

  private static boolean WAIT_FOR_VALID_STEPS = false;
  private static int stepsSinceRebooting;
  private static int todaysSteps = 0;
  private String cachedDate;

  private final static int MICROSECONDS_IN_ONE_MINUTE = 60000000;

  @Override public void onAccuracyChanged(final Sensor sensor, int accuracy) {
    //
  }

  @Override public void onSensorChanged(final SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {

      stepsSinceRebooting = (int) event.values[0];

      if (stepsSinceRebooting > 0) {
        if (stepsSinceRebooting == preferencesHelper.getUserGoal()) {
          if (!AchievementGenerator.Achievements.REACH_GOAL.isReached()) {
            AchievementGenerator.Achievements.REACH_GOAL.setReached();
          }
        }

        achievementGenerator.setWalkOneReached(stepsSinceRebooting);
        achievementGenerator.setWalkTwoReached(stepsSinceRebooting);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());

        if (stepsProvider.getSteps(date) == 0) {
          todaysSteps = 0;
          stepsProvider.insertSteps(date, 0);
        }

        //// TODO: 30/05/16 we really need to store the reboot count and use it here
        todaysSteps = stepsSinceRebooting - stepsProvider.getTotalStepsApartFromToday();
        if (todaysSteps > 0) {
          stepsProvider.insertSteps(date, todaysSteps);
        }
      }
    }
  }

  @Override public IBinder onBind(final Intent intent) {
    return null;
  }

  @Override public int onStartCommand(final Intent intent, int flags, int startId) {
    ((AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE)).set(
        AlarmManager.RTC, System.currentTimeMillis() + AlarmManager.INTERVAL_HOUR,
        PendingIntent.getService(getApplicationContext(), 2, new Intent(this, SensorListener.class),
            PendingIntent.FLAG_UPDATE_CURRENT));
    WAIT_FOR_VALID_STEPS = true;

    return START_STICKY;
  }

  @Override public void onCreate() {
    super.onCreate();
    IocUtil.getApplicationComponent(this).inject(this);
    reRegisterSensor();
  }

  @Override public void onTaskRemoved(final Intent rootIntent) {
    super.onTaskRemoved(rootIntent);
    // Restart service in 500 ms
    ((AlarmManager) getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC,
        System.currentTimeMillis() + 500,
        PendingIntent.getService(this, 3, new Intent(this, SensorListener.class), 0));
  }

  @Override public void onDestroy() {
    super.onDestroy();
    try {
      SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
      sm.unregisterListener(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void reRegisterSensor() {
    SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
    try {
      sm.unregisterListener(this);
    } catch (Exception e) {
      e.printStackTrace();
    }

    sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
        SensorManager.SENSOR_DELAY_NORMAL, 5 * MICROSECONDS_IN_ONE_MINUTE);
  }
}

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
import android.util.Log;
import android.widget.Toast;
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
  private static int steps;

  private final static int MICROSECONDS_IN_ONE_MINUTE = 60000000;

  @Override public void onAccuracyChanged(final Sensor sensor, int accuracy) {
    //
  }

  @Override public void onSensorChanged(final SensorEvent event) {
    Log.e("SensorListener", "onSensorChanged");
    // if (event.values[0] < Integer.MAX_VALUE) {
    // if (BuildConfig.DEBUG) Logger.log("probably not a real value: " + event.values[0]);
    steps = (int) event.values[0];

    Log.e("SensorListener", "onSensorChanged::steps: " + steps);

    Toast.makeText(SensorListener.this, "Steps: " + steps, Toast.LENGTH_SHORT).show();

    if (steps > 0) {
      //WAIT_FOR_VALID_STEPS = false;

      if (steps == preferencesHelper.getUserGoal()) {
        if (!AchievementGenerator.Achievements.REACH_GOAL.isReached()) {
          AchievementGenerator.Achievements.REACH_GOAL.setReached();
        }
      }

      achievementGenerator.setWalkOneReached(steps);
      achievementGenerator.setWalkTwoReached(steps);

      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      String date = sdf.format(new Date());
      stepsProvider.insertSteps(date, steps);

      //// TODO: 23/05/16 store in db
      //updateNotificationState();

      //// TODO: 23/05/16 widget
      // startService(new Intent(this, WidgetUpdateService.class));
    }
    //}
  }

  @Override public IBinder onBind(final Intent intent) {
    return null;
  }

  @Override public int onStartCommand(final Intent intent, int flags, int startId) {
    if (intent != null && ACTION_PAUSE.equals(intent.getStringExtra("action"))) {
     /* if (steps == 0) {
        Database db = Database.getInstance(this);
        steps = db.getCurrentSteps();
        db.close();
      }
      SharedPreferences prefs = getSharedPreferences("pedometer", Context.MODE_MULTI_PROCESS);
      if (prefs.contains("pauseCount")) { // resume counting
        int difference =
            steps - prefs.getInt("pauseCount", steps); // number of steps taken during the pause
        Database db = Database.getInstance(this);
        db.updateSteps(Util.getToday(), -difference);
        db.close();
        prefs.edit().remove("pauseCount").commit();
        updateNotificationState();
      } else { // pause counting
        // cancel restart
        ((AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE)).cancel(
            PendingIntent.getService(getApplicationContext(), 2,
                new Intent(this, SensorListener.class), PendingIntent.FLAG_UPDATE_CURRENT));
        prefs.edit().putInt("pauseCount", steps).commit();
        updateNotificationState();
        stopSelf();
        return START_NOT_STICKY;
      }

      */
    }

    // restart service every hour to get the current step count
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
    //    updateNotificationState();
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

  //// TODO: 23/05/16
  /*private void updateNotificationState() {
  //  SharedPreferences prefs = getSharedPreferences("pedometer", Context.MODE_MULTI_PROCESS);
    NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    if (prefs.getBoolean("notification", true)) {
      int goal = prefs.getInt("goal", 10000);
      Database db = Database.getInstance(this);
      int today_offset = db.getStepsForDate(Util.getToday());
      if (steps == 0) steps = db.getCurrentSteps(); // use saved value if we haven't anything better
      db.close();
      Notification.Builder notificationBuilder = new Notification.Builder(this);
      if (steps > 0) {
        if (today_offset == Integer.MIN_VALUE) today_offset = -steps;
        notificationBuilder.setProgress(goal, today_offset + steps, false)
            .setContentText(
                today_offset + steps >= goal ? getString(R.string.goal_reached_notification,
                    NumberFormat.getInstance(Locale.getDefault()).format((today_offset + steps)))
                    : getString(R.string.notification_text,
                        NumberFormat.getInstance(Locale.getDefault())
                            .format((goal - today_offset - steps))));
      } else { // still no step value?
        notificationBuilder.setContentText(
            getString(R.string.your_progress_will_be_shown_here_soon));
      }
      boolean isPaused = prefs.contains("pauseCount");
      notificationBuilder.setPriority(Notification.PRIORITY_MIN)
          .setShowWhen(false)
          .setContentTitle(
              isPaused ? getString(R.string.ispaused) : getString(R.string.notification_title))
          .setContentIntent(
              PendingIntent.getActivity(this, 0, new Intent(this, Activity_Main.class),
                  PendingIntent.FLAG_UPDATE_CURRENT))
          .setSmallIcon(R.drawable.ic_notification)
          .addAction(isPaused ? R.drawable.ic_resume : R.drawable.ic_pause,
              isPaused ? getString(R.string.resume) : getString(R.string.pause),
              PendingIntent.getService(this, 4,
                  new Intent(this, SensorListener.class).putExtra("action", ACTION_PAUSE),
                  PendingIntent.FLAG_UPDATE_CURRENT))
          .setOngoing(true);
      nm.notify(NOTIFICATION_ID, notificationBuilder.build());
    } else {
      nm.cancel(NOTIFICATION_ID);
    }
  }
  */

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

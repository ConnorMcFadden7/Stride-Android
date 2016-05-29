package com.stride.android.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by connormcfadden on 23/05/16.
 */
public class ShutdownReceiver extends BroadcastReceiver {

  @Override public void onReceive(final Context context, final Intent intent) {
    /*Database db = Database.getInstance(context);
    // if it's already a new day, add the temp. steps to the last one
    if (db.getStepsForDate(Util.getToday()) == Integer.MIN_VALUE) {
      int steps = db.getCurrentSteps();
      int pauseDifference =
          steps - context.getSharedPreferences("pedometer", Context.MODE_MULTI_PROCESS)
              .getInt("pauseCount", steps);
      db.insertNewDay(Util.getToday(), steps - pauseDifference);
      if (pauseDifference > 0) {
        // update pauseCount for the new day
        context.getSharedPreferences("pedometer", Context.MODE_MULTI_PROCESS)
            .edit()
            .putInt("pauseCount", steps)
            .commit();
      }
    } else {
      db.updateSteps(Util.getToday(), db.getCurrentSteps());
    }
    // current steps will be reset on boot @see BootReceiver
    db.close();
    */
  }
}

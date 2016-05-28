package com.stride.android.util;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class TimeUtils {

  public static boolean isToday(long time) {
    final Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);

    final int thenYear = calendar.get(Calendar.YEAR);
    final int thenMonth = calendar.get(Calendar.MONTH);
    final int thenMonthDay = calendar.get(Calendar.DAY_OF_MONTH);

    calendar.setTimeInMillis(System.currentTimeMillis());
    return (thenYear == calendar.get(Calendar.YEAR))
        && (thenMonth == calendar.get(Calendar.MONTH))
        && (thenMonthDay == calendar.get(Calendar.DAY_OF_MONTH));
  }

  public static boolean isYesterday(long time) {
    return true;
  }

  public static String getToday() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(new Date(calendar.getTimeInMillis()));
  }

  public static String getYesterday() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(new Date(calendar.getTimeInMillis()));
  }
}

package com.stride.android.data.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.Calendar;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by connormcfadden on 23/05/16.
 */
@Singleton public class DatabaseHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "stride";

  // IMPORTANT NOTE: When changing DATABASE_VERSION please update onUpgrade
  public static final int DATABASE_VERSION = 1;

  // Tables definitions
  private static final String TABLE_STEPS = "CREATE TABLE progress (" +
      "steps INTEGER NOT NULL, " +
      "date INTEGER NOT NULL);";

  @Inject DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    // Create tables
    db.execSQL(TABLE_STEPS);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (newVersion > oldVersion) {
      //
    }
  }

  public void insertDay(long date, int steps) {
    Log.e("DatabaseHelper", "insertDay::date: " + date + " steps: " + steps);
    getWritableDatabase().beginTransaction();
    try {
      Cursor c = getReadableDatabase().query("progress", new String[] { "date" }, "date = ?",
          new String[] { String.valueOf(date) }, null, null, null);
      if (c.getCount() == 0 && steps >= 0) {
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("steps", steps);
        getWritableDatabase().insert("progress", null, values);

        Log.e("DatabaseHelper", "inserting");

        // add 'steps' to yesterdays count
        Calendar yesterday = Calendar.getInstance();
        yesterday.setTimeInMillis(date);
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        updateSteps(yesterday.getTimeInMillis(), steps);
      }
      c.close();
      getWritableDatabase().setTransactionSuccessful();
    } finally {
      getWritableDatabase().endTransaction();
    }
  }

  //// TODO: 23/05/16 run these as constants
  public void updateSteps(final long date, int steps) {
    getWritableDatabase().execSQL(
        "UPDATE " + "progress" + " SET steps = steps + " + steps + " WHERE date = " + date);
  }

  public int getDateGivenSteps(final long date) {
    Cursor c = getReadableDatabase().query("progress", new String[] { "steps" }, "date = ?",
        new String[] { String.valueOf(date) }, null, null, null);
    c.moveToFirst();
    int re;
    if (c.getCount() == 0) {
      re = Integer.MIN_VALUE;
    } else {
      re = c.getInt(0);
    }
    c.close();
    return re;
  }

  public int getTotalSteps() {
    Cursor c =
        getReadableDatabase().query("progress", new String[] { "MAX(steps)" }, "date > 0", null,
            null, null, null);
    c.moveToFirst();
    int re = c.getInt(0);
    c.close();
    return re;
  }

  public int getDays() {
    /*Cursor c = getReadableDatabase()
        .query("progress", new String[] { "COUNT(*)" }, "steps > ? AND date < ? AND date > 0",
            new String[] { String.valueOf(0), String.valueOf(Util.getToday()) }, null, null, null);
    c.moveToFirst();
    // todays is not counted yet
    int re = c.getInt(0) + 1;
    c.close();
    return re <= 0 ? 1 : re;
  }*/

    return 0;
  }

  public int getCurrentSteps() {
    int re = getDateGivenSteps(-1);
    return re == Integer.MIN_VALUE ? 0 : re;
  }
}

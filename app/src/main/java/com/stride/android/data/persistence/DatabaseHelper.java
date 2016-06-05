package com.stride.android.data.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stride.android.data.model.ProgressHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by connormcfadden on 23/05/16.
 */
@Singleton public class DatabaseHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "stride";

  // When changing DATABASE_VERSION update onUpgrade
  public static final int DATABASE_VERSION = 1;

  // Tables definitions
  private static final String TABLE_STEPS = "CREATE TABLE progress (" +
      "steps INTEGER NOT NULL, " +
      "date TEXT NOT NULL);";

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

  public void insertOrUpdateSteps(String date, int steps) {
    getWritableDatabase().beginTransaction();
    try {
      Cursor c = getReadableDatabase().query("progress", new String[] { "date" }, "date = ?",
          new String[] { String.valueOf(date) }, null, null, null);
      c.moveToFirst();
      if (steps >= 0) {
        ContentValues values = new ContentValues();
        String latestDate = "";
        if (c.getCount() > 0) {
          latestDate = c.getString(0);
        }
        if (latestDate.equals(date)) {
          values.put("steps", steps);
          getWritableDatabase().update("progress", values, "date=?", new String[] { date });
        } else {
          values.put("date", date);
          values.put("steps", steps);
          getWritableDatabase().insert("progress", null, values);
        }
      }
      c.close();
      getWritableDatabase().setTransactionSuccessful();
    } finally {
      getWritableDatabase().endTransaction();
    }
  }

  /**
   * Creates an Observable which emits the average step count.
   */
  public Observable<Integer> getAverageStepsRx() {
    return Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override public void call(Subscriber<? super Integer> subscriber) {
        subscriber.onNext(getAverageSteps());
        subscriber.onCompleted();
      }
    }).subscribeOn(Schedulers.io());
  }

  public int getAverageSteps() {
    Cursor cursor =
        getReadableDatabase().query("progress", new String[] { "AVG(steps)" }, "date > 0", null,
            null, null, null);
    cursor.moveToFirst();
    int average = cursor.getInt(0);
    cursor.close();
    return average;
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

  /**
   * Creates an Observable which emits the steps for a given date.
   */
  public Observable<Integer> getStepsForDateRx(final String date) {
    return Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override public void call(Subscriber<? super Integer> subscriber) {
        subscriber.onNext(getStepsForDate(date));
        subscriber.onCompleted();
      }
    });
  }

  public int getStepsForDate(final String date) {
    Cursor stepsCursor =
        getReadableDatabase().query("progress", new String[] { "steps" }, "date = ?",
            new String[] { date }, null, null, null);
    stepsCursor.moveToFirst();
    int steps = 0;
    if (stepsCursor.getCount() > 0) {
      steps = stepsCursor.getInt(0);
    }
    stepsCursor.close();
    return steps;
  }

  /**
   * Creates an Observable which emits the list of progress history.
   */
  public Observable<List<ProgressHistory>> getProgressHistoryRx() {
    return Observable.create(new Observable.OnSubscribe<List<ProgressHistory>>() {
      @Override public void call(Subscriber<? super List<ProgressHistory>> subscriber) {
        subscriber.onNext(getProgressHistory());
        subscriber.onCompleted();
      }
    }).subscribeOn(Schedulers.io());
  }

  public List<ProgressHistory> getProgressHistory() {
    List<ProgressHistory> progressHistories = new ArrayList<>();

    Cursor progressCursor =
        getReadableDatabase().rawQuery("SELECT * FROM progress ORDER BY rowid DESC", null);
    if (progressCursor.moveToFirst()) {
      while (!progressCursor.isAfterLast()) {
        int steps = progressCursor.getInt(0);
        String date = progressCursor.getString(1);

        ProgressHistory progressHistory = new ProgressHistory();
        progressHistory.steps = steps;
        progressHistory.date = date;
        progressHistories.add(progressHistory);
        progressCursor.moveToNext();
      }
    }

    return progressHistories;
  }
}

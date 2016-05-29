package com.stride.android.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by connormcfadden on 29/05/16.
 */
public class ProgressHistory implements Parcelable {

  public String date;
  public int steps;

  public ProgressHistory() {

  }

  protected ProgressHistory(Parcel in) {
    date = in.readString();
    steps = in.readInt();
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(date);
    dest.writeInt(steps);
  }

  public static final Parcelable.Creator<ProgressHistory> CREATOR =
      new Parcelable.Creator<ProgressHistory>() {
        @Override public ProgressHistory createFromParcel(Parcel in) {
          return new ProgressHistory(in);
        }

        @Override public ProgressHistory[] newArray(int size) {
          return new ProgressHistory[size];
        }
      };
}

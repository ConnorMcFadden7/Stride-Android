package com.stride.android.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class Achievement implements Parcelable {

  public int id;
  public int icon = 0;
  public int icon_big = 0;
  public int empty_icon = 0;
  public int empty_icon_big = 0;
  public int title = 0;
  public String achieved_date = "";
  public int description = 0;
  public int progress = 0;
  public boolean is_achieved = false;

  public Achievement() {

  }

  protected Achievement(Parcel in) {
    id = in.readInt();
    icon = in.readInt();
    icon_big = in.readInt();
    empty_icon = in.readInt();
    empty_icon_big = in.readInt();
    title = in.readInt();
    achieved_date = in.readString();
    description = in.readInt();
    progress = in.readInt();
    is_achieved = in.readByte() != 0;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(id);
    dest.writeInt(icon);
    dest.writeInt(icon_big);
    dest.writeInt(empty_icon);
    dest.writeInt(empty_icon_big);
    dest.writeInt(title);
    dest.writeString(achieved_date);
    dest.writeInt(description);
    dest.writeInt(progress);
    dest.writeByte((byte) (is_achieved ? 1 : 0));
  }

  public static final Parcelable.Creator<Achievement> CREATOR =
      new Parcelable.Creator<Achievement>() {
        @Override public Achievement createFromParcel(Parcel in) {
          return new Achievement(in);
        }

        @Override public Achievement[] newArray(int size) {
          return new Achievement[size];
        }
      };
}

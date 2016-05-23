package com.stride.android.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by connormcfadden on 02/05/16.
 */
public class User implements Parcelable {

  public String userId;
  public byte[] photo;
  public String photo_url = "";
  public String username = "";
  public String name = "";
  public String bio;
  public String location;
  public String email;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.userId);
    dest.writeString(this.username);
    dest.writeString(this.name);
    dest.writeString(this.bio);
    dest.writeString(this.location);
    dest.writeString(this.photo_url);
    dest.writeInt(this.photo.length);
    dest.writeByteArray(this.photo);
    dest.writeString(this.email);
  }

  public User() {

  }

  public User(String username, String name, String bio, String location, String email) {
    this.username = username;
    this.name = name;
    this.bio = bio;
    this.location = location;
    this.email = email;
  }

  private User(Parcel in) {
    this.userId = in.readString();
    this.photo_url = in.readString();
    this.photo = new byte[in.readInt()];
    this.photo = new byte[in.readInt()];
    //in.readByteArray(this.photo);
    this.username = in.readString();
    this.name = in.readString();
    this.bio = in.readString();
    this.location = in.readString();
    this.email = in.readString();
  }

  public static final Creator<User> CREATOR = new Creator<User>() {
    public User createFromParcel(Parcel source) {
      return new User(source);
    }

    public User[] newArray(int size) {
      return new User[size];
    }
  };

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (!photo.equals(user.photo)) return false;
    if (!username.equals(user.username)) return false;
    if (!name.equals(user.name)) return false;
    if (!photo_url.equals(user.photo_url)) return false;
    if (!bio.equals(user.bio)) return false;
    if (!location.equals(user.location)) return false;

    return true;
  }
}
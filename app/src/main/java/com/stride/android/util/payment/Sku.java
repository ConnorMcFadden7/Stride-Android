package com.stride.android.util.payment;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.Pair;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class Sku implements Parcelable {

  public enum Period {
    ONE_OFF
  }

  public enum Discount {
    NONE
  }

  public static final class Key extends Pair<Period, Discount> {
    public Key(Period first, Discount second) {
      super(first, second);
    }

    public Key(Period monthly) {
      this(monthly, Discount.NONE);
    }
  }

  private final Period period;
  private final Discount discount;
  private final String name;
  private final String price;

  public Sku(Period period, String name, String price) {
    this(period, Discount.NONE, name, price);
  }

  public Sku(Period period, Discount discount, String name, String price) {
    this.period = period;
    this.discount = discount;
    this.name = name;
    this.price = price;
  }

  public Period period() {
    return period;
  }

  public Discount discount() {
    return discount;
  }

  public final String name() {
    return name;
  }

  public String price() {
    return price;
  }

  public Key key() {
    return new Key(period, discount);
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.period == null ? -1 : this.period.ordinal());
    dest.writeInt(this.discount == null ? -1 : this.discount.ordinal());
    dest.writeString(this.name);
    dest.writeString(this.price);
  }

  protected Sku(Parcel in) {
    int tmpPeriod = in.readInt();
    this.period = tmpPeriod == -1 ? null : Period.values()[tmpPeriod];
    int tmpDiscount = in.readInt();
    this.discount = tmpDiscount == -1 ? null : Discount.values()[tmpDiscount];
    this.name = in.readString();
    this.price = in.readString();
  }

  public static final Creator<Sku> CREATOR = new Creator<Sku>() {
    public Sku createFromParcel(Parcel source) {
      return new Sku(source);
    }

    public Sku[] newArray(int size) {
      return new Sku[size];
    }
  };

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Sku sku = (Sku) o;

    if (period != sku.period) return false;
    return discount == sku.discount;
  }

  @Override public int hashCode() {
    int result = period != null ? period.hashCode() : 0;
    result = 31 * result + (discount != null ? discount.hashCode() : 0);
    return result;
  }
}
package com.stride.android.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class StringUtil {

  public final static String WHITESPACE = " ";

  public static String formatNumber(int number) {
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    return formatter.format(number);
  }

  public static String getLocalisedInteger(int number) {
    return NumberFormat.getInstance(Locale.getDefault()).format(number);
  }
}


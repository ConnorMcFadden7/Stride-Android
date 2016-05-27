package com.stride.android.util;

import java.text.DecimalFormat;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class StringUtil {

  public final static String WHITESPACE = " ";

  public static String formatNumber(int number) {
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    return formatter.format(number);
  }
}

package com.stride.android.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.StringRes;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import com.stride.android.R;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class SpannableUtil {

  public static CharSequence formatTextAndBlueValue(Context context, @StringRes final int stringId,
      int value) {
    String formattedNumber = StringUtil.getLocalisedInteger(value);
    String stringToFormat = context.getString(stringId, formattedNumber);
    int start = stringToFormat.indexOf(formattedNumber);
    int end = start + formattedNumber.length();
    CharSequence formattedString = getFormattedString(context, stringId, value);
    return getFormattedTextInRange(context, formattedString, start, end);
  }

  public static Spannable getFormattedTextInRange(Context context, CharSequence text, int start,
      int end) {
    SpannableStringBuilder span = new SpannableStringBuilder(text);
    span.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)),
        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    span.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    span.setSpan(new RelativeSizeSpan(1.0f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    return span;
  }

  public static CharSequence getFormattedString(Context context, int resourceId,
      int numberToFormat) {
    String stringToFormat =
        context.getString(resourceId, StringUtil.getLocalisedInteger(numberToFormat));
    return toBold(stringToFormat, StringUtil.getLocalisedInteger(numberToFormat));
  }

  private static CharSequence toBold(String stringToFormat, String toBold) {
    int start = stringToFormat.indexOf(toBold);
    int end = start + toBold.length();
    final SpannableStringBuilder spannableStringBuilder =
        new SpannableStringBuilder(stringToFormat);
    spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    return spannableStringBuilder;
  }
}

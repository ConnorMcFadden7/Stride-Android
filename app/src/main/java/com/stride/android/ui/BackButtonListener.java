package com.stride.android.ui;

/**
 * Created by connormcfadden on 29/05/16.
 */
public interface BackButtonListener {

  BackButtonListener NULL = new BackButtonListener() {
    @Override public void onBackPressed() {
      // Empty
    }
  };

  void onBackPressed();
}

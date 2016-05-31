package com.stride.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.stride.android.R;

/**
 * Created by connormcfadden on 29/05/16.
 */
public class SplashActivity extends BaseActivity {

  private static final int SPLASH_SCREEN_DURATION = 3000;

  @Override public boolean isHomeAsUpEnabled() {
    return false;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    final Handler handler = new Handler();

    handler.postDelayed(new Runnable() {
      @Override public void run() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
      }
    }, SPLASH_SCREEN_DURATION);
  }
}

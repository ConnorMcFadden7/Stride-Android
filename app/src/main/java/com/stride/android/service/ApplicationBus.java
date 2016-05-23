package com.hairapp.android.service;

import android.os.Handler;
import android.os.Looper;
import com.squareup.otto.Bus;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Application Bus allows us to send Otto events from both on & off the main thread
 */
@Singleton public class ApplicationBus extends Bus {

  private final Handler handler = new Handler(Looper.getMainLooper());

  @Inject ApplicationBus() {
    //
  }

  @Override public void post(final Object event) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      super.post(event);
    } else {
      handler.post(new Runnable() {
        @Override public void run() {
          ApplicationBus.super.post(event);
        }
      });
    }
  }
}
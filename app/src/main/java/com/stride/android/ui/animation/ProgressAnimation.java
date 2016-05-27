package com.stride.android.ui.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class ProgressAnimation extends Animation {

  private ProgressBar donutProgress;
  private float from;
  private float to;

  public ProgressAnimation(ProgressBar donutProgress, float from, float to) {
    super();
    this.donutProgress = donutProgress;
    this.from = from;
    this.to = to;
  }

  @Override protected void applyTransformation(float interpolatedTime, Transformation t) {
    super.applyTransformation(interpolatedTime, t);
    float value = from + (to - from) * interpolatedTime;
    donutProgress.setProgress((int) value);
  }
}

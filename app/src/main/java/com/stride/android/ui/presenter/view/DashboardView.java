package com.stride.android.ui.presenter.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.auto.factory.AutoFactory;
import com.stride.android.R;
import com.stride.android.ui.animation.ProgressAnimation;
import com.stride.android.util.StringUtil;

/**
 * Created by connormcfadden on 27/05/16.
 */
@AutoFactory public class DashboardView {

  @BindView(R.id.main_progress) ProgressBar mTodayProgress;
  @BindView(R.id.tv_current_steps) TextView mCurrentSteps;
  @BindView(R.id.tv_goal) TextView mTextGoal;
  @BindView(R.id.tv_calories) TextView mTextCalories;
  @BindView(R.id.tv_steps_yesterday) TextView mStepsYesterday;

  private final Context mContext;

  DashboardView(@NonNull View parent, Context context) {
    ButterKnife.bind(this, parent);
    mContext = context;
  }

  public void setProgress(int progress) {
    // maybe only onCreate? rather than doing all the time
    ProgressAnimation anim = new ProgressAnimation(mTodayProgress, 0, 4235);
    anim.setDuration(1000);
    mTodayProgress.startAnimation(anim);
    countUpSteps(4235);
  }

  public void setGoalText(int goal) {
    mTodayProgress.setMax(goal);
    mTextGoal.setText(mContext.getString(R.string.dashboard_goal, StringUtil.formatNumber(goal)));
  }

  public void setCaloriesText(int calories) {
    mTextCalories.setText(
        mContext.getString(R.string.dashboard_calories, StringUtil.formatNumber(calories)));
  }

  public void setStepsYesterdayText(int stepsYesterday) {
    mStepsYesterday.setText(mContext.getString(R.string.dashboard_steps_yesterday,
        StringUtil.formatNumber(stepsYesterday)));
  }

  private void countUpSteps(int steps) {
    ValueAnimator animator = new ValueAnimator();
    animator.setObjectValues(0, steps);
    animator.setDuration(1200);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      public void onAnimationUpdate(ValueAnimator animation) {
        mCurrentSteps.setText(mContext.getResources()
            .getString(R.string.dashboard_steps_today, "" + (int) animation.getAnimatedValue()));
      }
    });
    animator.start();
  }
}

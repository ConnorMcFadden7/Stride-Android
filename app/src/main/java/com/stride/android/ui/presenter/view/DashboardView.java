package com.stride.android.ui.presenter.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.auto.factory.AutoFactory;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.stride.android.R;
import com.stride.android.ui.animation.ProgressAnimation;
import com.stride.android.ui.widget.DailyGoalPanel;
import com.stride.android.util.SpannableUtil;

/**
 * Created by connormcfadden on 27/05/16.
 */
@AutoFactory public class DashboardView {

  @BindView(R.id.main_progress) ProgressBar mTodayProgress;
  @BindView(R.id.tv_current_steps) TextView mCurrentSteps;
  @BindView(R.id.tv_goal) TextView mTextGoal;
  @BindView(R.id.tv_calories) TextView mTextCalories;
  @BindView(R.id.tv_steps_yesterday) TextView mStepsYesterday;
  @BindView(R.id.daily_goal_panel) DailyGoalPanel mDailyGoalPanel;
  @BindView(R.id.sliding_layout) SlidingUpPanelLayout mSlidingLayout;

  private final Context mContext;

  DashboardView(@NonNull View parent, Context context) {
    ButterKnife.bind(this, parent);
    mContext = context;
  }

  @OnClick(R.id.tv_goal) public void onShowGoalPanel() {
    showPanel();
  }

  public void setProgress(int progress) {
    // maybe only onCreate? rather than doing all the time
    ProgressAnimation anim = new ProgressAnimation(mTodayProgress, 0, progress);
    anim.setDuration(1000);
    mTodayProgress.startAnimation(anim);
  }

  public void setGoalText(int goal) {
    mDailyGoalPanel.updateUi(goal);
    mTodayProgress.setMax(goal);
    mTextGoal.setText(
        SpannableUtil.formatTextAndBlueValue(mContext, R.string.dashboard_goal, goal));
  }

  public void setGoalToggleListener(DailyGoalPanel.ToggleListener toggleListener) {
    mDailyGoalPanel.setToggleListener(toggleListener);
  }

  public void setCaloriesText(int calories) {
    mTextCalories.setText(
        SpannableUtil.formatTextAndBlueValue(mContext, R.string.dashboard_calories, calories));
  }

  public void setStepsYesterdayText(int stepsYesterday) {
    mStepsYesterday.setText(
        SpannableUtil.formatTextAndBlueValue(mContext, R.string.dashboard_steps_yesterday,
            stepsYesterday));
  }

  public void showPanel() {
    mDailyGoalPanel.setVisibility(View.VISIBLE);
    mSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
  }

  public void hidePanel() {
    mDailyGoalPanel.setVisibility(View.INVISIBLE);
    mSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
  }

  public void setGoalComplete() {
    mCurrentSteps.setText(
        mContext.getResources().getString(R.string.dashboard_daily_goal_complete));
    mCurrentSteps.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
  }

  public void setStepsToday(int steps) {
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

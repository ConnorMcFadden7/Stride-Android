package com.stride.android.ui.presenter.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
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

  public interface Listener {
    Listener NULL = new Listener() {
      @Override public void onPanelShown() {
        // Empty
      }

      @Override public void onPanelHidden() {
        // Empty
      }
    };

    void onPanelShown();

    void onPanelHidden();
  }

  @BindView(R.id.main_progress) ProgressBar mTodayProgress;
  @BindView(R.id.tv_current_steps) TextView mCurrentSteps;
  @BindView(R.id.tv_goal) TextView mTextGoal;
  @BindView(R.id.tv_calories) TextView mTextCalories;
  @BindView(R.id.tv_steps_yesterday) TextView mStepsYesterday;
  @BindView(R.id.tv_steps_average) TextView mStepsAverage;
  @BindView(R.id.daily_goal_panel) DailyGoalPanel mDailyGoalPanel;
  @BindView(R.id.sliding_layout) SlidingUpPanelLayout mSlidingLayout;

  private final Context mContext;
  private Listener mListener = Listener.NULL;

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
    animateCounter(goal, mTextGoal, 1000, R.string.dashboard_goal);
  }

  public void setGoalToggleListener(DailyGoalPanel.ToggleListener toggleListener) {
    mDailyGoalPanel.setToggleListener(toggleListener);
  }

  public void setCaloriesText(int calories) {
    mTextCalories.setText(
        SpannableUtil.formatTextAndBlueValue(mContext, R.string.dashboard_calories, calories));
  }

  public void setStepsYesterdayText(int stepsYesterday) {
    animateCounter(stepsYesterday, mStepsYesterday, 1000, R.string.dashboard_steps_yesterday);
  }

  public void showPanel() {
    mListener.onPanelShown();
    mDailyGoalPanel.setVisibility(View.VISIBLE);
    mSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
  }

  public void hidePanel() {
    mListener.onPanelHidden();
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

    animateCounter(steps, mCurrentSteps, 1200, R.string.dashboard_steps_today);
  }

  public void setAverageSteps(int steps) {
    animateCounter(steps, mStepsAverage, 1000, R.string.dashboard_average_steps);
  }

  public void setListener(Listener listener) {
    mListener = listener;
  }

  private void animateCounter(int count, final TextView textView, int duration,
      @StringRes final int stringId) {
    ValueAnimator animator = new ValueAnimator();
    animator.setObjectValues(0, count);
    animator.setDuration(duration);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      public void onAnimationUpdate(ValueAnimator animation) {
        textView.setText(SpannableUtil.formatTextAndBlueValue(mContext, stringId,
            (int) animation.getAnimatedValue()));
      }
    });
    animator.start();
  }
}

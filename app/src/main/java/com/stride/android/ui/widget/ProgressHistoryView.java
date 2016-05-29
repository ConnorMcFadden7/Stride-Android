package com.stride.android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.stride.android.R;
import com.stride.android.ioc.ServiceLocator;

/**
 * Created by connormcfadden on 29/05/16.
 */
public class ProgressHistoryView extends FrameLayout {

  private static final int USER_DEFAULT_GOAL = 10000;

  @BindView(R.id.tv_date) TextView mDate;
  @BindView(R.id.tv_steps) TextView mSteps;
  @BindView(R.id.main_progress) ProgressBar mProgress;

  public ProgressHistoryView(Context context) {
    super(context);
    initLayout();
  }

  public ProgressHistoryView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initLayout();
  }

  public ProgressHistoryView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initLayout();
  }

  private void initLayout() {
    LayoutInflater inflater = LayoutInflater.from(getContext());
    View mainLayout = inflater.inflate(R.layout.layout_progress_history_view, this, true);
    ButterKnife.bind(this, mainLayout);
    setProgress();
  }

  private void setProgress() {
    int goal = ServiceLocator.get().getPreferences().getUserGoal();
    mProgress.setMax(goal > -1 ? goal : USER_DEFAULT_GOAL);
  }

  public void setSteps(int steps) {
    mSteps.setText(getResources().getString(R.string.progress_history_steps, steps));
    mProgress.setProgress(steps);
  }

  public void setDate(String date) {
    mDate.setText(date);
  }
}

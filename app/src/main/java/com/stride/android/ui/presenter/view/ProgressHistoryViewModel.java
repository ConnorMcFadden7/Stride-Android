package com.stride.android.ui.presenter.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import com.stride.android.R;
import com.stride.android.ui.activity.ActivityFacade;
import com.stride.android.ui.adapter.ProgressHistoryAdapter;

/**
 * Created by connormcfadden on 29/05/16.
 */
@AutoFactory public class ProgressHistoryViewModel {

  @BindView(R.id.previous_steps_grid) GridView previousStepsGrid;
  @BindView(R.id.tv_no_history) TextView mNoHistory;

  private final ActivityFacade activityFacade;

  ProgressHistoryViewModel(@NonNull View parent, @Provided ActivityFacade activityFacade) {
    ButterKnife.bind(this, parent);
    this.activityFacade = activityFacade;
  }

  public void setAchievementAdapter(ProgressHistoryAdapter progressHistoryAdapter) {
    previousStepsGrid.setAdapter(progressHistoryAdapter);
    progressHistoryAdapter.notifyDataSetChanged();
  }

  public void setNoHistory() {
    mNoHistory.setVisibility(View.VISIBLE);
  }
}

package com.stride.android.ui.presenter;

import com.stride.android.data.model.ProgressHistory;
import com.stride.android.ui.adapter.ProgressHistoryAdapter;
import com.stride.android.ui.presenter.view.PreviousStepsView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 29/05/16.
 */
public class PreviousStepsPresenter {

  private final ProgressHistoryAdapter mProgressHistoryAdapter;

  @Inject PreviousStepsPresenter(ProgressHistoryAdapter progressHistoryAdapter) {
    this.mProgressHistoryAdapter = progressHistoryAdapter;
  }

  public void present(PreviousStepsView view) {

    List<ProgressHistory> progressHistoryList = new ArrayList<>();

    ProgressHistory progressHistory = new ProgressHistory();
    progressHistory.date = "15/05/2016";
    progressHistory.steps = 545;

    ProgressHistory progressHistory1 = new ProgressHistory();
    progressHistory1.date = "16/05/2016";
    progressHistory1.steps = 2545;

    ProgressHistory progressHistory2 = new ProgressHistory();
    progressHistory2.date = "17/05/2016";
    progressHistory2.steps = 44;

    progressHistoryList.add(progressHistory);
    progressHistoryList.add(progressHistory1);
    progressHistoryList.add(progressHistory2);

    mProgressHistoryAdapter.setData(progressHistoryList);
    view.setAchievementAdapter(mProgressHistoryAdapter);
  }
}

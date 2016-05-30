package com.stride.android.ui.presenter;

import com.stride.android.repository.ProgressHistoryRepository;
import com.stride.android.ui.adapter.ProgressHistoryAdapter;
import com.stride.android.ui.presenter.model.ProgressHistoryModel;
import com.stride.android.ui.presenter.view.ProgressHistoryViewModel;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 29/05/16.
 */
public class ProgressHistoryPresenter {

  private final ProgressHistoryAdapter mProgressHistoryAdapter;
  private final ProgressHistoryRepository mProgressHistoryRepository;

  @Inject ProgressHistoryPresenter(ProgressHistoryAdapter progressHistoryAdapter,
      ProgressHistoryRepository progressHistoryRepository) {
    this.mProgressHistoryAdapter = progressHistoryAdapter;
    this.mProgressHistoryRepository = progressHistoryRepository;
  }

  public void present(ProgressHistoryViewModel view) {
    ProgressHistoryModel model = mProgressHistoryRepository.getProgressHistoryModel();

    if (model.getProgressHistory().isEmpty()) {
      view.setNoHistory();
    } else {
      mProgressHistoryAdapter.setData(model.getProgressHistory());
      view.setAchievementAdapter(mProgressHistoryAdapter);
    }
  }
}

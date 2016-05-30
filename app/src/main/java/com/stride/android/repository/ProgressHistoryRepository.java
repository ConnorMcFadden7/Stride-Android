package com.stride.android.repository;

import com.stride.android.data.provider.ProgressProvider;
import com.stride.android.ui.presenter.model.ProgressHistoryModel;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 30/05/16.
 */
public class ProgressHistoryRepository {

  private final ProgressHistoryModel.ProgressHistoryMapper progressHistoryMapper;
  private final ProgressProvider progressProvider;

  @Inject ProgressHistoryRepository(
      ProgressHistoryModel.ProgressHistoryMapper progressHistoryMapper,
      ProgressProvider progressProvider) {
    this.progressHistoryMapper = progressHistoryMapper;
    this.progressProvider = progressProvider;
  }

  public ProgressHistoryModel getProgressHistoryModel() {
    return progressHistoryMapper.map(progressProvider.getProgressHistory());
  }
}

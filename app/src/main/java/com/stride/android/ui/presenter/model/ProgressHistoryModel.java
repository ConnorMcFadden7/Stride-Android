package com.stride.android.ui.presenter.model;

import com.stride.android.data.model.ProgressHistory;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 29/05/16.
 */
public class ProgressHistoryModel {

  private List<ProgressHistory> progressHistory;

  public ProgressHistoryModel(List<ProgressHistory> progressHistory) {
    this.progressHistory = progressHistory;
  }

  public List<ProgressHistory> getProgressHistory() {
    return progressHistory;
  }

  public static class ProgressHistoryMapper {

    @Inject ProgressHistoryMapper() {
      //
    }

    public ProgressHistoryModel map(List<ProgressHistory> progressHistory) {
      return new ProgressHistoryModel(progressHistory);
    }
  }
}

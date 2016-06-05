package com.stride.android.data.provider;

import com.stride.android.data.model.ProgressHistory;
import com.stride.android.data.persistence.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Observer;

/**
 * Rather than communicating directly with the DatabaseHelper in different parts of the app,
 * we create this ProgressProvider which acts as the middle man.
 *
 * Created by connormcfadden on 30/05/16.
 */
public class ProgressProvider {

  private final DatabaseHelper databaseHelper;
  private List<ProgressHistory> newProgressHistories = new ArrayList<>();

  @Inject ProgressProvider(DatabaseHelper databaseHelper) {
    this.databaseHelper = databaseHelper;
  }

  public List<ProgressHistory> getProgressHistory() {
    databaseHelper.getProgressHistoryRx().subscribe(new Observer<List<ProgressHistory>>() {
      @Override public void onCompleted() {
        //
      }

      @Override public void onError(Throwable e) {
        //
      }

      @Override public void onNext(List<ProgressHistory> progressHistories) {
        newProgressHistories.addAll(progressHistories);
      }
    });

    return newProgressHistories;
  }
}

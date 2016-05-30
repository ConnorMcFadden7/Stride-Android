package com.stride.android.data.provider;

import com.stride.android.data.model.ProgressHistory;
import com.stride.android.data.persistence.DatabaseHelper;
import java.util.List;
import javax.inject.Inject;

/**
 * Rather than communicating directly with the DatabaseHelper in different parts of the app,
 * we create this ProgressProvider which acts as the middle man.
 *
 * Created by connormcfadden on 30/05/16.
 */
public class ProgressProvider {

  private final DatabaseHelper databaseHelper;

  @Inject ProgressProvider(DatabaseHelper databaseHelper) {
    this.databaseHelper = databaseHelper;
  }

  public List<ProgressHistory> getProgressHistory() {
    return databaseHelper.getProgressHistory();
  }
}

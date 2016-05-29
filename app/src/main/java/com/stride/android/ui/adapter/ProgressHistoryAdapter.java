package com.stride.android.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import com.stride.android.R;
import com.stride.android.data.model.ProgressHistory;
import com.stride.android.ui.activity.ActivityFacade;
import com.stride.android.ui.widget.ProgressHistoryView;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 29/05/16.
 */
@AutoFactory public class ProgressHistoryAdapter extends BaseAdapter {

  private final ActivityFacade mContext;
  private List<ProgressHistory> mProgressHistories;

  @Inject ProgressHistoryAdapter(@Provided ActivityFacade context) {
    mContext = context;
  }

  public void setData(List<ProgressHistory> progressHistories) {
    mProgressHistories = progressHistories;
  }

  @Override public int getCount() {
    return mProgressHistories != null ? mProgressHistories.size() : 0;
  }

  public ProgressHistory getItem(int position) {
    return mProgressHistories.get(position);
  }

  public long getItemId(int position) {
    return 0;
  }

  @Override public boolean areAllItemsEnabled() {
    return false;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    final ProgressHistory progressHistory = getItem(position);
    final ViewHolder holder;
    if (convertView == null) {
      convertView =
          mContext.getLayoutInflater().inflate(R.layout.item_progress_history, parent, false);

      holder = new ViewHolder();
      convertView.setTag(holder);

      holder.progressHistoryView = (ProgressHistoryView) convertView;
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    bindHolder(holder, progressHistory);
    return convertView;
  }

  private void bindHolder(final ViewHolder holder, final ProgressHistory progressHistory) {
    holder.progressHistoryView.setDate(progressHistory.date);
    holder.progressHistoryView.setSteps(progressHistory.steps);
  }

  static class ViewHolder {
    ProgressHistoryView progressHistoryView;
  }
}

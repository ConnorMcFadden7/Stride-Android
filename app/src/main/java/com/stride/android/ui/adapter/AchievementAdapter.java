package com.stride.android.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.stride.android.R;
import com.stride.android.data.model.Achievement;
import com.stride.android.ui.activity.ActivityFacade;
import com.stride.android.ui.widget.AchievementIconView;
import java.util.List;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class AchievementAdapter extends BaseAdapter {

  public static BaseAdapter create(ActivityFacade activityFacade, AbsListView absListView,
      List<Achievement> badges) {
    final ScaleInAnimationAdapter adapter =
        new ScaleInAnimationAdapter(new AchievementAdapter(activityFacade, badges), 0.4f);
    adapter.setAbsListView(absListView);
    return adapter;
  }

  private final ActivityFacade mContext;
  private final List<Achievement> mAchievements;

  AchievementAdapter(ActivityFacade context, List<Achievement> achievements) {
    mContext = context;
    mAchievements = achievements;
  }

  @Override public int getCount() {
    return mAchievements != null ? mAchievements.size() : 0;
  }

  public Achievement getItem(int position) {
    return mAchievements.get(position);
  }

  public long getItemId(int position) {
    return 0;
  }

  @Override public boolean areAllItemsEnabled() {
    return false;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    final Achievement achievement = getItem(position);
    final ViewHolder holder;
    if (convertView == null) {
      convertView = mContext.getLayoutInflater().inflate(R.layout.item_achievement, parent, false);

      holder = new ViewHolder();
      convertView.setTag(holder);

      holder.achievementIconView = (AchievementIconView) convertView;
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    bindHolder(holder, achievement);
    return convertView;
  }

  private void bindHolder(final ViewHolder holder, final Achievement achievement) {
    // If the icon of the badge is 0 it's not achieved
    if (achievement.progress == 0) {
      holder.achievementIconView.setImage(achievement.empty_icon);
    } else {
      holder.achievementIconView.setAchieved();
      holder.achievementIconView.setProgress(achievement.progress);
      holder.achievementIconView.setImage(achievement.icon);
      holder.achievementIconView.getBadge().setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (mContext.canCommitFragmentTransaction()) {
            //BadgeDialogFragment.newInstance(badge, mTrackingLabel, false)
            //    .show(mContext.getFragmentManager(), BadgeDialogFragment.BADGE_DIALOG);
          }
        }
      });
    }
  }

  static class ViewHolder {
    AchievementIconView achievementIconView;
  }
}

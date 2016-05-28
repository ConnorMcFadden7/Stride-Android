package com.stride.android.ui.presenter.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import com.stride.android.R;
import com.stride.android.data.model.Achievement;
import com.stride.android.ui.activity.ActivityFacade;
import com.stride.android.ui.adapter.AchievementAdapter;
import java.util.List;

/**
 * Created by connormcfadden on 28/05/16.
 */
@AutoFactory public class AchievementView {

  @BindView(R.id.achievements_grid) GridView achievementsGrid;
  @BindView(R.id.tv_achievement_count) TextView achievementCount;

  private final ActivityFacade activityFacade;
  private BaseAdapter mAchievementAdapter;

  AchievementView(@NonNull View parent, @Provided ActivityFacade activityFacade) {
    ButterKnife.bind(this, parent);
    this.activityFacade = activityFacade;
  }

  public void setAchievementAdapter(List<Achievement> achievements) {
    mAchievementAdapter = AchievementAdapter.create(activityFacade, achievementsGrid, achievements);
    achievementsGrid.setAdapter(mAchievementAdapter);
    mAchievementAdapter.notifyDataSetChanged();
  }

  public void setAchievementCount(String achievementCount) {
    this.achievementCount.setText(achievementCount);
  }
}

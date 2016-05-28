package com.stride.android.ui.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.stride.android.R;
import com.stride.android.data.model.Achievement;
import com.stride.android.ioc.FragmentComponent;
import com.stride.android.ui.dialog.BaseDialogFragment;
import com.stride.android.ui.presenter.AchievementDialogPresenter;
import com.stride.android.ui.presenter.view.AchievementDialogViewFactory;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class AchievementDialogFragment extends BaseDialogFragment {

  public static final String ACHIEVEMENT_DIALOG = "achievement_dialog_tag";
  private static final String ACHIEVEMENT_ARGUMENT = "extra_achievement";

  @BindView(R.id.non_premium_popup) View mParent;

  @Inject AchievementDialogPresenter achievementDialogPresenter;
  @Inject AchievementDialogViewFactory achievementDialogViewFactory;
  private Achievement mAchievement;

  public static AchievementDialogFragment newInstance(Achievement achievement) {
    AchievementDialogFragment achievementDialogFragment = new AchievementDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(ACHIEVEMENT_ARGUMENT, achievement);
    achievementDialogFragment.setArguments(bundle);
    return achievementDialogFragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.mAchievement = getArguments().getParcelable(ACHIEVEMENT_ARGUMENT);
  }

  @Override protected void injectFragment(FragmentComponent fragmentComponent) {
    fragmentComponent.inject(this);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.layout_achievement_dialog, container);
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presentAchievementDialog();
  }

  private void presentAchievementDialog() {
    achievementDialogPresenter.present(achievementDialogViewFactory.create(mParent), mAchievement);
  }
}

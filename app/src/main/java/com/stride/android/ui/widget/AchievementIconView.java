package com.stride.android.ui.widget;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.stride.android.R;

/**
 * A custom View to create an achievement.
 *
 * Created by connormcfadden on 28/05/16.
 */
public class AchievementIconView extends FrameLayout {

  // Views
  @BindView(R.id.achievement_image) ImageView achievementImage;
  @BindView(R.id.achievement_progress) ProgressBar achievementProgress;

  private Context mContext;

  public AchievementIconView(Context context) {
    super(context);
    initialiseLayout();
    mContext = context;
  }

  public AchievementIconView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initialiseLayout();
    mContext = context;
  }

  public AchievementIconView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initialiseLayout();
    mContext = context;
  }

  private void initialiseLayout() {
    LayoutInflater inflater = LayoutInflater.from(getContext());
    View mainLayout = inflater.inflate(R.layout.layout_achievement_view, this, true);
    ButterKnife.bind(this, mainLayout);
  }

  public void setImage(@DrawableRes int imageResourceId) {
    achievementImage.setImageResource(imageResourceId);
  }

  public ImageView getBadge() {
    return achievementImage;
  }

  public void setAchieved() {
    achievementImage.setVisibility(View.VISIBLE);
    achievementProgress.setVisibility(View.VISIBLE);
  }

  public void setProgress(int progress) {
    ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(mContext,
        R.animator.achievement_progress_bar);
    objectAnimator.setTarget(achievementProgress);
    objectAnimator.setIntValues(progress);
    objectAnimator.start();
  }

  public void setPlaceHolder() {
    achievementImage.setImageResource(R.drawable.ic_achievement_placeholder);
  }
}



package com.stride.android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.stride.android.R;
import com.stride.android.ioc.ServiceLocator;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class DailyGoalPanel extends LinearLayout implements View.OnClickListener {

  public interface ToggleListener {
    ToggleListener NULL = new ToggleListener() {
      @Override public void onSetGoal(int goal) {
        // Empty
      }

      @Override public void onCurrentGoalSelected() {
        // Empty
      }

      @Override public void onCustomGoal() {
        // Empty
      }
    };

    void onSetGoal(int goal);

    void onCurrentGoalSelected();

    void onCustomGoal();
  }

  enum GoalOption {

    ONE(10000), TWO(15000), THREE(25000);

    private final int mPoints;

    GoalOption(int points) {
      this.mPoints = points;
    }

    public int getPoints() {
      return mPoints;
    }
  }

  @BindView(R.id.tv_ten_thousand) ImageView mFirstGoal;
  @BindView(R.id.tv_fifteen_thousand) ImageView mSecondGoal;
  @BindView(R.id.tv_twenty_five_thousand) ImageView mThirdGoal;
  @BindView(R.id.ed_other_goal) EditText mCustomGoal;
  @BindView(R.id.iv_set_custom_goal) ImageView mSetCustomGoal;

  private int mGoal = -1;
  private ToggleListener mToggleListener = ToggleListener.NULL;

  public DailyGoalPanel(Context context) {
    super(context);
    initialiseLayout();
  }

  public DailyGoalPanel(Context context, AttributeSet attrs) {
    super(context, attrs);
    initialiseLayout();
  }

  public DailyGoalPanel(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initialiseLayout();
  }

  private void initialiseLayout() {
    LayoutInflater inflater =
        (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View mainLayout = inflater.inflate(R.layout.layout_daily_goal_panel, this, true);
    ButterKnife.bind(this, mainLayout);
    setupClickListeners();
    hideOtherOptionIfNotPremium();
  }

  private void setupClickListeners() {
    mFirstGoal.setOnClickListener(this);
    mSecondGoal.setOnClickListener(this);
    mThirdGoal.setOnClickListener(this);
    mSetCustomGoal.setOnClickListener(this);
  }

  private void handleToggle(View view, int goal) {
    if (!view.isSelected()) {
      mToggleListener.onSetGoal(goal);
      view.setSelected(true);
    } else {
      mToggleListener.onCurrentGoalSelected();
    }
  }

  private void enableUsersChoice() {
    if (mGoal == GoalOption.ONE.getPoints()) {
      select(mFirstGoal, mSecondGoal, mThirdGoal);
    } else if (mGoal == GoalOption.TWO.getPoints()) {
      select(mSecondGoal, mFirstGoal, mThirdGoal);
    } else if (mGoal == GoalOption.THREE.getPoints()) {
      select(mThirdGoal, mFirstGoal, mSecondGoal);
    }
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_ten_thousand:
        onClickedOption(GoalOption.ONE.getPoints(), mFirstGoal, mSecondGoal, mThirdGoal);
        break;
      case R.id.tv_fifteen_thousand:
        onClickedOption(GoalOption.TWO.getPoints(), mSecondGoal, mFirstGoal, mThirdGoal);
        break;
      case R.id.tv_twenty_five_thousand:
        onClickedOption(GoalOption.THREE.getPoints(), mThirdGoal, mFirstGoal, mSecondGoal);
        break;
      case R.id.iv_set_custom_goal:
        saveCustomGoal();
        break;
    }
  }

  private void onClickedOption(int option, ImageView selectedView, ImageView unselectedViewOne,
      ImageView unselectedViewTwo) {
    if (mGoal != option) {
      mGoal = option;
      unselectedViewOne.setSelected(false);
      unselectedViewTwo.setSelected(false);
      handleToggle(selectedView, mGoal);
    } else {
      mToggleListener.onCurrentGoalSelected();
    }
  }

  private void saveCustomGoal() {
    if (!mCustomGoal.getText().toString().isEmpty()) {
      mFirstGoal.setSelected(false);
      mSecondGoal.setSelected(false);
      mThirdGoal.setSelected(false);
      mToggleListener.onSetGoal(Integer.parseInt(mCustomGoal.getText().toString()));
      mToggleListener.onCustomGoal();
    }
  }

  private void select(ImageView selectedView, ImageView unselectedOne, ImageView unselectedTwo) {
    selectedView.setSelected(true);
    unselectedOne.setSelected(false);
    unselectedTwo.setSelected(false);
  }

  private void hideOtherOptionIfNotPremium() {
    boolean hasUpgraded = ServiceLocator.get().getPreferences().hasUpgraded();
    mCustomGoal.setVisibility(hasUpgraded ? View.VISIBLE : View.GONE);
    mSetCustomGoal.setVisibility(hasUpgraded ? View.VISIBLE : View.GONE);
  }

  public void updateUi(int newGoal) {
    if (mGoal != newGoal) {
      mGoal = newGoal;
      enableUsersChoice();
    }
  }

  public void setToggleListener(ToggleListener toggleListener) {
    mToggleListener = toggleListener != null ? toggleListener : ToggleListener.NULL;
  }
}

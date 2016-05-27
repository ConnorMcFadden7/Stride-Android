package com.stride.android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.stride.android.R;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class DailyGoalPanel extends LinearLayout implements View.OnClickListener {

  public static final int DELAY_HIDE_PANEL = 600;

  public interface ToggleListener {
    ToggleListener NULL = new ToggleListener() {
      @Override public void onSetGoal(int goal) {
        // Empty
      }

      @Override public void onCurrentGoalSelected() {
        // Empty
      }
    };

    void onSetGoal(int goal);

    void onCurrentGoalSelected();
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

  @BindView(R.id.tv_ten_thousand) TextView mFirstGoal;
  @BindView(R.id.tv_fifteen_thousand) TextView mSecondGoal;
  @BindView(R.id.tv_twenty_five_thousand) TextView mThirdGoal;

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
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (!isInEditMode()) {
//      ServiceLocator.get().getBus().register(this);
    }
  }

  @Override protected void onDetachedFromWindow() {
    if (!isInEditMode()) {
    //  ServiceLocator.get().getBus().unregister(this);
    }
    super.onDetachedFromWindow();
  }

  private void setupClickListeners() {
    mFirstGoal.setOnClickListener(this);
    mSecondGoal.setOnClickListener(this);
    mThirdGoal.setOnClickListener(this);
  }

  private void handleToggle(int goal) {
    mToggleListener.onSetGoal(goal);
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
        onClickedOption(GoalOption.ONE.getPoints());
        break;
      case R.id.tv_fifteen_thousand:
        onClickedOption(GoalOption.TWO.getPoints());
        break;
      case R.id.tv_twenty_five_thousand:
        onClickedOption(GoalOption.THREE.getPoints());
        break;
    }
  }

  private void onClickedOption(int option) {
    //
  }

  private void select(TextView selectedView, TextView unselectedOne, TextView unselectedTwo) {
    //selectedView.setOptionSelected();
    //unselectedOne.setOptionUnselected();
    //unselectedTwo.setOptionUnselected();
  }

  //@Subscribe public void enableUsersChoice(DailyGoal.RetrieveCourseGoalEvent event) {
  ///updateUi(event.getGoal().getGoal());
  //}

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

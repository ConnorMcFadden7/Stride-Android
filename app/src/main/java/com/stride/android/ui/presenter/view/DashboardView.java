package com.stride.android.ui.presenter.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.auto.factory.AutoFactory;
import com.stride.android.R;
import com.stride.android.util.StringUtil;

/**
 * Created by connormcfadden on 27/05/16.
 */
@AutoFactory public class DashboardView {

  @BindView(R.id.main_progress) DonutProgress mTodayProgress;

  private final Context mContext;

  DashboardView(@NonNull View parent, Context context) {
    ButterKnife.bind(this, parent);
    mContext = context;

    // The library does not space out the suffix text
    mTodayProgress.setSuffixText(
        StringUtil.WHITESPACE + context.getResources().getString(R.string.dashboard_steps_today));
  }
}

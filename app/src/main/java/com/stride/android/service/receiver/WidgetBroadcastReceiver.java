package com.stride.android.service.receiver;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.stride.android.R;
import com.stride.android.data.provider.StepsProvider;
import com.stride.android.ioc.IocUtil;
import com.stride.android.ui.activity.MainActivity;
import com.stride.android.util.TimeUtils;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class WidgetBroadcastReceiver extends AppWidgetProvider {

  @Inject StepsProvider stepsProvider;

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    IocUtil.getApplicationComponent(context).inject(this);
    final int N = appWidgetIds.length;

    for (int i = 0; i < N; i++) {
      int appWidgetId = appWidgetIds[i];
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_stride_widget);

      Intent intent = new Intent(context, MainActivity.class);
      PendingIntent pendingIntent = PendingIntent.getActivity(context, 11, intent, 0);

      views.setTextViewText(R.id.tv_current_steps, context.getResources()
          .getString(R.string.widget_current_steps,
              String.valueOf(stepsProvider.getSteps(TimeUtils.getToday()))));
      views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

      appWidgetManager.updateAppWidget(appWidgetId, views);
    }
  }
}

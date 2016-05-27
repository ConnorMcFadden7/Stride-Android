package com.stride.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import com.stride.android.R;
import com.stride.android.data.persistence.DatabaseHelper;
import com.stride.android.ioc.ActivityComponent;
import com.stride.android.service.SensorListener;
import com.stride.android.ui.presenter.MainPresenter;
import com.stride.android.ui.presenter.view.MainViewFactory;
import com.stride.android.util.TimeUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;

public class MainActivity extends BaseActivity {

  @BindView(R.id.container_main) View mParent;

  // maybe a better helper method like a Provider to get the steps
  @Inject DatabaseHelper databaseHelper;
  @Inject MainPresenter mainPresenter;
  @Inject MainViewFactory mainViewFactory;
  @Inject ActivityFacade activityFacade;

  @Override public boolean isHomeAsUpEnabled() {
    return false;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);



    Calendar yesterday = Calendar.getInstance();
    yesterday.setTimeInMillis(1464460377503L);
    yesterday.add(Calendar.DAY_OF_YEAR, -1);



   // Log.e("MainActivity", "currentDate: " + date);


    //    Log.e("MainActivity", "isToday: " + TimeUtils.isToday(1464460377503L));



    startService(new Intent(this, SensorListener.class));
    presentMainScreen();


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String date = sdf.format(new Date());

    Log.e("MainActivity", "today: " + TimeUtils.getToday());
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override protected void injectActivity(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }

  private void presentMainScreen() {
    mainPresenter.present(mainViewFactory.create(mParent, activityFacade.wrap(this)));
  }
}

package com.stride.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.stride.android.util.payment.PaymentSystem;
import com.stride.android.util.payment.Sku;
import javax.inject.Inject;

public class MainActivity extends BaseActivity {

  @BindView(R.id.container_main) View mParent;

  // maybe a better helper method like a Provider to get the steps
  @Inject DatabaseHelper databaseHelper;
  @Inject MainPresenter mainPresenter;
  @Inject MainViewFactory mainViewFactory;
  @Inject ActivityFacade activityFacade;
  @Inject PaymentSystem paymentSystem;


  @Override public boolean isHomeAsUpEnabled() {
    return false;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    startService(new Intent(this, SensorListener.class));
    presentMainScreen();
    paymentSystem.checkPurchaseStatus(new PaymentSystem.PurchaseHistory() {
      @Override public void onResponse() {
        //hide ads
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_upgrade_to_pro:
        paymentSystem.startPayment(new Sku.Key(Sku.Period.ONE_OFF));
        break;
      case R.id.action_settings:
        //
        break;
      default:
        return super.onOptionsItemSelected(item);
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

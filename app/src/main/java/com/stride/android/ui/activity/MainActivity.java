package com.stride.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.stride.android.R;
import com.stride.android.data.local.PreferencesHelper;
import com.stride.android.data.persistence.DatabaseHelper;
import com.stride.android.ioc.ActivityComponent;
import com.stride.android.service.SensorListener;
import com.stride.android.ui.presenter.MainPresenter;
import com.stride.android.ui.presenter.view.MainViewFactory;
import com.stride.android.util.payment.PaymentSystem;
import com.stride.android.util.payment.Sku;
import javax.inject.Inject;

public class MainActivity extends BaseActivity {

  private static final String TEST_DEVICE_NEXUS_5 = "5E06724163EDE1D63AA54BFC8581E828";
  private static final int DELAY_BEFORE_DISPLAYING_INT_ADS = 120000;

  @BindView(R.id.container_main) View mParent;
  @BindView(R.id.ad_view) AdView mAdView;

  // maybe a better helper method like a Provider to get the steps
  @Inject DatabaseHelper databaseHelper;
  @Inject MainPresenter mainPresenter;
  @Inject MainViewFactory mainViewFactory;
  @Inject ActivityFacade activityFacade;
  @Inject PaymentSystem paymentSystem;
  @Inject PreferencesHelper preferencesHelper;

  private InterstitialAd mInterstitialAd;
  private final Handler mAdHandler = new Handler();
  private boolean mIsInterstitialAdShowing = false;

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

    //// TODO: 28/05/16
    initAdMob();
    //initInterstitialAds();

    paymentSystem.checkPurchaseStatus(new PaymentSystem.PurchaseHistory() {
      @Override public void onResponse() {
        if (!preferencesHelper.hasUpgraded()) {

          // initAdMob();

          mAdHandler.postDelayed(new Runnable() {
            public void run() {
              //  initInterstitialAds();
              mAdHandler.postDelayed(this, DELAY_BEFORE_DISPLAYING_INT_ADS);
            }
          }, DELAY_BEFORE_DISPLAYING_INT_ADS);
        }
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

  private void initAdMob() {
    AdRequest adRequest = new AdRequest.Builder().addTestDevice(TEST_DEVICE_NEXUS_5).build();

    mAdView.setVisibility(View.VISIBLE);
    mAdView.loadAd(adRequest);
  }

  private void initInterstitialAds() {
    mInterstitialAd = new InterstitialAd(this);
    mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
    AdRequest adRequest = new AdRequest.Builder().addTestDevice(TEST_DEVICE_NEXUS_5).build();
    mInterstitialAd.loadAd(adRequest);
    mInterstitialAd.setAdListener(new AdListener() {
      @Override public void onAdLoaded() {
        super.onAdLoaded();
        if (!mIsInterstitialAdShowing) {
          mIsInterstitialAdShowing = true;
          mInterstitialAd.show();
        }
      }

      @Override public void onAdClosed() {
        mIsInterstitialAdShowing = false;
      }
    });
  }
}

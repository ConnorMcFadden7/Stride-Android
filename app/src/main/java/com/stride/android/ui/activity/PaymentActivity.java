package com.stride.android.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.stride.android.R;
import com.stride.android.data.local.PreferencesHelper;
import com.stride.android.util.payment.PaymentSystem;
import com.stride.android.util.payment.Sku;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class PaymentActivity extends BaseActivity implements BillingProcessor.IBillingHandler {

  @Inject PreferencesHelper preferencesHelper;
  private String mProductId;
  private BillingProcessor mBillingProcessor;

  @Override public boolean isHomeAsUpEnabled() {
    return false;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_payment);
    Sku sku = getIntent().getParcelableExtra(PaymentSystem.SKU_EXTRA);

    if (sku == null) {
      finish();
    } else {
      mProductId = sku.name();
      mBillingProcessor = new BillingProcessor(this, null, this);
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mBillingProcessor.handleActivityResult(requestCode, resultCode, data);
  }

  @Override public void onProductPurchased(String productId, TransactionDetails details) {
    ProgressDialog dialog = createSimpleProgressDialog(this,
        getResources().getString(R.string.main_screen_one_off_payment));
    dialog.setCanceledOnTouchOutside(false);
    preferencesHelper.setHasUpgraded(true);
    setResult(PaymentSystem.SUCCESS_RESULT_CODE);
    finish();
  }

  @Override public void onPurchaseHistoryRestored() {

  }

  @Override public void onBillingError(int errorCode, Throwable error) {
    finish();
  }

  @Override public void onBillingInitialized() {
    mBillingProcessor.purchase(this, mProductId);
  }

  @Override public void onDestroy() {
    if (mBillingProcessor != null) {
      mBillingProcessor.release();
    }

    super.onDestroy();
  }

  public static ProgressDialog createSimpleProgressDialog(Activity activity, String msg) {
    ProgressDialog dialog = new ProgressDialog(activity);
    dialog.setMessage(msg);
    return dialog;
  }
}

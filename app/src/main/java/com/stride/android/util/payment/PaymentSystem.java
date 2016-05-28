package com.stride.android.util.payment;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.stride.android.data.local.PreferencesHelper;
import com.stride.android.ui.activity.PaymentActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Created by connormcfadden on 28/05/16.
 */
public class PaymentSystem {

  public interface Listener<T> {
    void onCallback(T item);
  }

  public interface PurchaseHistory {

    void onResponse();
  }

  public final class ProductId {
    public static final String PRODUCT_PREMIUM_UPGRADE = "premium_upgrade";
  }

  public static final String SKU_EXTRA = "sku_extra";

  public static final int SUCCESS_RESULT_CODE = 9;
  public static final int REQUEST_CODE = 1010;

  private static final ArrayList<String> SKU_LIST =
      new ArrayList<>(Arrays.asList(ProductId.PRODUCT_PREMIUM_UPGRADE));

  private static final Map<Sku.Key, Sku> SKU_MAP = new HashMap<>();

  @Inject PreferencesHelper preferencesHelper;
  private BillingProcessor billingProcessor;
  private final Activity activity;

  @Inject PaymentSystem(@NonNull Activity activity) {
    this.activity = activity;
  }

  public void startPayment(final Sku.Key sku) {
    if (SKU_MAP.isEmpty()) {
      getSupportedSkuList(new Listener<List<Sku>>() {
        @Override public void onCallback(List<Sku> item) {
          startActivity(PaymentActivity.class, SKU_MAP.get(sku));
        }
      });
    } else {
      startActivity(PaymentActivity.class, SKU_MAP.get(sku));
    }
  }

  public void getSupportedSkuList(final Listener<List<Sku>> skuListener) {
    if (!SKU_MAP.isEmpty()) {
      skuListener.onCallback(new ArrayList<>(SKU_MAP.values()));
    } else {
      billingProcessor =
          new BillingProcessor(getActivity(), null, new BillingProcessor.IBillingHandler() {
            @Override public void onProductPurchased(String productId, TransactionDetails details) {

            }

            @Override public void onPurchaseHistoryRestored() {

            }

            @Override public void onBillingError(int errorCode, Throwable error) {
              skuListener.onCallback(new ArrayList<Sku>());
            }

            @Override public void onBillingInitialized() {
              final List<SkuDetails> detailsList = new ArrayList<>();
              List<SkuDetails> purchaseListing =
                  billingProcessor.getPurchaseListingDetails(SKU_LIST);
              if (purchaseListing != null) {
                detailsList.addAll(purchaseListing);
              }
              skuListener.onCallback(processSkuDetails(detailsList));
            }
          });
    }
  }

  private List<Sku> processSkuDetails(List<SkuDetails> listingDetails) {
    List<Sku> skus = new ArrayList<>(listingDetails.size());
    for (SkuDetails skuDetail : listingDetails) {
      if (skuDetail.productId.equals(ProductId.PRODUCT_PREMIUM_UPGRADE)) {
        skus.add(
            new Sku(Sku.Period.ONE_OFF, ProductId.PRODUCT_PREMIUM_UPGRADE, skuDetail.priceText));
      }

      for (Sku sku : skus) {
        SKU_MAP.put(sku.key(), sku);
      }
    }

    return skus;
  }

  public void checkPurchaseStatus(final PurchaseHistory purchaseHistory) {
    billingProcessor =
        new BillingProcessor(getActivity(), null, new BillingProcessor.IBillingHandler() {
          @Override public void onProductPurchased(String productId, TransactionDetails details) {

          }

          @Override public void onPurchaseHistoryRestored() {
            purchaseHistory.onResponse();
          }

          @Override public void onBillingError(int errorCode, Throwable error) {
            purchaseHistory.onResponse();
            billingProcessor.release();
          }

          @Override public void onBillingInitialized() {
            boolean hasOwnedPurchases = billingProcessor.loadOwnedPurchasesFromGoogle();

            if (hasOwnedPurchases) {
              preferencesHelper.setHasUpgraded(true);
            } else {
              preferencesHelper.setHasUpgraded(false);
            }

            purchaseHistory.onResponse();
            billingProcessor.release();
          }
        });
  }

  protected void startActivity(Intent intent) {
    activity.startActivityForResult(intent, REQUEST_CODE);
  }

  protected void startActivity(Class<?> clazz, Sku sku) {
    startActivity(new Intent(activity, clazz).putExtra(SKU_EXTRA, sku));
  }

  protected String getString(@StringRes int stringRes) {
    return activity.getString(stringRes);
  }

  protected Activity getActivity() {
    return activity;
  }
}

package com.danosoftware.galaxyforce.billing;

import androidx.annotation.NonNull;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import java.util.List;

/**
 * Listener to the updates that happen when purchases list was updated or consumption of the item
 * was finished
 */
public interface BillingUpdatesListener {

  void onBillingClientSetupFinished(BillingManager billingManager);

  void onConsumeFinished(String token, @NonNull BillingResult billingResult);

  void onPurchasesUpdated(List<Purchase> purchases);
}

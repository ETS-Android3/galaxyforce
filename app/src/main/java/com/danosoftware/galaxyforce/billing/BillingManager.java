package com.danosoftware.galaxyforce.billing;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsResponseListener;
import java.util.List;

public interface BillingManager {

  void queryPurchases();

  void initiatePurchaseFlow(final SkuDetails skuDetails);

  void querySkuDetailsAsync(
      @BillingClient.SkuType final String itemType,
      final List<String> skuList,
      final SkuDetailsResponseListener listener);

  void consumeAsync(final String purchaseToken);

  boolean isConnected();

  void destroy();
}

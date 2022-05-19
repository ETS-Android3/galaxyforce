package com.danosoftware.galaxyforce.billing;

import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.QueryProductDetailsParams.Product;
import java.util.List;

public interface BillingManager {

  void queryPurchases();

  void initiatePurchaseFlow(final ProductDetails productDetails);

  void queryProductDetailsAsync(
      final List<Product> products,
      final ProductDetailsResponseListener listener);

  void consumeAsync(final String purchaseToken);

  boolean isConnected();

  void destroy();
}

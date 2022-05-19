package com.danosoftware.galaxyforce.billing;

import static com.danosoftware.galaxyforce.billing.KeyUtilities.getPublicKey;

import android.app.Activity;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClient.ProductType;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryProductDetailsParams.Product;
import com.android.billingclient.api.QueryPurchasesParams;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles all the interactions with Play Store (via Billing library), maintains connection to it
 * through BillingClient and caches temporary states/data if needed
 */
public class BillingManagerImpl implements PurchasesUpdatedListener, BillingManager {

  private enum BillingClientState {
    PENDING, CONNECTED, DISCONNECTED;
  }

  private static final String TAG = "BillingManager";
  /*
   * BASE_64_ENCODED_PUBLIC_KEY is the APPLICATION'S PUBLIC KEY
   * (from the Google Play developer console). This is not a
   * developer public key, it's the *app-specific* public key.
   */
  private static final String BASE_64_ENCODED_PUBLIC_KEY = getPublicKey();
  private final BillingUpdatesListener mBillingUpdatesListener;
  private final Activity mActivity;
  private final List<Purchase> mPurchases = new ArrayList<>();
  private BillingClient mBillingClient;
  private boolean mIsServiceConnected;
  private Set<String> mTokensToBeConsumed;
  private BillingClientState clientState;

  public BillingManagerImpl(
      final Activity activity,
      final BillingUpdatesListener updatesListener) {
    Log.d(TAG, "Creating Billing client.");
    this.mActivity = activity;
    this.mBillingUpdatesListener = updatesListener;
    this.clientState = BillingClientState.PENDING;
    this.mBillingClient = BillingClient
        .newBuilder(mActivity)
        .setListener(this)
        .enablePendingPurchases()
        .build();
    Log.d(TAG, "Connecting to Billing Client.");
    final BillingManager billingManager = this;
    mBillingClient.startConnection(new BillingClientStateListener() {
      @Override
      public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
          clientState = BillingClientState.CONNECTED;
          mBillingUpdatesListener.onBillingClientSetupFinished(billingManager);
          Log.d(TAG, "Billing Setup successful. Querying inventory.");
          queryPurchases();
        }
      }

      @Override
      public void onBillingServiceDisconnected() {
        clientState = BillingClientState.DISCONNECTED;
        Log.d(TAG, "Billing Service Disconnected.");
      }
    });
  }

//  private void clientSetUp() {
//    mBillingClient.startConnection(new BillingClientStateListener() {
//      @Override
//      public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
//        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//          clientState = BillingClientState.CONNECTED;
//          //mBillingUpdatesListener.onBillingClientSetupFinished(billingManager);
//          Log.d(TAG, "Billing Setup successful. Querying inventory.");
//          queryPurchases();
//        }
//      }
//
//      @Override
//      public void onBillingServiceDisconnected() {
//        clientState = BillingClientState.DISCONNECTED;
//        Log.d(TAG, "Billing Service Disconnected.");
//      }
//    });
//  }
//
//  private void checkClientState() {
//    if (clientState == BillingClientState.DISCONNECTED) {
//      Log.d(TAG, "Client is unavailable. Will attempt to reconnect async.");
//      return;
//    }
//  }

  /**
   * Handle a callback that purchases were updated from the Billing library
   */
  @Override
  public void onPurchasesUpdated(
      @NonNull BillingResult billingResult,
      @Nullable List<Purchase> purchases) {
    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
      for (Purchase purchase : purchases) {
        handlePurchase(purchase);
      }
      mBillingUpdatesListener.onPurchasesUpdated(mPurchases);
    } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
      Log.i(TAG, "onPurchasesUpdated() - user cancelled the purchase flow - skipping");
    } else {
      Log.w(TAG, "onPurchasesUpdated() got unknown resultCode: " + billingResult.getResponseCode());
    }
  }

  /**
   * Start a purchase
   */
  @Override
  public void initiatePurchaseFlow(final ProductDetails productDetails) {
    List<ProductDetailsParams> productDetailsParams = new ArrayList<>();
    productDetailsParams.add(
        ProductDetailsParams.newBuilder()
            // fetched via queryProductDetailsAsync
            .setProductDetails(productDetails)

            // to get an offer token, call ProductDetails.getOfferDetails()
            // for a list of offers that are available to the user
            //.setOfferToken(selectedOfferToken)
            .build());

    BillingFlowParams billingFlowParams =
        BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParams)
            .build();

    //Runnable purchaseFlowRequest = () -> {
    Log.d(TAG, "Launching in-app purchase flow for: " + productDetails.getProductId());
//      BillingFlowParams purchaseParams = BillingFlowParams
//          .newBuilder()
//          .setSkuDetails(skuDetails)
//          .build();
    mBillingClient.launchBillingFlow(mActivity, billingFlowParams);
    //};
    //executeRetryableBillingRequest(purchaseFlowRequest);
  }

  /**
   * Clear the resources
   */
  @Override
  public void destroy() {
    Log.d(TAG, "Destroying the Billing Manager.");
    if (mBillingClient != null && mBillingClient.isReady()) {
      mBillingClient.endConnection();
      mBillingClient = null;
    }
  }

  @Override
  public void queryProductDetailsAsync(
      //@SkuType final String itemType,
      final List<Product> products,
      final ProductDetailsResponseListener listener) {

    QueryProductDetailsParams queryProductDetailsParams =
        QueryProductDetailsParams.newBuilder()
            .setProductList(products)
            .build();

    mBillingClient.queryProductDetailsAsync(
        queryProductDetailsParams,
        new ProductDetailsResponseListener() {
          public void onProductDetailsResponse(BillingResult billingResult,
              List<ProductDetails> list) {
            listener.onProductDetailsResponse(billingResult, list);

            // check BillingResult
            // process returned ProductDetails list
          }
        }
    );

    // Creating a runnable from the request to use it inside our connection retry policy below
//    Runnable queryRequest = () -> {
//      // Query the purchase async
//      SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
//      params.setSkusList(skuList).setType(itemType);
//      mBillingClient.querySkuDetailsAsync(params.build(),
//          new SkuDetailsResponseListener() {
//            @Override
//            public void onSkuDetailsResponse(
//                @NonNull BillingResult billingResult,
//                @Nullable List<SkuDetails> skuDetailsList) {
//              listener.onSkuDetailsResponse(billingResult, skuDetailsList);
//            }
//          });
//    };
//    executeRetryableBillingRequest(queryRequest);
  }

//  @Override
//  public void querySkuDetailsAsync(
//      @SkuType final String itemType,
//      final List<String> skuList,
//      final SkuDetailsResponseListener listener) {
//    // Creating a runnable from the request to use it inside our connection retry policy below
//    Runnable queryRequest = () -> {
//      // Query the purchase async
//      SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
//      params.setSkusList(skuList).setType(itemType);
//      mBillingClient.querySkuDetailsAsync(params.build(),
//          new SkuDetailsResponseListener() {
//            @Override
//            public void onSkuDetailsResponse(
//                @NonNull BillingResult billingResult,
//                @Nullable List<SkuDetails> skuDetailsList) {
//              listener.onSkuDetailsResponse(billingResult, skuDetailsList);
//            }
//          });
//    };
//    executeRetryableBillingRequest(queryRequest);
//  }

  @Override
  public void consumeAsync(final String purchaseToken) {
    // If we've already scheduled to consume this token - no action is needed (this could happen
    // if you received the token when querying purchases inside onReceive() and later from
    // onActivityResult()
    if (mTokensToBeConsumed == null) {
      mTokensToBeConsumed = new HashSet<>();
    } else if (mTokensToBeConsumed.contains(purchaseToken)) {
      Log.i(TAG, "Token was already scheduled to be consumed - skipping...");
      return;
    }
    mTokensToBeConsumed.add(purchaseToken);

    // Generating Consume Response listener
    final ConsumeResponseListener onConsumeListener = (billingResult, purchaseTkn) ->
        mBillingUpdatesListener.onConsumeFinished(purchaseTkn, billingResult);

    // Creating a runnable from the request to use it inside our connection retry policy below
    Runnable consumeRequest = () -> {
      // Consume the purchase async
      ConsumeParams consumeParams =
          ConsumeParams
              .newBuilder()
              .setPurchaseToken(purchaseToken)
              .build();
      mBillingClient.consumeAsync(consumeParams, onConsumeListener);
    };

    executeRetryableBillingRequest(consumeRequest);
  }

  /**
   * Handles the purchase
   * <p>Note: Notice that for each purchase, we check if signature is valid on the client.
   * It's recommended to move this check into your backend. See {@link
   * Security#verifyPurchase(String, String, String)}
   * </p>
   *
   * @param purchase Purchase to be handled
   */
  private void handlePurchase(final Purchase purchase) {
    if (!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature())) {
      Log.i(TAG, "Got a purchase: " + purchase + "; but signature is bad. Skipping...");
      return;
    }

    Log.d(TAG, "Got a verified purchase: " + purchase);

    // we must acknowledge a purchase or it will be automatically cancelled
    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = billingResult -> {
      if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
        Log.i(TAG, "Acknowledged purchase: " + purchase);
      } else {
        Log.w(TAG, "Failed to acknowledge purchase: " + purchase);
      }
    };
    if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
      if (!purchase.isAcknowledged()) {
        AcknowledgePurchaseParams acknowledgePurchaseParams =
            AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();
        mBillingClient.acknowledgePurchase(
            acknowledgePurchaseParams,
            acknowledgePurchaseResponseListener);
      }
    }

    mPurchases.add(purchase);
  }

  /**
   * Handle a result from querying of purchases and report an updated list to the listener
   */
//  private void onQueryPurchasesFinished(PurchasesResult result) {
//    // Have we been disposed of in the meantime? If so, or bad result code, then quit
//    if (mBillingClient == null
//        || result.getResponseCode() != BillingClient.BillingResponseCode.OK) {
//      Log.w(
//          TAG,
//          "Billing client was null or result code ("
//              + result.getResponseCode()
//              + ") was bad - quitting");
//      return;
//    }
//
//    Log.d(TAG, "Query inventory was successful.");
//
//    // Update the UI and purchases inventory with new list of purchases
//    mPurchases.clear();
//    onPurchasesUpdated(
//        BillingResult
//            .newBuilder()
//            .setResponseCode(BillingClient.BillingResponseCode.OK)
//            .build(),
//        result.getPurchasesList());
//  }
  private void onQueryPurchasesFinished(List<Purchase> result) {
    // Have we been disposed of in the meantime? If so, or bad result code, then quit
//    if (mBillingClient == null
//        || result.getResponseCode() != BillingClient.BillingResponseCode.OK) {
//      Log.w(
//          TAG,
//          "Billing client was null or result code ("
//              + result.getResponseCode()
//              + ") was bad - quitting");
//      return;
//    }

    Log.d(TAG, "Query inventory was successful.");

    // Update the UI and purchases inventory with new list of purchases
    mPurchases.clear();
    onPurchasesUpdated(
        BillingResult
            .newBuilder()
            .setResponseCode(BillingClient.BillingResponseCode.OK)
            .build(),
        result);
  }

  /**
   * Query purchases across various use cases and deliver the result in a formalized way through a
   * listener
   */
  @Override
  public void queryPurchases() {

//      long time = System.currentTimeMillis();
//      PurchasesResult purchasesResult = mBillingClient.queryPurchases(SkuType.INAPP);

    mBillingClient.queryPurchasesAsync(
        QueryPurchasesParams.newBuilder()
            .setProductType(ProductType.INAPP)
            .build(),
        (billingResult, purchases) -> {
//            Log.i(TAG, "Querying purchases elapsed time: " + (System.currentTimeMillis() - time)
//                + "ms");
          if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
            Log.i(TAG, "queryPurchases() was successful");
          } else {
            Log.w(TAG, "queryPurchases() got an error response code: "
                + billingResult.getResponseCode());
          }
          onQueryPurchasesFinished(purchases);
        }
    );

//      Log.i(TAG, "Querying purchases elapsed time: " + (System.currentTimeMillis() - time)
//          + "ms");
//      if (purchasesResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//        Log.i(TAG, "queryPurchases() was successful");
//      } else {
//        Log.w(TAG, "queryPurchases() got an error response code: "
//            + purchasesResult.getResponseCode());
//      }
//      onQueryPurchasesFinished(purchasesResult);
    //executeRetryableBillingRequest(queryToExecute);
  }

  private void startServiceConnection(final Runnable executeOnSuccess) {
    mBillingClient.startConnection(new BillingClientStateListener() {
      @Override
      public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
        Log.i(TAG, "Setup finished. Response code: " + billingResult);

        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
          mIsServiceConnected = true;
          if (executeOnSuccess != null) {
            executeOnSuccess.run();
          }
        }
      }

      @Override
      public void onBillingServiceDisconnected() {
        mIsServiceConnected = false;
      }
    });
  }

  @Override
  public boolean isConnected() {
    return mIsServiceConnected;
  }

  private void executeRetryableBillingRequest(Runnable runnable) {
    if (mIsServiceConnected) {
      runnable.run();
    } else {
      // If billing service was disconnected, we try to reconnect 1 time.
      startServiceConnection(runnable);
    }
  }

  /**
   * Verifies that the purchase was signed correctly for this developer's public key.
   * <p>Note: It's strongly recommended to perform such check on your backend since hackers can
   * replace this method with "constant true" if they decompile/rebuild your app.
   * </p>
   */
  private boolean verifyValidSignature(String signedData, String signature) {
    try {
      return Security.verifyPurchase(BASE_64_ENCODED_PUBLIC_KEY, signedData, signature);
    } catch (IOException e) {
      Log.e(TAG, "Got an exception trying to validate a purchase: " + e);
      return false;
    }
  }
}


package com.danosoftware.galaxyforce.billing;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BillingServiceImpl implements BillingService, BillingUpdatesListener {

    private static final String TAG = "BillingService";

    private volatile boolean purchasesReady;
    private volatile PurchaseState fullGamePurchaseState;
    private volatile BillingManager billingManager;

    /*
     * set of observers to be notified following any purchase state changes.
     */
    private final Set<BillingObserver> observers;

    public BillingServiceImpl() {
        this.purchasesReady = false;
        this.fullGamePurchaseState = PurchaseState.NOT_READY;
        this.observers = new HashSet<>();
    }

    /**
     * Handle any changes to the list items of purchased by the user.
     * <p>
     * Called by BillingManager when purchases have changed. Could be triggered
     * by a request to re-query all purchases or if a new item has been purchased
     * by a user.
     * <p>
     * Until this method is called, we have no idea what items have been purchased by the
     * user.
     * <p>
     * The re-query process will be initiated on construction of the Billing
     * Manager and as part of resuming the application (to handle purchase changes
     * while the application was in the background).
     *
     * This game will never consume any purchases but if consumption is needed, use:
     * billingManager.consumeAsync(purchase.getPurchaseToken());
     * ... after successful purchase.
     */
    @Override
    public void onPurchasesUpdated(List<Purchase> purchases) {
        // default to "not purchased" in case the expected full game purchase is not in supplied list
        this.fullGamePurchaseState = PurchaseState.NOT_PURCHASED;

        for (Purchase purchase : purchases) {
            if (BillingConstants.SKU_FULL_GAME.equals(purchase.getSku())) {
                switch (purchase.getPurchaseState()) {
                    case Purchase.PurchaseState.PURCHASED:
                        Log.i(TAG, "Full Game Purchased: '" + purchase.getSku() + "'");
                        this.fullGamePurchaseState = PurchaseState.PURCHASED;
                        break;
                    case Purchase.PurchaseState.PENDING:
                        Log.i(TAG, "Full Game Pending: '" + purchase.getSku() + "'");
                        this.fullGamePurchaseState = PurchaseState.PENDING;
                        break;
                    default:
                        this.fullGamePurchaseState = PurchaseState.NOT_PURCHASED;
                        break;
                }
            } else {
                String errorMsg = "Unknown Purchased SKU: '" + purchase.getSku() + "'";
                Log.e(TAG, errorMsg);
            }
        }
        this.purchasesReady = true;

        /*
         * notify models of purchase state updates. this is called in a
         * billing thread so must be synchronized to avoid new observers
         * being added/removed by the main thread at same time.
         */
        synchronized (this) {
            for (BillingObserver observer : observers) {
                Log.i(TAG, "Sending Purchase State Change to " + observer);
                observer.onFullGamePurchaseStateChange(fullGamePurchaseState);
            }
        }
    }

    /**
     * Retrieve state of full game purchase.
     * <p>
     * Will only return a definite response once onPurchaseUpdated()
     * has been called by the Billing Manager.
     * <p>
     * Calling clients must handle NOT_READY state appropriately.
     */
    @Override
    public PurchaseState getFullGamePurchaseState() {
        final PurchaseState state;
        if (!purchasesReady) {
            state = PurchaseState.NOT_READY;
        } else {
            state = fullGamePurchaseState;
        }
        Log.i(TAG, "Full Game Purchase State: " + state.name());
        return state;
    }

    /**
     * Asynchronously retrieve requested SKU details for the full-game purchase SKU.
     * <p>
     * If the response contains the expected SKU details, return these back to the
     * supplied listener.
     *
     * @param listener - supplied listener to receive/process the SKU details
     */
    @Override
    public void queryFullGameSkuDetailsAsync(
            final SkuDetailsListener listener) {

        if (!isBillingManagerReady()) {
            Log.e(TAG, "Unable to query SKU details. Billing Manager is not ready.");
        } else {
            billingManager.querySkuDetailsAsync(
                    BillingClient.SkuType.INAPP,
                    Collections.singletonList(BillingConstants.SKU_FULL_GAME),
                    new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> skuDetailsList) {
                            if (billingResult.getResponseCode() != BillingClient.BillingResponseCode.OK) {
                                Log.w(TAG, "Unsuccessful SKU query. Error code: " + billingResult.getResponseCode());
                            } else if (skuDetailsList != null) {
                                for (SkuDetails details : skuDetailsList) {
                                    if (details.getSku().equals(BillingConstants.SKU_FULL_GAME)) {
                                        listener.onSkuDetailsRetrieved(details);
                                    }
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void purchaseFullGame(SkuDetails details) {
        if (!isBillingManagerReady()) {
            Log.e(TAG, "Unable to purchase Full Game. Billing Manager is not ready.");
        } else if (details == null) {
            Log.e(TAG, "Unable to purchase Full Game. SkuDetails is null.");
        } else if (!details.getSku().equals(BillingConstants.SKU_FULL_GAME)) {
            Log.e(TAG, "Unable to purchase Full Game. Incorrect SkuDetails supplied.");
        } else if (getFullGamePurchaseState() == PurchaseState.NOT_READY) {
            Log.e(TAG, "Unable to purchase Full Game. Previous purchases are not ready.");
        } else if (getFullGamePurchaseState() == PurchaseState.PURCHASED) {
            Log.e(TAG, "Unable to purchase Full Game. User has already purchased this.");
        } else if (getFullGamePurchaseState() == PurchaseState.PENDING) {
            Log.e(TAG, "Unable to purchase Full Game. An existing purchase is still pending.");
        } else {
            Log.i(TAG, "Requesting purchase of: '" + details.getSku() + "'");
            billingManager.initiatePurchaseFlow(details);
        }
    }

    /*
     * Register an observer for any purchase state refreshes. Normally called
     * when a observer is constructed.
     *
     * Synchronized to avoid adding observer in main thread while notifying
     * observers in billing thread.
     */
    @Override
    public synchronized void registerPurchasesObserver(BillingObserver billingObserver) {
        Log.d(TAG, "Register Billing Observer '" + billingObserver + "'.");
        observers.add(billingObserver);
    }

    /*
     * Unregister an observer for any purchase state refreshes. Normally called
     * when a observer is disposed.
     *
     * Synchronized to avoid removing observer in main thread while notifying
     * observers in billing thread.
     */
    @Override
    public synchronized void unregisterPurchasesObserver(BillingObserver billingObserver) {
        Log.d(TAG, "Unregister Billing Observer '" + billingObserver + "'.");
        observers.remove(billingObserver);
    }

    /**
     * Set Billing Manager once set-up is completed.
     */
    @Override
    public void onBillingClientSetupFinished(BillingManager billingManager) {
        this.billingManager = billingManager;
    }

    /**
     * Normally, we won't ever consume purchases.
     * <p>
     * This is added to support consumption of purchases during testing.
     */
    @Override
    public void onConsumeFinished(String token, @NonNull BillingResult billingResult) {
        Log.i(TAG, "Consumption finished. Purchase token: " + token + ", result: " + billingResult.getResponseCode());

        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
            Log.i(TAG, "Consumption successful.");
        } else {
            Log.e(TAG, "Consumption failed.");
        }

    }

    private boolean isBillingManagerReady() {
        return (billingManager != null
                && billingManager.isConnected());
    }
}

******************
Deployment Actions
******************

List of actions before deploying application as production version.



**** Modify Security.verifyPurchase() *****

Original method read:

public static boolean verifyPurchase(String base64PublicKey, String signedData, String signature)
    {
        if (TextUtils.isEmpty(signedData) || TextUtils.isEmpty(base64PublicKey) || TextUtils.isEmpty(signature))
        {
            Log.e(TAG, "Purchase verification failed: missing data.");
            return false;
        }

        PublicKey key = Security.generatePublicKey(base64PublicKey);
        return Security.verify(key, signedData, signature);
    }
    
However, static responses do not have a valid signature. Also when testing in development mode, there is no signedData.
This would result in verifyPurchase() always returning false when testing.
To avoid this, the following code was added to all verifyPurchase() to return true when testing.

IMPORTANT: This code must be removed when deploying so this method behaves as expected in production.


*******************************************


**** Modify BillingServiceImpl ***********

Improve any temporary development code.
Turn off IAB debug mode.
Should be:
 mHelper.enableDebugLogging(false);
 
 Turn off automatic consumption of products. Used to continually test products.

*******************************************

**** Modify GameConstants ***********

Remove any reference to static billing products.
Ensure only real products are listed.

*******************************************
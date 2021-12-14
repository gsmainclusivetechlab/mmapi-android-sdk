# MMAPI Android SDK
Android SDK to use MMAPI.

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://github.com/gsmainclusivetechlab/mmapi-android-sdk)
[![SDK Version](https://img.shields.io/badge/minSdkVersion-21-blue.svg)](https://developer.android.com/about/versions/android-4.1)
[![SDK Version](https://img.shields.io/badge/targetSdkVersion-31-informational.svg)](https://developer.android.com/sdk/api_diff/31/changes)

A library that fully covers payment process inside your Android application

# Requirements
---
To use the SDK the following requirements must be met:

1. **Android Studio 4.0** or newer
2. **Android Platform Version**: API 31
3. **Build gradle**:4.2.1

<a name="Setup"></a>

# How to include GSMA SDK in your android application

Copy the GSMASdk-debug-1.0.4.aar file, available in the latest version in aar folder in the project directory, into libs folder under your project directory.

Add the below line in dependencies of your build.gradle file in your application.

```
implementation files('libs/GSMASdk-debug-1.0.4.aar')
```
<a name="Configure"></a>

# Configure the SDK

After including the SDK into your project,Configure the SDK with either SANDBOX account or LIVE account

```
 /**
   * Initialise payment configuration with the following
   * consumerKey - provided by Client
   * consumerSecret - provided by Client
   * authenticationType - requried level of authentication, eg:AuthenticationType.STANDARD_LEVEL,AuthenticationType.NO_AUTH;
   * callbackUrl - server url to which long running operation responses are delivered
   * xAPIkey - provided by client 
   * environment - sandbox or production
   */

        PaymentConfiguration.
                init("Place your consumerkey",
                        "Place your consumerSecret",
                        "Place your AuthenticaticationType",
                        "Place your callback URL",
                        "Place your X API Key",
                        Environment.SANDBOX);


```


  Create a token  if the security option is DEVELOPMENT_LEVEL, STANDARD_LEVEL, ENHANCED_LEVEL,
```
   /**
     *Initialise the preference object
    */
 PreferenceManager.getInstance().init(this);
        /**
         * Token creation
         */
 SDKManager.getInstance().init(this, new PaymentInitialiseInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
       
            }

            @Override
            public void onSuccess(Token token) {
          
            }

            @Override
            public void onFailure(GSMAError gsmaError) {
    
            }
        });


  ```
### Merchant Payment 
Contains functions for all the use case scenarios within Merchant Payments.

<table>
<thead>
  <tr>
    <th>Scenarios</th>
    <th>API</th>
    <th>Function</th>
    <th>Parameters</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td rowspan="3">Payee-Initiated Merchant Payment using the Polling Method</td>
    <td>Payee Initiated Merchant Payment</td>
    <td>createMerchantTransaction</td>
    <td>Transaction $transaction, string $callBackUrl = null</td>
  </tr>
  <tr>
    <td>Poll to Determine the Request State</td>
    <td>viewRequestState</td>
    <td>string $serverCorrelationId</td>
  </tr>
  <tr>
    <td>Retrieve a Transaction</td>
    <td>RequestState</td>
    <td>string $transactionReference</td>
  </tr>
  <tr>
    <td>Payee-Initiated Merchant Payment</td>
    <td>Payee Initiated Merchant Payment</td>
    <td>createMerchantTransaction</td>
    <td>Transaction $transaction, string $callBackUrl = null</td>
  </tr>
  <tr>
    <td>Payer-Initiated Merchant Payment</td>
    <td>Payer Initiated Merchant Payment</td>
    <td>createMerchantTransaction</td>
    <td>Transaction $transaction, string $callBackUrl = null</td>
  </tr>
  <tr>
    <td rowspan="3">Payee-Initiated Merchant Payment using a Pre-authorised Payment Code</td>
    <td>Obtain an Authorisation Code</td>
    <td>createAuthorisationCode</td>
    <td>array $accountIdentifier, AuthorisationCode $authorisationCode</td>
  </tr>
  <tr>
    <td>Perform a Merchant Payment</td>
    <td>createMerchantTransaction</td>
    <td>Transaction $transaction, string $callBackUrl = null</td>
  </tr>
  <tr>
    <td>View An Authorisation Code</td>
    <td>viewAuthorisationCode</td>
    <td>string $accountIdentifier, string $authorisationCode</td>
  </tr>
  <tr>
    <td>Merchant Payment Refund</td>
    <td>Perform a Merchant Payment Refund</td>
    <td>createRefundTransaction</td>
    <td>string $transactionReference, Reversal $reversal=null, string $callBackUrl=null</td>
  </tr>
  <tr>
    <td>Merchant Payment Reversal</td>
    <td>Perform a Merchant Payment Reversal</td>
    <td>createReversal</td>
    <td>string $transactionReference, Reversal $reversal=null, string $callBackUrl=null</td>
  </tr>
  <tr>
    <td>Obtain a Merchant Balance</td>
    <td>Get an Account Balance</td>
    <td>viewAccountBalance</td>
    <td>array $accountIdentifier, array $filter=null</td>
  </tr>
  <tr>
    <td>Retrieve Payments for a Merchant</td>
    <td>Retrieve a Set of Transactions for an Account</td>
    <td>viewAccountTransactions</td>
    <td>array $accountIdentifier, array $filter=null</td>
  </tr>
  <tr>
    <td>Check for Service Availability</td>
    <td>Check for Service Availability</td>
    <td>viewServiceAvailability</td>
    <td>NA</td>
  </tr>
  <tr>
    <td>Retrieve a Missing API Response</td>
    <td>Retrieve a Missing Response</td>
    <td>viewResponse</td>
    <td>string $clientCorrelationId, Object $objRef=null</td>
  </tr>
</tbody>
</table>


# International Transfers

Contains functions for all the use case scenarios within International Transfers.

<table>
<thead>
  <tr>
    <th>Scenarios</th>
    <th>API</th>
    <th>Function</th>
    <th>Parameters</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td rowspan="3">International Transfer via Hub</td>
    <td>Request a International Transfer Quotation</td>
    <td>[createQuotation](docs/internationalTransfer/createQuotation.Readme.md)</td>
    <td>Transaction transaction, string callBackUrl = null</td>
  </tr>
  <tr>
    <td>Poll to Determine the Request State</td>
    <td>viewRequestState</td>
    <td>string $serverCorrelationId</td>
  </tr>
  <tr>
    <td>Retrieve a Transaction</td>
    <td>RequestState</td>
    <td>string $transactionReference</td>
  </tr>
  <tr>
    <td>Payee-Initiated Merchant Payment</td>
    <td>Payee Initiated Merchant Payment</td>
    <td>createMerchantTransaction</td>
    <td>Transaction $transaction, string $callBackUrl = null</td>
  </tr>
  <tr>
    <td>Payer-Initiated Merchant Payment</td>
    <td>Payer Initiated Merchant Payment</td>
    <td>createMerchantTransaction</td>
    <td>Transaction $transaction, string $callBackUrl = null</td>
  </tr>
  <tr>
    <td rowspan="3">Payee-Initiated Merchant Payment using a Pre-authorised Payment Code</td>
    <td>Obtain an Authorisation Code</td>
    <td>createAuthorisationCode</td>
    <td>array $accountIdentifier, AuthorisationCode $authorisationCode</td>
  </tr>
  <tr>
    <td>Perform a Merchant Payment</td>
    <td>createMerchantTransaction</td>
    <td>Transaction $transaction, string $callBackUrl = null</td>
  </tr>
  <tr>
    <td>View An Authorisation Code</td>
    <td>viewAuthorisationCode</td>
    <td>string $accountIdentifier, string $authorisationCode</td>
  </tr>
  <tr>
    <td>Merchant Payment Refund</td>
    <td>Perform a Merchant Payment Refund</td>
    <td>createRefundTransaction</td>
    <td>string $transactionReference, Reversal $reversal=null, string $callBackUrl=null</td>
  </tr>
  <tr>
    <td>Merchant Payment Reversal</td>
    <td>Perform a Merchant Payment Reversal</td>
    <td>createReversal</td>
    <td>string $transactionReference, Reversal $reversal=null, string $callBackUrl=null</td>
  </tr>
  <tr>
    <td>Obtain a Merchant Balance</td>
    <td>Get an Account Balance</td>
    <td>viewAccountBalance</td>
    <td>array $accountIdentifier, array $filter=null</td>
  </tr>
  <tr>
    <td>Retrieve Payments for a Merchant</td>
    <td>Retrieve a Set of Transactions for an Account</td>
    <td>viewAccountTransactions</td>
    <td>array $accountIdentifier, array $filter=null</td>
  </tr>
  <tr>
    <td>Check for Service Availability</td>
    <td>Check for Service Availability</td>
    <td>viewServiceAvailability</td>
    <td>NA</td>
  </tr>
  <tr>
    <td>Retrieve a Missing API Response</td>
    <td>Retrieve a Missing Response</td>
    <td>viewResponse</td>
    <td>string $clientCorrelationId, Object $objRef=null</td>
  </tr>
</tbody>
</table>






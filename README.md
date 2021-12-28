# MMAPI Android SDK
Android SDK to use MMAPI.

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://github.com/gsmainclusivetechlab/mmapi-android-sdk)
[![SDK Version](https://img.shields.io/badge/minSdkVersion-23-blue.svg)](https://developer.android.com/about/versions/android-4.1)
[![SDK Version](https://img.shields.io/badge/targetSdkVersion-31-informational.svg)](https://developer.android.com/sdk/api_diff/31/changes)

A library that fully covers payment process inside your Android application

This SDK provides for an easy way to connect to [GSMA Mobile Money API](https://developer.mobilemoneyapi.io/1.2).
Please refer to the following documentation for installation instructions and usage information.

-   [API Documentation](https://developer.mobilemoneyapi.io/1.2)
-   [How to use the test Application](GSMATest/README.md)

# Requirements

Optimum requirements to use this SDK are -

1. **Android Studio `4.0`** or newer
2. **Android Platform Version**: `API 31`
3. **Build gradle**: `4.2.1`

<a name="Setup"></a>

# How to include GSMA SDK in your android application

Copy the GSMASdk-v1.0.5.aar [Download](/aar/GSMASdk-v1.0.5.aar)
 file, available in the latest version in aar folder in the project directory, into libs folder under your project directory.

Add the below line in dependencies of your `build.gradle` file in your application.

```groovy
implementation files('libs/GSMASdk-v1.0.5.aar')
```
<a name="Configure"></a>

# Configure the SDK

After you install the SDK, make it available to your app and configure SDK. 
Configuration details include either sandbox for testing or live for production, and your `consumer key`, `consumer secret`, `api key`, security option  and `callback url` for your app.

In the directory where you installed the SDK,  include this code to make the SDK available and configure your environment with your application credentials for sandbox and live environments in the Developer Dashboard.


```java
 /**
   * Initialise payment configuration with the following
   * consumerKey - provided by Client
   * consumerSecret - provided by Client
   * authenticationType - required level of authentication, eg:AuthenticationType.STANDARD_LEVEL,AuthenticationType.NO_AUTH;
   * callbackUrl - server url to which long running operation responses are delivered
   * xAPIkey - provided by client 
   * environment - sandbox or production
   */

  PaymentConfiguration.init("<Place your consumerKey>","<Place your consumerSecret>","<Place your AuthenticationType>","<Place your callback URL>","<Place your X API Key>",Environment.SANDBOX);


```


  Create a token  if the security option is DEVELOPMENT_LEVEL, STANDARD_LEVEL, ENHANCED_LEVEL,
```java
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
 ## Use Cases

-   [Merchant Payments](#merchant-payments)
-   [Disbursements](#disbursements)
-   [International Transfers](#international-transfers)
-   [P2P Transfers](#p2p-transfers)
-   [Recurring Payments](#recurring)
-   [Account Linking](#account-link)
-   [Bill Payments](#bill-payments)


### Merchant Payments

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
    <td>Payee-Initiated Merchant Payment</td>
    <td><a href="/docs/merchantPayment/createMerchantTransaction.Readme.md">Payee Initiated Merchant Payment</a></td>
    <td>createMerchantTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transactionRequest ,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td rowspan="3">Payee-Initiated Merchant Payment using the Polling Method</td>
    <td><a href="/docs/merchantPayment/createMerchantTransaction.Readme.md">Payee Initiated Merchant Payment</a></td>
    <td>createMerchantTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transactionRequest ,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/merchantPayment/viewRequestState.Readme.md">Poll to Determine the Request State</a></td>
    <td>viewRequestState</td>
    <td>String serverCorrelationId,RequestStateInterface requestStateInterface</td>

  </tr>
  <tr>
    <td><a href="/docs/merchantPayment/viewTransaction.Readme.md">Retrieve a Transaction</a></td>
    <td>viewTransaction</td>
    <td>String transactionReference</td>
  </tr>
  <tr>
    <td>Payer-Initiated Merchant Payment</td>
    <td><a href="/docs/merchantPayment/createMerchantTransaction.Readme.md">Payer Initiated Merchant Payment</a></td>
    <td>createMerchantTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transactionRequest ,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td rowspan="3">Payee-Initiated Merchant Payment using a Pre-authorised Payment Code</td>
    <td><a href="/docs/merchantPayment/createAuthorisationCode.Readme.md">Obtain an Authorisation Code</a></td>
    <td>createAuthorisationCode</td>
     <td>ArrayList<Identifier> identifierList,NotificationMethod, string callBackUrl="",AuthorisationCode authorisationCodeRequest ,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/merchantPayment/createMerchantTransaction.Readme.md">Perform a Merchant Payment</a></td>
    <td>createMerchantTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transactionRequest ,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/merchantPayment/viewAuthorisationCode.Readme.md">View An Authorisation Code</a></td>
    <td>viewAuthorisationCode</td>
    <td>ArrayList<Identifier> identifierList,String transactionReference,AuthorisationCodeItemInterface authorisationCodeItemInterface</td>
 </tr>
  <tr>
    <td>Merchant Payment Refund</td>
    <td><a href="/docs/merchantPayment/createRefundTransaction.Readme.md">Perform a Merchant Payment Refund</a></td>
    <td>createRefundTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transactionRequest ,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td>Merchant Payment Reversal</td>
    <td><a href="/docs/merchantPayment/createReversal.Readme.md">Perform a Merchant Payment Reversal</a></td>
    <td>createReversal</td>
    <td>NotificationMethod, string callBackUrl="",String originalTransactionReference,Reversal reversalobject,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td>Obtain a Merchant Balance</td>
    <td><a href="docs/merchantPayment/viewAccountBalance.Readme.md">Get an Account Balance</a></td>
    <td>viewAccountBalance</td>
       <td>ArrayList<Identifier> identifierList,BalanceInterface balanceInterface</td>
  </tr>
  <tr>
    <td>Retrieve Payments for a Merchant</td>
    <td><a href="/docs/merchantPayment/viewAccountTransactions.Readme.md">Retrieve a Set of Transactions for an Account</a></td>
    <td>viewAccountTransactions</td>
    <td>ArrayList<Identifier> identifierList,TransactionFilter filter,RetrieveTransactionInterface retrieveTransactionInterface</td>
  </tr>
  <tr>
    <td>Check for Service Availability</td>
    <td><a href="/docs/merchantPayment/viewServiceAvailability.Readme.md">Check for Service Availability</a></td>
    <td>viewServiceAvailability</td>
    <td>NA</td>
  </tr>
  <tr>
    <td rowspan="2">Retrieve a Missing API Response</td>
    <td><a href="/docs/merchantPayment/viewResponse.Readme.md">Retrieve a Missing Response</a></td>
    <td>viewResponse</td>
    <td>String correlationId</td>
  </tr>
  <tr></tr>
</tbody>
</table>

### Disbursements

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
    <td>Individual Disbursement</td>
    <td><a href="/docs/disbursement/createDisbursementTransaction.Readme.md">Create A Disbursement Transaction</a></td>
    <td>createDisbursementTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transactionRequest ,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td rowspan="4">Bulk Disbursement</td>
    <td><a href="/docs/disbursement/createBatchTransaction.Readme.md">Create A Transaction Batch</a></td>
    <td>createBatchTransaction</td>
    <td>NotificationMethod, string callBackUrl="",BatchTransaction bulkTransactionObject,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/disbursement/viewBatchTransaction.Readme.md">View A Transaction Batch</a></td>
    <td>viewBatchTransaction</td>
    <td>String transactionReference,BatchTransactionItemInterface batchTransactionItemInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/disbursement/viewBatchCompletions.Readme.md">View Batch Completions</a></td>
    <td>viewBatchCompletions</td>
    <td>String batchId,BatchCompletionInterface batchCompletionInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/disbursement/viewBatchRejections.Readme.md">View Batch Rejections</a></td>
    <td>viewBatchRejections</td>
    <td>String batchId,BatchRejectionInterface batchRejectionInterface</td>
  </tr>
  <tr>
    <td rowspan="5">Bulk Disbursement with Maker / Checker</td>
    <td><a href="/docs/disbursement/createBatchTransaction.Readme.md">Create A Transaction Batch</a></td>
    <td>createBatchTransaction</td>
    <td>NotificationMethod, string callBackUrl="",BatchTransaction bulkTransactionObject,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/disbursement/updateBatchTransaction.Readme.md">Update A Transaction Batch</a></td>
    <td>updateBatchTransaction</td>
     <td>NotificationMethod, string callBackUrl="",String batchId,ArrayList<Batch> batchArrayList,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/disbursement/viewBatchTransaction.Readme.md">View A Transaction Batch</a></td>
    <td>viewBatchTransaction</td>
    <td>String transactionReference,BatchTransactionItemInterface batchTransactionItemInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/disbursement/viewBatchCompletions.Readme.md">View Batch Completions</a></td>
    <td>viewBatchCompletions</td>
    <td>String batchId,BatchCompletionInterface batchCompletionInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/disbursement/viewBatchRejections.Readme.md">View Batch Rejections</a></td>
    <td>viewBatchRejections</td>
    <td>String batchId,BatchRejectionInterface batchRejectionInterface</td>
  </tr>
  <tr>
    <td rowspan="3">Individual Disbursement Using the Polling Method</td>
    <td><a href="/docs/disbursement/createDisbursementTransaction.Readme.md">Create a Individual Disbursement request </a></td>
    <td>createDisbursementTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transactionRequest ,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/disbursement/viewRequestState.Readme.md">Poll to Determine the Request State</a></td>
    <td>viewRequestState</td>
    <td>String serverCorrelationId,RequestStateInterface requestStateInterface</td>
 </tr>
  <tr>
    <td><a href="/docs/disbursement/viewTransaction.Readme.md">Retrieve a Transaction</a></td>
    <td>viewTransaction</td>
    <td>String transactionReference</td>
  </tr>
  <tr>
    <td>Disbursement Reversal</td>
    <td><a href="/docs/disbursement/createReversal.Readme.md">Perform a Disbursement Reversal</a></td>
    <td>createReversal</td>
    <td>NotificationMethod, string callBackUrl="",String originalTransactionReference,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td>Obtain a Disbursement Organisation Balance</td>
    <td><a href="/docs/disbursement/viewAccountBalance.Readme.md">Get an Account Balance</a></td>
    <td>viewAccountBalance</td>
     <td>ArrayList<Identifier> identifierList,BalanceInterface balanceInterface</td>
  </tr>
  <tr>
    <td>Retrieve Transactions for a Disbursement Organisation</td>
    <td><a href="/docs/disbursement/viewAccountTransactions.Readme.md">Retrieve a Set of Transactions for an Account</a></td>
    <td>viewAccountTransactions</td>
    <td>ArrayList<Identifier> identifierList,TransactionFilter filter,RetrieveTransactionInterface retrieveTransactionInterface</td>
  </tr>
  <tr>
    <td>Check for Service Availability</td>
    <td><a href="/docs/disbursement/viewServiceAvailability.Readme.md">Check for Service Availability</a></td>
    <td>viewServiceAvailability</td>
    <td>NA</td>
  </tr>
  <tr>
    <td rowspan="2">Retrieve a Missing API Response</td>
    <td><a href="/docs/disbursement/viewResponse.Readme.md">Retrieve a Missing Response</a></td>
    <td>viewResponse</td>
    <td>String correlationId</td>
  </tr>
   <tr></tr>
</tbody>
</table>



 
### International Transfers

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
    <td><a href="/docs/internationalTransfer/createQuotation.Readme.md">Request a International Transfer Quotation</a></td>
    <td>createQuotation</td>
    <td>NotificationMethod, string callBackUrl="",Quotation quotationRequest,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/internationalTransfer/createInternationalTransaction.Readme.md">Perform an International Transfer</a></td>
    <td>createInternationalTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transactionRequest ,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td>Optional <a href="/docs/internationalTransfer/viewQuotation.Readme.md">View A Quotation</a></td>
    <td>viewQuotation</td>
    <td>String transactionReference,TransactionInterface transactionInterface</td>
  </tr>
  <tr>
    <td rowspan="3">Bilateral International Transfer</td>
    <td><a href="/docs/internationalTransfer/createQuotation.Readme.md">Request a International Transfer Quotation</a></td>
    <td>createQuotation</td>
    <td>NotificationMethod, string callBackUrl="",Quotation quotationRequest,RequestStateInterface requestStateInterface</td>
  </tr>

 <tr>
    <td><a href="/docs/internationalTransfer/createInternationalTransaction.Readme.md">Perform an International Transfer</a></td>
    <td>createInternationalTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transactionRequest ,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td>Optional <a href="/docs/internationalTransfer/viewQuotation.Readme.md">View A Quotation</a></td>
    <td>viewQuotation</td>
    <td>String transactionReference,TransactionInterface transactionInterface</td>
  </tr>
  <tr>
  <tr>
    <td>International Transfer Reversal</td>
    <td><a href="/docs/internationalTransfer/createReversal.Readme.md">Perform a Transaction Reversal</a></td>
    <td>createReversal</td>
    <td>NotificationMethod, string callBackUrl="",String referenceId,Reversal reversal,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td>Obtain an FSP Balance</td>
    <td><a href="/docs/internationalTransfer/viewAccountBalance.Readme.md">Get an Account Balance</a></td>
    <td>viewAccountBalance</td>
    <td>ArrayList<Identifier> identifierList,BalanceInterface balanceInterface</td>
  </tr>
  <tr>
    <td>Retrieve Transactions for an FSP</td>
    <td><a href="/docs/internationalTransfer/viewAccountTransactions.Readme.md">Retrieve a Set of Transactions for an Account</a></td>
    <td>viewAccountTransactions</td>
    <td>ArrayList<Identifier> identifierList,TransactionFilter filter,RetrieveTransactionInterface retrieveTransactionInterface</td>
  </tr>
  <tr>
    <td>Check for Service Availability</td>
    <td><a href="/docs/internationalTransfer/viewServiceAvailability.Readme.md">Check for Service Availability</a></td>
    <td>viewServiceAvailability</td>
    <td>NA</td>
  </tr>
  <tr>
    <td>Retrieve a Missing API Response</td>
    <td><a href="/docs/internationalTransfer/viewResponse.Readme.md">Retrieve a Missing Response</a></td>
    <td>viewResponse</td>
    <td>String correlationId</td>
  </tr>
</tbody>
</table>



### P2P Transfers

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
    <td rowspan="3">P2P Transfer via Switch</td>
    <td><a href="/docs/p2pTransfer/viewAccountName.Readme.md">Retrieve the Name of the Recipient</a></td>
    <td>viewAccountName</td>
    <td>ArrayList<Identifier> identifierArrayList,AccountHolderInterface accountHolderInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/p2pTransfer/createQuotation.Readme.md">Request a P2P Quotation</a></td>
    <td>createQuotation</td>
    <td>NotificationMethod, string callBackUrl="",Quotation quotationRequest,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td>Optional <a href="/docs/p2pTransfer/createTransferTransaction.Readme.md">Perform a P2P Transfer</a></td>
    <td>createTransferTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transactionRequest ,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td rowspan="2">Bilateral P2P Transfer</td>
    <td><a href="/docs/p2pTransfer/viewAccountName.Readme.md">Retrieve the Name of the Recipient</a></td>
    <td>viewAccountName</td>
    <td>ArrayList<Identifier> identifierArrayList,AccountHolderInterface accountHolderInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/p2pTransfer/createTransferTransaction.Readme.md">Perform a P2P Transfer</a></td>
    <td>createTransferTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Quotation quotationRequest,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td rowspan="3">‘On-us’ P2P Transfer Initiated by a Third Party Provider</td>
    <td><a href="/docs/p2pTransfer/viewAccountName.Readme.md">Retrieve the Name of the Recipient</a></td>
    <td>viewAccountName</td>
    <td>ArrayList<Identifier> identifierArrayList,AccountHolderInterface accountHolderInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/p2pTransfer/createQuotation.Readme.md">Request a P2P Quotation</a></td>
    <td>createQuotation</td>
    <td>NotificationMethod, string callBackUrl="",Quotation quotationRequest,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/p2pTransfer/createTransferTransaction.Readme.md">Perform a P2P Transfer</a></td>
    <td>createTransferTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transactionRequest ,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td>P2P Transfer Reversal</td>
    <td><a href="/docs/p2pTransfer/createReversal.Readme.md">Perform a Transaction Reversal</a></td>
    <td>createReversal</td>
    <td>NotificationMethod, string callBackUrl="",String referenceId,Reversal reversal,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td>Obtain an FSP Balance</td>
    <td><a href="/docs/p2pTransfer/viewAccountBalance.Readme.md">Get an Account Balance</a></td>
    <td>viewAccountBalance</td>
       <td>ArrayList<Identifier> identifierList,BalanceInterface balanceInterface</td>
  </tr>
   <tr>
    <td>Retrieve Transactions for an FSP</td>
    <td><a href="/docs/p2pTransfer/viewAccountTransactions.Readme.md">Retrieve a Set of Transactions for an Account</a></td>
    <td>viewAccountTransactions</td>
    <td>ArrayList<Identifier> identifierList,TransactionFilter filter,RetrieveTransactionInterface retrieveTransactionInterface</td>
  </tr>
  <tr>
    <td>Check for Service Availability</td>
    <td><a href="/docs/p2pTransfer/viewServiceAvailability.Readme.md">Check for Service Availability</a></td>
    <td>viewServiceAvailability</td>
    <td>NA</td>
  </tr>
  <tr>
    <td rowspan="2">Retrieve a Missing API Response</td>
    <td><a href="/docs/p2pTransfer/viewResponse.Readme.md">Retrieve a Missing Response</a></td>
    <td>viewResponse</td>
    <td>String correlationId</td>
 </tr>
 <tr></tr>
</tbody>
</table>

### Recurring Payments

Contains functions for all the use case scenarios within Recurring Payments.

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
    <td>Setup a Recurring Payment</td>
    <td><a href="/docs/recurringPayment/createAccountDebitMandate.Readme.md">Setup a Recurring Payment</a></td>
    <td>createAccountDebitMandate</td>
    <td>NotificationMethod, string callBackUrl="",DebitMandateRequest debitMandateRequest,ArrayList<Identifier> identifierArrayList,RequestStateInterface requestStateInterface
</td>
  </tr>
  
  <tr>
    <td>Take a Recurring Payment</td>
    <td><a href="/docs/recurringPayment/createMerchantTransaction.Readme.md">Take a Recurring Payment</a></td>
    <td>createMerchantTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transaction,RequestStateInterface requestStateInterface</td>
  </tr>
  
  <tr>
    <td rowspan="3">Take a Recurring Payment using the Polling Method</td>
    <td><a href="/docs/recurringPayment/createMerchantTransaction.Readme.md">Take a Recurring Payment</a></td>
    <td>createMerchantTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transaction,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td><a href="/docs/recurringPayment/viewRequestState.Readme.md">Poll to Determine the Request State</a></td>
    <td>viewRequestState</td>
    <td>String serverCorrelationId</td>
  </tr>
  <tr>
    <td><a href="/docs/recurringPayment/viewTransaction.Readme.md">Retrieve a Transaction</a></td>
    <td>viewTransaction</td>
    <td>String transactionReference</td>
  </tr>
  
  <tr>
    <td>Recurring Payment Refund</td>
    <td><a href="/docs/recurringPayment/createRefundTransaction.Readme.md">Perform a Recurring Payment Refund</a></td>
    <td>createRefundTransaction</td>
    <td>NotificationMethod, string callBackUrl="",Transaction transaction,RequestStateInterface requestStateInterface</td>
  </tr>
  
  <tr>
    <td>Recurring Payment Reversal</td>
    <td><a href="/docs/recurringPayment/createReversal.Readme.md">Perform a Merchant Payment Reversal</a></td>
    <td>createReversal</td>
    <td>NotificationMethod, string callBackUrl="",String originalTransactionReference,Reversal reversalobject,RequestStateInterface requestStateInterface</td>
  </tr>
  
  <tr>
    <td>Payer sets up a Recurring Payment using MMP Channel</td>
    <td><a href="/docs/recurringPayment/createAccountDebitMandate.Readme.md">Setup a Recurring Payment</a></td>
    <td>createAccountDebitMandate</td>
    <td>NotificationMethod, string callBackUrl="",DebitMandateRequest debitMandateRequest,ArrayList<Identifier> identifierArrayList,RequestStateInterface requestStateInterface
  </tr>
  
  <tr>
    <td>Obtain a Service Provider Balance</td>
    <td><a href="/docs/recurringPayment/viewAccountBalance.Readme.md">Get an Account Balance</a></td>
    <td>viewAccountBalance</td>
    <td>ArrayList<Identifier> identifierList,BalanceInterface balanceInterface</td>
  </tr>
  
  <tr>
    <td>Retrieve Payments for a Service Provider</td>
    <td><a href="/docs/recurringPayment/viewAccountTransactions.Readme.md">Retrieve a Set of Transactions for an Account</a></td>
    <td>viewAccountTransactions</td>
    <td>ArrayList<Identifier> identifierList,TransactionFilter filter,RetrieveTransactionInterface retrieveTransactionInterface</td>
  </tr>
  
  <tr>
    <td>Check for Service Availability</td>
    <td><a href="/docs/recurringPayment/viewServiceAvailability.Readme.md">Check for Service Availability</a></td>
    <td>viewServiceAvailability</td>
    <td>NA</td>
  </tr>
  
  <tr>
    <td>Retrieve a Missing API Response</td>
    <td><a href="/docs/recurringPayment/viewResponse.Readme.md">Retrieve a Missing Response</a></td>
    <td>viewResponse</td>
    <td>String correlationId</td>
  </tr>
  
</tbody>
</table>


### Account Linking

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
    <td rowspan="3">Common</td>
    <td><a href="/docs/accountLinking/viewAccountBalance.Readme.md">Obtain a Financial Service Provider Balance</a></td>
    <td>viewAccountBalance</td>
    <td>Identifiers identifiers</td>
  </tr>
  <tr>
    <td><a href="/docs/accountLinking/viewServiceAvailability.Readme.md">Check for Service Availability</a></td>
    <td>viewServiceAvailability</td>
    <td>NA</td>
  </tr>
  <tr>
    <td><a href="/docs/accountLinking/viewResponse.Readme.md">Retrieve a Missing API Response</a></td>
    <td>viewResponse</td>
    <td>String clientCorrelationId, Class<T> objectReference</td>
  </tr>
  <tr>
    <td>Setup an Account Link</td>
    <td><a href="/docs/accountLinking/createAccountLink.Readme.md">Establish an Account to Account Link</a></td>
    <td>createAccountLink</td>
    <td>Identifiers identifiers</td>
  </tr>
  <tr>
    <td>Perform a Transfer for a Linked Account</td>
    <td><a href="/docs/accountLinking/createTransferTransaction.Readme.md">Use a Link to make a Transfer</a></td>
    <td>createTransferTransaction</td>
    <td>NA</td>
  </tr>
  <tr>
    <td rowspan="3">Perform a Transfer using an Account Link via the Polling Method</td>
    <td><a href="docs/accountLinking/createTransferTransaction.Readme.md">Use a Link to make a Transfer</a></td>
    <td>createTransferTransaction</td>
    <td>NA</td>
  </tr>
  <tr>
    <td><a href="/docs/accountLinking/viewRequestState.Readme.md">Poll to Determine the Request State</a></td>
    <td>viewRequestState</td>
    <td>String serverCorrelationId</td>
  </tr>
  <tr>
    <td><a href="/docs/accountLinking/viewTransaction.Readme.md">Retrieve a Transaction</a></td>
    <td>viewTransaction</td>
    <td>String transactionReference</td>
  </tr>
  <tr>
    <td>Perform a Transfer Reversal</td>
    <td><a href="docs/accountLinking/createReversal.Readme.md">Perform a Transaction Reversal</a></td>
    <td>createReversal</td>
    <td>String transactionReference</td>
  </tr>
  <tr>
    <td>Retrieve Transfers for a Financial Service Provider</td>
    <td><a href="/docs/accountLinking/viewAccountTransactions.Readme.md">Retrieve a Set of Transactions for an Account</a></td>
    <td>viewAccountTransactions</td>
    <td>Identifiers identifiers</td>
  </tr>
  <tr>
    <td>Read a specific link for a given account.</td>
    <td><a href="/docs/accountLinking/viewAccountLink.Readme.md">Read a specific link for a given account</a></td>
    <td>viewAccountLink</td>
    <td>Identifiers identifiers, String linkReference</td>
  </tr>
</tbody>
</table>


### Bill Payments

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
    <td>Successful Retrieval of Bills</td>
    <td><a href="/docs/billPayment/viewAccountBills.Readme.md">Retrieve a Set of Bills</a></td>
    <td>viewAccountBills</td>
    <td>array $accountIdentifier, array $filter = null</td>
  </tr>
  <tr>
    <td rowspan="2">Make a Successful Bill Payment with Callback</td>
    <td><a href="/docs/billPayment/createBillTransaction.Readme.md">Create a Bill Transaction</a></td>
    <td>createBillTransaction</td>
    <td>Transaction $transaction, string $callBackUrl = null</td>
  </tr>
  <tr>
    <td><a href="/docs/billPayment/createBillPayment.Readme.md">Make a Bill Payment</a></td>
    <td>createBillPayment</td>
    <td>array $accountIdentifier, string $billReference, BillPay $billPay</td>
  </tr>
  <tr>
    <td rowspan="3">Make a Bill Payment with Polling</td>
    <td><a href="/docs/billPayment/createBillPayment.Readme.md">Make a Bill Payment</a></td>
    <td>createBillPayment</td>
    <td>array $accountIdentifier, string $billReference, BillPay $billPay</td>
  </tr>
   <tr>
    <td><a href="/docs/billPayment/viewRequestState.Readme.md">Poll to Determine the Request State</a></td>
    <td>viewRequestState</td>
    <td>string $serverCorrelationId</td>
  </tr>
  <tr>
    <td><a href="/docs/billPayment/viewBillPayment.Readme.md">Retrieve Bill Payments for a Given Bill</a></td>
    <td>viewBillPayment</td>
    <td>array $accountIdentifier, string $billReference, array $filter=null</td>
  </tr>
   <tr>
    <td>Retrieval of Bill Payments</td>
    <td><a href="/docs/billPayment/viewBillPayment.Readme.md">Retrieve a Set of Bill Payments</a></td>
    <td>viewBillPayment</td>
    <td>array $accountIdentifier, string $billReference, array $filter=null</td>
  </tr>
  <tr>
    <td>Check for Service Availability</td>
    <td><a href="/docs/billPayment/viewServiceAvailability.Readme.md">Check for Service Availability</a></td>
    <td>viewServiceAvailability</td>
    <td>NA</td>
  </tr>
  <tr>
    <td>Retrieve a Missing API Response</td>
    <td><a href="/docs/billPayment/viewBillPayment.Readme.md">Retrieve a Missing Response</a></td>
    <td>viewResponse</td>
    <td>string $clientCorrelationId, Object $objRef=null</td>
  </tr>
</tbody>
</table>









# MMAPI Android SDK
Android SDK to use MMAPI.

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://github.com/gsmainclusivetechlab/mmapi-android-sdk)
[![SDK Version](https://img.shields.io/badge/minSdkVersion-16-blue.svg)](https://developer.android.com/about/versions/android-4.1)
[![SDK Version](https://img.shields.io/badge/targetSdkVersion-31-informational.svg)](https://developer.android.com/sdk/api_diff/31/changes)

A library that fully covers payment process inside your Android application

# Table of Contents
---
1. [Requirements](#requirements)
2. [How to include GSMA-SDK in your android application](#Setup)
3. [Configure the SDK](#Configure)
4. [Use cases](#usecases)
   1. [Merchant Payment](#merchant-pay)
      1. [Payee-Initiated Merchant Payment](#payee-merchant-pay)
      2. [Payee-Initiated Merchant Payment using the Polling Method](#merchant-pay-polling)
      3. [Payer-Initiated Merchant Payment](#payee-merchant-pay)
      4. [Payee-Initiated Merchant Payment using a Pre-authorised Payment Code](#payee-merchant-pay-authcode)
      5. [Merchant Payment Refund](#merchant-pay-refund)
      6. [Merchant Payment Reversal](#merchant-pay-reversal)
      7. [Obtain a Merchant Balance](#merchant-pay-balance)
      8. [Retrieve Payments for a Merchant](#merchant-pay-retrieve)
      9. [Check for Service Availability](#check-for-service)
      10. [Retrieve a Missing API Response](#missing-response)
   
   2. [Disbursement](#disbursement)
      1. [Individual Disbursement](#individual)
      2. [Individual Disbursement using polling method](#individual-polling)
 5. [How to Test sample application](https://github.com/gsmainclusivetechlab/mmapi-android-sdk/blob/develop/GSMATest/README.md)
 <a name="requirements"></a>
# Requirements
---
To use the SDK the following requirements must be met:

1. **Android Studio 4.0** or newer
2. **Android Platform Version**: API 31
3. **Build gradle**:4.2.1

<a name="Setup"></a>

# How to include GSMA SDK in your android application

Copy the GSMASdk-debug-1.0.aar file, available in the latest version in aar folder in the project directory, into libs folder under your project directory.

Add the below line in dependencies of your build.gradle file in your application.

```
implementation files('libs/GSMASdk-debug-1.0.aar')
```
<a name="Configure"></a>

# Configure the SDK

After including the SDK into your project,Configure the SDK with either SANDBOX account or live account

```
//Initialise the payment system before calling the functions in the SDK

//Declare the configuration parameter

    private String consumerKey;//optional parameter is NO_AUTH
    private String consumerSecret;//optional paramater if the security level is NO_AUTH
    private Enum securityOption;// options  NO_AUTH , DEVELOPMENT_LEVEL, STANDARD_LEVEL, ENHANCED_LEVEL,
    private String callBackURL;//The backend server URL for your app for handling callbacks in application
    private String xAPIKey;//The API key provided from MMAPI dashboard

```

 Inside oncreate method setup the payment configuration constructor with required parameters

 ```

 //sample consumer key for testing,you can generate a consumer key for your app from MMAPI dashboard under apps
  consumerKey="9vthmq3f6i15v6jmcjskfkmh";

  //sample consumer secret for testing,you can generate a consumer secret for your app from MMAPI dashboard under apps
  consumerSecret="ef8tl4gihlpfd7r8jpc1t1nda33q5kcnn32cj375lq6mg2nv7rb";


  //The security option availbale in SDK are,AuthenticationType.NO_AUTH,AuthenticationType.DEVELOPMENT_LEVEL,AuthenticationType.STANDARD_LEVEL,AuthenticationType.ENHANCED_LEVEL

  securityOption=AuthenticationType.STANDARD_LEVEL;

  //Example mock URL for handling callback,Change the url into your live callback URL
   callBackURL="Put your callback URL here";

   xAPIKey="Put your x api key here";

   //Confgure the SDK
   PaymentConfiguration.init(consumerKey,consumerSecret,securityOption,callBackURL,xAPIKey,Environment.SANDBOX);
   PreferenceManager.getInstance().init(this);
  ```

  Create a token  if the security option is DEVELOPMENT_LEVEL, STANDARD_LEVEL, ENHANCED_LEVEL,
```
   /**
     *Initialise the preference object
    */



GSMAApi.getInstance().init(this, new PaymentInitialiseInterface() {
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

<a name="usecases"></a>

<a name="merchant-pay"></a>

# Merchant Payment
The Merchant Payment Mobile Money APIs allow merchants to accept payments from mobile money customers.


<a name="payee-merchant-pay"></a>

# Payee-initiated merchant payment.

The merchant initiates the request and will be credited when the payer approves the request.

A transaction object is to be created before calling the payee-initiated merchant payment,The example for transaction object as follows


```
private TransactionRequest transactionRequest;


```

```
private void createTransactionObject() {
        transactionRequest = new TransactionRequest();
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("Place your account id of debit party here");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("Place your account id of credit party here");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("Place your amount"); //eg:200.00
        transactionRequest.setCurrency("Place your currency here"); // for eg: RWF
  }
```
 Initiate the mechant pay request using the following code


```
    SDKManager.getInstance().merchantPay("merchantpay", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

             }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {

              }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }
        });

```
<a name="merchant-pay-polling"></a>
# Payee-Initiated Merchant Payment using the Polling Method

An asynchronous payment flow is used with the polling method. The client polls against the request state object to determine the outcome of the payment request.These payment flow can achieved using the following API

    1.Payee Initiated Merchant Payment
    2.Poll to Determine the Request State
    3.Retrieve a Transaction


## 1.Payee Initiated Merchant Payment

The merchant initiates the request and will be credited when the payer approves the request.

A transaction object is to be created before calling the payee-initiated merchant payment,The example for transaction object as follows


```
private TransactionRequest transactionRequest;
private String serverCorrelationId = "";
private String transactionRef = "";
```

```
private void createTransactionObject() {
        transactionRequest = new TransactionRequest();
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("Place your account id of debit party here");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("Place your account id of credit party here");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("Place your amount"); //eg:200.00
        transactionRequest.setCurrency("Place your currency here"); // for eg: RWF
  }
```
 Initiate the mechant pay request using the following code


```
    SDKManager.getInstance().merchantPay("merchantpay", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

             }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                   serverCorrelationId = requestStateObject.getServerCorrelationId();
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }
        });

```
   ### 2.Poll to Determine the Request State
   ````
   SDKManager.getInstance().viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                transactionRef = requestStateObject.getObjectReference();
         }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

        });
  ````
  ### 3.Retrieve a Transaction

  ```
    /**
         * @param accountid account identifier
         * @param offset Offset
         * @param limit  Limit
         * @param transaction Listener
         */
         SDKManager.getInstance().retrieveTransaction("2000", 0, 5, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction, String correlationID) {

            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {

            }
        });
  ```
  <a name="payer-merchant-pay"></a>

# Payer Initiated Merchant Payment

The merchant initiates the request and will be credited when the payer approves the request.

A transaction object is to be created before calling the payee-initiated merchant payment,The example for transaction object as follows


```
private TransactionRequest transactionRequest;
private String serverCorrelationId = "";
private String transactionRef = "";
```

```
private void createTransactionObject() {
        transactionRequest = new TransactionRequest();
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("Place your account id of debit party here");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("Place your account id of credt party here");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("Place your amount"); //eg:200.00
        transactionRequest.setCurrency("Place your currency here"); // for eg: RWF
  }
```
 Initiate the mechant pay request using the following code


```
    SDKManager.getInstance().merchantPay("merchantpay", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

             }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                   serverCorrelationId = requestStateObject.getServerCorrelationId();
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }
        });

```

<a name="payee-merchant-pay-authcode"></a>

# Payee-Initiated Merchant Payment using a Pre-authorised Payment Code

Mobile money app submit the request to generate the authorisation code to MMP,The mmp return the request state object to indicate the request is pending,The payer communicate the code to merchant,via verabally or QR code

 Declare the Authorization code object

 ```
 private AuthorisationCodeRequest authorisationCodeRequest;
 private TransactionRequest transactionRequest;
 private String serverCorrelationId = "";
 private String transactionRef = "";
 ```
 Initialise the  authorizarion code with  amount,date and currency


 ```
     private void createCodeRequestObject() {
        authorisationCodeRequest = new AuthorisationCodeRequest();
        authorisationCodeRequest.setAmount("Place your amount here"); // eg:200.00
        authorisationCodeRequest.setRequestDate("Place your date here"); //sample format 2021-10-18T10:43:27.405Z
        authorisationCodeRequest.setCurrency("Place your currency"); //eg:RWF

    }

```
Obtain Authorization code to perform merchant payment,The authorization code is send to the user,These scenario can be achieved by passing account number and authorization code request to the function

```
     SDKManager.getInstance().obtainAuthorisationCode("2000", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

        });

```

A transaction object is to be created before calling the payee-initiated merchant payment,The example for transaction object as follows
```
private void createTransactionObject() {
        transactionRequest = new TransactionRequest();
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();

        CreditPartyItem creditPartyItem = new CreditPartyItem();
        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("Place your account id of debit party here");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("Place your account id of credit party here");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("Place your amount"); //eg:200.00
        transactionRequest.setCurrency("Place your currency here"); // for eg: RWF
        transactionRequest.setOneTimeCode("Place your One time code");
}

```

Initiate the mechant pay request using the following code

```
    SDKManager.getInstance().merchantPay("merchantpay", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

             }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                   serverCorrelationId = requestStateObject.getServerCorrelationId();
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }
        });

```
<a name="merchant-pay-refund"></a>

# Merchant Payment Refund

Merchants can issue a refund to payers. Create transcation object for refund

```
private TransactionRequest transactionRequest;

```

```
private void createTransactionObject() {
        transactionRequest = new TransactionRequest();
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("Place your account id of debit party here");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("Place your account id of credt party here");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("Place your amount"); //eg:200.00
        transactionRequest.setCurrency("Place your currency here"); // for eg: RWF

}
```
Create a refund request with transaction parameter

```
SDKManager.getInstance().refundMerchantPay(transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {

            }
        });

```
<a name="merchant-pay-reversal"></a>

# Merchant Payment Reversal

In some failure scenarios, merchant may need to reverse a transaction,Create a reversal object of reversal transaction

Declare the revesal object

```
private ReversalObject reversalObject;
```

```
private void createPaymentReversalObject() {
        reversalObject = new ReversalObject();
        reversalObject.setReversal("reversal");
 }
```
Call the reversal function with reversal and reference Id of transaction obtained using the polling method

```

 SDKManager.getInstance().reversal("Place your reference id here", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {

            }
        });

```

<a name="merchant-pay-balance"></a>

# Obtain a Merchant Balance

Obtain the balance of requested account,Pass the accountid to the function to retrieve the balance details

```
  SDKManager.getInstance().getBalance("Place your account id here", new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onBalanceSuccess(Balance balance, String correlationID) {
              ;
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {

            }
        });
```
<a name="merchant-pay-retrieve"></a>

# Retrieve Payments for a Merchant

Merchant can retrieve all transaction details

```
        /**
         * @param accountid account identifier
         * @param offset Offset
         * @param limit  Limit
         * @param Retrieve transaction Listener
         */

 SDKManager.getInstance().retrieveTransaction("Place your account id", 0, 5, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction, String correlationID) {

            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {

            }
        });

```
<a name="check-for-service"></a>

# Check for Service Availability

The application should perform service availabilty check before calling the payment scenarios

    private void checkServiceAvailability() {
           SDKManager.getInstance().checkServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability, String correlationID) {

            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {

            }
        });

<a name="missing-response"></a>
# Retrieve a Missing API Response

Merchant to retrieve a link to the final representation of the resource for which it attempted to create. Use this API when a callback is not received from the mobile money provider.

## 1.Missing Transaction Response

```
SDKManager.getInstance().retrieveMissingTransaction(correlationId, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionObject transactionObject, String correlationId) {

            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {


            }

            @Override
            public void onValidationError(ErrorObject errorObject) {

           }

        });

```
### 2.Missing Authorization code response

```

 SDKManager.getInstance().retrieveMissingCode(correlationId, new AuthorisationCodeInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCode, String correlationId) {

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {

            }


            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

        });

```
<a name="disbursement"></a>

The Disbursement Mobile Money APIs allow organisations to disburse funds to mobile money recipients.
# Individual Disbursement

<a name="individual"></a>

Individual disbursement using an asynchronous flow with the notification provided via a callback.


A transaction object is to be created before calling the individual disbursement,The example for transaction object as follows


```
private TransactionRequest transactionRequest;
private String serverCorrelationId = "";
private String transactionRef = "";
```

```
private void createTransactionObject() {
        transactionRequest = new TransactionRequest();
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("Place your account id of debit party here");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("Place your account id of credt party here");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("Place your amount"); //eg:200.00
        transactionRequest.setCurrency("Place your currency here"); // for eg: RWF
  }
```
Intiate the disbursement using the following code

```
 SDKManager.getInstance().disbursementPay("disbursement", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
               
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
             

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
          
            }

        });

```
<a name="individual-polling"></a>

# Individual Disbursement Using the Polling Method

The individual disbursement using polling method can be completed using the following function calls

An asynchronous payment flow is used with the polling method. The client polls against the request state object to determine the outcome of the payment request.These payment flow can achieved using the following API

    1.Disbursement Request
    2.Poll to Determine the Request State
    3.Retrieve a Transaction


## 1.Disbursement Request

A transaction object is to be created before calling the payee-initiated merchant payment,The example for transaction object as follows


```
private TransactionRequest transactionRequest;
private String serverCorrelationId = "";
private String transactionRef = "";
```

```
private void createTransactionObject() {
        transactionRequest = new TransactionRequest();
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("Place your account id of debit party here");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("Place your account id of credit party here");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("Place your amount"); //eg:200.00
        transactionRequest.setCurrency("Place your currency here"); // for eg: RWF
  }
```
 Initiate the mechant pay request using the following code


```
    SDKManager.getInstance().merchantPay("disbursement", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

             }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                   serverCorrelationId = requestStateObject.getServerCorrelationId();
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }
        });

```
   ### 2.Poll to Determine the Request State
   ````
   SDKManager.getInstance().viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                transactionRef = requestStateObject.getObjectReference();
         }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

        });
  ````
  ### 3.Retrieve a Transaction

  ```
    /**
         * @param accountid account identifier
         * @param offset Offset
         * @param limit  Limit
         * @param transaction Listener
         */
         SDKManager.getInstance().retrieveTransaction("2000", 0, 5, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction, String correlationID) {

            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {

            }
        });
  ```



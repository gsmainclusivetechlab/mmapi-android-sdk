# MMAPI Android SDK
Android SDK to use MMAPI.

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://github.com/gsmainclusivetechlab/mmapi-android-sdk)
[![SDK Version](https://img.shields.io/badge/minSdkVersion-16-blue.svg)](https://developer.android.com/about/versions/android-4.1)
[![SDK Version](https://img.shields.io/badge/targetSdkVersion-31-informational.svg)](https://developer.android.com/sdk/api_diff/31/changes)

A library that fully covers payment process inside your Android application

# Table of Contents
---
1. [Requirements](#require)
2. [How to include GSMA-SDK in your android application](#Setup)
3. [Configure the SDK](#Configure)
4. [Use cases](#usecases)
   1. [Merchant Payment](#merchant-pay)
      1. [Payee-Initiated Merchant Payment](#payee-merchant-pay)
      2. [Payee-Initiated Merchant Payment using the Polling Method](#merchant-pay-polling)
           * [Payee Initiated Merchant Payment](#payee-merchant-pay)
           * [Poll to Determine the Request State](#request-state)
           * [Retrieve a Transaction](#view-transaction)
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
           * [Disbursement](#disbursement-request)
           * [Poll to Determine the Request State](#disbursement-request-state)
           * [Retrieve a Disbursement Transaction](#disbursement-view-transaction)
      3. [Bulk Disbursement](#bulk-disbursement)
            * [Perform a Bulk Disbursement](#bulk-disbursement-pay)
            * [Retrieve Batch Transactions that have Completed](#bulk-disbursement-completed)
            * [Retrieve Batch Transactions that have been Rejected](#bulk-disbursement-rejected)
      4. [Bulk Disbursement with maker/checker](#bulk-maker-checker)
            * [Perform a Bulk Disbursement](#bulk-disbursement-pay)
            * [Retrieve Batch Transactions that have Completed](#bulk-disbursement-completed)
            * [Retrieve Batch Transactions that have been Rejected](#bulk-disbursement-rejected)
            * [Approve Batch request](#bulk-disbursement-approve) 
            * [Retrieve the batch request](#bulk-disbursement-retrieve)
      5. [Disbursement Reversal](#disbursement-pay-reversal)
      6. [Organizational balance](#disbursement-pay-balance)
      7. [Retrieve Transactions for a Disbursement Organisation](#disbursement-pay-retrieve)
      8. [Check for Service Availability](#check-for-service-disbursement)
      9. [Retrieve a Missing API Response](#missing-response-disbursement)
      
   3. [International Transfers](#international-transfer)
       1. [International Transfer via Hub](#international-transfer-feature)
            * [Request a International Transfer Quotation](#international-transfer-quotation)
            * [Perform an International Transfer](international-transfer)
       2. [Bilateral International  Transfer](#international-transfer-feature)
            * [Request a International Transfer Quotation](#international-transfer-quotation)
            * [Perform an International Transfer](international-transfer) 
       4. [International Transfer Reversal](#international-pay-reversal)
       5. [Obtain an FSP Balance](#international-pay-balance)
       6. [Retrieve Transactions for an FSP](#international-pay-retrieve)
       7. [Check for Service Availability](#check-for-service-international)
       8. [Retrieve a Missing API Response](#missing-response-international)
   4. [PP2P Transfers](#p2p-switch)
      1. [P2P Transfer via Switch](#p2p-switch)
           * [Confirm the recipient name](#p2p-name)
           * [Request a quotation](#p2p-quotation) 
           * [Perform the transfer with the receiving FSP](#p2p-transfer-fsp)
       2. [Bilateral P2P Transfer](#p2p-bilateral)
           * [Confirm the recipient name](#p2p-name)
           * [Perform the transfer with the receiving FSP](#p2p-transfer-fsp) 
       3. [On-us’ P2P Transfer Initiated by a Third Party Provider](#p2p-switch)
           * [Confirm the recipient name](#p2p-name)
           * [Request a quotation](#p2p-quotation) 
           * [Perform the transfer with the receiving FSP](#p2p-transfer-fsp)  
       4. [Obtain an  FSP Balance](#p2p-pay-balance)
       5. [Retrieve Transactions for an FSP](#p2p-pay-retrieve)
       6. [Check for Service Availability](#check-for-service-p2p)
       7. [Retrieve a Missing API Response](#missing-response-p2p)
     5. [Recurring Payments](#recurring-payments) 
        1. [Setup a Recurring Payment](#recurring-setup)
        2. [Take a Recurring Payment](#recurring-take)
              * [Create Account Debit Mandate](#recurring-mandate)
              * [View Account Debit Mandate](#recurring-view-debit)
              * [Perform Merchant Transaction using Debit Mandate](#recurring-merchant)
        4. [Take a Recurring Payment using the Polling Method](#take-recurring)
              * [Perform Merchant Transaction using Debit Mandate](#recurring-merchant)
              * [Poll to Determine the Request State](#request-state-recurring)
              * [Retrieve a Transaction](#view-transaction-recurring)
        6. [Recurring Payment Refund](#recurring-pay-refund)
        7. [Recurring Payment Reversal](#recurring-pay-reversal)
        8. [Payer sets up a Recurring Payment using MMP Channel](#recurring-setup)
        9.  [Obtain a Service Provider Balance](#recurring-pay-balance)
        10. [Retrieve Transactions for an FSP](#recurring-pay-retrieve)
        11. [Check for Service Availability](#check-for-service-recurring)
        12. [Retrieve a Missing API Response](#missing-response-recurring)
           
  5. [How to Test sample application](GSMATest/README.md)
 

<a name="require"></a>

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

<a name="usecases"></a>

<a name="merchant-pay"></a>

# Merchant Payment
The Merchant Payment Mobile Money APIs allow merchants to accept payments from mobile money customers.


<a name="payee-merchant-pay"></a>

# Payee/Payer-initiated merchant payment.

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
  
   SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod.CALLBACK,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
      
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
      
            }
            @Override
            public void getCorrelationId(String correlationID) {
            
            }

       });

```
<a name="merchant-pay-polling"></a>
# Payee-Initiated Merchant Payment using the Polling Method

An asynchronous payment flow is used with the polling method. The client polls against the request state object to determine the outcome of the payment request.These payment flow can achieved using the following API

 * Payee Initiated Merchant Payment<br />
 * Poll to Determine the Request State<br />
 * Retrieve a Transaction<br />


<a name="#payee-merchant-pay"></a>
### Payee Initiated Merchant Payment

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
  SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod.CALLBACK,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
      
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            serverCorrelationId = requestStateObject.getServerCorrelationId();            
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
      
            }
            
            @Override
            public void getCorrelationId(String correlationID) {
                
            }


        });

```
   <a name="request-state"></a>
   ### Poll to Determine the Request State
   ````
 
    SDKManager.merchantPayment.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                 transactionRef = requestStateObject.getObjectReference();
      
            }
            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }
              @Override
            public void getCorrelationId(String correlationID) {
               
            }


        });
  
  ````
  <a name="view-transaction"></a>
  ### Retrieve a Transaction

  ```
   

      SDKManager.merchantPayment.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
    
            }

            @Override
            public void onTransactionSuccess(TransactionRequest transactionRequest) {
         
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
   
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
Obtain Authorization code to perform merchant payment,These scenario can be achieved by passing account identifiers to a function

Create account identifier before creating the authorisation code


```
private void createAccountIdentifier(){

        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);


    }
```

```
     SDKManager.merchantPayment.createAuthorisationCode(identifierArrayList,NotificationMethod.POLLING,"", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
               
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
             
            }
             @Override
            public void getCorrelationId(String correlationID) {
               
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
   SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
          
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
            
            }
            @Override
            public void getCorrelationId(String correlationID) {
               
            }
      });

```
<a name="merchant-pay-refund"></a>

# Merchant Payment Refund

Merchants can issue a refund to payers. Create transcation object for refund

```
private TransactionRequest transactionRequest;
private String serverCorrelationId="";

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

 SDKManager.merchantPayment.createRefundTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            serverCorrelationId = requestStateObject.getServerCorrelationId();
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
               
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
              
            }
            @Override
            public void getCorrelationId(String correlationID) {
               
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

  SDKManager.merchantPayment.createReversal(NotificationMethod.POLLING,"","Place your Reference id", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                      serverCorrelationId = requestStateObject.getServerCorrelationId();

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
             
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }
            @Override
            public void getCorrelationId(String correlationID) {
               
            }
        });

```

<a name="merchant-pay-balance"></a>

# Merchant Payment Balance

Obtain the balance of requested account,Pass the account identier list  to the function to retrieve the balance details

```
    private void createAccountIdentifier(){
        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);
    }

```

```

 SDKManager.merchantPayment.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onBalanceSuccess(Balance balance, String correlationID) {
       
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
              
            }
        });

```
<a name="merchant-pay-retrieve"></a>

# Retrieve Payments Merchant

Merchant can retrieve all transaction details

```
   private void createAccountIdentifier(){
        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);
    }

```

```

 SDKManager.merchantPayment.viewAccountTransactions(identifierArrayList, 0, 2, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
            
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction) {
           
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
         
            }
        });
 
 
 

```
<a name="check-for-service"></a>

# Check for Service Availability-Merchant Payment

The application should perform service availabilty check before calling the payment scenarios

    private void checkServiceAvailability() {
        SDKManager.merchantPayment.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
          
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
              
            }
        });
     }

<a name="missing-response"></a>
# Retrieve a Missing API Response

Merchant to retrieve a link to the final representation of the resource for which it attempted to create. Use this API when a callback is not received from the mobile money provider.

## 1.Missing Transaction Response

```
SDKManager.merchantPayment.viewTransactionResponse(correlationId, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject) {
              
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
   

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }

        });

```
### 2.Missing Authorization code response-Merchant Payment

```

 SDKManager.merchantPayment.viewAuthorisationCodeResponse(correlationId, new AuthorisationCodeInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCode) {

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
<a name="individual"></a>

# Individual Disbursement

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

   SDKManager.disbursement.createDisbursementTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
             serverCorrelationId = requestStateObject.getServerCorrelationId();
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
     
            }



```
<a name="individual-polling"></a>

# Individual Disbursement Using the Polling Method

The individual disbursement using polling method can be completed using the following function calls

An asynchronous payment flow is used with the polling method. The client polls against the request state object to determine the outcome of the payment request.These payment flow can achieved using the following API

* Disbursement Request<br />
* Poll to Determine the Request State<br />
* Retrieve a Transaction<br />


<a name="disbursement-request"></a>
                               
### Disbursement Request

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
    
      SDKManager.disbursement.createDisbursementTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
             serverCorrelationId = requestStateObject.getServerCorrelationId();
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
     
            }
              @Override
            public void getCorrelationId(String correlationID) {
               
            }
          }
    

```
   <a name="disbursement-request-state"></a>                                                                                      
   ### Poll to Determine the Request State
   ````
   SDKManager.disbursement.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                transactionRef = requestStateObject.getObjectReference();
         }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

        });
  ````
  
 <a name="disbursement-view-transaction"></a>      
 
  ### Retrieve a Disbursment Transaction
                                                                                
                                     
  ```
      SDKManager.disbursement.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
    
            }

            @Override
            public void onTransactionSuccess(TransactionRequest transactionRequest) {
         
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
   
            }

        });
   
   
  ```
  <a name="bulk-disbursement"></a>
                             
  # Bulk Disbursment
  
  The bulk disbursment use case consist of  follwing  request
      
 * Perform a Bulk Disbursement<br />
 * Retrieve Batch Transactions that have Completed<br />
 * Retrieve Batch Transactions that have been Rejected<br />
  
  
  <a name="bulk-disbursement-pay"></a>
  ### Perform a  bulk Disbursment
  
  Create a bulk Transaction Object before performing the bulk disbursement
  
  ```
  private BulkTransactionObject bulkTransactionObject;
  
  ```
  
  ```
    private void createBulkTransactionObject() {
        bulkTransactionObject = new BulkTransactionObject();

        ArrayList<TransactionItem> transactionItems = new ArrayList<>();
        TransactionItem transactionItem = new TransactionItem();
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("Place debit party account id here");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("Place credit party account id here");
        creditPartyList.add(creditPartyItem);

        transactionItem.setAmount("Place your amount here");//amount
        transactionItem.setType("transfer");
        transactionItem.setCurrency("RWF");//country code
        transactionItem.setCreditParty(creditPartyList);
        transactionItem.setDebitParty(debitPartyList);
        transactionItems.add(transactionItem);
        transactionItems.add(transactionItem);

        bulkTransactionObject.setBatchDescription("Testing a Batch transaction");
        bulkTransactionObject.setBatchTitle("Batch Test");
        bulkTransactionObject.setScheduledStartDate("2019-12-11T15:08:03.158Z");//scheduled time
        bulkTransactionObject.setTransactions(transactionItems);

    }
```
Perform the bulk transaction using the following code

```
   SDKManager.disbursement.createBatchTransaction(NotificationMethod.POLLING,"",bulkTransactionObject, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
           
             }
             @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
         
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
           
            }
            @Override
            public void getCorrelationId(String correlationID) {
               
            }

        });
  
 ``` 
 
   <a name="bulk-disbursement-completed"></a>

  ### Retrieve Batch Transactions that have Completed
  
  This use case allows the disbursement organisation to retrieve all completed transactions for a given batch.
  
  Pass the batch id as first parameter of function obtained from result callback of the bulk disbursment request(# 1) to a function to retrieve the completed 
  
  ```
  
   SDKManager.disbursement.retrieveBatchCompletions("Place your batch id here", new BatchCompletionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
              
            }

            @Override
            public void batchTransactionCompleted(BatchTransactionCompletion batchTransactionCompletion, String correlationID) {
           
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
      
            }
        });
  
  ```
  

<a name="bulk-disbursement-rejected"></a>

## Retrieve Batch Transactions that have been Rejected

This use case allows the disbursement organisation to retrieve all rejected transactions for a given batch

```
 SDKManager.disbursement.viewBatchRejections("Place your batch id here", new BatchRejectionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }

            @Override
            public void batchTransactionRejections(BatchTransactionRejection batchTransactionRejection, String correlationID) {
      
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
              
            }
        });
  
```
<a name="bulk-maker-checker"></a>

# Bulk Disbursement with maker/checker

  The bulk disbursment with maker/checkeer use case consist of  following  request<br />
      
  * Perform a Bulk Disbursement <br />
  * Retrieve Batch Transactions that have Completed <br />
  * Retrieve Batch Transactions that have been Rejected <br />
  * Approve Batch request <br />
  * Retrieve the batch request <br />
    
    
perfom the step 1 to step 3 which is already mentioned in bulk disbursment use cases     
   
<a name="bulk-disbursement-approve"></a>
   
## Approve the batch request

```
private ArrayList<Batch> batchArrayList;

```
    
Create a batch array with following object

```
 private void createBatchRequestObject(){
        //create a batch object
        Batch batchObject = new Batch();
        batchObject.setOp("replace");
        batchObject.setPath("/batchStatus");
        batchObject.setValue("approved");
        batchArrayList=new ArrayList<>();
        batchArrayList.add(batchObject);
    }
```
Call the update batch request function with batch id and batch array as input parameter

```

SDKManager.disbursement.updateBatchTransaction(NotificationMethod.POLLING,"","Place your batch id here",batchArrayList, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
     
            }

           @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
             
            }
            @Override
            public void getCorrelationId(String correlationID) {
               
            }

        });


```
Retrieve the details of batch request

<a name="bulk-disbursement-retrieve"></a>

## Retrieve the batch request

```

SDKManager.disbursement.viewBatchTransaction(transactionRef, new BatchTransactionItemInterface() {
                @Override
                public void batchTransactionSuccess(BatchTransactionItem batchTransactionItem, String correlationID) {
                   
                }

                @Override
                public void onTransactionFailure(GSMAError gsmaError) {
         
                }

                @Override
                public void onValidationError(ErrorObject errorObject) {
              
                }
            });


```

<a name="disbursement-pay-reversal"></a>

#  Payment Reversal-Disbursement

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

  SDKManager.disbursement.createReversal(NotificationMethod.POLLING,"","Place your Reference id", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                      serverCorrelationId = requestStateObject.getServerCorrelationId();

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
             
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }
            @Override
            public void getCorrelationId(String correlationID) {
               
            }
            
        });

```

<a name="disbursement-pay-balance"></a>

# Payment Balance-Disbursement

Obtain the balance of requested account,Pass the account identier list  to the function to retrieve the balance details

```
    private void createAccountIdentifier(){
        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);
    }

```

```

 SDKManager.disbursement.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onBalanceSuccess(Balance balance) {
       
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
              
            }
        });

```
<a name="disbursement-pay-retrieve"></a>

# Retrieve Payments-Disbursement

Merchant can retrieve all transaction details

```
   private void createAccountIdentifier(){
        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);
    }

```

```

 SDKManager.disbursement.viewAccountTransactions(identifierArrayList, 0, 2, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
            
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction) {
           
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
         
            }
        });
 
 
 

```
<a name="check-for-service-disbursement"></a>

# Check for Service Availability-Disbursement

The application should perform service availabilty check before calling the payment scenarios

    private void checkServiceAvailability() {
        SDKManager.disbursement.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
          
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
              
            }
        });
     }

<a name="missing-response-disbursement"></a>
# Retrieve a Missing API Response-Disbursement

Merchant to retrieve a link to the final representation of the resource for which it attempted to create. Use this API when a callback is not received from the mobile money provider.

## 1.Missing Transaction Response

```
SDKManager.disbursement.viewTransactionResponse(correlationId, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject) {
              
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
   

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }

        });

```


<a name="international-transfer"></a>

# International Transfers

The International Transfer Mobile Money APIs allow financial service providers to perform cross-border mobile money transfers, including remittances.

<a name="international-transfer-feature"></a>

# International Transfer via Hub/Bilateral International Transfer

The internation transfer via hub/bilateral international transfer uses case consist of following functionalities

* Request a International Transfer Quotation<br />
* Perform an International Transfer<br />


<a name="international-transfer-quotation"></a>

# Request a International Transfer Quotation

```
private TransactionRequest transactionRequest

```
Initlialise the transaction request obejct before calling the request quotation function
```

 transactionRequest=new TransactionRequest();

        //create debit party and credit party for internal transfer quotation
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        //debit party
        debitPartyItem.setKey("walletid");
        debitPartyItem.setValue("1");
        debitPartyList.add(debitPartyItem);

        //credit party
        creditPartyItem.setKey("msisdn");
        creditPartyItem.setValue("+44012345678");
        creditPartyList.add(creditPartyItem);

        //add debit and credit party to transaction object
        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);


        //set amount,currency and request date into transaction request
        transactionRequest.setRequestAmount("75.30");
        transactionRequest.setRequestCurrency("RWF");
        transactionRequest.setRequestDate("2018-07-03T11:43:27.405Z");
        transactionRequest.setType("inttransfer");
        transactionRequest.setSubType("abc");
        transactionRequest.setChosenDeliveryMethod("agent");

        //sender kyc object
        SenderKyc senderKyc=new SenderKyc();
        senderKyc.setNationality("GB");
        senderKyc.setDateOfBirth("1970-07-03T11:43:27.405Z");
        senderKyc.setOccupation("manager");
        senderKyc.setEmployerName("MFX");
        senderKyc.setContactPhone("447125588999");
        senderKyc.setGender("m"); // m or f
        senderKyc.setEmailAddress("luke.skywalkeraaabbb@gmail.com");
        senderKyc.setBirthCountry("GB");

        // create object for documentation
        ArrayList<IdDocumentItem> idDocumentItemList=new ArrayList<>();
        IdDocumentItem idDocumentItem=new IdDocumentItem();
        idDocumentItem.setIdType("nationalidcard");
        idDocumentItem.setIdNumber("1234567");
        idDocumentItem.setIssueDate("2018-07-03T11:43:27.405Z");
        idDocumentItem.setExpiryDate("2021-07-03T11:43:27.405Z");
        idDocumentItem.setIssuer("UKPA");
        idDocumentItem.setIssuerPlace("GB");
        idDocumentItem.setIssuerCountry("GB");
        idDocumentItem.setOtherIdDescription("test");

        idDocumentItemList.add(idDocumentItem);

        //add document details to kyc object
        senderKyc.setIdDocument(idDocumentItemList);

        //create object for postal address
        PostalAddress postalAddress=new PostalAddress();
        postalAddress.setCountry("GB");
        postalAddress.setAddressLine1("111 ABC Street");
        postalAddress.setCity("New York");
        postalAddress.setStateProvince("New York");
        postalAddress.setPostalCode("ABCD");

        //add postal address to kyc object
        senderKyc.setPostalAddress(postalAddress);

        //create subject model

        SubjectName subjectName=new SubjectName();
        subjectName.setTitle("Mr");
        subjectName.setFirstName("Luke");
        subjectName.setMiddleName("R");
        subjectName.setLastName("Skywalker");
        subjectName.setFullName("Luke R Skywalker");
        subjectName.setNativeName("ABC");

        //add  subject to kyc model

        senderKyc.setSubjectName(subjectName);

        //create array for custom data items
        ArrayList<CustomDataItem> customDataItemList=new ArrayList<>();

        // create a custom data item
        CustomDataItem customDataItem=new CustomDataItem();
        customDataItem.setKey("keytest");
        customDataItem.setValue("keyvalue");

        //add custom object into custom array
        customDataItemList.add(customDataItem);

        //add kyc object to request object
        transactionRequest.setSenderKyc(senderKyc);

        //add custom data object to request object
        transactionRequest.setCustomData(customDataItemList);

        transactionRequest.setSendingServiceProviderCountry("AD");
        transactionRequest.setOriginCountry("AD");
        transactionRequest.setReceivingCountry("AD");
        
        
   ```     

Request a quotation to perform international transfer with transaction request object as input paramater

```


  SDKManager.internationalTransfer.createQuotation(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
              

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
          
            }
            @Override
            public void getCorrelationId(String correlationID) {
               
            }
        });


```
<a name="international-transfer-inititate"></a>
## Perform an International Transfer<br />

To perform international request add the international transfer information,amount and currency to existing request object

```
           //set amount and currency
            transactionRequest.setAmount("100");
            transactionRequest.setCurrency("GBP");

            //create international information object to perform international transfer

            InternationalTransferInformation internationalTransferInformation = new InternationalTransferInformation();
            internationalTransferInformation.setOriginCountry("GB");
            internationalTransferInformation.setQuotationReference("REF-1636521507766");
            internationalTransferInformation.setQuoteId("REF-1636521507766");
            internationalTransferInformation.setReceivingCountry("RW");
            internationalTransferInformation.setRemittancePurpose("personal");
            internationalTransferInformation.setRelationshipSender("none");
            internationalTransferInformation.setDeliveryMethod("agent");
            internationalTransferInformation.setSendingServiceProviderCountry("AD");
            transactionRequest.setInternationalTransferInformation(internationalTransferInformation);

            RequestingOrganisation requestingOrganisation = new RequestingOrganisation();
            requestingOrganisation.setRequestingOrganisationIdentifierType("organisationid");
            requestingOrganisation.setRequestingOrganisationIdentifier("testorganisation");

            //add requesting organisation object into transaction request
            transactionRequest.setRequestingOrganisation(requestingOrganisation);


```
Perform international transfer request using transaction request

```
   
 SDKManager.internationalTransfer.createInternationalTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
               

          }

           @Override
            public void onValidationError(ErrorObject errorObject) {
       
            }
              @Override
            public void getCorrelationId(String correlationID) {
               
            }
            
        });

```



<a name="international-pay-reversal"></a>

#  Payment Reversal-International Transfer

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

  SDKManager.internationalTransfer.createReversal(NotificationMethod.POLLING,"","Place your Reference id", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                      serverCorrelationId = requestStateObject.getServerCorrelationId();

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
             
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }
            @Override
            public void getCorrelationId(String correlationID) {
               
            }
        });

```

<a name="international-pay-balance"></a>

# Payment Balance-International Transfer
Obtain the balance of requested account,Pass the account identier list  to the function to retrieve the balance details

```
    private void createAccountIdentifier(){
        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);
    }

```

```

 SDKManager.internationalTransfer.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onBalanceSuccess(Balance balance) {
       
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
              
            }
        });

```
<a name="international-pay-retrieve"></a>

# Retrieve Payments-International Transfer

Merchant can retrieve all transaction details

```
   private void createAccountIdentifier(){
        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);
    }

```

```

 SDKManager.internationalTransfer.viewAccountTransactions(identifierArrayList, 0, 2, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
            
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction) {
           
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
         
            }
        });
 
 
 

```
<a name="check-for-service-international"></a>

# Check for Service Availability-International Transfer

The application should perform service availabilty check before calling the payment scenarios

    private void checkServiceAvailability() {
        SDKManager.internationalTransfer.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
          
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
              
            }
        });
     }

<a name="missing-response-international"></a>
# Retrieve a Missing API Response-International Transfer

Merchant to retrieve a link to the final representation of the resource for which it attempted to create. Use this API when a callback is not received from the mobile money provider.

## 1.Missing Transaction Response

```
SDKManager.internationalTransfer.viewTransactionResponse(correlationId, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject) {
              
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
   

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }

        });

```





<a name="p2p-switch"></a>


# P2p Transfer via switch/On-us’ P2P Transfer Initiated by a Third Party Provider

A switch is used to send FSP to<br /> 


* confirm the recipient name<br />
* request a quotation<br /> 
* perform the transfer with the receiving FSP.


<a name="p2p-name"></a>

## Confirm the recipient name


```
    ArrayList<Identifier> identifierArrayList;


```

Create identifier to get the name of recipient
```

    private void createAccountIdentifier() {

        identifierArrayList = new ArrayList<>();
        identifierArrayList.clear();
        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("1");
        identifierArrayList.add(identifierAccount);
   }

```

Perform a request to view the account name


```

 SDKManager.p2pTransfer.viewAccountName(identifierArrayList, new AccountHolderInterface() {
            @Override
            public void onRetrieveAccountInfoSuccess(AccountHolderObject accountHolderObject, String correlationID) {
             
           

            @Override
            public void onRetrieveAccountInfoFailure(GSMAError gsmaError) {
        
            }


            @Override
            public void onValidationError(ErrorObject errorObject) {
          
            }

        });

```

<a name="p2p-quotation"></a>

## Request a quotation


```
private TransactionRequest transactionRequest;
private String serverCorrelationId;

```

 private void createP2PQuotationObject() {

        transactionRequest = new TransactionRequest();

        //create debit party and credit party for P2P transfer quotation
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        //debit party
        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("2999");
        debitPartyList.add(debitPartyItem);

        //credit party
        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("2000");
        creditPartyList.add(creditPartyItem);

        //add debit and credit party to transaction object
        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);


        //set amount,currency and request date into transaction request
        transactionRequest.setRequestAmount("75.30");
        transactionRequest.setRequestCurrency("RWF");
        transactionRequest.setRequestDate("2018-07-03T11:43:27.405Z");
        transactionRequest.setType("transfer");
        transactionRequest.setSubType("abc");
        transactionRequest.setChosenDeliveryMethod("directtoaccount");

        //create array for custom data items
        ArrayList<CustomDataItem> customDataItemList = new ArrayList<>();

        // create a custom data item
        CustomDataItem customDataItem = new CustomDataItem();
        customDataItem.setKey("keytest");
        customDataItem.setValue("keyvalue");

        //add custom object into custom array
        customDataItemList.add(customDataItem);

        //add custom data object to request object
        transactionRequest.setCustomData(customDataItemList);

        //request for quotation
        requestQuotation();

    }

```
 SDKManager.p2pTransfer.createQuotation(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
  
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
        

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
     
            }
              @Override
            public void getCorrelationId(String correlationID) {
               
            }
        });


```


<a name="p2p-transfer-fsp"></a>

## perform the transfer with the receiving FSP.

```

    private TransactionRequest transactionRequest;


```

Create p2p Transfer object

```
  private void createP2PTransferObject() {
     if (transactionRequest == null) {
            Utils.showToast(this, "Please request Quotation before performing this request");
            return;
        } else {

            //set amount and currency
            transactionRequest.setAmount("100");
            transactionRequest.setCurrency("GBP");

            ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
            ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
            DebitPartyItem debitPartyItem = new DebitPartyItem();
            CreditPartyItem creditPartyItem = new CreditPartyItem();

            //debit party
            debitPartyItem.setKey("accountid");
            debitPartyItem.setValue("2999");
            debitPartyList.add(debitPartyItem);

            //credit party
            creditPartyItem.setKey("accountid");
            creditPartyItem.setValue("2000");
            creditPartyList.add(creditPartyItem);

            //create international information object to perform international transfer

            InternationalTransferInformation internationalTransferInformation = new InternationalTransferInformation();
            internationalTransferInformation.setOriginCountry("AD");
            internationalTransferInformation.setQuotationReference("REF-1636521507766");
            internationalTransferInformation.setQuoteId("REF-1636521507766");
            internationalTransferInformation.setRemittancePurpose("personal");
            internationalTransferInformation.setDeliveryMethod("agent");
            transactionRequest.setInternationalTransferInformation(internationalTransferInformation);

            RequestingOrganisation requestingOrganisation = new RequestingOrganisation();
            requestingOrganisation.setRequestingOrganisationIdentifierType("organisationid");
            requestingOrganisation.setRequestingOrganisationIdentifier("testorganisation");

            //add requesting organisation object into transaction request
            transactionRequest.setRequestingOrganisation(requestingOrganisation);

            //add debit and credit party to transaction object
            transactionRequest.setDebitParty(debitPartyList);
            transactionRequest.setCreditParty(creditPartyList);

            performTransfer();
        }
    }

```

```
 private void performTransfer() {
    SDKManager.getInstance().createTransferTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                   serverCorrelationId = requestStateObject.getServerCorrelationId();
              }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
            
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
        
            }
            @Override
            public void getCorrelationId(String correlationID) {
               
            }
        });

```

<a name="p2p-bilateral"></a>
## Bilateral P2P Transfer

The bilateral P2P transfer can be perfomed using following use cases

* confirm the recipient name<br />
* perform the transfer with the receiving FSP

<a name="p2p-pay-balance"></a>
# Payment Balance-P2P Transfer
Obtain the balance of requested account,Pass the account identier list  to the function to retrieve the balance details

```
    private void createAccountIdentifier(){
        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);
    }

```

```

 SDKManager.p2pTransfer.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onBalanceSuccess(Balance balance, String correlationID) {
       
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
              
            }
        });

```
<a name="p2p-pay-retrieve"></a>

# Retrieve Payments - P2P Transfer

Merchant can retrieve all transaction details

```
   private void createAccountIdentifier(){
        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);
    }

```

```

 SDKManager.p2pTransfer.viewAccountTransactions(identifierArrayList, 0, 2, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
            
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction) {
           
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
         
            }
        });
 
 
 

```
<a name="check-for-service-p2p"></a>

# Check for Service Availability - P2P Transfer

The application should perform service availabilty check before calling the payment scenarios

    private void checkServiceAvailability() {
        SDKManager.p2pTransfer.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
          
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
              
            }
        });
     }

<a name="missing-response-p2p"></a>
# Retrieve a Missing API Response - P2p Transfer

Merchant to retrieve a link to the final representation of the resource for which it attempted to create. Use this API when a callback is not received from the mobile money provider.

## 1.Missing Transaction Response

```
SDKManager.p2pTransfer.viewTransactionResponse(correlationId, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject) {
              
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
   

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }

        });

```
<a name="recurring-payments"></a>

# Recurring Payments
 
 The Recurring Payments Mobile Money APIs allow service providers to setup electronic payment mandates for mobile money customers and initiate payments against payment   mandates.

<a name="recurring-setup"></a>

# Set up a Recurring Payment

In this use case the setup for the recurring payment is done using debit mandate,The service provider initiates the request which is authorised by the account holding customer. 

 * Create Account Debit Mandate 

<a name="recurring-mandate">
   
# Create Account Debit Mandate 
   
```
    private String serverCorrelationId;
```   
```
   private void createAccountIdentifier() {
        identifierArrayList = new ArrayList<>();
        identifierArrayList.clear();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");

        identifierArrayList.add(identifierAccount);
    }
```   
   
```
 SDKManager.recurringPayment.createAccountDebitMandate(NotificationMethod.POLLING, "", identifierArrayList, debitMandateRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
             
                serverCorrelationId = requestStateObject.getServerCorrelationId();
          
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
             
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
            
            }
             @Override
            public void getCorrelationId(String correlationID) {
               
            }
        });
   
```
<a name="recurring-take"></a>
   
 # Take a Recurring Payment
   
   Perform a recurring payment consist of following scenarios 
   
 * Create Account Debit Mandate
 * View Account Debit Mandate
 * Perform Merchant Transaction using Debit Mandate

 <a name="recurring-view-debit"></a>
   
## View Account Debit Mandate
   
```
private String debitMandateReference; 
```   
   
 ```
     SDKManager.recurringPayment.viewAccountDebitMandate(identifierArrayList, transactionRef, new DebitMandateInterface() {
            @Override
            public void onDebitMandateSuccess(DebitMandate debitMandate) {
                debitMandateReference=debitMandate.getMandateReference();
            }

            @Override
            public void onDebitMandateFailure(GSMAError gsmaError) {
       
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }
             
        });

   
   
 ```
<a name="recurring-merchant"></a>
   
## Perform Merchant Transaction using Debit Mandate

```
   private TransactionRequest transactionRequest;
   
```   
   
   
```
    private void createTransactionObject(){
        transactionRequest=new TransactionRequest();
        transactionRequest.setAmount("200");
        transactionRequest = new TransactionRequest();

        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("mandatereference");
        debitPartyItem.setValue(debitMandateReference);
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("2999");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("200.00");
        transactionRequest.setCurrency("RWF");

        initiateMerchantPayment();
   }
   
 ```
   
 ```  
   private void initiateMerchantPayment(){
        SDKManager.recurringPayment.createMerchantTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
         
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
             
            }
            @Override
            public void getCorrelationId(String correlationID) {
               
            }

        });
    }
   
 ```  
   <a name="take-recurring"></a>
# Take a Recurring Payment using the Polling Method
   
 The following are usecase scenario for polling method
   
 * Perform Merchant Transaction using Debit Mandate
 * Poll to Determine the Request State 
 * Retrieve a Transaction 
  
  <a name="request-state-recurring"></a>

  ### Poll to Determine the Request State
   
 ````
   SDKManager.recurringPayment.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                 transactionRef = requestStateObject.getObjectReference();
      
            }
            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

        });
  ````   
 
   
  <a name="view-transaction-recurring"></a>

  ### Retrieve a Transaction

  ```
     SDKManager.recurringPayment.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
    
            }

            @Override
            public void onTransactionSuccess(TransactionRequest transactionRequest) {
         
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
   
            }

        });
   
   ```   
   <a name="recurring-pay-refund"></a>
   
# Recurring Payment Refund

Merchants can issue a refund to payers. Create transcation object for refund

```
private TransactionRequest transactionRequest;
private String serverCorrelationId="";

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

 SDKManager.recurringPayment.createRefundTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            serverCorrelationId = requestStateObject.getServerCorrelationId();
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
               
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
              
            }
            @Override
            public void getCorrelationId(String correlationID) {
               
            }
        });

```
<a name="recurring-pay-reversal"></a>

# Recurring Payment Reversal

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

  SDKManager.recurringPayment.createReversal(NotificationMethod.POLLING,"","Place your Reference id", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                      serverCorrelationId = requestStateObject.getServerCorrelationId();

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
             
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }
            @Override
            public void getCorrelationId(String correlationID) {
               
            }
        });

```
<a name="recurring-pay-balance"></a>

# Payment Balance-Recurring Payment

Obtain the balance of requested account,Pass the account identier list  to the function to retrieve the balance details

```
    private void createAccountIdentifier(){
        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);
    }

```

```

 SDKManager.disbursement.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onBalanceSuccess(Balance balance) {
       
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
              
            }
        });

```
<a name="recurring-pay-retrieve"></a>

# Retrieve Payments-Recurring Payment

Merchant can retrieve all transaction details

```
   private void createAccountIdentifier(){
        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);
    }

```

```

 SDKManager.recurringPayment.viewAccountTransactions(identifierArrayList, 0, 2, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
            
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction) {
           
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
         
            }
        });
 
 
 

```
<a name="check-for-service-recurring"></a>

# Check for Service Availability-Recurring Payments

The application should perform service availabilty check before calling the payment scenarios

    private void checkServiceAvailability() {
        SDKManager.recurringPayment.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
          
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
              
            }
        });
     }

<a name="missing-response-recurring"></a>
   
# Retrieve a Missing API Response-Recurring Payments

Merchant to retrieve a link to the final representation of the resource for which it attempted to create. Use this API when a callback is not received from the mobile money provider.

## 1.Missing Transaction Response

```
SDKManager.recurringPayment.viewTransactionResponse(correlationId, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject) {
              
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
   

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }

        });

```
   
   

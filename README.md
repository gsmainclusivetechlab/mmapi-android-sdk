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
      3. [Bulk Disbursement](#bulk-disbursement)
      4. [Bulk Disbursement with maker/checker](#bulk-maker-checker)
      5. [Disbursement Reversal](#merchant-pay-reversal)
      6. [Organizational balance](#merchant-pay-balance)
      7. [Retrieve Transactions for a Disbursement Organisation](#merchant-pay-retrieve)
      8. [Check for Service Availability](#check-for-service)
      9. [Retrieve a Missing API Response](#missing-response)
      
   3. [International Transfers](#international-transfer)
       1. [International Transfer via Hub](#international-transfer-feature)
       2. [Bilateral International Transfer](#international-transfer-feature)
       3. [International Transfer Reversal](#merchant-pay-reversal)
       4. [Obtain an FSP Balance](#merchant-pay-balance)
       5. [Retrieve Transactions for an FSP](#merchant-pay-retrieve)
       6. [Check for Service Availability](#check-for-service)
       7. [Retrieve a Missing API Response](#missing-response)

       
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

Copy the GSMASdk-debug-1.0.1.aar file, available in the latest version in aar folder in the project directory, into libs folder under your project directory.

Add the below line in dependencies of your build.gradle file in your application.

```
implementation files('libs/GSMASdk-debug-1.0.1.aar')
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
  
    SDKManager.getInstance().createMerchantTransaction(NotificationMethod.CALLBACK,"",transactionRequest, new RequestStateInterface() {
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

 1.Payee Initiated Merchant Payment<br />
 2.Poll to Determine the Request State<br />
 3.Retrieve a Transaction<br />


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
  SDKManager.getInstance().createMerchantTransaction(NotificationMethod.CALLBACK,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
      
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
            serverCorrelationId = requestStateObject.getServerCorrelationId()            
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
   

      SDKManager.getInstance().viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
    
            }

            @Override
            public void onTransactionSuccess(TransactionRequest transactionRequest, String correlationID) {
         
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
   
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
 Initiate the merchant pay request using the following code


```
 SDKManager.getInstance().createMerchantTransaction(NotificationMethod.CALLBACK,"",transactionRequest, new RequestStateInterface() {
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
     SDKManager.getInstance().createAuthorisationCode(identifierArrayList,NotificationMethod.POLLING,"", authorisationCodeRequest, new RequestStateInterface() {
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
   SDKManager.getInstance().initiateMerchantPayment(transactionRequest, new RequestStateInterface() {
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

 SDKManager.getInstance().createRefundTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
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

# Payment Reversal

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

  SDKManager.getInstance().createReversal(NotificationMethod.POLLING,"","Place your Reference id", reversalObject, new RequestStateInterface() {
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

# Balance

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

 SDKManager.getInstance().viewAccountBalance(identifierArrayList, new BalanceInterface() {
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

# Retrieve Payments

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

 SDKManager.getInstance().viewAccountTransactions(identifierArrayList, 0, 2, new RetrieveTransactionInterface() {
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
        SDKManager.getInstance().viewServiceAvailability(new ServiceAvailabilityInterface() {
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
     }

<a name="missing-response"></a>
# Retrieve a Missing API Response

Merchant to retrieve a link to the final representation of the resource for which it attempted to create. Use this API when a callback is not received from the mobile money provider.

## 1.Missing Transaction Response

```
SDKManager.getInstance().viewTransactionResponse(correlationId, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject, String correlationId) {
              
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

 SDKManager.getInstance()..viewAuthorisationCodeResponse(correlationId, new AuthorisationCodeInterface() {
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

   SDKManager.getInstance().createDisbursementTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
             
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
     
            }



```
<a name="individual-polling"></a>

# Individual Disbursement Using the Polling Method

The individual disbursement using polling method can be completed using the following function calls

An asynchronous payment flow is used with the polling method. The client polls against the request state object to determine the outcome of the payment request.These payment flow can achieved using the following API

1.Disbursement Request<br />
2.Poll to Determine the Request State<br />
3.Retrieve a Transaction<br />


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
    
      SDKManager.getInstance().createDisbursementTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
             
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
     
            }
    

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
   
      SDKManager.getInstance().viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
    
            }

            @Override
            public void onTransactionSuccess(TransactionRequest transactionRequest, String correlationID) {
         
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
   
            }

        });
   
   
  ```
  <a name="bulk-disbursement"></a>
  # Bulk Disbursment
  
  The bulk disbursment use case consist of  follwing  request
      
 1.Perform a Bulk Disbursement<br />
 2.Retrieve Batch Transactions that have Completed<br />
 3.Retrieve Batch Transactions that have been Rejected<br />
  
  
  ## 1.Perform a  bulk Disbursment
  
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
   SDKManager.getInstance().createBatchTransaction(NotificationMethod.POLLING,"",bulkTransactionObject, new RequestStateInterface() {
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
 
  ## 2.Retrieve Batch Transactions that have Completed
  
  This use case allows the disbursement organisation to retrieve all completed transactions for a given batch.
  
  Pass the batch id as first parameter of function obtained from result callback of the bulk disbursment request(# 1) to a function to retrieve the completed 
  
  ```
  
   SDKManager.getInstance().retrieveBatchCompletions("Place your batch id here", new BatchCompletionInterface() {
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
  
 ## 3.Retrieve Batch Transactions that have been Rejected

This use case allows the disbursement organisation to retrieve all rejected transactions for a given batch

```
 SDKManager.getInstance().viewBatchRejections("Place your batch id here", new BatchRejectionInterface() {
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

    The bulk disbursment with maker/checkeer use case consist of  following  request
      
  1.Perform a Bulk Disbursement <br />
  2.Retrieve Batch Transactions that have Completed <br />
  3.Retrieve Batch Transactions that have been Rejected <br />
  4.Approve Batch request <br />
  5.Retrieve the batch request <br />
    
    
perfom the step 1 to step 3 which is already mentioned in bulk disbursment use cases     
    
## 4.Approve the batch request

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

SDKManager.getInstance().updateBatchTransaction(NotificationMethod.POLLING,"","Place your batch id here",batchArrayList, new RequestStateInterface() {
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
Retrieve the details of batch request

## 5.Retrieve the batch request

```

SDKManager.getInstance().viewBatchTransaction(transactionRef, new BatchTransactionItemInterface() {
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
<a name="international-transfer"></a>

# International Transfers

The International Transfer Mobile Money APIs allow financial service providers to perform cross-border mobile money transfers, including remittances.

<a name="international-transfer-feature"></a>

# International Transfer via Hub/Bilateral International Transfer

The internation transfer via hub/bilateral international transfer uses case consist of following functionalities

1.Request a International Transfer Quotation<br />
2.Perform an International Transfer<br />

## 1.Request a International Transfer Quotation

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


  SDKManager.getInstance().createQuotation(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
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
## 2.Perform an International Transfer<br />

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

 SDKManager.getInstance().createInternationalTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
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





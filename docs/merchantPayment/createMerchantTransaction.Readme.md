# Create A MerchantPay Transaction

`createMerchantTransaction() creates a POST request to /transactions/type/merchantpay`

> `Provided with a valid object representation, this endpoint allows for a new transaction to be created for a given transaction type 'merchantpay' passed via the URL.`



```java

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
  
 
```

 Initiate the mechant pay request using the following code


```java

   SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod,"CALLBACK URL",transactionRequest, new RequestStateInterface() {
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

### Example Output - Callback

```json
202
{
  "serverCorrelationId": "2e9ce6e1-8bbc-4938-9274-418c28e78f80",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "9122",
  "pollLimit": 100
}
```

### Example Output - Polling

```json
202
{
  "serverCorrelationId": "2e9ce6e1-8bbc-4938-9274-418c28e78f80",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "9122",
  "pollLimit": 100
}
```

### NOTE

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state.
Use the <a href="viewRequestState.Readme.md">viewRequestState()</a> function for the polling mechanism to receive the status of a request, and the <a href="viewTransaction.Readme.md">viewTransaction()</a>
function to acquire the final representation of the Transaction object.


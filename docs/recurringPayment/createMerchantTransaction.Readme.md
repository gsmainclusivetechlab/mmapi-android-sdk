
# Create A MerchantPay Transaction

`Here, createMerchantTransaction() creates a POST request to /transactions/type/merchantpay`

> `Provided with a valid object representation, this endpoint allows for a new transaction to be created for a given transaction type 'merchantpay' passed via the URL.`
### Usage/Examples

```java

        transactionRequest = new Transaction();
        transactionRequest.setAmount("200");
        transactionRequest = new Transaction();

        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

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


   SDKManager.recurringPayment.createMerchantTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
              
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
             
              
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
             }
            

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
           
            }

        });

```

### Example Output - Callback

```java
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

```java
202
{
  "serverCorrelationId": "2e9ce6e1-8bbc-4938-9274-418c28e78f80",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "9122",
  "pollLimit": 100
}
```

---

**NOTE**

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state. Use the [viewRequestState()](viewRequestState.Readme.md) function for the polling mechanism to receive the status of a request, and the [viewTransaction()](viewTransaction.Readme.md) function to acquire the final representation of the Transaction object.

---

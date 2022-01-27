
# Create a Transaction

`Here, createRefundTransaction() creates a POST request to /transactions/type/adjustment`

> `Provided with a valid object representation, this endpoint allows for a new transaction to be created for a given transaction type passed via the URL.`


```java

        Transaction  transactionRequest = new Transaction();
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


```


```java

 SDKManager.recurringPayment.createRefundTransaction(NotificationMethod,"CALLBACK URL",transactionRequest, new RequestStateInterface() {
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

### Example Output - Callback

```json
202
{
  "serverCorrelationId": "233b226e-a2da-48f7-8510-9c79a352906b",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "8182",
  "pollLimit": 100
}
```

### Example Output - Polling

```json
202
{
  "serverCorrelationId": "a0913b62-a7e1-4641-98fa-f93750413600",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "14208",
  "pollLimit": 100
}
```

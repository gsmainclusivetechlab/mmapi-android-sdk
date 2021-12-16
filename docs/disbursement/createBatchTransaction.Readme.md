# Create A Transaction Batch

The function allows for a new transaction batch to be created`


### Usage/Examples

 Create a bulk Transaction Object before performing the bulk disbursement
  
  ```java
  private BulkTransactionObject bulkTransactionObject;
  
  ```
  
  ```java
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

```java

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
 
### Example Output - Callback

```json
202
{
  "serverCorrelationId": "8a8e8d59-8f3b-40a7-b9cc-1fdb63358a75",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "429",
  "pollLimit": 100
}
```

### Example Output - Polling

```javascript
202
{
  "serverCorrelationId": "f91e19ec-5116-4491-a447-11cfc2bc7f93",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "920",
  "pollLimit": 100
}
```

**NOTE**

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state. Use the [viewRequestState()](viewRequestState.Readme.md) function for the polling mechanism to receive the status of a request, and the [viewTransaction()](viewTransaction.Readme.md) function to acquire the final representation of the Transaction object.

---

# Create a Transaction

`Here, createDisbursementTransaction() creates a POST request to /transactions/type/disbursement`

> `Provided with a valid object representation, this endpoint allows for a new transaction to be created for a given transaction type passed via the URL.`


### Usage/Examples

A transaction object is to be created before calling the payee-initiated merchant payment,The example for transaction object as follows


```java
private Transaction transactionRequest;
private String serverCorrelationId = "";
private String transactionRef = "";
```

```java

        transactionRequest = new Transaction();
        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();

        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("2999");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("2999");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("200.00");
        transactionRequest.setCurrency("RWF");

   

```
 Initiate the mechant pay request using the following code

```java
    
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



### Example Output - Callback

```json
202
{
  "serverCorrelationId": "208108a9-18f7-4b11-8c50-cbb13e25c39d",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "8331",
  "pollLimit": 100
}
```

### Example Output - Polling

```json
202
{
  "serverCorrelationId": "208108a9-18f7-4b11-8c50-cbb13e25c39d",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "8331",
  "pollLimit": 100
}

```
### NOTE

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state.
Use the <a href="viewRequestState.Readme.md">viewRequestState()</a> function for the polling mechanism to receive the status of a request, and the <a href="viewTransaction.Readme.md">viewTransaction()</a>
function to acquire the final representation of the Transaction object.



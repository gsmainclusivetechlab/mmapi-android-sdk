# Create a Transaction

`Here, createTransferTransaction() creates a POST request to /transactions/type/transfer`

> `Provided with a valid object representation, this endpoint allows for a new transaction to be created for a given transaction type passed via the URL.`
### Usage/Examples

```java
        Transaction  transactionRequest = new Transaction();
        //set amount and currency
        transactionRequest.setAmount("200");
        transactionRequest.setCurrency("RWF");

        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

        //debit party
        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("2999");
        debitPartyList.add(debitPartyItem);

        //credit party
        creditPartyItem.setKey("linkref");
        creditPartyItem.setValue(linkReference);
        creditPartyList.add(creditPartyItem);

        //add debit and credit party to transaction object
        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);


```

```java

  SDKManager.accountLinking.createTransferTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
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
 

        });

```

### Response Example

```java
{
  "serverCorrelationId": "1d477bf9-63d6-4f24-82fd-184689a4cb05",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "19035",
  "pollLimit": 100
}
```

### NOTE

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state.
Use the <a href="viewRequestState.Readme.md">viewRequestState()</a> function for the polling mechanism to receive the status of a request, and the <a href="viewTransaction.Readme.md">viewTransaction()</a>
function to acquire the final representation of the Transaction object.

# Create A Disbursement Transaction

> Function allows for a new transaction to be created for a given transaction type 'disbursement' passed via the URL.`

### Usage/Examples

A transaction object is to be created before calling the payee-initiated merchant payment,The example for transaction object as follows


```java
private TransactionRequest transactionRequest;
private String serverCorrelationId = "";
private String transactionRef = "";
```

```java

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

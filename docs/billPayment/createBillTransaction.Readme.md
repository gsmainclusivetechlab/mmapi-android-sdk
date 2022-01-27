 # Create A BillPay Transaction

`createBillTransaction() creates a POST request to /transactions/type/billpay`

> `Provided with a valid object representation, this endpoint allows for a new transaction to be created for a given transaction type 'billpay' passed via the URL.`
### Usage/Examples

```java

        Transaction transactionRequest = new Transaction();
        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();

        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

        debitPartyItem.setKey("walletid");
        debitPartyItem.setValue("1");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("msisdn");
        creditPartyItem.setValue("+44012345678");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("200.00");
        transactionRequest.setCurrency("RWF");



```

```java


   SDKManager.billPayment.createBillTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
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
                correlationId = correlationID;
              
            }

        });
```

### Example Output - Callback

```java

{
	"notificationMethod": "callback",
	"objectReference": "19601",
	"pollLimit": 100,
	"serverCorrelationId": "6f84e8aa-fe20-4da4-add2-7de2dfcb51d7",
	"status": "pending"
}

```

### Example Output - Polling

```java
{
	"notificationMethod": "polling",
	"objectReference": "19601",
	"pollLimit": 100,
	"serverCorrelationId": "6f84e8aa-fe20-4da4-add2-7de2dfcb51d7",
	"status": "pending"
}

```

---

**NOTE**

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state. Use the [viewRequestState()](viewRequestState.Readme.md) function for the polling mechanism to receive the status of a request, and the [viewTransaction()](viewTransaction.Readme.md) function to acquire the final representation of the Transaction object.

---

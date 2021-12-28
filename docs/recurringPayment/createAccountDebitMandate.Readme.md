# Create A Debit Mandate

`Here, createAccountDebitMandate({ identifierType1: identifier1 }) creates a POST request to /accounts/{identifierType}/{identifier}/debitmandates`

> `Provided with a valid object representation, this endpoint allows for a new debit mandate to be created for a specific account where one identifier suffices to uniquely identify an account. Note that the payer account is identified in the path whereas the payee account is identified in the request body.`
`Here, createAccountDebitMandate({ identifierType1: identifier1, identifierType2: identifier2, identifierType3: identifier3 }) creates a POST request to /accounts/{AccountIdentifiers}/debitmandates`

> `Provided with a valid object representation, this endpoint allows for a new debit mandate to be created for a specific account where a single identifier is not sufficient to identify an account. Note that the payer account is identified in the path whereas the payee account is identified in the request body.`
### Usage/Examples

```java

   identifierArrayList = new ArrayList<>();
   identifierArrayList.clear();

   //account id
   Identifier identifierAccount = new Identifier();
   identifierAccount.setKey("accountid");
   identifierAccount.setValue("2000");

   identifierArrayList.add(identifierAccount);


```

```java

        debitMandateRequest = new DebitMandate();

        //payee object of debit mandate
        ArrayList<AccountIdentifier> payeeItemArrayList = new ArrayList<>();

        //payee object
        AccountIdentifier payeeItem = new AccountIdentifier();
        payeeItem.setKey("accountid");
        payeeItem.setValue("2999");
        payeeItemArrayList.add(payeeItem);

        //add items to debit mandate object

        debitMandateRequest.setPayee(payeeItemArrayList);
        debitMandateRequest.setRequestDate("2018-07-03T10:43:27.405Z");
        debitMandateRequest.setStartDate("2018-07-03T10:43:27.405Z");
        debitMandateRequest.setCurrency("GBP");
        debitMandateRequest.setAmountLimit("1000.00");
        debitMandateRequest.setEndDate("2028-07-03T10:43:27.405Z");
        debitMandateRequest.setNumberOfPayments("2");
        debitMandateRequest.setFrequencyType("sixmonths");


        //creating custom data array
        ArrayList<CustomDataItem> customDataItemArrayList = new ArrayList<>();

        CustomDataItem customDataItem = new CustomDataItem();
        customDataItem.setKey("keytest");
        customDataItem.setValue("keyvalue");

        customDataItemArrayList.add(customDataItem);

        debitMandateRequest.setCustomData(customDataItemArrayList);

```

```java

SDKManager.recurringPayment.createAccountDebitMandate(NotificationMethod.POLLING, "", identifierArrayList, debitMandateRequest, new RequestStateInterface() {
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

    }

```

### Example Output - Callback

```java
202
{
  "serverCorrelationId": "f2fbaf72-5ca7-46df-ba9d-e8cda6bd267d",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "153",
  "pollLimit": 100
}
```

### Example Output - Polling

```java
202
{
  "serverCorrelationId": "1bca17a5-fe2f-45cc-87dc-d65502507031",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "152",
  "pollLimit": 100
}
```

---

**NOTE**

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state. Use the [viewRequestState()](viewRequestState.Readme.md) function for the polling mechanism to receive the status of a request, and the [viewAccountDebitMandate()](viewAccountDebitMandate.Readme.md) function to acquire the final representation of the Debit Mandate object.

---

# View A Debit Mandate

`Here, viewAccountDebitMandate({ identifierType1: identifier1 }) creates a GET request to /accounts/{identifierType}/{identifier}/debitmandates/{debitMandateReference}`

> `This endpoint returns a specific debit mandate linked to an account where one identifier suffices to uniquely identify an account. Note that the payer account is identified in the path.`
`Here, viewAccountDebitMandate({ identifierType1: identifier1, identifierType2: identifier2, identifierType3: identifier3 }) creates a GET request to /accounts/{AccountIdentifiers}/debitmandates/{debitMandateReference}`

> `This endpoint returns a specific debit mandate linked to an account where a single identifier is not sufficient to identify an account. Note that the payer account is identified in the path.`
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
  SDKManager.recurringPayment.viewAccountDebitMandate(identifierArrayList, transactionRef, new DebitMandateInterface() {
            @Override
            public void onDebitMandateSuccess(DebitMandate debitMandate) {
     
            
           }

            @Override
            public void onDebitMandateFailure(GSMAError gsmaError) {
 
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }
        });




```

### Example Output

```java
202
{
    "currency": "GBP",
    "amountLimit": "1000.00",
    "startDate": "2018-07-03",
    "endDate": "2028-07-03",
    "numberOfPayments": 2,
    "frequencyType": "sixmonths",
    "mandateStatus": "active",
    "requestDate": "2018-07-03T10:43:27",
    "mandateReference": "REF-1637662586029",
    "creationDate": "2021-11-23T10:16:26",
    "modificationDate": "2021-11-23T10:16:26",
    "payee": [
        {
            "key": "accountid",
            "value": "2999"
        }
    ],
    "customData": [
        {
            "key": "keytest",
            "value": "keyvalue"
        }
    ]
}
```

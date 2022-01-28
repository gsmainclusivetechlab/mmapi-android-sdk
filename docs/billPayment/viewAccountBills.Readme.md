# Retrieve a Set of Bills

1. `viewAccountBills([ identifierType => identifier ]) creates a GET request to /accounts/{identifierType}/{identifier}/bills`

> `This endpoint returns bills linked to an account where one identifier suffices to uniquely identify an account.`
2. `viewAccountBills([ 'identifierType1' => 'identifier1', 'identifierType2' => 'identifier2', 'identifierType3' => 'identifier3' ]) creates a GET request to /accounts/{AccountIdentifiers}/bills`

> `This endpoint returns bills linked to an account where a single identifier is not sufficient to identify an account.`
### Usage/Examples

```java

       ArrayList<Identifier>  identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("1");

        identifierArrayList.add(identifierAccount);


```

```java

        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        SDKManager.billPayment.viewAccountBills(identifierArrayList, transactionFilter, new RetrieveBillPaymentInterface() {
            @Override
            public void onRetrieveBillPaymentSuccess(Bills bills) {
             
            }

            @Override
            public void onRetrieveBillPaymentFailure(GSMAError gsmaError) {
                

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
               

            }
        });


```

### Example Output

```java

 {
	"bills": [{
		"amountDue": "50.00",
		"billReference": "REF-000001",
		"creationDate": "2021-01-17T00:00:00",
		"currency": "GBP",
		"dueDate": "2016-08-02",
		"minimumAmountDue": "0.00",
		"modificationDate": "2021-02-17T00:00:00"
	}, {
		"amountDue": "50.00",
		"billReference": "REF-000004",
		"creationDate": "2021-02-15T11:44:58",
		"currency": "GBP",
		"dueDate": "2016-08-02",
		"minimumAmountDue": "0.00",
		"modificationDate": "2021-02-17T00:00:00"
	}, {
		"amountDue": "50.00",
		"billReference": "REF-000005",
		"creationDate": "2021-02-17T00:00:00",
		"currency": "GBP",
		"customData": [{
			"key": "customKey1",
			"value": "customValue1"
		}, {
			"key": "customKey2",
			"value": "customValue2"
		},  {
			"key": "customKey15",
			"value": "customValue15"
		}, {
			"key": "customKey16",
			"value": "customValue16"
		}, {
			"key": "customKey17",
			"value": "customValue17"
		}, {
			"key": "customKey18",
			"value": "customValue18"
		}, {
			"key": "customKey19",
			"value": "customValue19"
		}, {
			"key": "customKey20",
			"value": "customValue20"
		}],
		"dueDate": "2016-08-02",
		"metadata": [{
			"key": "metadata1",
			"value": "metadataValue1"
		}],
		"minimumAmountDue": "0.00",
		"modificationDate": "2021-02-17T00:00:00"
	}, {
		"amountDue": "50.00",
		"billReference": "REF-000006",
		"creationDate": "2021-02-15T11:44:58",
		"currency": "GBP",
		"customData": [{
			"key": "customKey1",
			"value": "customValue1"
		}],
		"dueDate": "2016-08-02",
		"minimumAmountDue": "0.00",
		"modificationDate": "2021-02-17T00:00:00"
	}, {
		"amountDue": "50.00",
		"billReference": "REF-000007",
		"creationDate": "2021-02-15T11:44:58",
		"currency": "GBP",
		"dueDate": "2016-08-02",
		"minimumAmountDue": "0.00",
		"modificationDate": "2021-02-17T00:00:00"
	}]
}


```

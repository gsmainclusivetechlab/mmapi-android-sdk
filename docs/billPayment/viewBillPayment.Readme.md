# Retrieve Bill Payments for a Given Bill

1. `viewBillPayment() creates a GET request to /accounts/{identifierType}/{identifier}/bills/{billReference}/payments`

> `This endpoint allows for bill payments for a specific account to be returned where a single identifier is not sufficient to identify an account.`

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

        SDKManager.billPayment.viewBillPayment(identifierArrayList, transactionFilter, "REF-000001", new BillPaymentInterface() {
            @Override
            public void onBillPaymentSuccess(BillPayments billPayments) {
              
            }

            @Override
            public void onBillPaymentFailure(GSMAError gsmaError) {
               
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
         
            }
        });

```

### Example Output

```java

 {
 	"billPayments": [{
 		"amountPaid": "0.99",
 		"billPaymentStatus": "unpaid",
 		"creationDate": "2021-02-17T00:00:00",
 		"currency": "GBP",
 		"customerReference": "customer ref 0001",
 		"modificationDate": "2021-02-18T08:20:58",
 		"requestDate": "2021-02-18T08:21:27",
 		"supplementaryBillReferenceDetails": [{
 			"paymentReferenceType": "type 1",
 			"paymentReferenceValue": "value 1"
 		}]
 	}, {
 		"amountPaid": "0.99",
 		"billPaymentStatus": "unpaid",
 		"creationDate": "2021-02-17T00:00:00",
 		"currency": "GBP",
 		"customerReference": "customer ref 0001",
 		"modificationDate": "2021-02-18T08:20:58",
 		"requestDate": "2021-02-18T08:21:27",
 		"supplementaryBillReferenceDetails": [{
 			"paymentReferenceType": "type 1",
 			"paymentReferenceValue": "value 1"
 		}]
 	}, {
 		"amountPaid": "55.00",
 		"billPaymentStatus": "unpaid",
 		"creationDate": "2021-02-17T00:00:00",
 		"currency": "AED",
 		"modificationDate": "2021-02-18T08:20:58",
 		"requestDate": "2021-02-18T08:21:27"
 	}, {
 		"amountPaid": "55.00",
 		"billPaymentStatus": "unpaid",
 		"creationDate": "2021-02-17T00:00:00",
 		"currency": "AED",
 		"modificationDate": "2021-02-18T08:20:58",
 		"requestDate": "2021-02-18T08:21:27"
 	}, {
 		"amountPaid": "55.00",
 		"billPaymentStatus": "unpaid",
 		"creationDate": "2021-02-17T00:00:00",
 		"currency": "AED",
 		"modificationDate": "2021-02-18T08:20:58",
 		"requestDate": "2021-02-18T08:21:27"
 	}]
 }

```

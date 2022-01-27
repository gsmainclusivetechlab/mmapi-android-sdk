# Make a Bill Payment

`Here, createBillPayment(['identifierType' => 'identifier'], billReference, $billPay) creates a POST request to /accounts/{identifierType}/{identifier}/bills/{billReference}/payments`

> `Provided with a valid object representation, this endpoint allows for a new bill payment to be created for a specific account where one identifier suffices to uniquely identify an account.`
`Here, createBillPayment([ 'identifierType1' => 'identifier1', 'identifierType2' => 'identifier2', 'identifierType3' => 'identifier3' ], billReference, billPay) creates a POST request to /accounts/{AccountIdentifiers}/bills/{billReference}/payments`

> `Provided with a valid object representation, this endpoint allows for a new bill payment to be created for a specific account where a single identifier is not sufficient to identify an account.`
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

        BillPay billPayment = new BillPay();
        billPayment.setCurrency("GBP");
        billPayment.setAmountPaid("5.30");

        SDKManager.billPayment.createBillPayment(NotificationMethod.CALLBACK, "", identifierArrayList, billPayment, "REF-000001", new RequestStateInterface() {
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

            @Override
            public void onValidationError(ErrorObject errorObject) {
               
            }
        });


```

### Example Output - Callback

```json
{
 	"notificationMethod": "callback",
 	"objectReference": "1199",
 	"pollLimit": 100,
 	"serverCorrelationId": "3befe732-513e-4bc0-a99d-113161157b2e",
 	"status": "pending"
 }

```

### Example Output - Polling


```json
 
 {
 	"notificationMethod": "polling",
 	"objectReference": "1199",
 	"pollLimit": 100,
 	"serverCorrelationId": "3befe732-513e-4bc0-a99d-113161157b2e",
 	"status": "pending"
 }
 ```

**NOTE**

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state. Use the [viewRequestState()](viewRequestState.Readme.md) function for the polling mechanism to receive the status of a request, and the [viewBillPayment()](viewBillPayment.Readme.md) function to acquire the final representation of the BillPay object.

---

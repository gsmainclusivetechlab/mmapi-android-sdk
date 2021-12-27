# Make a Bill Payment

`Here, createBillPayment(['identifierType' => 'identifier'], billReference, $billPay) creates a POST request to /accounts/{identifierType}/{identifier}/bills/{billReference}/payments`

> `Provided with a valid object representation, this endpoint allows for a new bill payment to be created for a specific account where one identifier suffices to uniquely identify an account.`
`Here, createBillPayment([ 'identifierType1' => 'identifier1', 'identifierType2' => 'identifier2', 'identifierType3' => 'identifier3' ], billReference, billPay) creates a POST request to /accounts/{AccountIdentifiers}/bills/{billReference}/payments`

> `Provided with a valid object representation, this endpoint allows for a new bill payment to be created for a specific account where a single identifier is not sufficient to identify an account.`
### Usage/Examples

```java

```

### Example Output - Callback


### Example Output - Polling


**NOTE**

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state. Use the [viewRequestState()](viewRequestState.Readme.md) function for the polling mechanism to receive the status of a request, and the [viewBillPayment()](viewBillPayment.Readme.md) function to acquire the final representation of the BillPay object.

---

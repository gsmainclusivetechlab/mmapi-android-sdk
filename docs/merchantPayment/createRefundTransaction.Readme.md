
# Create a Transaction

`Here, createRefundTransaction() creates a POST request to /transactions/type/adjustment`

> `Provided with a valid object representation, this endpoint allows for a new transaction to be created for a given transaction type passed via the URL.`



```java

 SDKManager.merchantPayment.createRefundTransaction(NotificationMethod,"CALLBACK URL",transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            serverCorrelationId = requestStateObject.getServerCorrelationId();
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

```

### Example Output - Callback

```json
202
{
  "serverCorrelationId": "233b226e-a2da-48f7-8510-9c79a352906b",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "8182",
  "pollLimit": 100
}
```

### Example Output - Polling

```json
202
{
  "serverCorrelationId": "a0913b62-a7e1-4641-98fa-f93750413600",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "14208",
  "pollLimit": 100
}
```

### NOTE

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state.
Use the <a href="viewRequestState.Readme.md">viewRequestState()</a> function for the polling mechanism to receive the status of a request, and the <a href="viewTransaction.Readme.md">viewTransaction()</a>
function to acquire the final representation of the Transaction object.



# View A Request State-International Transfers

`Here, viewRequestState(String serverCorrelationId) creates a GET request to /requeststates/{serverCorrelationId}`

> `This endpoint returns a specific request state.`

### Usage/Examples

```java
The serverCorrelationId is passed into viewRequestState function to get the state of transaction

```

```java
    SDKManager.p2PTransfer.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                transactionRef = requestStateObject.getObjectReference();
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

### Example Output

```json
200
{
  "serverCorrelationId": "8626661d-2b3a-4166-b3d2-33a0c5fccd35",
  "status": "completed",
  "notificationMethod": "callback",
  "objectReference": "REF-1635488317033",
  "pollLimit": 100
}
```

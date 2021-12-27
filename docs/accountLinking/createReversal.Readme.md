# Create A Reversal

`Here, createReversal(String transactionReference) creates a POST request to 
/transactions/{transactionReference}/reversals`

> `Provided with a valid object representation, this endpoint allows for a new reversal to be created.`


```java
private void createPaymentReversalObject() {
        reversalObject = new ReversalObject();
        reversalObject.setReversal("reversal");
 }
```
Call the reversal function with reference Id of the transaction obtained.

```java

  SDKManager.accountLinking.createReversal(NotificationMethod,"CALLBACK URL","PLACE YOUR REFERENCE ID HERE", reversalObject, new RequestStateInterface() {
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

### Example Output

```json
202
{
  "serverCorrelationId": "66b3e91a-1d36-41a6-8f4a-833ef1f9d125",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "8287",
  "pollLimit": 100
}
```

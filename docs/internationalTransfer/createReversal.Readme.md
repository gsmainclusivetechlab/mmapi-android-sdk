# Create A Reversal - International Transfers

In some failure scenarios, merchant may need to reverse a transaction,Create a reversal object of reversal transaction

### Usage/Examples

Declare the revesal object

```
private ReversalObject reversalObject;
```

```
private void createPaymentReversalObject() {
        reversalObject = new ReversalObject();
        reversalObject.setReversal("reversal");
 }
```
Call the reversal function with reversal and reference Id of transaction obtained using the polling method

```

  SDKManager.internationalTransfer.createReversal(NotificationMethod.POLLING,"","Place your Reference id", reversalObject, new RequestStateInterface() {
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

```javascript
202
{
  "serverCorrelationId": "66b3e91a-1d36-41a6-8f4a-833ef1f9d125",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "8287",
  "pollLimit": 100
}
```

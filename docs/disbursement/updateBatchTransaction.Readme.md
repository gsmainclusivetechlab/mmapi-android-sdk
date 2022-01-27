# Update A Transaction Batch

`Here, updateBatchTransaction(String batchId) creates a PATCH request to /batchtransactions/{batchId}`

> `This endpoint updates a specific transaction batch. Only the batchStatus field can be modified.`

### Usage / Examples


Create a batch array with following object

```java
          private ArrayList<PatchData> patchDataArrayList;

        //create a batch object
             PatchData batchObject = new PatchData();
             batchObject.setOp("replace");
             batchObject.setPath("/batchStatus");
             batchObject.setValue("approved");
             patchDataArrayList = new ArrayList<>();
             patchDataArrayList.add(batchObject);

```
Call the update batch request function with batch id and batch array as input parameter

```java

SDKManager.disbursement.updateBatchTransaction(NotificationMethod.POLLING,"","Place your batch id here",patchDataArrayList, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
             
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
     
            }

           @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
             
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
  "serverCorrelationId": "ade88ed4-decc-4e29-9c24-0c362b2ec284",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "493",
  "pollLimit": 100
}
```

### Example Output - Polling

```json
202
{
  "serverCorrelationId": "ad221629-1d95-4832-ae46-62d86146d7e0",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "750",
  "pollLimit": 100
}
```

### NOTE

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state.
Use the <a href="viewRequestState.Readme.md">viewRequestState()</a> function for the polling mechanism to receive the status of a request, and the <a href="viewBatchTransaction.Readme.md">viewBatchTransaction()</a>
function to acquire the final representation of the Transaction object.

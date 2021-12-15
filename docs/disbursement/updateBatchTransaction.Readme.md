# Update A Transaction Batch

This function updates a specific transaction batch. Only the batchStatus field can be modified. The Batch Status is set to 'approved'`

### Usage / Examples


```java

private ArrayList<Batch> batchArrayList;

```
    
Create a batch array with following object

```java
 private void createBatchRequestObject(){
        //create a batch object
        Batch batchObject = new Batch();
        batchObject.setOp("replace");
        batchObject.setPath("/batchStatus");
        batchObject.setValue("approved");
        batchArrayList=new ArrayList<>();
        batchArrayList.add(batchObject);
    }
```
Call the update batch request function with batch id and batch array as input parameter

```java

SDKManager.disbursement.updateBatchTransaction(NotificationMethod.POLLING,"","Place your batch id here",batchArrayList, new RequestStateInterface() {
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

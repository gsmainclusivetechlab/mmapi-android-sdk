
# View Batch Completions

`Here, viewBatchCompletions(String batchId) creates a GET request to /batchtransactions/{batchId}/completions`

> `This endpoint returns completed transactions for a specific batch.`


### Usage / Examples

This use case allows the disbursement organisation to retrieve all completed transactions for a given batch.
  
  Pass the batch id as first parameter of function obtained from result callback of the bulk disbursment request(# 1) to a function to retrieve the completed 
  
  ```java
  
   SDKManager.disbursement.viewBatchCompletions("Place your batch id here", new BatchCompletionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
              
            }

            @Override
            public void batchTransactionCompleted(BatchTransactionCompletion batchTransactionCompletion, String correlationID) {
           
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
      
            }
        });
  
  ```
  



### Example Output

```json
200
[]
```

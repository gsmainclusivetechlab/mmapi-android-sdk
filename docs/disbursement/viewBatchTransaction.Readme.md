# View A Transaction Batch



> `This endpoint returns the current status of a batch transaction.`
### Usage / Examples

```java
 
 SDKManager.disbursement.viewBatchTransaction(transactionRef, new BatchTransactionItemInterface() {
            @Override
            public void batchTransactionSuccess(BatchTransaction batchTransactionItem) {
               
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
               
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                
            }
        });


  
```
### Example Output

```json
200
{
  "batchId": "REF-1635846330263",
  "batchStatus": "created",
  "batchTitle": "Batch_Test",
  "batchDescription": "Testing a Batch",
  "processingFlag": false,
  "completedCount": 0,
  "rejectionCount": 0,
  "parsingSuccessCount": 0,
  "scheduledStartDate": "2019-12-11T15:08:03",
  "creationDate": "2021-11-02T09:45:30",
  "modificationDate": "2021-11-02T09:45:30",
  "requestDate": "2021-11-02T09:45:30"
}
```

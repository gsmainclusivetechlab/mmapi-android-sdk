# View Batch Rejections

This API enables clients to retrieve information on all transactions that have either failed parsing or have failed to complete.`

### Usage / Examples


        SDKManager.disbursement.viewBatchRejections("REF-1635765084301", new BatchRejectionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
            
            }

            @Override
            public void batchTransactionRejections(BatchRejection batchTransactionRejection) {
     
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
        
            }
        });


### Example Output

```json
200
[]
```

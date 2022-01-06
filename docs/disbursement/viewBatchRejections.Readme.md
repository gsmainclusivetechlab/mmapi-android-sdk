# View Batch Rejections

`Here, viewBatchRejections(String batchId) creates a GET request to /batchtransactions/{batchId}/rejections`

> `This endpoint returns rejected transactions for a specific batch.`

### Usage / Examples

```java
        SDKManager.disbursement.viewBatchRejections("REF-1635765084301", new BatchRejectionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
            
            }

            @Override
            public void batchTransactionRejections(BatchRejections batchTransactionRejection) {
     
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
        
            }
        });

```
### Example Output
200

```json

[
  {
    "transactionReference": "string",
    "requestingOrganisationTransactionReference": "string",
    "creditParty": [
      {
        "key": "msisdn",
        "value": "+33555123456"
      }
    ],
    "debitParty": [
      {
        "key": "msisdn",
        "value": "+33555123456"
      }
    ],
    "rejectionReason": "string",
    "rejectionDate": "2021-12-16T05:43:19.429Z",
    "customData": [
      {
        "key": "string",
        "value": "string"
      }
    ]
  }
]

```

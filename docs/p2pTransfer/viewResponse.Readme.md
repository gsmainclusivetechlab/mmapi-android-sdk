
# View A Response

`Here, viewResponse(String clientCorrelationId,MissingResponse Response) creates a GET request to /responses/{clientCorrelationId}`

> `This endpoint returns a specific response.`


### Usage/Examples

```java


        SDKManager.p2PTransfer.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
              
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
               
            }
        });



```

### Example Output

```json

{
 	"Transaction": [{
 		"amount": "200.00",
 		"creationDate": "2021-04-10T09:58:12",
 		"creditParty": [{
 			"key": "accountid",
 			"value": "2999"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907197912"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907232832"
 		}],
 		"currency": "RWF",
 		"debitParty": [{
 			"key": "accountid",
 			"value": "2999"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907197912"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907232832"
 		}],
 		"modificationDate": "2021-04-10T09:58:12",
 		"requestDate": "2021-04-10T09:58:12",
 		"transactionReference": "REF-1618045092324",
 		"transactionStatus": "pending",
 		"type": "merchantpay"
 	}, {
 		"amount": "200.00",
 		"creationDate": "2021-04-10T09:58:35",
 		"creditParty": [{
 			"key": "accountid",
 			"value": "2999"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907197912"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907232832"
 		}],
 		"currency": "RWF",
 		"debitParty": [{
 			"key": "accountid",
 			"value": "2999"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907197912"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907232832"
 		}],
 		"modificationDate": "2021-04-10T09:58:35",
 		"requestDate": "2021-04-10T09:58:35",
 		"transactionReference": "REF-1618045115056",
 		"transactionStatus": "pending",
 		"type": "merchantpay"
 	}]
 }

```

# View A Transaction

The details of particular transaction can be viewed using following functions,Pass the tranactionb reference obtained from view Request state


### Usage/Examples

```java

SDKManager.internationalTransfer.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
            }

            @Override
            public void onTransactionSuccess(Transaction transactionObject) {
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
             
            }

        });



```

### Example Output

```json
200
{
  "transactionReference": "REF-1635490512846",
  "creditParty": [
    {
      "key": "accountid",
      "value": "2999"
    }
  ],
  "debitParty": [
    {
      "key": "accountid",
      "value": "2999"
    }
  ],
  "type": "merchantpay",
  "transactionStatus": "pending",
  "amount": "200.00",
  "currency": "RWF",
  "creationDate": "2021-10-29T07:55:13",
  "modificationDate": "2021-10-29T07:55:13",
  "requestDate": "2021-10-29T07:55:13"
}
```

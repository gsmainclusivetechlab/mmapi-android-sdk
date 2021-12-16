# Retrieve An Authorisation Code

1. `viewAuthorisationCode([ 'identifierType1' => 'identifier1' ], authorisationCode) creates a GET request to /accounts/{identifierType}/{identifier}/authorisationcodes/{authorisationCode}`

> `This endpoint returns a specific Authorisation Code linked to an account.`
1. `Here, viewAuthorisationCode([ 'identifierType1' => 'identifier1', 'identifierType2' => 'identifier2', 'identifierType3' => 'identifier3' ], authorisationCode) creates a GET request to /accounts/{RequestorAccountIdentifiers}/authorisationcodes/{authorisationCode}`

> `This endpoint returns a specific Authorisation Code linked to an account.`

### Usage/Examples

Transaction Reference and Identifier list must be provided.

```java

    private void createAccountIdentifier(){
        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);
    }

```

```java

SDKManager.merchantPayment.viewAuthorisationCode(identifierArrayList, transactionRef, new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(authorisationCodeItem));
                Log.d(SUCCESS, "onAuthorizationCodeItem: " + new Gson().toJson(authorisationCodeItem));
            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onAuthorizationCodeFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, ""+errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });
        
```

### Example Output

```json
200
{
  "authorisationCode": "ad922511-77ae-4c17-b674-f85a96fffbf7",
  "codeState": "active",
  "amount": "1000.00",
  "currency": "GBP",
  "redemptionAccountIdentifiers": [
    {
      "key": "accountid",
      "value": "2000"
    },
    {
      "key": "linkref",
      "value": "REF-1621839627337"
    },
    {
      "key": "linkref",
      "value": "REF-1635445811066"
    }
  ],
  "creationDate": "2021-11-16T15:42:05",
  "modificationDate": "2021-11-16T15:42:05",
  "requestDate": "2018-07-03T10:43:27"
}
```

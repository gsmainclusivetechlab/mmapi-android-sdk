
`Here, viewAccountName(Identifiers identifiers) creates a GET request to /accounts/{identifierType}/{identifier}/accountname`

> `This endpoint returns the name of an account holder.



### Usage/Examples

```java
        identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("1");
        identifierArrayList.add(identifierAccount);

```

```java

  SDKManager.p2PTransfer.viewAccountName(identifierArrayList, new AccountHolderInterface() {
            @Override
            public void onRetrieveAccountInfoSuccess(AccountHolderName accountHolderObject) {
         
            }

            @Override
            public void onRetrieveAccountInfoFailure(GSMAError gsmaError) {
            
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
  "name": {
    "title": "Mr",
    "firstName": "Jeff",
    "middleName": "James",
    "lastName": "Jimmer",
    "fullName": "Jeff Jimmer"
  },
  "lei": "AAAA0012345678901299"
}
```

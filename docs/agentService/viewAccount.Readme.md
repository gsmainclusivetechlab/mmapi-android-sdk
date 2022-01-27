# View an Account

`Here, viewAccount(Identifiers identifiers) creates a GET request to /accounts/{identifierType}/{identifier}`

> `This endpoint returns the details of an account.`
### Usage/Examples

```java

        ArrayList<Identifier> identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("1");

        identifierArrayList.add(identifierAccount);


```

```java

 SDKManager.agentService.viewAccount(identifierArrayList, new AccountInterface() {
            @Override
            public void onAccountSuccess(Account account) {
             identityId = account.getIdentity().get(0).getIdentityId();
             }

            @Override
            public void onAccountFailure(GSMAError gsmaError) {
       
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
              
            }
        });



```

###  Example Output

```java
{
  "accountIdentifiers": [
    {
      "key": "msisdn",
      "value": "+449999999"
    },
    {
      "key": "walletid",
      "value": "1"
    },
    {
      "key": "accountid",
      "value": "1"
    },
    {
      "key": "mandatereference",
      "value": "REF-1583141449478"
    },
    {
      "key": "linkref",
      "value": "REF-1473082363913"
    },
    {
      "key": "linkref",
      "value": "REF-1579002505974"
    },
    {
      "key": "mandatereference",
      "value": "REF-1599647601577"
    },

  ],...}
```

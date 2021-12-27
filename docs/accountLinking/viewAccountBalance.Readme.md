# View Account Balance

`Here, viewAccountBalance(Identifiers identifiers) creates a GET request to /accounts/{identifierType}/{identifier}/balance`

> `This endpoint returns the balance of an account.`
### Usage/Examples

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
 SDKManager.accountLinking.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
            }
            @Override
            public void onBalanceSuccess(Balance balance, String correlationID) {
       
            }
            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
              
            }
        });
```


### Response Example

```java
{
  "currentBalance": "0.00",
  "availableBalance": "0.00",
  "reservedBalance": "0.00",
  "unclearedBalance": "0.00",
  "accountStatus": "available"
}
```

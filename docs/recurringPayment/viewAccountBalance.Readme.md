# View Account Balance

1) `Here, viewAccountBalance({ identifierType: identifier }) creates a GET request to /accounts/{identifierType}/{identifier}/balance`

> `This endpoint returns the balance of an account where one identifier suffices to uniquely identify an account.`
2) `Here, viewAccountBalance({ identifierType1: identifier1, identifierType2: identifier2, identifierType3: identifier3 }) creates a GET request to /accounts/{AccountIdentifiers}/balance`

> `This endpoint returns the balance of an account where a single identifier is not sufficient to identify an account.`
3) `Here, viewAccountBalance() creates a GET request to /accounts/balance`

> `This endpoint returns the balance of an account. As the account is not passed as a parameter, the account is assumed to be that of the calling client.`
Obtain the balance of requested account,Pass the account identifier list  to the function to retrieve the balance details

```java
   
        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();
        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);

```

```java
 SDKManager.recurringPayment.viewAccountBalance(identifierArrayList, new BalanceInterface() {
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

### Example Output

```json
200
{
  "currentBalance": "0.00",
  "availableBalance": "0.00",
  "reservedBalance": "0.00",
  "unclearedBalance": "0.00",
  "accountStatus": "available"
}
```

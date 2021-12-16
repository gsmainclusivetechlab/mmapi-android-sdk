# View Account Balance

Obtain the balance of requested account,Pass the account identier list  to the function to retrieve the balance details

### Usage/Examples

```java
    ArrayList<Identifier> identifierArrayList;

```

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

 SDKManager.internationalTransfer.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onBalanceSuccess(Balance balance) {
       
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

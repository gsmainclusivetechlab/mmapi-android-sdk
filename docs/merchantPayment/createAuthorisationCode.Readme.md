# Create An Authorisation Code Via An Account Identifier

Mobile money payer/payee submits the request to generate a code which when presented to the other party, can be redeemed for an amount set by the payer/payee, depending on the use case. The MMP returns the request state object to indicate the request is pending. The payer/payee communicate the code to respective parties verabally or by a QR code.
### Usage/Examples


 Declare the Authorization code object

 ```java
 private AuthorisationCodeRequest authorisationCodeRequest;
 private TransactionRequest transactionRequest;
 private String serverCorrelationId = "";
 ```
 Initialise the  authorizarion code with  amount,date and currency


 ```java
     private void createCodeRequestObject() {
        authorisationCodeRequest = new AuthorisationCodeRequest();
        authorisationCodeRequest.setAmount("Place your amount here"); // eg:200.00
        authorisationCodeRequest.setRequestDate("Place your date here"); //sample format 2021-10-18T10:43:27.405Z
        authorisationCodeRequest.setCurrency("Place your currency"); //eg:RWF

    }

```
Obtain Authorization code to perform merchant payment,These scenario can be achieved by passing account identifiers to a function

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
     SDKManager.merchantPayment.createAuthorisationCode(identifierArrayList,NotificationMethod.POLLING,"", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
               
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
             
            }
             @Override
            public void getCorrelationId(String correlationID) {
               
            }

        });
```

### Example Output - Callback

```json
202
{
  "serverCorrelationId": "dae8ef64-4340-40b4-863e-ddbe9d63374b",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "1056",
  "pollLimit": 100
}
```

### Example Output - Polling

```json
202
{
  "serverCorrelationId": "679b684e-9b2f-4140-b0b8-dc53d183ffaf",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "1707",
  "pollLimit": 100
}
```

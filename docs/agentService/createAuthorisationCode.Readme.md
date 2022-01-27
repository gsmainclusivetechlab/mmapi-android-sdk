# Create an Authorisation Code via an account identifier

`Here, createAuthorisationCode(Identifiers identifiers) creates a POST request to /accounts/{identifierType}/{identifier}/authorisationcodes`

> `This endpoint allows allows a mobile money payer or payee to generate a code which when presented to the other party, can be redeemed for an amount set by the payer or payee, depending upon the use case.`
### Usage/Examples


```java

        ArrayList<Identifier> identifierArrayList= new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2999");

        identifierArrayList.add(identifierAccount);

```


```java


     AuthorisationCode  authorisationCodeRequest = new AuthorisationCode();
     authorisationCodeRequest.setAmount("200.00");
     authorisationCodeRequest.setRequestDate("2021-10-18T10:43:27.405Z");
     authorisationCodeRequest.setCurrency("RWF");
     authorisationCodeRequest.setCodeLifetime(1)

```

```java


 SDKManager.agentService.createAuthorisationCode(identifierArrayList, NotificationMethod.POLLING, "", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
      
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
             serverCorrelationId = requestStateObject.getServerCorrelationId();
           }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
   
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
             }

        });


```


### Example Output

```java
{
  "serverCorrelationId": "8c8d04ee-24b9-4eb9-8b7a-c6d0d94dc842",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "2514",
  "pollLimit": 100
}
```

### NOTE

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state.
Use the <a href="viewRequestState.Readme.md">viewRequestState()</a> function for the polling mechanism to receive the status of a request, and the <a href="viewAuthorisationCode.Readme.md">viewAuthorisationCode()</a>
function to acquire the final representation of the Transaction object.

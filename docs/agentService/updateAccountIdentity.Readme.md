# Update an Account Identity

`Here, updateAccountIdentity(Identifiers identifiers, final String identityId) creates a PATCH request to /accounts/{identifierType}/{identifier}/identities/{identityId}`

> `This endpoint updates an account identity. identityStatus, kycVerificationStatus, kycVerificationEntity and kycLevel field updates are permitted.`
### Usage/Examples

```java

        ArrayList<Identifier>  identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2999");

        identifierArrayList.add(identifierAccount);


```


```java
        private ArrayList<PatchData> patchDataArrayList;

        PatchData patchObject = new PatchData();
        patchObject.setOp("replace");
        patchObject.setPath("/kycVerificationStatus");
        patchObject.setValue("verified");
        
        patchDataArrayList = new ArrayList<>();
        patchDataArrayList.add(patchObject);
```

```java

  SDKManager.agentService.updateAccountIdentity(NotificationMethod.POLLING, "", identityId, patchDataArrayList, identifierArrayList, new RequestStateInterface() {
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

### Example Output

```java
{
  "serverCorrelationId": "37518e97-95e9-4c6a-85f5-e0547dc9aa9a",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "1",
  "pollLimit": 100
}
```

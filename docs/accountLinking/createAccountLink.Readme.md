# Create A Link

`Here, createAccountLink(Identifiers identifiers) creates a POST request to /accounts/{identifierType}/{identifier}/links`

> `Provided with a valid object representation, this endpoint allows a new link to be created for a specific account.`

### Usage/Examples


```java

private Link accountLinkingObject;

```

```java
        identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");

        identifierArrayList.add(identifierAccount);

```

```java

        accountLinkingObject = new Link();

        //set amount and currency

        accountLinkingObject.setMode("active");
        accountLinkingObject.setStatus("both");

        ArrayList<AccountIdentifier> sourceAccountIdentifiersList = new ArrayList<>();
        ArrayList<CustomDataItem> customDataList = new ArrayList<>();
        AccountIdentifier sourceAccountIdentifiersItem = new AccountIdentifier();
        CustomDataItem customDataItem = new CustomDataItem();
        RequestingOrganisation requestingOrganisationItem = new RequestingOrganisation();

        //Source Account Identifiers
        sourceAccountIdentifiersItem.setKey("accountid");
        sourceAccountIdentifiersItem.setValue("2999");
        sourceAccountIdentifiersList.add(sourceAccountIdentifiersItem);

        //Custom Data
        customDataItem.setKey("keytest");
        customDataItem.setValue("keyvalue");
        customDataList.add(customDataItem);

        //Requesting Organisation data
        requestingOrganisationItem.setRequestingOrganisationIdentifierType("organisationid");
        requestingOrganisationItem.setRequestingOrganisationIdentifier("12345");


        //add details to account linking object
        accountLinkingObject.setSourceAccountIdentifiers(sourceAccountIdentifiersList);
        accountLinkingObject.setCustomData(customDataList);
        accountLinkingObject.setRequestingOrganisation(requestingOrganisationItem);

        SDKManager.accountLinking.createAccountLinking(NotificationMethod.POLLING, "", identifierArrayList, accountLinkingObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
        
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
    
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
          
            }

            @Override
            public void getCorrelationId(String correlationID) {
             
            }

        });

```

### Response Example

```java
{
  "serverCorrelationId": "03a059de-3867-47a6-8481-fa11effee7a4",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "425",
  "pollLimit": 100
}
```

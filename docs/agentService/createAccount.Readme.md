# Create an Account

`Here, createAccount() creates a POST request to /accounts/{identityType}`

> `Provided with a valid object representation, this endpoint allows for a new account to be created.`
### Usage/Examples

```java


        accountRequest = new Account();

        ArrayList<AccountIdentifier> accountIdentifiers = new ArrayList<>();

        //account identifiers
        AccountIdentifier accountIdentifier = new AccountIdentifier();
        accountIdentifier.setKey("msisdn");
        accountIdentifier.setValue("Place your value for account identifier"));
        accountIdentifiers.add(accountIdentifier);

        //add account identifier to account object
        accountRequest.setAccountIdentifiers(accountIdentifiers);

        //identity array
        ArrayList<Identity> identityArrayList = new ArrayList<>();
        //identity object
        Identity identity = new Identity();

        //kyc information
        KYCInformation kycInformation = new KYCInformation();

        kycInformation.setBirthCountry("AD");
        kycInformation.setContactPhone("+447777777777");
        kycInformation.setDateOfBirth("2000-11-20");
        kycInformation.setEmailAddress("xyz@xyz.com");
        kycInformation.setEmployerName("String");
        kycInformation.setGender("m");

        //create  id document object

        ArrayList<IdDocument> idDocumentArrayList = new ArrayList<>();
        IdDocument idDocument = new IdDocument();
        idDocument.setIdType("passport");
        idDocument.setIdNumber("111111");
        idDocument.setIssueDate("2018-11-20");
        idDocument.setExpiryDate("2018-11-20");
        idDocument.setIssuer("ABC");
        idDocument.setIssuerPlace("DEF");
        idDocument.setIssuerCountry("AD");

        idDocumentArrayList.add(idDocument);

        kycInformation.setIdDocument(idDocumentArrayList);


        kycInformation.setNationality("AD");
        kycInformation.setOccupation("Miner");

        //Postal Address
        Address address = new Address();
        address.setAddressLine1("37");
        address.setAddressLine2("ABC Drive");
        address.setAddressLine3("string");
        address.setCity("Berlin");
        address.setStateProvince("string");
        address.setPostalCode("AF1234");
        address.setCountry("AD");

        kycInformation.setPostalAddress(address);

        //subject information
        SubjectName subjectName = new SubjectName();
        subjectName.setTitle("Mr");
        subjectName.setFirstName("H");
        subjectName.setMiddleName("I");
        subjectName.setLastName("J");
        subjectName.setFullName("H I J ");
        subjectName.setNativeName("string");


        kycInformation.setSubjectName(subjectName);

        identity.setIdentityKyc(kycInformation);
        identity.setAccountRelationship("accountHolder");
        identity.setKycVerificationStatus("verified");
        identity.setKycVerificationEntity("ABC Agent");

        //kyc level
        identity.setKycLevel(1);

        //custom data for identity
        ArrayList<CustomDataItem> customDataItemArrayList = new ArrayList<>();
        CustomDataItem customDataItem = new CustomDataItem();
        customDataItem.setKey("test");
        customDataItem.setValue("custom");

        customDataItemArrayList.add(customDataItem);

        identity.setCustomData(customDataItemArrayList);

        //add identity to array
        identityArrayList.add(identity);

        //add identity array into account object
        accountRequest.setIdentity(identityArrayList);


        //account type
        accountRequest.setAccountType("string");

        //custom data for account


        ArrayList<CustomDataItem> customDataItemAccountArrayList = new ArrayList<>();
        CustomDataItem customDataAccountItem = new CustomDataItem();
        customDataAccountItem.setKey("test");
        customDataAccountItem.setValue("custom1");

        customDataItemAccountArrayList.add(customDataAccountItem);

        accountRequest.setCustomData(customDataItemAccountArrayList);

        //Fees array

        ArrayList<Fees> feesArrayList = new ArrayList<>();

        Fees fees = new Fees();
        fees.setFeeType("string");
        fees.setFeeAmount("5.46");
        fees.setFeeCurrency("AED");

        feesArrayList.add(fees);

        accountRequest.setFees(feesArrayList);


        accountRequest.setRegisteringEntity("ABC Agent");
        accountRequest.setRequestDate("2021-02-17T15:41:45.194Z");


```

```java


   SDKManager.agentService.createAccount(NotificationMethod.CALLBACK, "", accountRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                       
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {
    
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
          
            }
        });

```

### Example Output

```json
{
  "serverCorrelationId": "ac6d6e38-7322-43cf-a4bb-57305a5bfc6e",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "268",
  "pollLimit": 100
}
```

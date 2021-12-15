# Create A New Quotation

Request a quotation to perform an international transfer transaction

### Usage/Examples

```
    private String correlationId = "";
    private String serverCorrelationId;

```

```

    //create a transaction object for international transfer request
    private void createInternationalQuotationObject() {
        quotationRequest = new Quotation();


        //create debit party and credit party for internal transfer quotation
        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

        //debit party
        debitPartyItem.setKey("walletid");
        debitPartyItem.setValue("1");
        debitPartyList.add(debitPartyItem);

        //credit party
        creditPartyItem.setKey("msisdn");
        creditPartyItem.setValue("+44012345678");
        creditPartyList.add(creditPartyItem);

        //add debit and credit party to transaction object
        quotationRequest.setDebitParty(debitPartyList);
        quotationRequest.setCreditParty(creditPartyList);


        //set amount,currency and request date into transaction request
        quotationRequest.setRequestAmount("75.30");
        quotationRequest.setRequestCurrency("RWF");
        quotationRequest.setRequestDate("2018-07-03T11:43:27.405Z");
        quotationRequest.setType("inttransfer");
        quotationRequest.setSubType("abc");
        quotationRequest.setChosenDeliveryMethod("agent");

        //sender kyc object
        KYCInformation senderKyc = new KYCInformation();
        senderKyc.setNationality("GB");
        senderKyc.setDateOfBirth("1970-07-03T11:43:27.405Z");
        senderKyc.setOccupation("manager");
        senderKyc.setEmployerName("MFX");
        senderKyc.setContactPhone("447125588999");
        senderKyc.setGender("m"); // m or f
        senderKyc.setEmailAddress("luke.skywalkeraaabbb@gmail.com");
        senderKyc.setBirthCountry("GB");

        // create object for documentation
        ArrayList<IdDocument> idDocumentItemList = new ArrayList<>();
        IdDocument idDocumentItem = new IdDocument();
        idDocumentItem.setIdType("nationalidcard");
        idDocumentItem.setIdNumber("1234567");
        idDocumentItem.setIssueDate("2018-07-03T11:43:27.405Z");
        idDocumentItem.setExpiryDate("2021-07-03T11:43:27.405Z");
        idDocumentItem.setIssuer("UKPA");
        idDocumentItem.setIssuerPlace("GB");
        idDocumentItem.setIssuerCountry("GB");
        idDocumentItem.setOtherIdDescription("test");

        idDocumentItemList.add(idDocumentItem);

        //add document details to kyc object
        senderKyc.setIdDocument(idDocumentItemList);

        //create object for postal address
        Address postalAddress = new Address();
        postalAddress.setCountry("GB");
        postalAddress.setAddressLine1("111 ABC Street");
        postalAddress.setCity("New York");
        postalAddress.setStateProvince("New York");
        postalAddress.setPostalCode("ABCD");

        //add postal address to kyc object
        senderKyc.setPostalAddress(postalAddress);

        //create subject model

        SubjectName subjectName = new SubjectName();
        subjectName.setTitle("Mr");
        subjectName.setFirstName("Luke");
        subjectName.setMiddleName("R");
        subjectName.setLastName("Skywalker");
        subjectName.setFullName("Luke R Skywalker");
        subjectName.setNativeName("ABC");

        //add  subject to kyc model

        senderKyc.setSubjectName(subjectName);

        //create array for custom data items
        ArrayList<CustomDataItem> customDataItemList = new ArrayList<>();

        // create a custom data item
        CustomDataItem customDataItem = new CustomDataItem();
        customDataItem.setKey("keytest");
        customDataItem.setValue("keyvalue");

        //add custom object into custom array
        customDataItemList.add(customDataItem);

        //add kyc object to request object
        quotationRequest.setSenderKyc(senderKyc);

        //add custom data object to request object
        quotationRequest.setCustomData(customDataItemList);

        quotationRequest.setSendingServiceProviderCountry("AD");
        quotationRequest.setOriginCountry("AD");
        quotationRequest.setReceivingCountry("AD");

        //request for quotation
        requestQuotation();

    }

```

```

    //Request the quotation to perform international transfer
    private void requestQuotation() {

        SDKManager.p2pTransfer.createQuotation(NotificationMethod.POLLING, "", quotationRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                serverCorrelationId = requestStateObject.getServerCorrelationId();
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
             

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
               
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
     
            }

        });
    }


```


## Example Output - Callback

```json
202
{
  "serverCorrelationId": "7a20ef01-996c-4652-95ee-13766f116544",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "535",
  "pollLimit": 100
}
```

### Example Output - Polling

```json
202
{
  "serverCorrelationId": "eb95b1b5-79bb-4729-9d7c-67d8bd357f8e",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "804",
  "pollLimit": 100
}
```



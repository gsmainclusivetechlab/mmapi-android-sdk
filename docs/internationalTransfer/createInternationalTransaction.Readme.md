
 # Perform A International Transfer Transaction

The sender FSP will sent a request with Transaction object to a function to perform a International Transfer Transaction


### Usage/Examples

```
private Transaction transactionRequest;

```

```
    private void createInternationalTransferObject() {
       transactionRequest=new Transaction();
        if (transactionRequest == null) {
            Utils.showToast(this, "Please request Quotation before performing this request");
            return;
        } else {

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
            transactionRequest.setDebitParty(debitPartyList);
            transactionRequest.setCreditParty(creditPartyList);


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



            //set amount and currency
            transactionRequest.setAmount("100");
            transactionRequest.setCurrency("GBP");

            //create international information object to perform international transfer

            InternationalTransferInformation internationalTransferInformation = new InternationalTransferInformation();
            internationalTransferInformation.setOriginCountry("GB");
            internationalTransferInformation.setQuotationReference("REF-1636521507766");
            internationalTransferInformation.setQuoteId("REF-1636521507766");
            internationalTransferInformation.setReceivingCountry("RW");
            internationalTransferInformation.setRemittancePurpose("personal");
            internationalTransferInformation.setRelationshipSender("none");
            internationalTransferInformation.setDeliveryMethod("agent");
            internationalTransferInformation.setSendingServiceProviderCountry("AD");
            transactionRequest.setInternationalTransferInformation(internationalTransferInformation);

            RequestingOrganisation requestingOrganisation = new RequestingOrganisation();
            requestingOrganisation.setRequestingOrganisationIdentifierType("organisationid");
            requestingOrganisation.setRequestingOrganisationIdentifier("testorganisation");

            //add requesting organisation object into transaction request
            transactionRequest.setRequestingOrganisation(requestingOrganisation);

            performInternationalTransfer();
        }
    }

```


```

private void performInternationalTransfer() {

SDKManager.internationalTransfer.createInternationalTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
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
 
}

```

### Example Output - Callback

```json
202
{
  "serverCorrelationId": "848542b2-8c3c-47e8-b3ec-9c2bad3f9916",
  "status": "pending",
  "notificationMethod": "callback",
  "objectReference": "11582",
  "pollLimit": 100
}
```

### Example Output - Polling

```json
202
{
  "serverCorrelationId": "6d4ca881-8b73-4036-8385-4364ba47bbbc",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "13728",
  "pollLimit": 100
}
```

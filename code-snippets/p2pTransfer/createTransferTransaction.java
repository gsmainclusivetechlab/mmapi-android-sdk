private Transaction transactionRequest;


if (transactionRequest == null) {
      Utils.showToast(this, "Please request Quotation before performing this request");
      return;
  } else {

      //set amount and currency
      transactionRequest.setAmount("100");
      transactionRequest.setCurrency("GBP");

      ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
      ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
      DebitPartyItem debitPartyItem = new DebitPartyItem();
      CreditPartyItem creditPartyItem = new CreditPartyItem();

      //debit party
      debitPartyItem.setKey("accountid");
      debitPartyItem.setValue("2999");
      debitPartyList.add(debitPartyItem);

      //credit party
      creditPartyItem.setKey("accountid");
      creditPartyItem.setValue("2000");
      creditPartyList.add(creditPartyItem);

      //create international information object to perform international transfer

      InternationalTransferInformation internationalTransferInformation = new InternationalTransferInformation();
      internationalTransferInformation.setOriginCountry("AD");
      internationalTransferInformation.setQuotationReference("REF-1636521507766");
      internationalTransferInformation.setQuoteId("REF-1636521507766");
      internationalTransferInformation.setRemittancePurpose("personal");
      internationalTransferInformation.setDeliveryMethod("agent");
      transactionRequest.setInternationalTransferInformation(internationalTransferInformation);

      RequestingOrganisation requestingOrganisation = new RequestingOrganisation();
      requestingOrganisation.setRequestingOrganisationIdentifierType("organisationid");
      requestingOrganisation.setRequestingOrganisationIdentifier("testorganisation");

      //add requesting organisation object into transaction request
      transactionRequest.setRequestingOrganisation(requestingOrganisation);

      //add debit and credit party to transaction object
      transactionRequest.setDebitParty(debitPartyList);
      transactionRequest.setCreditParty(creditPartyList);

      performTransfer();
  
}

private void performTransfer() {
SDKManager.getInstance().createTransferTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
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
        
    }
});
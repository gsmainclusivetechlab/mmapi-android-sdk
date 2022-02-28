ArrayList<Identifier> identifierArrayList= new ArrayList<>();

//account id
Identifier identifierAccount = new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("2999");

identifierArrayList.add(identifierAccount);

AuthorisationCode  authorisationCodeRequest = new AuthorisationCode();
authorisationCodeRequest.setAmount("200.00");
authorisationCodeRequest.setRequestDate("2021-10-18T10:43:27.405Z");
authorisationCodeRequest.setCurrency("RWF");
authorisationCodeRequest.setCodeLifetime(1)

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
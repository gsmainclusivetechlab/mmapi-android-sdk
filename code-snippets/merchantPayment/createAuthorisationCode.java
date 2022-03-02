// Declare the Authorization code object

private AuthorisationCodeRequest authorisationCodeRequest;
 
//Initialise the  authorization code with  amount,date and currency

authorisationCodeRequest = new AuthorisationCodeRequest();
authorisationCodeRequest.setAmount("Place your amount here"); // eg:200.00
authorisationCodeRequest.setRequestDate("Place your date here"); //sample format 2021-10-18T10:43:27.405Z
authorisationCodeRequest.setCurrency("Place your currency"); //eg:RWF

// Obtain Authorization code to perform merchant payment,These scenario can be achieved by passing account identifiers to a function


private void createAccountIdentifier(){

    identifierArrayList=new ArrayList<>();
    identifierArrayList.clear();

    Identifier identifierAccount=new Identifier();
    identifierAccount.setKey("accountid");
    identifierAccount.setValue("2000");
    identifierArrayList.add(identifierAccount);

}

SDKManager.merchantPayment.createAuthorisationCode(identifierArrayList,NotificationMethod,"CALLBACK URL", authorisationCodeRequest, new RequestStateInterface() {
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
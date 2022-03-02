ArrayList<Identifier> identifierArrayList=new ArrayList<>();
identifierArrayList.clear();
Identifier identifierAccount=new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("2000");
identifierArrayList.add(identifierAccount);

SDKManager.agentService.viewAuthorisationCode(identifierArrayList, transactionRef, new AuthorisationCodeItemInterface() {
    @Override
    public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {
     
    }
    @Override
    public void onAuthorisationCodeFailure(GSMAError gsmaError) {
             
    }
    @Override
    public void onValidationError(ErrorObject errorObject) {
            
    }
});
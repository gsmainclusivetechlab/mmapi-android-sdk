ArrayList<Identifier> identifierArrayList = new ArrayList<>();

//account id
Identifier identifierAccount = new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("1");

identifierArrayList.add(identifierAccount);

SDKManager.agentService.viewAccount(identifierArrayList, new AccountInterface() {
    @Override
    public void onAccountSuccess(Account account) {
    identityId = account.getIdentity().get(0).getIdentityId();
    }

    Override
    public void onAccountFailure(GSMAError gsmaError) {
       
    }

    @Override
    public void onValidationError(ErrorObject errorObject) {
              
    }
});
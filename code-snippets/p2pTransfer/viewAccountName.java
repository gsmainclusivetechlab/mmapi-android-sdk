identifierArrayList = new ArrayList<>();

//account id
Identifier identifierAccount = new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("1");
identifierArrayList.add(identifierAccount);

SDKManager.p2PTransfer.viewAccountName(identifierArrayList, new AccountHolderInterface() {
    @Override
    public void onRetrieveAccountInfoSuccess(AccountHolderName accountHolderObject) {
  
    }

    @Override
    public void onRetrieveAccountInfoFailure(GSMAError gsmaError) {
    
    }


    @Override
    public void onValidationError(ErrorObject errorObject) {
        
    }

});
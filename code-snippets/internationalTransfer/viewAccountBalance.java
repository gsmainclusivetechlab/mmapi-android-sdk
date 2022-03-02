ArrayList<Identifier> identifierArrayList;

identifierArrayList=new ArrayList<>();
identifierArrayList.clear();

Identifier identifierAccount=new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("2000");
identifierArrayList.add(identifierAccount);

SDKManager.internationalTransfer.viewAccountBalance(identifierArrayList, new BalanceInterface() {
  @Override
  public void onValidationError(ErrorObject errorObject) {

  }

  @Override
  public void onBalanceSuccess(Balance balance) {
       
  }

  @Override
  public void onBalanceFailure(GSMAError gsmaError) {
              
  }
});
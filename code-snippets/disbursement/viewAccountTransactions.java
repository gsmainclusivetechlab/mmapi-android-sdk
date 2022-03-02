ArrayList<Identifier> identifierArrayList;

identifierArrayList=new ArrayList<>();
identifierArrayList.clear();

Identifier identifierAccount=new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("2000");
identifierArrayList.add(identifierAccount);
  
TransactionFilter transactionFilter=new TransactionFilter();
transactionFilter.setLimit(5);
transactionFilter.setOffset(0);

SDKManager.disbursement.viewAccountTransactions(identifierArrayList, transactionFilter,  new RetrieveTransactionInterface() {
    @Override
    public void onValidationError(ErrorObject errorObject) {
        
    }

    @Override
    public void onRetrieveTransactionSuccess(Transactions transaction) {
           
    }

    @Override
    public void onRetrieveTransactionFailure(GSMAError gsmaError) {
              
    }
});
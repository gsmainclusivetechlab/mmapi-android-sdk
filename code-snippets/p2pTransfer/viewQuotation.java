
private String transactionRef = "";

SDKManager.p2PTransfer.viewQuotation(transactionRef, new TransactionInterface() {
    @Override
    public void onTransactionSuccess(Transaction transactionObject) {
  
    }

    @Override
    public void onTransactionFailure(GSMAError gsmaError) {
        
    }

    @Override
    public void onValidationError(ErrorObject errorObject) {
        
    }
});
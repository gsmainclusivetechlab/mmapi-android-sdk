SDKManager.recurringPayment.viewTransaction(transactionRef, new TransactionInterface() {
    @Override
    public void onValidationError(ErrorObject errorObject) {
    }
    @Override
    public void onTransactionSuccess(Transaction transactionObject) {
    }
    @Override
    public void onTransactionFailure(GSMAError gsmaError) {
      
    }
});
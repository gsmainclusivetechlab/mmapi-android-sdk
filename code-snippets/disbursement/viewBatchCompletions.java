SDKManager.disbursement.viewBatchCompletions("Place your batch id here", new BatchCompletionInterface() {
  @Override
  public void onValidationError(ErrorObject errorObject) {
              
  }

  @Override
  public void batchTransactionCompleted(BatchCompletions batchTransactionCompletion, String correlationID) {
           
  }

  @Override
  public void onTransactionFailure(GSMAError gsmaError) {
      
  }
});
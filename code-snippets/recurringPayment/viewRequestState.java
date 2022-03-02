SDKManager.recurringPayment.viewRequestState(serverCorrelationId, new RequestStateInterface() {
    @Override
    public void onValidationError(ErrorObject errorObject) {
    }
    @Override
    public void onRequestStateSuccess(RequestStateObject requestStateObject) {
          transactionRef = requestStateObject.getObjectReference();

    }
    @Override
    public void onRequestStateFailure(GSMAError gsmaError) {
    }
      @Override
    public void getCorrelationId(String correlationID) {
        
    }
});
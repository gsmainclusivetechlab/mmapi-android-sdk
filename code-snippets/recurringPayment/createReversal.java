Reversal  reversalObject = new Reversal();
reversalObject.setReversal("reversal");

// Call the reversal function with reference Id of the transaction obtained.

SDKManager.recurringPayment.createReversal(NotificationMethod,"CALLBACK URL","PLACE YOUR REFERENCE ID HERE", reversalObject, new RequestStateInterface() {
    @Override
    public void onRequestStateSuccess(RequestStateObject requestStateObject) {
              serverCorrelationId = requestStateObject.getServerCorrelationId();

    }

    @Override
    public void onRequestStateFailure(GSMAError gsmaError) {
      
    }

    @Override
    public void onValidationError(ErrorObject errorObject) {
        
    }
    @Override
    public void getCorrelationId(String correlationID) {
        
    }
});
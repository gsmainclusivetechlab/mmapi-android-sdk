private Reversal reversalObject;

reversalObject = new Reversal();
reversalObject.setReversal("reversal");

// Call the reversal function with reversal and reference Id of transaction obtained using the polling method

SDKManager.internationalTransfer.createReversal(NotificationMethod.POLLING,"","Place your Reference id", reversalObject, new RequestStateInterface() {
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
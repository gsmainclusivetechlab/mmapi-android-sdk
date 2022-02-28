private Transaction transactionRequest;
private String serverCorrelationId = "";
private String transactionRef = "";

transactionRequest = new Transaction();        
ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        
ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
        
AccountIdentifier debitPartyItem = new AccountIdentifier();
        
AccountIdentifier creditPartyItem = new AccountIdentifier();

debitPartyItem.setKey("accountid");        
debitPartyItem.setValue("2999");        
debitPartyList.add(debitPartyItem);

creditPartyItem.setKey("accountid");        
creditPartyItem.setValue("2999");        
creditPartyList.add(creditPartyItem);

transactionRequest.setDebitParty(debitPartyList);
transactionRequest.setCreditParty(creditPartyList);
transactionRequest.setAmount("200.00");        
transactionRequest.setCurrency("RWF");

// Initiate the mechant pay request using the following code
  
SDKManager.disbursement.createDisbursementTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
  @Override
  public void onValidationError(ErrorObject errorObject) {
             
  }

  @Override
  public void onRequestStateSuccess(RequestStateObject requestStateObject) {
    serverCorrelationId = requestStateObject.getServerCorrelationId();
  }

  @Override
  public void onRequestStateFailure(GSMAError gsmaError) {
     
  }
  @Override
  public void getCorrelationId(String correlationID) {
               
  }
}
private BatchTransaction bulkTransactionObject;

bulkTransactionObject = new BatchTransaction();

ArrayList<Transaction> transactionItems = new ArrayList<>();
Transaction transactionItem = new Transaction();
ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
AccountIdentifier debitPartyItem = new AccountIdentifier();
AccountIdentifier creditPartyItem = new AccountIdentifier();

debitPartyItem.setKey("accountid");
debitPartyItem.setValue("2000");
debitPartyList.add(debitPartyItem);

creditPartyItem.setKey("walletid");
creditPartyItem.setValue("1");
creditPartyList.add(creditPartyItem);

transactionItem.setAmount("200");
transactionItem.setType("transfer");
transactionItem.setCurrency("RWF");
transactionItem.setCreditParty(creditPartyList);
transactionItem.setDebitParty(debitPartyList);
transactionItems.add(transactionItem);
transactionItems.add(transactionItem);

bulkTransactionObject.setBatchDescription("Testing a Batch transaction");
bulkTransactionObject.setBatchTitle("Batch Test");
bulkTransactionObject.setScheduledStartDate("2019-12-11T15:08:03.158Z");
bulkTransactionObject.setTransactions(transactionItems);

// Perform the bulk transaction using the following code

SDKManager.disbursement.createBatchTransaction(NotificationMethod.POLLING,"",bulkTransactionObject, new RequestStateInterface() {
  @Override
  public void onValidationError(ErrorObject errorObject) {
           
  }
  @Override
  public void onRequestStateSuccess(RequestStateObject requestStateObject) {
         
  }

  @Override
  public void onRequestStateFailure(GSMAError gsmaError) {
           
  }
  @Override
  public void getCorrelationId(String correlationID) {
               
  }

});
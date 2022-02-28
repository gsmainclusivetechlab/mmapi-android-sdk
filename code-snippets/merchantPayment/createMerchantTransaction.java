Transaction transactionRequest = new Transaction();
ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
DebitPartyItem debitPartyItem = new DebitPartyItem();
CreditPartyItem creditPartyItem = new CreditPartyItem();

debitPartyItem.setKey("accountid");
debitPartyItem.setValue("Place your account id of debit party here");
debitPartyList.add(debitPartyItem);

creditPartyItem.setKey("accountid");
creditPartyItem.setValue("Place your account id of credit party here");
creditPartyList.add(creditPartyItem);

transactionRequest.setDebitParty(debitPartyList);
transactionRequest.setCreditParty(creditPartyList);
transactionRequest.setAmount("Place your amount"); //eg:200.00
transactionRequest.setCurrency("Place your currency here"); // for eg: RWF

// Initiate the mechant pay request using the following code

SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod,"CALLBACK URL",transactionRequest, new RequestStateInterface() {
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
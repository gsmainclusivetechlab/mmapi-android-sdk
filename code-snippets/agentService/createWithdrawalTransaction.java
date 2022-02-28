Transaction transactionRequest = new Transaction();
ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();

AccountIdentifier debitPartyItem = new AccountIdentifier();
AccountIdentifier creditPartyItem = new AccountIdentifier();

debitPartyItem.setKey("walletid");
debitPartyItem.setValue("1");
debitPartyList.add(debitPartyItem);

creditPartyItem.setKey("msisdn");
creditPartyItem.setValue("+44012345678");
creditPartyList.add(creditPartyItem);

transactionRequest.setDebitParty(debitPartyList);
transactionRequest.setCreditParty(creditPartyList);
transactionRequest.setAmount("200.00");
transactionRequest.setCurrency("RWF");

 SDKManager.agentService.createWithdrawalTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
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
        correlationId = correlationID;

    }

});
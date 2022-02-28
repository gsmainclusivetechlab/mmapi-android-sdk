Transaction  transactionRequest = new Transaction();
//set amount and currency
transactionRequest.setAmount("200");
transactionRequest.setCurrency("RWF");

ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
AccountIdentifier debitPartyItem = new AccountIdentifier();
AccountIdentifier creditPartyItem = new AccountIdentifier();

//debit party
debitPartyItem.setKey("accountid");
debitPartyItem.setValue("2999");
debitPartyList.add(debitPartyItem);

//credit party
creditPartyItem.setKey("linkref");
creditPartyItem.setValue(linkReference);
creditPartyList.add(creditPartyItem);

//add debit and credit party to transaction object
transactionRequest.setDebitParty(debitPartyList);
transactionRequest.setCreditParty(creditPartyList);

SDKManager.accountLinking.createTransferTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
        @Override
        public void onRequestStateSuccess(RequestStateObject requestStateObject) {
           
        }

        @Override
        public void onRequestStateFailure(GSMAError gsmaError) {
           

        }

        @Override
        public void onValidationError(ErrorObject errorObject) {
    
        }

        @Override
        public void getCorrelationId(String correlationID) {

});
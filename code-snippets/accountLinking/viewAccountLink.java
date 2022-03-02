ArrayList<Identifier> identifierArrayList = new ArrayList<>();

//account id
Identifier identifierAccount = new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("2000");

identifierArrayList.add(identifierAccount);

SDKManager.accountLinking.viewAccountLink(identifierArrayList, transactionRef, new AccountLinkInterface() {
        @Override
        public void onAccountLinkSuccess(Link accountLinks) {
             
        }

        @Override
        public void onAccountLinkFailure(GSMAError gsmaError) {
         
        }

        @Override
        public void onValidationError(ErrorObject errorObject) {
        
        }
});
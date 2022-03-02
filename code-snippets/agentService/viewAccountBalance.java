ArrayList<Identifier> identifierArrayList = new ArrayList<>();

//account id
Identifier identifierAccount = new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("1");

identifierArrayList.add(identifierAccount);

SDKManager.agentService.viewAccountBalance(identifierArrayList, new BalanceInterface() {
        @Override
        public void onValidationError(ErrorObject errorObject) {
           

        }

        @Override
        public void onBalanceSuccess(Balance balance) {

        }

        @Override
        public void onBalanceFailure(GSMAError gsmaError) {
           
        }
});
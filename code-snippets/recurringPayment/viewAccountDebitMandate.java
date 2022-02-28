ArrayList<Identifier> identifierArrayList = new ArrayList<>();
identifierArrayList.clear();

//account id
Identifier identifierAccount = new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("2000");

identifierArrayList.add(identifierAccount);

SDKManager.recurringPayment.viewAccountDebitMandate(identifierArrayList, transactionRef, new DebitMandateInterface() {
    @Override
    public void onDebitMandateSuccess(DebitMandate debitMandate) {

    
    }

    @Override
    public void onDebitMandateFailure(GSMAError gsmaError) {

    }

    @Override
    public void onValidationError(ErrorObject errorObject) {
        
    }
});
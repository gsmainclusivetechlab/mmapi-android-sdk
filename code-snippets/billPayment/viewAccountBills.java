ArrayList<Identifier>  identifierArrayList = new ArrayList<>();

//account id
Identifier identifierAccount = new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("1");

identifierArrayList.add(identifierAccount);
TransactionFilter transactionFilter = new TransactionFilter();
transactionFilter.setLimit(5);
transactionFilter.setOffset(0);

SDKManager.billPayment.viewAccountBills(identifierArrayList, transactionFilter, new RetrieveBillPaymentInterface() {
	@Override
    public void onRetrieveBillPaymentSuccess(Bills bills) {
             
    }

    @Override
    public void onRetrieveBillPaymentFailure(GSMAError gsmaError) {            

    }

    @Override
	public void onValidationError(ErrorObject errorObject) {           

    }
});
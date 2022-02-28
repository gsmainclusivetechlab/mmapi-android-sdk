ArrayList<Identifier>  identifierArrayList = new ArrayList<>();

//account id
Identifier identifierAccount = new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("1");

identifierArrayList.add(identifierAccount);

TransactionFilter transactionFilter = new TransactionFilter();
transactionFilter.setLimit(5);
transactionFilter.setOffset(0);

SDKManager.billPayment.viewBillPayment(identifierArrayList, transactionFilter, "REF-000001", new BillPaymentInterface() {
	@Override
    public void onBillPaymentSuccess(BillPayments billPayments) {
              
    }

    @Override
    public void onBillPaymentFailure(GSMAError gsmaError) {
               
    }

    @Override
    public void onValidationError(ErrorObject errorObject) {
         
    }
});
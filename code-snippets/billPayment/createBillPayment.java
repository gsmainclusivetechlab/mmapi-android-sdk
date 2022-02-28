ArrayList<Identifier>  identifierArrayList = new ArrayList<>();

//account id
Identifier identifierAccount = new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("1");

identifierArrayList.add(identifierAccount);

BillPay billPayment = new BillPay();
billPayment.setCurrency("GBP");
billPayment.setAmountPaid("5.30");

SDKManager.billPayment.createBillPayment(NotificationMethod.CALLBACK, "", identifierArrayList, billPayment, "REF-000001", new RequestStateInterface() {
    @Override
    public void onRequestStateSuccess(RequestStateObject requestStateObject) {
               

    }

    @Override
    public void onRequestStateFailure(GSMAError gsmaError) {
              

    }

    @Override
    public void getCorrelationId(String correlationID) {
        correlationId = correlationID;
    }

    @Override
    public void onValidationError(ErrorObject errorObject) {
               
    }
});
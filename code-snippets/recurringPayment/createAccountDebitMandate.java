ArrayList<Identifier>  identifierArrayList = new ArrayList<>();
identifierArrayList.clear();

//account id
Identifier identifierAccount = new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("2000");

identifierArrayList.add(identifierAccount);

debitMandateRequest = new DebitMandate();

//payee object of debit mandate
ArrayList<AccountIdentifier> payeeItemArrayList = new ArrayList<>();

//payee object
AccountIdentifier payeeItem = new AccountIdentifier();
payeeItem.setKey("accountid");
payeeItem.setValue("2999");
payeeItemArrayList.add(payeeItem);

//add items to debit mandate object

debitMandateRequest.setPayee(payeeItemArrayList);
debitMandateRequest.setRequestDate("2018-07-03T10:43:27.405Z");
debitMandateRequest.setStartDate("2018-07-03T10:43:27.405Z");
debitMandateRequest.setCurrency("GBP");
debitMandateRequest.setAmountLimit("1000.00");
debitMandateRequest.setEndDate("2028-07-03T10:43:27.405Z");
debitMandateRequest.setNumberOfPayments("2");
debitMandateRequest.setFrequencyType("sixmonths");


//creating custom data array
ArrayList<CustomDataItem> customDataItemArrayList = new ArrayList<>();

CustomDataItem customDataItem = new CustomDataItem();
customDataItem.setKey("keytest");
customDataItem.setValue("keyvalue");

customDataItemArrayList.add(customDataItem);

debitMandateRequest.setCustomData(customDataItemArrayList);

SDKManager.recurringPayment.createAccountDebitMandate(NotificationMethod.POLLING, "", identifierArrayList, debitMandateRequest, new RequestStateInterface() {
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
        
        }

    });

}
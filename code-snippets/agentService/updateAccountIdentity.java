ArrayList<Identifier>  identifierArrayList = new ArrayList<>();

//account id
Identifier identifierAccount = new Identifier();
identifierAccount.setKey("accountid");
identifierAccount.setValue("2999");

identifierArrayList.add(identifierAccount);

private ArrayList<PatchData> patchDataArrayList;

PatchData patchObject = new PatchData();
patchObject.setOp("replace");
patchObject.setPath("/kycVerificationStatus");
patchObject.setValue("verified");
        
patchDataArrayList = new ArrayList<>();
patchDataArrayList.add(patchObject);

SDKManager.agentService.updateAccountIdentity(NotificationMethod.POLLING, "", identityId, patchDataArrayList, identifierArrayList, new RequestStateInterface() {
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
private ArrayList<PatchData> patchDataArrayList;

//create a batch object
PatchData batchObject = new PatchData();
batchObject.setOp("replace");
batchObject.setPath("/batchStatus");
batchObject.setValue("approved");
patchDataArrayList = new ArrayList<>();
patchDataArrayList.add(batchObject);


// Call the update batch request function with batch id and batch array as input parameter 

SDKManager.disbursement.updateBatchTransaction(NotificationMethod.POLLING,"","Place your batch id here",patchDataArrayList, new RequestStateInterface() {
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
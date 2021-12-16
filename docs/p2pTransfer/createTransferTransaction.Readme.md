# Create a Transaction

`Here, createTransferTransaction() creates a POST request to /transactions/type/transfer`

> `Provided with a valid object representation, this endpoint allows for a new transaction to be created for a given transaction type passed via the URL.`

### Usage/Examples

```java

private TransactionRequest transactionRequest;

```

```java
private void createP2PTransferObject() {
     if (transactionRequest == null) {
            Utils.showToast(this, "Please request Quotation before performing this request");
            return;
        } else {

            //set amount and currency
            transactionRequest.setAmount("100");
            transactionRequest.setCurrency("GBP");

            ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
            ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
            DebitPartyItem debitPartyItem = new DebitPartyItem();
            CreditPartyItem creditPartyItem = new CreditPartyItem();

            //debit party
            debitPartyItem.setKey("accountid");
            debitPartyItem.setValue("2999");
            debitPartyList.add(debitPartyItem);

            //credit party
            creditPartyItem.setKey("accountid");
            creditPartyItem.setValue("2000");
            creditPartyList.add(creditPartyItem);

            //create international information object to perform international transfer

            InternationalTransferInformation internationalTransferInformation = new InternationalTransferInformation();
            internationalTransferInformation.setOriginCountry("AD");
            internationalTransferInformation.setQuotationReference("REF-1636521507766");
            internationalTransferInformation.setQuoteId("REF-1636521507766");
            internationalTransferInformation.setRemittancePurpose("personal");
            internationalTransferInformation.setDeliveryMethod("agent");
            transactionRequest.setInternationalTransferInformation(internationalTransferInformation);

            RequestingOrganisation requestingOrganisation = new RequestingOrganisation();
            requestingOrganisation.setRequestingOrganisationIdentifierType("organisationid");
            requestingOrganisation.setRequestingOrganisationIdentifier("testorganisation");

            //add requesting organisation object into transaction request
            transactionRequest.setRequestingOrganisation(requestingOrganisation);

            //add debit and credit party to transaction object
            transactionRequest.setDebitParty(debitPartyList);
            transactionRequest.setCreditParty(creditPartyList);

            performTransfer();
        }
    }


```


```
 private void performTransfer() {
    SDKManager.getInstance().createTransferTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                   serverCorrelationId = requestStateObject.getServerCorrelationId();
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

```


### Example Output - Callback

```json
202
{
    "serverCorrelationId": "85025241-57e6-49b7-b9b4-84c45974a75f",
    "status": "pending",
    "notificationMethod": "callback",
    "objectReference": "14452",
    "pollLimit": 100
}
```

### Example Output - Polling

```json
202
{
  "serverCorrelationId": "b501aeeb-c3b8-45d8-9fc7-375fb42ea87e",
  "status": "pending",
  "notificationMethod": "polling",
  "objectReference": "14457",
  "pollLimit": 100
}
```
**NOTE**

In asynchronous flows, a callback mechanism or polling mechanism is utilised to allow the client to determine the request's final state. Use the [viewRequestState()](viewRequestState.Readme.md) function for the polling mechanism to receive the status of a request, and the [viewTransaction()](viewTransaction.Readme.md) function to acquire the final representation of the Transaction object.

---

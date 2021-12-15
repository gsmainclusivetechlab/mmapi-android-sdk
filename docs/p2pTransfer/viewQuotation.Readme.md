# View A Quotation

 View a quotation details using transacation reference 

### Usage/Examples

```
    private String transactionRef = "";

```

```

        SDKManager.p2PTransfer.viewQuotation(transactionRef, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(Transaction transactionObject) {
          
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
               
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
               
            }
        });

```

### Example Output

```json
200
{
  "quotationReference": "REF-1636533162268",
  "creditParty": [
    {
      "key": "accountid",
      "value": "2000"
    },
    {
      "key": "linkref",
      "value": "REF-1621839627337"
    },
    {
      "key": "linkref",
      "value": "REF-1635445811066"
    }
  ],
  "debitParty": [
    {
      "key": "accountid",
      "value": "2999"
    }
  ],
  "type": "inttransfer",
  "subType": "abc",
  "quotationStatus": "completed",
  "requestAmount": "75.30",
  "requestCurrency": "RWF",
  "chosenDeliveryMethod": "agent",
  "originCountry": "AD",
  "receivingCountry": "AD",
  "senderKyc": {
    "nationality": "GB",
    "dateOfBirth": "1970-07-03",
    "occupation": "Manager",
    "employerName": "MFX",
    "contactPhone": "+447125588999",
    "gender": "m",
    "idDocument": [
      {
        "idType": "nationalidcard",
        "idNumber": "1234567",
        "issueDate": "2018-07-03",
        "expiryDate": "2021-07-03",
        "issuer": "UKPA",
        "issuerPlace": "GB",
        "issuerCountry": "GB"
      }
    ],
    "postalAddress": {
      "addressLine1": "111 ABC Street",
      "city": "New York",
      "stateProvince": "New York",
      "postalCode": "ABCD",
      "country": "GB"
    },
    "subjectName": {
      "title": "Mr",
      "firstName": "Luke",
      "middleName": "R",
      "lastName": "Skywalker",
      "fullName": "Luke R Skywalker",
      "nativeName": "ABC"
    },
    "emailAddress": "luke.skywalkeraaabbb@gmail.com",
    "birthCountry": "GB"
  },
  "sendingServiceProviderCountry": "AD",
  "creationDate": "2021-11-10T08:32:42",
  "modificationDate": "2021-11-10T08:32:42",
  "requestDate": "2018-07-03T11:43:27",
  "customData": [
    {
      "key": "keytest",
      "value": "keyvalue"
    }
  ]
}
```


# View Account Specific Transaction

`Here, viewAccountTransactions(Identifiers identifiers, TransactionFilter filter) creates a GET request to /accounts/{identifierType}/{identifier}/transactions`

> `This endpoint returns transactions linked to a specific account.`


```java
 
        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);
   

```

```java

        TransactionFilter transactionFilter=new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        SDKManager.merchantPayment.viewAccountTransactions(identifierArrayList, transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
            
            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {
            
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
               
            }
        });
 
 
 

```

### Example Output

```json
200
[
    {
        "transactionReference": "REF-1620028406917",
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
        "transactionStatus": "completed",
        "amount": "100.00",
        "currency": "GBP",
        "internationalTransferInformation": {
            "originCountry": "GB",
            "quotationReference": "{{quotationReference}}",
            "quoteId": "{{quoteId}}",
            "deliveryMethod": "agent",
            "receivingCountry": "RW",
            "relationshipSender": "none",
            "remittancePurpose": "personal",
            "sendingServiceProviderCountry": "AD"
        },
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
        "requestingOrganisation": {
            "requestingOrganisationIdentifierType": "organisationid",
            "requestingOrganisationIdentifier": "testorganisation"
        },
        "creationDate": "2021-05-03T08:53:27",
        "modificationDate": "2021-05-03T08:53:27",
        "requestDate": "2021-05-03T08:53:27"
    },
    
    {
        "transactionReference": "REF-1620133857481",
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
        "transactionStatus": "pending",
        "amount": "100.00",
        "currency": "GBP",
        "internationalTransferInformation": {
            "originCountry": "GB",
            "quotationReference": "{{quotationReference}}",
            "quoteId": "{{quoteId}}",
            "deliveryMethod": "agent",
            "receivingCountry": "RW",
            "relationshipSender": "none",
            "remittancePurpose": "personal",
            "sendingServiceProviderCountry": "AD"
        },
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
        "requestingOrganisation": {
            "requestingOrganisationIdentifierType": "organisationid",
            "requestingOrganisationIdentifier": "testorganisation"
        },
        "creationDate": "2021-05-04T14:10:57",
        "modificationDate": "2021-05-04T14:10:57",
        "requestDate": "2021-05-04T14:10:57"
    }
]
```

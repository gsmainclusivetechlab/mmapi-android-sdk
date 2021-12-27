# View A Link

`Here, viewAccountLink(Identifiers identifiers, String linkReference) creates a GET request to /accounts/{accountId}/links/{linkReference}`

> `This endpoint returns a specific link.`
### Usage/Examples

```java
       identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");

        identifierArrayList.add(identifierAccount);
```
```java

 SDKManager.accountLinking.viewAccountLink(identifierArrayList, transactionRef, new AccountLinkInterface() {
            @Override
            public void onAccountLinkSuccess(Link accountLinks) {
             
            }

            @Override
            public void onAccountLinkFailure(GSMAError gsmaError) {
         
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
        
            }
        });
```


### Response Example

```java
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
    ...,
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

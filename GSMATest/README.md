
# MMAPI-Sample App for Android SDK

This is a sample app provided to demonstrate the working of MMAPI SDK in android



## Getting started



As usual, you get started by 

* Installing  the apk in real device

* Cloning the project into local machine



## How  to install the apk in real Device

1.Download the file GSMATest-v1.0.5 from the following link into the filemanager of your  device
 
[Download](https://github.com/gsmainclusivetechlab/mmapi-android-sdk/raw/develop/release/GSMATest-v1.0.5.apk)

2.Click on the file GSMATest-v1.0.5 from your device and system will ask for the installation dialog and continue the installation process 

3.Once the Apk is installed in your device,Open the application from app drawer of your device 


## How to clone the project into local machine


Clone the project using SSH or HTTPS 

#### HTTPS

```
https://github.com/gsmainclusivetechlab/mmapi-android-sdk.git

```

#### SSH  

```
git@github.com:gsmainclusivetechlab/mmapi-android-sdk.git

```

#### Steps to run test app using android studio

1.Open this repo in Android Studio,

2.Select GSMATest Module and run the test module using android Emulator or Real device and then open the application from the installed device


#### Prerequisites

1.Android Studio 

2.JDK 8 (or later)

3.SDK

## How to test sample app

1.Once's the app is deployed in your device,Open the application and it will redirect to the Landing page

2.The landing page will have list of all uses cases

3.Click on use case link and the app will redirect to merchant payment activity

4.The activity contains all scenarios for a particular use case

5.Each uses cases can be tested using the link  provided in the test application

# Use cases

* Merchant Payments
* Disbursements
* International Transfers
* P2P Transfers
* Recurring Payments
* Account Linking
* Bill Payments
* Agent Services (including Cash-In and Cash-Out)


# Merchant Payment

* [Payee-Initiated Merchant Payment](#payee-initiated)
* [Payee-Initiated Merchant Payment using the Polling Method](#payee-initiated-polling)
* [Payer-Initiated Merchant Payment](#payee-initiated)
* [Payee-Initiated Merchant Payment using a Pre-authorised Payment Code](#auth-code)
* [Merchant Payment Refund](#refund)
* [Merchant Payment Reversal](#reversal)
* [Obtain a Merchant Balance](#balance)
* [Retrieve Payments for a Merchant](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response](#missing-response)


# Disbursement


* [Individual Disbursement](#individual-disbursement)
* [Bulk Disbursement](#bulk-disbursement)
* [Bulk Disbursement with Maker / Checker](#bulk-disbursement-maker)
* [Individual Disbursement Using the Polling Method](#disbursment-polling)
* [Disbursement Reversal](#reversal)
* [Obtain a Disbursement Organisation Balance](#balance)
* [Retrieve Transactions for a Disbursement Organisation](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response]((#missing-response))

# International Transfers

* [International Transfer via Hub](#international-transfer-hub)
* [Bilateral International Transfer](#international-transfer-hub)
* [International Transfer Reversal](#reversal)
* [Obtain an FSP Balance](#balance)
* [Retrieve Transactions for an FSP](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response](#missing-response)



# P2P Transfers


* [P2P Transfer via Switch](#p2p-transfer-switch)
* [Bilateral P2P Transfer](#p2p-transfer-bilateral)
* [‘On-us’ P2P Transfer Initiated by a Third Party Provider](#p2p-transfer-switch)
* [P2P Transfer Reversal](#reversal)
* [Obtain an FSP Balance](#balance)
* [Retrieve Transactions for an FSP](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response](#missing-response)

# Recurring Payments

* [Setup a Recurring Payment](#setup-recurring)
* [Take a Recurring Payment](#take-recurring)
* [Take a Recurring Payment using the Polling Method](#take-polling)
* [Recurring Payment Refund](#refund)
* [Recurring Payment Reversal](#reversal)
* [Payer sets up a Recurring Payment using MMP Channel](#setup-recurring)
* [Obtain a Service Provider Balance](#balance)
* [Retrieve Payments for a Service Provider](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response](#missing-response)

# Account Linking

* [Setup an Account Link](#setup-accountlink)
* [Perform a Transfer for a Linked Account](#transfer-account-link)
* [Perform a Transfer using an Account Link via the Polling Method]
* [Perform a Transfer Reversal]
* [Obtain a Financial Service Provider Balance](#balance)
* [Retrieve Transfers for a Financial Service Provider](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response](#missing-response)

# Bill Payment

* [Successful Retrieval of Bills](#retrieval-bills)
* [Make a Successful Bill Payment with Callback](#make-bill-callback)
* [Make a Bill Payment with Polling](#make-bill-callback)
* [Retrieval of Bill Payments](#retrieve-payments)
* [Check for Service Availability])(#check-for-service))





<a name="payee-initiated"></a>

# Payee/Payer initiated Merchant Payment
 
 In this scenario the payer/payee can initiate a payment request,Click on the following buttons in test app to perform merchant payment
 
 * Payee Initiated
 
 The expected output of this request is given below
 
 ### Example Output - Polling

```json
 {
	"notificationMethod": "polling",
	"objectReference": "15596",
	"pollLimit": 100,
	"serverCorrelationId": "d6582f57-f353-45b7-962b-7e35bda38765",
	"status": "pending"
}
```

### Example Output - Callback
 
In the test app default notification method is polling and you can change notification methods from the sample code to callback if needed

The sample output if the notification method is callback 

```json
 {
	"notificationMethod": "callback",
	"objectReference": "15596",
	"pollLimit": 100,
	"serverCorrelationId": "d6582f57-f353-45b7-962b-7e35bda38765",
	"status": "pending"
}
```

<a name="payee-initiated-polling"></a>

# Payee-Initiated Merchant Payment using the Polling Method

The polling scenario can be completed by clicking following buttons of sample app in sequential order

* Payee Initiated
* Request State
* View Transaction


 
 ### Example Output - Payee Initiated

```json
 {
	"notificationMethod": "polling",
	"objectReference": "15596",
	"pollLimit": 100,
	"serverCorrelationId": "d6582f57-f353-45b7-962b-7e35bda38765",
	"status": "pending"
}
```
 The serverCorrelationId is obtained from the result of payee initiated request,This serverCorrelationId is passed to request state function to view the request state of a transaction

 
 ### Example Output - Request State

```json
{
	"notificationMethod": "polling",
	"objectReference": "REF-1638274655726",
	"pollLimit": 100,
	"serverCorrelationId": "d6582f57-f353-45b7-962b-7e35bda38765",
	"status": "completed"
}

```
The object reference obtained from the request state is passed to view transaction function,The view transaction function will retrieve the details of the transaction

 ### Example Output - View Transaction
 
 ```json
 
 {
	"transactionReference": "REF-1638274655726",
	"creditParty": [{
		"key": "msisdn",
		"value": "+44012345678"
	}],
	"debitParty": [{
		"key": "msisdn",
		"value": "+449999999"
	}, {
		"key": "linkref",
		"value": "REF-1614172481727"
	}],
	"type": "merchantpay",
	"transactionStatus": "completed",
	"amount": "200.00",
	"currency": "RWF",
	"creationDate": "2021-11-30T12:37:15",
	"modificationDate": "2021-11-30T12:37:15",
	"requestDate": "2021-11-30T12:37:15"
}
 
 ```

<a name="auth-code"></a>

# Payee-Initiated Merchant Payment using a Pre-authorised Payment Code

The Payee-Initiated Merchant Payment using a Pre-authorised Payment Code can be completed by clicking the following buttons in sequential order

* Auth Code
* Request State
* View Auth Code
* Payee Initiated



 ### Example Output - Auth Code
 ```json
 {
	"notificationMethod": "callback",
	"objectReference": "1839",
	"pollLimit": 100,
	"serverCorrelationId": "3fa62436-9935-468c-8911-62263e6272c3",
	"status": "pending"
}

 
```
 The serverCorrelationId is obtained from the result of payee intiated request,This serverCorrelationId is passed to request state function to view the request state of a   transaction 
 
### Example Output - Request State

```json
{
	"notificationMethod": "polling",
	"objectReference": "93e2e3ac-e5ee-470e-a1de-ae9757e63106",
	"pollLimit": 100,
	"serverCorrelationId": "3fa62436-9935-468c-8911-62263e6272c3",
	"status": "completed"
}

```
The objectReference is passed as a parameter to view auth code function to retrieve the authorisation code

### Example Output - View Auth Code

```json
 {
 	"amount": "200.00",
 	"authorisationCode": "93e2e3ac-e5ee-470e-a1de-ae9757e63106",
 	"codeState": "active",
 	"creationDate": "2021-11-30T13:42:09",
 	"currency": "RWF",
 	"modificationDate": "2021-11-30T13:42:09",
 	"redemptionAccountIdentifiers": [{
 		"key": "accountid",
 		"value": "2999"
 	},{
 		"key": "mandatereference",
 		"value": "REF-1637907483978"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637909732171"
 	}],
 	"requestDate": "2021-10-18T10:43:27"
 }
```

### Example Output - Payee Initiated 

```json
 {
	"notificationMethod": "polling",
	"objectReference": "15596",
	"pollLimit": 100,
	"serverCorrelationId":"3fa62436-9935-468c-8911-62263e6272c3",
	"status": "pending"
}

```

<a name="refund"></a>

# Payment Refund

 The refund scenario  can be completed by using following buttons
 
 * Refund

### Example Output - Refund


```json
{
	"notificationMethod": "polling",
	"objectReference": "15621",
	"pollLimit": 100,
	"serverCorrelationId": "4c9313cf-185a-4335-87ae-feced48f1ee2",
	"status": "pending"
}

```

<a name="reversal"></a>

# Payment Reversal

 The reversal scenario  can be completed by using following buttons
 
  * Reversal

### Example Output - Reversal


```json

{
	"notificationMethod": "polling",
	"objectReference": "4572",
	"pollLimit": 100,
	"serverCorrelationId": "ca3eb926-5b8c-4d11-b184-06782408208e",
	"status": "pending"
}

```
<a name="balance"> </a>

# Balance

The balance scenario can be completed by using following methods

* Balance

### Example Output - Balance


```json
 {
 	"accountStatus": "available",
 	"availableBalance": "0.00",
 	"currentBalance": "0.00",
 	"reservedBalance": "0.00",
 	"unclearedBalance": "0.00"
 }

```
<a name="retrieve-payments"></a>


# Retrieve a set of transaction
 
  The retrieve Transaction can be completed by clicking following button
 
 * Retrieve transaction

### Example Output - Retrieve Transaction

```json
 {
 	"Transaction": [{
 		"amount": "200.00",
 		"creationDate": "2021-04-10T09:58:12",
 		"creditParty": [{
 			"key": "accountid",
 			"value": "2999"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907197912"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907232832"
 		}],
 		"currency": "RWF",
 		"debitParty": [{
 			"key": "accountid",
 			"value": "2999"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907197912"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907232832"
 		}],
 		"modificationDate": "2021-04-10T09:58:12",
 		"requestDate": "2021-04-10T09:58:12",
 		"transactionReference": "REF-1618045092324",
 		"transactionStatus": "pending",
 		"type": "merchantpay"
 	}, {
 		"amount": "200.00",
 		"creationDate": "2021-04-10T09:58:35",
 		"creditParty": [{
 			"key": "accountid",
 			"value": "2999"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907197912"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907232832"
 		}],
 		"currency": "RWF",
 		"debitParty": [{
 			"key": "accountid",
 			"value": "2999"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907197912"
 		}, {
 			"key": "mandatereference",
 			"value": "REF-1637907232832"
 		}],
 		"modificationDate": "2021-04-10T09:58:35",
 		"requestDate": "2021-04-10T09:58:35",
 		"transactionReference": "REF-1618045115056",
 		"transactionStatus": "pending",
 		"type": "merchantpay"
 	}]
 }

```

<a name="check-for-service"></a>

# Check for Service Availability

The service functionality will trigger automatically when we select merchant payment use cases

### Example Output - Check for Service

```json
{
	"serviceStatus": "available"
}

```

<a name="missing-response"></a>

# Retrieve a Missing API Response

 The missing response of an request can be performed using the correlation id used for the request,Pass the correlationId obtained for a transaction request to get the missing  response of a particular API
 
For eg:if the transaction response is missing,To retrieve the missing response of transaction click the following button in sequential order

* Payee Initiated
* Missing Transaction

### Example Output - Payee Initiated

```json
{
	"notificationMethod": "polling",
	"objectReference": "93e2e3ac-e5ee-470e-a1de-ae9757e63106",
	"pollLimit": 100,
	"serverCorrelationId": "3fa62436-9935-468c-8911-62263e6272c3",
	"status": "completed"
}

```


### Example Output - Missing Transaction

```json
{
	"notificationMethod": "polling",
	"objectReference": "93e2e3ac-e5ee-470e-a1de-ae9757e63106",
	"pollLimit": 100,
	"serverCorrelationId": "3fa62436-9935-468c-8911-62263e6272c3",
	"status": "completed"
}

```

<a name="individual-disbursement"></a>


# Individual Disbursement

The individual disbursement can be completed by clicking following buttons

* Individual Disbursement


### Example Output - Individual Disbursement

```json
{
	"notificationMethod": "polling",
	"objectReference": "93e2e3ac-e5ee-470e-a1de-ae9757e63106",
	"pollLimit": 100,
	"serverCorrelationId": "3fa62436-9935-468c-8911-62263e6272c3",
	"status": "completed"
}

```

<a name="bulk-disbursement"></a>

# Bulk Disbursement

The bulk Disbursement can be completed by clicking following button in sequential order

* Bulk Transaction
* Request State
* Get Batch Details
* Batch Completion
* Batch Rejection


### Example Output - Bulk Transaction

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "1153",
 	"pollLimit": 100,
 	"serverCorrelationId": "fa8b8839-7323-45c2-8319-a075395307a7",
 	"status": "pending"
 }
 
```

### Example Output - Request State

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638337785175",
 	"pollLimit": 100,
 	"serverCorrelationId": "fa8b8839-7323-45c2-8319-a075395307a7",
 	"status": "completed"
 }

```

 ### Example Output - Get Batch Details

```json
 
  {
 	"batchDescription": "Testing a Batch transaction",
 	"batchId": "REF-1638337785175",
 	"batchStatus": "created",
 	"batchTitle": "Batch Test",
 	"completedCount": 0,
 	"creationDate": "2021-12-01T05:49:45",
 	"modificationDate": "2021-12-01T05:49:45",
 	"parsingSuccessCount": 0,
 	"processingFlag": false,
 	"rejectionCount": 0,
 	"requestDate": "2021-12-01T05:49:45",
 	"scheduledStartDate": "2019-12-11T15:08:03"
 }
 

```

 ### Example Output - Batch Completion

```json
{
	"BatchTransactionCompletion": []
}

```


 ### Example Output - Batch Rejection

```json
{
	"BatchTransactionRejection": []
}

```


<a name="bulk-disbursement-maker"></a>
# Bulk Disbursement using Maker/Checker

The bulk Disbursement can be completed by clicking following button in sequential order

* Bulk Transaction
* Request State
* Update Batch 
* Get Batch Details
* Batch Completion
* Batch Rejection


### Example Output - Bulk Transaction

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "1153",
 	"pollLimit": 100,
 	"serverCorrelationId": "fa8b8839-7323-45c2-8319-a075395307a7",
 	"status": "pending"
 }
 
```

### Example Output - Request State

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638337785175",
 	"pollLimit": 100,
 	"serverCorrelationId": "fa8b8839-7323-45c2-8319-a075395307a7",
 	"status": "completed"
 }

```

### Example Output - Update Batch

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638337785175",
 	"pollLimit": 100,
 	"serverCorrelationId": "fa8b8839-7323-45c2-8319-a075395307a7",
 	"status": "completed"
 }

```


 ### Example Output - Get Batch Details

```json
 
  {
 	"batchDescription": "Testing a Batch transaction",
 	"batchId": "REF-1638337785175",
 	"batchStatus": "created",
 	"batchTitle": "Batch Test",
 	"completedCount": 0,
 	"creationDate": "2021-12-01T05:49:45",
 	"modificationDate": "2021-12-01T05:49:45",
 	"parsingSuccessCount": 0,
 	"processingFlag": false,
 	"rejectionCount": 0,
 	"requestDate": "2021-12-01T05:49:45",
 	"scheduledStartDate": "2019-12-11T15:08:03"
 }
 

```

 ### Example Output - Batch Completion

```json
{
	"BatchTransactionCompletion": []
}

```


 ### Example Output - Batch Rejection

```json
{
	"BatchTransactionRejection": []
}

```

<a name="disbursment-polling"></a>

# Individual Disbursement Using the Polling Method

Disbursement using polling method can be completed using clicking following buttons in sequential order

* Individual Disbursement
* Request State
* View Transaction

### Example Output - Individual Disbursement

```json
{
	 {
 	"notificationMethod": "polling",
 	"objectReference": "15673",
 	"pollLimit": 100,
 	"serverCorrelationId": "edcb2346-1829-4ce8-b171-2a4b1a105a21",
 	"status": "pending"
 }
}

```

 ### Example Output - Request State

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638339051465",
 	"pollLimit": 100,
 	"serverCorrelationId": "edcb2346-1829-4ce8-b171-2a4b1a105a21",
 	"status": "completed"
 }

```
The object reference obtained from the request state is passed to view transaction function,The view transaction function will retrieve the details of the transaction

 ### Example Output - View Transaction
 
 ```json
 
 {
	"transactionReference": "REF-1638274655726",
	"creditParty": [{
		"key": "msisdn",
		"value": "+44012345678"
	}],
	"debitParty": [{
		"key": "msisdn",
		"value": "+449999999"
	}, {
		"key": "linkref",
		"value": "REF-1614172481727"
	}],
	"type": "merchantpay",
	"transactionStatus": "completed",
	"amount": "200.00",
	"currency": "RWF",
	"creationDate": "2021-11-30T12:37:15",
	"modificationDate": "2021-11-30T12:37:15",
	"requestDate": "2021-11-30T12:37:15"
}
```
<a name="international-transfer-hub"></a>
# International Transfer via Hub/Bilateral International Transfer

This use case can be completed by clicking following button in sequential order

<<<<<<< HEAD
* Request a international Transfer Quotation
* Perform a international Transfer

 ### Example Output - Request a international Transfer Quotation

* Request a international Transfer Quotation
* Perform a international Transfer

 ### Example Output - Request a international Transfer Quotation
 
 ```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "1295",
 	"pollLimit": 100,
 	"serverCorrelationId": "b9b86e55-7b1b-446d-8a2b-ab28894e37bf",
 	"status": "pending"
 }
```

### Example Output - Perform a international Transfer
 
 ```json
{
 	"notificationMethod": "polling",
 	"objectReference": "1295",
 	"pollLimit": 100,
 	"serverCorrelationId": "b9b86e55-7b1b-446d-8a2b-ab28894e37bf",
 	"status": "pending"
 }
```
Use polling or callback scenario to get the complete status for a transaction  


<a name="p2p-transfer-switch"></a>

# P2P Transfer via Switch/On-us’ P2P Transfer Initiated by a Third Party Provider

The p2 transfer via switch can be completed by clicking the following buttons

* Retrieve the Name of the Recipient
* Request a P2P Quotation
* Perform a  p2p Transfer


 ### Example Output - Retrieve the Name of the Recipient
 
 ```json
{
 	"lei": "AAAA0012345678901299",
 	"name": {
 		"firstName": "Jeff",
 		"fullName": "Jeff Jimmer",
 		"lastName": "Jimmer",
 		"middleName": "James",
 		"title": "Mr"
 	}
 }
```
 ### Example Output - Request a P2P Quotation
 
 ```json
 {
	"notificationMethod": "polling",
	"objectReference": "1306",
	"pollLimit": 100,
	"serverCorrelationId": "e97885d3-2686-48ca-b66d-dfae1cb4ae42",
	"status": "pending"
}

```
 ### Example Output - Perform a  p2p Transfer


 ```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "15681",
 	"pollLimit": 100,
 	"serverCorrelationId": "eb3ca49e-3d5d-4050-81b6-ebc0fa6b053e",
 	"status": "pending"
 }

```

<a name="p2p-transfer-bilateral"></a>

# Bilateral P2P Transfer

The p2 transfer via switch can be completed by clicking the following buttons

* Retrieve the Name of the Recipient
* Perform a  p2p Transfer


 ### Example Output - Retrieve the Name of the Recipient
 
 ```json
{
 	"lei": "AAAA0012345678901299",
 	"name": {
 		"firstName": "Jeff",
 		"fullName": "Jeff Jimmer",
 		"lastName": "Jimmer",
 		"middleName": "James",
 		"title": "Mr"
 	}
 }
```
 ### Example Output - Perform a  p2p Transfer


 ```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "15681",
 	"pollLimit": 100,
 	"serverCorrelationId": "eb3ca49e-3d5d-4050-81b6-ebc0fa6b053e",
 	"status": "pending"
 }

```
<a name="setup-recurring"></a>

# Set up a recurring payment

Set up a recurring payment can be completed by clicking the following buttons

* Create a debit Mandate 

 ### Example Output - Create a debit Mandate


 ```json

 {
 	"notificationMethod": "polling",
 	"objectReference": "428",
 	"pollLimit": 100,
 	"serverCorrelationId": "70089e23-35ca-4e29-a166-4668024cb236",
 	"status": "pending"
 }

```
<a name="take-recurring"></a>

# Take a Recurring Payment

 Take a recurring payment can be completed by passing debit mandate reference to merchant payment functions


* Create a Debit Mandate
* Request State
* Read a Debit Mandate
* Merchant Payment using Debit Mandate

 ### Example Output - Create a Debit Mandate
 

 ```json

 {
 	"notificationMethod": "polling",
 	"objectReference": "432",
 	"pollLimit": 100,
 	"serverCorrelationId": "82da04e9-05f5-4b1e-96f9-06b21deb7fc4",
 	"status": "pending"
 }

 
 ```

 ### Example Output - Request State
 
 ```json
{
	"notificationMethod": "polling",
	"objectReference": "REF-1638343640945",
	"pollLimit": 100,
	"serverCorrelationId": "82da04e9-05f5-4b1e-96f9-06b21deb7fc4",
	"status": "completed"
}
```


 ### Example Output - Read a Debit Mandate
 
 ```json
 {
 	"amountLimit": "1000.00",
 	"creationDate": "2021-12-01T07:27:21",
 	"currency": "GBP",
 	"customData": [{
 		"key": "keytest",
 		"value": "keyvalue"
 	}],
 	"endDate": "2028-07-03",
 	"frequencyType": "sixmonths",
 	"mandateReference": "REF-1638343640945",
 	"mandateStatus": "active",
 	"modificationDate": "2021-12-01T07:27:21",
 	"numberOfPayments": "2",
 	"payee": [{
 		"key": "accountid",
 		"value": "2999"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907197912"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907232832"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907265888"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907412029"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907483978"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637909732171"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1638330257762"
 	}],
 	"requestDate": "2018-07-03T10:43:27",
 	"startDate": "2018-07-03"
 }

```


 ### Example Output -  Merchant Payment using Debit Mandate
 
 ```json

{
	"notificationMethod": "polling",
	"objectReference": "15684",
	"pollLimit": 100,
	"serverCorrelationId": "164b0df3-ddf0-4459-96e2-82b4514a8c17",
	"status": "pending"
}


```

<a name="take-polling"></a>

# Take a recurring payment using polling method

<a name="p2p-transfer-switch"></a>

# P2P Transfer via Switch/On-us’ P2P Transfer Initiated by a Third Party Provider

The p2 transfer via switch can be completed by clicking the following buttons

* Retrieve the Name of the Recipient
* Request a P2P Quotation
* Perform a  p2p Transfer


 ### Example Output - Retrieve the Name of the Recipient
 
 ```json
{
 	"lei": "AAAA0012345678901299",
 	"name": {
 		"firstName": "Jeff",
 		"fullName": "Jeff Jimmer",
 		"lastName": "Jimmer",
 		"middleName": "James",
 		"title": "Mr"
 	}
 }
```
 ### Example Output - Request a P2P Quotation
 
 ```json
 {
	"notificationMethod": "polling",
	"objectReference": "1306",
	"pollLimit": 100,
	"serverCorrelationId": "e97885d3-2686-48ca-b66d-dfae1cb4ae42",
	"status": "pending"
}

```
 ### Example Output - Perform a  p2p Transfer


 ```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "15681",
 	"pollLimit": 100,
 	"serverCorrelationId": "eb3ca49e-3d5d-4050-81b6-ebc0fa6b053e",
 	"status": "pending"
 }

```

<a name="p2p-transfer-bilateral"></a>

# Bilateral P2P Transfer

The p2 transfer via switch can be completed by clicking the following buttons

* Retrieve the Name of the Recipient
* Perform a  p2p Transfer


 ### Example Output - Retrieve the Name of the Recipient
 
 ```json
{
 	"lei": "AAAA0012345678901299",
 	"name": {
 		"firstName": "Jeff",
 		"fullName": "Jeff Jimmer",
 		"lastName": "Jimmer",
 		"middleName": "James",
 		"title": "Mr"
 	}
 }
```
 ### Example Output - Perform a  p2p Transfer


 ```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "15681",
 	"pollLimit": 100,
 	"serverCorrelationId": "eb3ca49e-3d5d-4050-81b6-ebc0fa6b053e",
 	"status": "pending"
 }

```
<a name="setup-recurring"></a>

# Set up a recurring payment

Set up a recurring payment can be completed by clicking the following buttons

* Create a debit Mandate 

 ### Example Output - Create a debit Mandate


 ```json

 {
 	"notificationMethod": "polling",
 	"objectReference": "428",
 	"pollLimit": 100,
 	"serverCorrelationId": "70089e23-35ca-4e29-a166-4668024cb236",
 	"status": "pending"
 }

```
<a name="take-recurring"></a>

# Take a Recurring Payment

 Take a recurring payment can be completed by passing debit mandate reference to merchant payment functions


* Create a Debit Mandate
* Request State
* Read a Debit Mandate
* Merchant Payment using Debit Mandate

 ### Example Output - Create a Debit Mandate
 

 ```json

 {
 	"notificationMethod": "polling",
 	"objectReference": "432",
 	"pollLimit": 100,
 	"serverCorrelationId": "82da04e9-05f5-4b1e-96f9-06b21deb7fc4",
 	"status": "pending"
 }

 
 ```

 ### Example Output - Request State
 
 ```json
{
	"notificationMethod": "polling",
	"objectReference": "REF-1638343640945",
	"pollLimit": 100,
	"serverCorrelationId": "82da04e9-05f5-4b1e-96f9-06b21deb7fc4",
	"status": "completed"
}
```


 ### Example Output - Read a Debit Mandate
 
 ```json
 {
 	"amountLimit": "1000.00",
 	"creationDate": "2021-12-01T07:27:21",
 	"currency": "GBP",
 	"customData": [{
 		"key": "keytest",
 		"value": "keyvalue"
 	}],
 	"endDate": "2028-07-03",
 	"frequencyType": "sixmonths",
 	"mandateReference": "REF-1638343640945",
 	"mandateStatus": "active",
 	"modificationDate": "2021-12-01T07:27:21",
 	"numberOfPayments": "2",
 	"payee": [{
 		"key": "accountid",
 		"value": "2999"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907197912"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907232832"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907265888"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907412029"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907483978"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637909732171"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1638330257762"
 	}],
 	"requestDate": "2018-07-03T10:43:27",
 	"startDate": "2018-07-03"
 }

```


 ### Example Output -  Merchant Payment using Debit Mandate
 
 ```json

{
	"notificationMethod": "polling",
	"objectReference": "15684",
	"pollLimit": 100,
	"serverCorrelationId": "164b0df3-ddf0-4459-96e2-82b4514a8c17",
	"status": "pending"
}


```

<a name="take-polling"></a>

# Take a recurring payment using polling method


Click the following buttons to perform take a recurring payment using following,Before that make sure that you have completed take a recurring payment scenario

* Request State
* View Transaction

 ### Example Output - Request State

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638339051465",
 	"pollLimit": 100,
 	"serverCorrelationId": "edcb2346-1829-4ce8-b171-2a4b1a105a21",
 	"status": "completed"
 }

```
The object reference obtained from the request state is passed to view transaction function,The view transaction function will retrieve the details of the transaction

 ### Example Output - View Transaction
 
 ```json
 
 {
	"transactionReference": "REF-1638274655726",
	"creditParty": [{
		"key": "msisdn",
		"value": "+44012345678"
	}],
	"debitParty": [{
		"key": "msisdn",
		"value": "+449999999"
	}, {
		"key": "linkref",
		"value": "REF-1614172481727"
	}],
	"type": "merchantpay",
	"transactionStatus": "completed",
	"amount": "200.00",
	"currency": "RWF",
	"creationDate": "2021-11-30T12:37:15",
	"modificationDate": "2021-11-30T12:37:15",
	"requestDate": "2021-11-30T12:37:15"
}
```

<a name="setup-accountlink"></a>

# [Setup an Account Link]

The setup an account link scenario can be completed by clicking the following buttons

* Create a Account Link
* Request State
* View an account Link

 ### Example Output - Create a Debit Mandate
 ```json
   {
 	"notificationMethod": "polling",
 	"objectReference": "440",
 	"pollLimit": 100,
 	"serverCorrelationId": "3bebab13-8ca6-479e-90dc-05fd134ec80b",
 	"status": "pending"
 }
 ```
 
 ### Example Output - Request State
 
 ```json
  {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638425137077",
 	"pollLimit": 100,
 	"serverCorrelationId": "3bebab13-8ca6-479e-90dc-05fd134ec80b",
 	"status": "completed"
 }
 ```
  ### Example Output - View an account Link
 
 ```json


 {
 	"creationDate": "2021-12-02T06:36:52",
 	"customData": [{
 		"key": "keytest",
 		"value": "keyvalue"
 	}],
 	"linkReference": "REF-1638427012132",
 	"mode": "active",
 	"modificationDate": "2021-12-02T06:36:52",
 	"requestingOrganisation": {},
 	"sourceAccountIdentifiers": [{
 		"key": "accountid",
 		"value": "2999"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907197912"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907232832"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907265888"
 	}, {
 		"key": "mandatereference",
 		"value": "REF-1637907412029"
 	}],
 	"status": "active"
 }

 ```
 


Click the following buttons to perform take a recurring payment using following,Before that make sure that you have completed take a recurring payment scenario

* Request State
* View Transaction

 ### Example Output - Request State

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638339051465",
 	"pollLimit": 100,
 	"serverCorrelationId": "edcb2346-1829-4ce8-b171-2a4b1a105a21",
 	"status": "completed"
 }

```
The object reference obtained from the request state is passed to view transaction function,The view transaction function will retrieve the details of the transaction

 ### Example Output - View Transaction
 
 ```json
 
 {
	"transactionReference": "REF-1638274655726",
	"creditParty": [{
		"key": "msisdn",
		"value": "+44012345678"
	}],
	"debitParty": [{
		"key": "msisdn",
		"value": "+449999999"
	}, {
		"key": "linkref",
		"value": "REF-1614172481727"
	}],
	"type": "merchantpay",
	"transactionStatus": "completed",
	"amount": "200.00",
	"currency": "RWF",
	"creationDate": "2021-11-30T12:37:15",
	"modificationDate": "2021-11-30T12:37:15",
	"requestDate": "2021-11-30T12:37:15"
}
```

<a name="retrieval-bills"></a>

# Successful Retrieval of Bills

The successful Retrieval of bils scenarios can be completed by clicking the following buttons

* View Account Bills

 ### Example Output - View Account Bills
 
 ```json
   {
 	"notificationMethod": "polling",
 	"objectReference": "440",
 	"pollLimit": 100,
 	"serverCorrelationId": "3bebab13-8ca6-479e-90dc-05fd134ec80b",
 	"status": "pending"
 }
```

<a name="make-bill-callback"></a>

# Make a Successful Bill Payment with Callback

The bill payment with callback can be completed by clicking the following methods

 * Create Bill Transaction
 * Create Bill Payments

 ### Example Output - Create Bill Transaction
 
 ```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "19686",
 	"pollLimit": 100,
 	"serverCorrelationId": "0cebe7cf-6268-42b2-b2d3-de0986f7e41c",
 	"status": "pending"
 }
```

 ### Example Output - Create Bill Payments
 
 ```json
 {
 	"notificationMethod": "callback",
 	"objectReference": "1207",
 	"pollLimit": 100,
 	"serverCorrelationId": "57972c80-793f-4b5d-82a7-763bdff7465f",
 	"status": "pending"
 }
```

<a name="make-bill-polling"></a>

# Make a Bill Payment with Polling

The Bill Payment with polling can be completed by clicking the followimg buttons in sequential order

* Create Bill Payment 
* Request State
* View Bill Payment

### Example Output - Create Bill Payments
 
 ```json
 {
 	"notificationMethod": "callback",
 	"objectReference": "1207",
 	"pollLimit": 100,
 	"serverCorrelationId": "57972c80-793f-4b5d-82a7-763bdff7465f",
 	"status": "pending"
 }
```
 ### Example Output - Request State

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638339051465",
 	"pollLimit": 100,
 	"serverCorrelationId": "edcb2346-1829-4ce8-b171-2a4b1a105a21",
 	"status": "completed"
 }

```

### Example Output - View Bill Payment

```json

{
 	"billPayments": [{
 		"amountPaid": "0.99",
 		"billPaymentStatus": "unpaid",
 		"creationDate": "2021-02-17T00:00:00",
 		"currency": "GBP",
 		"customerReference": "customer ref 0001",
 		"modificationDate": "2021-02-18T08:20:58",
 		"requestDate": "2021-02-18T08:21:27",
 		"supplementaryBillReferenceDetails": [{
 			"paymentReferenceType": "type 1",
 			"paymentReferenceValue": "value 1"
 		}]
 	}, {
 		"amountPaid": "0.99",
 		"billPaymentStatus": "unpaid",
 		"creationDate": "2021-02-17T00:00:00",
 		"currency": "GBP",
 		"customerReference": "customer ref 0001",
 		"modificationDate": "2021-02-18T08:20:58",
 		"requestDate": "2021-02-18T08:21:27",
 		"supplementaryBillReferenceDetails": [{
 			"paymentReferenceType": "type 1",
 			"paymentReferenceValue": "value 1"
 		}]
 	}, {
 		"amountPaid": "55.00",
 		"billPaymentStatus": "unpaid",
 		"creationDate": "2021-02-17T00:00:00",
 		"currency": "AED",
 		"modificationDate": "2021-02-18T08:20:58",
 		"requestDate": "2021-02-18T08:21:27"
 	}, {
 		"amountPaid": "55.00",
 		"billPaymentStatus": "unpaid",
 		"creationDate": "2021-02-17T00:00:00",
 		"currency": "AED",
 		"modificationDate": "2021-02-18T08:20:58",
 		"requestDate": "2021-02-18T08:21:27"
 	}, {
 		"amountPaid": "55.00",
 		"billPaymentStatus": "unpaid",
 		"creationDate": "2021-02-17T00:00:00",
 		"currency": "AED",
 		"modificationDate": "2021-02-18T08:20:58",
 		"requestDate": "2021-02-18T08:21:27"
 	}]
 }


```


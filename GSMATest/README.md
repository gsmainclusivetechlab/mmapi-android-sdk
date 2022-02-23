
# MMAPI-Sample App for Android SDK

This is a sample app provided to demonstrate the working of MMAPI SDK in android



## Getting started



As usual, you get started by 

* Installing  the apk in real device

* Cloning the project into local machine



## How  to install the apk in real Device

1.Download the file GSMATest-v1.0.8 from the following link into the filemanager of your  device
 
[Download](../release/GSMATest-v1.0.8.apk)


2.Click on the file GSMATest-v1.0.8 from your device and system will ask for the installation dialog and continue the installation process

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

3.Click on use case link and the app will redirect to corresponding activity

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
* [Payer-Initiated Merchant Payment](#payer-initiated)
* [Payee-Initiated Merchant Payment using a Pre-authorised Payment Code](#auth-code)
* [Merchant Payment Refund](#refund)
* [Merchant Payment Reversal](#reversal)
* [Obtain a Merchant Balance](#balances)
* [Retrieve Payments for a Merchant](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response](#missing-response)


# Disbursement


* [Individual Disbursement](#individual-disbursement)
* [Bulk Disbursement](#bulk-disbursement)
* [Bulk Disbursement with Maker / Checker](#bulk-disbursement-maker)
* [Individual Disbursement Using the Polling Method](#disbursment-polling)
* [Disbursement Reversal](#reversal)
* [Obtain a Disbursement Organisation Balance](#balances)
* [Retrieve Transactions for a Disbursement Organisation](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response](#missing-response)

# International Transfers

* [International Transfer via Hub](#international-transfer-hub)
* [Bilateral International Transfer](#bilateral-transfer-hub)
* [International Transfer Reversal](#reversal)
* [Obtain an FSP Balance](#balances)
* [Retrieve Transactions for an FSP](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response](#missing-response)

# P2P Transfers

* [P2P Transfer via Switch](#p2p-transfer-switch)
* [Bilateral P2P Transfer](#p2p-transfer-bilateral)
* [‘On-us’ P2P Transfer Initiated by a Third Party Provider](#onus-transfer-switch)
* [P2P Transfer Reversal](#reversal)
* [Obtain an FSP Balance](#balances)
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
* [Obtain a Service Provider Balance](#balances)
* [Retrieve Payments for a Service Provider](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response](#missing-response)

# Account Linking

* [Setup an Account Link](#setup-accountlink)
* [Perform a Transfer for a Linked Account](#perform-transfer)
* [Perform a Transfer using an Account Link via the Polling Method](#transfer-account-link-polling)
* [Perform a Transfer Reversal](#reversal)
* [Obtain a Financial Service Provider Balance](#balances)
* [Retrieve Transfers for a Financial Service Provider](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response](#missing-response)

# Bill Payment

* [Successful Retrieval of Bills](#retrieval-bills)
* [Make a Successful Bill Payment with Callback](#make-bill-callback)
* [Make a Bill Payment with Polling](#make-bill-polling)
* [Retrieval of Bill Payments](#retrieve-payments)
* [Check for Service Availability](#check-for-service)

# Agent Services

* [Agent-initiated Cash-out](#agent-service-cash-out)
* [Agent-initiated Cash-out using the Polling Method](#agent-service-cash-out-polling)
* [Customer-initiated Cash-out](#agent-service-cash-out)
* [Customer Cash-out at an ATM using an Authorisation Code](#agent-service-cash-out-auth)
* [Agent-initiated Customer Cash-in](#agent-initiated-cash-in)
* [Cash-out Reversal](#reversal)
* [Register a Customer Mobile Money Account](#register-customer)
* [Verify a Customer’s KYC](#verify-customer)
* [Obtain an Agent Balance](#balances)
* [Retrieve Transactions for an Agent](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response](#missing-response)


<a name="payee-initiated"></a>

# Payee initiated Merchant Payment
 
 In this scenario the payer/payee can initiate a payment request,Click on the following buttons in test app to perform merchant payment
 
 * Payee Initiated Merchant Payment
 
 The expected output of this request is given below

###  Payee initiated Merchant Payment-Output
 
In the test app default notification method is CALLBACK and you can change notification methods from the sample code to POLLING if needed

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

The Payee-Initiated Merchant Payment using the Polling Method scenario can be completed by clicking following buttons of sample app

* Payee-Initiated Merchant Payment using the Polling Method

 ### Payee initiated Merchant Payment Polling - Output

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

 
 ### View Request State - Output

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

 ### View Transaction - Output
 
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

<a name="payer-initiated"></a>

# Payer initiated Merchant Payment
 
 In this scenario the payer/payee can initiate a payment request,Click on the following buttons in test app to perform merchant payment
 
 * Payer Initiated Merchant Payment
 
 The expected output of this request is given below

###  Payer initiated Merchant Payment-Output
 
In the test app default notification method is CALLBACK and you can change notification methods from the sample code to POLLING if needed

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

<a name="auth-code"></a>

# Payee-Initiated Merchant Payment using a Pre-authorised Payment Code

The Payee-Initiated Merchant Payment using a Pre-authorised Payment Code can be completed by clicking the following buttons

* Payee-Initiated Merchant Payment using a Pre-authorised Payment Code

 ### Create Authorisation Code - Output

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
 
### View Request state - Output

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

### View Authorization Code - Output

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

### Payee-Initiated Merchant Payment - Output 

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

### Payment Refund - Output


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

### Payment Reversal -Output

```json

{
	"notificationMethod": "polling",
	"objectReference": "4572",
	"pollLimit": 100,
	"serverCorrelationId": "ca3eb926-5b8c-4d11-b184-06782408208e",
	"status": "pending"
}

```

<a name="balances"></a>

# Balance

The balance scenario can be completed by using following methods

* Balance

### Balance - Output


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

### Retrieve Transaction - Output

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

### Check Service Availability - Output

```json
{
	"serviceStatus": "available"
}

```

<a name="missing-response"></a>

# Retrieve a Missing API Response

 The missing response of an request can be performed using the correlation id used for the request,Pass the correlationId of a transaction request to get the missing  response of a particular API
 
For eg:if the transaction response is missing,To retrieve the missing response of transaction click the following button 

* Retrieve a Missing API Response

### Create Missing Transaction - Output

```json
{
	"notificationMethod": "polling",
	"objectReference": "93e2e3ac-e5ee-470e-a1de-ae9757e63106",
	"pollLimit": 100,
	"serverCorrelationId": "3fa62436-9935-468c-8911-62263e6272c3",
	"status": "completed"
}

```

### Missing Response - Output

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


### Individual disbursement - Output

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

* Bulk Disbursement

### Create Batch Transaction - Output

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "1153",
 	"pollLimit": 100,
 	"serverCorrelationId": "fa8b8839-7323-45c2-8319-a075395307a7",
 	"status": "pending"
 }
 
```

### View Request state - Output

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638337785175",
 	"pollLimit": 100,
 	"serverCorrelationId": "fa8b8839-7323-45c2-8319-a075395307a7",
 	"status": "completed"
 }

```

 ### Batch Transaction - Output

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

 ### Batch Completions -  Output

```json
{
	"BatchTransactionCompletion": []
}

```


 ### Batch Rejection - Output

```json
{
	"BatchTransactionRejection": []
}

```


<a name="bulk-disbursement-maker"></a>
# Bulk Disbursement using Maker/Checker

The bulk Disbursement can be completed by clicking following button in sequential order

* Bulk Disbursement using Maker/Checker


###  Create Batch Transaction - Output

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "1153",
 	"pollLimit": 100,
 	"serverCorrelationId": "fa8b8839-7323-45c2-8319-a075395307a7",
 	"status": "pending"
 }
 
```

### View Request state - Output

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638337785175",
 	"pollLimit": 100,
 	"serverCorrelationId": "fa8b8839-7323-45c2-8319-a075395307a7",
 	"status": "completed"
 }

```

### Batch Transaction - Output

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

### Update a batch transaction - Output

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638337785175",
 	"pollLimit": 100,
 	"serverCorrelationId": "fa8b8839-7323-45c2-8319-a075395307a7",
 	"status": "completed"
 }


```

 ###  Batch Completions -  Output

```json
{
	"BatchTransactionCompletion": []
}

```


 ### Batch Completions -  Output

```json
{
	"BatchTransactionRejection": []
}

```

<a name="disbursment-polling"></a>

# Individual Disbursement Using the Polling Method

Disbursement using polling method can be completed using clicking following button

* Individual Disbursement Using the Polling Method

### Individual disbursement Polling - Output 

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

 ### View Request state - Output

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

 ### View Transaction - Output
 
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

# International Transfer via Hub

This use case can be completed by clicking following button

*  International Transfer via Hub


 ### Create Quotation Request - Output
 
 ```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "1295",
 	"pollLimit": 100,
 	"serverCorrelationId": "b9b86e55-7b1b-446d-8a2b-ab28894e37bf",
 	"status": "pending"
 }
```

### Create International Transfer Transaction - Output
 
 ```json
{
 	"notificationMethod": "polling",
 	"objectReference": "1295",
 	"pollLimit": 100,
 	"serverCorrelationId": "b9b86e55-7b1b-446d-8a2b-ab28894e37bf",
 	"status": "pending"
 }
```

<a name="p2p-transfer-bilateral"></a>

# Bilateral International Transfer

This use case can be completed by clicking following button

*  Bilateral International Transfer
  

 ### Create Quotation Request - Output
 
 ```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "1295",
 	"pollLimit": 100,
 	"serverCorrelationId": "b9b86e55-7b1b-446d-8a2b-ab28894e37bf",
 	"status": "pending"
 }
```

### Create International Transfer Transaction - Output
 
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

# P2P Transfer via Switch

 The p2 transfer via switch can be completed by clicking the following buttons
 
 * P2P Transfer via Switch
 
 ### Retrieve the Name of the Recipient - Output 
 
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
 ### Request Quotation - Output
 
 ```json
 {
	"notificationMethod": "polling",
	"objectReference": "1306",
	"pollLimit": 100,
	"serverCorrelationId": "e97885d3-2686-48ca-b66d-dfae1cb4ae42",
	"status": "pending"
}

```
 ###  Perform p2p transfer - Output


 ```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "15681",
 	"pollLimit": 100,
 	"serverCorrelationId": "eb3ca49e-3d5d-4050-81b6-ebc0fa6b053e",
 	"status": "pending"
 }

```


<a name="onus-transfer-switch"></a>

# ‘On-us’ P2P Transfer Initiated by a Third Party Provider

 The p2 transfer via switch can be completed by clicking the following buttons
 
 * ‘On-us’ P2P Transfer Initiated by a Third Party Provider
 
 ### Retrieve the Name of the Recipient - Output 
 
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
 ### Request Quotation - Output
 
 ```json
 {
	"notificationMethod": "polling",
	"objectReference": "1306",
	"pollLimit": 100,
	"serverCorrelationId": "e97885d3-2686-48ca-b66d-dfae1cb4ae42",
	"status": "pending"
}

```
 ###  Perform p2p transfer - Output


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

 The p2 transfer via bilateral can be completed by clicking the following buttons
 
 * Bilateral P2P Transfer
 
 ### Retrieve the Name of the Recipient - Output 
 
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
 ### Request Quotation - Output
 
 ```json
 {
	"notificationMethod": "polling",
	"objectReference": "1306",
	"pollLimit": 100,
	"serverCorrelationId": "e97885d3-2686-48ca-b66d-dfae1cb4ae42",
	"status": "pending"
}

```
 ###  Perform p2p transfer - Output


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

* Setup a Recurring Payment

 ### Create Debit Mandate - Output


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

 * Take a Recurring Payment


 ### Create Debit Mandate - Output 
 

 ```json

 {
 	"notificationMethod": "polling",
 	"objectReference": "432",
 	"pollLimit": 100,
 	"serverCorrelationId": "82da04e9-05f5-4b1e-96f9-06b21deb7fc4",
 	"status": "pending"
 }

 
 ```

 ### View Request state - Output
 
 ```json
{
	"notificationMethod": "polling",
	"objectReference": "REF-1638343640945",
	"pollLimit": 100,
	"serverCorrelationId": "82da04e9-05f5-4b1e-96f9-06b21deb7fc4",
	"status": "completed"
}
```


 ### View Debit Mandate - Output
 
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


 ### Create Merchant payment -Output
 
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

 ### Create Debit Mandate - Output 
 

 ```json

 {
 	"notificationMethod": "polling",
 	"objectReference": "432",
 	"pollLimit": 100,
 	"serverCorrelationId": "82da04e9-05f5-4b1e-96f9-06b21deb7fc4",
 	"status": "pending"
 }

 
 ```

 ### View Request state - Output
 
 ```json
{
	"notificationMethod": "polling",
	"objectReference": "REF-1638343640945",
	"pollLimit": 100,
	"serverCorrelationId": "82da04e9-05f5-4b1e-96f9-06b21deb7fc4",
	"status": "completed"
}
```


 ### View Debit Mandate - Output
 
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


 ### Create Merchant payment -Output
 
 ```json

{
	"notificationMethod": "polling",
	"objectReference": "15684",
	"pollLimit": 100,
	"serverCorrelationId": "164b0df3-ddf0-4459-96e2-82b4514a8c17",
	"status": "pending"
}


```

<a name="setup-recurring"></a>

# Payer sets up a Recurring Payment using MMP Channel

Set up a recurring payment can be completed by clicking the following buttons

* Payer sets up a Recurring Payment using MMP Channel

 ### Create Debit Mandate - Output

 ```json

 {
 	"notificationMethod": "polling",
 	"objectReference": "428",
 	"pollLimit": 100,
 	"serverCorrelationId": "70089e23-35ca-4e29-a166-4668024cb236",
 	"status": "pending"
 }

```

<a name="setup-accountlink"></a>

# Setup an Account Link

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
 
 <a name="perform-transfer"></a>
 
 # Perform a Transfer for Linked Account
 
  Click the the following button to perform a transfer for a Linked Account
 
 * Perform a Transfer for Linked Account
 
  ### Example Output - Transfer

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638339051465",
 	"pollLimit": 100,
 	"serverCorrelationId": "edcb2346-1829-4ce8-b171-2a4b1a105a21",
 	"status": "completed"
 }

 ```
 

 <a name="transfer-account-link-polling"></a>
 
# Perform a Transfer using an Account Link via the Polling Method

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

The successful Retrieval of bills scenarios can be completed by clicking the following buttons

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

The Bill Payment with polling can be completed by clicking the following buttons in sequential order

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

<a name="agent-service-cash-out"></a>
# Agent Initiated Cash out/Customer-initiated Cash-out

The agent initiated Cash out can be scenario can be achieved by clicking the following buttons

* Agent Initiated Cash Out 

### Example Output - Agent Initiated Cash out / Customer-initiated Cash-out

 
 ```json
 {
 	"notificationMethod": "callback",
 	"objectReference": "1207",
 	"pollLimit": 100,
 	"serverCorrelationId": "57972c80-793f-4b5d-82a7-763bdff7465f",
 	"status": "pending"
 }
```
<a name="agent-service-cash-out-polling"></a>

# Agent Initiated Cash out using Polling Method

The agent Initiated Cash out can be scenario can be achieved by clicking the following buttons

* Agent Initiated Cash Out 
* Request State
* View Transaction


### Example Output - Agent Initiated Cash out

 
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
 	"serverCorrelationId": "57972c80-793f-4b5d-82a7-763bdff7465f",
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
<a name="agent-service-cash-out-auth"></a>

# Customer Cash-out at an ATM using an Authorisation Code

Customer Initiated Cash out at atm using Authorisation Code can be achieved by clicking following button

* Create Authorisation Code
* Request State
* View Authorisation Code
* Agent Initiated Cash Out


### Example Output - Create Authorisation Code

```json
 {
 	"notificationMethod": "polling",
 	"objectReference": "REF-1638339051465",
 	"pollLimit": 100,
 	"serverCorrelationId": "edcb2346-1829-4ce8-b171-2a4b1a105a21",
 	"status": "completed"
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

### Example Output - Agent Initiated Cash out

 
 ```json
 {
 	"notificationMethod": "callback",
 	"objectReference": "1207",
 	"pollLimit": 100,
 	"serverCorrelationId": "edcb2346-1829-4ce8-b171-2a4b1a105a21",
 	"status": "pending"
 }
```
<a name="agent-initiated-cash-in"></a>

# Agent-initiated Customer Cash-in

Agent-initiated Customer Cash-in  can be achieved by clicking following button

* Retrieve the name of the Depositing Customer
* Agent Initiated Cash In 

### Example Output - Retrieve the name of the Depositing Customer


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


### Example Output -Agent Initiated Cash In 

```json
 	"notificationMethod": "callback",
 	"objectReference": "1207",
 	"pollLimit": 100,
 	"serverCorrelationId": "edcb2346-1829-4ce8-b171-2a4b1a105a21",
 	"status": "pending"
 }

```

<a name="register-customer"></a>

# Register a Customer Mobile Money Account

The register a customer in mobile money account can be achieved by clicking the following buttons

* Create a Mobile Money Account

### Example Output -Create a Mobile Money Account

```json
{
        "notificationMethod": "callback",
 	"objectReference": "1207",
 	"pollLimit": 100,
 	"serverCorrelationId": "edcb2346-1829-4ce8-b171-2a4b1a105a21",
 	"status": "pending"
}
```

<a name="verify-customer"></a>

# Verify a Customer’s KYC

Verify a customer KYC can be achieved by clicking the following buttons

* Retrieve Account information
* Update KYC verification Status

### Example Output -Retrieve Account information

```java


{
	"accountIdentifiers": [{
			"key": "msisdn",
			"value": "+449999999"
		},
		{
			"key": "walletid",
			"value": "1"
		},
		{
			"key": "accountid",
			"value": "1"
		},
		{
			"key": "mandatereference",
			"value": "REF-1583141449478"
		},
		{
			"key": "linkref",
			"value": "REF-1473082363913"
		}
	],
	...
}
```
### Example Output - Update KYC verification Status

```json
{
        "notificationMethod": "callback",
 	"objectReference": "1207",
 	"pollLimit": 100,
 	"serverCorrelationId": "edcb2346-1829-4ce8-b171-2a4b1a105a21",
 	"status": "pending"
}



```





















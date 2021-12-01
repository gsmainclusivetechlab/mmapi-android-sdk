
# MMAPI-Sample App for Android SDK

This is a sample app provided to demonstrate the working of MMAPI SDK in android



## Getting started



As usual, you get started by 

* Installing  the apk in real device

* Cloning the project into local machine



## How  to install the apk in real Device

1.Download the file GSMA-Test-debug-1.0.4 from the following link into the filemanager of your  device
 
[Download](https://github.com/gsmainclusivetechlab/mmapi-android-sdk/raw/develop/release/GSMA-Test-debug-1.0.4.apk)

2.Click on the file GSMA-Test-debug-1.0.4 from your device and system will ask for the installation dialog and continue the installation process 

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

<a name="payee-initiated"></a>

# Disbursment

* [Individual Disbursement](#individual-disbursement)
* [Bulk Disbursement](#bulk-disbursement)
* [Bulk Disbursement with Maker / Checker](#bulk-disbursement-maker)
* [Individual Disbursement Using the Polling Method](#disbursment-polling)
* [Disbursement Reversal](#reversal)
* [Obtain a Disbursement Organisation Balance](#balance)
* [Retrieve Transactions for a Disbursement Organisation](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response]((#missing-response))

# International Transfer

* [International Transfer via Hub]
* [Bilateral International Transfer]
* [International Transfer Reversal](#reversal)
* [Obtain an FSP Balance](#balance)
* [Retrieve Transactions for an FSP](#retrieve-payments)
* [Check for Service Availability](#check-for-service)
* [Retrieve a Missing API Response](#missing-response)





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

```
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

The service functionality will be trigerred automatically when we select merchant payment use cases

### Example Output - Check for Service

```json
{
	"serviceStatus": "available"
}

```

<a name="missing-response"></a>

# Retrieve a Missing API Response

 The missing response of an request can be performed using the correlation id used for the request,Pass the correlationid obtained for a transaction request to get the missing  response of a particular API
 
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

 








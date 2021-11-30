
# MMAPI-Sample App for Android SDK

This is a sample app provided to demonstrate the working of MMAPI SDK in android



## Getting started



As usual, you get started by 

1.Installing  the apk in real device

2.Cloning the project into local machine



## How  to install the apk in real Device

1.Download the file GSMA-Test-debug-1.0.1 from the following link into the filemanager of your  device
 
[Download](https://github.com/gsmainclusivetechlab/mmapi-android-sdk/raw/develop/release/GSMA-Test-debug-1.0.3.apk)

2.Click on the file GSMA-Test-debug-1.0.3 from your device and system will ask for the installation dialog and continue the installation process 

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

* [Payee-Initiated Merchant Payment](#payee-intiated)
* Payee-Initiated Merchant Payment using the Polling Method
* [Payer-Initiated Merchant Payment](#payee-intiated)
* Payee-Initiated Merchant Payment using a Pre-authorised Payment Code
* Merchant Payment Refund
* Merchant Payment Reversal
* Obtain a Merchant Balance
* Retrieve Payments for a Merchant
* Check for Service Availability
* Retrieve a Missing API Response

<a name="payee-intiated"></a>

# Payee/Payer intiated Merchant Payment
 
 In this scenario the payer/payee can intiate a payment request,Click on the following buttons in test app to perform merchant payment
 
 * Payee Intiated
 
 The expected output of this request is given below
 
 ### Example Output - Polling

```
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

```
 {
	"notificationMethod": "callback",
	"objectReference": "15596",
	"pollLimit": 100,
	"serverCorrelationId": "d6582f57-f353-45b7-962b-7e35bda38765",
	"status": "pending"
}
```
# Payee-Initiated Merchant Payment using the Polling Method

The polling scenario can be completed by clicking following buttons of sample app in sequentical order

* Payee Intiated
* Request State
* View Transaction


 
 ### Example Output - Payee Intiated

```
 {
	"notificationMethod": "polling",
	"objectReference": "15596",
	"pollLimit": 100,
	"serverCorrelationId": "d6582f57-f353-45b7-962b-7e35bda38765",
	"status": "pending"
}
```
 The serverCorrelationId is obtained from the result of payee intiated request,This serverCorrelationId is passed to request state function to view the request state of a transaction  

 
 ### Example Output - Request State

```
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
 
 ```
 
 {
	"transactionReference": "REF-1638275835490",
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




 
 
 




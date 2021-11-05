
# MMAPI-Sample App for Android SDK

This is a sample app provided to demonstrate the working of MMAPI SDK in android



## Getting started



As usual, you get started by 

1.Installing  the apk in real device

2.Cloning the project into local machine



## How  to install the apk in real Device

1.Copy or download the file GSMMA-test-1.0.apk from the following path  into the filemanager of your  device
 
[Download](https://github.com/gsmainclusivetechlab/mmapi-android-sdk/blob/develop/GSMATest/README.md)

2.Click on the file GSMMA-test-1.0.apk from your device and system will ask for the installation dialog and continue the installation process 

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

2.The landing page contains buttons for both Merchant Payment and Disbursement Feature

3.Click on merchant payment button and the app will redirect to merchant payment activity

4.The merchant payment activity contains buttons for testing all use cases for merchant payment

5.Each uses cases can be tested using the buttons provided in the test application


## Features

- Payee/payer-Initiated Merchant Payment
- Payee-Initiated Merchant Payment using the Polling Method
- Payee-Initiated Merchant Payment using a Pre-authorised Payment Code
- Merchant Payment Refund
- Merchant Payment Reversal
- Obtain a Merchant Balance
- Retrieve Payments for a Merchant
- Check for Service Availability
- Retrieve a Missing API Response


### Payee/payer-Initiated Merchant Payment

The merchant initiates the request and will be credited when the payer approves the request.This use case scenario can be tested by clicking the  button "mechant pay initiated"

### Payee-Initiated Merchant Payment using the Polling Method

In this scenario the client polls against the request state object to determine the outcome of the payment request.Three Api operations are to be performed to complete this use cases,

1.First click on the "mechant payment" button and the api will return the server correlation id for the above request

2.Then click on the "Request state" button and will return the request of the object with reference of transaction,Server correlation id obtained from result of mechant payment api is passed to request state api to get the request state of a transaction(step 1)

3.Finally click "view transaction" button,It will  call the view transaction api using the transaction id obtained as result of request state api(step 2),


### Payee-Initiated Merchant Payment using a Pre-authorised Payment Code

Authorisationcodes API is used to obtain a pre-authorised payment code. This in turn is presented by the payer to the merchant who initiates the payment request.

1.In this scenario,Initially we call the AuthorizationCodes Api using the button "Auth Code",An One time code is send to the payer,The payer communicate the code to merchant via qrcode or verbally 

2.The One time code is added in the request body of transaction request and call the merchant pay api to initiate a transaction using authorization code, 

### Merchant Payment Refund

Merchants can issue a refund to payers. The refund operation is performed using button "Payment refund"

### Merchant Payment Reversal

In some failure scenarios, a merchant may need to reverse a transaction,The Revesal operation can be tested using "Payment Reversal"

### Obtain a Merchant Balance

The button "balance" is used to check the balance of a particular account

### Retrieve Payments for a Merchant

The "retrieve transaction" button is used to retrieve all payments for a merchant via multiple requests.

## Check for Service Availability

The service availabilty is automtically called once application is visible to the user,It will check if the service is available or not

## Retrieve a Missing API Response

The details of missing response can be tested using the "Missing transaction" and "missing code" button

1."Missing transaction" button  will retrieve the missing response of the  particular transaction,For that we should click "merchant pay" button before clicking the "missing transaction"" button

2."Missing Code" button will retrieve the missing response for authorization code request API

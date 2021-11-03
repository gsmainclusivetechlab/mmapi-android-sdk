# MMAPI Android SDK
Android SDK to use MMAPI.

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://github.com/gsmainclusivetechlab/mmapi-android-sdk)
[![SDK Version](https://img.shields.io/badge/minSdkVersion-16-blue.svg)](https://developer.android.com/about/versions/android-4.1)
[![SDK Version](https://img.shields.io/badge/targetSdkVersion-31-informational.svg)](https://developer.android.com/sdk/api_diff/31/changes)

A library that fully covers payment process inside your Android application

# Table of Contents
---
1. [Requirements](#requirements)
2. [How to include payments-sdk in your android application](#Setup)
3. [Configure the SDK](#Configure)

<a name="requirements"></a>
# Requirements
---
To use the SDK the following requirements must be met:

1. **Android Studio 4.0** or newer
2. **Android Platform Version**: API 31
3. **Build gradle**:4.2.1

<a name="Setup"></a>

# How to include payments-sdk in your android application

Copy the aar GSMASdk-debug.aar file, available in the latest version in release folder, into libs folder under your project directory.

Add the below line in dependencies of your build.gradle file in your application.

```
implementation files('libs/GSMASdk-debug.aar')
```
<a name="Configure"></a>

# Configure the SDK
  
After including the SDK into your project,Configure the SDK with either SANDBOX account or live account

```
//Initialise the payment system before calling the functions in the SDK

//Declare the configuration parameter

    private String consumerKey;//optional parameter
    private String consumerSecret;//optional paramater if the security level
    private String securityOption;// options  NO_AUTH , DEVELOPMENT_LEVEL, STANDARD_LEVEL, ENHANCED_LEVEL,
    private String callBackURL;//The backend server URL for your app for handling callbacks in application


 //inside oncreate method setup the payment configuration constructor with required parameters
 
 
 //sample consumer key for testing,you can generate a consumer key for your app from MMAPI dashboard under apps 
  consumerKey="9vthmq3f6i15v6jmcjskfkmh"; 
  
  /sample consumer secret for testing,you can generate a consumer secret for your app from MMAPI dashboard under apps 
  consumerSecret="ef8tl4gihlpfd7r8jpc1t1nda33q5kcnn32cj375lq6mg2nv7rb"; 
  
  
  //The security option availbale in SDK are,AuthenticationType.NO_AUTH,AuthenticationType.DEVELOPMENT_LEVEL,AuthenticationType.STANDARD_LEVEL,AuthenticationType.ENHANCED_LEVEL
  
  securityOption=AuthenticationType.STANDARD_LEVEL;
  
  //Example mock URL for handling callback,Change the url into your live callback URL
  callbackURL="https://93248bb1-c64e-4961-bacf-b1d4aaa103bc.mock.pstmn.io/callback"
 
   //Confgure the SDK 
   PaymentConfiguration.init(consumerKey,consumerSecret,securityOption,Environment.SANDBOX);

  //Create a token  if the security option 





```



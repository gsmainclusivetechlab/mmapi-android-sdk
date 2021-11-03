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

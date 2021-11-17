
package com.gsmaSdk.gsma.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;

import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.network.retrofit.PaymentConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * The type Utils - for reusable utility functions.
 */
@SuppressWarnings("ALL")
public class Utils {

    /**
     * Gets currency name.
     *
     * @param convertedKey the formatted key
     * @return the encoded key
     */


    public static String base64EncodeString(String convertedKey) {
        byte[] data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            data = convertedKey.getBytes(StandardCharsets.UTF_8);
        } else {
            //noinspection CharsetObjectCanBeUsed
            data = convertedKey.getBytes(Charset.forName("UTF-8"));
        }
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }


    public static ErrorObject parseError(String response) {
        JSONObject jsonObject;
        ErrorObject errorObject = new ErrorObject();
        String category;
        String description;
        String code;
        String dateTime;
        String message;
        final String CATEGORY = "errorCategory";
        final String CODE = "errorCode";
        final String DESCRIPTION = "errorDescription";
        final String DATETIME = "errorDateTime";
        final String MESSAGE = "message";

        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.has(DESCRIPTION)) {
                description = jsonObject.getString(DESCRIPTION);
                errorObject.setErrorDescription(description);
            }
            if (jsonObject.has(CATEGORY)) {
                category = jsonObject.getString(CATEGORY);
                errorObject.setErrorCategory(category);
            }
            if (jsonObject.has(CODE)) {
                code = jsonObject.getString(CODE);
                errorObject.setErrorCode(code);
            }
            if (jsonObject.has(DATETIME)) {
                dateTime = jsonObject.getString(DATETIME);
                errorObject.setErrorDateTime(dateTime);
            }
            if (jsonObject.has(MESSAGE)) {
                message = jsonObject.getString(MESSAGE);
                errorObject.setMessage(message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errorObject;
    }

    public static boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null) && (activeNetworkInfo.isConnected());
    }

    public static ErrorObject setError(int errorCode) {
        ErrorObject errorObject = new ErrorObject();
        switch (errorCode) {
            case 0:
                errorObject.setErrorCategory("Service Unavailable");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("No Internet connectivity");
                break;
            case 1:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid account identifier");
                break;
            case 2:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid server correlation id");
                break;
            case 3:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid transaction reference");
                break;
            case 4:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid reference id");
                break;
            case 5:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid json format");
                break;
            case 6:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid correlation id");
                break;
            case 7:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid Transaction type");
                break;
            case 8:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid Identifier");
                break;
            case 9:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid Authorisation Code");
                break;

            case 10:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid Quotation Reference");
                break;

            case 11:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid account identifier format!maximum 3 identifiers are allowed");
                break;

            default:
                errorObject.setErrorCategory("");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("");
                break;
        }

        return errorObject;
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String setCallbackUrl(Enum notificationMethod, String callBackUrl) {
        if (notificationMethod == NotificationMethod.CALLBACK) {
            if (callBackUrl != null) {
                if (callBackUrl.isEmpty()) {
                    if (PaymentConfiguration.getCallBackURL().isEmpty()) {
                        return "";
                    } else {
                        return PaymentConfiguration.getCallBackURL();
                    }
                } else {
                    return callBackUrl;
                }
            } else {
                if (PaymentConfiguration.getCallBackURL().isEmpty()) {
                    return "";
                } else {
                    return PaymentConfiguration.getCallBackURL();
                }
            }
        } else {
            return "";
        }
    }
}


package com.gsmaSdk.gsma.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;

import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.network.retrofit.PaymentConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
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

    public static HashMap<String, String> getHashMapFromObject(Object object) {
        HashMap<String, String> params = new HashMap<>();

        if (object instanceof TransactionFilter) {
            TransactionFilter transactionFilter = (TransactionFilter) object;
            params.put("limit", String.valueOf(transactionFilter.getLimit()));
            params.put("offset", String.valueOf(transactionFilter.getOffset()));
            if (transactionFilter.getFromDateTime() != null) {
                params.put("fromDateTime", transactionFilter.getFromDateTime());
            }
            if (transactionFilter.getToDateTime() != null) {
                params.put("toDateTime", transactionFilter.getToDateTime());
            }
            if (transactionFilter.getTransactionStatus() != null) {
                params.put("transactionStatus", transactionFilter.getTransactionStatus());
            }
            if (transactionFilter.getTransactionType() != null) {
                params.put("transactionType", transactionFilter.getTransactionType());
            }
        }

        return params;
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
        final String DESCRIPTION = "errordescription";
        final String DATETIME = "errorDateTime";
        final String MESSAGE = "message";

        if(response==null){
            errorObject.setErrorDescription("Invalid Json Format");
            return errorObject;
        }
        if (response.isEmpty()) {
            errorObject.setErrorDescription("Invalid Json Format");
            return errorObject;
        }
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
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
        } catch (Exception e) {
            System.out.println("errors" + e.getMessage());
        }
        return errorObject;
    }

    public static boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null) && (activeNetworkInfo.isConnected());
    }

    public static String getIdentifiers(ArrayList<Identifier> identifierArrayList) {
        String identifierValue = "";
        if (identifierArrayList.size() == 1) {
            Identifier identifier = identifierArrayList.get(0);
            identifierValue = identifierValue + identifier.getKey() + "/" + identifier.getValue();
        } else {
            for (int i = 0; i < identifierArrayList.size(); i++) {
                Identifier identifier = identifierArrayList.get(i);
                if (identifierArrayList.size() - 1 == i) {
                    identifierValue = identifierValue + identifier.getKey() + "@" + identifier.getValue();
                } else {
                    identifierValue = identifierValue + identifier.getKey() + "@" + identifier.getValue() + "$";

                }

            }
        }
        return identifierValue;
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

            case 12:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid bill Reference");
                break;
            case 13:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid Patch Object");
                break;
            case 14:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid Identity Id");
                break;
            case 15:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid Batch Id");
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
                if(PaymentConfiguration.getCallBackURL()==null){
                    return "";
                }
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

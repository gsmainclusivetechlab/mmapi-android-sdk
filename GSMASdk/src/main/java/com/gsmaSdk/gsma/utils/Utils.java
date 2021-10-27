package com.gsmaSdk.gsma.utils;

import android.os.Build;
import android.util.Base64;

import com.gsmaSdk.gsma.models.common.ErrorObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * The type Utils - for reusable utility functions.
 */
@SuppressWarnings("unused")
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
//        JSONArray jsonErrorParams;
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
//            jsonErrorParams = jsonObject.getJSONArray(ERRORS);
//            errorObject.setErrorParameters(jsonErrorParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errorObject;
    }

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static ErrorObject setError(int errorCode) {
        ErrorObject errorObject = new ErrorObject();
        switch (errorCode){
            case 0:
                errorObject.setErrorCategory("Service Unavailable");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("No Internet connectivity");
                break;
            case 1:
                errorObject.setErrorCategory("validation");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("Invalid accountId format");
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
            default:
                errorObject.setErrorCategory("");
                errorObject.setErrorCode("GenericError");
                errorObject.setErrorDescription("");
                break;
        }

        return errorObject;
    }
}

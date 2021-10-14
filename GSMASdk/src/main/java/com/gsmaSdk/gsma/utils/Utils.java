package com.gsmaSdk.gsma.utils;

import android.util.Base64;

import com.gsmaSdk.gsma.models.common.ErrorObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * The type Utils - for reusable utility functions.
 */
public class Utils {

    /**
     * Gets currency name.
     *
     * @param convertedKey the formatted key
     * @return the encoded key
     */
    public static String base64EncodeString(String convertedKey) {
        byte[] data = new byte[0];
        try {
            data = convertedKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }

    public static ErrorObject parseError(String response) {
//        JSONArray jsonErrorParams;
        JSONObject jsonObject;
        ErrorObject errorObject = new ErrorObject(null,null,null);
        String category = "";
        String description = "";
        String code;
        String dateTime;
        final String CATEGORY = "errorCategory";
        final String CODE = "errorCode";
        final String DESCRIPTION = "errorDescription";
        final String DATETIME = "errorDateTime";
        final String ERRORS = "errorParameters";
        try {
            jsonObject = new JSONObject(response);
            description = jsonObject.getString(DESCRIPTION);
            category = jsonObject.getString(CATEGORY);
            code = jsonObject.getString(CODE);
            dateTime = jsonObject.getString(DATETIME);
//            jsonErrorParams = jsonObject.getJSONArray(ERRORS);
            errorObject.setErrorCategory(category);
            errorObject.setErrorCode(code);
            errorObject.setErrorDateTime(dateTime);
            errorObject.setErrorDescription(description);
//            errorObject.setErrorParameters(jsonErrorParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errorObject;
    }
}

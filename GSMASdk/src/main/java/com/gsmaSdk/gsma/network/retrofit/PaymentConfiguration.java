package com.gsmaSdk.gsma.network.retrofit;

import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.enums.Environment;
import com.gsmaSdk.gsma.utils.Utils;

/**
 * The type App info - app info is temporarily kept for processing.
 */
@SuppressWarnings({"unused", "rawtypes"})
public class PaymentConfiguration {

    //auth information for headers
    public static Enum authType;
    private static String authToken;
    private static String base64Value;
    private static String consumerKey;
    private static String consumerSecret;
    private static String urlVersion;
    private static String callBackURL;
    private static String xAPIKey;



    /**
     * @param consumerKey    ConsumerKey
     * @param consumerSecret ConsumerSecret
     * @param authType       Levels of authentication
     */

    public static void init(String consumerKey, String consumerSecret, Enum authType, String callBackURL,Enum environmentType) {
        PaymentConfiguration.authType = authType;
        PaymentConfiguration.consumerSecret = consumerSecret;
        PaymentConfiguration.consumerKey = consumerKey;
        urlVersion = PaymentConfiguration.getAuthType() == AuthenticationType.NO_AUTH ? APIConstants.URL_VER : APIConstants.URL_VER_OAUTH;
        PaymentConfiguration.setUrlVersion(urlVersion);
        APIConstants.BASE_URL=environmentType==Environment.SANDBOX?APIConstants.SANDBOX_BASE_URL:APIConstants.LIVE_BASE_URL;
        PaymentConfiguration.base64Value = Utils.base64EncodeString(PaymentConfiguration.consumerKey + ":" + PaymentConfiguration.consumerSecret);
        PaymentConfiguration.callBackURL = callBackURL;
        PaymentConfiguration.xAPIKey ="";
    }

    public static void init(String consumerKey, String consumerSecret, Enum authType, String callBackURL, String xAPIKey,Enum environmentType) {
        PaymentConfiguration.authType = authType;
        PaymentConfiguration.consumerSecret = consumerSecret;
        PaymentConfiguration.consumerKey = consumerKey;
        APIConstants.BASE_URL=environmentType==Environment.SANDBOX?APIConstants.SANDBOX_BASE_URL:APIConstants.LIVE_BASE_URL;
        urlVersion = PaymentConfiguration.getAuthType() == AuthenticationType.NO_AUTH ? APIConstants.URL_VER : APIConstants.URL_VER_OAUTH;
        PaymentConfiguration.setUrlVersion(urlVersion);
        PaymentConfiguration.base64Value = Utils.base64EncodeString(PaymentConfiguration.consumerKey + ":" + PaymentConfiguration.consumerSecret);
        PaymentConfiguration.callBackURL = callBackURL;
        PaymentConfiguration.xAPIKey = xAPIKey;
    }

    public static void init( String callBackURL,Enum environmentType) {
        PaymentConfiguration.authType = AuthenticationType.NO_AUTH;
        PaymentConfiguration.consumerSecret ="";
        PaymentConfiguration.consumerKey = "";
        APIConstants.BASE_URL=environmentType==Environment.SANDBOX?APIConstants.SANDBOX_BASE_URL:APIConstants.LIVE_BASE_URL;
        urlVersion = APIConstants.URL_VER;
        PaymentConfiguration.callBackURL = callBackURL;
        PaymentConfiguration.xAPIKey ="";
    }


    public static Enum getAuthType() {
        return authType;
    }


    public static String getBase64Value() {
        return base64Value;
    }

    /**
     * Sets auth token.
     *
     * @param authToken the auth token
     */
    public static void setAuthToken(String authToken) {
        PaymentConfiguration.authToken = authToken;
    }

    /**
     * Gets auth token.
     *
     * @return the auth token
     */
    public static String getAuthToken() {
        return authToken;
    }

    public static String getCallBackURL() {
        return callBackURL;
    }

    public static String getxAPIKey() {
        return xAPIKey;
    }

    public static String getUrlVersion() {
        return urlVersion;
    }

    public static void setUrlVersion(String urlVersion) {
        PaymentConfiguration.urlVersion = urlVersion;
    }
}

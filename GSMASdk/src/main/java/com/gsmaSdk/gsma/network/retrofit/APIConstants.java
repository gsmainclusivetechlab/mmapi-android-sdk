package com.gsmaSdk.gsma.network.retrofit;

/**
 * The type Api constants.
 */
@SuppressWarnings("SpellCheckingInspection")
final class APIConstants {


    /**
     *
     * The udid of the transaction
     */

    static final String X_CORRELATION_ID="X-CorrelationID";


    /**
     * The Base url.
     */
    static String BASE_URL = "https://sandbox.mobilemoneyapi.io/";
    /**
     * The Base url version.
     *
     */

    /**
     * Base url for live account
     */

    static final String LIVE_BASE_URL="https://sandbox.mobilemoneyapi.io/";

    /**
     *
     * Sandbox Base URL
     */
    static final String SANDBOX_BASE_URL="https://sandbox.mobilemoneyapi.io/";



    static final String URL_VER = "simulator/v1.2/passthrough/mm/";
    /**
     * The Base url version with O Auth.
     */
    static final String URL_VER_OAUTH = "2/oauth/simulator/v1.2/mm/";
    /**
     * The Base url version for token.
     */
    static final String URL_VER_TOKEN = "v1/oauth/";
    /**
     * The Auth token key.
     */
    static final String AUTHORIZATION = "Authorization";
    /**
     * The Auth token prefix.
     */
    static final String AUTH_TOKEN_PREFIX = "Basic ";

    /**
     * The Bearer Token
     *
     */

    static final String AUTH_TOKEN_BEARER = "Bearer ";

    /**
     * Callback URL
     *
     */


    static final String CALL_BACK_URL = "X-Callback-URL";
    /**
     * The X APi key.
     */
    static final String X_API_KEY = "X-API-Key";
    /**
     * The Access Token.
     */
    static final String ACCESS_TOKEN = "accesstoken";
    /**
     * Client credentials.
     */
    static final String CLIENT_CREDENTIALS = "client_credentials";
}
package com.gsmaSdk.gsma.network.retrofit;


import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.interfaces.PaymentInitialiseInterface;
import com.gsmaSdk.gsma.manager.PreferenceManager;
import com.gsmaSdk.gsma.models.Balance;
import com.gsmaSdk.gsma.models.Refund;

import com.gsmaSdk.gsma.models.CodeRequest;
import com.gsmaSdk.gsma.models.RequestStateObject;
import com.gsmaSdk.gsma.models.Reversal;
import com.gsmaSdk.gsma.models.ReversalObject;
import com.gsmaSdk.gsma.models.Token;
import com.gsmaSdk.gsma.models.common.GSMAError;

import com.gsmaSdk.gsma.models.transaction.Transaction;

import com.gsmaSdk.gsma.models.common.ServiceAvailability;

import com.gsmaSdk.gsma.models.transaction.TransactionObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;

import java.util.HashMap;
import java.util.UUID;

import androidx.annotation.RestrictTo;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * The type GSMA api - api call related data is processed here.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class GSMAApi {
    private final APIService apiHelper;
    // Declare and pending requests to initialization
    private final RequestManager requestManager;
    private final HashMap<String,String> headers;
    private final MediaType mediaType = MediaType.parse("application/json");
    private Context context;


    private GSMAApi() {
        apiHelper = RetrofitHelper.getApiHelper();
        requestManager = new RequestManager();

        headers = new HashMap<>();

        headers.put(APIConstants.CALL_BACK_URL, PaymentConfiguration.getCallBackURL());
        if(PaymentConfiguration.getxAPIKey()!=null){
            headers.put(APIConstants.X_API_KEY,PaymentConfiguration.getxAPIKey());
        }
        setHeaders();
    }

    /**
     * Level of authentication check,if the level is NO_AUTH then access token is not generated
     *
     */

    public void init(Context context, PaymentInitialiseInterface paymentInitialiseInterface){
        if(PaymentConfiguration.getAuthType()!= AuthenticationType.NO_AUTH){
            String converted=APIConstants.AUTH_TOKEN_PREFIX+PaymentConfiguration.getBase64Value();
            GSMAApi.getInstance().createToken(converted,new APIRequestCallback<Token>() {
                        @Override
                        public void onSuccess(int responseCode, Token serializedResponse) {
                            paymentInitialiseInterface.onSuccess(serializedResponse);
                            PaymentConfiguration.setAuthToken(serializedResponse.getAccessToken());

                            PreferenceManager.getInstance().saveToken(serializedResponse.getAccessToken());
                        }
                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            PreferenceManager.getInstance().saveToken("");
                            paymentInitialiseInterface.onFailure(errorDetails);
                        }
                    }
            );
        }else{
            this.context=context;

        }

    }

    /**
     * Initialising header parameters
     *
     */

    public void setHeaders(){
        PreferenceManager.getInstance().init(context);
        if(PaymentConfiguration.getAuthType()!=AuthenticationType.NO_AUTH){
            headers.put(APIConstants.AUTHORIZATION,APIConstants.AUTH_TOKEN_BEARER+PreferenceManager.getInstance().retrieveToken());
        }
        headers.put(APIConstants.X_CORRELATION_ID, UUID.randomUUID().toString());
    }
    private static class SingletonCreationAdmin {
        @SuppressLint("StaticFieldLeak")
        private static final GSMAApi INSTANCE = new GSMAApi();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static GSMAApi getInstance() {
        SingletonCreationAdmin.INSTANCE.setHeaders();
        return SingletonCreationAdmin.INSTANCE;
    }

    /**
     * Create Token.
     * @param key Base 64 encode of consumer key and client secret
     * @param apiRequestCallback Listener for api operation
     */
    public void createToken(String key,APIRequestCallback<Token> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createToken(key,APIConstants.CLIENT_CREDENTIALS),apiRequestCallback));
    }

    /**
     * Check Balance.
     *
     * @param accountId Account id
     * @param apiRequestCallback Listener for api operation
     */
    public void checkBalance(String accountId,APIRequestCallback<Balance> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBalance(PaymentConfiguration.getUrlVersion(),accountId,headers),apiRequestCallback));
    }

    /**
     * Merchant Pay.
     * @param transactionType Type of transaction,merchant pay
     * @param transactionRequest  Model class for Transaction Request
     */
    public void merchantPay(String transactionType, TransactionRequest transactionRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.merchantPay(transactionType, PaymentConfiguration.getUrlVersion(),RequestBody.create(new Gson().toJson(transactionRequest),mediaType),headers),apiRequestCallback));
    }

    /**
     * View Transaction.
     * @param transactionReference Reference to a particular transaction
     * @param apiRequestCallback apiRequestCallback listener for api operation
     */
    public void viewTransaction(String transactionReference,APIRequestCallback<TransactionObject> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewTransaction(PaymentConfiguration.getUrlVersion(),transactionReference,headers),apiRequestCallback));
    }

    /**
     * View Request State.
     * @param serverCorrelationId UUID generated in server
     * @param apiRequestCallback Listener for api operation
     */
    public void viewRequestState(String serverCorrelationId,APIRequestCallback<RequestStateObject> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewRequestState(PaymentConfiguration.getUrlVersion(),serverCorrelationId,headers),apiRequestCallback));
    }

    /**
     * Refund
     * @param transactionRequest Model class for Transaction Request
     * @param apiRequestCallback Listener for api operation
     */

    public void refund(TransactionRequest transactionRequest, APIRequestCallback<Refund> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.refund(PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(transactionRequest), mediaType), headers), apiRequestCallback));
    }

    /**
     *Reversal
     * @param referenceId  Reference id for reversal of a transaction
     * @param reversalObject Model class for Reversal Object
     * @param apiRequestCallback Listener for api operation
     */
    public void reversal(String referenceId, ReversalObject reversalObject, APIRequestCallback<Reversal> apiRequestCallback){
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.reversal(PaymentConfiguration.getUrlVersion(),referenceId,RequestBody.create(new Gson().toJson(reversalObject),mediaType),headers), apiRequestCallback));
    }

    /**
     * Retrieve Transaction.
     *
     * @param accountId Account id
     * @param offset Offset
     * @param limit  Offset
     * @param apiRequestCallback Listener for api operation
     */
    public void retrieveTransaction(String accountId,int offset,int limit, APIRequestCallback<Transaction> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveTransaction(PaymentConfiguration.getUrlVersion(),accountId,headers,offset,limit),apiRequestCallback));
    }

    /**
     * Check Service Availability.
     * @param apiRequestCallback Listener for api operation
     */
    public void checkServiceAvailability(APIRequestCallback<ServiceAvailability> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.checkServiceAvailability(PaymentConfiguration.getUrlVersion(),headers),apiRequestCallback));
    }

    /**
     * obtain Authorisation code.
     *
     * @param codeRequest Model class for Authorisation Code Request
     * @param accountId Account id
     * @param apiRequestCallback Listener for api operation
     */
    public void obtainAuthorisationCode(String accountId, CodeRequest codeRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.obtainAuthorisationCode(accountId, PaymentConfiguration.getUrlVersion(),RequestBody.create(new Gson().toJson(codeRequest),mediaType),headers),apiRequestCallback));
    }

}
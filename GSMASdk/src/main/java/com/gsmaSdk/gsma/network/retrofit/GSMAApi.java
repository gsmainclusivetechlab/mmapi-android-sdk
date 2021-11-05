package com.gsmaSdk.gsma.network.retrofit;


import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.interfaces.PaymentInitialiseInterface;
import com.gsmaSdk.gsma.manager.PreferenceManager;
import com.gsmaSdk.gsma.models.common.Balance;
import com.gsmaSdk.gsma.models.transaction.Batch;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeRequest;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.GetLink;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionCompletion;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionItem;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionRejection;
import com.gsmaSdk.gsma.models.transaction.BulkTransactionObject;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.models.transaction.TransactionObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.RestrictTo;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * The type GSMA api - api call related data is processed here.
 */
@SuppressWarnings("unused")
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class GSMAApi {
    private final APIService apiHelper;
    // Declare and pending requests to initialization
    private final RequestManager requestManager;
    private final HashMap<String, String> headers;
    private final MediaType mediaType = MediaType.parse("application/json");
    private Context context;


    private GSMAApi() {
        apiHelper = RetrofitHelper.getApiHelper();
        requestManager = new RequestManager();

        headers = new HashMap<>();

        headers.put(APIConstants.CALL_BACK_URL, PaymentConfiguration.getCallBackURL());
        if (PaymentConfiguration.getxAPIKey() != null) {
            headers.put(APIConstants.X_API_KEY, PaymentConfiguration.getxAPIKey());
        }
        setHeaders();
    }

    /**
     * Level of authentication check,if the level is NO_AUTH then access token is not generated
     */

    public void init(Context context, PaymentInitialiseInterface paymentInitialiseInterface) {
        if (PaymentConfiguration.getAuthType() != AuthenticationType.NO_AUTH) {
            String converted = APIConstants.AUTH_TOKEN_PREFIX + PaymentConfiguration.getBase64Value();
            if (Utils.isOnline()) {
                GSMAApi.getInstance().createToken(converted, new APIRequestCallback<Token>() {
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
            } else {
                paymentInitialiseInterface.onValidationError(Utils.setError(0));
            }
        } else {
            this.context = context;

        }

    }

    /**
     * Initialising header parameters
     */

    public void setHeaders() {
        PreferenceManager.getInstance().init(context);
        if (PaymentConfiguration.getAuthType() != AuthenticationType.NO_AUTH) {
            headers.put(APIConstants.AUTHORIZATION, APIConstants.AUTH_TOKEN_BEARER + PreferenceManager.getInstance().retrieveToken());
        }
//        headers.put(APIConstants.X_CORRELATION_ID, UUID.randomUUID().toString());
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
     *
     * @param key                Base 64 encode of consumer key and client secret
     * @param apiRequestCallback Listener for api operation
     */
    public void createToken(String key, APIRequestCallback<Token> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createToken(key, APIConstants.CLIENT_CREDENTIALS), apiRequestCallback));
    }

    /**
     * Check Balance.
     *
     * @param accountId          Account id
     * @param apiRequestCallback Listener for api operation
     */
    public void checkBalance(String uuid, String accountId, APIRequestCallback<Balance> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBalance(PaymentConfiguration.getUrlVersion(), accountId, headers), apiRequestCallback));
    }

    /**
     * Merchant Pay.
     *
     * @param transactionType    Type of transaction,merchant pay
     * @param transactionRequest Model class for Transaction Request
     */
    public void merchantPay(String uuid, String transactionType, TransactionRequest transactionRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.merchantPay(transactionType, PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(transactionRequest), mediaType), headers), apiRequestCallback));
    }

    /**
     * View Transaction.
     *
     * @param transactionReference Reference to a particular transaction
     * @param apiRequestCallback   apiRequestCallback listener for api operation
     */
    public void viewTransaction(String uuid, String transactionReference, APIRequestCallback<TransactionObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewTransaction(PaymentConfiguration.getUrlVersion(), transactionReference, headers), apiRequestCallback));
    }

    /**
     * View Request State.
     *
     * @param serverCorrelationId UUID generated in server
     * @param apiRequestCallback  Listener for api operation
     */
    public void viewRequestState(String uuid, String serverCorrelationId, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewRequestState(PaymentConfiguration.getUrlVersion(), serverCorrelationId, headers), apiRequestCallback));
    }

    /**
     * Refund
     *
     * @param transactionRequest Model class for Transaction Request
     * @param apiRequestCallback Listener for api operation
     */

    public void refund(String uuid, TransactionRequest transactionRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.refund(PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(transactionRequest), mediaType), headers), apiRequestCallback));
    }

    /**
     * Reversal
     *
     * @param referenceId        Reference id for reversal of a transaction
     * @param reversalObject     Model class for Reversal Object
     * @param apiRequestCallback Listener for api operation
     */
    public void reversal(String uuid, String referenceId, ReversalObject reversalObject, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.reversal(PaymentConfiguration.getUrlVersion(), referenceId, RequestBody.create(new Gson().toJson(reversalObject), mediaType), headers), apiRequestCallback));
    }

    /**
     * Retrieve Transaction.
     *
     * @param accountId          Account id
     * @param offset             Offset
     * @param limit              Offset
     * @param apiRequestCallback Listener for api operation
     */
    public void retrieveTransaction(String uuid, String accountId, int offset, int limit, APIRequestCallback<Transaction> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveTransaction(PaymentConfiguration.getUrlVersion(), accountId, headers, offset, limit), apiRequestCallback));
    }

    /**
     * Check Service Availability.
     *
     * @param apiRequestCallback Listener for api operation
     */
    public void checkServiceAvailability(String uuid, APIRequestCallback<ServiceAvailability> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.checkServiceAvailability(PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }

    /**
     * obtain Authorisation code.
     *
     * @param authorisationCodeRequest        Model class for Authorisation Code Request
     * @param accountId          Account id
     * @param apiRequestCallback Listener for api operation
     */
    public void obtainAuthorisationCode(String uuid, String accountId, AuthorisationCodeRequest authorisationCodeRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.obtainAuthorisationCode(accountId, PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(authorisationCodeRequest), mediaType), headers), apiRequestCallback));
    }

    /**
     * Retrieve Missing Response.
     *
     * @param apiRequestCallback Listener for api operation
     */
    public void retrieveMissingResponse(String uuid, String correlationId, APIRequestCallback<GetLink> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveMissingResponse(correlationId, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }

    /**
     * Retrieve Missing Transaction.
     *
     * @param apiRequestCallback Listener for api operation
     */
    public void getMissingTransactions(String link, APIRequestCallback<TransactionObject> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.getMissingTransactions(link, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }

    /**
     * Retrieve Missing Code.
     *
     * @param apiRequestCallback Listener for api operation
     */
    public void getMissingCodes(String link, APIRequestCallback<AuthorisationCode> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.getMissingCodes(link, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }

    /**
     * Bulk Transaction
     *
     * @param bulkTransactionObject Model class for Bulk Transaction
     * @param apiRequestCallback Listener for api operation
     */
    public void bulkTransaction(String uuid, BulkTransactionObject bulkTransactionObject, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.bulkTransaction(PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(bulkTransactionObject), mediaType), headers), apiRequestCallback));
    }


    public void updateBatch(String uuid, String batchId, ArrayList<Batch> batchArrayList, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.updateBatchTransaction(PaymentConfiguration.getUrlVersion(),batchId,RequestBody.create(String.valueOf(new Gson().toJsonTree(batchArrayList).getAsJsonArray()),mediaType), headers), apiRequestCallback));
    }

    public void retrieveBatch(String uuid, String batchId,APIRequestCallback<BatchTransactionItem> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBatchTransaction(PaymentConfiguration.getUrlVersion(),batchId,headers), apiRequestCallback));
    }

    /**
     * Retrieve Batch Transaction Rejections.
     *
     * @param apiRequestCallback Listener for api operation
     */
    public void retrieveBatchRejections(String uuid, String batchId, APIRequestCallback<BatchTransactionRejection> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBatchRejections(batchId, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }

    /**
     * Retrieve Batch Transaction Completions.
     *
     * @param apiRequestCallback Listener for api operation
     */
    public void retrieveBatchCompletions(String uuid, String batchId, APIRequestCallback<BatchTransactionCompletion> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBatchCompletions(batchId, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }

}
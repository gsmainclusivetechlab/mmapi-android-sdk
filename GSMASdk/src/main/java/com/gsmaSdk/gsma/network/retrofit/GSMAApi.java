package com.gsmaSdk.gsma.network.retrofit;


import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.manager.PreferenceManager;

import com.gsmaSdk.gsma.models.AccountHolderObject;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeItem;

import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeRequest;
import com.gsmaSdk.gsma.models.common.Balance;
import com.gsmaSdk.gsma.models.common.GetLink;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.models.transaction.Batch;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionCompletion;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionItem;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionRejection;
import com.gsmaSdk.gsma.models.transaction.BulkTransactionObject;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.RestrictTo;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Class for processing api services.
 */
@SuppressWarnings("ALL")
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
    }

    /**
     * Initialising header parameters
     */
    public void setHeaders() {
        PreferenceManager.getInstance().init(context);
        if (PaymentConfiguration.getAuthType() != AuthenticationType.NO_AUTH) {
            headers.put(APIConstants.AUTHORIZATION, APIConstants.AUTH_TOKEN_BEARER + PreferenceManager.getInstance().retrieveToken());
        }

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


    /*************************************Common Functions********************************************/


    /**
     * Create Token.
     *
     * @param apiRequestCallback Listener for api operation
     */
    public void createToken(APIRequestCallback<Token> apiRequestCallback) {
        String key = APIConstants.AUTH_TOKEN_PREFIX + PaymentConfiguration.getBase64Value();
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createToken(key, APIConstants.CLIENT_CREDENTIALS), apiRequestCallback));
    }



    /**
     * Check Service Availability.
     *
     * @param uuid               UUID
     * @param apiRequestCallback Listener for api operation
     */
    public void checkServiceAvailability(String uuid, APIRequestCallback<ServiceAvailability> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.checkServiceAvailability(PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }



    /**
     * Merchant Pay.
     *
     * @param uuid               UUID
     * @param transactionType    Type of transaction - merchant pay
     * @param transactionRequest Model class for Transaction Request
     */
    public void initiatePayment(String uuid, Enum notificationMethod, String callbackUrl, String transactionType, TransactionRequest transactionRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.initiatePayment(transactionType, PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(transactionRequest), mediaType), headers), apiRequestCallback));
    }


    /**
     * Check Balance.
     *
     * @param uuid               UUID
     * @param accountIdentifier          Account id
     * @param apiRequestCallback Listener for api operation
     */
    public void checkBalance(String uuid, String accountIdentifier, APIRequestCallback<Balance> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBalance(PaymentConfiguration.getUrlVersion(),  accountIdentifier, headers), apiRequestCallback));
    }


    /**
     * View Transaction.
     *
     * @param uuid                 UUID
     * @param transactionReference Reference to a particular transaction
     * @param apiRequestCallback   apiRequestCallback listener for api operation
     */
    public void viewTransaction(String uuid, String transactionReference, APIRequestCallback<TransactionRequest> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewTransaction(PaymentConfiguration.getUrlVersion(), transactionReference, headers), apiRequestCallback));
    }

    /**
     * View Request State.
     *
     * @param uuid                UUID
     * @param serverCorrelationId UUID generated in server
     * @param apiRequestCallback  Listener for api operation
     */
    public void viewRequestState(String uuid, String serverCorrelationId, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewRequestState(PaymentConfiguration.getUrlVersion(), serverCorrelationId, headers), apiRequestCallback));
    }




    /**
     * Retrieve Transaction.
     *
     * @param uuid               UUID
     * @param accountIdentifier          Account id
     * @param offset             Offset
     * @param limit              limit
     * @param apiRequestCallback Listener for api operation
     */
    public void retrieveTransaction(String uuid, String accountIdentifier, int offset, int limit, APIRequestCallback<Transaction> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveTransaction(PaymentConfiguration.getUrlVersion(), accountIdentifier, headers, offset, limit), apiRequestCallback));
    }





    /**
     * Reversal
     *
     * @param uuid               UUID
     * @param referenceId        Reference id for reversal of a transaction
     * @param reversalObject     Model class for Reversal Object
     * @param apiRequestCallback Listener for api operation
     */
    public void reversal(String uuid, Enum notificationMethod, String callbackUrl, String referenceId, ReversalObject reversalObject, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.reversal(PaymentConfiguration.getUrlVersion(), referenceId, RequestBody.create(new Gson().toJson(reversalObject), mediaType), headers), apiRequestCallback));
    }

    /**
     * Retrieve Missing Response.
     *
     * @param uuid               UUID
     * @param apiRequestCallback Listener for api operation
     */
    public void retrieveMissingResponse(String uuid, String correlationId, APIRequestCallback<GetLink> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveMissingResponse(correlationId, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }

    /**
     * Retrieve Missing Transaction.
     *
     * @param link               url received from the response
     * @param apiRequestCallback Listener for api operation
     */
    public void getMissingTransactions(String link, APIRequestCallback<TransactionRequest> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.getMissingTransactions(link, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }



    /*************************************Merchant Payment Functions************************************************/




    /**
     * obtain Authorisation code.
     *
     * @param uuid                     UUID
     * @param authorisationCodeRequest Model class for Authorisation Code Request
     * @param accountIdentifier        identifier for account
     * @param apiRequestCallback       Listener for api operation
     */
    public void obtainAuthorisationCode(String uuid, Enum notificationMethod, String callbackUrl, String accountIdentifier, AuthorisationCodeRequest authorisationCodeRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.obtainAuthorisationCode(accountIdentifier, PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(authorisationCodeRequest), mediaType), headers), apiRequestCallback));
    }


    /*
    * View Authorization Code
    * @param uuid UUID
    * @param accountIdentifier Identifier type of the account
    * @param accountId Identifier No
    * @Param authorisationCode Authorization Code
    */

    public void viewAuthorizationCode(String uuid,String accountIdentifier,String authorizationCode,APIRequestCallback<AuthorisationCodeItem> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewAuthorizationCode(PaymentConfiguration.getUrlVersion(),accountIdentifier,authorizationCode,headers),apiRequestCallback));
    }


    /**
     * Retrieve Missing Code.
     *
     * @param link               url received from the response
     * @param apiRequestCallback Listener for api operation
     */
    public void getMissingCodes(String link, APIRequestCallback<AuthorisationCode> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.getMissingCodes(link, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }




    /**
     * Refund
     *
     * @param uuid               UUID
     * @param transactionRequest Model class for Transaction Request
     * @param apiRequestCallback Listener for api operation
     */

    public void refund(String uuid, Enum notificationMethod, String callbackUrl, TransactionRequest transactionRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.refund(PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(transactionRequest), mediaType), headers), apiRequestCallback));
    }

    /******************************************Disbursement Functions******************************************/

    /**
     * Bulk Transaction
     *
     * @param uuid                  UUID
     * @param bulkTransactionObject Model class for Bulk Transaction
     * @param apiRequestCallback    Listener for api operation
     */
    public void bulkTransaction(String uuid, Enum notificationMethod, String callbackUrl, BulkTransactionObject bulkTransactionObject, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.bulkTransaction(PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(bulkTransactionObject), mediaType), headers), apiRequestCallback));
    }

    /**
     * Retrieve Batch Transaction Rejections.
     *
     * @param uuid               UUID
     * @param batchId            batch Id of a batch transaction
     * @param apiRequestCallback Listener for api operation
     */
    public void retrieveBatchRejections(String uuid, String batchId, APIRequestCallback<BatchTransactionRejection> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBatchRejections(batchId, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }


    /**
     * Retrieve Batch Transaction Completions.
     *
     * @param uuid               UUID
     * @param batchId            batch Id of a batch transaction
     * @param apiRequestCallback Listener for api operation
     */
    public void retrieveBatchCompletions(String uuid, String batchId, APIRequestCallback<BatchTransactionCompletion> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBatchCompletions(batchId, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }

    /**
     * Update Batch Transaction.
     *
     * @param uuid               UUID
     * @param batchId            batch Id of a batch transaction
     * @param apiRequestCallback Listener for api operation
     */
    public void updateBatch(String uuid, Enum notificationMethod, String callbackUrl, String batchId, ArrayList<Batch> batchArrayList, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.updateBatchTransaction(PaymentConfiguration.getUrlVersion(), batchId, RequestBody.create(String.valueOf(new Gson().toJsonTree(batchArrayList).getAsJsonArray()), mediaType), headers), apiRequestCallback));
    }



    /**
     * Retrieve a Batch Transaction.
     *
     * @param uuid               UUID
     * @param batchId            batch Id of a batch transaction
     * @param apiRequestCallback Listener for api operation
     */
    public void retrieveBatch(String uuid, String batchId, APIRequestCallback<BatchTransactionItem> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBatchTransaction(PaymentConfiguration.getUrlVersion(), batchId, headers), apiRequestCallback));
    }



    /***************************************International Transfers Functions**************************************/

    /**
     * Request Quotation
     *
     * @param uuid               UUID
     * @param transactionRequest - International Transfer
     * @param apiRequestCallback Listener for api operation
     */
    public void requestQuotation(String uuid, Enum notificationMethod, String callbackUrl, TransactionRequest transactionRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.requestQuotation(PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(transactionRequest), mediaType), headers), apiRequestCallback));
    }



    /**
     * View Quotation
     *
     * @param uuid UUID
     * @param quotationReference - Quotation refernce of requested quotation
     * @param apiRequestCallback Listener for api operation
     */
    public void viewQuotation(String uuid,String quotationReference,APIRequestCallback<TransactionRequest> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewQuotation(PaymentConfiguration.getUrlVersion(), quotationReference, headers), apiRequestCallback));
    }


   /****************************************P2p Transfers Functions****************************************/

    /**
     * Retrieve Account Name.
     *
     * @param uuid               UUID
     * @param accountIdentifier          Account id
     * @param apiRequestCallback Listener for api operation
     */
    public void viewAccountName(String uuid, String accountIdentifier, APIRequestCallback<AccountHolderObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewAccountName(PaymentConfiguration.getUrlVersion(),  accountIdentifier, headers), apiRequestCallback));
    }



}

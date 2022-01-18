package com.gsmaSdk.gsma.network.retrofit;


import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.manager.PreferenceManager;
import com.gsmaSdk.gsma.models.account.Account;
import com.gsmaSdk.gsma.models.account.AccountHolderName;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Link;
import com.gsmaSdk.gsma.models.bills.BillPay;
import com.gsmaSdk.gsma.models.bills.BillPayments;
import com.gsmaSdk.gsma.models.bills.Bills;
import com.gsmaSdk.gsma.models.debitmandate.DebitMandate;
import com.gsmaSdk.gsma.models.common.MissingResponse;

import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodes;
import com.gsmaSdk.gsma.models.common.GetLink;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.models.transaction.PatchData;
import com.gsmaSdk.gsma.models.transaction.batchcompletion.BatchCompletions;
import com.gsmaSdk.gsma.models.transaction.batchrejection.BatchRejections;
import com.gsmaSdk.gsma.models.transaction.batchtransaction.BatchTransaction;
import com.gsmaSdk.gsma.models.transaction.quotation.Quotation;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;
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
    public void initiatePayment(String uuid, Enum notificationMethod, String callbackUrl, String transactionType, Transaction transactionRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
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
     * @param accountIdentifier  Account id
     * @param apiRequestCallback Listener for api operation
     */
    public void checkBalance(String uuid, String accountIdentifier, APIRequestCallback<Balance> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveBalance(PaymentConfiguration.getUrlVersion(), accountIdentifier, headers), apiRequestCallback));
    }


    /**
     * View Transaction.
     *
     * @param uuid                 UUID
     * @param transactionReference Reference to a particular transaction
     * @param apiRequestCallback   apiRequestCallback listener for api operation
     */
    public void viewTransaction(String uuid, String transactionReference, APIRequestCallback<Transaction> apiRequestCallback) {
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
     * @param accountIdentifier  Account id
     * @param params             Hashmap-Filter list
     * @param apiRequestCallback Listener for api operation
     */
    public void retrieveTransaction(String uuid, String accountIdentifier, HashMap<String, String> params, APIRequestCallback<Transactions> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveTransaction(PaymentConfiguration.getUrlVersion(), accountIdentifier, headers, params), apiRequestCallback));
    }


    /**
     * Reversal
     *
     * @param uuid               UUID
     * @param notificationMethod
     * @param referenceId        Reference id for reversal of a transaction
     * @param reversalObject     Model class for Reversal Object
     * @param apiRequestCallback Listener for api operation
     */
    public void reversal(String uuid, Enum notificationMethod, String callbackUrl, String referenceId, Reversal reversalObject, APIRequestCallback<RequestStateObject> apiRequestCallback) {
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
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.retrieveMissingLink(correlationId, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }

    /**
     * Retrieve Missing Transaction.
     *
     * @param link               url received from the response
     * @param apiRequestCallback Listener for api operation
     */
    public void getMissingTransactions(String link, APIRequestCallback<MissingResponse> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.getMissingResponses(link, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
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
    public void obtainAuthorisationCode(String uuid, Enum notificationMethod, String callbackUrl, String accountIdentifier, AuthorisationCode authorisationCodeRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.obtainAuthorisationCode(accountIdentifier, PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(authorisationCodeRequest), mediaType), headers), apiRequestCallback));
    }


    /**
     * View Authorization Code
     *
     * @param uuid              UUID
     * @param accountIdentifier Identifier type of the account
     * @param accountId         Identifier No
     * @Param authorisationCode Authorization Code
     */

    public void viewAuthorizationCode(String uuid, String accountIdentifier, String authorizationCode, APIRequestCallback<AuthorisationCode> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewAuthorizationCode(PaymentConfiguration.getUrlVersion(), accountIdentifier, authorizationCode, headers), apiRequestCallback));
    }


    /**
     * Retrieve Missing Code.
     *
     * @param link               url received from the response
     * @param apiRequestCallback Listener for api operation
     */
    public void getMissingCodes(String link, APIRequestCallback<AuthorisationCodes> apiRequestCallback) {
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.getMissingCodes(link, PaymentConfiguration.getUrlVersion(), headers), apiRequestCallback));
    }


    /**
     * Refund
     *
     * @param uuid               UUID
     * @param transactionRequest Model class for Transaction Request
     * @param apiRequestCallback Listener for api operation
     */

    public void refund(String uuid, Enum notificationMethod, String callbackUrl, Transaction transactionRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
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
    public void bulkTransaction(String uuid, Enum notificationMethod, String callbackUrl, BatchTransaction bulkTransactionObject, APIRequestCallback<RequestStateObject> apiRequestCallback) {
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
    public void retrieveBatchRejections(String uuid, String batchId, APIRequestCallback<BatchRejections> apiRequestCallback) {
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
    public void retrieveBatchCompletions(String uuid, String batchId, APIRequestCallback<BatchCompletions> apiRequestCallback) {
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
    public void updateBatch(String uuid, Enum notificationMethod, String callbackUrl, String batchId, ArrayList<PatchData> batchArrayList, APIRequestCallback<RequestStateObject> apiRequestCallback) {
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
    public void retrieveBatch(String uuid, String batchId, APIRequestCallback<BatchTransaction> apiRequestCallback) {
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
    public void requestQuotation(String uuid, Enum notificationMethod, String callbackUrl, Quotation quotationRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.requestQuotation(PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(quotationRequest), mediaType), headers), apiRequestCallback));
    }


    /**
     * View Quotation
     *
     * @param uuid               UUID
     * @param quotationReference - Quotation refernce of requested quotation
     * @param apiRequestCallback Listener for api operation
     */
    public void viewQuotation(String uuid, String quotationReference, APIRequestCallback<Transaction> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewQuotation(PaymentConfiguration.getUrlVersion(), quotationReference, headers), apiRequestCallback));
    }


    /****************************************P2p Transfers Functions****************************************/

    /**
     * Retrieve Account Name.
     *
     * @param uuid               UUID
     * @param accountIdentifier  Account id
     * @param apiRequestCallback Listener for api operation
     */
    public void viewAccountName(String uuid, String accountIdentifier, APIRequestCallback<AccountHolderName> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewAccountName(PaymentConfiguration.getUrlVersion(), accountIdentifier, headers), apiRequestCallback));
    }


    /****************************************Recurring Payments********************************************/

    /**
     * Create Debit Mandates
     *
     * @param uuid               UUID
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param accountIdentifier  Account id
     * @param apiRequestCallback Listener for api operation
     */
    public void createAccountDebitMandate(String uuid, Enum notificationMethod, String callbackUrl, String accountIdentifier, DebitMandate debitMandateRequest, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createAccountDebitMandate(PaymentConfiguration.getUrlVersion(), accountIdentifier, RequestBody.create(new Gson().toJson(debitMandateRequest), mediaType), headers), apiRequestCallback));
    }


    /**
     * View Debit Mandate
     *
     * @param uuid                 UUID
     * @param accountIdentifier    - Account Id
     * @param transactionReference - Quotation refernce of requested quotation
     * @param apiRequestCallback   Listener for api operation
     */
    public void viewAccountDebitMandate(String uuid, String accountIdentifier, String transactionReference, APIRequestCallback<DebitMandate> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewAccountDebitMandate(PaymentConfiguration.getUrlVersion(), accountIdentifier, transactionReference, headers), apiRequestCallback));
    }

/****************************************Account Linking********************************************/

    /**
     * Create Account Linking
     *
     * @param uuid               UUID
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param accountIdentifier  Account id
     * @param apiRequestCallback Listener for api operation
     */
    public void createAccountLinking(String uuid, Enum notificationMethod, String callbackUrl, String accountIdentifier, Link accountLinkingObject, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createAccountLinking(PaymentConfiguration.getUrlVersion(), accountIdentifier, RequestBody.create(new Gson().toJson(accountLinkingObject), mediaType), headers), apiRequestCallback));
    }

    /**
     * View Link
     *
     * @param uuid               UUID
     * @param accountIdentifier  - Account Id
     * @param linkReference      - Link Reference
     * @param apiRequestCallback Listener for api operation
     */
    public void viewAccountLink(String uuid, String accountIdentifier, String linkReference, APIRequestCallback<Link> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewAccountLink(PaymentConfiguration.getUrlVersion(), accountIdentifier, linkReference, headers), apiRequestCallback));
    }

/****************************************Bill Payments********************************************/
    /**
     * View Bill Payments
     *
     * @param uuid               UUID
     * @param accountIdentifier  - Account Id
     * @param billReference      - Bill Reference
     * @param apiRequestCallback Listener for api operation
     */
    public void viewBillPayment(String uuid, String accountIdentifier, HashMap<String, String> params, String billReference, APIRequestCallback<BillPayments> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewBillPayment(PaymentConfiguration.getUrlVersion(), accountIdentifier, billReference, headers, params), apiRequestCallback));
    }


    /****************************************Bill Payments********************************************/


    /**
     * Retrieval of Bills
     *
     * @param uuid               UUID
     * @param accountIdentifier  Account id
     * @param params             Hashmap-Filter list
     * @param apiRequestCallback Listener for api operation
     */
    public void viewAccountBills(String uuid, String accountIdentifier, HashMap<String, String> params, APIRequestCallback<Bills> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewAccountBills(PaymentConfiguration.getUrlVersion(), accountIdentifier, headers, params), apiRequestCallback));
    }


    /**
     * Bill Payment
     *
     * @param uuid               UUID
     * @param transactionType    Type of transaction - merchant pay
     * @param transactionRequest Model class for Transaction Request
     * @param apiRequestCallback Listener for api operation
     */
    public void createBillPayment(String uuid, Enum notificationMethod, String accountIdentifier, String callbackUrl, String billReference, BillPay billPayment, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createBillPayment(PaymentConfiguration.getUrlVersion(), accountIdentifier, billReference, RequestBody.create(new Gson().toJson(billPayment), mediaType), headers), apiRequestCallback));
    }


    /****************************************Agent Service********************************************/


    /**
     * Create Account
     *
     * @param uuid               UUID
     * @param callbackUrl        Callback Url
     * @param account            Model class for create account Request
     * @param apiRequestCallback Listener for api operation
     */
    public void createAccount(String uuid, Enum notificationMethod, String callbackUrl, Account account, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createAccount(PaymentConfiguration.getUrlVersion(), RequestBody.create(new Gson().toJson(account), mediaType), headers), apiRequestCallback));
    }


    /**
     * Retrieval of Bills
     *
     * @param uuid               UUID
     * @param accountIdentifier  Account id
     * @param apiRequestCallback Listener for api operation
     */
    public void viewAccount(String uuid, String accountIdentifier, APIRequestCallback<Account> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.viewAccount(PaymentConfiguration.getUrlVersion(), accountIdentifier, headers), apiRequestCallback));
    }


    /**
     * Update Account Idnetity - Update the KYC status of an account
     *
     * @param uuid               UUID
     * @param callbackUrl        Callback Url
     * @param identityId         The id used for identifying an account
     * @param accountIdentifier  The string combination of account identifiers
     * @param patchDataArrayList The array of patch data object to update the status of KYC
     * @param apiRequestCallback Listener for api operation
     */
    public void updateAccountIdentity(String uuid, Enum notificationMethod, String callbackUrl, String identityId, String acccountIdentifier, ArrayList<PatchData> patchDataArrayList, APIRequestCallback<RequestStateObject> apiRequestCallback) {
        headers.put(APIConstants.X_CORRELATION_ID, uuid);
        String xCallback = Utils.setCallbackUrl(notificationMethod, callbackUrl);
        if (xCallback.isEmpty()) {
            headers.remove(APIConstants.CALL_BACK_URL);
        } else {
            headers.put(APIConstants.CALL_BACK_URL, xCallback);
        }
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.updateAccountIdentity(PaymentConfiguration.getUrlVersion(), acccountIdentifier, identityId, RequestBody.create(new Gson().toJson(patchDataArrayList), mediaType),headers), apiRequestCallback));
    }


}




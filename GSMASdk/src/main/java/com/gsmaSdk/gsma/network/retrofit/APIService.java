package com.gsmaSdk.gsma.network.retrofit;


import com.gsmaSdk.gsma.models.account.AccountHolderName;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Link;
import com.gsmaSdk.gsma.models.bills.BillPayments;
import com.gsmaSdk.gsma.models.bills.Bills;
import com.gsmaSdk.gsma.models.debitmandate.DebitMandate;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodes;
import com.gsmaSdk.gsma.models.bills.BillPay;
import com.gsmaSdk.gsma.models.common.GetLink;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.models.transaction.batchcompletion.BatchCompletion;
import com.gsmaSdk.gsma.models.transaction.batchrejection.BatchRejection;
import com.gsmaSdk.gsma.models.transaction.batchtransaction.BatchTransaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

import java.util.Map;

import androidx.annotation.RestrictTo;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Interface for Retrofit methods
 */
@SuppressWarnings("ALL")
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface APIService {


    /************************************Common  API interfaces*********************************************/

    /**
     * Generate Access Token call.
     *
     * @return the call
     */
    @FormUrlEncoded
    @POST(APIConstants.URL_VER_TOKEN + APIConstants.ACCESS_TOKEN)
    Call<Token> createToken(@Header(APIConstants.AUTHORIZATION) String key, @Field("grant_type") String clientCredentials);


    /**
     * Check for service availability
     *
     * @return the call
     */
    @GET("{version}/heartbeat")
    Call<ServiceAvailability> checkServiceAvailability(@Path(value = "version", encoded = true) String version, @HeaderMap Map<String, String> headers);


    /**
     * perform a transaction.
     *
     * @return the call
     */
    @POST("{version}/transactions/type/{transactionType}")
    Call<RequestStateObject> initiatePayment(@Path("transactionType") String type, @Path(value = "version", encoded = true) String version, @Body RequestBody transaction, @HeaderMap Map<String, String> headers);


    /**
     * Reversal.
     *
     * @return the call
     */
    @POST("{version}/transactions/{referenceId}/reversals")
    Call<RequestStateObject> reversal(@Path(value = "version", encoded = true) String version, @Path("referenceId") String referenceId, @Body RequestBody transaction, @HeaderMap Map<String, String> headers);


    /**
     * Retrieve Balance.
     *
     * @return the call
     */

    @GET("{version}/accounts/{accountIdentifier}/balance")
    Call<Balance> retrieveBalance(@Path(value = "version", encoded = true) String version, @Path(value = "accountIdentifier", encoded = true) String accountIdentifier, @HeaderMap Map<String, String> headers);


    /**
     * View Transaction
     *
     * @return the call
     */
    @GET("{version}/transactions/{id}")
    Call<Transaction> viewTransaction(@Path(value = "version", encoded = true) String version, @Path("id") String id, @HeaderMap Map<String, String> headers);

    /**
     * View Request state.
     *
     * @return the call
     */
    @GET("{version}/requeststates/{id}")
    Call<RequestStateObject> viewRequestState(@Path(value = "version", encoded = true) String version, @Path("id") String id, @HeaderMap Map<String, String> headers);


    /**
     * View transactions list
     *
     * @return the call
     */
    @GET("{version}/accounts/{accountIdentfiers}/transactions")
    Call<Transactions> retrieveTransaction(@Path(value = "version", encoded = true) String version,
                                           @Path(value = "accountIdentfiers", encoded = true) String accountIdentfiers,
                                           @HeaderMap Map<String, String> headers,
                                           @QueryMap Map<String, String> params);


    /**
     * Get Link of  Missing Response
     *
     * @return the call
     */
    @GET("{version}/responses/{correlationId}")
    Call<GetLink> retrieveMissingResponse(@Path("correlationId") String correlationId, @Path(value = "version", encoded = true) String version, @HeaderMap Map<String, String> headers);

    /**
     * Check for Retrieve Missing Transaction
     *
     * @return the call
     */
    @GET("{version}/{url}")
    Call<MissingResponse> getMissingTransactions(@Path(value = "url", encoded = true) String url, @Path(value = "version", encoded = true) String version, @HeaderMap Map<String, String> headers);


    /*************************************Merchant Payments API Interfaces********************************/


    /**
     * Obtain Authorisation code.
     *
     * @return the call
     */
    @POST("{version}/accounts/{accountIdentifier}/authorisationcodes")
    Call<RequestStateObject> obtainAuthorisationCode(@Path(value = "accountIdentifier", encoded = true) String accountId, @
            Path(value = "version", encoded = true) String version,
                                                     @Body RequestBody codeRequest, @HeaderMap Map<String, String> headers);


    /**
     * View Authorization Code
     *
     * @return call
     */

    @GET("{version}/accounts/{accountIdentifier}/authorisationcodes/{authorizationCode}")
    Call<AuthorisationCode> viewAuthorizationCode(@Path(value = "version", encoded = true) String version,
                                                  @Path(value = "accountIdentifier", encoded = true) String accountIdentifier,
                                                  @Path("authorizationCode") String authorizationCode,
                                                  @HeaderMap Map<String, String> headers);


    /**
     * Check for Retrieve Missing Code
     *
     * @return the call
     */
    @GET("{version}/{url}")
    Call<AuthorisationCodes> getMissingCodes(@Path(value = "url", encoded = true)
                                                     String url, @Path(value = "version", encoded = true) String version, @HeaderMap Map<String, String> headers);


    /**
     * Refund.
     *
     * @return the call
     */
    @POST("{version}/transactions/type/adjustment")
    Call<RequestStateObject> refund(@Path(value = "version", encoded = true) String version, @Body RequestBody transaction, @HeaderMap Map<String, String> headers);


    /********************************Disbursment API interfaces***************************************/


    /**
     * Obtain a bulk Disbursement
     *
     * @return the call
     */
    @POST("{version}/batchtransactions")
    Call<RequestStateObject> bulkTransaction(@Path(value = "version", encoded = true) String version, @Body RequestBody bulkTransactionObject, @HeaderMap Map<String, String> headers);


    /**
     * Batch Transaction Rejection
     *
     * @return the call
     */
    @GET("{version}/batchtransactions/{id}/rejections")
    Call<BatchRejection> retrieveBatchRejections(@Path("id") String id, @Path(value = "version", encoded = true) String version, @HeaderMap Map<String, String> headers);


    /**
     * Batch Transaction Completion
     *
     * @return the call
     */
    @GET("{version}/batchtransactions/{id}/completions")
    Call<BatchCompletion> retrieveBatchCompletions(@Path("id") String id, @Path(value = "version", encoded = true) String version, @HeaderMap Map<String, String> headers);


    /**
     * Update a batch transaction
     */
    @PATCH("{version}/batchtransactions/{batchId}")
    Call<RequestStateObject> updateBatchTransaction(@Path(value = "version", encoded = true) String version, @Path("batchId") String batchId, @Body RequestBody batchTransactionObject, @HeaderMap Map<String, String> headers);


    /**
     * Retrieve a batch transaction
     */
    @GET("{version}/batchtransactions/{batchId}")
    Call<BatchTransaction> retrieveBatchTransaction(@Path(value = "version", encoded = true) String version, @Path("batchId") String batchId, @HeaderMap Map<String, String> headers);


    /*********************************International Transfers API interfaces************************/


    @POST("{version}/quotations")
    Call<RequestStateObject> requestQuotation(@Path(value = "version", encoded = true) String version, @Body RequestBody quoRequestBody, @HeaderMap Map<String, String> headers);


    @GET("{version}/quotations/{quotationReference}")
    Call<Transaction> viewQuotation(@Path(value = "version", encoded = true) String version, @Path("quotationReference") String quotationReference, @HeaderMap Map<String, String> headers);


    /***************************************P2P Transefer API Interfaces********************************/

    /**
     * View Account Name.
     *
     * @return the call
     */
    @SuppressWarnings("SpellCheckingInspection")
    @GET("{version}/accounts/{accountIdentifier}/accountname")
    Call<AccountHolderName> viewAccountName(@Path(value = "version", encoded = true) String version, @Path(value = "accountIdentifier", encoded = true) String accountIdentifier, @HeaderMap Map<String, String> headers);


    /*****************************************Recurring Payment Interfaces******************************/

    /**
     * Create Debit Mandate.
     *
     * @return the call
     */
    @SuppressWarnings("SpellCheckingInspection")
    @POST("{version}/accounts/{accountIdentifier}/debitmandates")
    Call<RequestStateObject> createAccountDebitMandate(@Path(value = "version", encoded = true) String version, @Path(value = "accountIdentifier", encoded = true) String accountIdentifier, @Body RequestBody debitMandate, @HeaderMap Map<String, String> headers);

    /**
     * View Debit Mandate.
     *
     * @return the call
     */
    @SuppressWarnings("SpellCheckingInspection")
    @GET("{version}/accounts/{accountIdentifier}/debitmandates/{transactionReference}")
    Call<DebitMandate> viewAccountDebitMandate(@Path(value = "version", encoded = true) String version, @Path(value = "accountIdentifier", encoded = true) String accountIdentifier, @Path("transactionReference") String transactionReference, @HeaderMap Map<String, String> headers);


    /*****************************************Account Linking Interfaces******************************/

    /**
     * Create Debit Mandate.
     *
     * @return the call
     */
    @SuppressWarnings("SpellCheckingInspection")
    @POST("{version}/accounts/{accountIdentifier}/links")
    Call<RequestStateObject> createAccountLinking(@Path(value = "version", encoded = true) String version, @Path(value = "accountIdentifier", encoded = true) String accountIdentifier, @Body RequestBody debitMandate, @HeaderMap Map<String, String> headers);

    /**
     * View Debit Mandate.
     *
     * @return the call
     */
    @SuppressWarnings("SpellCheckingInspection")
    @GET("{version}/accounts/{accountIdentifier}/links/{linkReference}")
    Call<Link> viewAccountLink(@Path(value = "version", encoded = true) String version, @Path(value = "accountIdentifier", encoded = true) String accountIdentifier, @Path("linkReference") String transactionReference, @HeaderMap Map<String, String> headers);



    /*********************************Bill Payment Interfaces******************************/

    /**
     * viewAccountBills
     *
     * @return the call
     */
    @GET("{version}/accounts/{accountIdentfiers}/bills")
    Call<Bills> viewAccountBills(@Path(value = "version", encoded = true) String version,
                                 @Path(value = "accountIdentfiers", encoded = true) String accountIdentfiers,
                                 @HeaderMap Map<String, String> headers,
                                 @QueryMap Map<String, String> params);

    /**
     * Make a Bill Payment
     *
     * @return the call
     */
    @POST("{version}/accounts/{accountIdentfiers}/bills/{billReference}/payments")
    Call<RequestStateObject> createBillPayment(@Path(value = "version", encoded = true) String version,
                                               @Path(value = "accountIdentfiers", encoded = true) String accountIdentfiers,
                                               @Path(value = "billReference", encoded = true) String billReference,
                                               @Body RequestBody billPayment,
                                               @HeaderMap Map<String, String> headers);

    /**
     * View Bill Payments.
     *
     * @return the call
     */
    @SuppressWarnings("SpellCheckingInspection")
    @GET("{version}/accounts/{accountIdentifier}/bills/{billReference}/payments")
    Call<BillPayments> viewBillPayment(@Path(value = "version", encoded = true) String version, @Path(value = "accountIdentifier", encoded = true) String accountIdentifier, @Path("billReference") String billReference, @HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);

}

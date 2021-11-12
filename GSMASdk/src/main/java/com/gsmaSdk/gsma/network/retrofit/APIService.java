package com.gsmaSdk.gsma.network.retrofit;


import com.gsmaSdk.gsma.models.common.Balance;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.common.GetLink;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionCompletion;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionItem;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionRejection;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

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
import retrofit2.http.Query;

/**
 * Interface for Retrofit methods
 */
@SuppressWarnings("ALL")
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface APIService {

    /**
     * Refund.
     *
     * @return the call
     */
    @POST("{version}/transactions/type/adjustment")
    Call<RequestStateObject> refund(@Path(value = "version", encoded = true) String version, @Body RequestBody transaction, @HeaderMap Map<String, String> headers);

    /**
     * Reversal.
     *
     * @return the call
     */
    @POST("{version}/transactions/{referenceId}/reversals")
    Call<RequestStateObject> reversal(@Path(value = "version", encoded = true) String version, @Path("referenceId") String referenceId, @Body RequestBody transaction, @HeaderMap Map<String, String> headers);

    /**
     * Generate Access Token call.
     *
     * @param key encoded key
     * @return the call
     */
    @FormUrlEncoded
    @POST(APIConstants.URL_VER_TOKEN + APIConstants.ACCESS_TOKEN)
    Call<Token> createToken(@Header(APIConstants.AUTHORIZATION) String key, @Field("grant_type") String clientCredentials);

    /**
     * Retrieve Balance.
     *
     * @return the call
     */
    @SuppressWarnings("SpellCheckingInspection")
    @GET("{version}/accounts/accountid/{id}/balance")
    Call<Balance> retrieveBalance(@Path(value = "version", encoded = true) String version, @Path("id") String id, @HeaderMap Map<String, String> headers);

    /**
     * Start a Merchant Pay Transaction.
     *
     * @return the call
     */
    @POST("{version}/transactions/type/{transactionType}")
    Call<RequestStateObject> initiatePayment(@Path("transactionType") String type, @Path(value = "version", encoded = true) String version, @Body RequestBody transaction, @HeaderMap Map<String, String> headers);



    @POST("{version}/quotations")
    Call<RequestStateObject> requestQuotation(@Path(value = "version", encoded = true) String version, @Body RequestBody transaction, @HeaderMap Map<String, String> headers);

    /**
     * View Transaction
     *
     * @return the call
     */
    @GET("{version}/transactions/{id}")
    Call<TransactionRequest> viewTransaction(@Path(value = "version", encoded = true) String version, @Path("id") String id, @HeaderMap Map<String, String> headers);

    /**
     * View Request state.
     *
     * @return the call
     */
    @GET("{version}/requeststates/{id}")
    Call<RequestStateObject> viewRequestState(@Path(value = "version", encoded = true) String version, @Path("id") String id, @HeaderMap Map<String, String> headers);

    /**
     * View Request state.
     *
     * @return the call
     */
    @GET("{version}/accounts/accountid/{id}/transactions")
    Call<Transaction> retrieveTransaction(@Path(value = "version", encoded = true) String version, @Path("id") String id, @HeaderMap Map<String, String> headers, @Query(value = "offset") int offset, @Query(value = "limit") int limit);

    /**
     * Check for service availability
     *
     * @return the call
     */
    @GET("{version}/heartbeat")
    Call<ServiceAvailability> checkServiceAvailability(@Path(value = "version", encoded = true) String version, @HeaderMap Map<String, String> headers);

    /**
     * Obtain Authorisation code.
     *
     * @return the call
     */
    @POST("{version}/accounts/accountid/{id}/authorisationcodes")
    Call<RequestStateObject> obtainAuthorisationCode(@Path("id") String accountId, @Path(value = "version", encoded = true) String version, @Body RequestBody codeRequest, @HeaderMap Map<String, String> headers);

    /**
     * Check for Retrieve Missing Response
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
    Call<TransactionRequest> getMissingTransactions(@Path(value = "url", encoded = true) String url, @Path(value = "version", encoded = true) String version, @HeaderMap Map<String, String> headers);

    /**
     * Check for Retrieve Missing Code
     *
     * @return the call
     */
    @GET("{version}/{url}")
    Call<AuthorisationCode> getMissingCodes(@Path(value = "url", encoded = true) String url, @Path(value = "version", encoded = true) String version, @HeaderMap Map<String, String> headers);

    /**
     * Obtain Authorisation code.
     *
     * @return the call
     */
    @POST("{version}/batchtransactions")
    Call<RequestStateObject> bulkTransaction(@Path(value = "version", encoded = true) String version, @Body RequestBody bulkTransactionObject, @HeaderMap Map<String, String> headers);

    /**
     *
     * Update a batch transaction
     */
    @PATCH("{version}/batchtransactions/{batchId}")
    Call<RequestStateObject> updateBatchTransaction(@Path(value = "version", encoded = true) String version,@Path("batchId") String batchId,@Body RequestBody batchTransactionObject, @HeaderMap Map<String, String> headers);


    /**
     * Retrieve a batch transaction
     *
     */
    @GET("{version}/batchtransactions/{batchId}")
    Call<BatchTransactionItem> retrieveBatchTransaction(@Path(value = "version", encoded = true) String version, @Path("batchId") String batchId,@HeaderMap Map<String, String> headers);

    /**
     * Batch Transaction Rejection
     *
     * @return the call
     */
    @GET("{version}/batchtransactions/{id}/rejections")
    Call<BatchTransactionRejection> retrieveBatchRejections(@Path("id") String id,@Path(value = "version", encoded = true) String version, @HeaderMap Map<String, String> headers);

    /**
     * Batch Transaction Completion
     *
     * @return the call
     */
    @GET("{version}/batchtransactions/{id}/completions")
    Call<BatchTransactionCompletion> retrieveBatchCompletions(@Path("id") String id,@Path(value = "version", encoded = true) String version,  @HeaderMap Map<String, String> headers);
}

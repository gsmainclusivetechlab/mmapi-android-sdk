package com.gsmaSdk.gsma.network.retrofit;


import com.gsmaSdk.gsma.models.Balance;
import com.gsmaSdk.gsma.models.Refund;
import com.gsmaSdk.gsma.models.RequestStateObject;
import com.gsmaSdk.gsma.models.Reversal;
import com.gsmaSdk.gsma.models.Token;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.TransactionObject;

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
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface for Retrofit methods
 */
@SuppressWarnings("SpellCheckingInspection")
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface APIService {


    @POST("{version}/transactions/type/adjustment")
    Call<Refund> refund(@Path(value = "version", encoded = true) String version, @Body RequestBody transaction, @HeaderMap Map<String, String> headers);


    @POST("{version}/transactions/{referenceId}/reversals")
    Call<Reversal> reversal(@Path(value = "version", encoded = true) String version, @Path("referenceId") String referenceId, @Body RequestBody transaction, @HeaderMap Map<String, String> headers);


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
    Call<RequestStateObject> merchantPay(@Path("transactionType") String type, @Path(value = "version", encoded = true) String version, @Body RequestBody transaction, @HeaderMap Map<String, String> headers);

    /**
     * View Transaction
     *
     * @return the call
     */
    @GET("{version}/transactions/{id}")
    Call<TransactionObject> viewTransaction(@Path(value = "version", encoded = true) String version, @Path("id") String id, @HeaderMap Map<String, String> headers);

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
    Call<MissingResponse> retrieveMissingResponse(@Path("correlationId") String correlationId, @Path(value = "version", encoded = true) String version, @HeaderMap Map<String, String> headers);
}

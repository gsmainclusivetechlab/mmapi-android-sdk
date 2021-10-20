package com.gsmaSdk.gsma.network.retrofit;


import com.gsmaSdk.gsma.models.Balance;
import com.gsmaSdk.gsma.models.RequestStateObject;
import com.gsmaSdk.gsma.models.Reversal;
import com.gsmaSdk.gsma.models.Token;
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

/**
 * Interface for Retrofit methods
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface APIService {




    /**
     * merchant pay reversal
     * @return the call
     */

    @POST("{version}/transactions/{referenceId}/reversals")
    Call<Reversal> reversal(@Path(value = "version", encoded = true) String version,@Path("referenceId") String referenceId, @Body RequestBody transaction, @HeaderMap Map<String, String> headers);


    /**
     * Generate Access Token call.
     *
     * @param key encoded key
     * @return the call
     */
    @FormUrlEncoded
    @POST(APIConstants.URL_VER_TOKEN+APIConstants.ACCESS_TOKEN)
    Call<Token> createToken(@Header(APIConstants.AUTHORIZATION) String key,@Field("grant_type") String clientCredentials);

    /**
     * Retrieve Balance.
     *
     * @return the call
     */
    @GET("{version}/accounts/accountid/{id}/balance")
    Call<Balance> retrieveBalance(@Path(value = "version", encoded = true) String version,@Path("id") String id,@HeaderMap Map<String, String> headers);

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
    Call<RequestStateObject> viewRequestState(@Path(value = "version", encoded = true) String version,@Path("id") String id,@HeaderMap Map<String, String> headers);
}

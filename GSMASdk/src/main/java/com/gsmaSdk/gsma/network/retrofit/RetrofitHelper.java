package com.gsmaSdk.gsma.network.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gsmaSdk.gsma.manager.PreferenceManager;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionCompletion;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionRejection;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.network.deserializers.BatchCompletionsDeserializer;
import com.gsmaSdk.gsma.network.deserializers.BatchRejectionsDeserializer;
import com.gsmaSdk.gsma.network.deserializers.MissingCodeDeserializer;
import com.gsmaSdk.gsma.network.deserializers.MissingResponseDeserializer;
import com.gsmaSdk.gsma.network.deserializers.TransactionResponseDeserializer;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The Retrofit helper class .
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class RetrofitHelper {
    private static Retrofit retrofit;
    private static APIService helper;

    /**
     * Gets api helper.
     *
     * @return the api helper
     */
    @NonNull
    public static APIService getApiHelper() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = getOkHttpClient();
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIConstants.BASE_URL)
                    .addConverterFactory(buildGsonConverter())
                    .client(okHttpClient)
                    .build();
        }

        if (helper == null) {
            helper = retrofit.create(APIService.class);
        }

        return helper;
    }

    private static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // Adding custom deserializers
        gsonBuilder.registerTypeAdapter(Transaction.class, new TransactionResponseDeserializer());
        gsonBuilder.registerTypeAdapter(AuthorisationCode.class, new MissingCodeDeserializer());
        gsonBuilder.registerTypeAdapter(BatchTransactionRejection.class, new BatchRejectionsDeserializer());
        gsonBuilder.registerTypeAdapter(BatchTransactionCompletion.class, new BatchCompletionsDeserializer());
        gsonBuilder.registerTypeAdapter(MissingResponse.class, new MissingResponseDeserializer());
        Gson myGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        return GsonConverterFactory.create(myGson);
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);

        // add application interceptor to httpClientBuilder
        httpClientBuilder.addInterceptor(chain -> {

            Response initialResponse=chain.proceed(chain.request());
            //checking accessing token is expired or not
            if(initialResponse.code()==401&&PaymentConfiguration.getAuthToken()!=null){
                String key=APIConstants.AUTH_TOKEN_PREFIX+PaymentConfiguration.getBase64Value();
                initialResponse.close();
                //calling create token method using asynchronous request
                retrofit2.Response<Token> callToken= Objects.requireNonNull(RetrofitHelper.getApiHelper()).
                        createToken(key,APIConstants.CLIENT_CREDENTIALS).execute();

                if(PreferenceManager.getInstance()!=null){
                    PreferenceManager.getInstance().retrieveToken();
                }
                Objects.requireNonNull(PreferenceManager.getInstance()).saveToken(Objects.requireNonNull(callToken.body()).getAccessToken());
                Request newRequest=chain.request().newBuilder()
                        .header(APIConstants.AUTHORIZATION,callToken.body().getAccessToken())
                        .build();
                return chain.proceed(newRequest);

//
            }
            return initialResponse;
        });

        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return httpClientBuilder.build();
    }
}
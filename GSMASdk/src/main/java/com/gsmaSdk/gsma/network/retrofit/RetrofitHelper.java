package com.gsmaSdk.gsma.network.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gsmaSdk.gsma.manager.PreferenceManager;
import com.gsmaSdk.gsma.models.Token;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import okhttp3.Interceptor;
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
    @Nullable
    public static APIService getApiHelper() {
        /**
         * Lazy loading
         */
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
        Gson myGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        return GsonConverterFactory.create(myGson);
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);

        // add application interceptor to httpClientBuilder
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {

                Response initialResponse=chain.proceed(chain.request());
                //checking accessing token is expired or not
                if(initialResponse.code()==401&&PaymentConfiguration.getAuthToken()!=null){
                    String key=APIConstants.AUTH_TOKEN_PREFIX+PaymentConfiguration.getBase64Value();
                    initialResponse.close();
                    //calling create token method Aysnchronously
                    retrofit2.Response<Token> callToken= RetrofitHelper.getApiHelper().
                            createToken(key,APIConstants.CLIENT_CREDENTIALS).execute();

                    if(PreferenceManager.getInstance()!=null){
                        PreferenceManager.getInstance().retrieveToken();
                    }
                    PreferenceManager.getInstance().saveToken(callToken.body().getAccessToken());
                    Request newRequest=chain.request().newBuilder()
                            .header(APIConstants.AUTHORIZATION,callToken.body().getAccessToken())
                            .build();
                    return chain.proceed(newRequest);

//
                }
                return initialResponse;
            }
        });

        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return httpClientBuilder.build();
    }
}
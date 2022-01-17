package com.gsmaSdk.gsma.merchantpay;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.util.Log;

import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.network.retrofit.APIService;
import com.gsmaSdk.gsma.network.retrofit.PaymentConfiguration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MerchantPaymentMockTest {




    @Before
    public void

    @Test
    public void createTestSynchronusCallBack() throws IOException {

        MockWebServer mockWebServer=new MockWebServer();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())

                //TODO Add your Retrofit parameters here
                .build();


        mockWebServer.enqueue(new MockResponse().setBody("{\n" +
                "    \"accountStatus\": \"available\",\n" +
                "    \"currentBalance\": \"15.00\",\n" +
                "    \"availableBalance\": \"15.00\",\n" +
                "    \"reservedBalance\": \"15.00\",\n" +
                "    \"unclearedBalance\": \"15.00\",\n" +
                "    \"currency\": \"AED\"\n" +
                "}"));


        APIService service = retrofit.create(APIService.class);

        HashMap<String,String> params=new HashMap<>();
        Call<Balance> call = service.retrieveBalance("simulator/v1.2/passthrough/mm","accountno@1",params);

        Balance balance=call.execute().body();


        //Finish web server
        mockWebServer.shutdown();

    }


}

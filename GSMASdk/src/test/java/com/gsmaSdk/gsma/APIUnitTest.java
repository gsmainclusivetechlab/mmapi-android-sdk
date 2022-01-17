package com.gsmaSdk.gsma;

import static org.junit.Assert.assertNotNull;

import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.network.retrofit.APIService;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUnitTest {
    Retrofit retrofit;

    MockWebServer mockWebServer;
    APIService apiService;
    public static String URL_VERSION = "simulator/v1.2/passthrough/mm";

    @Before
    public void setUp() {
        mockWebServer = new MockWebServer();
        retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())

                //TODO Add your Retrofit parameters here
                .build();
        apiService = retrofit.create(APIService.class);

    }


    @Test
    public void createTokenApiSuccess() throws IOException {
        String actualToken = FileReader.readFromFile("AccessToken.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualToken));

        Call<Token> tokenCall = apiService.
                createToken("Basic eyJraWQiOiJcL0V4YXNlMmpqdkVtcUtLNTdmNEwyMkUyUUx2MDhndkFqTGlZVHl3bFhzUT0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI1OXZ0aG1xM2Y2aTE1djZqbWNqc2tma21oIiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJvYXV0aFwvcmVhZCIsImF1dGhfdGltZSI6MTY0MTM4NTQzMiwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLmV1LXdlc3QtMi5hbWF6b25hd3MuY29tXC9ldS13ZXN0LTJfTWJqdUR6cUJjIiwiZXhwIjoxNjQxMzg5MDMyLCJpYXQiOjE2NDEzODU0MzIsInZlcnNpb24iOjIsImp0aSI6Ijk5YjE5NDQ1LTE5ZjEtNGQ4NS04MTRlLWJlNmVhMmMyZWU3MyIsImNsaWVudF9pZCI6IjU5dnRobXEzZjZpMTV2NmptY2pza2ZrbWgifQ.LtqLOZroxnE6v62BMEJjqB9Rs6CaJzfOLq-fdfCyZJK-x2aj52Sk-8bY2srF11ODNLNMpqxNtfcXMikHBJ_ebVsFSktTyMpr-hfkmzubpIcRG_GnFmF4Ur5RpAPUeMj1Wx4gJNN6Kj1rfPCGvMdjnjPgt_gYNUjyCmWNHOoa2JnfJHUbFMkUWEdrfO2EEcN_a748jg6wKLUeQB-yxm0b0K6KGHAw1Vm1qJdA4Kj_wckf3eLObxGNiMcxv-aYLdN9pIXynrI00aYVRaimd9sthLLy887JNpfeZar6-SJ0_y_G7Pg7ndksljuyC6XApaubKT7yMtTCt1txhcpwZwkEKg", "client_credentials");

        Token expectedToken = tokenCall.execute().body();

        assertNotNull(expectedToken);


    }

}

package com.gsmaSdk.gsma;

import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.network.retrofit.APIService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class APIUnitTest {
    Retrofit retrofit;

    MockWebServer mockWebServer;
    APIService apiService;
    private static String URL_VERSION = "simulator/v1.2/passthrough/mm";

    private static final String X_CORRELATION_ID = "X-CorrelationID";

    private static HashMap<String, String> headers;

    @Before
    public void setUp() {
        mockWebServer = new MockWebServer();
        headers = new HashMap<>();

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
        assertNotNull(expectedToken.getAccessToken());


    }

    @Test
    public void checkServiceAvailabilityApiSuccess() throws IOException {

        String actualServiceAvailability = FileReader.readFromFile("ServiceAvailability.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualServiceAvailability));

        headers.put(X_CORRELATION_ID, generateUUID());

        Call<ServiceAvailability> serviceAvailabilityCall = apiService.checkServiceAvailability(URL_VERSION, headers);

        ServiceAvailability serviceAvailability = serviceAvailabilityCall.execute().body();

        assertNotNull(serviceAvailability);

        assertNotNull(serviceAvailability.getServiceStatus());

    }

    public String generateUUID() {
        return UUID.randomUUID().toString();
    }


}

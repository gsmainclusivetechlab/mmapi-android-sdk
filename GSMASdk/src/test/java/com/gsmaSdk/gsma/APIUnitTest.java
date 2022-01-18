package com.gsmaSdk.gsma;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.Link;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodes;
import com.gsmaSdk.gsma.models.bills.BillPayments;
import com.gsmaSdk.gsma.models.bills.Bills;
import com.gsmaSdk.gsma.models.common.GetLink;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.models.transaction.batchcompletion.BatchCompletions;
import com.gsmaSdk.gsma.models.transaction.batchrejection.BatchRejections;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;
import com.gsmaSdk.gsma.network.deserializers.BatchCompletionsDeserializer;
import com.gsmaSdk.gsma.network.deserializers.BatchRejectionsDeserializer;
import com.gsmaSdk.gsma.network.deserializers.BillPaymentsDeserializer;
import com.gsmaSdk.gsma.network.deserializers.BillResponseDeserializer;
import com.gsmaSdk.gsma.network.deserializers.MissingCodeDeserializer;
import com.gsmaSdk.gsma.network.deserializers.MissingResponseDeserializer;
import com.gsmaSdk.gsma.network.deserializers.TransactionResponseDeserializer;
import com.gsmaSdk.gsma.network.retrofit.APIService;
import com.gsmaSdk.gsma.utils.Utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class APIUnitTest {

    Retrofit retrofit;
    MockWebServer mockWebServer;
    APIService apiService;

    private static String URL_VERSION = "simulator/v1.2/passthrough/mm";

    private static final String X_CORRELATION_ID = "X-CorrelationID";
    private static HashMap<String, String> headers;

    private Reversal reversalObject;
    private final MediaType mediaType = MediaType.parse("application/json");


    @Before
    public void setUp() {
        mockWebServer = new MockWebServer();
        headers = new HashMap<>();

        retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(buildGsonConverter())

                //TODO Add your Retrofit parameters here
                .build();
        apiService = retrofit.create(APIService.class);


    }

    /*****************************Common APIs************************************/

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

    @Test
    public void initiatePaymentApiSuccess() throws IOException {
        String actualRequestState = FileReader.readFromFile("RequestState.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualRequestState));
        headers.put(X_CORRELATION_ID, generateUUID());
        Call<RequestStateObject> requestStateObjectCall = apiService.viewRequestState(URL_VERSION, "b0b17e14-c937-4363-a131-f0d83c054f96", headers);

        RequestStateObject requestStateObject = requestStateObjectCall.execute().body();

        assertNotNull(requestStateObject);
        assertNotNull(requestStateObject.getStatus());
        assertNotNull(requestStateObject.getNotificationMethod());
        assertNotNull(requestStateObject.getObjectReference());

    }

    @Test
    public void reversalApiSuccess() throws IOException {
        String actualRequestState = FileReader.readFromFile("RequestState.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualRequestState));
        headers.put(X_CORRELATION_ID, generateUUID());
        Call<RequestStateObject> requestStateObjectCall = apiService.reversal(URL_VERSION, "REF-1633580365289", getReversalBody(), headers);

        RequestStateObject requestStateObject = requestStateObjectCall.execute().body();

        assertNotNull(requestStateObject);
        assertNotNull(requestStateObject.getStatus());
        assertNotNull(requestStateObject.getNotificationMethod());
        assertNotNull(requestStateObject.getObjectReference());

    }


    @Test
    public void balanceApiSuccess() throws IOException {
        String actualBalance = FileReader.readFromFile("Balance.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualBalance));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<Balance> balanceCall = apiService.retrieveBalance(URL_VERSION, getAccountIdentifier(), headers);

        Balance balance = balanceCall.execute().body();

        assertNotNull(balance);

    }


    @Test
    public void viewTransactionApiSuccess() throws IOException {
        String actualTransaction = FileReader.readFromFile("Transaction.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualTransaction));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<Transaction> transactionCall = apiService.viewTransaction(URL_VERSION, "1684", headers);
        Transaction transaction = transactionCall.execute().body();

        assertNotNull(transaction);
        assertNotNull(transaction.getTransactionReference());
        assertNotNull(transaction.getType());
        assertNotNull(transaction.getTransactionStatus());
        assertNotNull(transaction.getAmount());
        assertNotNull(transaction.getCurrency());

    }


    @Test
    public void viewRequestStateApiSuccess() throws IOException {
        String actualRequestState = FileReader.readFromFile("RequestState.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualRequestState));
        headers.put(X_CORRELATION_ID, generateUUID());
        Call<RequestStateObject> requestStateObjectCall = apiService.viewRequestState(URL_VERSION,"b0b17e14-c937-4363-a131-f0d83c054f96", headers);

        RequestStateObject requestStateObject = requestStateObjectCall.execute().body();

        assertNotNull(requestStateObject);
        assertNotNull(requestStateObject.getStatus());
        assertNotNull(requestStateObject.getNotificationMethod());
        assertNotNull(requestStateObject.getObjectReference());
    }


    @Test
    public void retrieveTransactionApiSuccess() throws IOException {
        String actualTransactions = FileReader.readFromFile("Transactions.json");

        TransactionFilter transactionFilter=new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        HashMap<String, String> params = Utils.getHashMapFromObject(transactionFilter);


        mockWebServer.enqueue(new MockResponse().setBody(actualTransactions));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<Transactions> transactionsCall = apiService.retrieveTransaction(URL_VERSION, getAccountIdentifier(), headers,params);
        Transactions transactions = transactionsCall.execute().body();

        assertNotNull(transactions);

    }

   @Test
   public void  retrieveMissingLinkApiSuccess() throws IOException {

       String actualLink = FileReader.readFromFile("MissingLink.json");

       mockWebServer.enqueue(new MockResponse().setBody(actualLink));
       headers.put(X_CORRELATION_ID, generateUUID());

       Call<GetLink> linkCall = apiService.retrieveMissingLink("b0b17e14-c937-4363-a131-f0d83c054f96",URL_VERSION, headers);

       GetLink link=linkCall.execute().body();

       assertNotNull(link);
       assertNotNull(link.getLink());

    }

    @Test
    public void getMissingResponsesApiSuccess() throws IOException {
        String actualMissingResponse = FileReader.readFromFile("MissingTransaction.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualMissingResponse));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<MissingResponse> missingResponseCall = apiService.getMissingResponses("/transactions/REF-1636956879897",URL_VERSION, headers);

        MissingResponse missingResponse=missingResponseCall.execute().body();

        assertNotNull(missingResponse);
        assertNotNull(missingResponse.getJsonObject());

    }




    private String getAccountIdentifier() {
        return "accountid@2999";

    }


    private RequestBody getReversalBody() {
        reversalObject = new Reversal();
        reversalObject.setType("reversal");
        return RequestBody.create(new Gson().toJson(reversalObject), mediaType);

    }


    public String generateUUID() {
        return UUID.randomUUID().toString();
    }


    private static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // Adding custom deserializers
        gsonBuilder.registerTypeAdapter(Transactions.class, new TransactionResponseDeserializer());
        gsonBuilder.registerTypeAdapter(AuthorisationCodes.class, new MissingCodeDeserializer());
        gsonBuilder.registerTypeAdapter(BatchRejections.class, new BatchRejectionsDeserializer());
        gsonBuilder.registerTypeAdapter(BatchCompletions.class, new BatchCompletionsDeserializer());
        gsonBuilder.registerTypeAdapter(MissingResponse.class, new MissingResponseDeserializer());
        gsonBuilder.registerTypeAdapter(Bills.class, new BillResponseDeserializer());
        gsonBuilder.registerTypeAdapter(BillPayments.class, new BillPaymentsDeserializer());
        Gson myGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        return GsonConverterFactory.create(myGson);
    }


    public static HashMap<String, String> getHashMapFromObject(Object object) {
        HashMap<String, String> params = new HashMap<>();

        if (object instanceof TransactionFilter) {
            TransactionFilter transactionFilter = (TransactionFilter) object;
            params.put("limit", String.valueOf(transactionFilter.getLimit()));
            params.put("offset", String.valueOf(transactionFilter.getOffset()));
            if (transactionFilter.getFromDateTime() != null) {
                params.put("fromDateTime", transactionFilter.getFromDateTime());
            }
            if (transactionFilter.getToDateTime() != null) {
                params.put("toDateTime", transactionFilter.getToDateTime());
            }
            if (transactionFilter.getTransactionStatus() != null) {
                params.put("transactionStatus", transactionFilter.getTransactionStatus());
            }
            if (transactionFilter.getTransactionType() != null) {
                params.put("transactionType", transactionFilter.getTransactionType());
            }
        }

        return params;
    }


}

package com.gsmaSdk.gsma;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gsmaSdk.gsma.models.account.Account;
import com.gsmaSdk.gsma.models.account.AccountHolderName;
import com.gsmaSdk.gsma.models.account.AccountIdentifier;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identity;
import com.gsmaSdk.gsma.models.account.Link;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodes;
import com.gsmaSdk.gsma.models.bills.BillPayments;
import com.gsmaSdk.gsma.models.bills.Bills;
import com.gsmaSdk.gsma.models.common.Address;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.Fees;
import com.gsmaSdk.gsma.models.common.GetLink;
import com.gsmaSdk.gsma.models.common.IdDocument;
import com.gsmaSdk.gsma.models.common.KYCInformation;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.RequestingOrganisation;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.common.SubjectName;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.models.transaction.PatchData;
import com.gsmaSdk.gsma.models.transaction.batchcompletion.BatchCompletions;
import com.gsmaSdk.gsma.models.transaction.batchrejection.BatchRejections;
import com.gsmaSdk.gsma.models.transaction.batchtransaction.BatchTransaction;
import com.gsmaSdk.gsma.models.transaction.quotation.Quotation;
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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class APIUnitTest {

    Retrofit retrofit;
    MockWebServer mockWebServer;
    APIService apiService;

    private static String URL_VERSION = "simulator/v1.2/passthrough/mm";

    private static final String X_CORRELATION_ID = "X-CorrelationID";
    private static HashMap<String, String> headers;


    Reversal reversalObject;


    private AuthorisationCode authorisationCodeRequest;
    private Transaction transactionRequest;
    private Quotation quotationRequest;
    private Account accountRequest;
    private ArrayList<PatchData> patchDataArrayList;

    private final MediaType mediaType = MediaType.parse("application/json");

    private Link accountLinkingObject;


    BatchTransaction bulkTransactionObject;

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
        Call<RequestStateObject> requestStateObjectCall = apiService.viewRequestState(URL_VERSION, "b0b17e14-c937-4363-a131-f0d83c054f96", headers);

        RequestStateObject requestStateObject = requestStateObjectCall.execute().body();

        assertNotNull(requestStateObject);
        assertNotNull(requestStateObject.getStatus());
        assertNotNull(requestStateObject.getNotificationMethod());
        assertNotNull(requestStateObject.getObjectReference());
    }


    @Test
    public void retrieveTransactionApiSuccess() throws IOException {
        String actualTransactions = FileReader.readFromFile("Transactions.json");

        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        HashMap<String, String> params = getHashMapFromObject(transactionFilter);


        mockWebServer.enqueue(new MockResponse().setBody(actualTransactions));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<Transactions> transactionsCall = apiService.retrieveTransaction(URL_VERSION, getAccountIdentifier(), headers, params);
        Transactions transactions = transactionsCall.execute().body();

        assertNotNull(transactions);

    }

    @Test
    public void retrieveMissingLinkApiSuccess() throws IOException {

        String actualLink = FileReader.readFromFile("MissingLink.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualLink));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<GetLink> linkCall = apiService.retrieveMissingLink("b0b17e14-c937-4363-a131-f0d83c054f96", URL_VERSION, headers);

        GetLink link = linkCall.execute().body();

        assertNotNull(link);
        assertNotNull(link.getLink());

    }

    @Test
    public void getMissingResponsesApiSuccess() throws IOException {
        String actualMissingResponse = FileReader.readFromFile("MissingTransaction.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualMissingResponse));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<MissingResponse> missingResponseCall = apiService.getMissingResponses("/transactions/REF-1636956879897", URL_VERSION, headers);

        MissingResponse missingResponse = missingResponseCall.execute().body();

        assertNotNull(missingResponse);
        assertNotNull(missingResponse.getJsonObject());

    }


    /*************************************Merchant Payments********************************/

    @Test
    public void obtainAuthorisationCodeApiSuccess() throws IOException {

        String actualAuthCodeSuccess = FileReader.readFromFile("RequestState.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualAuthCodeSuccess));

        headers.put(X_CORRELATION_ID, generateUUID());

        Call<RequestStateObject> obtainAuthorisationCodeCall = apiService.obtainAuthorisationCode(getAccountIdentifier(), URL_VERSION, getAuthorisationCodeRequestBody(), headers);

        RequestStateObject requestStateObject = obtainAuthorisationCodeCall.execute().body();

        assertNotNull(requestStateObject);

        assertNotNull(requestStateObject.getStatus());

    }

    @Test
    public void viewAuthorisationCodeApiSuccess() throws IOException {

        String actualViewAuthCodeSuccess = FileReader.readFromFile("AuthorisationCode.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualViewAuthCodeSuccess));

        headers.put(X_CORRELATION_ID, generateUUID());

        Call<AuthorisationCode> viewAuthorisationCodeCall = apiService.viewAuthorizationCode(URL_VERSION, getAccountIdentifier(), "b0b17e14-c937-4363-a131-f0d83c054f96", headers);

        AuthorisationCode authorisationCode = viewAuthorisationCodeCall.execute().body();

        assertNotNull(authorisationCode);

        assertNotNull(authorisationCode.getAuthorisationCode());

    }

    @Test
    public void getMissingCodesApiSuccess() throws IOException {

        String actualGetMissingCodesSuccess = FileReader.readFromFile("AuthorisationCodes.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualGetMissingCodesSuccess));

        headers.put(X_CORRELATION_ID, generateUUID());

        Call<AuthorisationCodes> getMissingCodesSuccessCall = apiService.getMissingCodes("/transactions/REF-1636956879897", URL_VERSION, headers);

        AuthorisationCodes authorisationCodes = getMissingCodesSuccessCall.execute().body();

        assertNotNull(authorisationCodes);

        assertNotNull(authorisationCodes.getAuthCode());

    }

    @Test
    public void refundApiSuccess() throws IOException {

        String actualRefundSuccess = FileReader.readFromFile("RequestState.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualRefundSuccess));

        headers.put(X_CORRELATION_ID, generateUUID());

        Call<RequestStateObject> refundCall = apiService.refund(URL_VERSION, getTransactionRequestBody(), headers);

        RequestStateObject requestStateObject = refundCall.execute().body();

        assertNotNull(requestStateObject);

        assertNotNull(requestStateObject.getStatus());

    }

    /*****************************Disbursement************************************/


    @Test
    public void bulkTransactionApiSuccess() throws IOException {

        String actualRequestState = FileReader.readFromFile("RequestState.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualRequestState));
        headers.put(X_CORRELATION_ID, generateUUID());
        Call<RequestStateObject> requestStateObjectCall = apiService.bulkTransaction(URL_VERSION, getBulkTransactionBody(), headers);

        RequestStateObject requestStateObject = requestStateObjectCall.execute().body();

        assertNotNull(requestStateObject);
        assertNotNull(requestStateObject.getStatus());
        assertNotNull(requestStateObject.getNotificationMethod());
        assertNotNull(requestStateObject.getObjectReference());

    }


    @Test
    public void retrieveBatchRejectionsApiSuccess() throws IOException {
        String actualBatchRejections = FileReader.readFromFile("BatchRejections.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualBatchRejections));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<BatchRejections> batchRejectionsCall = apiService.retrieveBatchRejections("REF-1635765084301", URL_VERSION, headers);

        BatchRejections batchRejections = batchRejectionsCall.execute().body();
        assertNotNull(batchRejections.getBatchTransactionRejection().get(0).getRejectionReason());
        assertNotNull(batchRejections.getBatchTransactionRejection().get(0).getDebitParty());
        assertNotNull(batchRejections.getBatchTransactionRejection().get(0).getCreditParty());


    }

    @Test
    public void retrieveBatchCompletionsApiSuccess() throws IOException {
        String actualBatchCompletion = FileReader.readFromFile("BatchCompletions.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualBatchCompletion));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<BatchCompletions> batchCompletionsCall = apiService.retrieveBatchCompletions("REF-1635765084301", URL_VERSION, headers);

        BatchCompletions batchCompletions = batchCompletionsCall.execute().body();
        assertNotNull(batchCompletions.getBatchTransactionCompletion().get(0).getTransactionReference());
        assertNotNull(batchCompletions.getBatchTransactionCompletion().get(0).getCreditParty());
        assertNotNull(batchCompletions.getBatchTransactionCompletion().get(0).getDebitParty());
        assertNotNull(batchCompletions.getBatchTransactionCompletion().get(0).getCompletionDate());
        assertNotNull(batchCompletions.getBatchTransactionCompletion().get(0).getLink());

    }

    @Test
    public void updateBatchTransactionApiSuccess() throws IOException {

        String actualRequestState = FileReader.readFromFile("RequestState.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualRequestState));
        headers.put(X_CORRELATION_ID, generateUUID());
        Call<RequestStateObject> requestStateObjectCall = apiService.updateBatchTransaction(URL_VERSION, "REF-1635765084301", getPatchDataBody(), headers);

        RequestStateObject requestStateObject = requestStateObjectCall.execute().body();

        assertNotNull(requestStateObject);
        assertNotNull(requestStateObject.getStatus());
        assertNotNull(requestStateObject.getNotificationMethod());
        assertNotNull(requestStateObject.getObjectReference());


    }

    @Test
    public void retrieveBatchTransactionApiSuccess() throws IOException {
        String actualBatchTransaction = FileReader.readFromFile("BatchTransaction.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualBatchTransaction));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<BatchTransaction> batchTransactionCall = apiService.retrieveBatchTransaction(URL_VERSION, "REF-1635765084301", headers);

        BatchTransaction batchTransaction = batchTransactionCall.execute().body();

        assertNotNull(batchTransaction);
        assertNotNull(batchTransaction.getBatchId());
        assertNotNull(batchTransaction.getBatchStatus());
        assertNotNull(batchTransaction.getApprovalDate());
        assertNotNull(batchTransaction.getCompletionDate());


    }

    /*********************************International Transfers************************/

    @Test
    public void requestQuotationApiSuccess() throws IOException {

        String actualRequestQuotationSuccess = FileReader.readFromFile("RequestState.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualRequestQuotationSuccess));

        headers.put(X_CORRELATION_ID, generateUUID());

        Call<RequestStateObject> requestQuotationCall = apiService.requestQuotation(URL_VERSION, getQuotationRequestBody(), headers);

        RequestStateObject requestStateObject = requestQuotationCall.execute().body();

        assertNotNull(requestStateObject);

        assertNotNull(requestStateObject.getStatus());

    }

    @Test
    public void viewQuotationApiSuccess() throws IOException {
        String actualViewQuotation = FileReader.readFromFile("Transaction.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualViewQuotation));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<Transaction> viewQuotationCall = apiService.viewQuotation(URL_VERSION, "1684", headers);
        Transaction transaction = viewQuotationCall.execute().body();

        assertNotNull(transaction);
        assertNotNull(transaction.getTransactionReference());
        assertNotNull(transaction.getType());
        assertNotNull(transaction.getTransactionStatus());
        assertNotNull(transaction.getAmount());
        assertNotNull(transaction.getCurrency());

    }

    /***************************************P2P Transfer********************************/

    @Test
    public void viewAccountNameApiSuccess() throws IOException {

        String actualViewAccountNameSuccess = FileReader.readFromFile("AccountHolderName.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualViewAccountNameSuccess));

        headers.put(X_CORRELATION_ID, generateUUID());

        Call<AccountHolderName> viewAccountNameSuccessCall = apiService.viewAccountName(URL_VERSION, getAccountIdentifier(), headers);

        AccountHolderName accountHolderName = viewAccountNameSuccessCall.execute().body();

        assertNotNull(accountHolderName);

        assertNotNull(accountHolderName.getName());

    }


    /***************************************Account Linking********************************/

    @Test
    public void createAccountLinkingApiSuccess() throws IOException {
        String actualRequestState = FileReader.readFromFile("RequestState.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualRequestState));
        headers.put(X_CORRELATION_ID, generateUUID());
        Call<RequestStateObject> requestStateObjectCall = apiService.createAccountLinking(URL_VERSION, getAccountIdentifier(), getDebitMandateBody(), headers);

        RequestStateObject requestStateObject = requestStateObjectCall.execute().body();

        assertNotNull(requestStateObject);
        assertNotNull(requestStateObject.getStatus());
        assertNotNull(requestStateObject.getNotificationMethod());
        assertNotNull(requestStateObject.getObjectReference());

    }

    @Test
    public void viewAccountLinkApiSuccess() throws IOException {

        String actualLink = FileReader.readFromFile("Link.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualLink));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<Link> linkCall = apiService.viewAccountLink(URL_VERSION, getAccountIdentifier(), "1684", headers);

        Link link = linkCall.execute().body();

        assertNotNull(link);
        assertNotNull(link.getLinkReference());

    }


    /***************************************Agent Service********************************/

    @Test
    public void createAccountApiSuccess() throws IOException {
        String actualCreateAccount = FileReader.readFromFile("RequestState.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualCreateAccount));
        headers.put(X_CORRELATION_ID, generateUUID());
        Call<RequestStateObject> requestStateObjectCall = apiService.createAccount(URL_VERSION, getCreateAccountRequestBody(), headers);

        RequestStateObject requestStateObject = requestStateObjectCall.execute().body();

        assertNotNull(requestStateObject);
        assertNotNull(requestStateObject.getStatus());
        assertNotNull(requestStateObject.getNotificationMethod());
        assertNotNull(requestStateObject.getObjectReference());

    }

    @Test
    public void viewAccountApiSuccess() throws IOException {

        String actualViewAccount = FileReader.readFromFile("Account.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualViewAccount));
        headers.put(X_CORRELATION_ID, generateUUID());

        Call<Account> viewAccountCall = apiService.viewAccount(URL_VERSION, getAccountIdentifier(), headers);

        Account viewAccount = viewAccountCall.execute().body();

        assertNotNull(viewAccount);
        assertNotNull(viewAccount.getAccountStatus());

    }

    @Test
    public void updateAccountIdentityApiSuccess() throws IOException {
        String actualUpdateAccountIdentity = FileReader.readFromFile("RequestState.json");

        mockWebServer.enqueue(new MockResponse().setBody(actualUpdateAccountIdentity));
        headers.put(X_CORRELATION_ID, generateUUID());
        Call<RequestStateObject> requestStateObjectCall = apiService.createAccount(URL_VERSION, getUpdateAccountRequestBody(), headers);

        RequestStateObject requestStateObject = requestStateObjectCall.execute().body();

        assertNotNull(requestStateObject);
        assertNotNull(requestStateObject.getStatus());
        assertNotNull(requestStateObject.getNotificationMethod());
        assertNotNull(requestStateObject.getObjectReference());

    }


    /*****************************Util functions************************************/


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


    private RequestBody getBulkTransactionBody() {
        bulkTransactionObject = new BatchTransaction();

        ArrayList<Transaction> transactionItems = new ArrayList<>();
        Transaction transactionItem = new Transaction();
        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("2000");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("2999");
        creditPartyList.add(creditPartyItem);

        transactionItem.setAmount("200");
        transactionItem.setType("transfer");
        transactionItem.setCurrency("RWF");
        transactionItem.setCreditParty(creditPartyList);
        transactionItem.setDebitParty(debitPartyList);
        transactionItems.add(transactionItem);
        transactionItems.add(transactionItem);

        bulkTransactionObject.setBatchDescription("Testing a Batch transaction");
        bulkTransactionObject.setBatchTitle("Batch Test");
        bulkTransactionObject.setScheduledStartDate("2019-12-11T15:08:03.158Z");
        bulkTransactionObject.setTransactions(transactionItems);

        return RequestBody.create(new Gson().toJson(bulkTransactionObject), mediaType);

    }


    private RequestBody getPatchDataBody() {
        //create a batch object
        ArrayList<PatchData> patchDataArrayList;
        PatchData patchObject = new PatchData();
        patchObject.setOp("replace");
        patchObject.setPath("/batchStatus");
        patchObject.setValue("approved");
        patchDataArrayList = new ArrayList<>();
        patchDataArrayList.add(patchObject);

        return RequestBody.create(String.valueOf(new Gson().toJsonTree(patchDataArrayList).getAsJsonArray()), mediaType);


    }


    private RequestBody getDebitMandateBody() {
        accountLinkingObject = new Link();

        //set amount and currency

        accountLinkingObject.setMode("active");
        accountLinkingObject.setStatus("both");

        ArrayList<AccountIdentifier> sourceAccountIdentifiersList = new ArrayList<>();
        ArrayList<CustomDataItem> customDataList = new ArrayList<>();
        AccountIdentifier sourceAccountIdentifiersItem = new AccountIdentifier();
        CustomDataItem customDataItem = new CustomDataItem();
        RequestingOrganisation requestingOrganisationItem = new RequestingOrganisation();

        //Source Account Identifiers
        sourceAccountIdentifiersItem.setKey("accountid");
        sourceAccountIdentifiersItem.setValue("2999");
        sourceAccountIdentifiersList.add(sourceAccountIdentifiersItem);

        //Custom Data
        customDataItem.setKey("keytest");
        customDataItem.setValue("keyvalue");
        customDataList.add(customDataItem);

        //Requesting Organisation data
        requestingOrganisationItem.setRequestingOrganisationIdentifierType("organisationid");
        requestingOrganisationItem.setRequestingOrganisationIdentifier("12345");


        //add details to account linking object
        accountLinkingObject.setSourceAccountIdentifiers(sourceAccountIdentifiersList);
        accountLinkingObject.setCustomData(customDataList);
        accountLinkingObject.setRequestingOrganisation(requestingOrganisationItem);

        return RequestBody.create(new Gson().toJson(accountLinkingObject), mediaType);

    }

    private RequestBody getAuthorisationCodeRequestBody() {
        authorisationCodeRequest = new AuthorisationCode();
        authorisationCodeRequest.setAmount("200.00");
        authorisationCodeRequest.setRequestDate("2021-10-18T10:43:27.405Z");
        authorisationCodeRequest.setCurrency("RWF");
        authorisationCodeRequest.setCodeLifetime(1);
        return RequestBody.create(new Gson().toJson(authorisationCodeRequest), mediaType);

    }

    private RequestBody getTransactionRequestBody() {
        transactionRequest = new Transaction();
        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();

        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

        debitPartyItem.setKey("walletid");
        debitPartyItem.setValue("1");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("msisdn");
        creditPartyItem.setValue("+44012345678");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("200.00");
        transactionRequest.setCurrency("RWF");
        return RequestBody.create(new Gson().toJson(transactionRequest), mediaType);
    }

    private RequestBody getQuotationRequestBody() {
        quotationRequest = new Quotation();

        //create debit party and credit party for internal transfer quotation
        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

        //debit party
        debitPartyItem.setKey("walletid");
        debitPartyItem.setValue("1");
        debitPartyList.add(debitPartyItem);

        //credit party
        creditPartyItem.setKey("msisdn");
        creditPartyItem.setValue("+44012345678");
        creditPartyList.add(creditPartyItem);

        //add debit and credit party to transaction object
        quotationRequest.setDebitParty(debitPartyList);
        quotationRequest.setCreditParty(creditPartyList);

        //set amount,currency and request date into transaction request
        quotationRequest.setRequestAmount("75.30");
        quotationRequest.setRequestCurrency("RWF");
        quotationRequest.setRequestDate("2018-07-03T11:43:27.405Z");
        quotationRequest.setType("inttransfer");
        quotationRequest.setSubType("abc");
        quotationRequest.setChosenDeliveryMethod("agent");

        //sender kyc object
        KYCInformation senderKyc = new KYCInformation();
        senderKyc.setNationality("GB");
        senderKyc.setDateOfBirth("1970-07-03T11:43:27.405Z");
        senderKyc.setOccupation("manager");
        senderKyc.setEmployerName("MFX");
        senderKyc.setContactPhone("447125588999");
        senderKyc.setGender("m"); // m or f
        senderKyc.setEmailAddress("luke.skywalkeraaabbb@gmail.com");
        senderKyc.setBirthCountry("GB");

        // create object for documentation
        ArrayList<IdDocument> idDocumentItemList = new ArrayList<>();
        IdDocument idDocumentItem = new IdDocument();
        idDocumentItem.setIdType("nationalidcard");
        idDocumentItem.setIdNumber("1234567");
        idDocumentItem.setIssueDate("2018-07-03T11:43:27.405Z");
        idDocumentItem.setExpiryDate("2021-07-03T11:43:27.405Z");
        idDocumentItem.setIssuer("UKPA");
        idDocumentItem.setIssuerPlace("GB");
        idDocumentItem.setIssuerCountry("GB");
        idDocumentItem.setOtherIdDescription("test");

        idDocumentItemList.add(idDocumentItem);

        //add document details to kyc object
        senderKyc.setIdDocument(idDocumentItemList);

        //create object for postal address
        Address postalAddress = new Address();
        postalAddress.setCountry("GB");
        postalAddress.setAddressLine1("111 ABC Street");
        postalAddress.setCity("New York");
        postalAddress.setStateProvince("New York");
        postalAddress.setPostalCode("ABCD");

        //add postal address to kyc object
        senderKyc.setPostalAddress(postalAddress);

        //create subject model

        SubjectName subjectName = new SubjectName();
        subjectName.setTitle("Mr");
        subjectName.setFirstName("Luke");
        subjectName.setMiddleName("R");
        subjectName.setLastName("Skywalker");
        subjectName.setFullName("Luke R Skywalker");
        subjectName.setNativeName("ABC");

        //add  subject to kyc model

        senderKyc.setSubjectName(subjectName);

        //create array for custom data items
        ArrayList<CustomDataItem> customDataItemList = new ArrayList<>();

        // create a custom data item
        CustomDataItem customDataItem = new CustomDataItem();
        customDataItem.setKey("keytest");
        customDataItem.setValue("keyvalue");

        //add custom object into custom array
        customDataItemList.add(customDataItem);

        //add kyc object to request object
        quotationRequest.setSenderKyc(senderKyc);

        //add custom data object to request object
        quotationRequest.setCustomData(customDataItemList);

        quotationRequest.setSendingServiceProviderCountry("AD");
        quotationRequest.setOriginCountry("AD");
        quotationRequest.setReceivingCountry("AD");
        return RequestBody.create(new Gson().toJson(transactionRequest), mediaType);
    }

    private RequestBody getUpdateAccountRequestBody() {
        PatchData patchObject = new PatchData();
        patchObject.setOp("replace");
        patchObject.setPath("/kycVerificationStatus");
        patchObject.setValue("verified");
        patchDataArrayList = new ArrayList<>();
        patchDataArrayList.add(patchObject);
        return RequestBody.create(new Gson().toJson(patchDataArrayList), mediaType);
    }

    private RequestBody getCreateAccountRequestBody() {
        Random r = new Random();
        int keyValue = r.nextInt(45 - 28) + 28;
        accountRequest = new Account();

        ArrayList<AccountIdentifier> accountIdentifiers = new ArrayList<>();

        //account identifiers
        AccountIdentifier accountIdentifier = new AccountIdentifier();
        accountIdentifier.setKey("msisdn");
        accountIdentifier.setValue(String.valueOf(keyValue));
        accountIdentifiers.add(accountIdentifier);

        //add account identifier to account object
        accountRequest.setAccountIdentifiers(accountIdentifiers);

        //identity array
        ArrayList<Identity> identityArrayList = new ArrayList<>();
        //identity object
        Identity identity = new Identity();

        //kyc information
        KYCInformation kycInformation = new KYCInformation();

        kycInformation.setBirthCountry("AD");
        kycInformation.setContactPhone("+447777777777");
        kycInformation.setDateOfBirth("2000-11-20");
        kycInformation.setEmailAddress("xyz@xyz.com");
        kycInformation.setEmployerName("String");
        kycInformation.setGender("m");

        //create  id document object

        ArrayList<IdDocument> idDocumentArrayList = new ArrayList<>();
        IdDocument idDocument = new IdDocument();
        idDocument.setIdType("passport");
        idDocument.setIdNumber("111111");
        idDocument.setIssueDate("2018-11-20");
        idDocument.setExpiryDate("2018-11-20");
        idDocument.setIssuer("ABC");
        idDocument.setIssuerPlace("DEF");
        idDocument.setIssuerCountry("AD");

        idDocumentArrayList.add(idDocument);

        kycInformation.setIdDocument(idDocumentArrayList);


        kycInformation.setNationality("AD");
        kycInformation.setOccupation("Miner");

        //Postal Address
        Address address = new Address();
        address.setAddressLine1("37");
        address.setAddressLine2("ABC Drive");
        address.setAddressLine3("string");
        address.setCity("Berlin");
        address.setStateProvince("string");
        address.setPostalCode("AF1234");
        address.setCountry("AD");

        kycInformation.setPostalAddress(address);

        //subject information
        SubjectName subjectName = new SubjectName();
        subjectName.setTitle("Mr");
        subjectName.setFirstName("H");
        subjectName.setMiddleName("I");
        subjectName.setLastName("J");
        subjectName.setFullName("H I J ");
        subjectName.setNativeName("string");


        kycInformation.setSubjectName(subjectName);

        identity.setIdentityKyc(kycInformation);
        identity.setAccountRelationship("accountHolder");
        identity.setKycVerificationStatus("verified");
        identity.setKycVerificationEntity("ABC Agent");

        //kyc level
        identity.setKycLevel(1);

        //custom data for identity
        ArrayList<CustomDataItem> customDataItemArrayList = new ArrayList<>();
        CustomDataItem customDataItem = new CustomDataItem();
        customDataItem.setKey("test");
        customDataItem.setValue("custom");

        customDataItemArrayList.add(customDataItem);

        identity.setCustomData(customDataItemArrayList);

        //add identity to array
        identityArrayList.add(identity);

        //add indentity array into account object
        accountRequest.setIdentity(identityArrayList);


        //account type
        accountRequest.setAccountType("string");

        //custom data for account


        ArrayList<CustomDataItem> customDataItemAccountArrayList = new ArrayList<>();
        CustomDataItem customDataAccountItem = new CustomDataItem();
        customDataAccountItem.setKey("test");
        customDataAccountItem.setValue("custom1");

        customDataItemAccountArrayList.add(customDataAccountItem);

        accountRequest.setCustomData(customDataItemAccountArrayList);

        //Fees array

        ArrayList<Fees> feesArrayList = new ArrayList<>();

        Fees fees = new Fees();
        fees.setFeeType("string");
        fees.setFeeAmount("5.46");
        fees.setFeeCurrency("AED");

        feesArrayList.add(fees);

        accountRequest.setFees(feesArrayList);


        accountRequest.setRegisteringEntity("ABC Agent");
        accountRequest.setRequestDate("2021-02-17T15:41:45.194Z");
        return RequestBody.create(new Gson().toJson(accountRequest), mediaType);
    }
}
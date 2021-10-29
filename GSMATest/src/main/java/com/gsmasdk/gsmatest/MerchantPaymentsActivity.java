package com.gsmasdk.gsmatest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.controllers.SDKManager;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.Balance;
import com.gsmaSdk.gsma.models.RequestStateObject;
import com.gsmaSdk.gsma.models.ReversalObject;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeRequest;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.CreditPartyItem;
import com.gsmaSdk.gsma.models.transaction.DebitPartyItem;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.models.transaction.TransactionObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for testing APIs
 */
public class MerchantPaymentsActivity extends AppCompatActivity {

    private TextView txtResponse;
    private TransactionRequest transactionRequest;
    private AuthorisationCodeRequest authorisationCodeRequest;
    private String transactionRef = "";
    private String serverCorrelationId = "";
    private String correlationId = "";
    private ReversalObject reversalObject;
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_payments);
        setTitle("Merchant Payments");

        Button btnBalance = findViewById(R.id.btnCheckBalance);
        Button btnPayeeInitiated = findViewById(R.id.btnPayeeInitiated);
        Button btnTransaction = findViewById(R.id.btnViewTransaction);
        Button btnRequestState = findViewById(R.id.btnRequestState);
        Button btnRefund = findViewById(R.id.btnRefund);
        Button btnReversal = findViewById(R.id.btnReversal);
        Button btnRetrieveTransaction = findViewById(R.id.btnRetrieveTransaction);
        Button btnObtainAuthCode = findViewById(R.id.btnObtainAuthCode);
        Button btnRetrieveMissingTransactionResponse = findViewById(R.id.btnRetrieveMissingTransactionResponse);
        Button btnRetrieveMissingCodeResponse = findViewById(R.id.btnRetrieveMissingCodeResponse);

        txtResponse = findViewById(R.id.txtResponse);

        createTransactionObject();
        checkServiceAvailability();
        createCodeRequestObject();
        createPaymentReversalObject();

        /*
          API for checking Balance.
         */

        btnBalance.setOnClickListener(v -> SDKManager.getInstance().getBalance("1", new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MerchantPaymentsActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onBalanceSuccess(Balance balance, String correlationID) {
                txtResponse.setText(new Gson().toJson(balance));
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
            }
        }));

         /*
          API for Reversal.
         */
        btnReversal.setOnClickListener(v->SDKManager.getInstance().reversal("REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                txtResponse.setText(new Gson().toJson(requestStateObject));
               correlationId = correlationID;
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MerchantPaymentsActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        }));


        /*
          API for Merchant Pay
         */
        btnPayeeInitiated.setOnClickListener(v -> SDKManager.getInstance().merchantPay("merchantpay", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MerchantPaymentsActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                correlationId = correlationID;
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

        }));


        /*
          API to view Request state.
         */

        btnRefund.setOnClickListener(v->SDKManager.getInstance().refundMerchantPay(transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                Log.d(SUCCESS, "onRefundSuccess" + new Gson().toJson(requestStateObject));
                txtResponse.setText(new Gson().toJson(requestStateObject));
                correlationId = correlationID;
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                Log.d(FAILURE, "onRefundFailure: " + new Gson().toJson(gsmaError));
                txtResponse.setText(new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MerchantPaymentsActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        }));

        btnRequestState.setOnClickListener(v -> SDKManager.getInstance().viewRequestState(serverCorrelationId, new RequestStateInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MerchantPaymentsActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                txtResponse.setText(new Gson().toJson(requestStateObject));
                transactionRef = requestStateObject.getObjectReference();
                correlationId = correlationID;
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

        }));

        /*
          API to view Transaction details.
         */

        btnTransaction.setOnClickListener(v -> SDKManager.getInstance().viewTransaction(transactionRef, new TransactionInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MerchantPaymentsActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(TransactionObject transactionObject, String correlationID) {
                txtResponse.setText(new Gson().toJson(transactionObject));
                correlationId = correlationID;
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }

        }));

        /*
          API for Obtaining Authorisation code
         */
        btnObtainAuthCode.setOnClickListener(v -> SDKManager.getInstance().obtainAuthorisationCode("2000", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MerchantPaymentsActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                correlationId = correlationID;
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

        }));


        /*
          API for retrieving transaction
         */

        btnRetrieveTransaction.setOnClickListener(v -> SDKManager.getInstance().retrieveTransaction("2000", 0, 5, new RetrieveTransactionInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MerchantPaymentsActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction, String correlationID) {
                txtResponse.setText(new Gson().toJson(transaction));
                correlationId = correlationID;
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
            }
        }));

         /*
          API for retrieving missing transaction response
         */

        btnRetrieveMissingTransactionResponse.setOnClickListener(v -> SDKManager.getInstance().retrieveMissingTransaction(correlationId, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionObject transactionObject, String correlationId) {
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject, TransactionObject.class));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MerchantPaymentsActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

        }));

           /*
          API for retrieving missing code response
         */

        btnRetrieveMissingCodeResponse.setOnClickListener(v -> SDKManager.getInstance().retrieveMissingCode(correlationId, new AuthorisationCodeInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCode, String correlationId) {
                txtResponse.setText(new Gson().toJson(authorisationCode));
                Log.d(SUCCESS, "onAuthorisationCodeSuccess: " + new Gson().toJson(authorisationCode, AuthorisationCode.class));
            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onAuthorisationCodeFailure: " + new Gson().toJson(gsmaError));
            }


            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MerchantPaymentsActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

        }));

    }

    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability() {

        SDKManager.getInstance().checkServiceAvailability(new ServiceAvailabilityInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MerchantPaymentsActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability, String correlationID) {
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                correlationId = correlationID;
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }


    /**
     * Create Payment Reversal Object.
     */
    private void createPaymentReversalObject() {
        reversalObject = new ReversalObject();
        reversalObject.setReversal("reversal");
    }

    /**
     * Transaction Object for Merchant Pay.
     */
    private void createTransactionObject() {
        transactionRequest = new TransactionRequest();
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("2999");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("2999");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("200.00");
        transactionRequest.setCurrency("RWF");


    }

    /**
     * Code Request Object for Obtaining Authorisation code.
     */
    private void createCodeRequestObject() {
        authorisationCodeRequest = new AuthorisationCodeRequest();
        authorisationCodeRequest.setAmount("200.00");
        authorisationCodeRequest.setRequestDate("2021-10-18T10:43:27.405Z");
        authorisationCodeRequest.setCurrency("RWF");

//        String gson = new Gson().toJson(codeRequest, CodeRequest.class);
//        Log.d("TAG", "createCodeRequestObject: " + gson);

    }
}
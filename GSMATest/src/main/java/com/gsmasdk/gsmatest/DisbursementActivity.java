package com.gsmasdk.gsmatest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.controllers.SDKManager;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.RequestStateObject;
import com.gsmaSdk.gsma.models.ReversalObject;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.CreditPartyItem;
import com.gsmaSdk.gsma.models.transaction.DebitPartyItem;
import com.gsmaSdk.gsma.models.transaction.TransactionObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

import java.util.ArrayList;

public class DisbursementActivity extends AppCompatActivity {

    private TransactionRequest transactionRequest;
    private TextView txtResponse;

    private String transactionRef="";
    private String serverCorrelationId;
    private ReversalObject reversalObject;
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private String correlationId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);

        Button btnIndividualDisbursement = findViewById(R.id.btnIndividualDisbursement);
        Button btnViewTransactionDisbursement = findViewById(R.id.btnViewTransactionDisbursement);
        Button btnRequestStateDisbursement = findViewById(R.id.btnRequestStateDisbursement);



        txtResponse = findViewById(R.id.txtDisbursementResponse);
        //create object for transaction request
        createTransactionObject();

        btnIndividualDisbursement.setOnClickListener(v -> SDKManager.getInstance().disbursementPay("disbursement", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject,String correlationID) {
               correlationId=correlationID;
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }



            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
            }

        }));

        btnRequestStateDisbursement.setOnClickListener(v -> SDKManager.getInstance().viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject,String correlationID) {
                correlationId=correlationID;
                txtResponse.setText(new Gson().toJson(requestStateObject));
                transactionRef = requestStateObject.getObjectReference();
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

        }));
        //individual disbursement using polling method-view transaction

        btnViewTransactionDisbursement.setOnClickListener(v -> SDKManager.getInstance().viewTransaction(transactionRef, new TransactionInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(TransactionObject transactionObject,String correlationId) {
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }

        }));

    }

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

}
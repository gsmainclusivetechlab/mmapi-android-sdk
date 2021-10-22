package com.gsmasdk.gsmatest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.controllers.SDKManager;
import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.PaymentInitialiseInterface;
import com.gsmaSdk.gsma.interfaces.RefundInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.ReversalInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.PreferenceManager;
import com.gsmaSdk.gsma.models.Balance;
import com.gsmaSdk.gsma.models.Refund;
import com.gsmaSdk.gsma.models.RequestStateObject;
import com.gsmaSdk.gsma.models.Reversal;
import com.gsmaSdk.gsma.models.ReversalObject;
import com.gsmaSdk.gsma.models.Token;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.CreditPartyItem;
import com.gsmaSdk.gsma.models.transaction.DebitPartyItem;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.models.transaction.TransactionItem;
import com.gsmaSdk.gsma.models.transaction.TransactionObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.network.retrofit.PaymentConfiguration;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for testing APIs
 */
public class MainActivity extends AppCompatActivity {

    private TextView txtResponse;
    private TransactionRequest transactionRequest;
    private String transactionRef="";
    private String serverCorrelationId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnToken = findViewById(R.id.btnGenerateToken);
        Button btnBalance = findViewById(R.id.btnCheckBalance);
        Button btnPayeeInitiated = findViewById(R.id.btnPayeeInitiated);
        Button btnTransaction = findViewById(R.id.btnViewTransaction);
        Button btnRequestState = findViewById(R.id.btnRequestState);
        Button btnRefund=findViewById(R.id.btnRefund);
        Button btnReversal=findViewById(R.id.btnReversal);
        Button btnRetrieveTransaction = findViewById(R.id.btnRetrieveTransaction);

        txtResponse = findViewById(R.id.txtResponse);


        PaymentConfiguration.
                init("59vthmq3f6i15v6jmcjskfkmh",
                        "ef8tl4gihlpfd7r8jpc1t1nda33q5kcnn32cj375lq6mg2nv7rb"
                        ,AuthenticationType.STANDARD_LEVEL,"https://www.google.com/");


        /**
//         * Configure the payment system
//         * Initialise the payment system
//         * consumerkey,cosumersecret,level of authentication,callbackurl,XAPI key
//         */
//        PaymentConfiguration.
//                init("59vthmq3f6i15v6jmcjskfkmh",
//                        "ef8tl4gihlpfd7r8jpc1t1nda33q5kcnn32cj375lq6mg2nv7rb"
//                        ,AuthenticationType.STANDARD_LEVEL,"https://www.google.com/","243343adasdsad234343434");
//

//        /**
//         * Configure the payment system
//         * Initialise the payment system without authorization
//         *
//         */

         //   PaymentConfiguration.init("https://www.google.com/");


        //iniliase the preference object
        PreferenceManager.getInstance().init(this);

        GSMAApi.getInstance().init(this, new PaymentInitialiseInterface() {
            @Override
            public void onSuccess(Token token) {
                Log.d("TAG", "onSuccess: "+token);
            }

            @Override
            public void onFailure(GSMAError gsmaError) {
                Log.d("TAG", "onFailure: "+gsmaError);
            }
        });

        createTransactionObject();
        checkServiceAvailability();

        /**
         * API for checking Balance.
         */

        btnBalance.setOnClickListener(v -> SDKManager.getInstance().getBalance("1", new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MainActivity.this, errorObject.getErrorDescription(),Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onValidationError: "+new Gson().toJson(errorObject));
            }

            @Override
            public void onBalanceSuccess(Balance balance) {
                txtResponse.setText(new StringBuilder().append(balance.getCurrentBalance()).append(" ").append(balance.getAccountStatus()).toString());
                Log.d("TAG", "onBalanceSuccess: "+new Gson().toJson(balance));
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                txtResponse.setText(new StringBuilder().append(getString(R.string.error)).append(new Gson().toJson(gsmaError.getErrorBody())));
                Log.d("TAG", "onBalanceFailure: "+new Gson().toJson(gsmaError));
            }
        }));

        // payment revesal object

        ReversalObject reversalObject=new ReversalObject();
        reversalObject.setReversal("reversal");

        btnReversal.setOnClickListener(v-> SDKManager.getInstance().reversal("REF-1633580365289", reversalObject, new ReversalInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MainActivity.this, errorObject.getErrorDescription(),Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onValidationError: "+new Gson().toJson(errorObject));
            }

            @Override
            public void onReversalSuccess(Reversal reversal) {
                Log.d("TAG", "onReversalSuccess:"+reversal);
            }

            @Override
            public void onReversalFailure(GSMAError gsmaError) {
                Log.d("TAG", "onReversalFailure: "+gsmaError);
            }
        }));



//        /**
//         * API to generate token
//         */
//        btnToken.setOnClickListener(v -> SDKManager.getInstance().getToken("59vthmq3f6i15v6jmcjskfkmh", "ef8tl4gihlpfd7r8jpc1t1nda33q5kcnn32cj375lq6mg2nv7rb", new TokenInterface() {
//            @Override
//            public void onTokenSuccess(Token token) {
//                txtResponse.setText("Token :" + token.getAccessToken());
//                PaymentConfiguration.setAuthToken(MainActivity.this,token.getAccessToken());
//            }
//
//            @Override
//            public void onTokenFailure(GSMAError gsmaError) {
//
//            }
//
//        }));

        /**
         * API for Merchant Pay
         */
        btnPayeeInitiated.setOnClickListener(v -> SDKManager.getInstance().merchantPay("merchantpay", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MainActivity.this, errorObject.getErrorDescription(),Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onValidationError: "+new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                txtResponse.setText("Correlation ID :" + requestStateObject.getServerCorrelationId());
                serverCorrelationId = requestStateObject.getServerCorrelationId();
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                txtResponse.setText("Error: "+gsmaError.getErrorCode());
            }

        }));


        /**
         * API to view Request state.
         */

        btnRefund.setOnClickListener(v->SDKManager.getInstance().getRefundMerchantPay(transactionRequest, new RefundInterface() {
            @Override
            public void onRefundSuccess(Refund refund) {
                Log.d("TAG", "onRefundSuccess"+refund.getServerCorrelationId());
            }

            @Override
            public void onRefundFailure(GSMAError gsmaError) {
                txtResponse.setText(new StringBuilder().append(getString(R.string.error)).append(new Gson().toJson(gsmaError.getErrorBody())));
                Log.d("TAG", "onRequestStateFailure: "+new Gson().toJson(gsmaError));

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Log.d("TAG", "onValidationError: "+new Gson().toJson(errorObject));
            }
        }));
        btnRequestState.setOnClickListener(v -> SDKManager.getInstance().viewRequestState(serverCorrelationId, new RequestStateInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(
                        MainActivity.this, errorObject.getErrorDescription(),Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onValidationError: "+new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                txtResponse.setText(new StringBuilder().append(requestStateObject.getStatus()).append(" ").append(requestStateObject.getObjectReference()).toString());
                Log.d("TAG", "onRequestStateSuccess: "+new Gson().toJson(requestStateObject));
                transactionRef = requestStateObject.getObjectReference();
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                txtResponse.setText(new StringBuilder().append(getString(R.string.error)).append(new Gson().toJson(gsmaError.getErrorBody())));
                Log.d("TAG", "onRequestStateFailure: "+new Gson().toJson(gsmaError));
            }

        }));

        /**
         * API to view Transaction details.
         */

        btnTransaction.setOnClickListener(v -> SDKManager.getInstance().viewTransaction(transactionRef, new TransactionInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MainActivity.this, errorObject.getErrorDescription(),Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onValidationError: "+new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(TransactionObject transactionObject) {
                txtResponse.setText(new StringBuilder().append(transactionObject.getTransactionStatus()).append(" ").append(transactionObject.getAmount()).toString());
                Log.d("TAG", "onTransactionSuccess: "+new Gson().toJson(transactionObject));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                txtResponse.setText(new StringBuilder().append(getString(R.string.error)).append(new Gson().toJson(gsmaError.getErrorBody())));
                Log.d("TAG", "onTransactionFailure: "+new Gson().toJson(gsmaError));
            }

        }));


        /**
         * API for retrieving transaction
         */

        btnRetrieveTransaction.setOnClickListener(v -> SDKManager.getInstance().retrieveTransaction("2000",0,1, new RetrieveTransactionInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MainActivity.this, errorObject.getErrorDescription(),Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onValidationError: "+new Gson().toJson(errorObject));
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction) {
                txtResponse.setText(new Gson().toJson(transaction));
                Log.d("TAG", "onRetrieveTransactionSuccess: "+new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                txtResponse.setText(new StringBuilder().append(getString(R.string.error)).append(new Gson().toJson(gsmaError.getErrorBody())));
                Log.d("TAG", "onRetrieveTransactionFailure: "+new Gson().toJson(gsmaError));
            }
        }));

    }

    private void checkServiceAvailability() {
        /**
         * API for checking Service Availability.
         */

        SDKManager.getInstance().checkServiceAvailability( new ServiceAvailabilityInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(MainActivity.this, errorObject.getErrorDescription(),Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onValidationError: "+new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                txtResponse.setText(new StringBuilder().append("Service Availability: ").append(serviceAvailability.getServiceStatus()).toString());
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                txtResponse.setText(new StringBuilder().append(getString(R.string.error)).append(new Gson().toJson(gsmaError.getErrorBody())));
                Log.d("TAG", "onServiceAvailabilityFailure: "+new Gson().toJson(gsmaError));
            }
        });
    }



    /**
     *Transaction Object for Merchant Pay.
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

        String gson = new Gson().toJson(transactionRequest, TransactionRequest.class);
        Log.d("TAG", "createTransactionObject: " + gson);

    }
}
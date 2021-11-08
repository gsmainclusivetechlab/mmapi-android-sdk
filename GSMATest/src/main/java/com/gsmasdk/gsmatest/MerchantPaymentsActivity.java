package com.gsmasdk.gsmatest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.controllers.SDKManager;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.common.Balance;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
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
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for testing APIs
 */
@SuppressWarnings("ALL")
public class MerchantPaymentsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private TextView txtResponse;
    private TransactionRequest transactionRequest;
    private AuthorisationCodeRequest authorisationCodeRequest;
    private String transactionRef = "";
    private String serverCorrelationId = "";
    private String correlationId = "";
    private ReversalObject reversalObject;
    private ProgressDialog progressdialog;
    private String[] merchantPaymentsArray = {
            "Balance",
            "Payee Initiated",
            "Request State",
            "View Transaction",
            "Payment Refund",
            "Payment Reversal",
            "Retrieve Transaction",
            "Auth Code",
            "Missing Transaction",
            "Missing Code"
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_payments);
        setTitle("Merchant Payments");
        ListView listUseCases = findViewById(R.id.merchantPaymentsList);
        CustomUseCaseAdapter customListAdapter = new CustomUseCaseAdapter(MerchantPaymentsActivity.this, new ArrayList(Arrays.asList(merchantPaymentsArray)));
        listUseCases.setAdapter(customListAdapter);
        listUseCases.setOnItemClickListener(this);
        txtResponse = findViewById(R.id.txtResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());

        progressdialog = Utils.initProgress(MerchantPaymentsActivity.this);

        createTransactionObject();
        checkServiceAvailability();
        createCodeRequestObject();
        createPaymentReversalObject();

    }

    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability() {
        showLoading();
        SDKManager.getInstance().checkServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability, String correlationID) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                correlationId = correlationID;
                Utils.showToast(MerchantPaymentsActivity.this,"Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(MerchantPaymentsActivity.this,"Failure");
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

    public void showLoading() {
        progressdialog.show();
    }

    public void hideLoading() {
        progressdialog.dismiss();
    }

    /**
     * Code Request Object for Obtaining Authorisation code.
     */
    private void createCodeRequestObject() {
        authorisationCodeRequest = new AuthorisationCodeRequest();
        authorisationCodeRequest.setAmount("200.00");
        authorisationCodeRequest.setRequestDate("2021-10-18T10:43:27.405Z");
        authorisationCodeRequest.setCurrency("RWF");

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                //balance check
                balanceCheck();
                break;
            case 1:
                //Payee Initiated
                payeeInitiated();
                break;
            case 2:
                //Request State
                requestState();
                break;
            case 3:
                //View Transaction
                viewTransasction();
                break;
            case 4:
                //Payment Refund
                paymentRefund();
                break;
            case 5:
                //Payment reversal
                paymentReversal();
                break;
            case 6:
                //Retrieve Transaction
                retrieveTransaction();
                break;
            case 7:
                //Authorization Code
                obtainAuthorizationCode();
                break;
            case 8:
                //Missing Transaction
                getMissingTransaction();
                break;
            case 9:
                // Missing Code Response
                retriveMissingCodeResponse();
                break;
            default:
                break;
        }

    }

    /**
     * Checking Balance.
     */
    private void balanceCheck() {
        showLoading();
        SDKManager.getInstance().getBalance("1", new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onBalanceSuccess(Balance balance, String correlationID) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Success");
                txtResponse.setText(new Gson().toJson(balance).toString());
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    /**
     * Payee initiated
     */
    private void payeeInitiated() {
        showLoading();
        SDKManager.getInstance().merchantPay("merchantpay", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject).toString());
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                correlationId = correlationID;
                Utils.showToast(MerchantPaymentsActivity.this,"Success");
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(MerchantPaymentsActivity.this,"Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

        });
    }

    /**
     * Request State
     */
    private void requestState() {
        showLoading();
        SDKManager.getInstance().viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                transactionRef = requestStateObject.getObjectReference();
                correlationId = correlationID;
                Utils.showToast(MerchantPaymentsActivity.this,"Success");
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(MerchantPaymentsActivity.this,"Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

        });
    }

    /**
     * View Transaction
     */
    private void viewTransasction() {
        showLoading();
        SDKManager.getInstance().viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(TransactionObject transactionObject, String correlationID) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(transactionObject));
                correlationId = correlationID;
                Utils.showToast(MerchantPaymentsActivity.this,"Success");
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }

        });
    }

    /**
     * Payment Refund
     */
    private void paymentRefund() {
        showLoading();
        SDKManager.getInstance().refundMerchantPay(transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Success");
                Log.d(SUCCESS, "onRefundSuccess" + new Gson().toJson(requestStateObject));
                txtResponse.setText(new Gson().toJson(requestStateObject));
                correlationId = correlationID;
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Failure");
                Log.d(FAILURE, "onRefundFailure: " + new Gson().toJson(gsmaError));
                txtResponse.setText(new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });
    }

    /**
     * Payment Reversal
     */
    private void paymentReversal() {
        showLoading();
        SDKManager.getInstance().reversal("REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                correlationId = correlationID;
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                hideLoading();
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });
    }

    /**
     * Retrieve Transaction
     */
    private void retrieveTransaction() {
        showLoading();
        SDKManager.getInstance().retrieveTransaction("2000", 0, 5, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction, String correlationID) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Success");
                txtResponse.setText(new Gson().toJson(transaction));
                correlationId = correlationID;
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    /**
     * Authorization Code
     */
    private void obtainAuthorizationCode() {
        showLoading();
        SDKManager.getInstance().obtainAuthorisationCode("2000", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                correlationId = correlationID;
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

        });
    }

    /**
     * Missing Transaction
     */
    private void getMissingTransaction() {
        showLoading();
        SDKManager.getInstance().retrieveMissingTransaction(correlationId, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionObject transactionObject, String correlationId) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Success");
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject, TransactionObject.class));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(MerchantPaymentsActivity.this,"Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

        });
    }

    /**
     * Retrive Missing Code
     */
    private void retriveMissingCodeResponse() {
        showLoading();
        SDKManager.getInstance().retrieveMissingCode(correlationId, new AuthorisationCodeInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCode, String correlationId) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Success");
                txtResponse.setText(new Gson().toJson(authorisationCode));
                Log.d(SUCCESS, "onAuthorisationCodeSuccess: " + new Gson().toJson(authorisationCode, AuthorisationCode.class));
            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onAuthorisationCodeFailure: " + new Gson().toJson(gsmaError));
            }


            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

        });
    }
}
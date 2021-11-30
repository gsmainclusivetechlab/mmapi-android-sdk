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
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeInterface;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeItemInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.MissingResponse;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeItem;
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
    private String serverCorrelationId="";
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
            "Missing Code",
            "View Auth Code"
    };
    ArrayList<Identifier> identifierArrayList;

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
        createAccountIdentifier();

    }
    /*
     * Account identitifers for transaction
     *
     */
    private void createAccountIdentifier(){

        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2999");
        identifierArrayList.add(identifierAccount);

//        //msisdn
//        Identifier identifierMsisdn = new Identifier();
//        identifierMsisdn.setKey("msisdn");
//        identifierMsisdn.setValue("%2B123456789102345");
//        identifierArrayList.add(identifierMsisdn);
//
//        //wallet id
//        Identifier identifierWallet = new Identifier();
//        identifierWallet.setKey("walletid");
//        identifierWallet.setValue("3355544");
//        identifierArrayList.add(identifierWallet);

    }


    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability() {
        showLoading();
        SDKManager.merchantPayment.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
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
            case 10:
                //View an authorization Code
                viewAuthorizationCode();

                break;
            default:
                break;
        }

    }


    /**
     * View Authorization Code
     *
     */

    private void viewAuthorizationCode() {
        showLoading();
        SDKManager.merchantPayment.viewAuthorisationCode(identifierArrayList, transactionRef, new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCodeItem authorisationCodeItem) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(authorisationCodeItem));
                Log.d(SUCCESS, "onAuthorizationCodeItem: " + new Gson().toJson(authorisationCodeItem));
            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onAuthorizationCodeFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, ""+errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });
    }

    /**
     * Checking Balance.
     */
    private void balanceCheck() {
        showLoading();
        SDKManager.merchantPayment.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));

            }

            @Override
            public void onBalanceSuccess(Balance balance) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(balance).toString());
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    /**
     * Payee/payer  initiated payment
     */
    private void payeeInitiated() {
        showLoading();
        SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject).toString());
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    /**
     * Request State
     */
    private void requestState() {
        showLoading();
        SDKManager.merchantPayment.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                transactionRef = requestStateObject.getObjectReference();
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    /**
     * View Transaction-View the transaction Details
     */
    private void viewTransasction() {
        showLoading();
        SDKManager.merchantPayment.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(TransactionRequest transactionRequest) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(transactionRequest));
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionRequest));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
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
        SDKManager.merchantPayment.createRefundTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onRefundSuccess" + new Gson().toJson(requestStateObject));
                txtResponse.setText(new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRefundFailure: " + new Gson().toJson(gsmaError));
                txtResponse.setText(new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    /**
     * Payment Reversal
     */
    private void paymentReversal() {
        showLoading();
        SDKManager.merchantPayment.createReversal(NotificationMethod.POLLING,"","REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                hideLoading();
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    /**
     * Retrieve Transaction
     */
    private void retrieveTransaction() {
        showLoading();
        SDKManager.merchantPayment.viewAccountTransactions(identifierArrayList, 0, 2, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transaction));
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
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
        SDKManager.merchantPayment.createAuthorisationCode(identifierArrayList,NotificationMethod.POLLING,"", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });

    }

    /**
     * Missing Transaction
     */
    private void getMissingTransaction() {

        showLoading();

        SDKManager.merchantPayment.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(missingResponse));
                Log.d(SUCCESS, "onMissingTransactionSuccess: " + new Gson().toJson(missingResponse));
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });




    }

    /**
     * Retrieve Missing Code
     */
    private void retriveMissingCodeResponse() {
        showLoading();
        SDKManager.merchantPayment.viewAuthorisationCodeResponse(correlationId, new AuthorisationCodeInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCode) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(authorisationCode));
                Log.d(SUCCESS, "onAuthorisationCodeSuccess: " + new Gson().toJson(authorisationCode, AuthorisationCode.class));
            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onAuthorisationCodeFailure: " + new Gson().toJson(gsmaError));
            }


            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

        });
    }
}
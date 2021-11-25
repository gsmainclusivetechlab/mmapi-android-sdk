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
import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.common.Balance;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.CreditPartyItem;
import com.gsmaSdk.gsma.models.transaction.DebitPartyItem;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class RecurringPaymentsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";
    private ReversalObject reversalObject;

    private String transactionRef = "";
    private String serverCorrelationId;

    private TransactionRequest transactionRequest;
    ArrayList<Identifier> identifierArrayList;

    private final String[] recurringPaymentArray = {
            "Create Debit Mandate",
            "Request State",
            "Read a Debit Mandate",
            "Merchant Payment using Debit Mandate",
            "View Transaction",
            "Recurring Payment Refund",
            "Recurring Payment Reversal",
            "Service Provide Balance",
            "Retrieve Payments for a Service provider",
            "Missing Transaction",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_payments);
        setTitle("Recurring Payments");

        ListView listUseCases = findViewById(R.id.recurringPaymentList);

        CustomUseCaseAdapter customListAdapter = new CustomUseCaseAdapter(RecurringPaymentsActivity.this, new ArrayList(Arrays.asList(recurringPaymentArray)));
        listUseCases.setAdapter(customListAdapter);
        listUseCases.setOnItemClickListener(this);
        txtResponse = findViewById(R.id.txtRecurringResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());

        progressdialog = Utils.initProgress(RecurringPaymentsActivity.this);

        createAccountIdentifier();
        createPaymentReversalObject();
        createTransactionObject();

    }


    public void showLoading() {
        progressdialog.show();
    }

    public void hideLoading() {
        progressdialog.dismiss();
    }


    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                //Create Debit Mandate;

                break;

            case 1:
                //Request State;

                break;

            case 2:
                //Read a Debit Mandate

                break;

            case 3:
                //Merchant Payment using Debit Mandate

                break;
            case 4:
                //View Transaction

                break;

            case 5:
                //Recurring Payment Refund
                paymentRefund();
                break;

            case 6:
                //Recurring Payment Reversal
                paymentReversal();
                break;
            case 7:
                // Service Provide Balance
                balanceCheck();
                break;
            case 8:
                //Retrieve Payments for a Service provider
                retrieveTransaction();
                break;
            case 9:
                //Missing Transaction
                getMissingTransaction();

            default:
                break;
        }

    }

    /**
     * Create Payment Reversal Object.
     */
    private void createPaymentReversalObject() {
        reversalObject = new ReversalObject();
        reversalObject.setReversal("reversal");
    }

    private void createAccountIdentifier(){

        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2999");
        identifierArrayList.add(identifierAccount);

    }

    /**
     * Transaction Object.
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

        creditPartyItem.setKey("mandateReference");
        creditPartyItem.setValue("");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("200.00");
        transactionRequest.setCurrency("RWF");


    }

    /**
     * Payment Refund
     */
    private void paymentRefund() {
        showLoading();
        SDKManager.recurringPayment.createRefundTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onRefundSuccess" + new Gson().toJson(requestStateObject));
                txtResponse.setText(new Gson().toJson(requestStateObject));
                correlationId = correlationID;
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRefundFailure: " + new Gson().toJson(gsmaError));
                txtResponse.setText(new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });
    }

    /**
     * Payment Reversal
     */
    private void paymentReversal() {
        showLoading();
        SDKManager.recurringPayment.createReversal(NotificationMethod.POLLING, "", "REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                correlationId = correlationID;
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                hideLoading();
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });
    }

    /**
     * Checking Balance.
     */
    private void balanceCheck() {
        showLoading();
        SDKManager.recurringPayment.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));

            }

            @Override
            public void onBalanceSuccess(Balance balance, String correlationID) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(balance).toString());
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    /**
     * Retrieve Transaction
     */
    private void retrieveTransaction() {
        showLoading();
        SDKManager.recurringPayment.viewAccountTransactions(identifierArrayList, 0, 2, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction, String correlationID) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transaction));
                correlationId = correlationID;
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    /**
     * Missing Transaction
     */
    private void getMissingTransaction() {
        showLoading();
        SDKManager.recurringPayment.viewTransactionResponse(correlationId, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject, String correlationId) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject, TransactionRequest.class));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

        });
    }

}
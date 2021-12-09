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
import com.gsmaSdk.gsma.interfaces.DebitMandateInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.debitmandate.DebitMandate;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.account.PayeeItem;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.common.CreditPartyItem;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.DebitPartyItem;
import com.gsmaSdk.gsma.models.transaction.reversal.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.TransactionRequest;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
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

    private String debitMandateReference;


    private DebitMandate debitMandateRequest;

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

        //service availability check
        checkServiceAvailability();
        createAccountIdentifier();
        createDebitMandateObject();
        createPaymentReversalObject();

    }


    private void createAccountIdentifier() {
        identifierArrayList = new ArrayList<>();
        identifierArrayList.clear();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");

        identifierArrayList.add(identifierAccount);
    }

    //debit mandate object
    private void createDebitMandateObject() {
        debitMandateRequest = new DebitMandate();

        //payee object of debit mandate
        ArrayList<PayeeItem> payeeItemArrayList = new ArrayList<>();

        //payee object
        PayeeItem payeeItem = new PayeeItem();
        payeeItem.setKey("accountid");
        payeeItem.setValue("2999");
        payeeItemArrayList.add(payeeItem);

        //add items to debit mandate object

        debitMandateRequest.setPayee(payeeItemArrayList);
        debitMandateRequest.setRequestDate("2018-07-03T10:43:27.405Z");
        debitMandateRequest.setStartDate("2018-07-03T10:43:27.405Z");
        debitMandateRequest.setCurrency("GBP");
        debitMandateRequest.setAmountLimit("1000.00");
        debitMandateRequest.setEndDate("2028-07-03T10:43:27.405Z");
        debitMandateRequest.setNumberOfPayments("2");
        debitMandateRequest.setFrequencyType("sixmonths");


        //creating custom data array
        ArrayList<CustomDataItem> customDataItemArrayList = new ArrayList<>();

        CustomDataItem customDataItem = new CustomDataItem();
        customDataItem.setKey("keytest");
        customDataItem.setValue("keyvalue");

        customDataItemArrayList.add(customDataItem);

        debitMandateRequest.setCustomData(customDataItemArrayList);

    }


    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability() {
        showLoading();
        SDKManager.recurringPayment.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
            }
        });
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
                createDebitMandateRequest();
                break;
            case 1:
                //Request State;
                requestState();
                break;
            case 2:
                //Read a Debit Mandate
                viewDebitMandate();
                break;
            case 3:
                //Merchant Payment using Debit Mandate
                createTransactionObject();
                break;
            case 4:
                //View Transaction
                viewTransaction();
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

    private void createDebitMandateRequest() {
        showLoading();
        SDKManager.recurringPayment.createAccountDebitMandate(NotificationMethod.POLLING, "", identifierArrayList, debitMandateRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onRecurringPaymentSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRecurringPaymentFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });

    }

    private void createTransactionObject() {
        transactionRequest = new TransactionRequest();
        transactionRequest.setAmount("200");
        transactionRequest = new TransactionRequest();

        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("mandatereference");
        debitPartyItem.setValue(debitMandateReference);
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("2999");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("200.00");
        transactionRequest.setCurrency("RWF");

        initiateMerchantPayment();

    }

    private void initiateMerchantPayment() {
        showLoading();
        SDKManager.recurringPayment.createMerchantTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject).toString());
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }


    //get the request state of a transaction
    private void requestState() {
        showLoading();
        SDKManager.recurringPayment.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                transactionRef = requestStateObject.getObjectReference();
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
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

    private void viewDebitMandate() {
        showLoading();
        SDKManager.recurringPayment.viewAccountDebitMandate(identifierArrayList, transactionRef, new DebitMandateInterface() {
            @Override
            public void onDebitMandateSuccess(DebitMandate debitMandate) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                debitMandateReference = debitMandate.getMandateReference();
                txtResponse.setText(new Gson().toJson(debitMandate));
                Log.d(SUCCESS, "onDebitMandateSuccess: " + new Gson().toJson(debitMandate));
            }

            @Override
            public void onDebitMandateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onDebitMandateFailure: " + new Gson().toJson(gsmaError));
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
     * Method for fetching a particular transaction.
     */
    private void viewTransaction() {
        showLoading();
        SDKManager.recurringPayment.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }

        });
    }

    /**
     * Create Payment Reversal Object.
     */
    private void createPaymentReversalObject() {

        reversalObject = new ReversalObject();
        reversalObject.setType("reversal");

    }

    /**
     * Payment Refund
     */
    private void paymentRefund() {
        showLoading();
        
        SDKManager.recurringPayment.createRefundTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onRefundSuccess" + new Gson().toJson(requestStateObject));
                txtResponse.setText(new Gson().toJson(requestStateObject));
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
        SDKManager.recurringPayment.createReversal(NotificationMethod.POLLING, "", "REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                txtResponse.setText(new Gson().toJson(requestStateObject));
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

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
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
            public void onBalanceSuccess(Balance balance) {
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
            public void onRetrieveTransactionSuccess(Transaction transaction) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transaction));
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
        SDKManager.recurringPayment.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(missingResponse));
                Log.d(SUCCESS, "onMissingTransactionSuccess: " + new Gson().toJson(missingResponse));
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
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
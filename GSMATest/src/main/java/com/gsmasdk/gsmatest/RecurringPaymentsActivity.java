package com.gsmasdk.gsmatest;

import androidx.appcompat.app.AppCompatActivity;

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
import com.gsmaSdk.gsma.interfaces.DebitMandateInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.DebitMandate;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.PayeeItem;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.CreditPartyItem;
import com.gsmaSdk.gsma.models.transaction.CustomDataItem;
import com.gsmaSdk.gsma.models.transaction.DebitPartyItem;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

import java.util.ArrayList;
import java.util.Arrays;

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
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability, String correlationID) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                correlationId = correlationID;
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
                break;

            case 6:
                //Recurring Payment Reversal

                break;
            case 7:
                // Service Provide Balance
                break;
            case 8:
                //Retrieve Payments for a Service provider
                break;
            case 9:
                //Missing Transaction


            default:
                break;
        }
    }

    private void createDebitMandateRequest() {
        showLoading();
        SDKManager.recurringPayment.createAccountDebitMandate(NotificationMethod.POLLING, "", identifierArrayList, debitMandateRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                correlationId = correlationID;
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
        });

    }

    private void createTransactionObject(){
        transactionRequest=new TransactionRequest();
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

    private void initiateMerchantPayment(){
        showLoading();
        SDKManager.recurringPayment.createMerchantTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject).toString());
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                correlationId = correlationID;
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
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                correlationId = correlationID;
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

        });
    }

    private void viewDebitMandate() {
        showLoading();
        SDKManager.recurringPayment.viewAccountDebitMandate(identifierArrayList, transactionRef, new DebitMandateInterface() {
            @Override
            public void onDebitMandateSuccess(DebitMandate debitMandate) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                debitMandateReference=debitMandate.getMandateReference();
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
            public void onTransactionSuccess(TransactionRequest transactionObject, String correlationID) {
                correlationId = correlationID;
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

}
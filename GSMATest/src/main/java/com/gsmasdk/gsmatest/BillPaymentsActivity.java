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
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveBillPaymentInterface;
import com.gsmaSdk.gsma.interfaces.BillPaymentInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.AccountIdentifier;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.bills.BillPay;
import com.gsmaSdk.gsma.models.bills.BillPayments;
import com.gsmaSdk.gsma.models.bills.Bills;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("unchecked")
public class BillPaymentsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";

    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";

    private String transactionRef = "";

    private String serverCorrelationId;
    ArrayList<Identifier> identifierArrayList;
    private Transaction transactionRequest;

    private final String[] billPaymentArray = {
            "View Account Bills",
            "Create Bill Transaction",
            "Create Bill Payments",
            "View Request State",
            "View Bill Payment",
            "Retrieve a Missing API Response",
            "View Transaction"
    };

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payments);

        setTitle("Bill Payments");

        ListView listUseCases = findViewById(R.id.billPaymentList);

        CustomUseCaseAdapter customListAdapter = new CustomUseCaseAdapter(BillPaymentsActivity.this, new ArrayList(Arrays.asList(billPaymentArray)));
        listUseCases.setAdapter(customListAdapter);
        listUseCases.setOnItemClickListener(this);
        txtResponse = findViewById(R.id.txtBillPaymentResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());
        progressdialog = Utils.initProgress(BillPaymentsActivity.this);

        checkServiceAvailability();
        createAccountIdentifier();
        createTransactionObject();

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
                //View Account Bills;
                viewAccountBillPayment();
                break;
            case 1:
                //Create Bill Transaction
                createBillTransaction();

                break;
            case 2:
                //Create Bill Payments
                createBillPayments();

                break;
            case 3:
                //View Request State
                requestState();

                break;
            case 4:
                //View Bill Payment
                viewBillPayment();
                break;
            case 5:
                //  Retrieve a Missing API Response
                getMissingTransaction();
                break;
            case 6:
                //view transaction
                viewTransaction();
                break;
            default:
                break;

        }

    }


    /**
     * Transaction Object for Merchant Pay.
     */
    private void createTransactionObject() {
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

    }

    /**
     * Payee/payer  initiated payment
     */
    private void createBillTransaction() {
        showLoading();
        SDKManager.billPayment.createBillTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(BillPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(BillPaymentsActivity.this, "Failure");
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
     * Retrieve Transaction
     */
    private void viewAccountBillPayment() {
        showLoading();

        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        SDKManager.billPayment.viewAccountBills(identifierArrayList, transactionFilter, new RetrieveBillPaymentInterface() {
            @Override
            public void onRetrieveBillPaymentSuccess(Bills bills) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(bills));
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(bills));
            }

            @Override
            public void onRetrieveBillPaymentFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, gsmaError.getErrorBody().getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(gsmaError));

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(errorObject));
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(errorObject));

            }
        });

    }


    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability() {
        showLoading();
        SDKManager.accountLinking.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                Utils.showToast(BillPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
            }
        });


    }

    /**
     * Method for retrieve a set of Bill payments.
     */
    private void viewBillPayment() {
        showLoading();

        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        SDKManager.billPayment.viewBillPayment(identifierArrayList, transactionFilter, "REF-000001", new BillPaymentInterface() {
            @Override
            public void onBillPaymentSuccess(BillPayments billPayments) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(billPayments));
                Log.d(SUCCESS, "onBillPaymentSuccess: " + new Gson().toJson(billPayments));
            }

            @Override
            public void onBillPaymentFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onBillPaymentFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });
    }

    /**
     * View Transaction-View the transaction Details
     */
    private void viewTransaction() {
        showLoading();
        SDKManager.billPayment.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(Transaction transactionRequest) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(transactionRequest));
                Utils.showToast(BillPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionRequest));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }

        });
    }

    /**
     * Retrieve a missing Transaction
     */
    private void getMissingTransaction() {
        showLoading();

        SDKManager.billPayment.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(missingResponse));
                Log.d(SUCCESS, "onMissingTransactionSuccess: " + new Gson().toJson(missingResponse));
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onMissingResponseFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });

    }

    public void showLoading() {
        progressdialog.show();
    }

    public void hideLoading() {
        progressdialog.dismiss();
    }

    private void createAccountIdentifier() {
        identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("1");

        identifierArrayList.add(identifierAccount);

    }

    private void createBillPayments() {
        showLoading();
        BillPay billPayment = new BillPay();
        billPayment.setCurrency("GBP");
        billPayment.setAmountPaid("5.30");

        SDKManager.billPayment.createBillPayment(NotificationMethod.CALLBACK, "", identifierArrayList, billPayment, "REF-000001", new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, gsmaError.getErrorBody().getErrorDescription());
                Log.d(VALIDATION, "onRequestStateFailure: " + new Gson().toJson(gsmaError));

            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });

    }

    /**
     * Get the request state of a transaction
     */
    private void requestState() {
        showLoading();
        SDKManager.accountLinking.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Success");
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

}
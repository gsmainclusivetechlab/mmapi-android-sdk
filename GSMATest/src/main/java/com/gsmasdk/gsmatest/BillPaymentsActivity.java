package com.gsmasdk.gsmatest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.BillPaymentInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveBillPaymentInterface;
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
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ALL")
public class BillPaymentsActivity extends AppCompatActivity implements CustomUseCaseRecyclerAdapter.ItemClickListener {


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
    StringBuilder sbOutPut;

    private CustomUseCaseRecyclerAdapter customRecyclerAdapter;


    private final String[] billPaymentArray = {
            "Successful Retrieval of Bills",
            "Make a Successful Bill Payment with Callback",
            "Make a Bill Payment with Polling",
            "Retrieval of Bill Payments",
            "Check for Service Availability",
    };

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payments);

        setTitle("Bill Payments");


        RecyclerView recyclerView = findViewById(R.id.billPaymentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        customRecyclerAdapter = new CustomUseCaseRecyclerAdapter(this, true, billPaymentArray);
        customRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(customRecyclerAdapter);

        txtResponse = findViewById(R.id.txtBillPaymentResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());
        progressdialog = Utils.initProgress(BillPaymentsActivity.this);


        createTransactionObject();

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
    private void createBillTransaction(int position,NotificationMethod notificationMethod) {
        showLoading();
        SDKManager.billPayment.createBillTransaction(notificationMethod, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(errorObject));
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut);
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
                //noinspection ConstantConditions
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    hideLoading();
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(new Gson().toJson(requestStateObject));

                } else {
                    hideLoading();
                    serverCorrelationId = requestStateObject.getServerCorrelationId();
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    requestStateTransaction(position);
                }


            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
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
    private void viewAccountBillPayment(int position) {
        showLoading();
        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        SDKManager.billPayment.viewAccountBills(createAccountIdentifier("accountid","1"), transactionFilter, new RetrieveBillPaymentInterface() {
            @Override
            public void onRetrieveBillPaymentSuccess(Bills bills) {
                hideLoading();
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(bills));
                if (bills.getBillList() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty\n\n");
                } else {
                    Utils.showToast(BillPaymentsActivity.this, "Success");
                    sbOutPut.append(new Gson().toJson(bills) + "\n\n");
                    customRecyclerAdapter.setStatus(1, position);
                }
                txtResponse.setText(sbOutPut);
            }

            @Override
            public void onRetrieveBillPaymentFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError) + "\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(errorObject) + "\n\n");
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());

            }
        });

    }


    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability(int position) {
        showLoading();
        SDKManager.billPayment.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(serviceAvailability).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(1, position);
                Utils.showToast(BillPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
            }
        });


    }

    /**
     * Method for retrieve a set of Bill payments.
     */
    private void viewBillPayment(int position) {
        showLoading();
        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        SDKManager.billPayment.viewBillPayment(createAccountIdentifier("accountid","1"), transactionFilter, "REF-000001", new BillPaymentInterface() {
            @Override
            public void onBillPaymentSuccess(BillPayments billPayments) {
                hideLoading();
                Log.d(SUCCESS, "onBillPaymentSuccess: " + new Gson().toJson(billPayments));
                if (billPayments.getBillPayments().get(0).getBillPaymentStatus() == null
                        || billPayments.getBillPayments().get(0).getAmountPaid() == null
                        || billPayments.getBillPayments().get(0).getCurrency() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty\n\n");

                } else {
                    Utils.showToast(BillPaymentsActivity.this, "Success");
                    sbOutPut.append(new Gson().toJson(billPayments));
                    customRecyclerAdapter.setStatus(1, position);
                }
                txtResponse.setText(sbOutPut);


            }

            @Override
            public void onBillPaymentFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError) + "\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
                ;

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(errorObject) + "\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
            }
        });
    }

    /**
     * View Transaction-View the transaction Details
     */
    private void viewTransaction(int position) {
        sbOutPut.append("\n\n View Transaction\n\n");
        SDKManager.billPayment.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject)+"\n\n");
                txtResponse.setText(sbOutPut);
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(Transaction transactionRequest) {

                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionRequest));
                if (transactionRequest == null
                        || transactionRequest.getTransactionReference() == null
                        || transactionRequest.getTransactionStatus() == null
                        || transactionRequest.getCurrency() == null
                        || transactionRequest.getCreditParty() == null
                        || transactionRequest.getDebitParty() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(transactionRequest)+"\n\n");
                    txtResponse.setText(sbOutPut.toString());
                    sbOutPut.append("\n\n Create Bill Payment - Output\n");
                    createBillPayments(position,NotificationMethod.CALLBACK);

                }

            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError)+"\n\n");
                txtResponse.setText(sbOutPut);
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

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
     * Return account Identifier
     */

    private ArrayList<Identifier> createAccountIdentifier(String accountIdentifierKey, String accountIdentifierValue) {
        identifierArrayList = new ArrayList<>();
        identifierArrayList.clear();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey(accountIdentifierKey);
        identifierAccount.setValue(accountIdentifierValue);
        identifierArrayList.add(identifierAccount);
        return identifierArrayList;
    }

    private void createBillPayments(int position, NotificationMethod notificationMethod) {

        BillPay billPayment = new BillPay();
        billPayment.setCurrency("GBP");
        billPayment.setAmountPaid("5.30");

        SDKManager.billPayment.createBillPayment(NotificationMethod.POLLING, "", createAccountIdentifier("accountid","1"), billPayment, "REF-000001", new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    hideLoading();
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    if (notificationMethod == NotificationMethod.POLLING) {
                        sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                        requestState(position);
                    } else {
                        hideLoading();
                        Utils.showToast(BillPaymentsActivity.this, "Success");
                        customRecyclerAdapter.setStatus(1, position);
                        sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                        txtResponse.setText(sbOutPut.toString());

                    }
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);

            }
        });

    }

    /**
     * Get the request state of a transaction
     */
    private void requestState(int position) {
        sbOutPut.append("\n\n Request State-Output \n\n");
        SDKManager.billPayment.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    hideLoading();
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty\n\n");
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    //transactionRef= requestStateObject.getObjectReference();
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    sbOutPut.append("\n\n View Bill Payment - Output\n\n");
                    viewBillPayment(position);


                }

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    /**
     * Get the request state of a transaction
     */
    private void requestStateTransaction(int position) {
        sbOutPut.append("\n\n Request State - Output \n\n");
        SDKManager.billPayment.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    hideLoading();
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty\n\n");
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    transactionRef= requestStateObject.getObjectReference();
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    viewTransaction(position);

                }

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(BillPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    @Override
    public void onItemClick(View view, int position) {

        switch (position) {
            case 0:
                //Successful Retrieval of Bills
                sbOutPut = new StringBuilder();
                sbOutPut.append("View Account Bill - Output \n\n");
                viewAccountBillPayment(position);
                break;

            case 1:
                //Make a Successful Bill Payment with Callback
                sbOutPut = new StringBuilder();
                sbOutPut.append("Create Bill Transaction - Output \n\n");
                createBillTransaction(position, NotificationMethod.CALLBACK);

                break;
            case 2:
                //Make a Bill Payment with Polling
                sbOutPut = new StringBuilder();
                showLoading();
                sbOutPut.append("Create Bill Payment Polling-Output \n\n");
                createBillPayments(position, NotificationMethod.POLLING);

                break;
            case 3:
                //Retrieval of Bill Payments
                sbOutPut = new StringBuilder();
                sbOutPut.append("View bill payment-Output \n\n");
                viewBillPayment(position);
                break;
            case 4:
                //check service availability
                sbOutPut = new StringBuilder();
                sbOutPut.append("Check Service Availability -Output\n\n");
                checkServiceAvailability(position);
                break;

            default:
                break;

        }
    }
}
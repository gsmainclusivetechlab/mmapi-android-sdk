package com.gsmasdk.gsmatest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
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
import com.gsmaSdk.gsma.models.account.AccountIdentifier;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.debitmandate.DebitMandate;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ALL")
public class RecurringPaymentsActivity extends AppCompatActivity implements CustomUseCaseRecyclerAdapter.ItemClickListener {


    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";
    private Reversal reversalObject;


    private String transactionRef = "";
    private String serverCorrelationId;

    private Transaction transactionRequest;
    ArrayList<Identifier> identifierArrayList;
    private CustomUseCaseRecyclerAdapter customRecyclerAdapter;
    private String debitMandateReference;
    StringBuilder sbOutPut;

    private DebitMandate debitMandateRequest;

    private final String[] recurringPaymentArray = {
            "Setup a Recurring Payment",
            "Take a Recurring Payment",
            "Take a Recurring Payment using the Polling Method",
            "Recurring Payment Refund",
            "Recurring Payment Reversal",
            "Payer sets up a Recurring Payment using MMP Channel",
            "Obtain a Service Provider Balance",
            "Retrieve Payments for a Service Provider",
            "Check for Service Availability",
            "Retrieve a Missing API Response",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_payments);
        setTitle("Recurring Payments");

        RecyclerView recyclerView = findViewById(R.id.recurringPaymentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        customRecyclerAdapter = new CustomUseCaseRecyclerAdapter(this,true, recurringPaymentArray);
        customRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(customRecyclerAdapter);

        txtResponse = findViewById(R.id.txtRecurringResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());
        progressdialog = Utils.initProgress(RecurringPaymentsActivity.this);

        //service availability check
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
        identifierAccount.setValue("1");

        identifierArrayList.add(identifierAccount);
    }

    //debit mandate object
    private void createDebitMandateObject() {
        debitMandateRequest = new DebitMandate();

        //payee object of debit mandate
        ArrayList<AccountIdentifier> payeeItemArrayList = new ArrayList<>();

        //payee object
        AccountIdentifier payeeItem = new AccountIdentifier();
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
    private void checkServiceAvailability(int position) {
        showLoading();
        SDKManager.recurringPayment.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
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
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
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
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                //Create Debit Mandate;
                createDebitMandateRequest(position);
                break;
            case 1:
                //Request State;
                requestState(position);
                break;
            case 2:
                //Read a Debit Mandate
                viewDebitMandate(position);
                break;
            case 3:
                //Merchant Payment using Debit Mandate
                createTransactionObject(position);
                break;
            case 4:
                //Transfer Reversal
                sbOutPut = new StringBuilder();
                sbOutPut.append("Reversal -Output\n\n");
                paymentReversal(position);
                break;
            case 5:
                //Set up recurring payment
                sbOutPut = new StringBuilder();
                sbOutPut.append("Set Up Recurring Payment -Output\n\n");
                initiateMerchantPayment(position);
                break;
            case 6:
                //Obtain an FSP Balance
                sbOutPut = new StringBuilder();
                sbOutPut.append("Balance -Output\n\n");
                balanceCheck(position);
                break;
            case 7:
                //Retrieve payment
                sbOutPut = new StringBuilder();
                sbOutPut.append("Retrieve Payment -Output\n\n");
                retrieveTransaction(position);
                break;
            case 8:
                //check service availability
                sbOutPut = new StringBuilder();
                sbOutPut.append("Check Service Availability -Output\n\n");
                checkServiceAvailability(position);
                break;
            case 9:
                //missing response
                sbOutPut = new StringBuilder();
                sbOutPut.append("Missing Response -Output\n\n");
                recurringMissingResponse(position);
            default:
                break;
        }
    }

    private void createDebitMandateRequest(int position) {
        showLoading();
        SDKManager.recurringPayment.createAccountDebitMandate(NotificationMethod.POLLING, "", identifierArrayList, debitMandateRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onRecurringPaymentSuccess: " + new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRecurringPaymentFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });

    }

    private void createTransactionObject(int position) {
        transactionRequest = new Transaction();
        transactionRequest.setAmount("200");
        transactionRequest = new Transaction();

        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

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

        initiateMerchantPayment(position);

    }

    private void initiateMerchantPayment(int position) {
        showLoading();
        SDKManager.recurringPayment.createMerchantTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();


                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    customRecyclerAdapter.setStatus(1, position);
                    Utils.showToast(RecurringPaymentsActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
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


    //get the request state of a transaction
    private void requestState(int position) {
        showLoading();
        SDKManager.recurringPayment.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                transactionRef = requestStateObject.getObjectReference();
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
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

    private void viewDebitMandate(int position) {
        showLoading();
        SDKManager.recurringPayment.viewAccountDebitMandate(identifierArrayList, transactionRef, new DebitMandateInterface() {
            @Override
            public void onDebitMandateSuccess(DebitMandate debitMandate) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                debitMandateReference = debitMandate.getMandateReference();
                txtResponse.setText(new Gson().toJson(debitMandate));
                Log.d(SUCCESS, "onDebitMandateSuccess: " + new Gson().toJson(debitMandate));
                if (debitMandate == null || debitMandate.getMandateReference() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }
            }

            @Override
            public void onDebitMandateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onDebitMandateFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }

    /**
     * Method for fetching a particular transaction.
     */
    private void viewTransaction(int position) {
        showLoading();
        SDKManager.recurringPayment.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onTransactionSuccess(Transaction transactionObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject));
                if (transactionObject == null
                        || transactionObject.getTransactionReference() == null
                        || transactionObject.getTransactionStatus() == null
                        || transactionObject.getCurrency() == null
                        || transactionObject.getCreditParty() == null
                        || transactionObject.getDebitParty() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

        });
    }

    /**
     * Create Payment Reversal Object.
     */
    private void createPaymentReversalObject() {

        reversalObject = new Reversal();
        reversalObject.setType("reversal");

    }

    /**
     * Payment Refund
     */
    private void paymentRefund(int position) {
        showLoading();

        SDKManager.recurringPayment.createRefundTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(RecurringPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onRefundSuccess" + new Gson().toJson(requestStateObject));
                txtResponse.setText(new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRefundFailure: " + new Gson().toJson(gsmaError));
                txtResponse.setText(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
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
     * Payment Reversal
     */
    private void paymentReversal(int position) {
        showLoading();
        SDKManager.recurringPayment.createReversal(NotificationMethod.POLLING, "", "REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();

                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    Utils.showToast(RecurringPaymentsActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
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
     * Checking Balance.
     */
    private void balanceCheck(int position) {
        showLoading();
        SDKManager.recurringPayment.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onBalanceSuccess(Balance balance) {
                hideLoading();

                if (balance == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(balance).toString());
                    Utils.showToast(RecurringPaymentsActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }

    /**
     * Retrieve Transaction
     */
    private void retrieveTransaction(int position) {
        showLoading();

        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        identifierArrayList.clear();
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2999");
        identifierArrayList.add(identifierAccount);

        SDKManager.recurringPayment.viewAccountTransactions(identifierArrayList, transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {
                hideLoading();

                Transaction transactionRequest = transaction.getTransaction().get(0);
                if (transactionRequest == null
                        || transactionRequest.getTransactionReference() == null
                        || transactionRequest.getTransactionStatus() == null
                        || transactionRequest.getCurrency() == null
                        || transactionRequest.getCreditParty() == null
                        || transactionRequest.getDebitParty() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    sbOutPut.append(new Gson().toJson(transaction).toString());
                    customRecyclerAdapter.setStatus(1, position);
                    Utils.showToast(RecurringPaymentsActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }

    /**
     * Missing Transaction
     */
    private void getMissingTransaction(int position) {
        showLoading();
        SDKManager.recurringPayment.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
                hideLoading();

                if (missingResponse == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(missingResponse).toString());
                    Utils.showToast(RecurringPaymentsActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onMissingTransactionSuccess: " + new Gson().toJson(missingResponse));
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(RecurringPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(RecurringPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }

    /**
     * Recurring payments missing transaction
     */
    private void recurringMissingResponse(int position) {
        showLoading();
        SDKManager.recurringPayment.createMerchantTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(requestStateObject));
                    getMissingTransaction(position);
                }
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
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
}
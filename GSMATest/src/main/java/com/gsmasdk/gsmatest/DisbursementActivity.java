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
import com.gsmaSdk.gsma.interfaces.BatchCompletionInterface;
import com.gsmaSdk.gsma.interfaces.BatchRejectionInterface;
import com.gsmaSdk.gsma.interfaces.BatchTransactionItemInterface;
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
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.PatchData;
import com.gsmaSdk.gsma.models.transaction.batchcompletion.BatchCompletions;
import com.gsmaSdk.gsma.models.transaction.batchrejection.BatchRejections;
import com.gsmaSdk.gsma.models.transaction.batchtransaction.BatchTransaction;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ALL")
public class DisbursementActivity extends AppCompatActivity implements CustomUseCaseRecyclerAdapter.ItemClickListener {

    private Transaction transactionRequest;
    private BatchTransaction bulkTransactionObject;
    private TextView txtResponse;

    private String transactionRef = "";
    private String serverCorrelationId;
    private Reversal reversalObject;
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private String correlationId = "";
    private ArrayList<PatchData> patchDataArrayList;
    private ProgressDialog progressdialog;
    ArrayList<Identifier> identifierArrayList;

    private CustomUseCaseRecyclerAdapter customRecyclerAdapter;

    StringBuilder sbOutPut;

    private final String[] disbursementArray = {
            "Individual Disbursement",
            "Bulk Disbursement",
            "Bulk Disbursement with Maker / Checker",
            "Individual Disbursement Using the Polling Method",
            "Disbursement Reversal",
            "Obtain a Disbursement Organisation Balance",
            "Retrieve Transactions for a Disbursement Organisation",
            "Check for Service Availability",
            "Retrieve a Missing API Response"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);
        setTitle("Disbursement");


        RecyclerView recyclerView = findViewById(R.id.disbursementList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        customRecyclerAdapter = new CustomUseCaseRecyclerAdapter(this, true, disbursementArray);
        customRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(customRecyclerAdapter);

        txtResponse = findViewById(R.id.txtResponseDisbursement);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());

        progressdialog = Utils.initProgress(DisbursementActivity.this);

        createTransactionObject();
        createPaymentReversalObject();
        createBulkTransactionObject();
        createBatchRequestObject();

    }


    private void createTransactionObject() {
        transactionRequest = new Transaction();
        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();

        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

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

    private void createPaymentReversalObject() {
        reversalObject = new Reversal();
        reversalObject.setType("reversal");
    }

    private void createBatchRequestObject() {
        //create a batch object
        PatchData batchObject = new PatchData();
        batchObject.setOp("replace");
        batchObject.setPath("/batchStatus");
        batchObject.setValue("approved");
        patchDataArrayList = new ArrayList<>();
        patchDataArrayList.add(batchObject);
    }

    /**
     * Method for creating Bulk transaction object.
     */
    private void createBulkTransactionObject() {
        bulkTransactionObject = new BatchTransaction();

        ArrayList<Transaction> transactionItems = new ArrayList<>();
        Transaction transactionItem = new Transaction();
        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

        debitPartyItem.setKey("msisdn");
        debitPartyItem.setValue("+44012345678");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("walletid");
        creditPartyItem.setValue("1");
        creditPartyList.add(creditPartyItem);

        transactionItem.setAmount("16.00");
        transactionItem.setType("transfer");
        transactionItem.setCurrency("USD");
        transactionItem.setCreditParty(creditPartyList);
        transactionItem.setDebitParty(debitPartyList);
        transactionItems.add(transactionItem);

        bulkTransactionObject.setBatchDescription("Testing a Batch transaction");
        bulkTransactionObject.setBatchTitle("Batch Test");
        bulkTransactionObject.setScheduledStartDate("2019-12-11T15:08:03.158Z");
        bulkTransactionObject.setTransactions(transactionItems);

    }

    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability(int position) {
        showLoading();
        SDKManager.disbursement.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(serviceAvailability).toString());
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(DisbursementActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
                customRecyclerAdapter.setStatus(1, position);
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(DisbursementActivity.this, "Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }

    /**
     * Return account Identifier
     */

    private ArrayList<Identifier> createAccountIdentifier(String accountIdentifierKey, String accountIdentifierValue){
        identifierArrayList = new ArrayList<>();
        identifierArrayList.clear();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey(accountIdentifierKey);
        identifierAccount.setValue(accountIdentifierValue);
        identifierArrayList.add(identifierAccount);
        return identifierArrayList;
    }



    public void showLoading() {
        progressdialog.show();
    }

    public void hideLoading() {
        progressdialog.dismiss();
    }



    //individual disbursement
    private void individualDisbursement(int position,NotificationMethod notificationMethod) {
        showLoading();
        SDKManager.disbursement.createDisbursementTransaction(notificationMethod, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);

            }
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Success");
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());

                } else {
                    serverCorrelationId = requestStateObject.getServerCorrelationId();
                    Utils.showToast(DisbursementActivity.this, "Success");
                    Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
                    if(notificationMethod==NotificationMethod.CALLBACK){
                        if(position==8){
                            sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                            getMissingTransaction(position);
                        }else{
                            customRecyclerAdapter.setStatus(1, position);
                            sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                            txtResponse.setText(sbOutPut.toString());
                        }

                    }else{
                        sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                        requestState(position);
                    }

                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Failure");
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




    //Reversal
    private void reversal(int position) {
        showLoading();
        SDKManager.disbursement.createReversal(NotificationMethod.CALLBACK, "", "REF-1633580365289", reversalObject, new RequestStateInterface() {
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
                    Utils.showToast(DisbursementActivity.this, "Success");
                }
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
                txtResponse.setText(sbOutPut.toString());
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Success");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    //Retrieve Disbursement
    private void retrieveTransactionDisbursement(int position) {
        showLoading();
        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        SDKManager.disbursement.viewAccountTransactions(createAccountIdentifier("accountid","2999"), transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));

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
                    Utils.showToast(DisbursementActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }

    //Bulk Disbursement
    public void bulkDisbursement(int position,NotificationMethod notificationMethod) {
        showLoading();
        SDKManager.disbursement.createBatchTransaction(notificationMethod, "", bulkTransactionObject, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    hideLoading();
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    requestState(position);
                }

                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Failure");
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




    /**
     * Request State
     */
    private void requestState(int position) {
        sbOutPut.append("\n\nView Request state - Output\n\n");
        SDKManager.merchantPayment.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject) + "\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                transactionRef = requestStateObject.getObjectReference();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                    hideLoading();
                } else {
                    if(position==3){
                         hideLoading();
                          sbOutPut.append(new Gson().toJson(requestStateObject).toString() + "\n\n");
                          viewTransasction(position);
                    }else{
                          sbOutPut.append(new Gson().toJson(requestStateObject).toString() + "\n\n");
                          batchTransaction(position);
                    }
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError) + "\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());

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
    private void viewTransasction(int position) {
        sbOutPut.append("View Transaction - Output\n\n");
        SDKManager.merchantPayment.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
            }

            @Override
            public void onTransactionSuccess(Transaction transactionRequest) {
                hideLoading();
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
                    txtResponse.setText(sbOutPut.toString());


                } else {
                    Utils.showToast(DisbursementActivity.this, "Success");
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(transactionRequest)+"\n\n");
                    txtResponse.setText(sbOutPut.toString());
                }

            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError)+"\n\n");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

        });
    }



    //Check Balance
    private void balanceCheck(int position) {
        showLoading();
        SDKManager.disbursement.viewAccountBalance(createAccountIdentifier("accountid","1"), new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
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
                    Utils.showToast(DisbursementActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Success");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }
        });
    }

    //Completed Batch Transactions
    private void viewBatchCompletions(int position, String batchId) {
        sbOutPut.append("\n\nBatch Completions -  Output\n\n");
        SDKManager.disbursement.viewBatchCompletions(batchId, new BatchCompletionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void batchTransactionCompleted(BatchCompletions batchTransactionCompletion) {
                txtResponse.setText(new Gson().toJson(batchTransactionCompletion));
                Log.d(SUCCESS, "batchTransactionCompleted: " + new Gson().toJson(batchTransactionCompletion));
                if (batchTransactionCompletion == null) {
                    hideLoading();
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());

                } else {
                    sbOutPut.append(new Gson().toJson(batchTransactionCompletion).toString());
                    viewBatchRejectionsBulk(position, batchId);
                }

            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }
        });
    }


    //Rejected Disbursements
    private void viewBatchRejectionsBulk(int position, String batchId) {
        sbOutPut.append("\n\nBatch Rejection - Output\n\n");
        SDKManager.disbursement.viewBatchRejections(batchId, new BatchRejectionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void batchTransactionRejections(BatchRejections batchTransactionRejection) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(batchTransactionRejection));
                Log.d(SUCCESS, "batchTransactionRejections: " + new Gson().toJson(batchTransactionRejection));
                if (batchTransactionRejection == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    Utils.showToast(DisbursementActivity.this, "Success");
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(batchTransactionRejection).toString());
                    txtResponse.setText(sbOutPut.toString());

                }
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }
        });
    }



    //Update Batch Transaction
    private void updateBatch(int position, String batchId) {
        sbOutPut.append("\n\nUpdate a batch transaction - Output\n\n");
        SDKManager.disbursement.updateBatchTransaction(NotificationMethod.POLLING, "","REF-1635765084301", patchDataArrayList, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }


            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                    hideLoading();
                } else {
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    viewBatchCompletions(position,batchId);
                 }

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
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

    //Get Batch Transaction Details
    private void batchTransaction(int position) {
        sbOutPut.append("\nBatch Transaction - Output\n\n");
        SDKManager.disbursement.viewBatchTransaction(transactionRef, new BatchTransactionItemInterface() {
            @Override
            public void batchTransactionSuccess(BatchTransaction batchTransactionItem) {
                txtResponse.setText(new Gson().toJson(batchTransactionItem));
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(batchTransactionItem));

                if (batchTransactionItem == null || batchTransactionItem.getBatchId() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                    hideLoading();
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    sbOutPut.append(new Gson().toJson(batchTransactionItem));
                    if(position==2){
                        updateBatch(position,batchTransactionItem.getBatchId());
                    }else{
                        viewBatchCompletions(position, batchTransactionItem.getBatchId());
                    }

                }
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }
        });

    }



    //Retrieve a missing Transaction
    private void getMissingTransaction(int position) {
        sbOutPut.append("\n\n Missing Response - Output\n\n");
        SDKManager.disbursement.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
                hideLoading();
                if (missingResponse == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(missingResponse).toString());
                    Utils.showToast(DisbursementActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onMissingTransactionSuccess: " + new Gson().toJson(missingResponse));
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(DisbursementActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                //individual disbursement;
                sbOutPut = new StringBuilder();
                sbOutPut.append("Individual disbursement - Output  \n\n");
                individualDisbursement(position,NotificationMethod.CALLBACK);

                break;
            case 1:
                //bulkDisbursement
                sbOutPut = new StringBuilder();
                sbOutPut.append("Create Batch Transaction - Output  \n\n");
                bulkDisbursement(position,NotificationMethod.CALLBACK);

                break;
            case 2:
                //Bulk Disbursement with Maker / Checker
                sbOutPut = new StringBuilder();
                sbOutPut.append("Create Batch Transaction - Output  \n\n");
                bulkDisbursement(position,NotificationMethod.CALLBACK);
                break;
            case 3:
                //individual disbursement polling;
                sbOutPut = new StringBuilder();
                sbOutPut.append("Individual disbursement Polling - Output  \n\n");
                individualDisbursement(position,NotificationMethod.POLLING);

                break;
            case 4:
                //reversal
                sbOutPut = new StringBuilder();
                sbOutPut.append("Reversal -Output\n\n");
                reversal(position);

                break;
            case 5:
                //check balance
                sbOutPut = new StringBuilder();
                sbOutPut.append("Balance - Output\n\n");
                balanceCheck(position);
                break;
            case 6:
                //retrieve transaction
                sbOutPut = new StringBuilder();
                sbOutPut.append("Retrieve Transaction - Output\n\n");
                retrieveTransactionDisbursement(position);
                break;

            case 7:

                //check service availability
                sbOutPut = new StringBuilder();
                sbOutPut.append("Check Service Availability - Output\n\n");
                checkServiceAvailability(position);
                break;

            case 8:
                //missing response
                sbOutPut = new StringBuilder();
                sbOutPut.append("Create Missing Transaction - Output\n\n");
                individualDisbursement(position,NotificationMethod.CALLBACK);

                break;

            default:
                break;
        }
    }
}
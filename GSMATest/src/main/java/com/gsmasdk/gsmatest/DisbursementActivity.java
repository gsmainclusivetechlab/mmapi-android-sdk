package com.gsmasdk.gsmatest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.controllers.SDKManager;
import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.BatchCompletionInterface;
import com.gsmaSdk.gsma.interfaces.BatchRejectionInterface;
import com.gsmaSdk.gsma.interfaces.BatchTransactionItemInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.common.Balance;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.Batch;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionCompletion;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionItem;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionRejection;
import com.gsmaSdk.gsma.models.transaction.BulkTransactionObject;
import com.gsmaSdk.gsma.models.transaction.CreditPartyItem;
import com.gsmaSdk.gsma.models.transaction.DebitPartyItem;

import com.gsmaSdk.gsma.models.transaction.Transaction;


import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
public class DisbursementActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private TransactionRequest transactionRequest;
    private BulkTransactionObject bulkTransactionObject;
    private TextView txtResponse;

    private String transactionRef = "";
    private String serverCorrelationId;
    private ReversalObject reversalObject;
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private String correlationId = "";
    private ArrayList<Batch> batchArrayList;
    private ProgressDialog progressdialog;
    ArrayList<Identifier> identifierArrayList;
    private final String[] disbursementArray = {
            "Individual Disbursement",
            "Request State",
            "View Transaction",
            "Reversal",
            "Retrieve Transaction",
            "Disbursement Organisation Balance",
            "Bulk Transaction",
            "Batch Rejection",
            "Batch Completions",
            "Update Batch",
            "Get batch Details",
            "Missing Transaction Disbursement",

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);
        setTitle("Disbursement");

        ListView listUseCases = findViewById(R.id.disbursementList);
        CustomUseCaseAdapter customListAdapter = new CustomUseCaseAdapter(DisbursementActivity.this, new ArrayList(Arrays.asList(disbursementArray)));
        listUseCases.setAdapter(customListAdapter);
        listUseCases.setOnItemClickListener(this);
        txtResponse = findViewById(R.id.txtResponseDisbursement);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());

        progressdialog = Utils.initProgress(DisbursementActivity.this);

        checkServiceAvailability();
        createTransactionObject();
        createPaymentReversalObject();
        createBulkTransactionObject();
        createBatchRequestObject();
        createAccountIdentifier();
    }

    /*
     * Account identitifers for transaction
     *
     */
    private void createAccountIdentifier(){

        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();
//
        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("15523");
        identifierArrayList.add(identifierAccount);

        //msisdn
        Identifier identifierMsisdn = new Identifier();
        identifierMsisdn.setKey("msisdn");
        identifierMsisdn.setValue("%2B123456789102345");
        identifierArrayList.add(identifierMsisdn);

        //wallet id

        Identifier identifierWallet = new Identifier();
        identifierWallet.setKey("walletid");
        identifierWallet.setValue("3355544");
        identifierArrayList.add(identifierWallet);
//

    }

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

    private void createPaymentReversalObject() {
        reversalObject = new ReversalObject();
        reversalObject.setReversal("reversal");
    }

    private void createBatchRequestObject(){
        //create a batch object
        Batch batchObject = new Batch();
        batchObject.setOp("replace");
        batchObject.setPath("/batchStatus");
        batchObject.setValue("approved");
        batchArrayList=new ArrayList<>();
        batchArrayList.add(batchObject);
    }

    /**
     * Method for creating Bulk transaction object.
     */
    private void createBulkTransactionObject() {
        bulkTransactionObject = new BulkTransactionObject();

        ArrayList<TransactionRequest> transactionItems = new ArrayList<>();
        TransactionRequest transactionItem = new TransactionRequest();
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("2000");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("2999");
        creditPartyList.add(creditPartyItem);

        transactionItem.setAmount("200");
        transactionItem.setType("transfer");
        transactionItem.setCurrency("RWF");
        transactionItem.setCreditParty(creditPartyList);
        transactionItem.setDebitParty(debitPartyList);
        transactionItems.add(transactionItem);
        transactionItems.add(transactionItem);

        bulkTransactionObject.setBatchDescription("Testing a Batch transaction");
        bulkTransactionObject.setBatchTitle("Batch Test");
        bulkTransactionObject.setScheduledStartDate("2019-12-11T15:08:03.158Z");
        bulkTransactionObject.setTransactions(transactionItems);

    }

    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability() {
        showLoading();
        SDKManager.getInstance().viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability, String correlationID) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                correlationId = correlationID;
                Utils.showToast(DisbursementActivity.this,"Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(DisbursementActivity.this,"Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    /**
     * Method for fetching a particular transaction.
     */
    private void viewTransaction(){
        showLoading();
        SDKManager.getInstance().viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject, String correlationID) {
                correlationId = correlationID;
                hideLoading();
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


    public void showLoading() {
        progressdialog.show();
    }

    public void hideLoading() {
        progressdialog.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                //individual disbursement;
                 individualDisbursement();
                break;
            case 1:
                //request state
                 requestState();
                 break;
            case 2:
                //view Transaction
                viewTransaction();
                break;
            case 3:
                //reversal
                reversal();
                break;
            case  4:
                retrieveTransactionDisbursement();
                break;
            case 5:
                balanceCheck();
                break;
            case  6:
                bulkDisbursement();
                break;

            case  7:
                batchRejections();
                break;

            case 8:
               batchCompletion();
               break;
            case  9:
                updateBatch();
                break;
            case  10:
                getBatchDetails();
                break;

            case 11:
                getMissingTransaction();
                break;

            default:
                break;
        }
    }

    //get the request state of a transaction
    private void requestState(){
        showLoading();
        SDKManager.getInstance().viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
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
   //individual disbursement
    private void individualDisbursement(){
        showLoading();
        SDKManager.getInstance().createDisbursementTransaction(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                correlationId = correlationID;
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
            }

        });
    }

    //Reversal
    private void reversal(){
        showLoading();
        SDKManager.getInstance().createReversal(NotificationMethod.POLLING,"","REF-1633580365289", reversalObject, new RequestStateInterface() {
                @Override
                public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                    hideLoading();
                    serverCorrelationId = requestStateObject.getServerCorrelationId();
                    txtResponse.setText(new Gson().toJson(requestStateObject));
                    correlationId = correlationID;
                    Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
                }

                @Override
                public void onRequestStateFailure(GSMAError gsmaError) {
                     hideLoading();
                    txtResponse.setText(new Gson().toJson(gsmaError));
                    Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
                }

                @Override
                public void onValidationError(ErrorObject errorObject) {
                    hideLoading();
                    Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                    Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                }
            });
    }

    //Retrieve Disbursement
    private void retrieveTransactionDisbursement(){
        showLoading();
        SDKManager.getInstance().viewAccountTransactions(identifierArrayList, 0, 5, new RetrieveTransactionInterface() {
                @Override
                public void onValidationError(ErrorObject errorObject) {
                    hideLoading();
                    Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                    Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                }

                @Override
                public void onRetrieveTransactionSuccess(Transaction transaction, String correlationID) {
                    hideLoading();
                    txtResponse.setText(new Gson().toJson(transaction));
                    correlationId = correlationID;
                    Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
                }

                @Override
                public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                    hideLoading();
                    txtResponse.setText(new Gson().toJson(gsmaError));
                    Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
                }
            });
    }

    //Bulk Disbursement
    public void bulkDisbursement(){
        showLoading();
        SDKManager.getInstance().createBatchTransaction(NotificationMethod.POLLING,"",bulkTransactionObject, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                correlationId=correlationID;
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                txtResponse.setText(new Gson().toJson(requestStateObject));
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

    //Rejected Disbursements
    private void batchRejections(){
        showLoading();
        SDKManager.getInstance().viewBatchRejections("REF-1635765084301", new BatchRejectionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void batchTransactionRejections(BatchTransactionRejection batchTransactionRejection, String correlationID) {
                hideLoading();
                correlationId=correlationID;
                txtResponse.setText(new Gson().toJson(batchTransactionRejection));
                Log.d(SUCCESS, "batchTransactionRejections: " + new Gson().toJson(batchTransactionRejection));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    //Check Balance
    private void balanceCheck(){
        showLoading();
        SDKManager.getInstance().viewAccountBalance(identifierArrayList, new BalanceInterface() {
                @Override
                public void onValidationError(ErrorObject errorObject) {
                    hideLoading();
                    Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                    Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                }

                @Override
                public void onBalanceSuccess(Balance balance, String correlationID) {
                    hideLoading();
                    correlationId=correlationID;
                    txtResponse.setText(new Gson().toJson(balance));
                    Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
                }

                @Override
                public void onBalanceFailure(GSMAError gsmaError) {
                    hideLoading();
                    txtResponse.setText(new Gson().toJson(gsmaError));
                    Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
                }
            });
    }

    //Completed Batch Transactions
    private void batchCompletion(){
        showLoading();
        SDKManager.getInstance().viewBatchCompletions("REF-1635765084301", new BatchCompletionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void batchTransactionCompleted(BatchTransactionCompletion batchTransactionCompletion, String correlationID) {
                hideLoading();
                correlationId=correlationID;
                txtResponse.setText(new Gson().toJson(batchTransactionCompletion));
                Log.d(SUCCESS, "batchTransactionCompleted: " + new Gson().toJson(batchTransactionCompletion));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    //Update Batch Transaction
    private void updateBatch(){
        showLoading();
        SDKManager.getInstance().updateBatchTransaction(NotificationMethod.POLLING,"","REF-1635765084301",batchArrayList, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                correlationId=correlationID;
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                txtResponse.setText(new Gson().toJson(requestStateObject));
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

    //Get Batch Transaction Details
    private void getBatchDetails(){
        showLoading();
        SDKManager.getInstance().viewBatchTransaction(transactionRef, new BatchTransactionItemInterface() {
                @Override
                public void batchTransactionSuccess(BatchTransactionItem batchTransactionItem, String correlationID) {
                    hideLoading();
                    correlationId=correlationID;
                    txtResponse.setText(new Gson().toJson(batchTransactionItem));
                    Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(batchTransactionItem));
                }

                @Override
                public void onTransactionFailure(GSMAError gsmaError) {
                    hideLoading();
                    txtResponse.setText(new Gson().toJson(gsmaError));
                    Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
                }

                @Override
                public void onValidationError(ErrorObject errorObject) {
                    hideLoading();
                    Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                    Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                }
            });

    }

    //Retrieve a missing Transaction
    private void getMissingTransaction() {
        showLoading();
        SDKManager.getInstance().viewTransactionResponse(correlationId, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject, String correlationId) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject, TransactionRequest.class));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(DisbursementActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(DisbursementActivity.this, "Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

        });
    }
}
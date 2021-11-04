package com.gsmasdk.gsmatest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.controllers.SDKManager;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.BatchCompletionInterface;
import com.gsmaSdk.gsma.interfaces.BatchRejectionInterface;
import com.gsmaSdk.gsma.interfaces.BatchTransactionItemInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.common.Balance;
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

import com.gsmaSdk.gsma.models.transaction.TransactionItem;

import com.gsmaSdk.gsma.models.transaction.TransactionObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class DisbursementActivity extends AppCompatActivity {

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
    private Batch batchObject;
    private ArrayList<Batch> batchArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);

        Button btnIndividualDisbursement = findViewById(R.id.btnIndividualDisbursement);
        Button btnViewTransactionDisbursement = findViewById(R.id.btnViewTransactionDisbursement);
        Button btnRequestStateDisbursement = findViewById(R.id.btnRequestStateDisbursement);

        Button btnReversalDisbursement = findViewById(R.id.btnReversalDisbursement);
        Button btnBalanceDisbursement = findViewById(R.id.btnBalanceDisbursement);
        Button btnRetrieveTransactionDisbursement = findViewById(R.id.btnRetrieveTransactionDisbursement);

        Button btnBulkDisbursement = findViewById(R.id.btnBulkDisbursement);
        Button btnBatchRejections = findViewById(R.id.btnBatchRejections);
        Button btnBatchCompletions = findViewById(R.id.btnBatchCompletions);
        Button btnBatchUpdate = findViewById(R.id.btnBatchUpdate);
        Button btnRetrieveBatch = findViewById(R.id.btnRetrieveBatch);

        txtResponse = findViewById(R.id.txtDisbursementResponse);
        //create object for transaction request
        createTransactionObject();
        createPaymentReversalObject();
        createBulkTransactionObject();
        createBatchRequestObject();



        btnIndividualDisbursement.setOnClickListener(v -> SDKManager.getInstance().disbursementPay("disbursement", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                correlationId = correlationID;
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
            }

        }));


        btnRequestStateDisbursement.setOnClickListener(v -> SDKManager.getInstance().viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                correlationId = correlationID;
                txtResponse.setText(new Gson().toJson(requestStateObject));
                transactionRef = requestStateObject.getObjectReference();
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

        }));

        //individual disbursement using polling method-view transaction
        btnViewTransactionDisbursement.setOnClickListener(v -> SDKManager.getInstance().viewTransaction(transactionRef, new TransactionInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(TransactionObject transactionObject, String correlationId) {
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }

        }));

        btnReversalDisbursement.setOnClickListener(v-> {
            SDKManager.getInstance().reversal("REF-1633580365289", reversalObject, new RequestStateInterface() {
                @Override
                public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                    txtResponse.setText(new Gson().toJson(requestStateObject));
                    correlationId = correlationID;
                    Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
                }

                @Override
                public void onRequestStateFailure(GSMAError gsmaError) {
                    txtResponse.setText(new Gson().toJson(gsmaError));
                    Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
                }

                @Override
                public void onValidationError(ErrorObject errorObject) {
                    Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                    Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                }
            });
        });

        //balance of organisation
        btnBalanceDisbursement.setOnClickListener(v -> {
            SDKManager.getInstance().getBalance("1", new BalanceInterface() {
                @Override
                public void onValidationError(ErrorObject errorObject) {
                    Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                    Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                }

                @Override
                public void onBalanceSuccess(Balance balance, String correlationID) {
                    txtResponse.setText(new Gson().toJson(balance));
                    Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
                }

                @Override
                public void onBalanceFailure(GSMAError gsmaError) {
                    txtResponse.setText(new Gson().toJson(gsmaError));
                    Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
                }
            });
        });

        //Get the batch details

        btnRetrieveBatch.setOnClickListener(v -> {
            SDKManager.getInstance().retrieveBatchTransaction("REF-1635765084301", new BatchTransactionItemInterface() {
                @Override
                public void batchTransactionSuccess(BatchTransactionItem batchTransactionItem, String correlationId) {
                    txtResponse.setText(new Gson().toJson(batchTransactionItem));
                    Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(batchTransactionItem));
                }

                @Override
                public void onTransactionFailure(GSMAError gsmaError) {
                    txtResponse.setText(new Gson().toJson(gsmaError));
                    Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
                }

                @Override
                public void onValidationError(ErrorObject errorObject) {
                    Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                    Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                }
            });
        });


         //Retrieve a list of transaction of a particular account
        btnRetrieveTransactionDisbursement.setOnClickListener(v -> {
            SDKManager.getInstance().retrieveTransaction("2000", 0, 5, new RetrieveTransactionInterface() {
                @Override
                public void onValidationError(ErrorObject errorObject) {
                    Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                    Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                }

                @Override
                public void onRetrieveTransactionSuccess(Transaction transaction, String correlationID) {
                    txtResponse.setText(new Gson().toJson(transaction));
                    correlationId = correlationID;
                    Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
                }

                @Override
                public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                    txtResponse.setText(new Gson().toJson(gsmaError));
                    Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
                }
            });
        });

        //Bulk disbursement using polling method-view transaction
        btnBulkDisbursement.setOnClickListener(v -> SDKManager.getInstance().bulkTransaction(bulkTransactionObject, new RequestStateInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationId) {
                txtResponse.setText(new Gson().toJson(requestStateObject));
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

        }));

        //Bulk disbursement rejection
        btnBatchRejections.setOnClickListener(v -> SDKManager.getInstance().retrieveBatchRejections("REF-1635765084301", new BatchRejectionInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void batchTransactionRejections(BatchTransactionRejection batchTransactionRejection, String correlationId) {
                txtResponse.setText(new Gson().toJson(batchTransactionRejection));
                Log.d(SUCCESS, "batchTransactionRejections: " + new Gson().toJson(batchTransactionRejection));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }
        }));

        //Bulk disbursement completion
        btnBatchCompletions.setOnClickListener(v -> SDKManager.getInstance().retrieveBatchCompletions("REF-1635765084301", new BatchCompletionInterface() {

            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void batchTransactionCompleted(BatchTransactionCompletion batchTransactionCompletion, String correlationId) {
                txtResponse.setText(new Gson().toJson(batchTransactionCompletion));
                Log.d(SUCCESS, "batchTransactionCompleted: " + new Gson().toJson(batchTransactionCompletion));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }
        }));

        btnBatchUpdate.setOnClickListener(v -> SDKManager.getInstance().updateBatch("REF-1635765084301",batchArrayList, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(DisbursementActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationId) {
                txtResponse.setText(new Gson().toJson(requestStateObject));
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

        }));



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
        batchObject=new Batch();
        batchObject.setOp("replace");
        batchObject.setPath("/status");
        batchObject.setValue("approved");
        batchArrayList=new ArrayList<>();
        batchArrayList.add(batchObject);
    }



    private void createBulkTransactionObject() {
        bulkTransactionObject = new BulkTransactionObject();

        ArrayList<TransactionItem> transactionItems = new ArrayList<>();
        TransactionItem transactionItem = new TransactionItem();
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
}
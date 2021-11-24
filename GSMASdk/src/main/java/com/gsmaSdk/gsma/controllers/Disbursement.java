package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.BatchCompletionInterface;
import com.gsmaSdk.gsma.interfaces.BatchRejectionInterface;
import com.gsmaSdk.gsma.interfaces.BatchTransactionItemInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.Batch;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionCompletion;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionItem;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionRejection;
import com.gsmaSdk.gsma.models.transaction.BulkTransactionObject;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;

public class Disbursement extends Common{




    /**
     * Intiate Payment Disbursment - Intiate disbursement transaction
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for recieving response of transaction
     * @param transactionRequest Transaction Object containing details required for initiating the transaction
     */

    public void createDisbursementTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (transactionRequest == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
            return;
        } else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().initiatePayment(uuid, notificationMethod, callbackUrl, "disbursement", transactionRequest, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse, uuid);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            requestStateInterface.onRequestStateFailure(errorDetails);
                        }
                    }
            );

        }
    }


    /**
     * Bulk Disbursement - Organisations can make bulk disbursements to customers
     *
     * @param notificationMethod    The enumerated datatype to determine polling or callback
     * @param callbackUrl           The server URl for recieving response of a transaction
     * @param bulkTransactionObject Transaction Object containing details required for initiating the bulk transaction
     */
    public void createBatchTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull BulkTransactionObject bulkTransactionObject, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (bulkTransactionObject == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().bulkTransaction(uuid, notificationMethod, callbackUrl, bulkTransactionObject, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse, uuid);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            requestStateInterface.onRequestStateFailure(errorDetails);
                        }
                    }
            );

        }
    }


    /**
     * Retrieve rejected transactions from a batch transaction
     *
     * @param batchId Unique identifier for identifying a batch transaction
     */

    public void viewBatchRejections(String batchId, @NonNull BatchRejectionInterface batchRejectionInterface) {
        if (!Utils.isOnline()) {
            batchRejectionInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (batchId.isEmpty()) {
            batchRejectionInterface.onValidationError(Utils.setError(6));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveBatchRejections(uuid, batchId, new APIRequestCallback<BatchTransactionRejection>() {
                @Override
                public void onSuccess(int responseCode, BatchTransactionRejection serializedResponse) {

                    batchRejectionInterface.batchTransactionRejections(serializedResponse, uuid);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    batchRejectionInterface.onTransactionFailure(errorDetails);
                }
            });

        }
    }

    /**
     * Retrieve completed transactions from a batch transaction
     *
     * @param batchId Unique identifier for identifying a batch transaction
     */

    public void viewBatchCompletions(String batchId, @NonNull BatchCompletionInterface batchCompletionInterface) {
        if (!Utils.isOnline()) {
            batchCompletionInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (batchId.isEmpty()) {
            batchCompletionInterface.onValidationError(Utils.setError(6));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveBatchCompletions(uuid, batchId, new APIRequestCallback<BatchTransactionCompletion>() {

                @Override
                public void onSuccess(int responseCode, BatchTransactionCompletion serializedResponse) {
                    batchCompletionInterface.batchTransactionCompleted(serializedResponse, uuid);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    batchCompletionInterface.onTransactionFailure(errorDetails);
                }
            });

        }
    }


    /**
     * Update a batch transaction
     *
     * @param batchId        Unique identifier for identifying a batch transaction
     * @param batchArrayList list containing required details for updating a batch disbursement
     */

    public void updateBatchTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, String batchId, @NonNull ArrayList<Batch> batchArrayList, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (batchId == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
            return;
        } else if (batchId.isEmpty()) {
            requestStateInterface.onValidationError(Utils.setError(1));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().updateBatch(uuid, notificationMethod, callbackUrl, batchId, batchArrayList, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse, uuid);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            requestStateInterface.onRequestStateFailure(errorDetails);
                        }
                    }
            );

        }
    }

    /**
     * Retrieve a batch transaction
     *
     * @param batchId Unique identifier for identifying a batch transaction
     */
    public void viewBatchTransaction(String batchId, @NonNull BatchTransactionItemInterface batchTransactionItemInterface) {

        if (!Utils.isOnline()) {
            batchTransactionItemInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (batchId == null) {
            batchTransactionItemInterface.onValidationError(Utils.setError(3));
            return;
        } else if (batchId.isEmpty()) {
            batchTransactionItemInterface.onValidationError(Utils.setError(3));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveBatch(uuid, batchId, new APIRequestCallback<BatchTransactionItem>() {
                        @Override
                        public void onSuccess(int responseCode, BatchTransactionItem serializedResponse) {
                            batchTransactionItemInterface.batchTransactionSuccess(serializedResponse, uuid);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            batchTransactionItemInterface.onTransactionFailure(errorDetails);
                        }
                    }
            );

        }
    }


    public void createReversal(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull String referenceId, @NonNull ReversalObject reversal, @NonNull RequestStateInterface requestStateInterface) {
        ReversalTransaction.getInstance().createReversal(notificationMethod,callbackUrl,referenceId,reversal,requestStateInterface);

    }
    public void viewAccountTransactions(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull int offset, @NonNull int limit, @NonNull RetrieveTransactionInterface retrieveTransactionInterface) {
        AccountTransactions.getInstance().viewAccountTransactions(identifierArrayList,offset,limit,retrieveTransactionInterface);

    }

    public void viewAccountBalance(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull BalanceInterface balanceInterface) {
        AccountBalance.getInstance().viewAccountBalance(identifierArrayList,balanceInterface);

    }




}

package com.gsmaSdk.gsma.controllers;

import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.BatchCompletionInterface;
import com.gsmaSdk.gsma.interfaces.BatchRejectionInterface;
import com.gsmaSdk.gsma.interfaces.BatchTransactionItemInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.Batch;
import com.gsmaSdk.gsma.models.transaction.batchcompletion.BatchCompletion;
import com.gsmaSdk.gsma.models.transaction.batchrejection.BatchRejection;
import com.gsmaSdk.gsma.models.transaction.batchtransaction.BatchTransaction;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;

@SuppressWarnings("ALL")
public class Disbursement extends Common {

    /**
     * Initiate Payment Disbursement - Initiate disbursement transaction
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param transactionRequest Transaction Object containing details required for initiating the transaction
     */
    public void createDisbursementTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull Transaction transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (transactionRequest == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
        } else {
            String uuid = Utils.generateUUID();
            requestStateInterface.getCorrelationId(uuid);
            GSMAApi.getInstance().initiatePayment(uuid, notificationMethod, callbackUrl, "disbursement", transactionRequest, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse);
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
     * @param callbackUrl           The server URl for receiving response of a transaction
     * @param bulkTransactionObject Transaction Object containing details required for initiating the bulk transaction
     */
    public void createBatchTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull BatchTransaction bulkTransactionObject, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (bulkTransactionObject == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
        } else {
            String uuid = Utils.generateUUID();
            requestStateInterface.getCorrelationId(uuid);
            GSMAApi.getInstance().bulkTransaction(uuid, notificationMethod, callbackUrl, bulkTransactionObject, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse);
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
            GSMAApi.getInstance().retrieveBatchRejections(uuid, batchId, new APIRequestCallback<BatchRejection>() {
                @Override
                public void onSuccess(int responseCode, BatchRejection serializedResponse) {

                    batchRejectionInterface.batchTransactionRejections(serializedResponse);
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
            GSMAApi.getInstance().retrieveBatchCompletions(uuid, batchId, new APIRequestCallback<BatchCompletion>() {

                @Override
                public void onSuccess(int responseCode, BatchCompletion serializedResponse) {
                    batchCompletionInterface.batchTransactionCompleted(serializedResponse);
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
     * @param batchArrayList List contains required details for updating a batch disbursement
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
            requestStateInterface.getCorrelationId(uuid);
            GSMAApi.getInstance().updateBatch(uuid, notificationMethod, callbackUrl, batchId, batchArrayList, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse);
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
     * View Batch Transaction- Retrieve a batch transaction
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
            GSMAApi.getInstance().retrieveBatch(uuid, batchId, new APIRequestCallback<BatchTransaction>() {
                        @Override
                        public void onSuccess(int responseCode, BatchTransaction serializedResponse) {
                            batchTransactionItemInterface.batchTransactionSuccess(serializedResponse);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            batchTransactionItemInterface.onTransactionFailure(errorDetails);
                        }
                    }
            );

        }
    }


    /**
     * Reversal - provides transaction reversal
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for recieving response of transaction
     * @param referenceId        Reference id of a previous transaction
     * @param reversal           Reversal Object containing the type of the transaction
     */
    public void createReversal(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull String referenceId, @NonNull Reversal reversal, @NonNull RequestStateInterface requestStateInterface) {
        ReversalTransaction.getInstance().createReversal(notificationMethod, callbackUrl, referenceId, reversal, requestStateInterface);

    }

    /**
     * * View Account Transaction - Retrieve a set of transactions
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param offset              offset required for pagination
     * @param limit               limit set for receiving records per request
     */
    public void viewAccountTransactions(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull int offset, @NonNull int limit, @NonNull RetrieveTransactionInterface retrieveTransactionInterface) {
        AccountTransactionsController.getInstance().viewAccountTransactions(identifierArrayList, offset, limit, retrieveTransactionInterface);

    }

    /**
     * View Account Balance-Get the balance of a particular account
     *
     * @param identifierArrayList - List of identifiers to identify a particular account
     */

    public void viewAccountBalance(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull BalanceInterface balanceInterface) {
        AccountBalanceController.getInstance().viewAccountBalance(identifierArrayList, balanceInterface);

    }


}

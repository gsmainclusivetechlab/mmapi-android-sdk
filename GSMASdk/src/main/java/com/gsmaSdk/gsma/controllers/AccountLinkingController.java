package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.AccountLinkInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.models.account.Link;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class AccountLinkingController extends  Common {

    /**
     * Reversal - provides transaction reversal
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param referenceId        Reference id of a previous transaction
     * @param reversal           Reversal Object containing the type of the transaction
     */

    public void createReversal(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull String referenceId, @NonNull Reversal reversal, @NonNull RequestStateInterface requestStateInterface) {
        ReversalTransaction.getInstance().createReversal(notificationMethod, callbackUrl, referenceId, reversal, requestStateInterface);

    }

    /**
     * View Account Balance-Get the balance of a particular account
     *
     * @param identifierArrayList - List of identifiers to identify a particular account
     */
    public void viewAccountBalance(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull BalanceInterface balanceInterface) {
        AccountBalanceController.getInstance().viewAccountBalance(identifierArrayList, balanceInterface);

    }

    /**
     * Retrieve a transaction
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param offset              offset required for pagination
     * @param limit               limit set for receiving records per request
     */
    public void viewAccountTransactions(@NonNull ArrayList<Identifier> identifierArrayList, int offset, int limit, @NonNull RetrieveTransactionInterface retrieveTransactionInterface) {
        AccountTransactionsController.getInstance().viewAccountTransactions(identifierArrayList, offset, limit, retrieveTransactionInterface);

    }

    /**
     * Initiate Payment Disbursement - Initiate disbursement transaction
     *
     * @param notificationMethod   The enumerated datatype to determine polling or callback
     * @param callbackUrl          The server URl for receiving response of transaction
     * @param identifierArrayList  account identifiers of the user
     * @param accountLinkingObject Account Linking Object containing details required for initiating the account linking
     */
    public void createAccountLinking(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull ArrayList<Identifier> identifierArrayList, Link accountLinkingObject, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (accountLinkingObject == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
        } else {
            String uuid = Utils.generateUUID();
            requestStateInterface.getCorrelationId(uuid);
            GSMAApi.getInstance().createAccountLinking(uuid, notificationMethod, callbackUrl, Utils.getIdentifiers(identifierArrayList), accountLinkingObject, new APIRequestCallback<RequestStateObject>() {
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
     * Create Debit Mandate-Create Debit Mandate for recurring Payment
     *
     * @param identifierArrayList  account identifiers of the user
     * @param linkReference Link Reference
     */
    @SuppressWarnings({"ConstantConditions", "UnnecessaryReturnStatement"})
    public void viewAccountLink(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull String linkReference, @NonNull AccountLinkInterface accountLinkInterface) {
        if (!Utils.isOnline()) {
            accountLinkInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (linkReference == null) {
            accountLinkInterface.onValidationError(Utils.setError(3));
            return;
        }
        //noinspection ConstantConditions
        if (identifierArrayList == null) {
            accountLinkInterface.onValidationError(Utils.setError(1));
            return;

        } else if (linkReference.isEmpty()) {
            accountLinkInterface.onValidationError(Utils.setError(3));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewAccountLink(uuid, Utils.getIdentifiers(identifierArrayList), linkReference, new APIRequestCallback<Link>() {
                @Override
                public void onSuccess(int responseCode, Link serializedResponse) {
                    accountLinkInterface.onAccountLinkSuccess(serializedResponse);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    accountLinkInterface.onAccountLinkFailure(errorDetails);
                }
            });

        } else {
            accountLinkInterface.onValidationError(Utils.setError(1));
        }
    }


    /**
     * Transfer Transaction - Create a Transfer Transaction
     *
     * @param notificationMethod    The enumerated datatype to determine polling or callback
     * @param callbackUrl           The server URl for receiving response of transaction
     * @param transactionRequest    the request object-P2P Transfers
     * @param requestStateInterface callback for request state object
     */
    public void createTransferTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull Transaction transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        TransferTransaction.getInstance().createTransferTransaction(notificationMethod, callbackUrl, transactionRequest, requestStateInterface);
    }

}

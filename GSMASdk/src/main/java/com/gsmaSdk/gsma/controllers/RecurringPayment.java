package com.gsmaSdk.gsma.controllers;

import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.DebitMandateInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.models.account.DebitMandate;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.reversal.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.transactions.TransactionRequest;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;

@SuppressWarnings("ALL")
public class RecurringPayment extends Common {

    /**
     * Create Debit Mandate-Create Debit Mandate for recurring Payment
     *
     * @param notificationMethod  The enumerated datatype to determine polling or callback
     * @param callbackUrl         The server URl for receiving response of transaction
     * @param identifierArrayList account identifiers of the user
     * @param debitMandateRequest Request object for creating debit mandate
     */
    public void createAccountDebitMandate(@NonNull NotificationMethod notificationMethod, @NonNull String callbackUrl, @NonNull ArrayList<Identifier> identifierArrayList, DebitMandate debitMandateRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (debitMandateRequest == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
            return;
        }
        if (identifierArrayList == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
            return;
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            requestStateInterface.getCorrelationId(uuid);
            GSMAApi.getInstance().createAccountDebitMandate(uuid, notificationMethod, callbackUrl, Utils.getIdentifiers(identifierArrayList), debitMandateRequest, new APIRequestCallback<RequestStateObject>() {
                @Override
                public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                    requestStateInterface.onRequestStateSuccess(serializedResponse);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    requestStateInterface.onRequestStateFailure(errorDetails);
                }
            });

        } else {
            requestStateInterface.onValidationError(Utils.setError(1));
        }
    }

    /**
     * Create Debit Mandate-Create Debit Mandate for recurring Payment
     *
     * @param identifierArrayList  account identifiers of the user
     * @param transactionReference Reference of Debit mandate Request
     */
    public void viewAccountDebitMandate(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull String transactionReference, @NonNull DebitMandateInterface debitMandateInterface) {
        if (!Utils.isOnline()) {
            debitMandateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (transactionReference == null) {
            debitMandateInterface.onValidationError(Utils.setError(3));
            return;
        }
        if (identifierArrayList == null) {
            debitMandateInterface.onValidationError(Utils.setError(1));
            return;

        } else if (transactionReference.isEmpty()) {
            debitMandateInterface.onValidationError(Utils.setError(3));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewAccountDebitMandate(uuid, Utils.getIdentifiers(identifierArrayList), transactionReference, new APIRequestCallback<DebitMandate>() {
                @Override
                public void onSuccess(int responseCode, DebitMandate serializedResponse) {
                    debitMandateInterface.onDebitMandateSuccess(serializedResponse);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    debitMandateInterface.onDebitMandateFailure(errorDetails);
                }
            });

        } else {
            debitMandateInterface.onValidationError(Utils.setError(1));
        }
    }

    /**
     * Initiate Payment - Initiate merchant pay
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param transactionRequest Transaction Object containing details required for initiating the transaction
     */
    public void createMerchantTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        MerchantTransaction.getInstance().createMerchantTransaction(notificationMethod,callbackUrl,transactionRequest,requestStateInterface);
    }

    /**
     * Refund - provides refund to a given account
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param transactionRequest Transaction Object containing details required for initiating the refund process
     */
    public void createRefundTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        RefundTransaction.getInstance().createRefundTransaction(notificationMethod,callbackUrl,transactionRequest,requestStateInterface);
    }

    /**
     * Reversal - provides transaction reversal
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for recieving response of transaction
     * @param referenceId        Reference id of a previous transaction
     * @param reversal           Reversal Object containing the type of the transaction
     */

    public void createReversal(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull String referenceId, @NonNull ReversalObject reversal, @NonNull RequestStateInterface requestStateInterface) {
        ReversalTransaction.getInstance().createReversal(notificationMethod,callbackUrl,referenceId,reversal,requestStateInterface);

    }

    /**
     * View Account Balance-Get the balance of a particular account
     *
     * @param identifierArrayList - List of identifiers to identify a particular account
     */
    public void viewAccountBalance(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull BalanceInterface balanceInterface) {
        AccountBalance.getInstance().viewAccountBalance(identifierArrayList,balanceInterface);

    }

    /**
     * Retrieve a transaction
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param offset              offset required for pagination
     * @param limit               limit set for receiving records per request
     */
    public void viewAccountTransactions(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull int offset, @NonNull int limit, @NonNull RetrieveTransactionInterface retrieveTransactionInterface) {
        AccountTransactions.getInstance().viewAccountTransactions(identifierArrayList,offset,limit,retrieveTransactionInterface);

    }
}

package com.gsmaSdk.gsma.controllers;

import com.gsmaSdk.gsma.interfaces.AccountLinkInterface;
import com.gsmaSdk.gsma.interfaces.DebitMandateInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.models.AccountLinkingObject;
import com.gsmaSdk.gsma.models.AccountLinks;
import com.gsmaSdk.gsma.models.DebitMandate;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class AccountLinking extends Common {

    /**
     * Initiate Payment Disbursement - Initiate disbursement transaction
     *
     * @param notificationMethod   The enumerated datatype to determine polling or callback
     * @param callbackUrl          The server URl for receiving response of transaction
     * @param identifierArrayList  account identifiers of the user
     * @param accountLinkingObject Account Linking Object containing details required for initiating the account linking
     */
    public void createAccountLinking(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull ArrayList<Identifier> identifierArrayList, AccountLinkingObject accountLinkingObject, @NonNull RequestStateInterface requestStateInterface) {
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
    public void viewAccountLink(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull String linkReference, @NonNull AccountLinkInterface accountLinkInterface) {
        if (!Utils.isOnline()) {
            accountLinkInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (linkReference == null) {
            accountLinkInterface.onValidationError(Utils.setError(3));
            return;
        }
        if (identifierArrayList == null) {
            accountLinkInterface.onValidationError(Utils.setError(1));
            return;

        } else if (linkReference.isEmpty()) {
            accountLinkInterface.onValidationError(Utils.setError(3));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewAccountLink(uuid, Utils.getIdentifiers(identifierArrayList), linkReference, new APIRequestCallback<AccountLinks>() {
                @Override
                public void onSuccess(int responseCode, AccountLinks serializedResponse) {
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
    public void createTransferTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        TransferTransaction.getInstance().createTransferTransaction(notificationMethod, callbackUrl, transactionRequest, requestStateInterface);
    }
}

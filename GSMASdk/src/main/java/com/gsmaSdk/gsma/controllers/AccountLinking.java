package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;

import java.util.ArrayList;

public class AccountLinking extends  Common{

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
    public void viewAccountTransactions(@NonNull ArrayList<Identifier> identifierArrayList, int offset, int limit, @NonNull RetrieveTransactionInterface retrieveTransactionInterface) {
        AccountTransactions.getInstance().viewAccountTransactions(identifierArrayList,offset,limit,retrieveTransactionInterface);

    }



}

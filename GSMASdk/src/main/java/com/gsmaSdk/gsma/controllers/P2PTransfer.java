package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.AccountHolderInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.transaction.reversal.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.transactions.TransactionRequest;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class P2PTransfer extends Common{

    /**
     * View Account Name-Get name of  particular account holder
     *
     * @param identifierArrayList account identifiers of the user
     */
    public void viewAccountName(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull AccountHolderInterface accountHolderInterface) {
       AccountName.getInstance().viewAccountName(identifierArrayList,accountHolderInterface);
    }

    /**
     * Quotation - Quotation request for transaction
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param transactionRequest    the request object-International Transfers
     * @param requestStateInterface callback for request state object
     */

    public void createQuotation(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        Quotation.getInstance().createQuotation(notificationMethod,callbackUrl,transactionRequest,requestStateInterface);
    }

    /**
     * View a quotation details
     *
     * @param quotationReference - Quotation reference obtained from callback of request quotation API
     *
     */
    public void viewQuotation(@NonNull String quotationReference, @NonNull TransactionInterface transactionInterface) {
        Quotation.getInstance().viewQuotation(quotationReference,transactionInterface);
    }


    /**
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param transactionRequest    the request object-P2P Transfers
     * @param requestStateInterface callback for request state object
     */
    public void createTransferTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        TransferTransaction.getInstance().createTransferTransaction(notificationMethod,callbackUrl,transactionRequest,requestStateInterface);
    }



    /**
     * Reversal - provides transaction reversal
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
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

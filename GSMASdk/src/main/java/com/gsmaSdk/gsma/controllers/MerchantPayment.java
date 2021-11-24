package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.AuthorisationCodeInterface;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeItemInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeItem;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeRequest;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.GetLink;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;

public class MerchantPayment extends Common{


    /**
     * View Account Balance-Get the balance of a particular acccount
     *
     * @param identifierArrayList - List of identifiers to identify a particular account
     */
    public void viewAccountBalance(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull BalanceInterface balanceInterface) {
       AccountBalance.getInstance().viewAccountBalance(identifierArrayList,balanceInterface);

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
     * Retrieve a transaction
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param offset              offset required for pagination
     * @param limit               limit set for receiving records per request
     */
    public void viewAccountTransactions(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull int offset, @NonNull int limit, @NonNull RetrieveTransactionInterface retrieveTransactionInterface) {
       AccountTransactions.getInstance().viewAccountTransactions(identifierArrayList,offset,limit,retrieveTransactionInterface);

    }

    /**
     * Obtain Authorisation code for a transaction
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param codeRequest  An Object containing required details for getting the authorisation code
     */
    public void createAuthorisationCode(ArrayList<Identifier> identifierArrayList, @NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull AuthorisationCodeRequest codeRequest, @NonNull RequestStateInterface requestStateInterface) {
       AuthorisationCode.getInstance().createAuthorisationCode(identifierArrayList,notificationMethod,callbackUrl,codeRequest,requestStateInterface);
    }

    /**
     * Retrieve Missing Code - To retrieve a missing Authorisation code
     *
     * @param correlationId UUID that enables the client to correlate the API request with the resource created/updated by the provider
     */

    public void viewAuthorisationCodeResponse(String correlationId, @NonNull AuthorisationCodeInterface authorisationCodeInterface) {
         AuthorisationCode.getInstance().viewAuthorisationCodeResponse(correlationId,authorisationCodeInterface);
    }

    /**
     * View Authorization Code
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param authorisationCode Created Authorisation Code
     *
     */
    public void viewAuthorisationCode(@NonNull ArrayList<Identifier> identifierArrayList, String authorisationCode, AuthorisationCodeItemInterface authorisationCodeInterface) {
      AuthorisationCode.getInstance().viewAuthorisationCode(identifierArrayList,authorisationCode,authorisationCodeInterface);

    }



}

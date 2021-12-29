package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.AuthorisationCodeInterface;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeItemInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;

import java.util.ArrayList;

public class AgentServiceController extends Common {

    /**
     * Initiate Payment - Initiate Bill Payment
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param transactionRequest Transaction Object containing details required for initiating the transaction
     */
    public void createWithdrawalTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull Transaction transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        MerchantTransaction.getInstance().createMerchantTransaction(notificationMethod, callbackUrl, transactionRequest,"withdrawal", requestStateInterface);
    }



    /**
     * Obtain Authorisation code for a transaction
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param codeRequest  An Object containing required details for getting the authorisation code
     */
    public void createAuthorisationCode(ArrayList<Identifier> identifierArrayList, @NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull AuthorisationCode codeRequest, @NonNull RequestStateInterface requestStateInterface) {
        AuthorisationCodeController.getInstance().createAuthorisationCode(identifierArrayList,notificationMethod,callbackUrl,codeRequest,requestStateInterface);
    }

    /**
     * View Authorization Code
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param authorisationCode Created Authorisation Code
     *
     */
    public void viewAuthorisationCode(@NonNull ArrayList<Identifier> identifierArrayList, String authorisationCode, AuthorisationCodeItemInterface authorisationCodeInterface) {
        AuthorisationCodeController.getInstance().viewAuthorisationCode(identifierArrayList,authorisationCode,authorisationCodeInterface);

    }

}

package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;

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



}

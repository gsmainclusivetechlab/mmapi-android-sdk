package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;


public class AccountTransactionsController {

    /**
     * View Account Transaction - Retrieve a set of transactions
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param transactionFilter   Filter for transaction
     */

    public void viewAccountTransactions(ArrayList<Identifier> identifierArrayList, TransactionFilter transactionFilter, @NonNull RetrieveTransactionInterface retrieveTransactionInterface) {
        if (identifierArrayList == null) {
            retrieveTransactionInterface.onValidationError(Utils.setError(1));
            return;
        }  else if (identifierArrayList.size() == 0) {
            retrieveTransactionInterface.onValidationError(Utils.setError(1));
        }
        else if (!Utils.isOnline()) {
            retrieveTransactionInterface.onValidationError(Utils.setError(0));
        }
        else {
            String uuid = Utils.generateUUID();
            HashMap<String, String> params = Utils.getHashMapFromObject(transactionFilter);
            GSMAApi.getInstance().retrieveTransaction(uuid, Utils.getIdentifiers(identifierArrayList), params, new APIRequestCallback<Transactions>() {
                        @Override
                        public void onSuccess(int responseCode, Transactions serializedResponse) {
                            retrieveTransactionInterface.onRetrieveTransactionSuccess(serializedResponse);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            retrieveTransactionInterface.onRetrieveTransactionFailure(errorDetails);
                        }
                    }
            );
        }

    }

    public static AccountTransactionsController getInstance() {
        return AccountTransactionsController.SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final AccountTransactionsController INSTANCE = new AccountTransactionsController();
    }


}

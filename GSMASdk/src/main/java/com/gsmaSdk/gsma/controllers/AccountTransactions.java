package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;


public class AccountTransactions {

    /**
     *View Account Transaction - Retrieve a set of transactions
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param offset              offset required for pagination
     * @param limit               limit set for receiving records per request
     */

    public void viewAccountTransactions(ArrayList<Identifier> identifierArrayList, int offset, int limit, @NonNull RetrieveTransactionInterface retrieveTransactionInterface) {
        if (!Utils.isOnline()) {
            retrieveTransactionInterface.onValidationError(Utils.setError(0));
        } else if (identifierArrayList == null) {
            retrieveTransactionInterface.onValidationError(Utils.setError(1));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveTransaction(uuid, Utils.getIdentifiers(identifierArrayList), offset, limit, new APIRequestCallback<Transaction>() {
                        @Override
                        public void onSuccess(int responseCode, Transaction serializedResponse) {
                            retrieveTransactionInterface.onRetrieveTransactionSuccess(serializedResponse);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            retrieveTransactionInterface.onRetrieveTransactionFailure(errorDetails);
                        }
                    }
            );
        } else {
            retrieveTransactionInterface.onValidationError(Utils.setError(1));
        }


    }

    public static AccountTransactions getInstance() {
        return AccountTransactions.SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final AccountTransactions INSTANCE = new AccountTransactions();
    }



}

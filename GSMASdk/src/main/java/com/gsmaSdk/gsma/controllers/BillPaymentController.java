package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.RetrieveBillPaymentInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.bills.Bills;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class BillPaymentController extends Common{


    /**
     * View Account Transaction - Retrieve a set of transactions
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param transactionFilter Filter for transaction
     */

    public void viewAccountBills(ArrayList<Identifier> identifierArrayList, TransactionFilter transactionFilter, @NonNull RetrieveBillPaymentInterface retrieveBillPaymentInterface) {
        if (!Utils.isOnline()) {
            retrieveBillPaymentInterface.onValidationError(Utils.setError(0));
        } else if (identifierArrayList == null) {
            retrieveBillPaymentInterface.onValidationError(Utils.setError(1));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            HashMap<String, String> params = Utils.getHashMapFromObject(transactionFilter);
            GSMAApi.getInstance().viewAccountBills(uuid, Utils.getIdentifiers(identifierArrayList), params, new APIRequestCallback<Bills>() {
                        @Override
                        public void onSuccess(int responseCode, Bills serializedResponse) {
                            retrieveBillPaymentInterface.onRetrieveBillPaymentSuccess(serializedResponse);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            retrieveBillPaymentInterface.onRetrieveBillPaymentFailure(errorDetails);
                        }
                    }
            );
        } else {
            retrieveBillPaymentInterface.onValidationError(Utils.setError(1));
        }


    }

    public static BillPaymentController getInstance() {
        return BillPaymentController.SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final BillPaymentController INSTANCE = new BillPaymentController();
    }


}

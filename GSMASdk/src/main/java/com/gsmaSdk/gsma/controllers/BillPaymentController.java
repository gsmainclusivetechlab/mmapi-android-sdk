package com.gsmaSdk.gsma.controllers;

import com.gsmaSdk.gsma.interfaces.BillPaymentInterface;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.Link;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.bills.BillPayment;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;

public class BillPaymentController extends Common{

    /**
     * View bill payments for a specific account
     *
     * @param identifierArrayList  account identifiers of the user
     * @param billReference Link Reference
     */
    @SuppressWarnings({"ConstantConditions", "UnnecessaryReturnStatement"})
    public void viewBillPayment(@NonNull ArrayList<Identifier> identifierArrayList, TransactionFilter transactionFilter, @NonNull String billReference, @NonNull BillPaymentInterface billPaymentInterface) {
        if (!Utils.isOnline()) {
            billPaymentInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (billReference == null) {
            billPaymentInterface.onValidationError(Utils.setError(3));
            return;
        }
        //noinspection ConstantConditions
        if (identifierArrayList == null) {
            billPaymentInterface.onValidationError(Utils.setError(1));
            return;

        } else if (billReference.isEmpty()) {
            billPaymentInterface.onValidationError(Utils.setError(3));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            HashMap<String, String> params = Utils.getHashMapFromObject(transactionFilter);
            GSMAApi.getInstance().viewBillPayment(uuid, Utils.getIdentifiers(identifierArrayList),params, billReference, new APIRequestCallback<BillPayment>() {
                @Override
                public void onSuccess(int responseCode, BillPayment serializedResponse) {
                    billPaymentInterface.onBillPaymentSuccess(serializedResponse);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    billPaymentInterface.onBillPaymentFailure(errorDetails);
                }
            });

        } else {
            billPaymentInterface.onValidationError(Utils.setError(1));
        }
    }
}

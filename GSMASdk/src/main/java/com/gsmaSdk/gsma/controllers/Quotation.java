package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

@SuppressWarnings("ConstantConditions")
public class Quotation {



    /**
     * Quotation - Quotation request for transaction
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param transactionRequest    the request object-International Transfers
     * @param requestStateInterface callback for request state object
     */
    @SuppressWarnings("rawtypes")
    public void createQuotation(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }

        if (transactionRequest == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
        } else if (Utils.isOnline()) {
            String uuid = Utils.generateUUID();
            requestStateInterface.getCorrelationId(uuid);
            GSMAApi.getInstance().requestQuotation(uuid, notificationMethod, callbackUrl, transactionRequest, new APIRequestCallback<RequestStateObject>() {
                @Override
                public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                    requestStateInterface.onRequestStateSuccess(serializedResponse);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    requestStateInterface.onRequestStateFailure(errorDetails);
                }
            });
        }

    }



    /**
     * View a quotation details
     *
     * @param quotationReference - Quotation reference obtained from callback of request quotation API
     *
     */

    public void viewQuotation(@NonNull String quotationReference, @NonNull TransactionInterface transactionInterface) {
        if (!Utils.isOnline()) {
            transactionInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (quotationReference == null || quotationReference.isEmpty()) {
            transactionInterface.onValidationError(Utils.setError(10));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewQuotation(uuid, quotationReference, new APIRequestCallback<TransactionRequest>() {
                        @Override
                        public void onSuccess(int responseCode, TransactionRequest serializedResponse) {
                            transactionInterface.onTransactionSuccess(serializedResponse);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            transactionInterface.onTransactionFailure((errorDetails));
                        }
                    }
            );
        }
    }



    public static Quotation getInstance() {
        return Quotation.SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final Quotation INSTANCE = new Quotation();
    }


}

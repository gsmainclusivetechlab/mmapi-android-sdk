package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.quotation.Quotation;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

@SuppressWarnings({"ConstantConditions", "unused"})
public class QuotationController {



    /**
     * Quotation - Quotation request for transaction
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param quotationRequest    the quotation request object-International Transfers
     * @param requestStateInterface callback for request state object
     */
    @SuppressWarnings("rawtypes")
    public void createQuotation(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull Quotation quotationRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (quotationRequest == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
        }
        else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        }
        else {
            String uuid = Utils.generateUUID();
            requestStateInterface.getCorrelationId(uuid);
            GSMAApi.getInstance().requestQuotation(uuid, notificationMethod, callbackUrl, quotationRequest, new APIRequestCallback<RequestStateObject>() {
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

        if (quotationReference == null ) {
            transactionInterface.onValidationError(Utils.setError(10));
        }
        else if(quotationReference.isEmpty()){
            transactionInterface.onValidationError(Utils.setError(10));
        }
        else if (!Utils.isOnline()) {
            transactionInterface.onValidationError(Utils.setError(0));
        }
        else {
            String uuid = Utils.generateUUID();
            //noinspection unused,unused,unused,unused
            GSMAApi.getInstance().viewQuotation(uuid, quotationReference, new APIRequestCallback<Transaction>() {
                        @Override
                        public void onSuccess(int responseCode, Transaction serializedResponse) {
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



    public static QuotationController getInstance() {
        return QuotationController.SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final QuotationController INSTANCE = new QuotationController();
    }


}

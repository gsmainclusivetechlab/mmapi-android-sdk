package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

@SuppressWarnings("ConstantConditions")
public class TransferTransaction {


    /**
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param transactionRequest    the request object-P2P Transfers
     * @param requestStateInterface callback for request state object
     */

    public void createTransferTransaction(@SuppressWarnings("rawtypes") @NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull Transaction transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (transactionRequest == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
        }
        else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        }
        else {
            String uuid = Utils.generateUUID();
            requestStateInterface.getCorrelationId(uuid);
            GSMAApi.getInstance().initiatePayment(uuid, notificationMethod, callbackUrl, "transfer", transactionRequest, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            requestStateInterface.onRequestStateFailure(errorDetails);
                        }
                    }
            );
        }
    }



    public static TransferTransaction getInstance() {
        return TransferTransaction.SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final TransferTransaction INSTANCE = new TransferTransaction();
    }

}

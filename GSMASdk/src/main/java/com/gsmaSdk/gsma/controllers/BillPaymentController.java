package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.BillPaymentInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveBillPaymentInterface;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.bills.BillPay;
import com.gsmaSdk.gsma.models.bills.BillPayments;
import com.gsmaSdk.gsma.models.bills.Bills;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
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
            GSMAApi.getInstance().viewBillPayment(uuid, Utils.getIdentifiers(identifierArrayList),params, billReference, new APIRequestCallback<BillPayments>() {
                @Override
                public void onSuccess(int responseCode, BillPayments serializedResponse) {
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


    /**
     * Initiate Payment - Initiate Bill Payment
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param transactionRequest Transaction Object containing details required for initiating the transaction
     */
    public void createBillTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull Transaction transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        MerchantTransaction.getInstance().createMerchantTransaction(notificationMethod, callbackUrl, transactionRequest, requestStateInterface);
    }


    /**
     * Initiate Payment - Initiate Bill Payment
     *
     * @param notificationMethod  The enumerated datatype to determine polling or callback
     * @param callbackUrl         The server URl for receiving response of transaction
     * @param identifierArrayList List of account identifiers of a user
     * @param billPayment         billPayment Object containing details required for initiating the transaction
     * @param billReference       The Reference of the generated bill
     */
    public void createBillPayment(@NonNull NotificationMethod notificationMethod, @NonNull String callbackUrl, ArrayList<Identifier> identifierArrayList, @NonNull BillPay billPayment, @NonNull String billReference, @NonNull RequestStateInterface requestStateInterface) {
        if (billPayment == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
            return;
        }
        if (billReference == null) {
            requestStateInterface.onValidationError(Utils.setError(12));
            return;
        }
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else if (identifierArrayList == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            requestStateInterface.getCorrelationId(uuid);
            GSMAApi.getInstance().createBillPayment(uuid, notificationMethod, Utils.getIdentifiers(identifierArrayList), callbackUrl, billReference, billPayment, new APIRequestCallback<RequestStateObject>() {
                @Override
                public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                    requestStateInterface.onRequestStateSuccess(serializedResponse);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    requestStateInterface.onRequestStateFailure(errorDetails);
                }
            });

        } else {
            requestStateInterface.onValidationError(Utils.setError(1));
        }

    }

    public static BillPaymentController getInstance() {
        return BillPaymentController.SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final BillPaymentController INSTANCE = new BillPaymentController();
    }


}
package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.AccountHolderInterface;
import com.gsmaSdk.gsma.interfaces.DebitMandateInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.models.AccountHolderObject;
import com.gsmaSdk.gsma.models.DebitMandate;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;

public class RecurringPayment extends Common {

    /**
     * Create Debit Mandate-Create Debit Mandate for recurring Payment
     *
     * @param notificationMethod  The enumerated datatype to determine polling or callback
     * @param callbackUrl         The server URl for receiving response of transaction
     * @param identifierArrayList account identifiers of the user
     * @param debitMandateRequest Request object for creating debit mandate
     */
    public void createAccountDebitMandate(@NonNull NotificationMethod notificationMethod, @NonNull String callbackUrl, @NonNull ArrayList<Identifier> identifierArrayList, DebitMandate debitMandateRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (debitMandateRequest == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
            return;
        }
        if (identifierArrayList == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
            return;
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().createAccountDebitMandate(uuid, notificationMethod, callbackUrl, Utils.getIdentifiers(identifierArrayList), debitMandateRequest, new APIRequestCallback<RequestStateObject>() {
                @Override
                public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                    requestStateInterface.onRequestStateSuccess(serializedResponse, uuid);
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


    /**
     * Create Debit Mandate-Create Debit Mandate for recurring Payment
     *
     * @param identifierArrayList  account identifiers of the user
     * @param transactionReference Reference of Debit mandate Request
     */
    public void viewAccountDebitMandate(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull String transactionReference, @NonNull DebitMandateInterface debitMandateInterface) {
        if (!Utils.isOnline()) {
            debitMandateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (transactionReference == null) {
            debitMandateInterface.onValidationError(Utils.setError(3));
            return;
        }
        if (identifierArrayList == null) {
            debitMandateInterface.onValidationError(Utils.setError(1));
            return;

        } else if (transactionReference.isEmpty()) {
            debitMandateInterface.onValidationError(Utils.setError(3));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewAccountDebitMandate(uuid, Utils.getIdentifiers(identifierArrayList), transactionReference, new APIRequestCallback<DebitMandate>() {
                @Override
                public void onSuccess(int responseCode, DebitMandate serializedResponse) {
                    debitMandateInterface.onDebitMandateSuccess(serializedResponse);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    debitMandateInterface.onDebitMandateFailure(errorDetails);
                }
            });

        } else {
            debitMandateInterface.onValidationError(Utils.setError(1));
        }
    }


}

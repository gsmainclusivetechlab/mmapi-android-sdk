package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.GetLink;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.transactions.TransactionRequest;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

@SuppressWarnings("ConstantConditions")
public class Common {


    /**
     * Check Service Availability - To check whether the service is available
     */
    public void viewServiceAvailability(@NonNull ServiceAvailabilityInterface serviceAvailabilityInterface) {
        if (Utils.isOnline()) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().checkServiceAvailability(uuid, new APIRequestCallback<ServiceAvailability>() {
                        @Override
                        public void onSuccess(int responseCode, ServiceAvailability serializedResponse) {
                            serviceAvailabilityInterface.onServiceAvailabilitySuccess(serializedResponse);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            serviceAvailabilityInterface.onServiceAvailabilityFailure(errorDetails);
                        }
                    }
            );
        } else {
            serviceAvailabilityInterface.onValidationError(Utils.setError(0));
        }
    }


    /**
     * View a Transaction
     *
     * @param transactionReference Transaction Reference ID for identifying a transaction that is already initiated
     */

    public void viewTransaction(@NonNull String transactionReference, @NonNull TransactionInterface transactionInterface) {
        if (!Utils.isOnline()) {
            transactionInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (transactionReference == null) {
            transactionInterface.onValidationError(Utils.setError(3));
            return;
        }
        if (transactionReference.isEmpty()) {
            transactionInterface.onValidationError(Utils.setError(3));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewTransaction(uuid, transactionReference, new APIRequestCallback<TransactionRequest>() {
                        @Override
                        public void onSuccess(int responseCode, TransactionRequest serializedResponse) {
                            transactionInterface.onTransactionSuccess(serializedResponse);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            transactionInterface.onTransactionFailure(errorDetails);
                        }
                    }
            );
        }
    }


    /**
     * View Request State - returns the state of a transaction
     *
     * @param serverCorrelationId A unique identifier issued by the provider to enable the client to identify the RequestState resource on subsequent polling requests.
     */

    public void viewRequestState(@NonNull String serverCorrelationId, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (serverCorrelationId == null) {
            requestStateInterface.onValidationError(Utils.setError(2));
            return;
        }
        if (serverCorrelationId.isEmpty()) {
            requestStateInterface.onValidationError(Utils.setError(2));
        } else {
            String uuid = Utils.generateUUID();
            requestStateInterface.getCorrelationId(uuid);
            GSMAApi.getInstance().viewRequestState(uuid, serverCorrelationId, new APIRequestCallback<RequestStateObject>() {
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



    /**
     * Retrieve Missing Transaction Response
     *
     * @param correlationId UUID that enables the client to correlate the API request with the resource created/updated by the provider
     */

    public void viewResponse(String correlationId, @NonNull MissingResponseInterface missingTransactionInterface) {
        if (!Utils.isOnline()) {
            missingTransactionInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (correlationId == null) {
            missingTransactionInterface.onValidationError(Utils.setError(6));
            return;
        }
        if (correlationId.isEmpty()) {
            missingTransactionInterface.onValidationError(Utils.setError(6));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveMissingResponse(uuid, correlationId, new APIRequestCallback<GetLink>() {
                        @Override
                        public void onSuccess(int responseCode, GetLink serializedResponse) {
                            GSMAApi.getInstance().getMissingTransactions(serializedResponse.getLink(), new APIRequestCallback<MissingResponse>() {
                                @Override
                                public void onSuccess(int responseCode, MissingResponse serializedResponse) {
                                    missingTransactionInterface.onMissingResponseSuccess(serializedResponse);
                                }

                                @Override
                                public void onFailure(GSMAError errorDetails) {
                                    missingTransactionInterface.onMissingResponseFailure(errorDetails);

                                }
                            });

                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            missingTransactionInterface.onMissingResponseFailure(errorDetails);                        }
                    }
            );
        }
    }




}

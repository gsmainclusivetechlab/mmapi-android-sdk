package com.gsmaSdk.gsma.controllers;

import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.RefundInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;

import com.gsmaSdk.gsma.interfaces.ReversalInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TokenInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.Balance;
import com.gsmaSdk.gsma.models.Refund;
import com.gsmaSdk.gsma.models.CodeRequest;
import com.gsmaSdk.gsma.models.RequestStateObject;
import com.gsmaSdk.gsma.models.Reversal;
import com.gsmaSdk.gsma.models.ReversalObject;
import com.gsmaSdk.gsma.models.Token;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.TransactionObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import androidx.annotation.NonNull;

/**
 * Class for managing sdk function calls
 */

public class SDKManager {
    /**
     * Singleton getInstance method
     *
     * @return the instance
     */

    private SDKManager() {
    }

    public static SDKManager getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final SDKManager INSTANCE = new SDKManager();
    }

    /**
     * Get Token
     *
     * @param consumerKey    consumer key
     * @param consumerSecret consumer secret
     */
    public void getToken(@NonNull String consumerKey, @NonNull String consumerSecret, @NonNull TokenInterface tokenInterface) {
        String convertedKey = Utils.base64EncodeString(consumerKey + ":" + consumerSecret);
        convertedKey = "Basic " + convertedKey;
        GSMAApi.getInstance().createToken(convertedKey, new APIRequestCallback<Token>() {
                    @Override
                    public void onSuccess(int responseCode, Token serializedResponse) {
                        tokenInterface.onTokenSuccess(serializedResponse);
                    }

                    @Override
                    public void onFailure(GSMAError errorDetails) {
                        tokenInterface.onTokenFailure(errorDetails);
                    }
                }
        );
    }

    /**
     * Get Balance
     *
     * @param accountId account id
     */

    public void getBalance(@NonNull String accountId, @NonNull BalanceInterface balanceInterface) {
        if (accountId.isEmpty()) {
            balanceInterface.onValidationError(new ErrorObject("validation", "genericError", "Invalid accountId format"));
        } else {
            GSMAApi.getInstance().checkBalance(accountId, new APIRequestCallback<Balance>() {
                        @Override
                        public void onSuccess(int responseCode, Balance serializedResponse) {
                            balanceInterface.onBalanceSuccess(serializedResponse);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            balanceInterface.onBalanceFailure(errorDetails);
                        }
                    }
            );
        }
    }

    /**
     * Merchant Payment
     *
     * @param transactionRequest Transaction Object
     */

    public void merchantPay(@NonNull String transactionType, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {

        if (transactionRequest.getAmount().isEmpty() || transactionRequest.getCurrency().isEmpty()) {
            requestStateInterface.onValidationError(new ErrorObject("validation", "genericError", "Invalid accountId format"));
        } else {
            GSMAApi.getInstance().merchantPay(transactionType, transactionRequest, new APIRequestCallback<RequestStateObject>() {
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
     * View Transaction
     *
     * @param transactionReference Transaction Reference ID
     */

    public void viewTransaction(@NonNull String transactionReference, @NonNull TransactionInterface transactionInterface) {

        if (transactionReference != null) {
            if (transactionReference.isEmpty()) {
                transactionInterface.onValidationError(new ErrorObject("validation", "genericError", "Invalid transaction reference"));
            } else {
                GSMAApi.getInstance().viewTransaction(transactionReference, new APIRequestCallback<TransactionObject>() {
                            @Override
                            public void onSuccess(int responseCode, TransactionObject serializedResponse) {
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
    }

    /**
     * View Request State
     *
     * @param serverCorrelationId Server Correlation ID
     */

    public void viewRequestState(@NonNull String serverCorrelationId, @NonNull RequestStateInterface requestStateInterface) {
        if (serverCorrelationId != null) {
            if (serverCorrelationId.isEmpty()) {
                requestStateInterface.onValidationError(new ErrorObject("validation", "genericError", "Invalid server correlation id"));
            } else {
                GSMAApi.getInstance().viewRequestState(serverCorrelationId, new APIRequestCallback<RequestStateObject>() {
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
    }


    public void getRefundMerchantPay(@NonNull TransactionRequest transactionRequest, @NonNull RefundInterface refundInterface) {
        if (transactionRequest.getAmount().isEmpty() || transactionRequest.getCurrency().isEmpty()) {
            refundInterface.onValidationError(new ErrorObject("validation", "genericError", "Invalid accountId format"));
        } else {
            GSMAApi.getInstance().refund(transactionRequest, new APIRequestCallback<Refund>() {
                @Override
                public void onSuccess(int responseCode, Refund serializedResponse) {
                    refundInterface.onRefundSuccess(serializedResponse);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    refundInterface.onRefundFailure(errorDetails);
                }
            });
        }
    }

    public void reversal(@NonNull String referenceId, @NonNull ReversalObject reversal, @NonNull ReversalInterface reversalInterface) {

        if (referenceId.isEmpty()) {
            reversalInterface.onValidationError(new ErrorObject("validation", "genericError", "Invalid reference id"));
            return;
        }
        if (reversal == null) {
            reversalInterface.onValidationError(new ErrorObject("validation", "genericError", "Invalid json format"));
            return;
        }
        GSMAApi.getInstance().reversal(referenceId, reversal, new APIRequestCallback<Reversal>() {
            @Override
            public void onSuccess(int responseCode, Reversal serializedResponse) {
                reversalInterface.onReversalSuccess(serializedResponse);
            }

            @Override
            public void onFailure(GSMAError errorDetails) {
                reversalInterface.onReversalFailure(errorDetails);
            }
        });

    }

    public void obtainAuthorisationCode(@NonNull String accountId, @NonNull CodeRequest codeRequest, @NonNull RequestStateInterface requestStateInterface) {

        if (accountId.isEmpty()) {
            requestStateInterface.onValidationError(new ErrorObject("validation", "genericError", "Invalid accountId format"));
        } else {
            GSMAApi.getInstance().obtainAuthorisationCode(accountId, codeRequest, new APIRequestCallback<RequestStateObject>() {
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
     * View Request State
     *
     * @param accountId Account ID
     */

    public void retrieveTransaction(@NonNull String accountId, @NonNull int offset, @NonNull int limit, @NonNull RetrieveTransactionInterface retrieveTransactionInterface) {

        if (accountId.isEmpty()) {
            retrieveTransactionInterface.onValidationError(new ErrorObject("validation", "genericError", "Invalid account id"));
        } else {
            GSMAApi.getInstance().retrieveTransaction(accountId, offset, limit, new APIRequestCallback<Transaction>() {
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
        }
    }

    /**
     * Check Service Availability
     */

    public void checkServiceAvailability(@NonNull ServiceAvailabilityInterface serviceAvailabilityInterface) {
        GSMAApi.getInstance().checkServiceAvailability(new APIRequestCallback<ServiceAvailability>() {
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
    }

}




package com.gsmaSdk.gsma.controllers;

import com.gsmaSdk.gsma.interfaces.AuthorisationCodeInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.BatchCompletionInterface;
import com.gsmaSdk.gsma.interfaces.BatchRejectionInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TokenInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.Balance;
import com.gsmaSdk.gsma.models.RequestStateObject;
import com.gsmaSdk.gsma.models.ReversalObject;
import com.gsmaSdk.gsma.models.Token;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeRequest;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.GetLink;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionCompletion;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionRejection;
import com.gsmaSdk.gsma.models.transaction.BulkTransactionObject;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.models.transaction.TransactionObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import androidx.annotation.NonNull;

/**
 * Class for managing sdk function calls
 */

@SuppressWarnings("ALL")
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

        if (Utils.isOnline()) {
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
        } else {
            tokenInterface.onValidationError(Utils.setError(0));
        }
    }

    /**
     * Get Balance
     *
     * @param accountId account id
     */

    public void getBalance(@NonNull String accountId, @NonNull BalanceInterface balanceInterface) {
        if (accountId == null) {
            balanceInterface.onValidationError(Utils.setError(1));
            return;
        }
        if (accountId.isEmpty()) {
            balanceInterface.onValidationError(Utils.setError(1));
        } else if (!Utils.isOnline()) {
            balanceInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().checkBalance(uuid, accountId, new APIRequestCallback<Balance>() {
                        @Override
                        public void onSuccess(int responseCode, Balance serializedResponse) {
                            balanceInterface.onBalanceSuccess(serializedResponse, uuid);
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
        if (transactionType == null) {
            requestStateInterface.onValidationError(Utils.setError(7));
            return;
        }
        if (transactionType.isEmpty()) {
            requestStateInterface.onValidationError(Utils.setError(7));
        } else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().merchantPay(uuid, transactionType, transactionRequest, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse, uuid);
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

        if (transactionInterface == null) {
            transactionInterface.onValidationError(Utils.setError(3));
            return;
        }
        if (transactionReference.isEmpty()) {
            transactionInterface.onValidationError(Utils.setError(3));
        } else if (!Utils.isOnline()) {
            transactionInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewTransaction(uuid, transactionReference, new APIRequestCallback<TransactionObject>() {
                        @Override
                        public void onSuccess(int responseCode, TransactionObject serializedResponse) {
                            transactionInterface.onTransactionSuccess(serializedResponse, uuid);
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
     * View Request State
     *
     * @param serverCorrelationId Server Correlation ID
     */

    public void viewRequestState(@NonNull String serverCorrelationId, @NonNull RequestStateInterface requestStateInterface) {

        if (serverCorrelationId == null) {
            requestStateInterface.onValidationError(Utils.setError(2));
            return;
        }
        if (serverCorrelationId.isEmpty()) {
            requestStateInterface.onValidationError(Utils.setError(2));
        } else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewRequestState(uuid, serverCorrelationId, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse, uuid);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            requestStateInterface.onRequestStateFailure(errorDetails);
                        }
                    }
            );
        }
    }


    public void refundMerchantPay(@NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().refund(uuid, transactionRequest, new APIRequestCallback<RequestStateObject>() {
                @Override
                public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                    requestStateInterface.onRequestStateSuccess(serializedResponse, uuid);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    requestStateInterface.onRequestStateFailure(errorDetails);
                    ;
                }
            });
        }
    }

    public void reversal(@NonNull String referenceId, @NonNull ReversalObject reversal, @NonNull RequestStateInterface requestStateInterface) {
        if (referenceId == null) {
            requestStateInterface.onValidationError(Utils.setError(4));
            return;
        }
        if (referenceId.isEmpty()) {
            requestStateInterface.onValidationError(Utils.setError(4));
            return;
        }
        if (reversal == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
            return;
        }
        if (Utils.isOnline()) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().reversal(uuid, referenceId, reversal, new APIRequestCallback<RequestStateObject>() {
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
            requestStateInterface.onValidationError(Utils.setError(0));
        }

    }

    public void obtainAuthorisationCode(@NonNull String accountId, @NonNull AuthorisationCodeRequest codeRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (accountId == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
            return;
        }
        if (accountId.isEmpty()) {
            requestStateInterface.onValidationError(Utils.setError(1));
        } else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().obtainAuthorisationCode(uuid, accountId, codeRequest, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse, uuid);
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
        if (accountId == null) {
            retrieveTransactionInterface.onValidationError(Utils.setError(1));
        }
        if (accountId.isEmpty()) {
            retrieveTransactionInterface.onValidationError(Utils.setError(1));
        } else if (!Utils.isOnline()) {
            retrieveTransactionInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveTransaction(uuid, accountId, offset, limit, new APIRequestCallback<Transaction>() {
                        @Override
                        public void onSuccess(int responseCode, Transaction serializedResponse) {
                            retrieveTransactionInterface.onRetrieveTransactionSuccess(serializedResponse, uuid);
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
        if (Utils.isOnline()) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().checkServiceAvailability(uuid, new APIRequestCallback<ServiceAvailability>() {
                        @Override
                        public void onSuccess(int responseCode, ServiceAvailability serializedResponse) {
                            serviceAvailabilityInterface.onServiceAvailabilitySuccess(serializedResponse, uuid);
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
     * Retrieve Missing Transaction Response
     */

    public void retrieveMissingTransaction(String correlationId, @NonNull TransactionInterface missingTransactionInterface) {
        if (correlationId.isEmpty()) {
            missingTransactionInterface.onValidationError(Utils.setError(6));
        } else if (!Utils.isOnline()) {
            missingTransactionInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveMissingResponse(uuid, correlationId, new APIRequestCallback<GetLink>() {
                        @Override
                        public void onSuccess(int responseCode, GetLink serializedResponse) {

                            GSMAApi.getInstance().getMissingTransactions(serializedResponse.getLink(), new APIRequestCallback<TransactionObject>() {
                                @Override
                                public void onSuccess(int responseCode, TransactionObject serializedResponse) {
                                    missingTransactionInterface.onTransactionSuccess(serializedResponse, uuid);
                                }

                                @Override
                                public void onFailure(GSMAError errorDetails) {
                                    missingTransactionInterface.onTransactionFailure(errorDetails);
                                }
                            });
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            missingTransactionInterface.onTransactionFailure(errorDetails);
                        }
                    }
            );
        }
    }

    /**
     * Retrieve Missing Code
     */

    public void retrieveMissingCode(String correlationId, @NonNull AuthorisationCodeInterface authorisationCodeInterface) {
        if (correlationId == null) {
            authorisationCodeInterface.onValidationError(Utils.setError(6));
            return;
        }
        if (correlationId.isEmpty()) {
            authorisationCodeInterface.onValidationError(Utils.setError(6));
        } else if (!Utils.isOnline()) {
            authorisationCodeInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveMissingResponse(uuid, correlationId, new APIRequestCallback<GetLink>() {
                        @Override
                        public void onSuccess(int responseCode, GetLink serializedResponse) {

                            GSMAApi.getInstance().getMissingCodes(serializedResponse.getLink(), new APIRequestCallback<AuthorisationCode>() {
                                @Override
                                public void onSuccess(int responseCode, AuthorisationCode serializedResponse) {
                                    authorisationCodeInterface.onAuthorisationCodeSuccess(serializedResponse, uuid);
                                }

                                @Override
                                public void onFailure(GSMAError errorDetails) {
                                    authorisationCodeInterface.onAuthorisationCodeFailure(errorDetails);

                                }
                            });
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            authorisationCodeInterface.onAuthorisationCodeFailure(errorDetails);
                        }
                    }
            );
        }
    }

    /**
     * Disbursement
     */
    public void disbursementPay(@NonNull String transactionType, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (transactionRequest.getAmount() == null || transactionRequest.getCurrency() == null || transactionType == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
            return;
        }
        if (transactionRequest.getAmount().isEmpty() || transactionRequest.getCurrency().isEmpty() || transactionType.isEmpty()) {
            requestStateInterface.onValidationError(Utils.setError(1));
        } else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().merchantPay(uuid, transactionType, transactionRequest, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse, uuid);
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
     * Bulk Transaction
     */
    public void bulkTransaction(@NonNull BulkTransactionObject bulkTransactionObject, @NonNull RequestStateInterface requestStateInterface) {
        if (bulkTransactionObject == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
        } else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().bulkTransaction(uuid, bulkTransactionObject, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse, uuid);
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
     * Retrieve Batch transaction Rejections
     */

    public void retrieveBatchRejections(String batchId, @NonNull BatchRejectionInterface batchRejectionInterface) {
        if (batchId.isEmpty()) {
            batchRejectionInterface.onValidationError(Utils.setError(6));
        } else if (!Utils.isOnline()) {
            batchRejectionInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveBatchRejections(uuid, batchId, new APIRequestCallback<BatchTransactionRejection>() {
                @Override
                public void onSuccess(int responseCode, BatchTransactionRejection serializedResponse) {

                    batchRejectionInterface.batchTransactionRejections(serializedResponse, uuid);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    batchRejectionInterface.onTransactionFailure(errorDetails);
                }
            });

        }
    }

    /**
     * Retrieve Batch transaction completions
     */

    public void retrieveBatchCompletions(String batchId, @NonNull BatchCompletionInterface batchCompletionInterface) {
        if (batchId.isEmpty()) {
            batchCompletionInterface.onValidationError(Utils.setError(6));
        } else if (!Utils.isOnline()) {
            batchCompletionInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveBatchCompletions(uuid, batchId, new APIRequestCallback<BatchTransactionCompletion>() {

                @Override
                public void onSuccess(int responseCode, BatchTransactionCompletion serializedResponse) {
                    batchCompletionInterface.batchTransactionCompleted(serializedResponse, uuid);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    batchCompletionInterface.onTransactionFailure(errorDetails);
                }
            });

        }
    }


}


package com.gsmaSdk.gsma.controllers;

import android.content.Context;

import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeInterface;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeItemInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.BatchCompletionInterface;
import com.gsmaSdk.gsma.interfaces.BatchRejectionInterface;
import com.gsmaSdk.gsma.interfaces.BatchTransactionItemInterface;
import com.gsmaSdk.gsma.interfaces.PaymentInitialiseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;

import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeItem;
import com.gsmaSdk.gsma.models.common.Balance;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.transaction.Batch;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.manager.PreferenceManager;

import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeRequest;
import com.gsmaSdk.gsma.models.common.Balance;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.GetLink;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.models.transaction.Batch;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionCompletion;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionItem;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionRejection;
import com.gsmaSdk.gsma.models.transaction.BulkTransactionObject;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.network.retrofit.PaymentConfiguration;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;

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
     * Level of authentication check,if the level is NO_AUTH then access token is not generated
     */
    public void init(Context context, PaymentInitialiseInterface paymentInitialiseInterface) {
        if (PaymentConfiguration.getAuthType() != AuthenticationType.NO_AUTH) {
            if (Utils.isOnline()) {
                GSMAApi.getInstance().createToken(new APIRequestCallback<Token>() {
                                                      @Override
                                                      public void onSuccess(int responseCode, Token serializedResponse) {
                                                          paymentInitialiseInterface.onSuccess(serializedResponse);
                                                          PaymentConfiguration.setAuthToken(serializedResponse.getAccessToken());

                                                          PreferenceManager.getInstance().saveToken(serializedResponse.getAccessToken());
                                                      }

                                                      @Override
                                                      public void onFailure(GSMAError errorDetails) {
                                                          PreferenceManager.getInstance().saveToken("");
                                                          paymentInitialiseInterface.onFailure(errorDetails);
                                                      }
                                                  }
                );
            } else {
                paymentInitialiseInterface.onValidationError(Utils.setError(0));
            }
        }
    }

    /**
     * Get Balance
     *
     * @param accountId account identifier of the user
     */

    public void viewAccountBalance(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull BalanceInterface balanceInterface) {

        if (identifierArrayList == null) {
            balanceInterface.onValidationError(Utils.setError(1));
            return;
        }
        if (!Utils.isOnline()) {
            balanceInterface.onValidationError(Utils.setError(0));
            return;
        } else if (identifierArrayList.size() != 0) {
            String identifierValue = "";
            if (identifierArrayList.size() == 1) {
                Identifier identifier = identifierArrayList.get(0);
                identifierValue = identifierValue + identifier.getKey() + "/" + identifier.getValue();
            } else if (identifierArrayList.size() <= 3) {
                for (int i = 0; i < identifierArrayList.size(); i++) {
                    Identifier identifier = identifierArrayList.get(i);
                    if (identifierArrayList.size() - 1 == i) {
                        identifierValue = identifierValue + identifier.getKey() + "@" + identifier.getValue();
                    } else {
                        identifierValue = identifierValue + identifier.getKey() + "@" + identifier.getValue() + "$";

                    }

                }
            } else if (identifierArrayList.size() > 3) {
                balanceInterface.onValidationError(Utils.setError(11));
                return;
            }
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().checkBalance(uuid, identifierValue, new APIRequestCallback<Balance>() {
                @Override
                public void onSuccess(int responseCode, Balance serializedResponse) {
                    balanceInterface.onBalanceSuccess(serializedResponse, uuid);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    balanceInterface.onBalanceFailure(errorDetails);
                }
            });

        } else {
            balanceInterface.onValidationError(Utils.setError(1));
        }


    }

    /**
     * View a Transaction
     *
     * @param transactionReference Transaction Reference ID for identifying a transaction that is already initiated
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
            GSMAApi.getInstance().viewTransaction(uuid, transactionReference, new APIRequestCallback<TransactionRequest>() {
                        @Override
                        public void onSuccess(int responseCode, TransactionRequest serializedResponse) {
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
     * View Request State - returns the state of a transaction
     *
     * @param serverCorrelationId A unique identifier issued by the provider to enable the client to identify the RequestState resource on subsequent polling requests.
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

    /**
     * Refund - provides refund to a given account
     *
     * @param transactionRequest Transaction Object containing details required for initiating the refund process
     */
    public void createRefundTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().refund(uuid, notificationMethod, callbackUrl, transactionRequest, new APIRequestCallback<RequestStateObject>() {
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

    /**
     * Reversal - provides transaction reversal
     *
     * @param referenceId Reference id of a previous transaction
     * @param reversal    Reversal Object containing the type of the transaction
     */
    public void createReversal(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull String referenceId, @NonNull ReversalObject reversal, @NonNull RequestStateInterface requestStateInterface) {
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
            GSMAApi.getInstance().reversal(uuid, notificationMethod, callbackUrl, referenceId, reversal, new APIRequestCallback<RequestStateObject>() {
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

    /**
     * @param transactionRequest    the request object-International Transfers
     * @param requestStateInterface callback for request state object
     */
    public void createQuotation(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (transactionRequest == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
            return;
        }
        if (Utils.isOnline()) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().requestQuotation(uuid, notificationMethod, callbackUrl, transactionRequest, new APIRequestCallback<RequestStateObject>() {
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

    /**
     * @param transactionRequest    the request object-International Transfers
     * @param requestStateInterface callback for request state object
     */

    public void createInternationalTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (transactionRequest == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
        } else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().initiatePayment(uuid, notificationMethod, callbackUrl, "inttransfer", transactionRequest, new APIRequestCallback<RequestStateObject>() {
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
     * Obtain Authorisation code for a transaction
     *
     * @param accountId   Account identifier of a user
     * @param codeRequest An Object containing required details for getting the authorisation code
     */
    public void createAuthorisationCode(ArrayList<Identifier> identifierArrayList, @NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull String accountId, @NonNull AuthorisationCodeRequest codeRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (identifierArrayList == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
            return;
        }
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        } else if (identifierArrayList.size() != 0) {
            String identifierValue = "";
            if (identifierArrayList.size() == 1) {
                Identifier identifier = identifierArrayList.get(0);
                identifierValue = identifierValue + identifier.getKey() + "/" + identifier.getValue();
            } else if (identifierArrayList.size() <= 3) {
                for (int i = 0; i < identifierArrayList.size(); i++) {
                    Identifier identifier = identifierArrayList.get(i);
                    if (identifierArrayList.size() - 1 == i) {
                        identifierValue = identifierValue + identifier.getKey() + "@" + identifier.getValue();
                    } else {
                        identifierValue = identifierValue + identifier.getKey() + "@" + identifier.getValue() + "$";

                    }

                }
            } else if (identifierArrayList.size() > 3) {
                requestStateInterface.onValidationError(Utils.setError(11));
                return;
            }
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().obtainAuthorisationCode(uuid, notificationMethod, callbackUrl, identifierValue, codeRequest, new APIRequestCallback<RequestStateObject>() {
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

        } else {
            requestStateInterface.onValidationError(Utils.setError(1));
        }
    }

    /*
     * View Authorization Code
     * @param accountIdentifier Identifier of account
     * @param accountId Account id
     * @param authorizationCode Authorization
     */

    public void viewAuthorisationCode(@NonNull ArrayList<Identifier> identifierArrayList, String authorisationCode, AuthorisationCodeItemInterface authorisationCodeInterface) {

        if (identifierArrayList == null) {
            authorisationCodeInterface.onValidationError(Utils.setError(1));
            return;
        }
        if (!Utils.isOnline()) {
            authorisationCodeInterface.onValidationError(Utils.setError(0));
            return;
        } else if (identifierArrayList.size() != 0) {
            String identifierValue = "";
            if (identifierArrayList.size() == 1) {
                Identifier identifier = identifierArrayList.get(0);
                identifierValue = identifierValue + identifier.getKey() + "/" + identifier.getValue();
            } else if (identifierArrayList.size() <= 3) {
                for (int i = 0; i < identifierArrayList.size(); i++) {
                    Identifier identifier = identifierArrayList.get(i);
                    if (identifierArrayList.size() - 1 == i) {
                        identifierValue = identifierValue + identifier.getKey() + "@" + identifier.getValue();
                    } else {
                        identifierValue = identifierValue + identifier.getKey() + "@" + identifier.getValue() + "$";

                    }

                }
            } else if (identifierArrayList.size() > 3) {
                authorisationCodeInterface.onValidationError(Utils.setError(11));
                return;
            }
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewAuthorizationCode(uuid, identifierValue, authorisationCode, new APIRequestCallback<AuthorisationCodeItem>() {
                        @Override
                        public void onSuccess(int responseCode, AuthorisationCodeItem serializedResponse) {
                            authorisationCodeInterface.onAuthorisationCodeSuccess(serializedResponse, uuid);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            authorisationCodeInterface.onAuthorisationCodeFailure(errorDetails);
                        }
                    }
            );

        } else {
            authorisationCodeInterface.onValidationError(Utils.setError(1));
        }

        //
//        if (accountIdentifier == null || accountIdentifier.isEmpty()) {
//            authorisationCodeInterface.onValidationError(Utils.setError(8));
//            return;
//        }
//        if (accountId == null || accountId.isEmpty()) {
//            authorisationCodeInterface.onValidationError(Utils.setError(1));
//            return;
//        }
//        if (authorisationCode == null || authorisationCode.isEmpty()) {
//            authorisationCodeInterface.onValidationError(Utils.setError(9));
//            return;
//        }
//
//        String uuid = Utils.generateUUID();
//        GSMAApi.getInstance().viewAuthorizationCode(uuid, accountIdentifier, accountId, authorisationCode, new APIRequestCallback<AuthorisationCodeItem>() {
//                    @Override
//                    public void onSuccess(int responseCode, AuthorisationCodeItem serializedResponse) {
//                        authorisationCodeInterface.onAuthorisationCodeSuccess(serializedResponse, uuid);
//                    }
//
//                    @Override
//                    public void onFailure(GSMAError errorDetails) {
//                        authorisationCodeInterface.onAuthorisationCodeFailure(errorDetails);
//                    }
//                }
//        );


    }

    /**
     * Retrieve a transaction
     *
     * @param accountId Account identifier of a user
     * @param offset    offset required for pagination
     * @param limit     limit set for receiving records per request
     */

    public void viewAccountTransactions(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull int offset, @NonNull int limit, @NonNull RetrieveTransactionInterface retrieveTransactionInterface) {
        if (identifierArrayList == null) {
            retrieveTransactionInterface.onValidationError(Utils.setError(1));
            return;
        }
        if (!Utils.isOnline()) {
            retrieveTransactionInterface.onValidationError(Utils.setError(0));
            return;
        } else if (identifierArrayList.size() != 0) {
            String identifierValue = "";
            if (identifierArrayList.size() == 1) {
                Identifier identifier = identifierArrayList.get(0);
                identifierValue = identifierValue + identifier.getKey() + "/" + identifier.getValue();
            } else if (identifierArrayList.size() <= 3) {
                for (int i = 0; i < identifierArrayList.size(); i++) {
                    Identifier identifier = identifierArrayList.get(i);
                    if (identifierArrayList.size() - 1 == i) {
                        identifierValue = identifierValue + identifier.getKey() + "@" + identifier.getValue();
                    } else {
                        identifierValue = identifierValue + identifier.getKey() + "@" + identifier.getValue() + "$";

                    }

                }
            } else if (identifierArrayList.size() > 3) {
                retrieveTransactionInterface.onValidationError(Utils.setError(11));
                return;
            }
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveTransaction(uuid, identifierValue, offset, limit, new APIRequestCallback<Transaction>() {
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
        } else {
            retrieveTransactionInterface.onValidationError(Utils.setError(1));
        }


    }


    /**
     * Check Service Availability - To check whether the service is available
     */

    public void viewServiceAvailability(@NonNull ServiceAvailabilityInterface serviceAvailabilityInterface) {
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
     *
     * @param correlationId UUID that enables the client to correlate the API request with the resource created/updated by the provider
     */

    public void viewTransactionResponse(String correlationId, @NonNull TransactionInterface missingTransactionInterface) {
        if (correlationId.isEmpty()) {
            missingTransactionInterface.onValidationError(Utils.setError(6));
        } else if (!Utils.isOnline()) {
            missingTransactionInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveMissingResponse(uuid, correlationId, new APIRequestCallback<GetLink>() {
                        @Override
                        public void onSuccess(int responseCode, GetLink serializedResponse) {

                            GSMAApi.getInstance().getMissingTransactions(serializedResponse.getLink(), new APIRequestCallback<TransactionRequest>() {
                                @Override
                                public void onSuccess(int responseCode, TransactionRequest serializedResponse) {
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
     * Retrieve Missing Code - To retrieve a missing Authorisation code
     *
     * @param correlationId UUID that enables the client to correlate the API request with the resource created/updated by the provider
     */

    public void viewAuthorisationCodeResponse(String correlationId, @NonNull AuthorisationCodeInterface authorisationCodeInterface) {
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
     * Intiate Payment - Intiate merchant pay
     *
     * @param transactionType    Type of the transaction that is being carried out
     * @param transactionRequest Transaction Object containing details required for initiating the transaction
     */
    public void createMerchantTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (transactionRequest.getAmount() == null || transactionRequest.getCurrency() == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
            return;
        }
        if (transactionRequest.getAmount().isEmpty() || transactionRequest.getCurrency().isEmpty()) {
            requestStateInterface.onValidationError(Utils.setError(1));
        } else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().initiatePayment(uuid, notificationMethod, callbackUrl, "merchantpay", transactionRequest, new APIRequestCallback<RequestStateObject>() {
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
     * Intiate Payment Disbursment - Intiate disbursement transaction
     *
     * @param transactionType    Type of the transaction that is being carried out
     * @param transactionRequest Transaction Object containing details required for initiating the transaction
     */

    public void createDisbursementTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull TransactionRequest transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (transactionRequest.getAmount() == null || transactionRequest.getCurrency() == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
            return;
        }
        if (transactionRequest.getAmount().isEmpty() || transactionRequest.getCurrency().isEmpty()) {
            requestStateInterface.onValidationError(Utils.setError(1));
        } else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().initiatePayment(uuid, notificationMethod, callbackUrl, "disbursement", transactionRequest, new APIRequestCallback<RequestStateObject>() {
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


    /*
     * View a quotation details
     * @param quotationReference - Quotation reference obtained from callback of request quotation API
     *
     */

    public void viewQuotation(@NonNull String quotationReference, @NonNull TransactionInterface transactionInterface) {
        if (quotationReference == null || quotationReference.isEmpty()) {
            transactionInterface.onValidationError(Utils.setError(10));
            return;
        }else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewQuotation(uuid, quotationReference, new APIRequestCallback<TransactionRequest>() {
                        @Override
                        public void onSuccess(int responseCode, TransactionRequest serializedResponse) {
                            transactionInterface.onTransactionSuccess(serializedResponse, uuid);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            transactionInterface.onTransactionFailure((errorDetails));
                        }
                    }
            );
        }
    }


    /**
     * Bulk Disbursement - Organisations can make bulk disbursements to customers
     *
     * @param bulkTransactionObject Transaction Object containing details required for initiating the bulk transaction
     */
    public void createBatchTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull BulkTransactionObject bulkTransactionObject, @NonNull RequestStateInterface requestStateInterface) {
        if (bulkTransactionObject == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
        } else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().bulkTransaction(uuid, notificationMethod, callbackUrl, bulkTransactionObject, new APIRequestCallback<RequestStateObject>() {
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
     * Retrieve rejected transactions from a batch transaction
     *
     * @param batchId Unique identifier for identifying a batch transaction
     */

    public void viewBatchRejections(String batchId, @NonNull BatchRejectionInterface batchRejectionInterface) {
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
     * Update a batch transaction
     *
     * @param batchId        Unique identifier for identifying a batch transaction
     * @param batchArrayList list containing required details for updating a batch disbursement
     */

    public void updateBatchTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, String batchId, @NonNull ArrayList<Batch> batchArrayList, @NonNull RequestStateInterface requestStateInterface) {
        if (batchId == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
            return;
        } else if (batchId.isEmpty()) {
            requestStateInterface.onValidationError(Utils.setError(1));
        } else if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().updateBatch(uuid, notificationMethod, callbackUrl, batchId, batchArrayList, new APIRequestCallback<RequestStateObject>() {
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
     * Retrieve a batch transaction
     *
     * @param batchId Unique identifier for identifying a batch transaction
     */
    public void viewBatchTransaction(String batchId, @NonNull BatchTransactionItemInterface batchTransactionItemInterface) {
        if (batchId == null) {
            batchTransactionItemInterface.onValidationError(Utils.setError(1));
            return;
        } else if (batchId.isEmpty()) {
            batchTransactionItemInterface.onValidationError(Utils.setError(1));
        } else if (!Utils.isOnline()) {
            batchTransactionItemInterface.onValidationError(Utils.setError(0));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveBatch(uuid, batchId, new APIRequestCallback<BatchTransactionItem>() {
                        @Override
                        public void onSuccess(int responseCode, BatchTransactionItem serializedResponse) {
                            batchTransactionItemInterface.batchTransactionSuccess(serializedResponse, uuid);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            batchTransactionItemInterface.onTransactionFailure(errorDetails);
                        }
                    }
            );

        }
    }

    /**
     * Retrieve completed transactions from a batch transaction
     *
     * @param batchId Unique identifier for identifying a batch transaction
     */

    public void viewBatchCompletions(String batchId, @NonNull BatchCompletionInterface batchCompletionInterface) {
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

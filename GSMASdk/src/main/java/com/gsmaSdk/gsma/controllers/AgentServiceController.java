package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.AccountHolderInterface;
import com.gsmaSdk.gsma.interfaces.AccountInterface;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeInterface;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeItemInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.models.account.Account;
import com.gsmaSdk.gsma.models.account.AccountHolderName;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.PatchData;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;

public class AgentServiceController extends Common {

    /**
     * Initiate Payment - Initiate bill withdrawal
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param transactionRequest Transaction Object containing details required for initiating the transaction
     */
    public void createWithdrawalTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull Transaction transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        MerchantTransaction.getInstance().createMerchantTransaction(notificationMethod, callbackUrl, transactionRequest, "withdrawal", requestStateInterface);
    }

    /**
     * Initiate Payment - Initiate Deposit
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param transactionRequest Transaction Object containing details required for initiating the transaction
     */
    public void createDepositTransaction(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull Transaction transactionRequest, @NonNull RequestStateInterface requestStateInterface) {
        MerchantTransaction.getInstance().createMerchantTransaction(notificationMethod, callbackUrl, transactionRequest, "deposit", requestStateInterface);
    }


    /**
     * Create Account- Create Deposit Account
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        Callback Url
     * @param accountRequest     Model class of create account Request
     */
    public void createAccount(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull Account accountRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (accountRequest == null) {
            requestStateInterface.onValidationError(Utils.setError(5));
        } else {
            String uuid = Utils.generateUUID();
            requestStateInterface.getCorrelationId(uuid);
            GSMAApi.getInstance().createAccount(uuid, notificationMethod, callbackUrl, accountRequest, new APIRequestCallback<RequestStateObject>() {
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
     * Obtain Authorisation code for a transaction
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param codeRequest         An Object containing required details for getting the authorisation code
     */
    public void createAuthorisationCode(ArrayList<Identifier> identifierArrayList, @NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull AuthorisationCode codeRequest, @NonNull RequestStateInterface requestStateInterface) {
        AuthorisationCodeController.getInstance().createAuthorisationCode(identifierArrayList, notificationMethod, callbackUrl, codeRequest, requestStateInterface);
    }

    /**
     * View Authorization Code
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param authorisationCode   Created Authorisation Code
     */
    public void viewAuthorisationCode(@NonNull ArrayList<Identifier> identifierArrayList, String authorisationCode, AuthorisationCodeItemInterface authorisationCodeInterface) {
        AuthorisationCodeController.getInstance().viewAuthorisationCode(identifierArrayList, authorisationCode, authorisationCodeInterface);

    }

    /**
     * View Account Name-Get name of  particular account holder
     *
     * @param identifierArrayList account identifiers of the user
     */
    public void viewAccountName(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull AccountHolderInterface accountHolderInterface) {
        AccountNameController.getInstance().viewAccountName(identifierArrayList, accountHolderInterface);
    }

    /**
     * Reversal - provides transaction reversal
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl        The server URl for receiving response of transaction
     * @param referenceId        Reference id of a previous transaction
     * @param reversal           Reversal Object containing the type of the transaction
     */

    public void createReversal(@NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull String referenceId, @NonNull Reversal reversal, @NonNull RequestStateInterface requestStateInterface) {
        ReversalTransaction.getInstance().createReversal(notificationMethod, callbackUrl, referenceId, reversal, requestStateInterface);

    }


    /**
     * View Account Details - Get account details of a account
     *
     * @param identifierArrayList account identifiers of the user
     */

    public void viewAccount(ArrayList<Identifier> identifierArrayList, @NonNull AccountInterface accountInterface) {
        if (!Utils.isOnline()) {
            accountInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (identifierArrayList == null) {
            accountInterface.onValidationError(Utils.setError(1));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewAccount(uuid, Utils.getIdentifiers(identifierArrayList), new APIRequestCallback<Account>() {
                @Override
                public void onSuccess(int responseCode, Account serializedResponse) {
                    accountInterface.onAccountSuccess(serializedResponse);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    accountInterface.onAccountFailure(errorDetails);
                }
            });

        } else {
            accountInterface.onValidationError(Utils.setError(1));
        }
    }

    /**
     * Update Account Identity - Update the KYC of a customer
     *
     * @param notificationMethod The enumerated datatype to determine polling or callback
     * @param callbackUrl   The server URl for receiving response of transaction
     * @param identityId    The id used to identify an account
     * @param patchDataArrayList List contains required details for updating the kyc of a customer
     * @param identifierArrayList List of account identifiers of a user
     */
    public void updateAccountIdentity(@NonNull Enum notificationMethod, @NonNull String callbackUrl, String identityId, @NonNull ArrayList<PatchData> patchDataArrayList,ArrayList<Identifier> identifierArrayList,@NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if(patchDataArrayList==null){
            requestStateInterface.onValidationError(Utils.setError(13));
            return;
        }
        if(identityId==null){
            requestStateInterface.onValidationError(Utils.setError(14));
            return;
        }
        if(identityId.isEmpty()){
            requestStateInterface.onValidationError(Utils.setError(14));
            return;
        }
        if (identifierArrayList == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().updateAccountIdentity(uuid, notificationMethod,callbackUrl,identityId,Utils.getIdentifiers(identifierArrayList),patchDataArrayList, new APIRequestCallback<RequestStateObject>() {
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

    /**
     * View Account Balance-Get the balance of a particular account
     *
     * @param identifierArrayList - List of identifiers to identify a particular account
     */
    public void viewAccountBalance(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull BalanceInterface balanceInterface) {
        AccountBalanceController.getInstance().viewAccountBalance(identifierArrayList, balanceInterface);

    }

    /**
     * Retrieve a transaction
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param transactionFilter   Filter object for transaction
     */
    public void viewAccountTransactions(@NonNull ArrayList<Identifier> identifierArrayList, TransactionFilter transactionFilter, @NonNull RetrieveTransactionInterface retrieveTransactionInterface) {
        AccountTransactionsController.getInstance().viewAccountTransactions(identifierArrayList, transactionFilter, retrieveTransactionInterface);

    }



}

package com.gsmaSdk.gsma.merchantpay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.util.Log;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeInterface;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeItemInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.Identity;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodes;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class MerchantPaymentUnitTest {

    /************************************Balance************************************/
    @Test
    public void viewBalanceEmptyIdentifierSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.merchantPayment.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onBalanceSuccess(Balance balance) {

            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });

    }


    @Test
    public void viewBalanceNullIdentifierSuccess() {
        SDKManager.merchantPayment.viewAccountBalance(null, new BalanceInterface() {
            @Override
            public void onBalanceSuccess(Balance balance) {
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });

    }


    /*****************************Create Merchant Transaction************************************/

    @Test
    public void createMerchantTransactionNullTransactionRequestSuccess() {
        SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod.POLLING, "", null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid json format");

            }
        });

    }

    /*********************************Request State**********************************/

    @Test
    public void viewRequestStateNullServerCorrelationIdSuccess() {

        SDKManager.merchantPayment.viewRequestState(null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
            }

            @Override
            public void getCorrelationId(String correlationID) {
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid server correlation id");
            }
        });

    }

    @Test
    public void viewRequestStateEmptyServerCorrelationIdSuccess() {

        SDKManager.merchantPayment.viewRequestState("", new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
            }

            @Override
            public void getCorrelationId(String correlationID) {
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid server correlation id");
            }
        });

    }

    /*******************************View transaction******************/

    @Test
    public void viewTransactionNullReferenceSuccess() {
        SDKManager.merchantPayment.viewTransaction(null, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(Transaction transactionObject) {

            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid transaction reference");
            }
        });

    }


    @Test
    public void viewTransactionEmptyReferenceSuccess() {
        SDKManager.merchantPayment.viewTransaction("", new TransactionInterface() {
            @Override
            public void onTransactionSuccess(Transaction transactionObject) {

            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid transaction reference");
            }
        });

    }




    /*******************************Refund***********************************/

    @Test
    public void createRefundTransactionNullTransactionSuccess() {
        SDKManager.merchantPayment.createRefundTransaction(NotificationMethod.POLLING, "", null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid json format");

            }
        });

    }


    /***************************Reversal****************************************/

    @Test
    public void createReversalNullReferenceIdSuccess() {

        Reversal reversalObject = new Reversal();
        reversalObject.setType("reversal");

        SDKManager.merchantPayment.createReversal(NotificationMethod.POLLING, "", null, reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid reference id");
            }
        });
    }

    @Test
    public void createReversalEmptyReferenceIdSuccess() {

        Reversal reversalObject = new Reversal();
        reversalObject.setType("reversal");

        SDKManager.merchantPayment.createReversal(NotificationMethod.POLLING, "", "", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid reference id");
            }
        });
    }

    @Test
    public void createReversalNullReversalReferenceSuccess() {
        SDKManager.merchantPayment.createReversal(NotificationMethod.POLLING, "", "REF-10121", null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid json format");

            }
        });
    }

    @Test
    public void createReversalNullReversalNullReferenceSuccess() {
        SDKManager.merchantPayment.createReversal(NotificationMethod.POLLING, "", null, null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid reference id");

            }
        });

    }

    @Test
    public void createReversalNullReversalEmptyReferenceSuccess() {
        SDKManager.merchantPayment.createReversal(NotificationMethod.POLLING, "", "", null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid reference id");

            }
        });

    }


    /****************************View Account Transactions**********************/

    @Test
    public void viewAccountEmptyIdentifierEmptyFilterSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();

        TransactionFilter transactionFilter = new TransactionFilter();

        SDKManager.merchantPayment.viewAccountTransactions(identifierArrayList, transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {

            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {

            }
        });

    }

    @Test
    public void viewAccountEmptyIdentifierNullFilterSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.merchantPayment.viewAccountTransactions(identifierArrayList, null, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {

            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {

            }
        });
    }


    @Test
    public void viewAccountEmptyIdentifierFilterSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();


        TransactionFilter transactionFilter=new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);


        SDKManager.merchantPayment.viewAccountTransactions(identifierArrayList, transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {

            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {

            }
        });

    }

    @Test
    public void viewAccountNullIdentifierEmptyFilterSuccess() {

        TransactionFilter transactionFilter = new TransactionFilter();

        SDKManager.merchantPayment.viewAccountTransactions(null, transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {

            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {

            }
        });

    }

    @Test
    public void viewAccountNullIdentifierNullFilterSuccess() {

        SDKManager.merchantPayment.viewAccountTransactions(null, null, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {

            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {

            }
        });
    }


    @Test
    public void viewAccountNullIdentifierFilterSuccess() {

        TransactionFilter transactionFilter=new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);


        SDKManager.merchantPayment.viewAccountTransactions(null, transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {

            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {

            }
        });

    }

    @Test
    public void viewTransactionIdentifierNullFilterSuccess() {

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        Identifier identifier=new Identifier();
        identifier.setKey("accountno");
        identifier.setValue("2999");
        identifierArrayList.add(identifier);


        SDKManager.merchantPayment.viewAccountTransactions(identifierArrayList, null, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid json format");
            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {

            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {

            }
        });

    }




    /***********************Authorisation Code*********************/
    @Test
    public void createAuthCodeEmptyIdentifierSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();

        AuthorisationCode authorisationCodeRequest = new AuthorisationCode();
        authorisationCodeRequest.setAmount("200.00");
        authorisationCodeRequest.setRequestDate("2021-10-18T10:43:27.405Z");
        authorisationCodeRequest.setCurrency("RWF");
        authorisationCodeRequest.setCodeLifetime(1);

        SDKManager.merchantPayment.createAuthorisationCode(identifierArrayList, NotificationMethod.POLLING, "", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });

    }

    @Test
    public void createAuthCodeNullIdentifierSuccess() {

        AuthorisationCode authorisationCodeRequest = new AuthorisationCode();
        authorisationCodeRequest.setAmount("200.00");
        authorisationCodeRequest.setRequestDate("2021-10-18T10:43:27.405Z");
        authorisationCodeRequest.setCurrency("RWF");
        authorisationCodeRequest.setCodeLifetime(1);

        SDKManager.merchantPayment.createAuthorisationCode(null, NotificationMethod.POLLING, "", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });

    }


    /********************************View Response***********************************/

    @Test
    public void viewResponseEmptyCorrelationIdSuccess() {

        SDKManager.merchantPayment.viewResponse("", new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid correlation id");

            }
        });

    }


    @Test
    public void viewResponseNullCorrelationIdSuccess() {

        SDKManager.merchantPayment.viewResponse(null, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid correlation id");
            }
        });

    }


    /****************************View AuthorisationCode Response*************/

    @Test
    public void viewAuthCodeResponseEmptyCorrelationIdSuccess() {
        SDKManager.merchantPayment.viewAuthorisationCodeResponse("", new AuthorisationCodeInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCodes authorisationCode) {

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid correlation id");

            }
        });
    }

    @Test
    public void viewAuthCodeResponseNullCorrelationIdSuccess() {
        SDKManager.merchantPayment.viewAuthorisationCodeResponse(null, new AuthorisationCodeInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCodes authorisationCode) {

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid correlation id");

            }
        });
    }


    /****************************View AuthorisationCode*************/

    @Test
    public void viewAuthCodeEmptyIdentifierEmptyAuthCodeSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.merchantPayment.viewAuthorisationCode(identifierArrayList, "", new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });
    }

    @Test
    public void viewAuthCodeEmptyIdentifierNullAuthCodeSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.merchantPayment.viewAuthorisationCode(identifierArrayList, null, new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });
    }

    @Test
    public void viewAuthCodeEmptyIdentifierAuthCodeSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.merchantPayment.viewAuthorisationCode(identifierArrayList, "REF-10102", new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });
    }



    @Test
    public void viewAuthCodeNullIdentifierEmptyAuthCodeSuccess() {
        SDKManager.merchantPayment.viewAuthorisationCode(null, "", new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });
    }

    @Test
    public void viewAuthCodeNullIdentifierNullAuthCodeSuccess() {
        SDKManager.merchantPayment.viewAuthorisationCode(null, null, new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });
    }

    @Test
    public void viewAuthCodeNullIdentifierAuthCodeSuccess() {

        SDKManager.merchantPayment.viewAuthorisationCode(null, "REF-10102", new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });
    }


    @Test
    public void viewAuthCodeIdentifierEmptyAuthCodeSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        Identifier identifier=new Identifier();
        identifier.setKey("accountid");
        identifier.setValue("2999");
        identifierArrayList.add(identifier);

        SDKManager.merchantPayment.viewAuthorisationCode(identifierArrayList, "", new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid Authorisation Code");
            }
        });
    }

    @Test
    public void viewAuthCodeIdentifierNullAuthCodeSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        Identifier identifier=new Identifier();
        identifier.setKey("accountid");
        identifier.setValue("2999");
        identifierArrayList.add(identifier);

        SDKManager.merchantPayment.viewAuthorisationCode(identifierArrayList, null, new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid Authorisation Code");
            }
        });
    }


}
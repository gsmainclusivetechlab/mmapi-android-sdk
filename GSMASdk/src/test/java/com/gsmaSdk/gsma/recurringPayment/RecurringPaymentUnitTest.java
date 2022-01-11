package com.gsmaSdk.gsma.recurringPayment;

import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.DebitMandateInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.debitmandate.DebitMandate;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RecurringPaymentUnitTest {
    /************************************Balance************************************/
    @Test
    public void viewBalanceEmptyIdentifierSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.recurringPayment.viewAccountBalance(identifierArrayList, new BalanceInterface() {
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
        SDKManager.recurringPayment.viewAccountBalance(null, new BalanceInterface() {
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

    /************************************Create Debit Mandate ************************************/
    @Test
    public void createAccountDebitMandateEmptyIdentifierSuccess() {
        DebitMandate debitMandateRequest = new DebitMandate();
        SDKManager.recurringPayment.createAccountDebitMandate(NotificationMethod.POLLING, "", null,debitMandateRequest, new RequestStateInterface() {
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
    public void createAccountDebitMandateEmptyDebitMandateSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.recurringPayment.createAccountDebitMandate(NotificationMethod.POLLING, "", identifierArrayList,null, new RequestStateInterface() {
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

    /*****************************View Account Debit Mandate************************************/

    @Test
    public void viewAccountDebitMandateNullIdentifierSuccess() {
        SDKManager.recurringPayment.viewAccountDebitMandate(null, "REF1234",  new DebitMandateInterface() {
            @Override
            public void onDebitMandateSuccess(DebitMandate debitMandate) {

            }

            @Override
            public void onDebitMandateFailure(GSMAError gsmaError) {

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
    public void viewAccountDebitMandateEmptyTransactionReferenceSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.recurringPayment.viewAccountDebitMandate(identifierArrayList, "",  new DebitMandateInterface() {
            @Override
            public void onDebitMandateSuccess(DebitMandate debitMandate) {

            }

            @Override
            public void onDebitMandateFailure(GSMAError gsmaError) {

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
    public void viewAccountDebitMandateNullTransactionReferenceSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.recurringPayment.viewAccountDebitMandate(identifierArrayList, null,  new DebitMandateInterface() {
            @Override
            public void onDebitMandateSuccess(DebitMandate debitMandate) {

            }

            @Override
            public void onDebitMandateFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid transaction reference");

            }
        });

    }

    /*********************************Request State**********************************/

    @Test
    public void viewRequestStateNullServerCorrelationIdSuccess() {

        SDKManager.recurringPayment.viewRequestState(null, new RequestStateInterface() {
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

        SDKManager.recurringPayment.viewRequestState("", new RequestStateInterface() {
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
        SDKManager.recurringPayment.viewTransaction(null, new TransactionInterface() {
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
        SDKManager.recurringPayment.viewTransaction("", new TransactionInterface() {
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

    /***************************Reversal****************************************/

    @Test
    public void createReversalNullReferenceIdSuccess() {

        Reversal reversalObject = new Reversal();
        reversalObject.setType("reversal");

        SDKManager.recurringPayment.createReversal(NotificationMethod.POLLING, "", null, reversalObject, new RequestStateInterface() {
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

        SDKManager.recurringPayment.createReversal(NotificationMethod.POLLING, "", "", reversalObject, new RequestStateInterface() {
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
        SDKManager.recurringPayment.createReversal(NotificationMethod.POLLING, "", "REF-10121", null, new RequestStateInterface() {
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
        SDKManager.recurringPayment.createReversal(NotificationMethod.POLLING, "", null, null, new RequestStateInterface() {
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
    public void viewAccountTransactionEmptyIdentifierSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();

        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        SDKManager.recurringPayment.viewAccountTransactions(identifierArrayList, transactionFilter, new RetrieveTransactionInterface() {
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
    public void viewAccountTransactionNullIdentifierSuccess() {

        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);
        SDKManager.recurringPayment.viewAccountTransactions(null, transactionFilter, new RetrieveTransactionInterface() {
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

    /********************************View Response***********************************/

    @Test
    public void viewResponseEmptyCorrelationIdSuccess() {

        SDKManager.recurringPayment.viewResponse("", new MissingResponseInterface() {
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

        SDKManager.recurringPayment.viewResponse(null, new MissingResponseInterface() {
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

}

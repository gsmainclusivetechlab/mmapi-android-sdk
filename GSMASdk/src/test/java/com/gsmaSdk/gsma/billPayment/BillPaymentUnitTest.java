package com.gsmaSdk.gsma.billPayment;

import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.BillPaymentInterface;
import com.gsmaSdk.gsma.interfaces.DebitMandateInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveBillPaymentInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.bills.BillPay;
import com.gsmaSdk.gsma.models.bills.BillPayments;
import com.gsmaSdk.gsma.models.bills.Bills;
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

public class BillPaymentUnitTest {

    /************************************View Account Bills************************************/
    @Test
    public void viewAccountBillsEmptyIdentifierSuccess() {
        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        TransactionFilter transactionFilter = new TransactionFilter();
        SDKManager.billPayment.viewAccountBills(identifierArrayList,transactionFilter, new RetrieveBillPaymentInterface() {

            @Override
            public void onRetrieveBillPaymentSuccess(Bills bills) {

            }

            @Override
            public void onRetrieveBillPaymentFailure(GSMAError gsmaError) {

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
    public void viewAccountBillsNullIdentifierSuccess() {

        TransactionFilter transactionFilter = new TransactionFilter();
        SDKManager.billPayment.viewAccountBills(null,transactionFilter, new RetrieveBillPaymentInterface() {

            @Override
            public void onRetrieveBillPaymentSuccess(Bills bills) {

            }

            @Override
            public void onRetrieveBillPaymentFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });

    }


    /************************************View Bill Payment************************************/


    @Test
    public void viewBillPaymentEmptyIdentifierSuccess() {
        TransactionFilter transactionFilter = new TransactionFilter();
        SDKManager.billPayment.viewBillPayment(null, transactionFilter, "REF12345", new BillPaymentInterface() {

            @Override
            public void onBillPaymentSuccess(BillPayments billPayments) {

            }

            @Override
            public void onBillPaymentFailure(GSMAError gsmaError) {

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
    public void viewBillPaymentNullIdentifierSuccess() {
        TransactionFilter transactionFilter = new TransactionFilter();
        SDKManager.billPayment.viewBillPayment(null, transactionFilter, "REF12345", new BillPaymentInterface() {

            @Override
            public void onBillPaymentSuccess(BillPayments billPayments) {

            }

            @Override
            public void onBillPaymentFailure(GSMAError gsmaError) {

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
    public void viewBillPaymentEmptyBillReferenceSuccess() {
        ArrayList<Identifier>identifierArrayList = new ArrayList<>();
        TransactionFilter transactionFilter = new TransactionFilter();
        SDKManager.billPayment.viewBillPayment(identifierArrayList, transactionFilter, "", new BillPaymentInterface() {

            @Override
            public void onBillPaymentSuccess(BillPayments billPayments) {

            }

            @Override
            public void onBillPaymentFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid bill Reference");
            }
        });

    }

    @Test
    public void viewBillPaymentNullBillReferenceSuccess() {
        ArrayList<Identifier>identifierArrayList = new ArrayList<>();
        TransactionFilter transactionFilter = new TransactionFilter();
        SDKManager.billPayment.viewBillPayment(identifierArrayList, transactionFilter, null, new BillPaymentInterface() {

            @Override
            public void onBillPaymentSuccess(BillPayments billPayments) {

            }

            @Override
            public void onBillPaymentFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid bill Reference");
            }
        });

    }

    /*****************************Create Bill Payment************************************/

    @Test
    public void createBillPaymentNullIdentifierSuccess() {
        BillPay billPayment = new BillPay();
        SDKManager.billPayment.createBillPayment(NotificationMethod.POLLING,"", null,billPayment,"REF1234",  new RequestStateInterface() {

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
    public void createBillPaymentNullBillPaySuccess() {
        ArrayList<Identifier>identifierArrayList = new ArrayList<>();
        SDKManager.billPayment.createBillPayment(NotificationMethod.POLLING,"", identifierArrayList,null,"REF1234",  new RequestStateInterface() {

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
    public void createBillPaymentNullReferenceSuccess() {
        ArrayList<Identifier>identifierArrayList = new ArrayList<>();
        BillPay billPayment = new BillPay();
        SDKManager.billPayment.createBillPayment(NotificationMethod.POLLING,"", identifierArrayList,billPayment,null,  new RequestStateInterface() {

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
                assertEquals(errorObject.getErrorDescription(), "Invalid bill Reference");

            }
        });

    }

    @Test
    public void createBillPaymentEmptyReferenceSuccess() {
        ArrayList<Identifier>identifierArrayList = new ArrayList<>();
        BillPay billPayment = new BillPay();
        SDKManager.billPayment.createBillPayment(NotificationMethod.POLLING,"", identifierArrayList,billPayment,"",  new RequestStateInterface() {

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
                assertEquals(errorObject.getErrorDescription(), "Invalid bill Reference");

            }
        });

    }

    /*****************************Create Merchant Transaction************************************/

    @Test
    public void createMerchantTransactionNullTransactionRequestSuccess() {
        SDKManager.billPayment.createBillTransaction(NotificationMethod.POLLING, "", null, new RequestStateInterface() {
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

        SDKManager.billPayment.viewRequestState(null, new RequestStateInterface() {
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

        SDKManager.billPayment.viewRequestState("", new RequestStateInterface() {
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
        SDKManager.billPayment.viewTransaction(null, new TransactionInterface() {
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
        SDKManager.billPayment.viewTransaction("", new TransactionInterface() {
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


    /********************************View Response***********************************/

    @Test
    public void viewResponseEmptyCorrelationIdSuccess() {

        SDKManager.billPayment.viewResponse("", new MissingResponseInterface() {
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

        SDKManager.billPayment.viewResponse(null, new MissingResponseInterface() {
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

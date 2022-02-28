package com.gsmaSdk.gsma.p2ptransfer;

import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.AccountHolderInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.AccountHolderName;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("ALL")
public class P2PTransferUnitTest {



    /*******************************View Account Name**************************/

    @Test
    public void viewAccountNameEmptyIdentifierSuccess(){
        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        SDKManager.p2PTransfer.viewAccountName(identifierArrayList, new AccountHolderInterface() {
            @Override
            public void onRetrieveAccountInfoSuccess(AccountHolderName accountHolderObject) {

            }

            @Override
            public void onRetrieveAccountInfoFailure(GSMAError gsmaError) {


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
    public void viewAccountNameNullIdentifierSuccess(){
        SDKManager.p2PTransfer.viewAccountName(null, new AccountHolderInterface() {
            @Override
            public void onRetrieveAccountInfoSuccess(AccountHolderName accountHolderObject) {

            }

            @Override
            public void onRetrieveAccountInfoFailure(GSMAError gsmaError) {


            }
            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });
    }

    /*******************************Create Quotation**************************/

    @Test
    public void createQuotationNullQuotationSuccess(){

        SDKManager.p2PTransfer.createQuotation(NotificationMethod.POLLING, "",null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @SuppressWarnings("EmptyMethod")
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
    /*******************************Create Transfer Transaction**************************/
    @Test
    public void createTransferNullTransactionSuccess(){
        SDKManager.p2PTransfer.createTransferTransaction(NotificationMethod.POLLING, "",null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @SuppressWarnings("EmptyMethod")
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

        SDKManager.p2PTransfer.createReversal(NotificationMethod.POLLING, "", null, reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @SuppressWarnings("EmptyMethod")
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

        SDKManager.p2PTransfer.createReversal(NotificationMethod.POLLING, "", "", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @SuppressWarnings("EmptyMethod")
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
        SDKManager.p2PTransfer.createReversal(NotificationMethod.POLLING, "", "REF-10121", null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @SuppressWarnings("EmptyMethod")
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
        SDKManager.p2PTransfer.createReversal(NotificationMethod.POLLING, "", null, null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @SuppressWarnings("EmptyMethod")
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
        SDKManager.p2PTransfer.createReversal(NotificationMethod.POLLING, "", "", null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @SuppressWarnings("EmptyMethod")
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

        SDKManager.p2PTransfer.viewAccountTransactions(identifierArrayList, transactionFilter, new RetrieveTransactionInterface() {
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
        SDKManager.p2PTransfer.viewAccountTransactions(identifierArrayList, null, new RetrieveTransactionInterface() {
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


        SDKManager.p2PTransfer.viewAccountTransactions(identifierArrayList, transactionFilter, new RetrieveTransactionInterface() {
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

        SDKManager.p2PTransfer.viewAccountTransactions(null, transactionFilter, new RetrieveTransactionInterface() {
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

        SDKManager.p2PTransfer.viewAccountTransactions(null, null, new RetrieveTransactionInterface() {
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


        SDKManager.p2PTransfer.viewAccountTransactions(null, transactionFilter, new RetrieveTransactionInterface() {
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


        SDKManager.p2PTransfer.viewAccountTransactions(identifierArrayList, null, new RetrieveTransactionInterface() {
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


    /********************************View Response***********************************/

    @Test
    public void viewResponseEmptyCorrelationIdSuccess() {

        SDKManager.p2PTransfer.viewResponse("", new MissingResponseInterface() {
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

        SDKManager.p2PTransfer.viewResponse(null, new MissingResponseInterface() {
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

    /********************************View Quotation***********************************/


    @Test
    public void viewQuotationEmptyQuotationReferenceSuccess(){

        SDKManager.p2PTransfer.viewQuotation("", new TransactionInterface() {
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
                assertEquals(errorObject.getErrorDescription(), "Invalid Quotation Reference");

            }
        });


    }

    @Test
    public void viewQuotationNullQuotationReferenceSuccess(){

        SDKManager.p2PTransfer.viewQuotation(null, new TransactionInterface() {
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
                assertEquals(errorObject.getErrorDescription(), "Invalid Quotation Reference");

            }
        });


    }

    /*********************************Request State**********************************/

    @Test
    public void viewRequestStateNullServerCorrelationIdSuccess() {

        SDKManager.p2PTransfer.viewRequestState(null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            }

            @SuppressWarnings("EmptyMethod")
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

        SDKManager.p2PTransfer.viewRequestState("", new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
            }

            @SuppressWarnings("EmptyMethod")
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
        SDKManager.p2PTransfer.viewTransaction(null, new TransactionInterface() {
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
        SDKManager.p2PTransfer.viewTransaction("", new TransactionInterface() {
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





}

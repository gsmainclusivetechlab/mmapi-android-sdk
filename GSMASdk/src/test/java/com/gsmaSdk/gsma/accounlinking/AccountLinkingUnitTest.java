package com.gsmaSdk.gsma.accounlinking;

import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.AccountLinkInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.AccountIdentifier;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.Link;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.RequestingOrganisation;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"EmptyMethod", "unused", "ConstantConditions"})
public class AccountLinkingUnitTest {


    /******************************Create Account Link******************/

    @Test
    public  void createAccountLinkNullIdentifierNullLinkSuccess(){
        SDKManager.accountLinking.createAccountLinking(NotificationMethod.POLLING, "", null, null, new RequestStateInterface() {
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
    public  void createAccountLinkNullIdentifierLinkSuccess(){
        Link link=getAccountLinkingObject();
        SDKManager.accountLinking.createAccountLinking(NotificationMethod.POLLING, "", null, link, new RequestStateInterface() {
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
    public  void createAccountLinkEmptyIdentifierLinkSuccess(){
        Link link=getAccountLinkingObject();
        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        SDKManager.accountLinking.createAccountLinking(NotificationMethod.POLLING, "", identifierArrayList, link, new RequestStateInterface() {
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
    public  void createAccountLinkEmptyIdentifierNullLinkSuccess(){
        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        SDKManager.accountLinking.createAccountLinking(NotificationMethod.POLLING, "", identifierArrayList, null, new RequestStateInterface() {
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
    public  void createAccountLinkIdentifierNullLinkSuccess(){
        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        Identifier identifier=new Identifier();
        identifierArrayList.add(identifier);

        SDKManager.accountLinking.createAccountLinking(NotificationMethod.POLLING, "", identifierArrayList, null, new RequestStateInterface() {
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

        SDKManager.accountLinking.viewRequestState(null, new RequestStateInterface() {
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

        SDKManager.accountLinking.viewRequestState("", new RequestStateInterface() {
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




    /*************************viewAccountLink*********************************/


    @Test
    public void viewAccountLinkEmptyIdentifierEmptyLinkRefSuccess(){

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        SDKManager.accountLinking.viewAccountLink(identifierArrayList, "", new AccountLinkInterface() {
            @Override
            public void onAccountLinkSuccess(Link accountLinks) {

            }

            @Override
            public void onAccountLinkFailure(GSMAError gsmaError) {

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
    public void viewAccountLinkEmptyIdentifierNullLinkRefSuccess(){

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        SDKManager.accountLinking.viewAccountLink(identifierArrayList, null, new AccountLinkInterface() {
            @Override
            public void onAccountLinkSuccess(Link accountLinks) {

            }

            @Override
            public void onAccountLinkFailure(GSMAError gsmaError) {

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
    public void viewAccountLinkEmptyIdentifierLinkRefSuccess(){

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        SDKManager.accountLinking.viewAccountLink(identifierArrayList, "REF-1012", new AccountLinkInterface() {
            @Override
            public void onAccountLinkSuccess(Link accountLinks) {

            }

            @Override
            public void onAccountLinkFailure(GSMAError gsmaError) {

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
    public void viewAccountLinkNullIdentifierEmptyLinkRefSuccess(){

        SDKManager.accountLinking.viewAccountLink(null, "", new AccountLinkInterface() {
            @Override
            public void onAccountLinkSuccess(Link accountLinks) {

            }

            @Override
            public void onAccountLinkFailure(GSMAError gsmaError) {

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
    public void viewAccountLinkNullIdentifierNullLinkRefSuccess(){

        SDKManager.accountLinking.viewAccountLink(null, null, new AccountLinkInterface() {
            @Override
            public void onAccountLinkSuccess(Link accountLinks) {

            }

            @Override
            public void onAccountLinkFailure(GSMAError gsmaError) {

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
    public void viewAccountLinkNullIdentifierLinkRefSuccess(){

        SDKManager.accountLinking.viewAccountLink(null, "REF-101", new AccountLinkInterface() {
            @Override
            public void onAccountLinkSuccess(Link accountLinks) {

            }

            @Override
            public void onAccountLinkFailure(GSMAError gsmaError) {

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
    public void viewAccountLinkIdentifierEmptyLinkRefSuccess(){

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        Identifier identifier=new Identifier();
        identifier.setKey("accountno");
        identifier.setValue("1");
        identifierArrayList.add(identifier);

        SDKManager.accountLinking.viewAccountLink(identifierArrayList, "", new AccountLinkInterface() {
            @Override
            public void onAccountLinkSuccess(Link accountLinks) {

            }

            @Override
            public void onAccountLinkFailure(GSMAError gsmaError) {

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
    public void viewAccountLinkIdentifierNullLinkRefSuccess(){

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        Identifier identifier=new Identifier();
        identifier.setKey("accountno");
        identifier.setValue("1");
        identifierArrayList.add(identifier);

        SDKManager.accountLinking.viewAccountLink(identifierArrayList, null, new AccountLinkInterface() {
            @Override
            public void onAccountLinkSuccess(Link accountLinks) {

            }

            @Override
            public void onAccountLinkFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid transaction reference");
            }
        });

    }


    /********************Create Transfer Transaction********************/


    @Test
    public void createTransferNullTransactionSuccess() {

        SDKManager.accountLinking.createTransferTransaction(NotificationMethod.POLLING, "", null, new RequestStateInterface() {
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
    /*******************************View transaction******************/

    @Test
    public void viewTransactionNullReferenceSuccess() {
        SDKManager.accountLinking.viewTransaction(null, new TransactionInterface() {
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
        SDKManager.accountLinking.viewTransaction("", new TransactionInterface() {
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





    private Link getAccountLinkingObject() {
        Link  accountLinkingObject = new Link();

        //set amount and currency

        accountLinkingObject.setMode("active");
        accountLinkingObject.setStatus("both");

        ArrayList<AccountIdentifier> sourceAccountIdentifiersList = new ArrayList<>();
        ArrayList<CustomDataItem> customDataList = new ArrayList<>();
        AccountIdentifier sourceAccountIdentifiersItem = new AccountIdentifier();
        CustomDataItem customDataItem = new CustomDataItem();
        RequestingOrganisation requestingOrganisationItem = new RequestingOrganisation();

        //Source Account Identifiers
        sourceAccountIdentifiersItem.setKey("accountid");
        sourceAccountIdentifiersItem.setValue("2999");
        sourceAccountIdentifiersList.add(sourceAccountIdentifiersItem);

        //Custom Data
        customDataItem.setKey("keytest");
        customDataItem.setValue("keyvalue");
        customDataList.add(customDataItem);

        //Requesting Organisation data
        requestingOrganisationItem.setRequestingOrganisationIdentifierType("organisationid");
        requestingOrganisationItem.setRequestingOrganisationIdentifier("12345");


        //add details to account linking object
        accountLinkingObject.setSourceAccountIdentifiers(sourceAccountIdentifiersList);
        accountLinkingObject.setCustomData(customDataList);
        accountLinkingObject.setRequestingOrganisation(requestingOrganisationItem);
        return accountLinkingObject;

    }


    /***************************Reversal****************************************/

    @Test
    public void createReversalNullReferenceIdSuccess() {

        Reversal reversalObject = new Reversal();
        reversalObject.setType("reversal");

        SDKManager.accountLinking.createReversal(NotificationMethod.POLLING, "", null, reversalObject, new RequestStateInterface() {
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

        SDKManager.accountLinking.createReversal(NotificationMethod.POLLING, "", "", reversalObject, new RequestStateInterface() {
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
        SDKManager.accountLinking.createReversal(NotificationMethod.POLLING, "", "REF-10121", null, new RequestStateInterface() {
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
        SDKManager.accountLinking.createReversal(NotificationMethod.POLLING, "", null, null, new RequestStateInterface() {
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
        SDKManager.accountLinking.createReversal(NotificationMethod.POLLING, "", "", null, new RequestStateInterface() {
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


    /************************************Balance************************************/


    @Test
    public void viewBalanceEmptyIdentifierSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.accountLinking.viewAccountBalance(identifierArrayList, new BalanceInterface() {
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
        SDKManager.accountLinking.viewAccountBalance(null, new BalanceInterface() {
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

    /********************************View Response***********************************/

    @Test
    public void viewResponseEmptyCorrelationIdSuccess() {

        SDKManager.accountLinking.viewResponse("", new MissingResponseInterface() {
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

        SDKManager.accountLinking.viewResponse(null, new MissingResponseInterface() {
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



    /****************************View Account Transactions**********************/


    @Test
    public void viewAccountEmptyIdentifierEmptyFilterSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();

        TransactionFilter transactionFilter = new TransactionFilter();

        SDKManager.accountLinking.viewAccountTransactions(identifierArrayList, transactionFilter, new RetrieveTransactionInterface() {
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
        SDKManager.accountLinking.viewAccountTransactions(identifierArrayList, null, new RetrieveTransactionInterface() {
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


        SDKManager.accountLinking.viewAccountTransactions(identifierArrayList, transactionFilter, new RetrieveTransactionInterface() {
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

        SDKManager.accountLinking.viewAccountTransactions(null, transactionFilter, new RetrieveTransactionInterface() {
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

        SDKManager.accountLinking.viewAccountTransactions(null, null, new RetrieveTransactionInterface() {
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


        SDKManager.accountLinking.viewAccountTransactions(null, transactionFilter, new RetrieveTransactionInterface() {
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


        SDKManager.accountLinking.viewAccountTransactions(identifierArrayList, null, new RetrieveTransactionInterface() {
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



}

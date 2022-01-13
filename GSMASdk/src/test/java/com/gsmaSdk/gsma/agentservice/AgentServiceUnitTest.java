package com.gsmaSdk.gsma.agentservice;

import static org.junit.Assert.assertEquals;

import android.widget.ArrayAdapter;

import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.AccountHolderInterface;
import com.gsmaSdk.gsma.interfaces.AccountInterface;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeItemInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.Account;
import com.gsmaSdk.gsma.models.account.AccountHolderName;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.transaction.PatchData;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

import org.junit.Test;

import java.util.ArrayList;

public class AgentServiceUnitTest {


    /**********************Create Withdrawal Transaction**********************/

    @Test
    public void createWithdrawalNullTransactionSuccess() {

        SDKManager.agentService.createWithdrawalTransaction(NotificationMethod.POLLING, "", null, new RequestStateInterface() {
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


            }
        });

    }

    /*********************************Request State**********************************/

    @Test
    public void viewRequestStateNullServerCorrelationIdSuccess() {

        SDKManager.agentService.viewRequestState(null, new RequestStateInterface() {
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

        SDKManager.agentService.viewRequestState("", new RequestStateInterface() {
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

    /*******************************View transaction******************************/

    @Test
    public void viewTransactionNullReferenceSuccess() {
        SDKManager.agentService.viewTransaction(null, new TransactionInterface() {
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
        SDKManager.agentService.viewTransaction("", new TransactionInterface() {
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


    /***********************Authorisation Code*********************/

    @Test
    public void createAuthCodeEmptyIdentifierSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();

        AuthorisationCode authorisationCodeRequest = new AuthorisationCode();
        authorisationCodeRequest.setAmount("200.00");
        authorisationCodeRequest.setRequestDate("2021-10-18T10:43:27.405Z");
        authorisationCodeRequest.setCurrency("RWF");
        authorisationCodeRequest.setCodeLifetime(1);

        SDKManager.agentService.createAuthorisationCode(identifierArrayList, NotificationMethod.POLLING, "", authorisationCodeRequest, new RequestStateInterface() {
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

        SDKManager.agentService.createAuthorisationCode(null, NotificationMethod.POLLING, "", authorisationCodeRequest, new RequestStateInterface() {
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

    /****************************View AuthorisationCode*************/

    @Test
    public void viewAuthCodeEmptyIdentifierEmptyAuthCodeSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.agentService.viewAuthorisationCode(identifierArrayList, "", new AuthorisationCodeItemInterface() {
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
        SDKManager.agentService.viewAuthorisationCode(identifierArrayList, null, new AuthorisationCodeItemInterface() {
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
        SDKManager.agentService.viewAuthorisationCode(identifierArrayList, "REF-10102", new AuthorisationCodeItemInterface() {
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
        SDKManager.agentService.viewAuthorisationCode(null, "", new AuthorisationCodeItemInterface() {
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
        SDKManager.agentService.viewAuthorisationCode(null, null, new AuthorisationCodeItemInterface() {
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

        SDKManager.agentService.viewAuthorisationCode(null, "REF-10102", new AuthorisationCodeItemInterface() {
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

        SDKManager.agentService.viewAuthorisationCode(identifierArrayList, "", new AuthorisationCodeItemInterface() {
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

        SDKManager.agentService.viewAuthorisationCode(identifierArrayList, null, new AuthorisationCodeItemInterface() {
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


    /*******************************View Account Name**************************/

    @Test
    public void viewAccountNameEmptyIdentifierSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.agentService.viewAccountName(identifierArrayList, new AccountHolderInterface() {
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
    public void viewAccountNameNullIdentifierSuccess() {
        SDKManager.agentService.viewAccountName(null, new AccountHolderInterface() {
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


    /********************Create Deposit Transaction********************/


    @Test
    public void createDepositNullTransactionSuccess() {

        SDKManager.agentService.createDepositTransaction(NotificationMethod.POLLING, "", null, new RequestStateInterface() {
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

        SDKManager.agentService.createReversal(NotificationMethod.POLLING, "", "", reversalObject, new RequestStateInterface() {
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
        SDKManager.agentService.createReversal(NotificationMethod.POLLING, "", "REF-10121", null, new RequestStateInterface() {
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
        SDKManager.agentService.createReversal(NotificationMethod.POLLING, "", null, null, new RequestStateInterface() {
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

    /************************Create Account******************************/


    @Test
    public void createAccountNullAccountSuccess() {

        SDKManager.agentService.createAccount(NotificationMethod.POLLING, "", null, new RequestStateInterface() {
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

    /************************View Account******************************/


    @Test
    public void viewAccountEmptyIdentifierSuccess() {

        ArrayList<Identifier> identifierArrayList = new ArrayList<>();

        SDKManager.agentService.viewAccount(identifierArrayList, new AccountInterface() {
            @Override
            public void onAccountSuccess(Account account) {

            }

            @Override
            public void onAccountFailure(GSMAError gsmaError) {

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
    public void viewAccountNullIdentifierSuccess() {
        SDKManager.agentService.viewAccount(null, new AccountInterface() {
            @Override
            public void onAccountSuccess(Account account) {

            }

            @Override
            public void onAccountFailure(GSMAError gsmaError) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });


    }

    /************************** Update KYC Status******************************/

    @Test
    public void updateAccountEmptyIdentifierIdentity() {

        ArrayList<PatchData> patchDataArrayList = new ArrayList<>();
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();


        SDKManager.agentService.updateAccountIdentity(NotificationMethod.POLLING, "", "", patchDataArrayList, identifierArrayList, new RequestStateInterface() {
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
                assertEquals(errorObject.getErrorDescription(), "Invalid Patch Object");
            }
        });
    }


    @Test
    public void updateAccountNullIdentifierIdentity() {
        SDKManager.agentService.updateAccountIdentity(NotificationMethod.POLLING, "", null, null, null, new RequestStateInterface() {
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
                assertEquals(errorObject.getErrorDescription(), "Invalid Patch Object");
            }
        });

    }

    @Test
    public void updateAccountNullIdentity() {

        PatchData patchObject = new PatchData();
        patchObject.setOp("replace");
        patchObject.setPath("/kycVerificationStatus");
        patchObject.setValue("verified");
        ArrayList<PatchData> patchDataArrayList = new ArrayList<>();
        patchDataArrayList.add(patchObject);

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        Identifier identifier=new Identifier();
        identifier.setKey("walledtid");
        identifier.setValue("1");
        identifierArrayList.add(identifier);

        SDKManager.agentService.updateAccountIdentity(NotificationMethod.POLLING, "", null, patchDataArrayList, identifierArrayList, new RequestStateInterface() {
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
                assertEquals(errorObject.getErrorDescription(), "Invalid Identity Id");
            }
        });
    }


    @Test
    public void updateAccountEmptyIdentity() {

        PatchData patchObject = new PatchData();
        patchObject.setOp("replace");
        patchObject.setPath("/kycVerificationStatus");
        patchObject.setValue("verified");
        ArrayList<PatchData> patchDataArrayList = new ArrayList<>();
        patchDataArrayList.add(patchObject);

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        Identifier identifier=new Identifier();
        identifier.setKey("walledtid");
        identifier.setValue("1");
        identifierArrayList.add(identifier);

        SDKManager.agentService.updateAccountIdentity(NotificationMethod.POLLING, "", "", patchDataArrayList, identifierArrayList, new RequestStateInterface() {
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
                assertEquals(errorObject.getErrorDescription(), "Invalid Identity Id");
            }
        });
    }

    @Test
    public void updateAccountNullPatchObject() {

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        Identifier identifier=new Identifier();
        identifier.setKey("walledtid");
        identifier.setValue("1");
        identifierArrayList.add(identifier);

        SDKManager.agentService.updateAccountIdentity(NotificationMethod.POLLING, "", "REF-10344", null, identifierArrayList, new RequestStateInterface() {
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
                assertEquals(errorObject.getErrorDescription(), "Invalid Patch Object");
            }
        });
    }


    @Test
    public void updateAccountEmptyPatchObject() {

        ArrayList<PatchData> patchDataArrayList=new ArrayList<>();

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();
        Identifier identifier=new Identifier();
        identifier.setKey("walledtid");
        identifier.setValue("1");
        identifierArrayList.add(identifier);

        SDKManager.agentService.updateAccountIdentity(NotificationMethod.POLLING, "", "REF-10344", patchDataArrayList, identifierArrayList, new RequestStateInterface() {
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
                assertEquals(errorObject.getErrorDescription(), "Invalid Patch Object");
            }
        });
    }

    @Test
    public void updateAccountEmptyIdentifier() {

        PatchData patchObject = new PatchData();
        patchObject.setOp("replace");
        patchObject.setPath("/kycVerificationStatus");
        patchObject.setValue("verified");
        ArrayList<PatchData> patchDataArrayList = new ArrayList<>();
        patchDataArrayList.add(patchObject);

        ArrayList<Identifier> identifierArrayList=new ArrayList<>();


        SDKManager.agentService.updateAccountIdentity(NotificationMethod.POLLING, "", "REF-10344", patchDataArrayList, identifierArrayList, new RequestStateInterface() {
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
    public void updateAccountNullIdentifier() {

        PatchData patchObject = new PatchData();
        patchObject.setOp("replace");
        patchObject.setPath("/kycVerificationStatus");
        patchObject.setValue("verified");
        ArrayList<PatchData> patchDataArrayList = new ArrayList<>();
        patchDataArrayList.add(patchObject);

        SDKManager.agentService.updateAccountIdentity(NotificationMethod.POLLING, "", "REF-10344", patchDataArrayList, null, new RequestStateInterface() {
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

        SDKManager.agentService.viewResponse("", new MissingResponseInterface() {
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

        SDKManager.agentService.viewResponse(null, new MissingResponseInterface() {
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
    public void viewAccountTransactionEmptyIdentifierSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();

        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        SDKManager.agentService.viewAccountTransactions(identifierArrayList, transactionFilter, new RetrieveTransactionInterface() {
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
        SDKManager.agentService.viewAccountTransactions(null, transactionFilter, new RetrieveTransactionInterface() {
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

    /************************************Balance************************************/
    @Test
    public void viewBalanceEmptyIdentifierSuccess() {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.agentService.viewAccountBalance(identifierArrayList, new BalanceInterface() {
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
        SDKManager.agentService.viewAccountBalance(null, new BalanceInterface() {
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


}

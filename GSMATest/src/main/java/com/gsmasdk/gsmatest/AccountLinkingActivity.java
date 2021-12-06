package com.gsmasdk.gsmatest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.NotificationMethod;

import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;

import com.gsmaSdk.gsma.interfaces.AccountLinkInterface;

import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.AccountLinkingObject;
import com.gsmaSdk.gsma.models.account.AccountLinks;
import com.gsmaSdk.gsma.models.account.Identifier;

import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.account.Balance;

import com.gsmaSdk.gsma.models.account.SourceAccountIdentifiersItem;

import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.RequestingOrganisation;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.common.CreditPartyItem;
import com.gsmaSdk.gsma.models.common.DebitPartyItem;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class AccountLinkingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";
    private ReversalObject reversalObject;

    private String transactionRef = "";
    private String linkReference = "";
    private String serverCorrelationId;

    private TransactionRequest transactionRequest;
    private AccountLinkingObject accountLinkingObject;
    ArrayList<Identifier> identifierArrayList;


    private final String[] accountLinkingArray = {
            "Set Up a Account Link ",
            "Request State",
            "View a Account Link",
            "Perform a Transfer for a Linked Account",
            "View Transaction",
            "Perform a Transfer Reversal",
            "Obtain a Financial Service Provider Balance",
            "Retrieve Transfers for a Financial Service Provider",
            "Retrieve a Missing API Response",
    };

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_linking);

        setTitle("Account Linking");

        ListView listUseCases = findViewById(R.id.accountLinkList);

        CustomUseCaseAdapter customListAdapter = new CustomUseCaseAdapter(AccountLinkingActivity.this, new ArrayList(Arrays.asList(accountLinkingArray)));
        listUseCases.setAdapter(customListAdapter);
        listUseCases.setOnItemClickListener(this);
        txtResponse = findViewById(R.id.txtAccountLinkResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());
        progressdialog = Utils.initProgress(AccountLinkingActivity.this);

        checkServiceAvailability();
        createPaymentReversalObject();
        createAccountIdentifier();


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                //Set Up a Account Link ;
                createAccountLinkingObject();
                break;
            case 1:
                //Request State
                requestState();
                break;
            case 2:
                //View a Account Link
                viewAccountLink();
                break;
            case 3:
                //Perform a Transfer for a Linked Account
                createTransactionObject();
                break;
            case 4:
                //View Transaction
                viewTransaction();

                break;
            case 5:
                //reversal
                paymentReversal();
                break;
            case 6:
                //Obtain a Financial Service Provider Balance
                balanceCheck();
                break;
            case 7:
                // Retrieve Transfers for a Financial Service Provider
                retrieveTransactionFSP();
                break;

            case 8:
                // Retrieve a Missing API Response
                getMissingTransaction();
            default:
                break;
        }

    }


    private void createTransactionObject() {
        transactionRequest = new TransactionRequest();
        //set amount and currency
        transactionRequest.setAmount("200");
        transactionRequest.setCurrency("RWF");

        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        //debit party
        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("2999");
        debitPartyList.add(debitPartyItem);

        //credit party
        creditPartyItem.setKey("linkref");
        creditPartyItem.setValue(linkReference);
        creditPartyList.add(creditPartyItem);

        //add debit and credit party to transaction object
        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        performTransfer();
    }

    private void createAccountLinkingObject() {
        accountLinkingObject = new AccountLinkingObject();
        //set amount and currency
        accountLinkingObject.setMode("active");
        accountLinkingObject.setStatus("both");

        ArrayList<SourceAccountIdentifiersItem> sourceAccountIdentifiersList = new ArrayList<>();
        ArrayList<CustomDataItem> customDataList = new ArrayList<>();
        SourceAccountIdentifiersItem sourceAccountIdentifiersItem = new SourceAccountIdentifiersItem();
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

        createAccountLinking();

    }

    /**
     * Create Payment Reversal Object.
     */
    private void createPaymentReversalObject() {
        reversalObject = new ReversalObject();
        reversalObject.setReversal("reversal");
    }


    private void createAccountIdentifier() {
        identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");

        identifierArrayList.add(identifierAccount);

    }


    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability() {
        showLoading();
        SDKManager.accountLinking.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                Utils.showToast(AccountLinkingActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    /**
     * Get the request state of a transaction
     */
    private void requestState() {
        showLoading();
        SDKManager.accountLinking.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                transactionRef = requestStateObject.getObjectReference();
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    /**
     * Perform Account Linking Transfer
     */
    private void performTransfer() {

        showLoading();
        SDKManager.accountLinking.createTransferTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(AccountLinkingActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                Log.d(SUCCESS, "onRequestSuccess " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Log.d(FAILURE, "onRequestFailure " + new Gson().toJson(gsmaError));
                txtResponse.setText(new Gson().toJson(gsmaError));

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    /**
     * Create Account Linking
     */
    private void createAccountLinking() {
        showLoading();
        SDKManager.accountLinking.createAccountLinking(NotificationMethod.POLLING, "", identifierArrayList, accountLinkingObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(AccountLinkingActivity.this, "Success");
                Log.d(SUCCESS, "onRecurringPaymentSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                Log.d(FAILURE, "onRecurringPaymentFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    /**
     * View Account Link
     */
    private void viewAccountLink() {
        showLoading();
        SDKManager.accountLinking.viewAccountLink(identifierArrayList, transactionRef, new AccountLinkInterface() {
            @Override
            public void onAccountLinkSuccess(AccountLinks accountLinks) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Success");
                linkReference = accountLinks.getLinkReference();
                txtResponse.setText(new Gson().toJson(accountLinks));
                Log.d(SUCCESS, "onAccountLinkSuccess: " + new Gson().toJson(accountLinks));
            }

            @Override
            public void onAccountLinkFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onAccountLinkFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });
    }


    /**
     * View Transaction-View the transaction Details
     */
    private void viewTransaction() {
        showLoading();
        SDKManager.accountLinking.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(TransactionRequest transactionRequest) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(transactionRequest));
                Utils.showToast(AccountLinkingActivity.this, "Success");
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionRequest));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }

        });
    }

    //Retrieve Transaction for an FSP
    private void retrieveTransactionFSP() {
        showLoading();
        SDKManager.accountLinking.viewAccountTransactions(identifierArrayList, 0, 2, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transaction));
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }


    /**
     * Payment Reversal
     */
    private void paymentReversal() {
        showLoading();
        SDKManager.accountLinking.createReversal(NotificationMethod.POLLING, "", "REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Success");
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                hideLoading();
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }


    //Check Balance
    private void balanceCheck() {
        showLoading();
        SDKManager.accountLinking.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @SuppressWarnings("unused")
            @Override
            public void onBalanceSuccess(Balance balance) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(balance));
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

            @SuppressWarnings("unused")
            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    //Retrieve a missing Transaction
    private void getMissingTransaction() {
        showLoading();

        SDKManager.accountLinking.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(missingResponse));
                Log.d(SUCCESS, "onMissingTransactionSuccess: " + new Gson().toJson(missingResponse));
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });


    }

    public void showLoading() {
        progressdialog.show();
    }

    public void hideLoading() {
        progressdialog.dismiss();
    }


}
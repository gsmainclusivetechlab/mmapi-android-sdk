package com.gsmasdk.gsmatest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.AccountLinkInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
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
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ALL")
public class AccountLinkingActivity extends AppCompatActivity implements CustomUseCaseRecyclerAdapter.ItemClickListener {

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";
    private Reversal reversalObject;

    private String transactionRef = "";
    private String linkReference = "";
    private String serverCorrelationId;
    private StringBuilder sbOutPut;

    private Transaction transactionRequest;
    private Link accountLinkingObject;
    ArrayList<Identifier> identifierArrayList;
    private CustomUseCaseRecyclerAdapter customRecyclerAdapter;


    private final String[] accountLinkingArray = {
            "Setup an Account Link",
            "Perform a Transfer for a Linked Account",
            "Perform a Transfer using an Account Link via the Polling Method",
            "Obtain a Financial Service Provider Balance",
            "Retrieve Transfers for a Financial Service Provider",
            "Check for Service Availability",
            "Retrieve a Missing API Response"
    };

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_linking);

        setTitle("Account Linking");

        RecyclerView recyclerView = findViewById(R.id.accountLinkingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        customRecyclerAdapter = new CustomUseCaseRecyclerAdapter(this, true, accountLinkingArray);
        customRecyclerAdapter.setClickListener(this);

        recyclerView.setAdapter(customRecyclerAdapter);

        txtResponse = findViewById(R.id.txtAccountLinkResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());
        progressdialog = Utils.initProgress(AccountLinkingActivity.this);

        createAccountLinkingObject();
        createPaymentReversalObject();


    }


    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                //Set Up a Account Link ;
                 sbOutPut = new StringBuilder();
                 sbOutPut.append("Setup an Account Link - Output\n\n");
                 createAccountLinking(position,NotificationMethod.CALLBACK);
                 break;
            case 1:
                //Perform a Transfer for a Linked Account;
                sbOutPut = new StringBuilder();
                sbOutPut.append("Perform a Transfer for a Linked Account - Output\n\n");
                createAccountLinking(position,NotificationMethod.CALLBACK);


                break;
            case 2:
                //Perform a Transfer for a Linked Account Polling;
                sbOutPut = new StringBuilder();
                sbOutPut.append("Perform a Transfer for a Linked Account via the polling method - Output\n\n");
                createAccountLinking(position,NotificationMethod.POLLING);


                break;
            case 3:
                //Obtain an FSP Balance
                sbOutPut = new StringBuilder();
                sbOutPut.append("Balance -Output\n\n");

                balanceCheck(position);

                break;
            case 4:
                //Retrieve payment
                sbOutPut = new StringBuilder();
                sbOutPut.append("Retrieve Payment -Output\n\n");
                retrieveTransactionFSP(position);

                break;
            case 5:
                //check service availability
                sbOutPut = new StringBuilder();
                sbOutPut.append("Check Service Availability -Output\n\n");
                checkServiceAvailability(position);
                break;
            case 6:
                //missing response
                sbOutPut = new StringBuilder();
                sbOutPut.append("Set Up an account Link -Output\n\n");
                createAccountLinking(position,NotificationMethod.CALLBACK);

                break;
            default:
                break;
        }

    }




    private void createAccountLinkingObject() {
        accountLinkingObject = new Link();

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


    }







    /**
     * Create Payment Reversal Object.
     */
    private void createPaymentReversalObject() {
        reversalObject = new Reversal();
        reversalObject.setType("reversal");
    }


    /**
     * Return account Identifier
     */

    private ArrayList<Identifier> createAccountIdentifier(String accountIdentifierKey, String accountIdentifierValue){
        identifierArrayList = new ArrayList<>();
        identifierArrayList.clear();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey(accountIdentifierKey);
        identifierAccount.setValue(accountIdentifierValue);
        identifierArrayList.add(identifierAccount);
        return identifierArrayList;
    }




    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability(int position) {
        showLoading();
        SDKManager.accountLinking.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(serviceAvailability).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(1, position);
                Utils.showToast(AccountLinkingActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    /**
     * Get the request state of a transaction
     */
    private void requestState(int position) {
        sbOutPut.append("\n\nRequest State - Output\n\n");
        SDKManager.accountLinking.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                transactionRef = requestStateObject.getObjectReference();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    hideLoading();
                    txtResponse.setText(sbOutPut);
                } else {
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString()+"\n\n");
                    viewAccountLink(position);
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError)+"\n\n");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
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
    private void performTransfer(int position,NotificationMethod notificationMethod) {

        transactionRequest = new Transaction();
        //set amount and currency
        transactionRequest.setAmount("200");
        transactionRequest.setCurrency("RWF");

        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

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

        sbOutPut.append("Perform Transaction Linked Account-Output\n\n");

        SDKManager.accountLinking.createTransferTransaction(notificationMethod, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                Log.d(SUCCESS, "onRequestSuccess " + new Gson().toJson(requestStateObject));

                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty\n\n");
                    hideLoading();
                    txtResponse.setText(sbOutPut);
                } else {
                    serverCorrelationId = requestStateObject.getServerCorrelationId();
                    if(position==2){
                        sbOutPut.append(new Gson().toJson(requestStateObject)+"\n\n");
                        requestStateTransaction(position);
                    }else if(position==6){
                        sbOutPut.append(new Gson().toJson(requestStateObject)+"\n\n");
                        getMissingTransaction(position);
                    }else{
                        hideLoading();
                        customRecyclerAdapter.setStatus(1, position);
                        sbOutPut.append(new Gson().toJson(requestStateObject)+"\n\n");
                        txtResponse.setText(sbOutPut);

                    }

                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError)+"\n\n");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    /**
     * Get the request state of a transaction
     */
    private void requestStateTransaction(int position) {
        sbOutPut.append("\n\nRequest State-Output\n\n");
        SDKManager.accountLinking.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                transactionRef = requestStateObject.getObjectReference();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    hideLoading();
                } else {
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString()+"\n\n");
                    viewTransaction(position);
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError)+"\n\n");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
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
    private void createAccountLinking(int position,NotificationMethod notificationMethod) {
        showLoading();
        SDKManager.accountLinking.createAccountLinking(notificationMethod, "", createAccountIdentifier("accountid","2999"), accountLinkingObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());

                } else {
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString() + "\n\n");
                    if(position==1||position==2){
                        requestState(position);
                    }
                    else{
                        Utils.showToast(AccountLinkingActivity.this, "Success");
                        customRecyclerAdapter.setStatus(1, position);
                        txtResponse.setText(sbOutPut.toString());
                    }

                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
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
    private void createAccountLinkingPolling(int position) {
        showLoading();
        SDKManager.accountLinking.createAccountLinking(NotificationMethod.CALLBACK, "", identifierArrayList, accountLinkingObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    hideLoading();
                    txtResponse.setText(sbOutPut);
                } else {
                    Utils.showToast(AccountLinkingActivity.this, "Success");
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString() + "\n\n");
                    requestState(position);
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(errorObject)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
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
    private void viewAccountLink(int position) {
        sbOutPut.append("\n\n View Account link - Output\n\n");
        SDKManager.accountLinking.viewAccountLink(createAccountIdentifier("accountid","2999"), transactionRef, new AccountLinkInterface() {
            @Override
            public void onAccountLinkSuccess(Link accountLinks) {
                Log.d(SUCCESS, "onAccountLinkSuccess: " + new Gson().toJson(accountLinks));
                if (accountLinks == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                    hideLoading();
                } else {
                    linkReference = accountLinks.getLinkReference();
                    sbOutPut.append(new Gson().toJson(accountLinks)+"\n\n");
                    if(position==1){
                        performTransfer(position,NotificationMethod.CALLBACK);
                    }else if(position==2){
                        performTransfer(position,NotificationMethod.POLLING);
                    }else if(position==6){
                        performTransfer(position,NotificationMethod.CALLBACK);

                    }

                }
            }

            @Override
            public void onAccountLinkFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError)+"\n\n");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(errorObject)+"\n\n");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(errorObject));
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }


    /**
     * View Transaction-View the transaction Details
     */
    private void viewTransaction(int position) {
        sbOutPut.append("\n\nView Transaction - Output\n\n");
        SDKManager.accountLinking.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(errorObject)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
            }

            @Override
            public void onTransactionSuccess(Transaction transactionRequest) {
                hideLoading();
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionRequest));
                if (transactionRequest == null
                        || transactionRequest.getTransactionReference() == null
                        || transactionRequest.getTransactionStatus() == null
                        || transactionRequest.getCurrency() == null
                        || transactionRequest.getCreditParty() == null
                        || transactionRequest.getDebitParty() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());

                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(transactionRequest)+"\n\n");
                    txtResponse.setText(sbOutPut.toString());
                }

            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError)+"\n\n");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

        });
    }

    //Retrieve Transaction for an FSP
    private void retrieveTransactionFSP(int position) {
        showLoading();

        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(2);
        transactionFilter.setOffset(0);

        SDKManager.accountLinking.viewAccountTransactions(createAccountIdentifier("accountid","2999"), transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {
                hideLoading();

                Transaction transactionRequest = transaction.getTransaction().get(0);
                if (transactionRequest == null
                        || transactionRequest.getTransactionReference() == null
                        || transactionRequest.getTransactionStatus() == null
                        || transactionRequest.getCurrency() == null
                        || transactionRequest.getCreditParty() == null
                        || transactionRequest.getDebitParty() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    sbOutPut.append(new Gson().toJson(transaction).toString());
                    customRecyclerAdapter.setStatus(1, position);
                    Utils.showToast(AccountLinkingActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }


    /**
     * Payment Reversal
     */
    private void paymentReversal(int position) {
        showLoading();
        SDKManager.accountLinking.createReversal(NotificationMethod.POLLING, "", "REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Success");
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                hideLoading();
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }


    //Check Balance
    private void balanceCheck(int position) {
        showLoading();

        SDKManager.accountLinking.viewAccountBalance(createAccountIdentifier("accountid","1"), new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @SuppressWarnings("unused")
            @Override
            public void onBalanceSuccess(Balance balance) {
                hideLoading();
                if (balance == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(balance).toString());
                    Utils.showToast(AccountLinkingActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

            @SuppressWarnings("unused")
            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }

    //Retrieve a missing Transaction
    private void getMissingTransaction(int position) {
        sbOutPut.append("\n\nMissing Response -Output\n\n");
        SDKManager.accountLinking.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
                hideLoading();
                if (missingResponse == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(missingResponse).toString());
                    Utils.showToast(AccountLinkingActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onMissingTransactionSuccess: " + new Gson().toJson(missingResponse));
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(AccountLinkingActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AccountLinkingActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
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
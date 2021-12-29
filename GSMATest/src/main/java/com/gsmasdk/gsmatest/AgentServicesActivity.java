package com.gsmasdk.gsmatest;

import androidx.appcompat.app.AppCompatActivity;

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
import com.gsmaSdk.gsma.interfaces.AccountHolderInterface;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeItemInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.AccountHolderName;
import com.gsmaSdk.gsma.models.account.AccountIdentifier;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("ALL")
public class AgentServicesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";

    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";
    private AuthorisationCode authorisationCodeRequest;

    private String transactionRef = "";

    private String serverCorrelationId;
    ArrayList<Identifier> identifierArrayList;
    private Transaction transactionRequest;

    private final String[] agentServiceArray = {
            "Agent Initiated Cash-Out",
            "View Request State",
            "View Transaction",
            "Create Authorization Code",
            "View Authorization Code",
            "Retrieve the Name of the Depositing Customer",
            "Agent Initiated Cash-in",
            "Perform a Transaction Reversal",
            "Create a Mobile Money Account",
            "Retrieve Account Information",
            "Update KYC Verification Status",
            "Obtain an Agent Balance",
            "Retrieve a Set of Transactions for an Account",
            "Retrieve a Missing API Response"

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_service);

        setTitle("Agent Services");

        ListView listUseCases = findViewById(R.id.agentServiceList);

        CustomUseCaseAdapter customListAdapter = new CustomUseCaseAdapter(AgentServicesActivity.this, new ArrayList(Arrays.asList(agentServiceArray)));
        listUseCases.setAdapter(customListAdapter);
        listUseCases.setOnItemClickListener(this);
        txtResponse = findViewById(R.id.txtAgentServiceResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());
        progressdialog = Utils.initProgress(AgentServicesActivity.this);

        checkServiceAvailability();
        createAccountIdentifier();
        createTransactionObject();
        createCodeRequestObject();


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
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                Utils.showToast(AgentServicesActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(AgentServicesActivity.this, "Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    private void createAccountIdentifier() {
        identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("1");

        identifierArrayList.add(identifierAccount);

    }

    public void showLoading() {
        progressdialog.show();
    }

    public void hideLoading() {
        progressdialog.dismiss();
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                //Agent Initiated Cash-Out ;
                createWithdrawalTransaction();
                break;
            case 1:
                //View Request State
                 requestState();
                break;
            case 2:
                //View Transaction
                viewTransaction();
                break;

            case 3:
                //Create Authorization Code
                obtainAuthorizationCode();

                break;
            case 4:
                //View Authorization Code
                viewAuthorizationCode();
                break;
            case 5:
                //Retrieve the Name of the Depositing Customer
                viewAccountName();

                break;
            case 6:
                //Agent Initiated Cash-in
                 createDepositTransaction();

                break;
            case 7:
                // Perform a Transaction Reversal


                break;

            case 8:
                // Create a Mobile Money Account

                break;
            case 9:
                // Retrieve Account Information
                break;
            case 10:
                // Update KYC Verification Status
                break;
            case 11:
                // Obtain an Agent Balance
                break;
            case 12:
                //Retrieve a Set of Transactions for an Account
                break;
            case 13:
                //Retrieve a Missing API Response
                break;

            default:
                break;
        }
    }
    /**
     * Code Request Object for Obtaining Authorisation code.
     */
    private void createCodeRequestObject() {
        authorisationCodeRequest = new AuthorisationCode();
        authorisationCodeRequest.setAmount("200.00");
        authorisationCodeRequest.setRequestDate("2021-10-18T10:43:27.405Z");
        authorisationCodeRequest.setCurrency("RWF");
        authorisationCodeRequest.setCodeLifetime(1);
    }

    /**
     * View Account Name
     */
    private void viewAccountName() {
        showLoading();
        SDKManager.agentService.viewAccountName(identifierArrayList, new AccountHolderInterface() {
            @Override
            public void onRetrieveAccountInfoSuccess(AccountHolderName accountHolderObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(accountHolderObject));
                Utils.showToast(AgentServicesActivity.this, "Success");
                Log.d(SUCCESS, "onRetrieveAccountInfoSuccess: " + new Gson().toJson(accountHolderObject));
            }

            @Override
            public void onRetrieveAccountInfoFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRetrieveAccountInfoFailure: " + new Gson().toJson(gsmaError));
            }


            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

        });
    }

    /**
     * View Authorization Code
     *
     */

    private void viewAuthorizationCode() {
        showLoading();
        SDKManager.agentService.viewAuthorisationCode(identifierArrayList, transactionRef, new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(authorisationCodeItem));
                Log.d(SUCCESS, "onAuthorizationCodeItem: " + new Gson().toJson(authorisationCodeItem));
            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onAuthorizationCodeFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, ""+errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });

    }


    /**
     * Authorization Code
     */
    private void obtainAuthorizationCode() {
        showLoading();
        SDKManager.agentService.createAuthorisationCode(identifierArrayList,NotificationMethod.POLLING,"", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
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


    private  void createWithdrawalTransaction(){
        showLoading();
        SDKManager.agentService.createWithdrawalTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(AgentServicesActivity.this, "Success");
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(AgentServicesActivity.this, "Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });

    }

    private  void createDepositTransaction(){
        showLoading();
        SDKManager.agentService.createDepositTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(AgentServicesActivity.this, "Success");
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(AgentServicesActivity.this, "Failure");
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
     * Transaction Object for Merchant Pay.
     */
    private void createTransactionObject() {
        transactionRequest = new Transaction();
        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();

        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

        debitPartyItem.setKey("walletid");
        debitPartyItem.setValue("1");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("msisdn");
        creditPartyItem.setValue("+44012345678");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("200.00");
        transactionRequest.setCurrency("RWF");

    }

    /**
     * Get the request state of a transaction
     */
    private void requestState() {
        showLoading();
        SDKManager.agentService.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Success");
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
     * View Transaction-View the transaction Details
     */
    private void viewTransaction() {
        showLoading();
        SDKManager.agentService.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(Transaction transactionRequest) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(transactionRequest));
                Utils.showToast(AgentServicesActivity.this, "Success");
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionRequest));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }

        });
    }

}


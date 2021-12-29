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
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;

import java.util.ArrayList;
import java.util.Arrays;

public class AgentServicesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";

    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";

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

                break;
            case 1:
                //View Request State

                break;
            case 2:
                //View Transaction
                break;

            case 3:
                //Create Authorization Code

                break;
            case 4:
                //View Authorization Code

                break;
            case 5:
                //Retrieve the Name of the Depositing Customer

                break;
            case 6:
                //Agent Initiated Cash-in

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
}
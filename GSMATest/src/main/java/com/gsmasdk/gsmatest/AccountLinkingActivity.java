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
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

import java.util.ArrayList;
import java.util.Arrays;

public class AccountLinkingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";
    private ReversalObject reversalObject;


    private String transactionRef = "";
    private String serverCorrelationId;

    private TransactionRequest transactionRequest;
    ArrayList<Identifier> identifierArrayList;
;

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
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         switch (position) {
                    case 0:
                        //Set Up a Account Link ;

                        break;
                    case 1:
                        //Request State

                        break;
                    case 2:
                       //View a Account Link
                        break;
                    case 3:
                        //Perform a Transfer Reversal

                        break;
                    case 4:
                      //View Transaction
                        break;
                    case 5:
                       //reversal
                        break;
                    case 6:
                        //Obtain a Financial Service Provider Balance

                        break;
                    case 7:
                        // Retrieve Transfers for a Financial Service Provider

                    case 8:
                        // Retrieve a Missing API Response
                    default:
                        break;
         }
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


    public void showLoading() {
        progressdialog.show();
    }

    public void hideLoading() {
        progressdialog.dismiss();
    }

}
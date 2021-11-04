package com.gsmasdk.gsmatest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.enums.Environment;
import com.gsmaSdk.gsma.interfaces.PaymentInitialiseInterface;
import com.gsmaSdk.gsma.manager.PreferenceManager;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.network.retrofit.PaymentConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("ALL")
public class LandingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static String LOG_TAG = "GSMA-SAMPLE";
    private Toast toastText;
    private String[] usecasesArray = {
            "Merchant Payments",
            "Disbursements",
            "International Transfers",
            "P2P Transfers",
            "Recurring Payments",
            "Account Linking",
            "Bill Payments",
            "Agent Services"
    };
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        setTitle("GSMA Sample Application");
        ListView listUseCases = findViewById(R.id.usesCasesList);
        CustomUseCaseAdapter customListAdapter = new CustomUseCaseAdapter(LandingActivity.this, new ArrayList(Arrays.asList(usecasesArray)));
        listUseCases.setAdapter(customListAdapter);
        listUseCases.setOnItemClickListener(this);
        progressdialog = Utils.initProgress(LandingActivity.this);
        showLoading();
        Utils.showToast(LandingActivity.this,"Configuring SDK...");

        /**
         * Initialise payment configuration with the following
         * consumerKey - provided by Client
         * consumerSecret - provided by Client
         * authenticationType - requried level of authentication
         * callbackUrl - server url to which long running operation responses are delivered
         * environment - sandbox or production
         */
        PaymentConfiguration.
                init("59vthmq3f6i15v6jmcjskfkmh",
                        "ef8tl4gihlpfd7r8jpc1t1nda33q5kcnn32cj375lq6mg2nv7rb",
                        AuthenticationType.STANDARD_LEVEL,
                        "https://93248bb1-c64e-4961-bacf-b1d4aaa103bc.mock.pstmn.io/callback",
                        Environment.SANDBOX);

        /**
         * Initialise the preference objects.
         */
        PreferenceManager.getInstance().init(this);
        /**
         * GSMAApi initialization
         */
        GSMAApi.getInstance().init(this, new PaymentInitialiseInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(LandingActivity.this,"Validation Error");
                Log.d(LOG_TAG, "Validation Error: " + errorObject);
            }

            @Override
            public void onSuccess(Token token) {
                hideLoading();
                Utils.showToast(LandingActivity.this,"Success");
                Log.d(LOG_TAG, "onSuccess : " + new Gson().toJson(token));
            }

            @Override
            public void onFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(LandingActivity.this,"Failure");
                Log.d(LOG_TAG, "onFailure : " + new Gson().toJson(gsmaError));
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                //Merchant Payments
                startActivity(new Intent(LandingActivity.this, MerchantPaymentsActivity.class));
                break;
            case 1:
                //Disbursements
                break;
            case 2:
                //International Transfers
                break;
            case 3:
                //P2P Transfers
                break;
            case 4:
                //Recurring Payments
                break;
            case 5:
                //Account Linking
                break;
            case 6:
                //Bill Payments
                break;
            case 7:
                //Agent Services
                break;
            default:
                break;
        }
    }

    public void showLoading() {
        progressdialog.show();
    }

    public void hideLoading() {
        progressdialog.dismiss();
    }

}
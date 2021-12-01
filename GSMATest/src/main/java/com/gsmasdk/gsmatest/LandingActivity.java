package com.gsmasdk.gsmatest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.enums.Environment;
import com.gsmaSdk.gsma.interfaces.PaymentInitialiseInterface;
import com.gsmaSdk.gsma.manager.PreferenceManager;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.network.retrofit.PaymentConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

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
                        "https://3a38bb51-0e77-4317-a54a-abda601877c9.mock.pstmn.io",
                        "",
                        Environment.SANDBOX);

      //  PaymentConfiguration.init("https://3a38bb51-0e77-4317-a54a-abda601877c9.mock.pstmn.io",Environment.LIVE);
        /**
         * Initialise the preference objects.
         */
        PreferenceManager.getInstance().init(this);
        /**
         * Token creation
         */
        SDKManager.getInstance().init(this, new PaymentInitialiseInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(LandingActivity.this,errorObject.getErrorDescription());
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
                startActivity(new Intent(LandingActivity.this, DisbursementActivity.class));
                break;
            case 2:
                //International Transfers
                startActivity(new Intent(LandingActivity.this, InternationalTransfersActivity.class));

                break;
            case 3:
                //P2P Transfers
                startActivity(new Intent(LandingActivity.this, P2PTransferActivity.class));

                break;
            case 4:
                //Recurring Payments
                startActivity(new Intent(LandingActivity.this,RecurringPaymentsActivity .class));

                break;

            case 5:
                //Account Linking
                startActivity(new Intent(LandingActivity.this,AccountLinkingActivity .class));


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
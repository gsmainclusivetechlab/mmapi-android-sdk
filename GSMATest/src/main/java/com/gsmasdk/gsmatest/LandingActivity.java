package com.gsmasdk.gsmatest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.enums.Environment;
import com.gsmaSdk.gsma.interfaces.PaymentInitialiseInterface;
import com.gsmaSdk.gsma.manager.PreferenceManager;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.network.retrofit.PaymentConfiguration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ALL")
public class LandingActivity extends AppCompatActivity implements CustomUseCaseRecyclerAdapter.ItemClickListener {

    private CustomUseCaseRecyclerAdapter customRecyclerAdapter;
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

        RecyclerView recyclerView = findViewById(R.id.usesCasesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        customRecyclerAdapter = new CustomUseCaseRecyclerAdapter(this,false, usecasesArray);
        customRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(customRecyclerAdapter);

        progressdialog = Utils.initProgress(LandingActivity.this);
        showLoading();
        Utils.showToast(LandingActivity.this, "Configuring SDK...");

        /**
         * Initialise payment configuration with the following
         * consumerKey - provided by Client
         * consumerSecret - provided by Client
         * authenticationType - requried level of authentication
         * callbackUrl - server url to which long running operation responses are delivered
         * environment - sandbox or production
         */


        PaymentConfiguration.
                init(getString(R.string.consumer_key),
                        getString(R.string.consumer_secret),
                        AuthenticationType.STANDARD_LEVEL,
                        getString(R.string.callback_url),
                        getString(R.string.api_key),
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
                Utils.showToast(LandingActivity.this, errorObject.getErrorDescription());
                Log.d(LOG_TAG, "Validation Error: " + errorObject);
            }

            @Override
            public void onSuccess(Token token) {
                hideLoading();
                Utils.showToast(LandingActivity.this, "Success");
                Log.d(LOG_TAG, "onSuccess : " + new Gson().toJson(token));
            }

            @Override
            public void onFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(LandingActivity.this, "Failure");
                Log.d(LOG_TAG, "onFailure : " + new Gson().toJson(gsmaError));
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
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
                startActivity(new Intent(LandingActivity.this, RecurringPaymentsActivity.class));

                break;

            case 5:
                //Account Linking
                startActivity(new Intent(LandingActivity.this, AccountLinkingActivity.class));


                break;
            case 6:
                //Bill Payments
                startActivity(new Intent(LandingActivity.this,BillPaymentsActivity.class));

                break;
            case 7:
                //Agent Services
                startActivity(new Intent(LandingActivity.this,AgentServicesActivity.class));

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
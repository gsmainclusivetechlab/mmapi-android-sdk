package com.gsmasdk.gsmatest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.enums.Environment;
import com.gsmaSdk.gsma.interfaces.PaymentInitialiseInterface;
import com.gsmaSdk.gsma.manager.PreferenceManager;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.network.retrofit.PaymentConfiguration;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        setTitle("GSMA Test Application");

        Button btnMerchantPay = findViewById(R.id.btnCaseMerchantPayment);
        Button btnDisbursement = findViewById(R.id.btnCaseDisbursement);

            /*
         //         * Configure the payment system
         //         * Initialise the payment system
         //         * consumerKey,consumerSecret,level of authentication,callBackURL,xAPI key
         //         */
//        PaymentConfiguration.
//                init("59vthmq3f6i15v6jmcjskfkmh",
//                        "ef8tl4gihlpfd7r8jpc1t1nda33q5kcnn32cj375lq6mg2nv7rb"
//                        , AuthenticationType.STANDARD_LEVEL, "https://93248bb1-c64e-4961-bacf-b1d4aaa103bc.mock.pstmn.io/callback", Environment.SANDBOX);

                PaymentConfiguration.
                init("59vthmq3f6i15v6jmcjskfkmh",
                        "ef8tl4gihlpfd7r8jpc1t1nda33q5kcnn32cj375lq6mg2nv7rb"
                        , AuthenticationType.STANDARD_LEVEL, "https://93248bb1-c64e-4961-bacf-b1d4aaa103bc.mock.pstmn.io/callback", Environment.SANDBOX);


        /*Initialise the preference object*/
        PreferenceManager.getInstance().init(this);

        GSMAApi.getInstance().init(this, new PaymentInitialiseInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Toast.makeText(LandingActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d("VALIDATION", "onSuccess: " + errorObject);
            }

            @Override
            public void onSuccess(Token token) {
                Log.d("SUCCESS", "onSuccess: " + new Gson().toJson(token));
            }

            @Override
            public void onFailure(GSMAError gsmaError) {
                Log.d("FAILURE", "onFailure: " + new Gson().toJson(gsmaError));
            }
        });

        btnMerchantPay.setOnClickListener(v -> startActivity(new Intent(LandingActivity.this, MerchantPaymentsActivity.class)));
        btnDisbursement.setOnClickListener(v -> startActivity(new Intent(LandingActivity.this, DisbursementActivity.class)));


    }
}
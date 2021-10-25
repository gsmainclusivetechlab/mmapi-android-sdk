package com.gsmasdk.gsmatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        setTitle("GSMA Test Application");

        Button btnMerchantPay = findViewById(R.id.btnCaseMerchantPayment);
        Button btnDisbursement = findViewById(R.id.btnCaseDisbursement);

        btnMerchantPay.setOnClickListener(v -> startActivity(new Intent(LandingActivity.this, MerchantPaymentsActivity.class)));
    }
}
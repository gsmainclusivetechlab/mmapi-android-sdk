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
import com.gsmaSdk.gsma.controllers.SDKManager;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

import java.util.ArrayList;
import java.util.Arrays;

public class RecurringPaymentsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


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

    private final String[] recurringPaymentArray = {
            "Create Debit Mandate",
            "Request State",
            "Read a Debit Mandate",
            "Merchant Payment using Debit Mandate",
            "View Transaction",
            "Recurring Payment Refund",
            "Recurring Payment Reversal",
            "Service Provide Balance",
            "Retrieve Payments for a Service provider",
            "Missing Transaction",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_payments);
        setTitle("Recurring Payments");

        ListView listUseCases = findViewById(R.id.recurringPaymentList);

        CustomUseCaseAdapter customListAdapter = new CustomUseCaseAdapter(RecurringPaymentsActivity.this, new ArrayList(Arrays.asList(recurringPaymentArray)));
        listUseCases.setAdapter(customListAdapter);
        listUseCases.setOnItemClickListener(this);
        txtResponse = findViewById(R.id.txtRecurringResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());

        progressdialog = Utils.initProgress(RecurringPaymentsActivity.this);


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
                //Create Debit Mandate;

                break;

            case 1:
                //Request State;

                break;

            case 2:
                //Read a Debit Mandate

                break;

            case 3:
                //Merchant Payment using Debit Mandate

                break;
            case 4:
                //View Transaction

                break;

            case  5:
              //Recurring Payment Refund
              break;

            case  6:
              //Recurring Payment Reversal

                break;
            case  7:
               // Service Provide Balance
                break;
            case  8:
                //Retrieve Payments for a Service provider
                break;
            case 9:
                //Missing Transaction


            default:
                break;
        }

    }
}
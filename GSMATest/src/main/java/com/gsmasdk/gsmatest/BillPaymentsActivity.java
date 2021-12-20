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
import com.gsmaSdk.gsma.models.account.Link;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;

import java.util.ArrayList;
import java.util.Arrays;

public class BillPaymentsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";

    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";
    private Reversal reversalObject;

    private String transactionRef = "";

    private String serverCorrelationId;
    ArrayList<Identifier> identifierArrayList;

    private final String[] billPaymentArray = {
            "View Account Bills",
            "Create Bill Transaction",
            "Create Bill Payments",
            "View Request State",
            "View Bill Payment",
            "Retrieve a Missing API Response",
            "View Transaction"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payments);

        setTitle("Bill Payments");

        ListView listUseCases = findViewById(R.id.billPaymentList);

        CustomUseCaseAdapter customListAdapter = new CustomUseCaseAdapter(BillPaymentsActivity.this, new ArrayList(Arrays.asList(billPaymentArray)));
        listUseCases.setAdapter(customListAdapter);
        listUseCases.setOnItemClickListener(this);
        txtResponse = findViewById(R.id.txtBillPaymentResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());
        progressdialog = Utils.initProgress(BillPaymentsActivity.this);

        checkServiceAvailability();
        createAccountIdentifier();
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
                //View Account Bills;
                break;
            case 1:
                //Create Bill Transaction
                break;
            case 2:
                //Create Bill Payments
                break;
            case 3:
                //View Request State
                break;
            case 4:
                //View Bill Payment
                break;
            case 5:
                //  Retrieve a Missing API Response
                break;
            case  6:
                //view transaction
                break;
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
                Utils.showToast(BillPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                Utils.showToast(BillPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(BillPaymentsActivity.this, "Failure");
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

    private void createAccountIdentifier() {
        identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");

        identifierArrayList.add(identifierAccount);

    }

}
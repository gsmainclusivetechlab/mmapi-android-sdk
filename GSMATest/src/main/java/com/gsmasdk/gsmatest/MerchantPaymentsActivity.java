package com.gsmasdk.gsmatest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeItemInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.AccountIdentifier;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Activity for testing APIs
 */
@SuppressWarnings("ALL")
public class MerchantPaymentsActivity extends AppCompatActivity implements CustomUseCaseRecyclerAdapter.ItemClickListener {

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private CustomUseCaseRecyclerAdapter customRecyclerAdapter;
    private TextView txtResponse;
    private Transaction transactionRequest;
    private AuthorisationCode authorisationCodeRequest;
    private String transactionRef = "";
    private String serverCorrelationId = "";
    private String correlationId = "";
    private Reversal reversalObject;
    private ProgressDialog progressdialog;
    private String[] merchantPaymentsArray = {
            "Payee-Initiated Merchant Payment",
            "Payee-Initiated Merchant Payment using Polling Method",
            "Payer-Initiated Merchant Payment",
            "Payee-Initiated Merchant Payment using a Pre-authorised Payment Code",
            "Merchant Payment Refund",
            "Merchant Payment Reversal",
            "Obtain a Merchant Balance",
            "Retrieve Payments for a Merchant",
            "Check for Service Availability",
            "Retrieve a Missing API Response",
    };
    ArrayList<Identifier> identifierArrayList;

    StringBuilder sbOutPut;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_payments);
        setTitle("Merchant Payments");


        RecyclerView recyclerView = findViewById(R.id.merchantPaymentsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        customRecyclerAdapter = new CustomUseCaseRecyclerAdapter(this,true, merchantPaymentsArray);
        customRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(customRecyclerAdapter);


        txtResponse = findViewById(R.id.txtResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());

        progressdialog = Utils.initProgress(MerchantPaymentsActivity.this);

        createTransactionObject();
        createCodeRequestObject();
        createPaymentReversalObject();

    }


    /**
     * Return account Identifier
     */

    private ArrayList<Identifier> createAccountIdentifier(String accountIdentifierKey, String accountIdentifierValue){
        identifierArrayList = new ArrayList<>();
        identifierArrayList.clear();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey(accountIdentifierKey);
        identifierAccount.setValue(accountIdentifierValue);
        identifierArrayList.add(identifierAccount);
        return identifierArrayList;
    }


    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability(int position) {
        showLoading();
        SDKManager.merchantPayment.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(serviceAvailability).toString());
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(MerchantPaymentsActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
                customRecyclerAdapter.setStatus(1, position);
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }


    /**
     * Create Payment Reversal Object.
     */
    private void createPaymentReversalObject() {
        reversalObject = new Reversal();
        reversalObject.setType("reversal");
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

    public void showLoading() {
        progressdialog.show();
    }

    public void hideLoading() {
        progressdialog.dismiss();
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

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                //Payee-Initiated Merchant Payment
                sbOutPut=new StringBuilder();
                sbOutPut.append("Payee-Initiated Merchant Payment - Output\n\n");
                payeeInitiatedMerchantPay(position,NotificationMethod.CALLBACK);
                break;
            case 1:
                //Payee-Initiated Merchant Payment using the Polling Method
                sbOutPut=new StringBuilder();
                sbOutPut.append("Payee-Initiated Merchant Payment Polling - Output\n\n");
                payeeInitiatedMerchantPay(position,NotificationMethod.POLLING);

                break;
            case 2:
                //Payer-Initiated Merchant Payment
                sbOutPut=new StringBuilder();
                sbOutPut.append("Payer-Initiated Merchant Payment - Output\n\n");
                payeeInitiatedMerchantPay(position,NotificationMethod.CALLBACK);

                break;
            case 3:
                //Payee-Initiated Merchant Payment using a Pre-authorised Payment Code"
                sbOutPut=new StringBuilder();
                sbOutPut.append("Create Authorisation Code - Output\n\n");
                payeeInitiatedPreAuthoirised(position);
                break;
            case 4:
                //Payment Refund
                sbOutPut=new StringBuilder();
                sbOutPut.append("Payment Refund - Output\n\n");
                paymentRefund(position);
                break;
            case 5:
                //Payment Reversal
                sbOutPut=new StringBuilder();
                sbOutPut.append("Payment Reversal - Output\n\n");
                paymentReversal(position);
                break;
            case 6:
                //Retrieve Balance
                sbOutPut=new StringBuilder();
                sbOutPut.append("Balance - Output\n\n");
                balanceCheck(position);
                break;
            case 7:
                //View Transaction
                sbOutPut=new StringBuilder();
                sbOutPut.append("Retrieve Transaction - Output\n\n");
                retrieveTransaction(position);
                break;
            case 8:
                //Check Service Availability
                sbOutPut=new StringBuilder();
                sbOutPut.append("Check Service Availability - Output\n\n");
                checkServiceAvailability(position);
                break;
            case 9:
                // Missing Code Response
                sbOutPut=new StringBuilder();
                sbOutPut.append("Create Missing Transaction - Output\n\n");
                payeeInitiatedMerchantPay(position,NotificationMethod.CALLBACK);
                break;
            default:
                break;
        }
    }


    /**
     * View Authorization Code
     */

    private void viewAuthorizationCode(int position) {
        sbOutPut.append("\n\nView Authorization Code - Output \n\n");
        SDKManager.merchantPayment.viewAuthorisationCode(createAccountIdentifier("accountid","2999"), transactionRef, new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {
                if (authorisationCodeItem.getAuthorisationCode() == null || authorisationCodeItem.getCodeState() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty\n\n");
                    txtResponse.setText(sbOutPut.toString());hideLoading();
                    hideLoading();
                    Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(authorisationCodeItem).toString()+"\n\n");
                    txtResponse.setText(sbOutPut.toString());
                    payeeInitiatedMerchantPayAuthCode(position);
                }

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {
                   hideLoading();
                    Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                   sbOutPut.append(new Gson().toJson(gsmaError).toString()+"\n\n");
                   txtResponse.setText(sbOutPut.toString());
                   customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                   hideLoading();
                 Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                   sbOutPut.append(new Gson().toJson(errorObject).toString()+"\n\n");
                   customRecyclerAdapter.setStatus(2, position);
                   txtResponse.setText(sbOutPut.toString());

            }
        });

    }
    /**
     * Checking Balance.
     */
    private void balanceCheck(int position) {
        showLoading();
        SDKManager.merchantPayment.viewAccountBalance(
                createAccountIdentifier("accountid","1"), new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onBalanceSuccess(Balance balance) {
                hideLoading();
                if (balance == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(balance).toString());
                    Utils.showToast(MerchantPaymentsActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }



    /**
     * Payee/payer  initiated payment
     */
    private void payeeInitiatedMerchantPay(int position,NotificationMethod notificationMethod) {
        showLoading();
        SDKManager.merchantPayment.createMerchantTransaction(notificationMethod, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    if(notificationMethod==NotificationMethod.CALLBACK){
                        if(position==9){
                            sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                            getMissingTransaction(position);
                        }else {
                            Utils.showToast(MerchantPaymentsActivity.this, "Success");
                            customRecyclerAdapter.setStatus(1, position);
                            sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                            txtResponse.setText(sbOutPut.toString());
                        }
                    }else{
                        sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                        requestState(position);
                    }

                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }



    /**
     * Request State
     */
    private void requestState(int position) {
        sbOutPut.append("\n\nView Request state - Output\n\n");
        SDKManager.merchantPayment.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                hideLoading();
                sbOutPut.append(new Gson().toJson(errorObject)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                transactionRef = requestStateObject.getObjectReference();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    hideLoading();
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString()+"\n\n");
                    viewTransasction(position);
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
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
    private void viewTransasction(int position) {
        sbOutPut.append("View Transaction-Output\n\n");
        SDKManager.merchantPayment.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
            }

            @Override
            public void onTransactionSuccess(Transaction transactionRequest) {
                hideLoading();
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionRequest));
                if (transactionRequest == null
                        || transactionRequest.getTransactionReference() == null
                        || transactionRequest.getTransactionStatus() == null
                        || transactionRequest.getCurrency() == null
                        || transactionRequest.getCreditParty() == null
                        || transactionRequest.getDebitParty() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());

                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(transactionRequest)+"\n\n");
                    txtResponse.setText(sbOutPut.toString());
                }

            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError)+"\n\n");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

        });
    }


    /**
     * Authorization Code
     */
    private void payeeInitiatedPreAuthoirised(int position) {
        showLoading();
        SDKManager.merchantPayment.createAuthorisationCode(createAccountIdentifier("accountid","2999")
                ,NotificationMethod.CALLBACK, "", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                customRecyclerAdapter.setStatus(2, position);
                sbOutPut.append(new Gson().toJson(errorObject)+"\n\n");
                txtResponse.setText(sbOutPut.toString());

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
                txtResponse.setText(new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    hideLoading();
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    serverCorrelationId = requestStateObject.getServerCorrelationId();
                    sbOutPut.append(new Gson().toJson(requestStateObject)+"\n\n");
                    requestStateAuthCode(position);
                }

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut);
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });

    }





    /**
     * Request State
     */
    private void requestStateAuthCode(int position) {
        sbOutPut.append("\n\nView Request state-Output\n\n");
        SDKManager.merchantPayment.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());
            }
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                transactionRef = requestStateObject.getObjectReference();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                    hideLoading();
                    Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                } else {
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    viewAuthorizationCode(position);
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError)+"\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());

            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }




    /**
     * Payee/payer  initiated payment
     */
    private void payeeInitiatedMerchantPayAuthCode(int position) {
        transactionRequest.setOneTimeCode(authorisationCodeRequest.getAuthorisationCode());
        sbOutPut.append("Payee Initiated Merchant Payment \n\n");
        SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod.CALLBACK, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                    Utils.showToast(MerchantPaymentsActivity.this, "Failure");

                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    txtResponse.setText(sbOutPut.toString());

                }
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }



    private void paymentRefund(int position) {
        showLoading();
        SDKManager.merchantPayment.createRefundTransaction(NotificationMethod.CALLBACK, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    Utils.showToast(MerchantPaymentsActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onRefundSuccess" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onRefundFailure: " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    /**
     * Payment Reversal
     */
    private void paymentReversal(int position) {
        showLoading();
        SDKManager.merchantPayment.createReversal(NotificationMethod.CALLBACK, "", "REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    Utils.showToast(MerchantPaymentsActivity.this, "Success");
                }
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
                txtResponse.setText(sbOutPut.toString());
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }

    /**
     * Retrieve Transaction
     */
    private void retrieveTransaction(int position) {
        showLoading();
        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        SDKManager.merchantPayment.viewAccountTransactions(createAccountIdentifier("accountid","2999"), transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {
                hideLoading();

                Transaction transactionRequest = transaction.getTransaction().get(0);
                if (transactionRequest == null
                        || transactionRequest.getTransactionReference() == null
                        || transactionRequest.getTransactionStatus() == null
                        || transactionRequest.getCurrency() == null
                        || transactionRequest.getCreditParty() == null
                        || transactionRequest.getDebitParty() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    sbOutPut.append(new Gson().toJson(transaction).toString());
                    customRecyclerAdapter.setStatus(1, position);
                    Utils.showToast(MerchantPaymentsActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }

    /**
     * Missing Transaction
     */
    private void getMissingTransaction(int position) {
        sbOutPut.append("\n\nMissing Response - Output\n\n");
        showLoading();
        SDKManager.merchantPayment.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
                hideLoading();
                if (missingResponse == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(missingResponse).toString());
                    Utils.showToast(MerchantPaymentsActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onMissingTransactionSuccess: " + new Gson().toJson(missingResponse));
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(MerchantPaymentsActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(MerchantPaymentsActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }
        });


    }






}
package com.gsmasdk.gsmatest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.enums.NotificationMethod;

import com.gsmaSdk.gsma.interfaces.BalanceInterface;

import com.gsmaSdk.gsma.interfaces.AccountHolderInterface;

import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.AccountHolderObject;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.MissingResponse;
import com.gsmaSdk.gsma.models.common.Balance;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.transaction.CreditPartyItem;
import com.gsmaSdk.gsma.models.transaction.CustomDataItem;
import com.gsmaSdk.gsma.models.transaction.DebitPartyItem;
import com.gsmaSdk.gsma.models.transaction.InternationalTransferInformation;
import com.gsmaSdk.gsma.models.transaction.RequestingOrganisation;
import com.gsmaSdk.gsma.models.transaction.ReversalObject;

import com.gsmaSdk.gsma.models.transaction.Transaction;


import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class P2PTransferActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

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


    private final String[] p2pTransfersArray = {
            "Retrieve the Name of the Recipient",
            "Request a P2P Quotation",
            "Perform a P2P Transfer",
            "P2P Transfer Reversal",
            "Obtain an FSP Balance",
            "Retrieve Transactions for an FSP",
            "Missing Transaction",
            "view Quotation",
            "View Request State",
            "View Transaction"
    };

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2p);
        setTitle("P2P Transfers");


        ListView listUseCases = findViewById(R.id.p2pTransferList);

        CustomUseCaseAdapter customListAdapter = new CustomUseCaseAdapter(P2PTransferActivity.this, new ArrayList(Arrays.asList(p2pTransfersArray)));
        listUseCases.setAdapter(customListAdapter);
        listUseCases.setOnItemClickListener(this);
        txtResponse = findViewById(R.id.txtP2PResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());

        progressdialog = Utils.initProgress(P2PTransferActivity.this);
        checkServiceAvailability();
        createPaymentReversalObject();
        createAccountIdentifier();

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                //Retrieve the Name of the Recipient;
                viewAccountName();
                break;

            case 1:
                //Request a P2P Quotation;
                createP2PQuotationObject();
                break;

            case 2:
                //Perform a P2P Transfer
                createP2PTransferObject();
                break;

            case 3:
                //P2P Transfer Reversal
                 reversal();
                break;
            case 4:
                //Obtain an FSP Balance
                balanceCheck();
                break;

            case  5:
                retrieveTransactionFSP();

                break;

            case 6:
                //Missing Transaction

                getMissingTransaction();
                break;

            case 7:

                viewQuotation();
                break;

            case 8:
                //view request State
                requestState();
                break;

            case 9:
                //view Transaction
                viewTransaction();
                break;
            default:
                break;
        }
    }

    //Retrieve a missing Transaction
    private void getMissingTransaction() {
        showLoading();

        SDKManager.p2PTransfer.viewTransactionResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse, String correlationId) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(missingResponse));
                Log.d(SUCCESS, "onMissingTransactionSuccess: " + new Gson().toJson(missingResponse));
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(P2PTransferActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });


    }

    //Retrieve Transaction for an FSP
    private void retrieveTransactionFSP() {
        showLoading();
        SDKManager.p2PTransfer.viewAccountTransactions(identifierArrayList, 0, 2, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction, String correlationID) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transaction));
                correlationId = correlationID;
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    /**
     * create a transaction object for P2P Quotation request
     */
    private void createP2PQuotationObject() {

        transactionRequest = new TransactionRequest();

        //create debit party and credit party for P2P transfer quotation
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        //debit party
        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("2999");
        debitPartyList.add(debitPartyItem);

        //credit party
        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("2000");
        creditPartyList.add(creditPartyItem);

        //add debit and credit party to transaction object
        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);


        //set amount,currency and request date into transaction request
        transactionRequest.setRequestAmount("75.30");
        transactionRequest.setRequestCurrency("RWF");
        transactionRequest.setRequestDate("2018-07-03T11:43:27.405Z");
        transactionRequest.setType("transfer");
        transactionRequest.setSubType("abc");
        transactionRequest.setChosenDeliveryMethod("directtoaccount");

        //create array for custom data items
        ArrayList<CustomDataItem> customDataItemList = new ArrayList<>();

        // create a custom data item
        CustomDataItem customDataItem = new CustomDataItem();
        customDataItem.setKey("keytest");
        customDataItem.setValue("keyvalue");

        //add custom object into custom array
        customDataItemList.add(customDataItem);

        //add custom data object to request object
        transactionRequest.setCustomData(customDataItemList);

        //request for quotation
        requestQuotation();

    }

    /**
     * create a transaction object for P2P transfer request
     */
    private void createP2PTransferObject() {
//        transactionRequest=new TransactionRequest();
        if (transactionRequest == null) {
            Utils.showToast(this, "Please request Quotation before performing this request");
        } else {

            //set amount and currency
            transactionRequest.setAmount("100");
            transactionRequest.setCurrency("GBP");

            ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
            ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
            DebitPartyItem debitPartyItem = new DebitPartyItem();
            CreditPartyItem creditPartyItem = new CreditPartyItem();

            //debit party
            debitPartyItem.setKey("accountid");
            debitPartyItem.setValue("2999");
            debitPartyList.add(debitPartyItem);

            //credit party
            creditPartyItem.setKey("accountid");
            creditPartyItem.setValue("2000");
            creditPartyList.add(creditPartyItem);

            //create international information object to perform international transfer

            InternationalTransferInformation internationalTransferInformation = new InternationalTransferInformation();
            internationalTransferInformation.setOriginCountry("AD");
            internationalTransferInformation.setQuotationReference("REF-1636521507766");
            internationalTransferInformation.setQuoteId("REF-1636521507766");
            internationalTransferInformation.setRemittancePurpose("personal");
            internationalTransferInformation.setDeliveryMethod("agent");
            transactionRequest.setInternationalTransferInformation(internationalTransferInformation);

            RequestingOrganisation requestingOrganisation = new RequestingOrganisation();
            requestingOrganisation.setRequestingOrganisationIdentifierType("organisationid");
            requestingOrganisation.setRequestingOrganisationIdentifier("testorganisation");

            //add requesting organisation object into transaction request
            transactionRequest.setRequestingOrganisation(requestingOrganisation);

            //add debit and credit party to transaction object
            transactionRequest.setDebitParty(debitPartyList);
            transactionRequest.setCreditParty(creditPartyList);

            performTransfer();
        }
    }


    /**
     * View Account Name
     */
    private void viewAccountName() {
        showLoading();
        SDKManager.p2PTransfer.viewAccountName(identifierArrayList, new AccountHolderInterface() {
            @Override
            public void onRetrieveAccountInfoSuccess(AccountHolderObject accountHolderObject, String correlationID) {
                hideLoading();
                correlationId = correlationID;
                txtResponse.setText(new Gson().toJson(accountHolderObject));
                Utils.showToast(P2PTransferActivity.this, "Success");
                Log.d(SUCCESS, "onRetrieveAccountInfoSuccess: " + new Gson().toJson(accountHolderObject));
            }

            @Override
            public void onRetrieveAccountInfoFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRetrieveAccountInfoFailure: " + new Gson().toJson(gsmaError));
            }


            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());

                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

        });
    }

    /**
     * Perform P2P Transfer
     */
    private void performTransfer() {

        showLoading();
        SDKManager.p2PTransfer.createTransferTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                correlationId = correlationID;
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                Log.d(SUCCESS, "onRequestSuccess " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Log.d(FAILURE, "onRequestFailure " + new Gson().toJson(gsmaError));
                txtResponse.setText(new Gson().toJson(gsmaError));

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });
    }


    //Check Balance
    private void balanceCheck() {
        showLoading();
        SDKManager.p2PTransfer.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @SuppressWarnings("unused")
            @Override
            public void onBalanceSuccess(Balance balance, String correlationID) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Success");
                correlationId = correlationID;
                txtResponse.setText(new Gson().toJson(balance));
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

            @SuppressWarnings("unused")
            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }


    //Reversal
    private void reversal() {
        showLoading();
        SDKManager.p2PTransfer.createReversal(NotificationMethod.POLLING, "", "REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Success");

                txtResponse.setText(new Gson().toJson(requestStateObject));
                correlationId = correlationID;
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });
    }


    /**
     * Method for fetching a particular transaction.
     */
    private void viewTransaction() {
        showLoading();
        SDKManager.p2PTransfer.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject, String correlationID) {
                correlationId = correlationID;
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
            }

        });
    }


    //get the request state of a transaction
    private void requestState() {
        showLoading();
        SDKManager.p2PTransfer.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Success");
                correlationId = correlationID;
                txtResponse.setText(new Gson().toJson(requestStateObject));
                transactionRef = requestStateObject.getObjectReference();
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
            }

        });
    }


    public void viewQuotation() {
        showLoading();
        SDKManager.p2PTransfer.viewQuotation(transactionRef, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject, String correlationID) {
                hideLoading();
                correlationId = correlationID;
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess " + new Gson().toJson(transactionObject));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Log.d(FAILURE, "onTransactionFailure " + new Gson().toJson(gsmaError));
                txtResponse.setText(new Gson().toJson(gsmaError));
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });

    }

    @SuppressWarnings("CommentedOutCode")
    private void createAccountIdentifier() {

        identifierArrayList = new ArrayList<>();
    
//

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("1");
        identifierArrayList.add(identifierAccount);

//        //msisdn
//        Identifier identifierMsisdn = new Identifier();
//        identifierMsisdn.setKey("msisdn");
//        identifierMsisdn.setValue("%2B123456789102345");
//        identifierArrayList.add(identifierMsisdn);
//
//        //wallet id
//
//        Identifier identifierWallet = new Identifier();
//        identifierWallet.setKey("walletid");
//        identifierWallet.setValue("3355544");
//        identifierArrayList.add(identifierWallet);
////


    }


    /**
     * Create Payment Reversal Object.
     */
    private void createPaymentReversalObject() {
        reversalObject = new ReversalObject();
        reversalObject.setReversal("reversal");
    }


    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability() {
        showLoading();
        SDKManager.p2PTransfer.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability, String correlationID) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                correlationId = correlationID;
                Utils.showToast(P2PTransferActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(P2PTransferActivity.this, "Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    //Request the quotation to perform P2P transfer
    private void requestQuotation() {

        SDKManager.p2PTransfer.createQuotation(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                correlationId = correlationID;
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                Log.d(SUCCESS, "onRequestSuccess " + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Log.d(FAILURE, "onRequestFailure " + new Gson().toJson(gsmaError));
                txtResponse.setText(new Gson().toJson(gsmaError));

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());

                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
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
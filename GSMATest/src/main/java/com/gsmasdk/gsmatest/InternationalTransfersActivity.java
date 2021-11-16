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
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.controllers.SDKManager;

import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;

import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;

import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.common.Balance;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;

import com.gsmaSdk.gsma.models.transaction.CreditPartyItem;
import com.gsmaSdk.gsma.models.transaction.CustomDataItem;
import com.gsmaSdk.gsma.models.transaction.DebitPartyItem;
import com.gsmaSdk.gsma.models.transaction.IdDocumentItem;
import com.gsmaSdk.gsma.models.transaction.InternationalTransferInformation;
import com.gsmaSdk.gsma.models.transaction.PostalAddress;
import com.gsmaSdk.gsma.models.transaction.RequestingOrganisation;
import com.gsmaSdk.gsma.models.transaction.SenderKyc;
import com.gsmaSdk.gsma.models.transaction.SubjectName;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

import com.gsmaSdk.gsma.models.transaction.ReversalObject;
import com.gsmaSdk.gsma.models.transaction.Transaction;


import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("ALL")
public class InternationalTransfersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";
    private ReversalObject reversalObject;

    private TransactionRequest transactionRequest;
    ArrayList<Identifier> identifierArrayList;

    private final String[] internationalTransfersArray = {
            "Request a International Transfer Quotation",
            "Perform an International Transfer",
            "Reversal",
            "Obtain an FSP Balance",
            "Retrieve Transaction for FSP",
            "Missing Transaction",
            "View Quotation"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_international_transfers);
        setTitle("International Transfers");

        ListView listUseCases = findViewById(R.id.internationalTransferList);
        CustomUseCaseAdapter customListAdapter = new CustomUseCaseAdapter(InternationalTransfersActivity.this, new ArrayList(Arrays.asList(internationalTransfersArray)));
        listUseCases.setAdapter(customListAdapter);
        listUseCases.setOnItemClickListener(this);
        txtResponse = findViewById(R.id.txtResponseInternational);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());

        progressdialog = Utils.initProgress(InternationalTransfersActivity.this);
        checkServiceAvailability();
        createPaymentReversalObject();
        createAccountIdentifier();


    }

    /*
    * Account identitifers for transaction
    *
    */
    private void createAccountIdentifier(){

        identifierArrayList=new ArrayList<>();
        identifierArrayList.clear();
//
        //account id
        Identifier identifierAccount=new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2000");
        identifierArrayList.add(identifierAccount);

        //msisdn
        Identifier identifierMsisdn=new Identifier();
        identifierMsisdn.setKey("msisdn");
        identifierMsisdn.setValue("+44012345678");
        identifierArrayList.add(identifierMsisdn);

        //wallet id

        Identifier identifierWallet=new Identifier();
        identifierWallet.setKey("walletid");
        identifierWallet.setValue("1");
        identifierArrayList.add(identifierWallet);


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                //request for quotation;
                createInternationalQuotationObject();
                break;
            case 1:
                createInternationalTransferObject();
                //Request a International Transfer Quotation;
                break;
            case 2:
                //Reversal
                reversal();
                break;
            case 3:
                //Obtain an FSP Balance
                balanceCheck();
                break;
            case 4:
                //Retrieve Transaction for FSP
                retrieveTransactionFSP();
                break;
            case 5:
                //Missing Transaction
                getMissingTransaction();

                break;
            case 6:
                //View Quotation
                viewQuotation();
                break;

            default:
                break;
        }


    }


    public void viewQuotation() {
        showLoading();
        SDKManager.getInstance().viewQuotation("REF-1636973900963", new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject, String correlationID) {
                hideLoading();
                correlationId = correlationID;
                Utils.showToast(InternationalTransfersActivity.this, "Success");
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
                Toast.makeText(InternationalTransfersActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });

    }


    private void createInternationalTransferObject() {
//        transactionRequest=new TransactionRequest();
        if (transactionRequest == null) {
            Utils.showToast(this, "Please request for Quotation before perfoming this request");
            return;
        } else {

            //set amount and currency
            transactionRequest.setAmount("100");
            transactionRequest.setCurrency("GBP");

            //create international information object to perform international transfer

            InternationalTransferInformation internationalTransferInformation = new InternationalTransferInformation();
            internationalTransferInformation.setOriginCountry("GB");
            internationalTransferInformation.setQuotationReference("REF-1636521507766");
            internationalTransferInformation.setQuoteId("REF-1636521507766");
            internationalTransferInformation.setReceivingCountry("RW");
            internationalTransferInformation.setRemittancePurpose("personal");
            internationalTransferInformation.setRelationshipSender("none");
            internationalTransferInformation.setDeliveryMethod("agent");
            internationalTransferInformation.setSendingServiceProviderCountry("AD");
            transactionRequest.setInternationalTransferInformation(internationalTransferInformation);

            RequestingOrganisation requestingOrganisation = new RequestingOrganisation();
            requestingOrganisation.setRequestingOrganisationIdentifierType("organisationid");
            requestingOrganisation.setRequestingOrganisationIdentifier("testorganisation");

            //add requesting organisation object into transaction request
            transactionRequest.setRequestingOrganisation(requestingOrganisation);

            performInternationalTransfer();
        }
    }


    //perform international transfer
    private void performInternationalTransfer() {
        showLoading();
        SDKManager.getInstance().initiateInternationalTransfer(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                correlationId = correlationID;
                Utils.showToast(InternationalTransfersActivity.this, "Success");
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
                Toast.makeText(InternationalTransfersActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });

    }

    //Request for quotation-Request the quotation to perform international transfer
    private void requestQuotation() {

        SDKManager.getInstance().requestQuotation(NotificationMethod.POLLING,"",transactionRequest, new RequestStateInterface() {
            @Override
         public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                correlationId = correlationID;
                Utils.showToast(InternationalTransfersActivity.this, "Success");
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
                Toast.makeText(InternationalTransfersActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
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

    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability() {
        showLoading();
        SDKManager.getInstance().checkServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(InternationalTransfersActivity.this, "Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability, String correlationID) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                correlationId = correlationID;
                Utils.showToast(InternationalTransfersActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(InternationalTransfersActivity.this, "Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }

    //create a transaction object for international transfer request
    private void createInternationalQuotationObject() {
        transactionRequest = new TransactionRequest();

        //create debit party and credit party for internal transfer quotation
        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        //debit party
        debitPartyItem.setKey("walletid");
        debitPartyItem.setValue("1");
        debitPartyList.add(debitPartyItem);

        //credit party
        creditPartyItem.setKey("msisdn");
        creditPartyItem.setValue("+44012345678");
        creditPartyList.add(creditPartyItem);

        //add debit and credit party to transaction object
        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);


        //set amount,currency and request date into transaction request
        transactionRequest.setRequestAmount("75.30");
        transactionRequest.setRequestCurrency("RWF");
        transactionRequest.setRequestDate("2018-07-03T11:43:27.405Z");
        transactionRequest.setType("inttransfer");
        transactionRequest.setSubType("abc");
        transactionRequest.setChosenDeliveryMethod("agent");

        //sender kyc object
        SenderKyc senderKyc = new SenderKyc();
        senderKyc.setNationality("GB");
        senderKyc.setDateOfBirth("1970-07-03T11:43:27.405Z");
        senderKyc.setOccupation("manager");
        senderKyc.setEmployerName("MFX");
        senderKyc.setContactPhone("447125588999");
        senderKyc.setGender("m"); // m or f
        senderKyc.setEmailAddress("luke.skywalkeraaabbb@gmail.com");
        senderKyc.setBirthCountry("GB");

        // create object for documentation
        ArrayList<IdDocumentItem> idDocumentItemList = new ArrayList<>();
        IdDocumentItem idDocumentItem = new IdDocumentItem();
        idDocumentItem.setIdType("nationalidcard");
        idDocumentItem.setIdNumber("1234567");
        idDocumentItem.setIssueDate("2018-07-03T11:43:27.405Z");
        idDocumentItem.setExpiryDate("2021-07-03T11:43:27.405Z");
        idDocumentItem.setIssuer("UKPA");
        idDocumentItem.setIssuerPlace("GB");
        idDocumentItem.setIssuerCountry("GB");
        idDocumentItem.setOtherIdDescription("test");

        idDocumentItemList.add(idDocumentItem);

        //add document details to kyc object
        senderKyc.setIdDocument(idDocumentItemList);

        //create object for postal address
        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setCountry("GB");
        postalAddress.setAddressLine1("111 ABC Street");
        postalAddress.setCity("New York");
        postalAddress.setStateProvince("New York");
        postalAddress.setPostalCode("ABCD");

        //add postal address to kyc object
        senderKyc.setPostalAddress(postalAddress);

        //create subject model

        SubjectName subjectName = new SubjectName();
        subjectName.setTitle("Mr");
        subjectName.setFirstName("Luke");
        subjectName.setMiddleName("R");
        subjectName.setLastName("Skywalker");
        subjectName.setFullName("Luke R Skywalker");
        subjectName.setNativeName("ABC");

        //add  subject to kyc model

        senderKyc.setSubjectName(subjectName);

        //create array for custom data items
        ArrayList<CustomDataItem> customDataItemList = new ArrayList<>();

        // create a custom data item
        CustomDataItem customDataItem = new CustomDataItem();
        customDataItem.setKey("keytest");
        customDataItem.setValue("keyvalue");

        //add custom object into custom array
        customDataItemList.add(customDataItem);

        //add kyc object to request object
        transactionRequest.setSenderKyc(senderKyc);

        //add custom data object to request object
        transactionRequest.setCustomData(customDataItemList);

        transactionRequest.setSendingServiceProviderCountry("AD");
        transactionRequest.setOriginCountry("AD");
        transactionRequest.setReceivingCountry("AD");

        //request for quotation
        requestQuotation();

    }

    //Check Balance
    private void balanceCheck() {
        showLoading();
        SDKManager.getInstance().getBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Toast.makeText(InternationalTransfersActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onBalanceSuccess(Balance balance, String correlationID) {
                hideLoading();
                correlationId = correlationID;
                txtResponse.setText(new Gson().toJson(balance));
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

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
        SDKManager.getInstance().reversal(NotificationMethod.POLLING,"","REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
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
                Toast.makeText(InternationalTransfersActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }
        });
    }

    //Retrieve Transaction for an FSP
    private void retrieveTransactionFSP() {
        showLoading();


        SDKManager.getInstance().retrieveTransaction(identifierArrayList, 0, 5, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Toast.makeText(InternationalTransfersActivity.this, errorObject.getErrorDescription(), Toast.LENGTH_SHORT).show();
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onRetrieveTransactionSuccess(Transaction transaction, String correlationID) {
                hideLoading();
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

    //Retrieve a missing Transaction
    private void getMissingTransaction() {
        showLoading();
        SDKManager.getInstance().retrieveMissingTransaction(correlationId, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(TransactionRequest transactionObject, String correlationId) {
                hideLoading();
                Utils.showToast(InternationalTransfersActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject, TransactionRequest.class));
            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(InternationalTransfersActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(InternationalTransfersActivity.this, "Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

        });
    }

    //create a reversal object for transaction
    private void createPaymentReversalObject() {
        reversalObject = new ReversalObject();
        reversalObject.setReversal("reversal");
    }


}
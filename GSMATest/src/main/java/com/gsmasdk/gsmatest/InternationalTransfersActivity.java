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
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
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

import java.util.ArrayList;
import java.util.Arrays;

public class InternationalTransfersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";

    private TransactionRequest transactionRequest;


    private final String[] internationalTransfersArray = {
            "Request a International Transfer Quotation",
            "Perform an International Transfer",
            "Reversal",
            "Obtain an FSP Balance",
            "Retrieve Transaction for FSP",
            "Missing Transaction",
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
                break;
            default:
                break;
        }

    }


    private void createInternationalTransferObject(){
        transactionRequest=new TransactionRequest();

        transactionRequest.setAmount("100");
        transactionRequest.setCurrency("GBP");



        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("walletid");
        debitPartyItem.setValue("1");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("msisdn");
        creditPartyItem.setValue("+44012345678");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);

        InternationalTransferInformation internationalTransferInformation=new InternationalTransferInformation();
        internationalTransferInformation.setOriginCountry("GB");
        internationalTransferInformation.setQuotationReference("REF-1636521507766");
        internationalTransferInformation.setQuoteId("REF-1636521507766");
        internationalTransferInformation.setReceivingCountry("RW");
        internationalTransferInformation.setRemittancePurpose("personal");
        internationalTransferInformation.setRelationshipSender("none");
        internationalTransferInformation.setDeliveryMethod("agent");
        internationalTransferInformation.setSendingServiceProviderCountry("AD");
        transactionRequest.setInternationalTransferInformation(internationalTransferInformation);

         //sender kyc object

        SenderKyc senderKyc=new SenderKyc();
        senderKyc.setNationality("GB");
        senderKyc.setDateOfBirth("1970-07-03T11:43:27.405Z");
        senderKyc.setOccupation("manager");
        senderKyc.setEmployerName("MFX");
        senderKyc.setContactPhone("447125588999");
        senderKyc.setGender("m"); // m or f
        senderKyc.setEmailAddress("luke.skywalkeraaabbb@gmail.com");
        senderKyc.setBirthCountry("GB");

        // create object for documentation

        ArrayList<IdDocumentItem> idDocumentItemList=new ArrayList<>();
        IdDocumentItem idDocumentItem=new IdDocumentItem();
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

        PostalAddress postalAddress=new PostalAddress();
        postalAddress.setCountry("GB");
        postalAddress.setAddressLine1("111 ABC Street");
        postalAddress.setCity("New York");
        postalAddress.setStateProvince("New York");
        postalAddress.setPostalCode("ABCD");

        //add postal address to kyc object
        senderKyc.setPostalAddress(postalAddress);

        //create subject model

        SubjectName subjectName=new SubjectName();
        subjectName.setTitle("Mr");
        subjectName.setFirstName("Luke");
        subjectName.setMiddleName("R");
        subjectName.setLastName("Skywalker");
        subjectName.setFullName("Luke R Skywalker");
        subjectName.setNativeName("ABC");

        //add  subject to kyc model

        senderKyc.setSubjectName(subjectName);

        //requesting organization object

        RequestingOrganisation requestingOrganisation=new RequestingOrganisation();
        requestingOrganisation.setRequestingOrganisationIdentifierType("organisationid");
        requestingOrganisation.setRequestingOrganisationIdentifier("testorganisation");

        //add requesting organisation object into transaction request
        transactionRequest.setRequestingOrganisation(requestingOrganisation);

        Log.d(SUCCESS, "onTransaction " + new Gson().toJson(transactionRequest));

        performInternationalTransfer();
    }



    private void performInternationalTransfer(){

        //perform international transfer
        showLoading();
        SDKManager.getInstance().intiateInternationalTransfer(transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                correlationId=correlationID;
                Utils.showToast(InternationalTransfersActivity.this,"Success");
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

    private void requestQuotation(){
        showLoading();
        SDKManager.getInstance().requestQuotation(transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject, String correlationID) {
                hideLoading();
                correlationId=correlationID;
                Utils.showToast(InternationalTransfersActivity.this,"Success");
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
                Utils.showToast(InternationalTransfersActivity.this,"Validation Error");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability, String correlationID) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                correlationId = correlationID;
                Utils.showToast(InternationalTransfersActivity.this,"Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(InternationalTransfersActivity.this,"Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }
    //create a transaction object for international transfer request
    private void createInternationalQuotationObject(){
        transactionRequest=new TransactionRequest();

        ArrayList<DebitPartyItem> debitPartyList = new ArrayList<>();
        ArrayList<CreditPartyItem> creditPartyList = new ArrayList<>();
        DebitPartyItem debitPartyItem = new DebitPartyItem();
        CreditPartyItem creditPartyItem = new CreditPartyItem();

        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("2000");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("2999");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);


        transactionRequest.setRequestAmount("75.30");
        transactionRequest.setRequestCurrency("RWF");
        transactionRequest.setRequestDate("2018-07-03T11:43:27.405Z");
        transactionRequest.setType("inttransfer");
        transactionRequest.setSubType("abc");
        transactionRequest.setChosenDeliveryMethod("agent");

        //sender kyc object

        SenderKyc senderKyc=new SenderKyc();
        senderKyc.setNationality("GB");
        senderKyc.setDateOfBirth("1970-07-03T11:43:27.405Z");
        senderKyc.setOccupation("manager");
        senderKyc.setEmployerName("MFX");
        senderKyc.setContactPhone("447125588999");
        senderKyc.setGender("m"); // m or f
        senderKyc.setEmailAddress("luke.skywalkeraaabbb@gmail.com");
        senderKyc.setBirthCountry("GB");

        // create object for documentation

        ArrayList<IdDocumentItem> idDocumentItemList=new ArrayList<>();
        IdDocumentItem idDocumentItem=new IdDocumentItem();
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

        PostalAddress postalAddress=new PostalAddress();
        postalAddress.setCountry("GB");
        postalAddress.setAddressLine1("111 ABC Street");
        postalAddress.setCity("New York");
        postalAddress.setStateProvince("New York");
        postalAddress.setPostalCode("ABCD");

        //add postal address to kyc object
        senderKyc.setPostalAddress(postalAddress);

        //create subject model

        SubjectName subjectName=new SubjectName();
        subjectName.setTitle("Mr");
        subjectName.setFirstName("Luke");
        subjectName.setMiddleName("R");
        subjectName.setLastName("Skywalker");
        subjectName.setFullName("Luke R Skywalker");
        subjectName.setNativeName("ABC");

        //add  subject to kyc model

        senderKyc.setSubjectName(subjectName);

        //create array for custom data items
        ArrayList<CustomDataItem> customDataItemList=new ArrayList<>();

        // create a custom data item
        CustomDataItem customDataItem=new CustomDataItem();
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
}
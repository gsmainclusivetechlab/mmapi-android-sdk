package com.gsmasdk.gsmatest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.AccountHolderInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;

import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.AccountHolderName;
import com.gsmaSdk.gsma.models.account.AccountIdentifier;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.common.Address;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.IdDocument;
import com.gsmaSdk.gsma.models.common.InternationalTransferInformation;
import com.gsmaSdk.gsma.models.common.KYCInformation;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.RequestingOrganisation;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.common.SubjectName;
import com.gsmaSdk.gsma.models.transaction.quotation.Quotation;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class P2PTransferActivity extends AppCompatActivity implements CustomUseCaseRecyclerAdapter.ItemClickListener {

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";
    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";
    private Reversal reversalObject;



    @SuppressWarnings("unused")
    private String serverCorrelationId;

    private Quotation quotationRequest;
    private Transaction transactionRequest;

    ArrayList<Identifier> identifierArrayList;
    StringBuilder sbOutPut;
    private CustomUseCaseRecyclerAdapter customRecyclerAdapter;


    private final String[] p2pTransfersArray = {
            "P2P Transfer via Switch",
            "Bilateral P2P Transfer",
            "‘On-us’ P2P Transfer Initiated by a Third Party Provider",
            "P2P Transfer Reversal",
            "Obtain an FSP Balance",
            "Retrieve Transactions for an FSP",
            "Check for Service Availability",
            "Retrieve a Missing API Response"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2p);
        setTitle("P2P Transfers");


        RecyclerView recyclerView = findViewById(R.id.p2pTransferList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        customRecyclerAdapter = new CustomUseCaseRecyclerAdapter(this, true, p2pTransfersArray);
        customRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(customRecyclerAdapter);


        txtResponse = findViewById(R.id.txtP2PResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());

        progressdialog = Utils.initProgress(P2PTransferActivity.this);

        createPaymentReversalObject();
        createP2PTransferObject();
        createP2PQuotationObject();

    }

    /**
     * Return account Identifier
     */

    @SuppressWarnings("SameParameterValue")
    private ArrayList<Identifier> createAccountIdentifier(String accountIdentifierKey, String accountIdentifierValue){
        identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey(accountIdentifierKey);
        identifierAccount.setValue(accountIdentifierValue);
        identifierArrayList.add(identifierAccount);
        return identifierArrayList;
    }


    //Retrieve a missing Transaction
    private void getMissingTransaction(int position) {
        sbOutPut.append("\n\n Retrieve Missing Response - Output\n\n");
        SDKManager.p2PTransfer.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
                hideLoading();
                if (missingResponse == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(missingResponse));
                    Utils.showToast(P2PTransferActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onMissingTransactionSuccess: " + new Gson().toJson(missingResponse));
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(P2PTransferActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject));
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);

            }
        });

    }

    //Retrieve Transaction for an FSP
    private void retrieveTransactionFSP(int position) {

        showLoading();
        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);


        SDKManager.p2PTransfer.viewAccountTransactions(createAccountIdentifier("accountid","2999"), transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject));
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);

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
                    sbOutPut.append(new Gson().toJson(transaction));
                    customRecyclerAdapter.setStatus(1, position);
                    Utils.showToast(P2PTransferActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }
        });
    }

    /**
     * create a transaction object for P2P Quotation request
     */
    private void createP2PQuotationObject() {

        quotationRequest = new Quotation();

        //create debit party and credit party for P2P transfer quotation
        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

        //debit party
        debitPartyItem.setKey("accountid");
        debitPartyItem.setValue("2999");
        debitPartyList.add(debitPartyItem);

        //credit party
        creditPartyItem.setKey("accountid");
        creditPartyItem.setValue("1");
        creditPartyList.add(creditPartyItem);

        //add debit and credit party to transaction object
        quotationRequest.setDebitParty(debitPartyList);
        quotationRequest.setCreditParty(creditPartyList);


        //set amount,currency and request date into transaction request
        quotationRequest.setRequestAmount("75.30");
        quotationRequest.setRequestCurrency("RWF");
        quotationRequest.setRequestDate("2018-07-03T11:43:27.405Z");
        quotationRequest.setType("inttransfer");
        quotationRequest.setSubType("abc");
        quotationRequest.setChosenDeliveryMethod("agent");

        //sender kyc object
        KYCInformation senderKyc = new KYCInformation();
        senderKyc.setNationality("GB");
        senderKyc.setDateOfBirth("1970-07-03T11:43:27.405Z");
        senderKyc.setOccupation("manager");
        senderKyc.setEmployerName("MFX");
        senderKyc.setContactPhone("447125588999");
        senderKyc.setGender("m"); // m or f
        senderKyc.setEmailAddress("luke.skywalkeraaabbb@gmail.com");
        senderKyc.setBirthCountry("GB");

        // create object for documentation
        ArrayList<IdDocument> idDocumentItemList = new ArrayList<>();
        IdDocument idDocumentItem = new IdDocument();
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
        Address postalAddress = new Address();
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
        quotationRequest.setSenderKyc(senderKyc);

        //add custom data object to request object
        quotationRequest.setCustomData(customDataItemList);

        quotationRequest.setSendingServiceProviderCountry("AD");
        quotationRequest.setOriginCountry("AD");
        quotationRequest.setReceivingCountry("AD");



    }

    /**
     * create a transaction object for P2P transfer request
     */
    @SuppressWarnings("ConstantConditions")
    private void createP2PTransferObject() {

        transactionRequest = new Transaction();
        if (transactionRequest == null) {
            Utils.showToast(this, "Please request Quotation before performing this request");
            //noinspection UnnecessaryReturnStatement
            return;
        } else {

            //create debit party and credit party for internal transfer quotation
            ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
            ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();
            AccountIdentifier debitPartyItem = new AccountIdentifier();
            AccountIdentifier creditPartyItem = new AccountIdentifier();

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


            //sender kyc object
            KYCInformation senderKyc = new KYCInformation();
            senderKyc.setNationality("GB");
            senderKyc.setDateOfBirth("1970-07-03T11:43:27.405Z");
            senderKyc.setOccupation("manager");
            senderKyc.setEmployerName("MFX");
            senderKyc.setContactPhone("447125588999");
            senderKyc.setGender("m"); // m or f
            senderKyc.setEmailAddress("luke.skywalkeraaabbb@gmail.com");
            senderKyc.setBirthCountry("GB");

            // create object for documentation
            ArrayList<IdDocument> idDocumentItemList = new ArrayList<>();
            IdDocument idDocumentItem = new IdDocument();
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
            Address postalAddress = new Address();
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

            transactionRequest.setCustomData(customDataItemList);
            //add custom data object to request object


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

        }

    }

    /**
     * View Account Name
     */
    private void viewAccountName(int position) {
        showLoading();
        SDKManager.p2PTransfer.viewAccountName(createAccountIdentifier("accountid","1"), new AccountHolderInterface() {
            @Override
            public void onRetrieveAccountInfoSuccess(AccountHolderName accountHolderObject) {
                Log.d(SUCCESS, "onRetrieveAccountInfoSuccess: " + new Gson().toJson(accountHolderObject));
                if (accountHolderObject == null) {
                    hideLoading();
                    sbOutPut.append("Data is either null or empty");
                    customRecyclerAdapter.setStatus(2, position);
                    txtResponse.setText(sbOutPut);
                } else {
                    sbOutPut.append(new Gson().toJson(accountHolderObject));
                    if(position==2||position==3){
                        sbOutPut.append("\n\n Perform p2p transfer - Output\n\n");
                        performTransfer(position);
                    }else{
                        requestQuotation(position);
                    }

                }
            }

            @Override
            public void onRetrieveAccountInfoFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Failure");
                Log.d(FAILURE, "onRetrieveAccountInfoFailure: " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut);

            }


            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject));
                txtResponse.setText(sbOutPut);
                customRecyclerAdapter.setStatus(2, position);
            }

        });
    }



    /**
     * Perform P2P Transfer
     */
    private void performTransfer(int position) {
        SDKManager.p2PTransfer.createTransferTransaction(NotificationMethod.CALLBACK, "", transactionRequest, new RequestStateInterface() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Log.d(SUCCESS, "onRequestSuccess " + new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut);
                } else {
                    if(position==7){
                         sbOutPut.append(new Gson().toJson(requestStateObject)).append("\n\n");
                         getMissingTransaction(position);
                    }else{
                        Utils.showToast(P2PTransferActivity.this, "Success");
                        customRecyclerAdapter.setStatus(1, position);
                        sbOutPut.append(new Gson().toJson(requestStateObject)).append("\n\n");
                        txtResponse.setText(sbOutPut);
                    }
                }

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Failure");
                Log.d(FAILURE, "onRequestFailure " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut);

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject));
                txtResponse.setText(sbOutPut);
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);

            }

        });
    }


    //Check Balance
    private void balanceCheck(int position) {

        showLoading();
        SDKManager.p2PTransfer.viewAccountBalance(createAccountIdentifier("accountid","1"), new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Failure");
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject));
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @SuppressWarnings("unused")
            @Override
            public void onBalanceSuccess(Balance balance) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(balance));
                if (balance == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(balance));
                    Utils.showToast(P2PTransferActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

            @SuppressWarnings("unused")
            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }


    //Reversal
    private void reversal(int position) {
        showLoading();
        SDKManager.p2PTransfer.createReversal(NotificationMethod.POLLING, "", "REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(requestStateObject));
                    Utils.showToast(P2PTransferActivity.this, "Success");
                }
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
                txtResponse.setText(sbOutPut.toString());

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject));
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
     * Create Payment Reversal Object.
     */
    private void createPaymentReversalObject() {
        reversalObject = new Reversal();
        reversalObject.setType("reversal");
    }

    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability(int position) {
        showLoading();
        SDKManager.p2PTransfer.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject));
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(serviceAvailability));
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(P2PTransferActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
                customRecyclerAdapter.setStatus(1, position);
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(P2PTransferActivity.this, "Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }

    //Request the quotation to perform P2P transfer
    private void requestQuotation(int position) {
        sbOutPut.append("\n\nRequest Quotation - Output\n\n");
        SDKManager.p2PTransfer.createQuotation(NotificationMethod.CALLBACK, "", quotationRequest, new RequestStateInterface() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Log.d(SUCCESS, "onRequestSuccess " + new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    hideLoading();
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut);
                } else {
                    sbOutPut.append(new Gson().toJson(requestStateObject)).append("\n\n");
                    sbOutPut.append("\n\n Perform p2p transfer - Output\n\n");
                    performTransfer(position);
                }

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Log.d(FAILURE, "onRequestFailure " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut);


            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject));
                txtResponse.setText(sbOutPut);
                customRecyclerAdapter.setStatus(2, position);
              }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);
            }

        });
    }


    public void showLoading() {
        progressdialog.show();
    }

    public void hideLoading() {
        progressdialog.dismiss();
    }

    @Override
    public void onItemClick(View view, int position) {

        switch (position) {
            case 0:
                //P2P Transfer via Switch
                sbOutPut=new StringBuilder();
                sbOutPut.append("Retrieve the Name of the Recipient - Output \n\n");
                viewAccountName(position);

                break;

            case 1:
                //‘On-us’ P2P Transfer Initiated by a Third Party Provider
                sbOutPut=new StringBuilder();
                sbOutPut.append("Retrieve the Name of the Recipient -  Output \n\n");
                viewAccountName(position);
                 break;

            case 2:

                //Bilateral P2P Transfer
                sbOutPut=new StringBuilder();
                sbOutPut.append("Retrieve the Name of the Recipient - Output  \n\n");
                viewAccountName(position);
                break;

            case 3:
                //P2P Transfer Reversal
                sbOutPut = new StringBuilder();
                sbOutPut.append("Reversal - Output\n\n");
                reversal(position);
                break;
            case 4:
                //Obtain an FSP Balance
                sbOutPut = new StringBuilder();
                sbOutPut.append("Balance - Output\n\n");
                balanceCheck(position);
                break;

            case 5:
                //Retrieve transaction FSP
                sbOutPut = new StringBuilder();
                sbOutPut.append("Retrieve Transaction - Output\n\n");
                retrieveTransactionFSP(position);
                break;

            case 6:
                //check service availability
                sbOutPut = new StringBuilder();
                sbOutPut.append("Check Service Availability - Output\n\n");
                checkServiceAvailability(position);
                break;

            case 7:
                //missing response
                sbOutPut = new StringBuilder();
                showLoading();
                sbOutPut.append("Create Missing Transaction - Output\n\n");
                performTransfer(position);
                break;

            default:
                break;
        }

    }
}
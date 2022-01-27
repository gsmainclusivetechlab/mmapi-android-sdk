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
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
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

    private String transactionRef = "";
    private String serverCorrelationId;

    private Quotation quotationRequest;
    private Transaction transactionRequest;

    ArrayList<Identifier> identifierArrayList;

    private CustomUseCaseRecyclerAdapter customRecyclerAdapter;


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


        RecyclerView recyclerView = findViewById(R.id.p2pTransferList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        customRecyclerAdapter = new CustomUseCaseRecyclerAdapter(this, true, p2pTransfersArray);
        customRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(customRecyclerAdapter);


        txtResponse = findViewById(R.id.txtP2PResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());

        progressdialog = Utils.initProgress(P2PTransferActivity.this);
        checkServiceAvailability();
        createPaymentReversalObject();
        createAccountIdentifier();

    }


    //Retrieve a missing Transaction
    private void getMissingTransaction(int position) {
        showLoading();

        SDKManager.p2PTransfer.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(missingResponse));
                Log.d(SUCCESS, "onMissingTransactionSuccess: " + new Gson().toJson(missingResponse));
                if (missingResponse == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Utils.showToast(P2PTransferActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
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


        identifierArrayList.clear();
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2999");
        identifierArrayList.add(identifierAccount);


        SDKManager.p2PTransfer.viewAccountTransactions(identifierArrayList, transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transaction));
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
                Transaction transactionRequest = transaction.getTransaction().get(0);
                if (transactionRequest == null
                        || transactionRequest.getTransactionReference() == null
                        || transactionRequest.getTransactionStatus() == null
                        || transactionRequest.getCurrency() == null
                        || transactionRequest.getCreditParty() == null
                        || transactionRequest.getDebitParty() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }


            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }
        });
    }

    /**
     * create a transaction object for P2P Quotation request
     */
    private void createP2PQuotationObject(int position) {

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


        //request for quotation
        requestQuotation(position);

    }

    /**
     * create a transaction object for P2P transfer request
     */
    @SuppressWarnings("ConstantConditions")
    private void createP2PTransferObject(int position) {

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

            performTransfer(position);
        }

    }

    /**
     * View Account Name
     */
    private void viewAccountName(int position) {
        showLoading();
        SDKManager.p2PTransfer.viewAccountName(identifierArrayList, new AccountHolderInterface() {
            @Override
            public void onRetrieveAccountInfoSuccess(AccountHolderName accountHolderObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(accountHolderObject));
                Utils.showToast(P2PTransferActivity.this, "Success");
                Log.d(SUCCESS, "onRetrieveAccountInfoSuccess: " + new Gson().toJson(accountHolderObject));
                if (accountHolderObject == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }


            }

            @Override
            public void onRetrieveAccountInfoFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onRetrieveAccountInfoFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }


            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

        });
    }

    /**
     * Perform P2P Transfer
     */
    private void performTransfer(int position) {

        showLoading();
        SDKManager.p2PTransfer.createTransferTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                Log.d(SUCCESS, "onRequestSuccess " + new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Log.d(FAILURE, "onRequestFailure " + new Gson().toJson(gsmaError));
                txtResponse.setText(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
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
        SDKManager.p2PTransfer.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @SuppressWarnings("unused")
            @Override
            public void onBalanceSuccess(Balance balance) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(balance));
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
                if (balance == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }

            }

            @SuppressWarnings("unused")
            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
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
                Utils.showToast(P2PTransferActivity.this, "Success");

                txtResponse.setText(new Gson().toJson(requestStateObject));
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
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
     * Method for fetching a particular transaction.
     */
    private void viewTransaction(int position) {
        showLoading();
        SDKManager.p2PTransfer.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onTransactionSuccess(Transaction transactionObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionObject));
                if (transactionObject == null
                        || transactionObject.getTransactionReference() == null
                        || transactionObject.getTransactionStatus() == null
                        || transactionObject.getCurrency() == null
                        || transactionObject.getCreditParty() == null
                        || transactionObject.getDebitParty() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }


            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

        });
    }


    //get the request state of a transaction
    private void requestState(int position) {
        showLoading();
        SDKManager.p2PTransfer.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                transactionRef = requestStateObject.getObjectReference();
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
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


    public void viewQuotation(int position) {
        showLoading();
        SDKManager.p2PTransfer.viewQuotation(transactionRef, new TransactionInterface() {
            @Override
            public void onTransactionSuccess(Transaction transactionObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transactionObject));
                Log.d(SUCCESS, "onTransactionSuccess " + new Gson().toJson(transactionObject));

                if (transactionObject == null
                        || transactionObject.getTransactionReference() == null
                        || transactionObject.getTransactionStatus() == null
                        || transactionObject.getCurrency() == null
                        || transactionObject.getCreditParty() == null
                        || transactionObject.getDebitParty() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }


            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Log.d(FAILURE, "onTransactionFailure " + new Gson().toJson(gsmaError));
                txtResponse.setText(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);

            }
        });

    }

    @SuppressWarnings("CommentedOutCode")
    private void createAccountIdentifier() {

        identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("1");
        identifierArrayList.add(identifierAccount);

        //msisdn
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
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
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
    private void requestQuotation(int position) {
        showLoading();
        SDKManager.p2PTransfer.createQuotation(NotificationMethod.POLLING, "", quotationRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(P2PTransferActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                Log.d(SUCCESS, "onRequestSuccess " + new Gson().toJson(requestStateObject));
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Log.d(FAILURE, "onRequestFailure " + new Gson().toJson(gsmaError));
                txtResponse.setText(new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);


            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(P2PTransferActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
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
                //Retrieve the Name of the Recipient;
                viewAccountName(position);
                break;

            case 1:
                //Request a P2P Quotation;
                createP2PQuotationObject(position);
                break;

            case 2:
                //Perform a P2P Transfer
                createP2PTransferObject(position);
                break;

            case 3:
                //P2P Transfer Reversal
                reversal(position);
                break;
            case 4:
                //Obtain an FSP Balance
                balanceCheck(position);
                break;

            case 5:
                retrieveTransactionFSP(position);

                break;

            case 6:
                //Missing Transaction

                getMissingTransaction(position);
                break;

            case 7:

                viewQuotation(position);
                break;

            case 8:
                //view request State
                requestState(position);
                break;

            case 9:
                //view Transaction
                viewTransaction(position);
                break;
            default:
                break;
        }

    }
}
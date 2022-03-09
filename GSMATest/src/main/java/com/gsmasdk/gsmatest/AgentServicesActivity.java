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
import com.gsmaSdk.gsma.interfaces.AccountInterface;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeItemInterface;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.MissingResponseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.interfaces.RetrieveTransactionInterface;
import com.gsmaSdk.gsma.interfaces.ServiceAvailabilityInterface;
import com.gsmaSdk.gsma.interfaces.TransactionInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.Account;
import com.gsmaSdk.gsma.models.account.AccountHolderName;
import com.gsmaSdk.gsma.models.account.AccountIdentifier;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.Identity;
import com.gsmaSdk.gsma.models.account.TransactionFilter;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.common.Address;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.Fees;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.IdDocument;
import com.gsmaSdk.gsma.models.common.KYCInformation;
import com.gsmaSdk.gsma.models.common.MissingResponse;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;
import com.gsmaSdk.gsma.models.common.SubjectName;
import com.gsmaSdk.gsma.models.transaction.PatchData;
import com.gsmaSdk.gsma.models.transaction.reversal.Reversal;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ALL")
public class AgentServicesActivity extends AppCompatActivity implements CustomUseCaseRecyclerAdapter.ItemClickListener {


    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String VALIDATION = "validation";

    private TextView txtResponse;
    private ProgressDialog progressdialog;
    private String correlationId = "";
    private AuthorisationCode authorisationCodeRequest;

    private String transactionRef = "";

    private String serverCorrelationId;
    ArrayList<Identifier> identifierArrayList;
    private Transaction transactionRequest;
    private Account accountRequest;
    private Reversal reversalObject;
    private ArrayList<PatchData> patchDataArrayList;
    private String identityId = "";
    private StringBuilder sbOutPut;

    private CustomUseCaseRecyclerAdapter customRecyclerAdapter;


    private final String[] agentServiceArray = {
            "Agent Initiated Cash-Out",
            "Agent-initiated Cash-out using the Polling Method",
            "Customer-initiated Cash-out",
            "Customer Cash-out at an ATM using an Authorisation Code",
            "Agent-initiated Customer Cash-in",
            "Cash-out Reversal",
            "Register a Customer Mobile Money Account",
            "Verify a Customer’s KYC",
            "Obtain an Agent Balance",
            "Retrieve Transactions for an Agent",
            "Check for Service Availability",
            "Retrieve a Missing API Response"

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_service);

        setTitle("Agent Services");

        RecyclerView recyclerView = findViewById(R.id.agentServiceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        customRecyclerAdapter = new CustomUseCaseRecyclerAdapter(this, true, agentServiceArray);
        customRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(customRecyclerAdapter);

        txtResponse = findViewById(R.id.txtAgentServiceResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());
        progressdialog = Utils.initProgress(AgentServicesActivity.this);


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
        SDKManager.agentService.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(serviceAvailability).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(1, position);
                Utils.showToast(AgentServicesActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
                Utils.showToast(AgentServicesActivity.this, "Failure");
                Log.d(FAILURE, "onServiceAvailabilityFailure: " + new Gson().toJson(gsmaError));
            }
        });
    }


    /**
     * Payment Reversal
     */
    private void paymentReversal(int position) {
        showLoading();
        SDKManager.agentService.createReversal(NotificationMethod.CALLBACK, "", "REF-1633580365289", reversalObject, new RequestStateInterface() {
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
                    Utils.showToast(AgentServicesActivity.this, "Success");
                }
                Log.d(SUCCESS, "onReversalSuccess:" + new Gson().toJson(requestStateObject));
                txtResponse.setText(sbOutPut.toString());
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
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

    private void createAccountObject(int position) {

        Random r = new Random();
        int keyValue = r.nextInt(4599 - 28) + 28;
        accountRequest = new Account();

        ArrayList<AccountIdentifier> accountIdentifiers = new ArrayList<>();

        //account identifiers
        AccountIdentifier accountIdentifier = new AccountIdentifier();
        accountIdentifier.setKey("msisdn");
        accountIdentifier.setValue(String.valueOf(keyValue));
        accountIdentifiers.add(accountIdentifier);

        //add account identifier to account object
        accountRequest.setAccountIdentifiers(accountIdentifiers);

        //identity array
        ArrayList<Identity> identityArrayList = new ArrayList<>();
        //identity object
        Identity identity = new Identity();

        //kyc information
        KYCInformation kycInformation = new KYCInformation();

        kycInformation.setBirthCountry("AD");
        kycInformation.setContactPhone("+447777777777");
        kycInformation.setDateOfBirth("2000-11-20");
        kycInformation.setEmailAddress("xyz@xyz.com");
        kycInformation.setEmployerName("String");
        kycInformation.setGender("m");

        //create  id document object

        ArrayList<IdDocument> idDocumentArrayList = new ArrayList<>();
        IdDocument idDocument = new IdDocument();
        idDocument.setIdType("passport");
        idDocument.setIdNumber("111111");
        idDocument.setIssueDate("2018-11-20");
        idDocument.setExpiryDate("2018-11-20");
        idDocument.setIssuer("ABC");
        idDocument.setIssuerPlace("DEF");
        idDocument.setIssuerCountry("AD");

        idDocumentArrayList.add(idDocument);

        kycInformation.setIdDocument(idDocumentArrayList);


        kycInformation.setNationality("AD");
        kycInformation.setOccupation("Miner");

        //Postal Address
        Address address = new Address();
        address.setAddressLine1("37");
        address.setAddressLine2("ABC Drive");
        address.setAddressLine3("string");
        address.setCity("Berlin");
        address.setStateProvince("string");
        address.setPostalCode("AF1234");
        address.setCountry("AD");

        kycInformation.setPostalAddress(address);

        //subject information
        SubjectName subjectName = new SubjectName();
        subjectName.setTitle("Mr");
        subjectName.setFirstName("H");
        subjectName.setMiddleName("I");
        subjectName.setLastName("J");
        subjectName.setFullName("H I J ");
        subjectName.setNativeName("string");


        kycInformation.setSubjectName(subjectName);

        identity.setIdentityKyc(kycInformation);
        identity.setAccountRelationship("accountHolder");
        identity.setKycVerificationStatus("verified");
        identity.setKycVerificationEntity("ABC Agent");

        //kyc level
        identity.setKycLevel(1);

        //custom data for identity
        ArrayList<CustomDataItem> customDataItemArrayList = new ArrayList<>();
        CustomDataItem customDataItem = new CustomDataItem();
        customDataItem.setKey("test");
        customDataItem.setValue("custom");

        customDataItemArrayList.add(customDataItem);

        identity.setCustomData(customDataItemArrayList);

        //add identity to array
        identityArrayList.add(identity);

        //add indentity array into account object
        accountRequest.setIdentity(identityArrayList);


        //account type
        accountRequest.setAccountType("string");

        //custom data for account


        ArrayList<CustomDataItem> customDataItemAccountArrayList = new ArrayList<>();
        CustomDataItem customDataAccountItem = new CustomDataItem();
        customDataAccountItem.setKey("test");
        customDataAccountItem.setValue("custom1");

        customDataItemAccountArrayList.add(customDataAccountItem);

        accountRequest.setCustomData(customDataItemAccountArrayList);

        //Fees array

        ArrayList<Fees> feesArrayList = new ArrayList<>();

        Fees fees = new Fees();
        fees.setFeeType("string");
        fees.setFeeAmount("5.46");
        fees.setFeeCurrency("AED");

        feesArrayList.add(fees);

        accountRequest.setFees(feesArrayList);


        accountRequest.setRegisteringEntity("ABC Agent");
        accountRequest.setRequestDate("2021-02-17T15:41:45.194Z");

        //create account using accout object

        createAccount(position);
    }

    private void createAccount(int position) {
        showLoading();
        sbOutPut.append("\n\nCreate Account - output\n\n");
        SDKManager.agentService.createAccount(NotificationMethod.CALLBACK, "", accountRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();

                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
                    Utils.showToast(AgentServicesActivity.this, "Success");
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString()+"\n\n");
                    txtResponse.setText(sbOutPut.toString());

                }

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut);
                Utils.showToast(AgentServicesActivity.this, "Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void getCorrelationId(String correlationID) {
                correlationId = correlationID;
                Log.d("getCorrelationId", "correlationId: " + correlationID);

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();

                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);

            }
        });

    }


    private void createAccountIdentifier() {
        identifierArrayList = new ArrayList<>();

        //account id
        Identifier identifierAccount = new Identifier();
        identifierAccount.setKey("accountid");
        identifierAccount.setValue("2999");

        identifierArrayList.add(identifierAccount);

    }

    public void showLoading() {
        progressdialog.show();
    }

    public void hideLoading() {
        progressdialog.dismiss();
    }


    /**
     * Retrieve a missing Transaction
     */
    private void getMissingTransaction(int position) {
        showLoading();
        sbOutPut.append("\n\nMissing Response -Output\n\n");
        SDKManager.agentService.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
                hideLoading();
                if (missingResponse == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(missingResponse).toString());
                    Utils.showToast(AgentServicesActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onMissingTransactionSuccess: " + new Gson().toJson(missingResponse));
            }

            @Override
            public void onMissingResponseFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(AgentServicesActivity.this, "Failure");
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
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

        SDKManager.agentService.viewAccountTransactions(createAccountIdentifier("accountid","2999"), transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {
                hideLoading();

                if (transaction.getTransaction().get(0) == null
                        || transaction.getTransaction().get(0).getTransactionReference() == null
                        || transaction.getTransaction().get(0).getTransactionStatus() == null
                        || transaction.getTransaction().get(0).getCurrency() == null
                        || transaction.getTransaction().get(0).getCreditParty() == null
                        || transaction.getTransaction().get(0).getDebitParty() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    sbOutPut.append(new Gson().toJson(transaction).toString());
                    customRecyclerAdapter.setStatus(1, position);
                    Utils.showToast(AgentServicesActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));
            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onRetrieveTransactionFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }
        });
    }

    /**
     * Checking Balance.
     */
    private void balanceCheck(int position) {
        showLoading();
        SDKManager.agentService.viewAccountBalance(createAccountIdentifier("accountid","1"), new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
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
                    Utils.showToast(AgentServicesActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onBalanceFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }
        });
    }


    private void updateKYCStatus(int position) {
        PatchData patchObject = new PatchData();
        patchObject.setOp("replace");
        patchObject.setPath("/kycVerificationStatus");
        patchObject.setValue("verified");
        patchDataArrayList = new ArrayList<>();
        patchDataArrayList.add(patchObject);

        showLoading();

        sbOutPut.append("\n\nUpdate Account Identity -Output\n\n");
        SDKManager.agentService.updateAccountIdentity(NotificationMethod.CALLBACK, "", identityId, patchDataArrayList, identifierArrayList, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
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
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    Utils.showToast(AgentServicesActivity.this, "Success");
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Utils.showToast(AgentServicesActivity.this, "Failure");
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
     * Code Request Object for Obtaining Authorisation code.
     */
    private void createCodeRequestObject() {
        authorisationCodeRequest = new AuthorisationCode();
        authorisationCodeRequest.setAmount("200.00");
        authorisationCodeRequest.setRequestDate("2021-10-18T10:43:27.405Z");
        authorisationCodeRequest.setCurrency("RWF");
        authorisationCodeRequest.setCodeLifetime(1);
    }


    /**
     * View Account-View Account details
     */

    private void viewAccount(int position) {
        showLoading();
        SDKManager.agentService.viewAccount(createAccountIdentifier("accountid","2999"), new AccountInterface() {
            @Override
            public void onAccountSuccess(Account account) {
                hideLoading();
                identityId = account.getIdentity().get(0).getIdentityId();

                if (account.getAccountIdentifiers() == null
                        || account.getIdentity() == null
                        || account.getAccountStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(account).toString());
                    updateKYCStatus(position);
                }
                txtResponse.setText(sbOutPut.toString());
                Log.d(SUCCESS, "onAccountSuccess: " + new Gson().toJson(account));
            }

            @Override
            public void onAccountFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(FAILURE, "onAccountFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }
        });

    }


    /**
     * View Account Name
     */
    private void viewAccountName(int position) {
        showLoading();
        SDKManager.agentService.viewAccountName(createAccountIdentifier("accountid","1"), new AccountHolderInterface() {
            @Override
            public void onRetrieveAccountInfoSuccess(AccountHolderName accountHolderObject) {
                Log.d(SUCCESS, "onRetrieveAccountInfoSuccess: " + new Gson().toJson(accountHolderObject));
                if (accountHolderObject == null) {
                    hideLoading();
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    sbOutPut.append(new Gson().toJson(accountHolderObject) + "\n\n");
                    createAccountObject(position);
                }
            }


            @Override
            public void onRetrieveAccountInfoFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError));
                txtResponse.setText(sbOutPut);
                Log.d(FAILURE, "onRetrieveAccountInfoFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }


            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject));
                txtResponse.setText(sbOutPut);
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);

            }

        });
    }

    /**
     * View Authorization Code
     */

    private void viewAuthorizationCode(int position) {
        sbOutPut.append("\n\nView Authorization Code - Output \n\n");
        SDKManager.agentService.viewAuthorisationCode(createAccountIdentifier("accountid","2999"),transactionRef, new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {
                if (authorisationCodeItem.getAuthorisationCode() == null || authorisationCodeItem.getCodeState() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty\n\n");
                    txtResponse.setText(sbOutPut.toString());
                    hideLoading();
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(authorisationCodeItem).toString() + "\n\n");
                    txtResponse.setText(sbOutPut.toString());
                    sbOutPut.append("\n\nCreate withdrawal transaction - Output\n\n");
                    createWithdrawalTransaction(position, NotificationMethod.CALLBACK);
                }
            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                Log.d(FAILURE, "onAuthorizationCodeFailure: " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError).toString() + "\n\n");
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "" + errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString() + "\n\n");
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }
        });

    }


    /**
     * Authorization Code
     */
    private void obtainAuthorizationCode(int position) {
        showLoading();
        SDKManager.agentService.createAuthorisationCode(createAccountIdentifier("accountid","2999"), NotificationMethod.CALLBACK, "", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                customRecyclerAdapter.setStatus(2, position);
                sbOutPut.append(new Gson().toJson(errorObject) + "\n\n");

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                Log.d(SUCCESS, "onRequestStateSuccess: " + new Gson().toJson(requestStateObject));
                txtResponse.setText(new Gson().toJson(requestStateObject));
                sbOutPut.append(new Gson().toJson(requestStateObject) + "\n\n");
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    hideLoading();
                } else {
                    serverCorrelationId = requestStateObject.getServerCorrelationId();
                    requestState(position);
                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
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
     * Create Withdrawal Transaction
     */
    private void createWithdrawalTransaction(int position, NotificationMethod notificationMethod) {
        showLoading();
        SDKManager.agentService.createWithdrawalTransaction(notificationMethod, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject) + "\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    hideLoading();
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty\n\n");
                    txtResponse.setText(sbOutPut.toString());

                } else {
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    if(position==11){
                        getMissingTransaction(position);
                        return;
                    }

                    if (notificationMethod == NotificationMethod.POLLING) {
                        requestState(position);
                    } else {
                        hideLoading();
                        customRecyclerAdapter.setStatus(1, position);
                        txtResponse.setText(sbOutPut.toString());
                    }

                }

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");                sbOutPut.append(new Gson().toJson(gsmaError) + "\n\n");
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
     * Create Deposit Transaction
     */
    private void createDepositTransaction(int position) {
        sbOutPut.append("\n\n Create Deposit Transaction - Output\n\n");
        SDKManager.agentService.createDepositTransaction(NotificationMethod.CALLBACK, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject));
                txtResponse.setText(sbOutPut);
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                hideLoading();
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");

                } else {
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString() + "\n\n");
                    customRecyclerAdapter.setStatus(1, position);
                }
                txtResponse.setText(sbOutPut);
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError));
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

    /**
     * Get the request state of a transaction
     */
    private void requestState(int position) {
        sbOutPut.append("\n\n Request State - Output\n\n");
        SDKManager.agentService.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(errorObject));
                sbOutPut.append(new Gson().toJson(errorObject).toString());
                txtResponse.setText(sbOutPut.toString());
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                if (requestStateObject == null || requestStateObject.getStatus() == null) {
                    hideLoading();
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty\n\n");
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    transactionRef = requestStateObject.getObjectReference();
                    sbOutPut.append(new Gson().toJson(requestStateObject).toString());
                    if (position == 3) {
                        viewAuthorizationCode(position);
                    } else {
                        sbOutPut.append("\n\n View Transaction - Output\n\n");
                        viewTransaction(position);
                    }

                }
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                hideLoading();
                Log.d(FAILURE, "onRequestStateFailure: " + new Gson().toJson(gsmaError));
                sbOutPut.append(new Gson().toJson(gsmaError).toString());
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
     * View Transaction-View the transaction Details
     */
    private void  viewTransaction(int position) {
        SDKManager.agentService.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                sbOutPut.append(new Gson().toJson(errorObject) + "\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());

            }

            @Override
            public void onTransactionSuccess(Transaction transactionRequest) {

                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionRequest));
                if (transactionRequest == null
                        || transactionRequest.getTransactionReference() == null
                        || transactionRequest.getTransactionStatus() == null
                        || transactionRequest.getCurrency() == null
                        || transactionRequest.getCreditParty() == null
                        || transactionRequest.getDebitParty() == null
                ) {
                    hideLoading();
                    customRecyclerAdapter.setStatus(2, position);
                    sbOutPut.append("Data is either null or empty");
                } else {
                    hideLoading();
                    customRecyclerAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(transactionRequest) + "\n\n");

                }
                txtResponse.setText(sbOutPut.toString());


            }

            @Override
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                sbOutPut.append(new Gson().toJson(gsmaError) + "\n\n");
                customRecyclerAdapter.setStatus(2, position);
                txtResponse.setText(sbOutPut.toString());

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

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                //Agent Initiated Cash-Out ;
                sbOutPut = new StringBuilder();
                sbOutPut.append("Create withdrawal transaction - Output\n\n");
                createWithdrawalTransaction(position, NotificationMethod.CALLBACK);
                break;
            case 1:
                //Agent-initiated Cash-out using the Polling Method
                sbOutPut = new StringBuilder();
                sbOutPut.append("Create withdrawal transaction - Output\n\n");
                createWithdrawalTransaction(position, NotificationMethod.POLLING);

                break;
            case 2:
                //Agent Initiated Cash-Out ;
                sbOutPut = new StringBuilder();
                sbOutPut.append("Create withdrawal transaction - Output\n\n");
                createWithdrawalTransaction(position, NotificationMethod.CALLBACK);
                break;

            case 3:
                //Customer Cash-out at an ATM using an Authorisation Code
                sbOutPut = new StringBuilder();
                sbOutPut.append("Create an authorisation code - Output \n\n");
                obtainAuthorizationCode(position);

                break;
            case 4:
                //Agent-initiated Customer Cash-in
                sbOutPut = new StringBuilder();
                sbOutPut.append("View Account Name - Output \n\n");
                viewAccountName(position);

                break;
            case 5:
                //Cash-out Reversal
                sbOutPut = new StringBuilder();
                sbOutPut.append("Revesal-Output \n\n");
                paymentReversal(position);

                break;
            case 6:
                //Register a Customer Mobile Money Account
                sbOutPut = new StringBuilder();
                showLoading();
                createDepositTransaction(position);

                break;
            case 7:
                // Verify customer KYC
                sbOutPut = new StringBuilder();
                sbOutPut.append("View Account information - Output\n\n");
                viewAccount(position);
                break;

            case 8:
                //Obtain Balance
                sbOutPut = new StringBuilder();
                sbOutPut.append("Balance -Output\n\n");
                balanceCheck(position);
                break;
            case 9:
                //Retrieve transaction
                sbOutPut = new StringBuilder();
                sbOutPut.append("Retrieve Transaction -Output\n\n");
                retrieveTransaction(position);
                break;
            case 10:
                //check service availability
                sbOutPut = new StringBuilder();
                sbOutPut.append("Check Service Availability -Output\n\n");
                checkServiceAvailability(position);
                break;
            case 11:
                //missing response
                sbOutPut = new StringBuilder();
                sbOutPut.append("Create Withdrawal Transaction -Output\n\n");
                createWithdrawalTransaction(position,NotificationMethod.CALLBACK);
                break;

            default:
                break;
        }
    }
}


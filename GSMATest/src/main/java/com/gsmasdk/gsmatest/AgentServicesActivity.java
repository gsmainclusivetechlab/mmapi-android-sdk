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

    private CustomUseCaseRecyclerAdapter customRecyclerAdapter;


    private final String[] agentServiceArray = {
            "Agent Initiated Cash-Out",
            "View Request State",
            "View Transaction",
            "Create Authorization Code",
            "View Authorization Code",
            "Retrieve the Name of the Depositing Customer",
            "Agent Initiated Cash-in",
            "Perform a Transaction Reversal",
            "Create a Mobile Money Account",
            "Retrieve Account Information",
            "Update KYC Verification Status",
            "Obtain an Agent Balance",
            "Retrieve a Set of Transactions for an Account",
            "Retrieve a Missing API Response"

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_service);

        setTitle("Agent Services");

        RecyclerView recyclerView = findViewById(R.id.agentServiceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        customRecyclerAdapter = new CustomUseCaseRecyclerAdapter(this,true, agentServiceArray);
        customRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(customRecyclerAdapter);

        txtResponse = findViewById(R.id.txtAgentServiceResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());
        progressdialog = Utils.initProgress(AgentServicesActivity.this);

        checkServiceAvailability();
        createAccountIdentifier();
        createTransactionObject();
        createCodeRequestObject();
        createPaymentReversalObject();

    }

    /**
     * Method for checking Service Availability.
     */
    private void checkServiceAvailability() {
        showLoading();
        SDKManager.agentService.viewServiceAvailability(new ServiceAvailabilityInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
            }

            @Override
            public void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(serviceAvailability));
                Utils.showToast(AgentServicesActivity.this, "Success");
                Log.d(SUCCESS, "onServiceAvailabilitySuccess: " + new Gson().toJson(serviceAvailability));
            }

            @Override
            public void onServiceAvailabilityFailure(GSMAError gsmaError) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(gsmaError));
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
        SDKManager.agentService.createReversal(NotificationMethod.POLLING, "", "REF-1633580365289", reversalObject, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Success");
                serverCorrelationId = requestStateObject.getServerCorrelationId();
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
                Utils.showToast(AgentServicesActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                hideLoading();
                Log.d(FAILURE, "onReversalFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
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
        SDKManager.agentService.createAccount(NotificationMethod.CALLBACK, "", accountRequest, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                Utils.showToast(AgentServicesActivity.this, "Success");
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
        SDKManager.agentService.viewResponse(correlationId, new MissingResponseInterface() {
            @Override
            public void onMissingResponseSuccess(MissingResponse missingResponse) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Success");
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
                Utils.showToast(AgentServicesActivity.this, "Failure");
                Log.d(FAILURE, "onMissingResponseFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

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

    /**
     * Retrieve Transaction
     */
    private void retrieveTransaction(int position) {
        showLoading();

        TransactionFilter transactionFilter = new TransactionFilter();
        transactionFilter.setLimit(5);
        transactionFilter.setOffset(0);

        SDKManager.agentService.viewAccountTransactions(identifierArrayList, transactionFilter, new RetrieveTransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);


            }

            @Override
            public void onRetrieveTransactionSuccess(Transactions transaction) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(transaction));
                Log.d(SUCCESS, "onRetrieveTransactionSuccess: " + new Gson().toJson(transaction));

                if (transaction.getTransaction().get(0) == null
                        || transaction.getTransaction().get(0).getTransactionReference() == null
                        || transaction.getTransaction().get(0).getTransactionStatus() == null
                        || transaction.getTransaction().get(0).getCurrency() == null
                        || transaction.getTransaction().get(0).getCreditParty() == null
                        || transaction.getTransaction().get(0).getDebitParty() == null
                ) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }

            }

            @Override
            public void onRetrieveTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
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
        SDKManager.agentService.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onBalanceSuccess(Balance balance) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(balance).toString());
                Log.d(SUCCESS, "onBalanceSuccess: " + new Gson().toJson(balance));
                if (balance == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);
                }
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
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
        SDKManager.agentService.updateAccountIdentity(NotificationMethod.POLLING, "", identityId, patchDataArrayList, identifierArrayList, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(AgentServicesActivity.this, "Success");
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
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
        SDKManager.agentService.viewAccount(identifierArrayList, new AccountInterface() {
            @Override
            public void onAccountSuccess(Account account) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(account));
                identityId = account.getIdentity().get(0).getIdentityId();
                Utils.showToast(AgentServicesActivity.this, "Success");
                Log.d(SUCCESS, "onAccountSuccess: " + new Gson().toJson(account));

                if (account.getAccountIdentifiers() == null
                        || account.getIdentity() == null
                        || account.getAccountStatus() == null) {
                    customRecyclerAdapter.setStatus(2, position);

                } else {
                    customRecyclerAdapter.setStatus(1, position);

                }

            }

            @Override
            public void onAccountFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onAccountFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);
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


    /**
     * View Account Name
     */
    private void viewAccountName(int position) {
        showLoading();
        SDKManager.agentService.viewAccountName(identifierArrayList, new AccountHolderInterface() {
            @Override
            public void onRetrieveAccountInfoSuccess(AccountHolderName accountHolderObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(accountHolderObject));
                Utils.showToast(AgentServicesActivity.this, "Success");
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
                Utils.showToast(AgentServicesActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onAuthorizationCodeFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

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

    /**
     * View Authorization Code
     */

    private void viewAuthorizationCode(int position) {
        showLoading();
        SDKManager.agentService.viewAuthorisationCode(identifierArrayList, transactionRef, new AuthorisationCodeItemInterface() {
            @Override
            public void onAuthorisationCodeSuccess(AuthorisationCode authorisationCodeItem) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(authorisationCodeItem));
                Log.d(SUCCESS, "onAuthorizationCodeItem: " + new Gson().toJson(authorisationCodeItem));

                if (authorisationCodeItem.getAuthorisationCode() == null || authorisationCodeItem.getCodeState() == null) {
                    customRecyclerAdapter.setStatus(2, position);
                } else {
                    customRecyclerAdapter.setStatus(1, position);

                }

            }

            @Override
            public void onAuthorisationCodeFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onAuthorizationCodeFailure: " + new Gson().toJson(gsmaError));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "" + errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);

            }
        });

    }


    /**
     * Authorization Code
     */
    private void obtainAuthorizationCode(int position) {
        showLoading();
        SDKManager.agentService.createAuthorisationCode(identifierArrayList, NotificationMethod.POLLING, "", authorisationCodeRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Success");
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
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
                Utils.showToast(AgentServicesActivity.this, "Failure");
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


    private void createWithdrawalTransaction(int position) {
        showLoading();
        SDKManager.agentService.createWithdrawalTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(AgentServicesActivity.this, "Success");
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
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

    private void createDepositTransaction(int position) {
        showLoading();
        SDKManager.agentService.createDepositTransaction(NotificationMethod.POLLING, "", transactionRequest, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);
            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(requestStateObject));
                serverCorrelationId = requestStateObject.getServerCorrelationId();
                Utils.showToast(AgentServicesActivity.this, "Success");
                Log.d(SUCCESS, "onRequestStateSuccess:" + new Gson().toJson(requestStateObject));
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
        showLoading();
        SDKManager.agentService.viewRequestState(serverCorrelationId, new RequestStateInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Success");
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


    /**
     * View Transaction-View the transaction Details
     */
    private void viewTransaction(int position) {
        showLoading();
        SDKManager.agentService.viewTransaction(transactionRef, new TransactionInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, errorObject.getErrorDescription());
                Log.d(VALIDATION, "onValidationError: " + new Gson().toJson(errorObject));
                customRecyclerAdapter.setStatus(2, position);

            }

            @Override
            public void onTransactionSuccess(Transaction transactionRequest) {
                hideLoading();
                txtResponse.setText(new Gson().toJson(transactionRequest));
                Utils.showToast(AgentServicesActivity.this, "Success");
                Log.d(SUCCESS, "onTransactionSuccess: " + new Gson().toJson(transactionRequest));
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
            public void onTransactionFailure(GSMAError gsmaError) {
                hideLoading();
                Utils.showToast(AgentServicesActivity.this, "Failure");
                txtResponse.setText(new Gson().toJson(gsmaError));
                Log.d(FAILURE, "onTransactionFailure: " + new Gson().toJson(gsmaError));
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

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                //Agent Initiated Cash-Out ;
                createWithdrawalTransaction(position);
                break;
            case 1:
                //View Request State
                requestState(position);
                break;
            case 2:
                //View Transaction
                viewTransaction(position);
                break;

            case 3:
                //Create Authorization Code
                obtainAuthorizationCode(position);

                break;
            case 4:
                //View Authorization Code
                viewAuthorizationCode(position);
                break;
            case 5:
                //Retrieve the Name of the Depositing Customer
                viewAccountName(position);

                break;
            case 6:
                //Agent Initiated Cash-in
                createDepositTransaction(position);

                break;
            case 7:
                // Perform a Transaction Reversal
                paymentReversal(position);

                break;

            case 8:
                // Create a Mobile Money Account
                createAccountObject(position);

                break;
            case 9:
                // Retrieve Account Information
                viewAccount(position);

                break;
            case 10:
                // Update KYC Verification Status

                updateKYCStatus(position);

                break;
            case 11:
                // Obtain an Agent Balance

                balanceCheck(position);

                break;
            case 12:
                //Retrieve a Set of Transactions for an Account
                retrieveTransaction(position);

                break;
            case 13:
                //Retrieve a Missing API Response
                getMissingTransaction(position);
                break;

            default:
                break;
        }
    }
}


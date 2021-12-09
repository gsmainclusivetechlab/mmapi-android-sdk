package com.gsmaSdk.gsma.models.transaction.transactions;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.authorisationCode.MetaDataObject;
import com.gsmaSdk.gsma.models.common.CreditPartyItem;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.DebitPartyItem;
import com.gsmaSdk.gsma.models.common.Fees;
import com.gsmaSdk.gsma.models.common.RequestingOrganisation;
import com.gsmaSdk.gsma.models.common.Kyc;
import com.gsmaSdk.gsma.models.common.InternationalTransferInformation;
import com.gsmaSdk.gsma.network.responses.BaseResponse;


/**
 * Model class for Transaction Request
 */
@SuppressWarnings("ALL")
public class TransactionRequest extends BaseResponse {

    @SerializedName("transactionReference")
    @Expose
    private String transactionReference;

    @SerializedName("requestingOrganisationTransactionReference")
    @Expose
    private String  requestingOrganisationTransactionReference;


    @SerializedName("originalTransactionReference")
    @Expose
    private String originalTransactionReference;


    @SerializedName("creditParty")
    @Expose
    private ArrayList<CreditPartyItem> creditParty;


    @SerializedName("debitParty")
    @Expose
    private ArrayList<DebitPartyItem> debitParty;

    @SerializedName("type")
    @Expose
    private  String type;

    @SerializedName("subType")
    @Expose
    private  String subType;

    @SerializedName("transactionStatus")
    @Expose
    private String transactionStatus;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("descriptionText")
    @Expose
    private String  descriptionText;

    @SerializedName("fees")
    private ArrayList<Fees> feesList;

    @SerializedName("geoCode")
    @Expose
    private String  geoCode;

    @SerializedName("oneTimeCode")
    @Expose
    private String oneTimeCode;


    @SerializedName("requestingOrganisation")
    @Expose
    private RequestingOrganisation requestingOrganisation;

    @SerializedName("servicingIdentity")
    @Expose
    private String  servicingIdentity;

    @SerializedName("transactionReceipt")
    @Expose
    private String   transactionReceipt;


    @SerializedName("creationDate")
    @Expose
    private String creationDate;


    @SerializedName("modificationDate")
    @Expose
    private String modificationDate;


    @SerializedName("requestDate")
    private  String requestDate;


    @SerializedName("customData")
    @Expose
    private List<CustomDataItem> customData;






    @SerializedName("internationalTransferInformation")
    @Expose
    private InternationalTransferInformation internationalTransferInformation;

    @SerializedName("senderKyc")
    @Expose
    private Kyc senderKyc;


    @SerializedName("recipientKyc")
    @Expose
    private Kyc  recipientKyc;

    @SerializedName("metadata")
    @Expose
    private ArrayList<MetaDataObject> metaDataObject;


    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getRequestingOrganisationTransactionReference() {
        return requestingOrganisationTransactionReference;
    }

    public void setRequestingOrganisationTransactionReference(String requestingOrganisationTransactionReference) {
        this.requestingOrganisationTransactionReference = requestingOrganisationTransactionReference;
    }

    public String getOriginalTransactionReference() {
        return originalTransactionReference;
    }

    public void setOriginalTransactionReference(String originalTransactionReference) {
        this.originalTransactionReference = originalTransactionReference;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public ArrayList<Fees> getFeesList() {
        return feesList;
    }

    public void setFeesList(ArrayList<Fees> feesList) {
        this.feesList = feesList;
    }

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public String getServicingIdentity() {
        return servicingIdentity;
    }

    public void setServicingIdentity(String servicingIdentity) {
        this.servicingIdentity = servicingIdentity;
    }

    public String getTransactionReceipt() {
        return transactionReceipt;
    }

    public void setTransactionReceipt(String transactionReceipt) {
        this.transactionReceipt = transactionReceipt;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }


    public Kyc getRecipientKyc() {
        return recipientKyc;
    }

    public void setRecipientKyc(Kyc recipientKyc) {
        this.recipientKyc = recipientKyc;
    }

    public ArrayList<MetaDataObject> getMetaDataObject() {
        return metaDataObject;
    }

    public void setMetaDataObject(ArrayList<MetaDataObject> metaDataObject) {
        this.metaDataObject = metaDataObject;
    }

    public RequestingOrganisation getRequestingOrganisation() {
        return requestingOrganisation;
    }

    public void setRequestingOrganisation(RequestingOrganisation requestingOrganisation) {
        this.requestingOrganisation = requestingOrganisation;
    }

    public InternationalTransferInformation getInternationalTransferInformation() {
        return internationalTransferInformation;
    }

    public void setInternationalTransferInformation(InternationalTransferInformation internationalTransferInformation) {
        this.internationalTransferInformation = internationalTransferInformation;
    }

    public String getOneTimeCode() {
        return oneTimeCode;
    }

    public void setOneTimeCode(String oneTimeCode) {
        this.oneTimeCode = oneTimeCode;
    }


    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setDebitParty(ArrayList<DebitPartyItem> debitParty) {
        this.debitParty = debitParty;
    }

    public ArrayList<DebitPartyItem> getDebitParty() {
        return debitParty;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCreditParty(ArrayList<CreditPartyItem> creditParty) {
        this.creditParty = creditParty;
    }

    public ArrayList<CreditPartyItem> getCreditParty() {
        return creditParty;
    }


    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }


    public Kyc getSenderKyc() {
        return senderKyc;
    }

    public void setSenderKyc(Kyc senderKyc) {
        this.senderKyc = senderKyc;
    }


    public List<CustomDataItem> getCustomData() {
        return customData;
    }

    public void setCustomData(List<CustomDataItem> customData) {
        this.customData = customData;
    }


}
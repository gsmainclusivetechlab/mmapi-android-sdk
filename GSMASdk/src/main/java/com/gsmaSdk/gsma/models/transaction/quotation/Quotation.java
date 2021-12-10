package com.gsmaSdk.gsma.models.transaction.quotation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.account.AccountIdentifier;
import com.gsmaSdk.gsma.models.authorisationCode.MetaData;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.KYCInformation;
import com.gsmaSdk.gsma.models.common.RequestingOrganisation;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for Account Balance
 * */
public class Quotation extends BaseResponse {

    @SerializedName("quotationReference")
    @Expose
    private String quotationReference;


    @SerializedName("creditParty")
    @Expose
    private ArrayList<AccountIdentifier> creditParty;


    @SerializedName("debitParty")
    @Expose
    private ArrayList<AccountIdentifier> debitParty;


    @SerializedName("type")
    @Expose
    private  String type;

    @SerializedName("subType")
    @Expose
    private  String subType;

    @SerializedName("quotationStatus")
    @Expose
    private String quotationStatus;


    @SerializedName("requestAmount")
    @Expose
    private String requestAmount;

    @SerializedName("requestCurrency")
    @Expose
    private  String requestCurrency;


    public List<Quote> getQuoteList() {
        return quoteList;
    }

    public void setQuoteList(List<Quote> quoteList) {
        this.quoteList = quoteList;
    }

    @SerializedName("availableDeliveryMethod")
    @Expose
    private  String  availableDeliveryMethod;


    @SerializedName("chosenDeliveryMethod")
    @Expose
    private  String chosenDeliveryMethod;


    @SerializedName("originCountry")
    @Expose
    private String originCountry;


    @SerializedName("receivingCountry")
    @Expose
    private String receivingCountry;


    @SerializedName("senderKyc")
    @Expose
    private KYCInformation senderKyc;


    @SerializedName("recipientKyc")
    @Expose
    private KYCInformation recipientKyc;

    @SerializedName("quotes")
    @Expose
    List<Quote> quoteList;

    @SerializedName("recipientBlockingReason")
    private String recipientBlockingReason;

    @SerializedName("senderBlockingReason")
    private String senderBlockingReason;


    @SerializedName("requestingOrganisation")
    @Expose
    private RequestingOrganisation requestingOrganisation;


    @SerializedName("sendingServiceProviderCountry")
    @Expose
    private String sendingServiceProviderCountry;


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


    @SerializedName("metadata")
    @Expose
    private ArrayList<MetaData> metaDataObject;

    public String getQuotationReference() {
        return quotationReference;
    }

    public void setQuotationReference(String quotationReference) {
        this.quotationReference = quotationReference;
    }

    public ArrayList<AccountIdentifier> getCreditParty() {
        return creditParty;
    }

    public void setCreditParty(ArrayList<AccountIdentifier> creditParty) {
        this.creditParty = creditParty;
    }

    public ArrayList<AccountIdentifier> getDebitParty() {
        return debitParty;
    }

    public void setDebitParty(ArrayList<AccountIdentifier> debitParty) {
        this.debitParty = debitParty;
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

    public String getQuotationStatus() {
        return quotationStatus;
    }

    public void setQuotationStatus(String quotationStatus) {
        this.quotationStatus = quotationStatus;
    }

    public String getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(String requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getRequestCurrency() {
        return requestCurrency;
    }

    public void setRequestCurrency(String requestCurrency) {
        this.requestCurrency = requestCurrency;
    }

    public String getAvailableDeliveryMethod() {
        return availableDeliveryMethod;
    }

    public void setAvailableDeliveryMethod(String availableDeliveryMethod) {
        this.availableDeliveryMethod = availableDeliveryMethod;
    }

    public String getChosenDeliveryMethod() {
        return chosenDeliveryMethod;
    }

    public void setChosenDeliveryMethod(String chosenDeliveryMethod) {
        this.chosenDeliveryMethod = chosenDeliveryMethod;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getReceivingCountry() {
        return receivingCountry;
    }

    public void setReceivingCountry(String receivingCountry) {
        this.receivingCountry = receivingCountry;
    }

    public KYCInformation getSenderKyc() {
        return senderKyc;
    }

    public void setSenderKyc(KYCInformation senderKyc) {
        this.senderKyc = senderKyc;
    }

    public KYCInformation getRecipientKyc() {
        return recipientKyc;
    }

    public void setRecipientKyc(KYCInformation recipientKyc) {
        this.recipientKyc = recipientKyc;
    }

    public String getRecipientBlockingReason() {
        return recipientBlockingReason;
    }

    public void setRecipientBlockingReason(String recipientBlockingReason) {
        this.recipientBlockingReason = recipientBlockingReason;
    }

    public String getSenderBlockingReason() {
        return senderBlockingReason;
    }

    public void setSenderBlockingReason(String senderBlockingReason) {
        this.senderBlockingReason = senderBlockingReason;
    }

    public RequestingOrganisation getRequestingOrganisation() {
        return requestingOrganisation;
    }

    public void setRequestingOrganisation(RequestingOrganisation requestingOrganisation) {
        this.requestingOrganisation = requestingOrganisation;
    }

    public String getSendingServiceProviderCountry() {
        return sendingServiceProviderCountry;
    }

    public void setSendingServiceProviderCountry(String sendingServiceProviderCountry) {
        this.sendingServiceProviderCountry = sendingServiceProviderCountry;
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

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public List<CustomDataItem> getCustomData() {
        return customData;
    }

    public void setCustomData(List<CustomDataItem> customData) {
        this.customData = customData;
    }

    public ArrayList<MetaData> getMetaDataObject() {
        return metaDataObject;
    }

    public void setMetaDataObject(ArrayList<MetaData> metaDataObject) {
        this.metaDataObject = metaDataObject;
    }
}

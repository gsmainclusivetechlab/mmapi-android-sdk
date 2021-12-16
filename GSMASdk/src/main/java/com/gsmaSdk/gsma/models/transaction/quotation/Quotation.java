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


    @SuppressWarnings("unused")
    public List<Quote> getQuoteList() {
        return quoteList;
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public String getQuotationReference() {
        return quotationReference;
    }

    @SuppressWarnings("unused")
    public void setQuotationReference(String quotationReference) {
        this.quotationReference = quotationReference;
    }

    @SuppressWarnings("unused")
    public ArrayList<AccountIdentifier> getCreditParty() {
        return creditParty;
    }

    public void setCreditParty(ArrayList<AccountIdentifier> creditParty) {
        this.creditParty = creditParty;
    }

    @SuppressWarnings("unused")
    public ArrayList<AccountIdentifier> getDebitParty() {
        return debitParty;
    }

    public void setDebitParty(ArrayList<AccountIdentifier> debitParty) {
        this.debitParty = debitParty;
    }

    @SuppressWarnings("unused")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SuppressWarnings("unused")
    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    @SuppressWarnings("unused")
    public String getQuotationStatus() {
        return quotationStatus;
    }

    @SuppressWarnings("unused")
    public void setQuotationStatus(String quotationStatus) {
        this.quotationStatus = quotationStatus;
    }

    @SuppressWarnings("unused")
    public String getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(String requestAmount) {
        this.requestAmount = requestAmount;
    }

    @SuppressWarnings("unused")
    public String getRequestCurrency() {
        return requestCurrency;
    }

    public void setRequestCurrency(String requestCurrency) {
        this.requestCurrency = requestCurrency;
    }

    @SuppressWarnings("unused")
    public String getAvailableDeliveryMethod() {
        return availableDeliveryMethod;
    }

    @SuppressWarnings("unused")
    public void setAvailableDeliveryMethod(String availableDeliveryMethod) {
        this.availableDeliveryMethod = availableDeliveryMethod;
    }

    @SuppressWarnings("unused")
    public String getChosenDeliveryMethod() {
        return chosenDeliveryMethod;
    }

    public void setChosenDeliveryMethod(String chosenDeliveryMethod) {
        this.chosenDeliveryMethod = chosenDeliveryMethod;
    }

    @SuppressWarnings("unused")
    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    @SuppressWarnings("unused")
    public String getReceivingCountry() {
        return receivingCountry;
    }

    public void setReceivingCountry(String receivingCountry) {
        this.receivingCountry = receivingCountry;
    }

    @SuppressWarnings("unused")
    public KYCInformation getSenderKyc() {
        return senderKyc;
    }

    public void setSenderKyc(KYCInformation senderKyc) {
        this.senderKyc = senderKyc;
    }

    @SuppressWarnings("unused")
    public KYCInformation getRecipientKyc() {
        return recipientKyc;
    }

    @SuppressWarnings("unused")
    public void setRecipientKyc(KYCInformation recipientKyc) {
        this.recipientKyc = recipientKyc;
    }

    @SuppressWarnings("unused")
    public String getRecipientBlockingReason() {
        return recipientBlockingReason;
    }

    @SuppressWarnings("unused")
    public void setRecipientBlockingReason(String recipientBlockingReason) {
        this.recipientBlockingReason = recipientBlockingReason;
    }

    @SuppressWarnings("unused")
    public String getSenderBlockingReason() {
        return senderBlockingReason;
    }

    @SuppressWarnings("unused")
    public void setSenderBlockingReason(String senderBlockingReason) {
        this.senderBlockingReason = senderBlockingReason;
    }

    @SuppressWarnings("unused")
    public RequestingOrganisation getRequestingOrganisation() {
        return requestingOrganisation;
    }

    @SuppressWarnings("unused")
    public void setRequestingOrganisation(RequestingOrganisation requestingOrganisation) {
        this.requestingOrganisation = requestingOrganisation;
    }

    @SuppressWarnings("unused")
    public String getSendingServiceProviderCountry() {
        return sendingServiceProviderCountry;
    }

    public void setSendingServiceProviderCountry(String sendingServiceProviderCountry) {
        this.sendingServiceProviderCountry = sendingServiceProviderCountry;
    }

    @SuppressWarnings("unused")
    public String getCreationDate() {
        return creationDate;
    }

    @SuppressWarnings("unused")
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @SuppressWarnings("unused")
    public String getModificationDate() {
        return modificationDate;
    }

    @SuppressWarnings("unused")
    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    @SuppressWarnings("unused")
    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    @SuppressWarnings("unused")
    public List<CustomDataItem> getCustomData() {
        return customData;
    }

    public void setCustomData(List<CustomDataItem> customData) {
        this.customData = customData;
    }

    @SuppressWarnings("unused")
    public ArrayList<MetaData> getMetaDataObject() {
        return metaDataObject;
    }

    @SuppressWarnings("unused")
    public void setMetaDataObject(ArrayList<MetaData> metaDataObject) {
        this.metaDataObject = metaDataObject;
    }
}

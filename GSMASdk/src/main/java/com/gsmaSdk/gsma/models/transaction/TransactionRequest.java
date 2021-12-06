package com.gsmaSdk.gsma.models.transaction;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.common.CreditPartyItem;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.DebitPartyItem;
import com.gsmaSdk.gsma.models.common.RequestingOrganisation;
import com.gsmaSdk.gsma.models.common.SenderKyc;
import com.gsmaSdk.gsma.network.responses.BaseResponse;


/**
 * Model class for Transaction Request
 */
@SuppressWarnings("ALL")
public class TransactionRequest extends BaseResponse {


    @SerializedName("quotationReference")
    private String quotationReference;


    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("debitParty")
    @Expose
    private ArrayList<DebitPartyItem> debitParty;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("creditParty")
    @Expose
    private ArrayList<CreditPartyItem> creditParty;


    @SerializedName("requestAmount")
    @Expose
    private String requestAmount;

    @SerializedName("requestCurrency")
    @Expose
    private  String requestCurrency;


    @SerializedName("receivingCountry")
    @Expose
    private String receivingCountry;

    @SerializedName("requestDate")
    private  String requestDate;


    @SerializedName("creationDate")
    @Expose
    private String creationDate;

    @SerializedName("modificationDate")
    @Expose
    private String modificationDate;

    @SerializedName("type")
    @Expose
    private  String type;

    @SerializedName("subType")
    @Expose
    private  String subType;

    @SerializedName("chosenDeliveryMethod")
    @Expose
    private  String chosenDeliveryMethod;

    @SerializedName("senderKyc")
    @Expose
    private SenderKyc senderKyc;

    @SerializedName("originCountry")
    @Expose
    private String originCountry;

    @SerializedName("transactionReference")
    @Expose
    private String transactionReference;

    @SerializedName("transactionStatus")
    @Expose
    private String transactionStatus;

    @SerializedName("customData")
    @Expose
    private List<CustomDataItem> customData;

    @SerializedName("sendingServiceProviderCountry")
    @Expose
    private String sendingServiceProviderCountry;

    @SerializedName("internationalTransferInformation")
    @Expose
    private InternationalTransferInformation internationalTransferInformation;


    @SerializedName("oneTimeCode")
    @Expose
    private String oneTimeCode;

    @SerializedName("requestingOrganisation")
    @Expose
    private RequestingOrganisation requestingOrganisation;

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

    public String getReceivingCountry() {
        return receivingCountry;
    }

    public void setReceivingCountry(String receivingCountry) {
        this.receivingCountry = receivingCountry;
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

    public String getChosenDeliveryMethod() {
        return chosenDeliveryMethod;
    }

    public void setChosenDeliveryMethod(String chosenDeliveryMethod) {
        this.chosenDeliveryMethod = chosenDeliveryMethod;
    }

    public SenderKyc getSenderKyc() {
        return senderKyc;
    }

    public void setSenderKyc(SenderKyc senderKyc) {
        this.senderKyc = senderKyc;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public List<CustomDataItem> getCustomData() {
        return customData;
    }

    public void setCustomData(List<CustomDataItem> customData) {
        this.customData = customData;
    }

    public String getSendingServiceProviderCountry() {
        return sendingServiceProviderCountry;
    }

    public void setSendingServiceProviderCountry(String sendingServiceProviderCountry) {
        this.sendingServiceProviderCountry = sendingServiceProviderCountry;
    }

}
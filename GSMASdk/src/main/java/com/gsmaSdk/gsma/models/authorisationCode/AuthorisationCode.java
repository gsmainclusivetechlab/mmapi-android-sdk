package com.gsmaSdk.gsma.models.authorisationCode;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

/**
 * Model class for Authorisation Code Items
 */
@SuppressWarnings("ALL")
public class AuthorisationCode extends BaseResponse {


    @Expose
    @SerializedName("authorisationCode")
    private String authorisationCode;

    @Expose
    @SerializedName("codeState")
    private String codeState;


    @Expose
    @SerializedName("amount")
    private String amount;

    @Expose
    @SerializedName("currency")
    private String currency;

    @Expose
    @SerializedName("amountType")
    private String amountType;

    @Expose
    @SerializedName("codeLifetime")
    private int codeLifetime;

    @Expose
    @SerializedName("holdFundsIndicator")
    private boolean holdFundsIndicator;

    @Expose
    @SerializedName("redemptionAccountIdentifiers")
    private List<AccountIdentifiers> redemptionAccountIdentifiers;

    @Expose
    @SerializedName("redemptionChannels")
    private ArrayList<ChannelType> redemptionChannels;

    @Expose
    @SerializedName("redemptionTransactionTypes")
    private ArrayList<TransactionType> redemptionTransactionTypes;


    @SerializedName("requestingOrganisationTransactionReference")
    @Expose
    private String requestingOrganisationTransactionReference;

    @Expose
    @SerializedName("creationDate")
    private String creationDate;


    @Expose
    @SerializedName("modificationDate")
    private String modificationDate;

    @Expose
    @SerializedName("requestDate")
    private String requestDate;

    @SerializedName("customData")
    @Expose
    private List<CustomDataItem> customData;


    public AuthorisationCode() {
        this.codeLifetime = 1;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public int getCodeLifetime() {
        return codeLifetime;
    }

    public void setCodeLifetime(int codeLifetime) {
        this.codeLifetime = codeLifetime;
    }

    public boolean isHoldFundsIndicator() {
        return holdFundsIndicator;
    }

    public void setHoldFundsIndicator(boolean holdFundsIndicator) {
        this.holdFundsIndicator = holdFundsIndicator;
    }

    public ArrayList<ChannelType> getRedemptionChannels() {
        return redemptionChannels;
    }

    public void setRedemptionChannels(ArrayList<ChannelType> redemptionChannels) {
        this.redemptionChannels = redemptionChannels;
    }

    public ArrayList<TransactionType> getRedemptionTransactionTypes() {
        return redemptionTransactionTypes;
    }

    public void setRedemptionTransactionTypes(ArrayList<TransactionType> redemptionTransactionTypes) {
        this.redemptionTransactionTypes = redemptionTransactionTypes;
    }

    public String getRequestingOrganisationTransactionReference() {
        return requestingOrganisationTransactionReference;
    }

    public void setRequestingOrganisationTransactionReference(String requestingOrganisationTransactionReference) {
        this.requestingOrganisationTransactionReference = requestingOrganisationTransactionReference;
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

    @SerializedName("metadata")
    @Expose
    private ArrayList<MetaData> metaDataObject;

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setRedemptionAccountIdentifiers(List<AccountIdentifiers> redemptionAccountIdentifiers) {
        this.redemptionAccountIdentifiers = redemptionAccountIdentifiers;
    }

    public List<AccountIdentifiers> getRedemptionAccountIdentifiers() {
        return redemptionAccountIdentifiers;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setAuthorisationCode(String authorisationCode) {
        this.authorisationCode = authorisationCode;
    }

    public String getAuthorisationCode() {
        return authorisationCode;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCodeState(String codeState) {
        this.codeState = codeState;
    }

    public String getCodeState() {
        return codeState;
    }
}
package com.gsmaSdk.gsma.models.debitmandate;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.account.PayeeItem;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings("ALL")
public class DebitMandate extends BaseResponse {

    @SerializedName("mandateReference")
    @Expose
    private String mandateReference;

    @SerializedName("payee")
    @Expose
    private List<PayeeItem> payee;

    @SerializedName("mandateStatus")
    @Expose
    private String mandateStatus;


    @SerializedName("startDate")
    @Expose
    private String startDate;

    @SerializedName("amountLimit")
    @Expose
    private String amountLimit;


    @SerializedName("currency")
    @Expose
    private String currency;


    @SerializedName("endDate")
    @Expose
    private String endDate;


    @SerializedName("frequencyType")
    @Expose
    private String frequencyType;


    @SerializedName("numberOfPayments")
    @Expose
    private String numberOfPayments;


    @SerializedName("creationDate")
    @Expose
    private String creationDate;


    @SerializedName("customData")
    @Expose
    private ArrayList<CustomDataItem> customData;


    @SerializedName("requestDate")
    @Expose
    private String requestDate;


    @SerializedName("modificationDate")
    @Expose
    private String modificationDate;

    public void setPayee(List<PayeeItem> payee) {
        this.payee = payee;
    }

    public String getMandateStatus() {
        return mandateStatus;
    }

    public void setMandateStatus(String mandateStatus) {
        this.mandateStatus = mandateStatus;
    }

    public String getMandateReference() {
        return mandateReference;
    }

    public void setMandateReference(String mandateReference) {
        this.mandateReference = mandateReference;
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

    public void setPayee(ArrayList<PayeeItem> payee) {
        this.payee = payee;
    }

    public void setAmountLimit(String amountLimit) {
        this.amountLimit = amountLimit;
    }

    public void setNumberOfPayments(String numberOfPayments) {
        this.numberOfPayments = numberOfPayments;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setFrequencyType(String frequencyType) {
        this.frequencyType = frequencyType;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCustomData(ArrayList<CustomDataItem> customData) {
        this.customData = customData;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<PayeeItem> getPayee() {
        return payee;
    }

    public String getAmountLimit() {
        return amountLimit;
    }

    public String getNumberOfPayments() {
        return numberOfPayments;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getFrequencyType() {
        return frequencyType;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public String getCurrency() {
        return currency;
    }

    public ArrayList<CustomDataItem> getCustomData() {
        return customData;
    }

    public String getStartDate() {
        return startDate;
    }
}
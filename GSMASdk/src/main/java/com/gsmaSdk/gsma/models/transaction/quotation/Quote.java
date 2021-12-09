package com.gsmaSdk.gsma.models.transaction.quotation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.common.Fees;

import java.util.List;

public class Quote {

    @SerializedName("quoteId")
    @Expose
    private String quoteId;

    @SerializedName("receivingAmount")
    @Expose
    private String receivingAmount;

    @SerializedName("receivingCurrency")
    @Expose
    private String receivingCurrency;


    @SerializedName("sendingAmount")
    @Expose
    private String sendingAmount;

    @SerializedName("sendingCurrency")
    @Expose
    private String sendingCurrency;


    @SerializedName("deliveryMethod")
    @Expose
    private String deliveryMethod;

    @SerializedName("fees")
    @Expose
    private List<Fees> fees;

    @SerializedName("fxRate")
    @Expose
    private String fxRate;


    @SerializedName("quoteExpiryTime")
    @Expose
    private String quoteExpiryTime;

    @SerializedName("receivingServiceProvider")
    @Expose
    private String receivingServiceProvider;



    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getReceivingAmount() {
        return receivingAmount;
    }

    public void setReceivingAmount(String receivingAmount) {
        this.receivingAmount = receivingAmount;
    }

    public String getReceivingCurrency() {
        return receivingCurrency;
    }

    public void setReceivingCurrency(String receivingCurrency) {
        this.receivingCurrency = receivingCurrency;
    }

    public String getSendingAmount() {
        return sendingAmount;
    }

    public void setSendingAmount(String sendingAmount) {
        this.sendingAmount = sendingAmount;
    }

    public String getSendingCurrency() {
        return sendingCurrency;
    }

    public void setSendingCurrency(String sendingCurrency) {
        this.sendingCurrency = sendingCurrency;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public List<Fees> getFees() {
        return fees;
    }

    public void setFees(List<Fees> fees) {
        this.fees = fees;
    }

    public String getFxRate() {
        return fxRate;
    }

    public void setFxRate(String fxRate) {
        this.fxRate = fxRate;
    }

    public String getQuoteExpiryTime() {
        return quoteExpiryTime;
    }

    public void setQuoteExpiryTime(String quoteExpiryTime) {
        this.quoteExpiryTime = quoteExpiryTime;
    }

    public String getReceivingServiceProvider() {
        return receivingServiceProvider;
    }

    public void setReceivingServiceProvider(String receivingServiceProvider) {
        this.receivingServiceProvider = receivingServiceProvider;
    }
}

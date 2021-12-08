package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fees {

    @SerializedName("feeType")
    @Expose
    private String feeType;

    @SerializedName("feeAmount")
    @Expose
    private String feeAmount;

    @SerializedName("feeCurrency")
    @Expose
    private String feeCurrency;

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeCurrency() {
        return feeCurrency;
    }

    public void setFeeCurrency(String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }
}

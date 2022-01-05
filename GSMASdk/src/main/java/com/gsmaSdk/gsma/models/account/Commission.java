package com.gsmaSdk.gsma.models.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings("ALL")
public class Commission extends BaseResponse {


    @SerializedName("commissionType")
    @Expose
    private String commissionType;

    @SerializedName("commissionAmount")
    @Expose
    private String commissionAmount;

    @SerializedName("commissionCurrency")
    @Expose
    private String commissionCurrency;

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public String getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(String commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getCommissionCurrency() {
        return commissionCurrency;
    }

    public void setCommissionCurrency(String commissionCurrency) {
        this.commissionCurrency = commissionCurrency;
    }
}

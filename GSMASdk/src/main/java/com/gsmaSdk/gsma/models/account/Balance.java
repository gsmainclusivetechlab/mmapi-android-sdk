package com.gsmaSdk.gsma.models.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

/**
 * Model class for Account Balance
 * */
@SuppressWarnings("ALL")
public class Balance extends BaseResponse {

    @SerializedName("currentBalance")
    @Expose
    private String currentBalance;

    @SerializedName("availableBalance")
    @Expose
    private String availableBalance;

    @SerializedName("reservedBalance")
    @Expose
    private String reservedBalance;

    @SerializedName("unclearedBalance")
    @Expose
    private String unclearedBalance;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("accountStatus")
    @Expose
    private String accountStatus;

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getReservedBalance() {
        return reservedBalance;
    }

    public void setReservedBalance(String reservedBalance) {
        this.reservedBalance = reservedBalance;
    }

    public String getUnclearedBalance() {
        return unclearedBalance;
    }

    public void setUnclearedBalance(String unclearedBalance) {
        this.unclearedBalance = unclearedBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}

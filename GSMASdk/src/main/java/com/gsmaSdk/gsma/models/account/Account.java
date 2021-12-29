package com.gsmaSdk.gsma.models.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.Fees;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class Account  extends BaseResponse {

    @SerializedName("accountIdentifiers")
    @Expose
    private  ArrayList<AccountIdentifier> accountIdentifiers;


    @SerializedName("identity")
    @Expose
    private ArrayList<Identity> identity;

    @SerializedName("accountType")
    @Expose
    private String accountType;

    @SerializedName("accountStatus")
    @Expose
    private String accountStatus;


    @SerializedName("accountSubStatus")
    @Expose
    private String accountSubStatus;


    @SerializedName("currentBalance")
    @Expose
    private String currentBalance;

    @SerializedName("availableBalance")
    @Expose
    private String availableBalance;

    @SerializedName("reservedBalance")
    @Expose
    private String reservedBalance;


    @SerializedName("unClearedBalance")
    @Expose
    private String unClearedBalance;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("customData")
    @Expose
    private ArrayList<CustomDataItem> customData;


    @SerializedName("fees")
    private ArrayList<Fees> fees;


    @SerializedName("commissionEarned")
    private ArrayList<Commission> commissionEarned;

    @SerializedName("registeringEntity")
    @Expose
    private String registeringEntity;

    @SerializedName("creationDate")
    @Expose
    private String creationDate;

    @SerializedName("modificationDate")
    @Expose
    private String modificationDate;

    @SerializedName("requestDate")
    @Expose
    private String requestDate;

    public ArrayList<AccountIdentifier> getAccountIdentifiers() {
        return accountIdentifiers;
    }

    public void setAccountIdentifiers(ArrayList<AccountIdentifier> accountIdentifiers) {
        this.accountIdentifiers = accountIdentifiers;
    }

    public ArrayList<Identity> getIdentity() {
        return identity;
    }

    public void setIdentity(ArrayList<Identity> identity) {
        this.identity = identity;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountSubStatus() {
        return accountSubStatus;
    }

    public void setAccountSubStatus(String accountSubStatus) {
        this.accountSubStatus = accountSubStatus;
    }

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

    public String getUnClearedBalance() {
        return unClearedBalance;
    }

    public void setUnClearedBalance(String unClearedBalance) {
        this.unClearedBalance = unClearedBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ArrayList<CustomDataItem> getCustomData() {
        return customData;
    }

    public void setCustomData(ArrayList<CustomDataItem> customData) {
        this.customData = customData;
    }

    public ArrayList<Fees> getFees() {
        return fees;
    }

    public void setFees(ArrayList<Fees> fees) {
        this.fees = fees;
    }

    public ArrayList<Commission> getCommissionEarned() {
        return commissionEarned;
    }

    public void setCommissionEarned(ArrayList<Commission> commissionEarned) {
        this.commissionEarned = commissionEarned;
    }

    public String getRegisteringEntity() {
        return registeringEntity;
    }

    public void setRegisteringEntity(String registeringEntity) {
        this.registeringEntity = registeringEntity;
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
}

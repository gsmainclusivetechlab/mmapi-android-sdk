package com.gsmaSdk.gsma.models.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.KYCInformation;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class Identity extends BaseResponse {


    @SerializedName("identityId")
    @Expose
    private String identityId;

    @SerializedName("identityType")
    @Expose
    private String identityType;

    @SerializedName("identityStatus")
    @Expose
    private String identityStatus;

    @SerializedName("identityKyc")
    @Expose
    private KYCInformation identityKyc;

    @SerializedName("accountRelationship")
    @Expose
    private String accountRelationship;

    @SerializedName("kycVerificationStatus")
    @Expose
    private String kycVerificationStatus;

    @SerializedName("kycVerificationEntity")
    @Expose
    private String kycVerificationEntity;

    @SerializedName("kycLevel")
    @Expose
    private int kycLevel;

    @SerializedName("customData")
    @Expose
    private ArrayList<CustomDataItem> customData;

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(String identityStatus) {
        this.identityStatus = identityStatus;
    }

    public KYCInformation getIdentityKyc() {
        return identityKyc;
    }

    public void setIdentityKyc(KYCInformation identityKyc) {
        this.identityKyc = identityKyc;
    }

    public String getAccountRelationship() {
        return accountRelationship;
    }

    public void setAccountRelationship(String accountRelationship) {
        this.accountRelationship = accountRelationship;
    }

    public String getKycVerificationStatus() {
        return kycVerificationStatus;
    }

    public void setKycVerificationStatus(String kycVerificationStatus) {
        this.kycVerificationStatus = kycVerificationStatus;
    }

    public String getKycVerificationEntity() {
        return kycVerificationEntity;
    }

    public void setKycVerificationEntity(String kycVerificationEntity) {
        this.kycVerificationEntity = kycVerificationEntity;
    }

    public int getKycLevel() {
        return kycLevel;
    }

    public void setKycLevel(int kycLevel) {
        this.kycLevel = kycLevel;
    }

    public ArrayList<CustomDataItem> getCustomData() {
        return customData;
    }

    public void setCustomData(ArrayList<CustomDataItem> customData) {
        this.customData = customData;
    }
}

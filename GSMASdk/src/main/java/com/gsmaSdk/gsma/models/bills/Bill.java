package com.gsmaSdk.gsma.models.bills;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.authorisationCode.MetaData;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class Bill extends BaseResponse {

    @Expose
    @SerializedName("billReference")
    private String billReference;

    @Expose
    @SerializedName("billStatus")
    private String billStatus;

    @Expose
    @SerializedName("amountDue")
    private String amountDue;

    @Expose
    @SerializedName("billDescription")
    private String billDescription;

    @Expose
    @SerializedName("currency")
    private String currency;

    @Expose
    @SerializedName("dueDate")
    private String dueDate;

    @Expose
    @SerializedName("minimumAmountDue")
    private String minimumAmountDue;

    @Expose
    @SerializedName("creationDate")
    private String creationDate;

    @Expose
    @SerializedName("modificationDate")
    private String modificationDate;

    @SerializedName("customData")
    @Expose
    private ArrayList<CustomDataItem> customData;

    @SerializedName("metadata")
    @Expose
    private ArrayList<MetaData> metaDataObject;

    public String getBillReference() {
        return billReference;
    }

    public void setBillReference(String billReference) {
        this.billReference = billReference;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(String amountDue) {
        this.amountDue = amountDue;
    }

    public String getBillDescription() {
        return billDescription;
    }

    public void setBillDescription(String billDescription) {
        this.billDescription = billDescription;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getMinimumAmountDue() {
        return minimumAmountDue;
    }

    public void setMinimumAmountDue(String minimumAmountDue) {
        this.minimumAmountDue = minimumAmountDue;
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

    public List<CustomDataItem> getCustomData() {
        return customData;
    }

    public void setCustomData(ArrayList<CustomDataItem> customData) {
        this.customData = customData;
    }

    public ArrayList<MetaData> getMetaDataObject() {
        return metaDataObject;
    }

    public void setMetaDataObject(ArrayList<MetaData> metaDataObject) {
        this.metaDataObject = metaDataObject;
    }
}

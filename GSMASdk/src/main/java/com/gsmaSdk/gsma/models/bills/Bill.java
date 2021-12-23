package com.gsmaSdk.gsma.models.bills;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.authorisationCode.MetaData;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"ALL", "unused"})
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

    @SuppressWarnings("unused")
    public String getBillReference() {
        return billReference;
    }

    @SuppressWarnings("unused")
    public void setBillReference(String billReference) {
        this.billReference = billReference;
    }

    @SuppressWarnings("unused")
    public String getBillStatus() {
        return billStatus;
    }

    @SuppressWarnings("unused")
    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    @SuppressWarnings("unused")
    public String getAmountDue() {
        return amountDue;
    }

    @SuppressWarnings("unused")
    public void setAmountDue(String amountDue) {
        this.amountDue = amountDue;
    }

    @SuppressWarnings("unused")
    public String getBillDescription() {
        return billDescription;
    }

    @SuppressWarnings("unused")
    public void setBillDescription(String billDescription) {
        this.billDescription = billDescription;
    }

    @SuppressWarnings("unused")
    public String getCurrency() {
        return currency;
    }

    @SuppressWarnings("unused")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @SuppressWarnings("unused")
    public String getDueDate() {
        return dueDate;
    }

    @SuppressWarnings("unused")
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @SuppressWarnings("unused")
    public String getMinimumAmountDue() {
        return minimumAmountDue;
    }

    @SuppressWarnings("unused")
    public void setMinimumAmountDue(String minimumAmountDue) {
        this.minimumAmountDue = minimumAmountDue;
    }

    @SuppressWarnings("unused")
    public String getCreationDate() {
        return creationDate;
    }

    @SuppressWarnings("unused")
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @SuppressWarnings("unused")
    public String getModificationDate() {
        return modificationDate;
    }

    @SuppressWarnings("unused")
    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    @SuppressWarnings("unused")
    public List<CustomDataItem> getCustomData() {
        return customData;
    }

    @SuppressWarnings("unused")
    public void setCustomData(ArrayList<CustomDataItem> customData) {
        this.customData = customData;
    }

    @SuppressWarnings("unused")
    public ArrayList<MetaData> getMetaDataObject() {
        return metaDataObject;
    }

    @SuppressWarnings("unused")
    public void setMetaDataObject(ArrayList<MetaData> metaDataObject) {
        this.metaDataObject = metaDataObject;
    }
}

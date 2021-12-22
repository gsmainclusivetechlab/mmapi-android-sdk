package com.gsmaSdk.gsma.models.bills;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.authorisationCode.MetaData;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

import java.util.ArrayList;

public class BillPay extends BaseResponse {

    @Expose
    @SerializedName("serviceProviderPaymentReference")
    private String serviceProviderPaymentReference;


    @Expose
    @SerializedName("requestingOrganisationTransactionReference")
    private String requestingOrganisationTransactionReference;


    @Expose
    @SerializedName("paymentType")
    private String paymentType;


    @Expose
    @SerializedName("billPaymentStatus")
    private String billPaymentStatus;


    @Expose
    @SerializedName("amountPaid")
    private String amountPaid;


    @Expose
    @SerializedName("currency")
    private String currency;


    @Expose
    @SerializedName("customerReference")
    private String customerReference;

    @Expose
    @SerializedName("requestingOrganisation")
    private String  requestingOrganisation;


    @Expose
    @SerializedName("supplementaryBillReferenceDetails")
    private ArrayList<BillReference> supplementaryBillReferenceList;

    @Expose
    @SerializedName("serviceProviderNotification")
    private String  serviceProviderNotification;


    @Expose
    @SerializedName("creationDate")
    private String  creationDate;

    @Expose
    @SerializedName("modificationDate")
    private String  modificationDate;

    @Expose
    @SerializedName("requestDate")
    private String  requestDate;

    @SerializedName("customData")
    @Expose
    private ArrayList<CustomDataItem> customData;


    @SerializedName("metadata")
    @Expose
    private ArrayList<MetaData> metaDataObject;


    public String getServiceProviderPaymentReference() {
        return serviceProviderPaymentReference;
    }

    public void setServiceProviderPaymentReference(String serviceProviderPaymentReference) {
        this.serviceProviderPaymentReference = serviceProviderPaymentReference;
    }

    public String getRequestingOrganisationTransactionReference() {
        return requestingOrganisationTransactionReference;
    }

    public void setRequestingOrganisationTransactionReference(String requestingOrganisationTransactionReference) {
        this.requestingOrganisationTransactionReference = requestingOrganisationTransactionReference;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getBillPaymentStatus() {
        return billPaymentStatus;
    }

    public void setBillPaymentStatus(String billPaymentStatus) {
        this.billPaymentStatus = billPaymentStatus;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(String customerReference) {
        this.customerReference = customerReference;
    }

    public String getRequestingOrganisation() {
        return requestingOrganisation;
    }

    public void setRequestingOrganisation(String requestingOrganisation) {
        this.requestingOrganisation = requestingOrganisation;
    }

    public ArrayList<BillReference> getSupplementaryBillReferenceList() {
        return supplementaryBillReferenceList;
    }

    public void setSupplementaryBillReferenceList(ArrayList<BillReference> supplementaryBillReferenceList) {
        this.supplementaryBillReferenceList = supplementaryBillReferenceList;
    }

    public String getServiceProviderNotification() {
        return serviceProviderNotification;
    }

    public void setServiceProviderNotification(String serviceProviderNotification) {
        this.serviceProviderNotification = serviceProviderNotification;
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

    public ArrayList<CustomDataItem> getCustomData() {
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


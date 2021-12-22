package com.gsmaSdk.gsma.models.bills;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillReference {

    @Expose
    @SerializedName("paymentReferenceType")
    private String paymentReferenceType;

    @Expose
    @SerializedName("paymentReferenceValue")
    private String paymentReferenceValue;

    public String getPaymentReferenceType() {
        return paymentReferenceType;
    }

    public void setPaymentReferenceType(String paymentReferenceType) {
        this.paymentReferenceType = paymentReferenceType;
    }

    public String getPaymentReferenceValue() {
        return paymentReferenceValue;
    }

    public void setPaymentReferenceValue(String paymentReferenceValue) {
        this.paymentReferenceValue = paymentReferenceValue;
    }
}

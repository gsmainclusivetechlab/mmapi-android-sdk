package com.gsmaSdk.gsma.models.authorisationCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

/***
 * Class RedemptionTransactionType
 */
@SuppressWarnings("unused")
public class TransactionType extends BaseResponse {

    @SuppressWarnings("unused")
    @Expose
    @SerializedName("transactionType")
    private String transactionType;

    @SuppressWarnings("unused")
    @Expose
    @SerializedName("transactionSubType")
    private String transactionSubtype;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionSubtype() {
        return transactionSubtype;
    }

    public void setTransactionSubtype(String transactionSubtype) {
        this.transactionSubtype = transactionSubtype;
    }
}
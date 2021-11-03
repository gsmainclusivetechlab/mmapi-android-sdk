package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.SerializedName;

public class ReversalObject {

    @SerializedName("type")
    private String reversal;


    @SuppressWarnings("unused")
    public String getReversal() {
        return reversal;
    }

    public void setReversal(String reversal) {
        this.reversal = reversal;
    }
}

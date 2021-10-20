package com.gsmaSdk.gsma.models;

import com.google.gson.annotations.SerializedName;

public class ReversalObject {

    @SerializedName("type")
    private String reversal;


    public String getReversal() {
        return reversal;
    }

    public void setReversal(String reversal) {
        this.reversal = reversal;
    }
}

package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class PatchData {

    @SerializedName("op")
    @Expose
    private String op;

    @SerializedName("path")
    @Expose
    private String path;

    @SerializedName("value")
    @Expose
    private String value;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

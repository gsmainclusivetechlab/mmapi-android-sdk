package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.SerializedName;

public class ErrorParameter {
    @SerializedName("key")
    private String key;

    @SerializedName("value")
    private String value;

    @SuppressWarnings("unused")
    public ErrorParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @SuppressWarnings("unused")
    public String getKey() {
        return key;
    }

    @SuppressWarnings("unused")
    public void setKey(String key) {
        this.key = key;
    }

    @SuppressWarnings("unused")
    public String getValue() {
        return value;
    }

    @SuppressWarnings("unused")
    public void setValue(String value) {
        this.value = value;
    }
}

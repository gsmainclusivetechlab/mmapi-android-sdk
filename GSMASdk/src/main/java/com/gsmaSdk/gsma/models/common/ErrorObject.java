package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for Error Object
 */
public class ErrorObject implements Serializable{

    @SerializedName("errorCode")
    private String errorCode;
    @SerializedName("errorDescription")
    private String errorDescription;
    @SerializedName("errorCategory")
    private String errorCategory;
    @SerializedName("errorDateTime")
    private String errorDateTime;
//    @SerializedName("errorParameters")
//    private JSONArray errorParameters;

    public ErrorObject(String errorCategory,String errorCode, String errorDescription) {
        this.errorCategory = errorCategory;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorCategory() {
        return errorCategory;
    }

    public void setErrorCategory(String errorCategory) {
        this.errorCategory = errorCategory;
    }

    public String getErrorDateTime() {
        return errorDateTime;
    }

    public void setErrorDateTime(String errorDateTime) {
        this.errorDateTime = errorDateTime;
    }

//    public JSONArray getErrorParameters() {
//        return errorParameters;
//    }
//
//    public void setErrorParameters(JSONArray errorParameters) {
//        this.errorParameters = errorParameters;
//    }
}
package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Model class for Error Object
 */
@SuppressWarnings("unused")
public class ErrorObject implements Serializable{

    @SerializedName("errorCode")
    private String errorCode;
    @SerializedName("errorDescription")
    private String errorDescription;
    @SerializedName("errorCategory")
    private String errorCategory;
    @SerializedName("errorDateTime")
    private String errorDateTime;

    @SerializedName("errorParameters")
    private List<ErrorParameter> errorParameterList;


    public ErrorObject(String errorCategory,String errorCode, String errorDescription) {
        this.errorCategory = errorCategory;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public List<ErrorParameter> getErrorParameterList() {
        return errorParameterList;
    }

    public void setErrorParameterList(List<ErrorParameter> errorParameterList) {
        this.errorParameterList = errorParameterList;
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


}
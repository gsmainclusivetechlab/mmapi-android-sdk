package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Model class for Error Object
 */
@SuppressWarnings("unused")
public class ErrorObject implements Serializable {

    @SerializedName("errorCode")
    private String errorCode;
    @SerializedName("errordescription")
    private String errorDescription;
    @SerializedName("errorCategory")
    private String errorCategory;
    @SerializedName("errorDateTime")
    private String errorDateTime;
    @SerializedName("errorParameters")
    private ArrayList<ErrorParameter> errorParameterList;
    @SerializedName("message")
    private String message;

    public ArrayList<ErrorParameter> getErrorParameterList() {
        return errorParameterList;
    }

    public void setErrorParameterList(ArrayList<ErrorParameter> errorParameterList) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
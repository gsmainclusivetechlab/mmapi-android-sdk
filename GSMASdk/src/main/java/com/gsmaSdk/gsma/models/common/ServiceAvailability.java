package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

/**
 * Model class for Service Availability
 * */
public class ServiceAvailability implements BaseResponse {
    @SerializedName("serviceStatus")
    @Expose
    private String serviceStatus;

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}

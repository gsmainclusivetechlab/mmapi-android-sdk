package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

/**
 * Model class for Service Availability
 * */
@SuppressWarnings("unused")
public class ServiceAvailability extends BaseResponse {


    @SerializedName("serviceStatus")
    @Expose
    private String serviceStatus;

    @SerializedName("delay")
    @Expose
    private int delay;


    @SerializedName("plannedRestorationTime")
    @Expose
    private String plannedRestorationTime;


    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getPlannedRestorationTime() {
        return plannedRestorationTime;
    }

    public void setPlannedRestorationTime(String plannedRestorationTime) {
        this.plannedRestorationTime = plannedRestorationTime;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}

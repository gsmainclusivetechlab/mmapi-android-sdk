package com.gsmaSdk.gsma.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

/**
 * Model class for Request State Object
 * */
public class RequestStateObject implements BaseResponse {
    @SerializedName("serverCorrelationId")
    @Expose
    private String serverCorrelationId;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("notificationMethod")
    @Expose
    private String notificationMethod;

    @SerializedName("objectReference")
    @Expose
    private String objectReference;

    @SerializedName("pollLimit")
    @Expose
    private int pollLimit;

    public String getServerCorrelationId() {
        return serverCorrelationId;
    }

    public void setServerCorrelationId(String serverCorrelationId) {
        this.serverCorrelationId = serverCorrelationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotificationMethod() {
        return notificationMethod;
    }

    public void setNotificationMethod(String notificationMethod) {
        this.notificationMethod = notificationMethod;
    }

    public String getObjectReference() {
        return objectReference;
    }

    public void setObjectReference(String objectReference) {
        this.objectReference = objectReference;
    }

    public int getPollLimit() {
        return pollLimit;
    }

    public void setPollLimit(int pollLimit) {
        this.pollLimit = pollLimit;
    }
}

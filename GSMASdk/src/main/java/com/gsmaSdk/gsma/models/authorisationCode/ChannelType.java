package com.gsmaSdk.gsma.models.authorisationCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

public class ChannelType extends BaseResponse {

    @Expose
    @SerializedName("channelType")
    private String channelType;


    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
}
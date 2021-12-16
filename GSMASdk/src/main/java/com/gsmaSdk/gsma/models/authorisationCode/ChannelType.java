package com.gsmaSdk.gsma.models.authorisationCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings("unused")
public class ChannelType extends BaseResponse {

    @Expose
    @SerializedName("channelType")
    private String channelType;


    @SuppressWarnings("unused")
    public String getChannelType() {
        return channelType;
    }

    @SuppressWarnings("unused")
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
}
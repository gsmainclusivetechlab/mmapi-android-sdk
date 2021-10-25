package com.gsmaSdk.gsma.models;

import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings("ALL")
public class Reversal implements BaseResponse {

	@SerializedName("pollLimit")
	private int pollLimit;

	@SerializedName("objectReference")
	private String objectReference;

	@SerializedName("notificationMethod")
	private String notificationMethod;

	@SerializedName("serverCorrelationId")
	private String serverCorrelationId;

	@SerializedName("status")
	private String status;

	public int getPollLimit(){
		return pollLimit;
	}

	public String getObjectReference(){
		return objectReference;
	}

	public String getNotificationMethod(){
		return notificationMethod;
	}

	public String getServerCorrelationId(){
		return serverCorrelationId;
	}

	public String getStatus(){
		return status;
	}
}
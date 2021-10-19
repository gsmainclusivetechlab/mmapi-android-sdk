package com.gsmaSdk.gsma.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

public class Refund implements BaseResponse {


	@SerializedName("pollLimit")
	@Expose
	private int pollLimit;

	@SerializedName("objectReference")
	@Expose
	private String objectReference;

	@SerializedName("notificationMethod")
	@Expose
	private String notificationMethod;

	@SerializedName("serverCorrelationId")
	@Expose
	private String serverCorrelationId;

	@SerializedName("status")
	@Expose
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
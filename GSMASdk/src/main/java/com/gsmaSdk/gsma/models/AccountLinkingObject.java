package com.gsmaSdk.gsma.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AccountLinkingObject{

	@SerializedName("mode")
	private String mode;

	@SerializedName("requestingOrganisation")
	private RequestingOrganisation requestingOrganisation;

	@SerializedName("customData")
	private List<CustomDataItem> customData;

	@SerializedName("sourceAccountIdentifiers")
	private List<SourceAccountIdentifiersItem> sourceAccountIdentifiers;

	@SerializedName("status")
	private String status;

	public void setMode(String mode){
		this.mode = mode;
	}

	public String getMode(){
		return mode;
	}

	public void setRequestingOrganisation(RequestingOrganisation requestingOrganisation){
		this.requestingOrganisation = requestingOrganisation;
	}

	public RequestingOrganisation getRequestingOrganisation(){
		return requestingOrganisation;
	}

	public void setCustomData(List<CustomDataItem> customData){
		this.customData = customData;
	}

	public List<CustomDataItem> getCustomData(){
		return customData;
	}

	public void setSourceAccountIdentifiers(List<SourceAccountIdentifiersItem> sourceAccountIdentifiers){
		this.sourceAccountIdentifiers = sourceAccountIdentifiers;
	}

	public List<SourceAccountIdentifiersItem> getSourceAccountIdentifiers(){
		return sourceAccountIdentifiers;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}
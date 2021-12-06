package com.gsmaSdk.gsma.models.account;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.RequestingOrganisation;

@SuppressWarnings("ALL")
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

	@SuppressWarnings("unused")
	public String getMode(){
		return mode;
	}

	public void setRequestingOrganisation(RequestingOrganisation requestingOrganisation){
		this.requestingOrganisation = requestingOrganisation;
	}

	@SuppressWarnings("unused")
	public RequestingOrganisation getRequestingOrganisation(){
		return requestingOrganisation;
	}

	public void setCustomData(List<CustomDataItem> customData){
		this.customData = customData;
	}

	@SuppressWarnings("unused")
	public List<CustomDataItem> getCustomData(){
		return customData;
	}

	public void setSourceAccountIdentifiers(List<SourceAccountIdentifiersItem> sourceAccountIdentifiers){
		this.sourceAccountIdentifiers = sourceAccountIdentifiers;
	}

	@SuppressWarnings("unused")
	public List<SourceAccountIdentifiersItem> getSourceAccountIdentifiers(){
		return sourceAccountIdentifiers;
	}

	public void setStatus(String status){
		this.status = status;
	}

	@SuppressWarnings("unused")
	public String getStatus(){
		return status;
	}
}
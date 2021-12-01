package com.gsmaSdk.gsma.models.account;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.RequestingOrganisation;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

public class AccountLinks extends BaseResponse {

	@Expose
	@SerializedName("mode")
	private String mode;

	@Expose
	@SerializedName("modificationDate")
	private String modificationDate;

	@Expose
	@SerializedName("requestingOrganisation")
	private RequestingOrganisation requestingOrganisation;

	@Expose
	@SerializedName("linkReference")
	private String linkReference;

	@Expose
	@SerializedName("customData")
	private List<CustomDataItem> customData;

	@Expose
	@SerializedName("creationDate")
	private String creationDate;

	@Expose
	@SerializedName("sourceAccountIdentifiers")
	private List<SourceAccountIdentifiersItem> sourceAccountIdentifiers;

	@Expose
	@SerializedName("status")
	private String status;

	public void setMode(String mode){
		this.mode = mode;
	}

	public String getMode(){
		return mode;
	}

	public void setModificationDate(String modificationDate){
		this.modificationDate = modificationDate;
	}

	public String getModificationDate(){
		return modificationDate;
	}

	public void setRequestingOrganisation(RequestingOrganisation requestingOrganisation){
		this.requestingOrganisation = requestingOrganisation;
	}

	public RequestingOrganisation getRequestingOrganisation(){
		return requestingOrganisation;
	}

	public void setLinkReference(String linkReference){
		this.linkReference = linkReference;
	}

	public String getLinkReference(){
		return linkReference;
	}

	public void setCustomData(List<CustomDataItem> customData){
		this.customData = customData;
	}

	public List<CustomDataItem> getCustomData(){
		return customData;
	}

	public void setCreationDate(String creationDate){
		this.creationDate = creationDate;
	}

	public String getCreationDate(){
		return creationDate;
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
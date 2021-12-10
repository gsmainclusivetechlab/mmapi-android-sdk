package com.gsmaSdk.gsma.models.account;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.RequestingOrganisation;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings("ALL")
public class Link extends BaseResponse {

	@Expose
	@SerializedName("linkReference")
	private String linkReference;


	@SerializedName("sourceAccountIdentifiers")
	private List<AccountIdentifier> sourceAccountIdentifiers;

	@SerializedName("mode")
	private String mode;

	@SerializedName("status")
	private String status;


	@SerializedName("requestingOrganisation")
	private RequestingOrganisation requestingOrganisation;


	@Expose
	@SerializedName("creationDate")
	private String creationDate;


	@Expose
	@SerializedName("modificationDate")
	private String modificationDate;

	@Expose
	@SerializedName("requestDate")
	private String requestDate;

	@SerializedName("customData")
	private List<CustomDataItem> customData;


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

	public String getLinkReference() {
		return linkReference;
	}

	public void setLinkReference(String linkReference) {
		this.linkReference = linkReference;
	}

	public List<AccountIdentifier> getSourceAccountIdentifiers() {
		return sourceAccountIdentifiers;
	}

	public void setSourceAccountIdentifiers(List<AccountIdentifier> sourceAccountIdentifiers) {
		this.sourceAccountIdentifiers = sourceAccountIdentifiers;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
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




	public void setStatus(String status){
		this.status = status;
	}

	@SuppressWarnings("unused")
	public String getStatus(){
		return status;
	}
}
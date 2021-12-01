package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.SerializedName;

public class RequestingOrganisation{

	@SerializedName("requestingOrganisationIdentifierType")
	private String requestingOrganisationIdentifierType;

	@SerializedName("requestingOrganisationIdentifier")
	private String requestingOrganisationIdentifier;

	public String getRequestingOrganisationIdentifierType(){
		return requestingOrganisationIdentifierType;
	}

	public void setRequestingOrganisationIdentifierType(String requestingOrganisationIdentifierType) {
		this.requestingOrganisationIdentifierType = requestingOrganisationIdentifierType;
	}

	public void setRequestingOrganisationIdentifier(String requestingOrganisationIdentifier) {
		this.requestingOrganisationIdentifier = requestingOrganisationIdentifier;
	}

	public String getRequestingOrganisationIdentifier(){
		return requestingOrganisationIdentifier;
	}
}
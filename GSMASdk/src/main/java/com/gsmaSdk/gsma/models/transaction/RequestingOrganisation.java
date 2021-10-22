package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.SerializedName;

public class RequestingOrganisation{

	@SerializedName("requestingOrganisationIdentifierType")
	private String requestingOrganisationIdentifierType;

	@SerializedName("requestingOrganisationIdentifier")
	private String requestingOrganisationIdentifier;

	public String getRequestingOrganisationIdentifierType(){
		return requestingOrganisationIdentifierType;
	}

	public String getRequestingOrganisationIdentifier(){
		return requestingOrganisationIdentifier;
	}
}
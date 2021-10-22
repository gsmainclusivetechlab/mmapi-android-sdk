package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.SerializedName;

public class IdDocumentItem{

	@SerializedName("expiryDate")
	private String expiryDate;

	@SerializedName("issuerCountry")
	private String issuerCountry;

	@SerializedName("idType")
	private String idType;

	@SerializedName("issuerPlace")
	private String issuerPlace;

	@SerializedName("idNumber")
	private String idNumber;

	@SerializedName("issueDate")
	private String issueDate;

	@SerializedName("issuer")
	private String issuer;

	public String getExpiryDate(){
		return expiryDate;
	}

	public String getIssuerCountry(){
		return issuerCountry;
	}

	public String getIdType(){
		return idType;
	}

	public String getIssuerPlace(){
		return issuerPlace;
	}

	public String getIdNumber(){
		return idNumber;
	}

	public String getIssueDate(){
		return issueDate;
	}

	public String getIssuer(){
		return issuer;
	}
}
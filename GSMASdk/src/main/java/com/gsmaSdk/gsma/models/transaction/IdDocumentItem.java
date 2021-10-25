package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings({"ALL", "unused"})
public class IdDocumentItem{

	@SuppressWarnings("unused")
	@SerializedName("expiryDate")
	private String expiryDate;

	@SuppressWarnings("unused")
	@SerializedName("issuerCountry")
	private String issuerCountry;

	@SuppressWarnings("unused")
	@SerializedName("idType")
	private String idType;

	@SuppressWarnings("unused")
	@SerializedName("issuerPlace")
	private String issuerPlace;

	@SuppressWarnings("unused")
	@SerializedName("idNumber")
	private String idNumber;

	@SuppressWarnings("unused")
	@SerializedName("issueDate")
	private String issueDate;

	@SuppressWarnings("unused")
	@SerializedName("issuer")
	private String issuer;

	@SuppressWarnings("unused")
	public String getExpiryDate(){
		return expiryDate;
	}

	@SuppressWarnings("unused")
	public String getIssuerCountry(){
		return issuerCountry;
	}

	@SuppressWarnings("unused")
	public String getIdType(){
		return idType;
	}

	@SuppressWarnings("unused")
	public String getIssuerPlace(){
		return issuerPlace;
	}

	@SuppressWarnings("unused")
	public String getIdNumber(){
		return idNumber;
	}

	@SuppressWarnings("unused")
	public String getIssueDate(){
		return issueDate;
	}

	@SuppressWarnings("unused")
	public String getIssuer(){
		return issuer;
	}
}
package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings({"ALL", "unused"})
public class IdDocumentItem{

	@SerializedName("idType")
	private String idType;

	@SerializedName("idNumber")
	private String idNumber;

	@SerializedName("issueDate")
	private String issueDate;

	@SerializedName("expiryDate")
	private String expiryDate;


	@SerializedName("issuer")
	private String issuer;

	@SerializedName("issuerPlace")
	private String issuerPlace;


	@SerializedName("issuerCountry")
	private String issuerCountry;


	@SerializedName("otherIdDescription")
	private String otherIdDescription;




	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setIssuerCountry(String issuerCountry) {
		this.issuerCountry = issuerCountry;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getOtherIdDescription() {
		return otherIdDescription;
	}

	public void setOtherIdDescription(String otherIdDescription) {
		this.otherIdDescription = otherIdDescription;
	}

	public void setIssuerPlace(String issuerPlace) {
		this.issuerPlace = issuerPlace;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

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
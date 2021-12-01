package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class PostalAddress{

	@SerializedName("country")
	private String country;

	@SerializedName("city")
	private String city;

	@SerializedName("postalCode")
	private String postalCode;

	@SerializedName("addressLine1")
	private String addressLine1;

	@SerializedName("stateProvince")
	private String stateProvince;

	public String getCountry(){
		return country;
	}

	public String getCity(){
		return city;
	}

	public String getPostalCode(){
		return postalCode;
	}

	public String getAddressLine1(){
		return addressLine1;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getStateProvince(){
		return stateProvince;
	}
}
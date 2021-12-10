package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class Address {

	@SerializedName("addressLine1")
	private String addressLine1;

	@SerializedName("addressLine2")
	private String addressLine2;

	@SerializedName("addressLine3")
	private String addressLine3;

	@SerializedName("city")
	private String city;

	@SerializedName("stateProvince")
	private String stateProvince;

	@SerializedName("postalCode")
	private String postalCode;

	@SerializedName("country")
	private String country;

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

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
package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.SerializedName;

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

	public String getStateProvince(){
		return stateProvince;
	}
}
package com.gsmaSdk.gsma.models.authorisationCode;

import com.google.gson.annotations.SerializedName;

/**
 * Model class for Redemption Account Identifier
 * */
@SuppressWarnings("ALL")
public class RedemptionAccountIdentifiersItem{

	@SerializedName("value")
	private String value;

	@SerializedName("key")
	private String key;

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getKey(){
		return key;
	}
}
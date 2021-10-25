package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class CreditPartyItem{

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
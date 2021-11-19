package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditPartyItem{

	@SerializedName("value")
	@Expose
	private String value;

	@SerializedName("key")
	@Expose
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
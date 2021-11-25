package com.gsmaSdk.gsma.models;

import com.google.gson.annotations.SerializedName;

public class PayeeItem{

	@SerializedName("value")
	private String value;

	@SerializedName("key")
	private String key;

	public String getValue(){
		return value;
	}

	public String getKey(){
		return key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
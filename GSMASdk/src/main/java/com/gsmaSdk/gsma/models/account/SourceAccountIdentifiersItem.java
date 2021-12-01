package com.gsmaSdk.gsma.models.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SourceAccountIdentifiersItem{
	@Expose
	@SerializedName("value")
	private String value;

	@Expose
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
package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class CustomDataItem{

	@SerializedName("value")
	@Expose
	private String value;

	@SerializedName("key")
	@Expose
	private String key;

	public void setValue(String value){
		this.value = value;
	}

	@SuppressWarnings("unused")
	public String getValue(){
		return value;
	}

	public void setKey(String key){
		this.key = key;
	}

	@SuppressWarnings("unused")
	public String getKey(){
		return key;
	}
}
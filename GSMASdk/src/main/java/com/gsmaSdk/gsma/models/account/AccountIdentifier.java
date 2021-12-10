package com.gsmaSdk.gsma.models.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class AccountIdentifier {
	@SerializedName("value")
	@Expose
	private String value;

	@SerializedName("key")
	@Expose
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
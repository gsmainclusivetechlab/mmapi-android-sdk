package com.gsmaSdk.gsma.models.authorisationCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for Redemption Account Identifier
 * */
@SuppressWarnings("ALL")
public class AccountIdentifiers {

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
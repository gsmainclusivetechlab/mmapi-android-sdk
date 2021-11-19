package com.gsmaSdk.gsma.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

public class AccountHolderObject extends BaseResponse {

	@SerializedName("lei")
	@Expose
	private String lei;

	@SerializedName("name")
	@Expose
	private Name name;

	public String getLei(){
		return lei;
	}

	public Name getName(){
		return name;
	}
}
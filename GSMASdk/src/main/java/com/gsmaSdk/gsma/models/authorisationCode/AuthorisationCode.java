package com.gsmaSdk.gsma.models.authorisationCode;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

/**
 * Model class for Authorisation Code
 * */
public class AuthorisationCode implements BaseResponse {

	@SerializedName("AuthCode")
	private List<AuthorisationCodeItem> authCode;

	public void setAuthCode(List<AuthorisationCodeItem> authCode){
		this.authCode = authCode;
	}

	public List<AuthorisationCodeItem> getAuthCode(){
		return authCode;
	}
}
package com.gsmaSdk.gsma.models.authorisationCode;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

/**
 * Model class for Authorisation Code
 * */
@SuppressWarnings("ALL")
public class AuthorisationCodes extends BaseResponse {

	@SerializedName("AuthCode")
	private List<AuthorisationCode> authCode;

	public void setAuthCode(List<AuthorisationCode> authCode){
		this.authCode = authCode;
	}

	public List<AuthorisationCode> getAuthCode(){
		return authCode;
	}
}
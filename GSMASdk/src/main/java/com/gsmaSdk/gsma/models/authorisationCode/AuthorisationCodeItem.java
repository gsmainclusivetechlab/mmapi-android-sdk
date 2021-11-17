package com.gsmaSdk.gsma.models.authorisationCode;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

/**
 * Model class for Authorisation Code Items
 * */
@SuppressWarnings("ALL")
public class AuthorisationCodeItem extends BaseResponse {

	@Expose
	@SerializedName("amount")
	private String amount;

	@Expose
	@SerializedName("modificationDate")
	private String modificationDate;

	@Expose
	@SerializedName("redemptionAccountIdentifiers")
	private List<RedemptionAccountIdentifiersItem> redemptionAccountIdentifiers;

	@Expose
	@SerializedName("requestDate")
	private String requestDate;

	@Expose
	@SerializedName("currency")
	private String currency;

	@Expose
	@SerializedName("authorisationCode")
	private String authorisationCode;

	@Expose
	@SerializedName("creationDate")
	private String creationDate;

	@Expose
	@SerializedName("codeState")
	private String codeState;

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setModificationDate(String modificationDate){
		this.modificationDate = modificationDate;
	}

	public String getModificationDate(){
		return modificationDate;
	}

	public void setRedemptionAccountIdentifiers(List<RedemptionAccountIdentifiersItem> redemptionAccountIdentifiers){
		this.redemptionAccountIdentifiers = redemptionAccountIdentifiers;
	}

	public List<RedemptionAccountIdentifiersItem> getRedemptionAccountIdentifiers(){
		return redemptionAccountIdentifiers;
	}

	public void setRequestDate(String requestDate){
		this.requestDate = requestDate;
	}

	public String getRequestDate(){
		return requestDate;
	}

	public void setCurrency(String currency){
		this.currency = currency;
	}

	public String getCurrency(){
		return currency;
	}

	public void setAuthorisationCode(String authorisationCode){
		this.authorisationCode = authorisationCode;
	}

	public String getAuthorisationCode(){
		return authorisationCode;
	}

	public void setCreationDate(String creationDate){
		this.creationDate = creationDate;
	}

	public String getCreationDate(){
		return creationDate;
	}

	public void setCodeState(String codeState){
		this.codeState = codeState;
	}

	public String getCodeState(){
		return codeState;
	}
}
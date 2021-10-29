package com.gsmaSdk.gsma.models.authorisationCode;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for Authorisation Code Items
 * */
public class AuthorisationCodeItem {

	@SerializedName("amount")
	private String amount;

	@SerializedName("modificationDate")
	private String modificationDate;

	@SerializedName("redemptionAccountIdentifiers")
	private List<RedemptionAccountIdentifiersItem> redemptionAccountIdentifiers;

	@SerializedName("requestDate")
	private String requestDate;

	@SerializedName("currency")
	private String currency;

	@SerializedName("authorisationCode")
	private String authorisationCode;

	@SerializedName("creationDate")
	private String creationDate;

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
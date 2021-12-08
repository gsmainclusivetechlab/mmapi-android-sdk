package com.gsmaSdk.gsma.models.authorisationCode;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

/**
 * Model class for Authorisation Code Items
 * */
@SuppressWarnings("ALL")
public class AuthorisationCodeItem extends BaseResponse {


	@Expose
	@SerializedName("authorisationCode")
	private String authorisationCode;

	@Expose
	@SerializedName("codeState")
	private String codeState;


	@Expose
	@SerializedName("amount")
	private String amount;

	@Expose
	@SerializedName("currency")
	private String currency;

	@Expose
	@SerializedName("amountType")
	private String amountType;

	@Expose
	@SerializedName("codeLifetime")
	private int codeLifetime;

	@Expose
	@SerializedName("holdFundsIndicator")
	private boolean holdFundsIndicator;

	@Expose
	@SerializedName("redemptionAccountIdentifiers")
	private List<RedemptionAccountIdentifiersItem> redemptionAccountIdentifiers;

	@Expose
	@SerializedName("redemptionChannels")
	private ArrayList<RedemptionChannel> redemptionChannels;

	@Expose
	@SerializedName("redemptionTransactionTypes")
	private ArrayList<RedemptionTransactionType> redemptionTransactionTypes;


	@SerializedName("requestingOrganisationTransactionReference")
	@Expose
	private String  requestingOrganisationTransactionReference;

	@Expose
	@SerializedName("creationDate")
	private String creationDate;


	@Expose
	@SerializedName("modificationDate")
	private String modificationDate;

	@Expose
	@SerializedName("requestDate")
	private String requestDate;

	@SerializedName("customData")
	@Expose
	private List<CustomDataItem> customData;


	@SerializedName("metadata")
	@Expose
	private ArrayList<MetaDataObject> metaDataObject;

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
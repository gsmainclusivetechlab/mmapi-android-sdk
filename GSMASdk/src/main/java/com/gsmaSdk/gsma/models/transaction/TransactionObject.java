package com.gsmaSdk.gsma.models.transaction;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

/**
 * Model class for Transaction Object
 * */
public class TransactionObject implements BaseResponse {

	@SerializedName("debitParty")
	@Expose
	private List<DebitPartyItem> debitParty;

	@SerializedName("amount")
	@Expose
	private String amount;

	@SerializedName("modificationDate")
	@Expose
	private String modificationDate;

	@SerializedName("transactionReference")
	@Expose
	private String transactionReference;

	@SerializedName("transactionStatus")
	@Expose
	private String transactionStatus;

	@SerializedName("requestDate")
	@Expose
	private String requestDate;

	@SerializedName("currency")
	@Expose
	private String currency;

	@SerializedName("type")
	@Expose
	private String type;

	@SerializedName("creationDate")
	@Expose
	private String creationDate;

	@SerializedName("creditParty")
	@Expose
	private List<CreditPartyItem> creditParty;

	public void setDebitParty(List<DebitPartyItem> debitParty){
		this.debitParty = debitParty;
	}

	public List<DebitPartyItem> getDebitParty(){
		return debitParty;
	}

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

	public void setTransactionReference(String transactionReference){
		this.transactionReference = transactionReference;
	}

	public String getTransactionReference(){
		return transactionReference;
	}

	public void setTransactionStatus(String transactionStatus){
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionStatus(){
		return transactionStatus;
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

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setCreationDate(String creationDate){
		this.creationDate = creationDate;
	}

	public String getCreationDate(){
		return creationDate;
	}

	public void setCreditParty(List<CreditPartyItem> creditParty){
		this.creditParty = creditParty;
	}

	public List<CreditPartyItem> getCreditParty(){
		return creditParty;
	}
}
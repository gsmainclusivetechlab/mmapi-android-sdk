package com.gsmaSdk.gsma.models.transaction;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

/**
 * Model class for Transaction Object
 * */
@SuppressWarnings("unused")
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

	@SuppressWarnings("unused")
    public void setDebitParty(List<DebitPartyItem> debitParty){
		this.debitParty = debitParty;
	}

	@SuppressWarnings("unused")
    public List<DebitPartyItem> getDebitParty(){
		return debitParty;
	}

	@SuppressWarnings("unused")
    public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	@SuppressWarnings("unused")
    public void setModificationDate(String modificationDate){
		this.modificationDate = modificationDate;
	}

	@SuppressWarnings("unused")
    public String getModificationDate(){
		return modificationDate;
	}

	@SuppressWarnings("unused")
    public void setTransactionReference(String transactionReference){
		this.transactionReference = transactionReference;
	}

	@SuppressWarnings("unused")
    public String getTransactionReference(){
		return transactionReference;
	}

	@SuppressWarnings("unused")
    public void setTransactionStatus(String transactionStatus){
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionStatus(){
		return transactionStatus;
	}

	@SuppressWarnings("unused")
    public void setRequestDate(String requestDate){
		this.requestDate = requestDate;
	}

	@SuppressWarnings("unused")
    public String getRequestDate(){
		return requestDate;
	}

	@SuppressWarnings("unused")
    public void setCurrency(String currency){
		this.currency = currency;
	}

	@SuppressWarnings("unused")
    public String getCurrency(){
		return currency;
	}

	@SuppressWarnings("unused")
    public void setType(String type){
		this.type = type;
	}

	@SuppressWarnings("unused")
    public String getType(){
		return type;
	}

	@SuppressWarnings("unused")
    public void setCreationDate(String creationDate){
		this.creationDate = creationDate;
	}

	@SuppressWarnings("unused")
    public String getCreationDate(){
		return creationDate;
	}

	@SuppressWarnings("unused")
    public void setCreditParty(List<CreditPartyItem> creditParty){
		this.creditParty = creditParty;
	}

	@SuppressWarnings("unused")
    public List<CreditPartyItem> getCreditParty(){
		return creditParty;
	}
}
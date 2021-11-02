package com.gsmaSdk.gsma.models.transaction;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BatchTransactionCompletionItem{

	@SerializedName("debitParty")
	private List<DebitPartyItem> debitParty;

	@SerializedName("transactionReference")
	private String transactionReference;

	@SerializedName("link")
	private String link;

	@SerializedName("requestingOrganisationTransactionReference")
	private String requestingOrganisationTransactionReference;

	@SerializedName("completionDate")
	private String completionDate;

	@SerializedName("customData")
	private List<CustomDataItem> customData;

	@SerializedName("creditParty")
	private List<CreditPartyItem> creditParty;

	@SerializedName("completedDate")
	private String completedDate;

	public void setDebitParty(List<DebitPartyItem> debitParty){
		this.debitParty = debitParty;
	}

	public List<DebitPartyItem> getDebitParty(){
		return debitParty;
	}

	public void setTransactionReference(String transactionReference){
		this.transactionReference = transactionReference;
	}

	public String getTransactionReference(){
		return transactionReference;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setRequestingOrganisationTransactionReference(String requestingOrganisationTransactionReference){
		this.requestingOrganisationTransactionReference = requestingOrganisationTransactionReference;
	}

	public String getRequestingOrganisationTransactionReference(){
		return requestingOrganisationTransactionReference;
	}

	public void setCompletionDate(String completionDate){
		this.completionDate = completionDate;
	}

	public String getCompletionDate(){
		return completionDate;
	}

	public void setCustomData(List<CustomDataItem> customData){
		this.customData = customData;
	}

	public List<CustomDataItem> getCustomData(){
		return customData;
	}

	public void setCreditParty(List<CreditPartyItem> creditParty){
		this.creditParty = creditParty;
	}

	public List<CreditPartyItem> getCreditParty(){
		return creditParty;
	}

	public void setCompletedDate(String completedDate){
		this.completedDate = completedDate;
	}

	public String getCompletedDate(){
		return completedDate;
	}
}
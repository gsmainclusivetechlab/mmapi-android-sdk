package com.gsmaSdk.gsma.models.transaction.batchcompletion;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.account.AccountIdentifier;
import com.gsmaSdk.gsma.models.common.CustomDataItem;

public class BatchCompletionItem {

	@SerializedName("transactionReference")
	private String transactionReference;


	@SerializedName("requestingOrganisationTransactionReference")
	private String requestingOrganisationTransactionReference;


	@SerializedName("debitParty")
	private List<AccountIdentifier> debitParty;

	@SerializedName("creditParty")
	private List<AccountIdentifier> creditParty;


	@SerializedName("completionDate")
	private String completionDate;

	@SerializedName("link")
	private String link;


	@SerializedName("customData")
	private List<CustomDataItem> customData;


	@SerializedName("completedDate")
	private String completedDate;

	public void setDebitParty(List<AccountIdentifier> debitParty){
		this.debitParty = debitParty;
	}

	public List<AccountIdentifier> getDebitParty(){
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

	public void setCreditParty(List<AccountIdentifier> creditParty){
		this.creditParty = creditParty;
	}

	public List<AccountIdentifier> getCreditParty(){
		return creditParty;
	}

	public void setCompletedDate(String completedDate){
		this.completedDate = completedDate;
	}

	public String getCompletedDate(){
		return completedDate;
	}
}
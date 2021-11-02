package com.gsmaSdk.gsma.models.transaction;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BatchTransactionRejectionItem{

	@SerializedName("debitParty")
	private List<DebitPartyItem> debitParty;

	@SerializedName("dateRejected")
	private String dateRejected;

	@SerializedName("transactionReference")
	private String transactionReference;

	@SerializedName("rejectionDate")
	private String rejectionDate;

	@SerializedName("requestingOrganisationTransactionReference")
	private String requestingOrganisationTransactionReference;

	@SerializedName("customData")
	private List<CustomDataItem> customData;

	@SerializedName("rejectionReason")
	private String rejectionReason;

	@SerializedName("creditParty")
	private List<CreditPartyItem> creditParty;

	public void setDebitParty(List<DebitPartyItem> debitParty){
		this.debitParty = debitParty;
	}

	public List<DebitPartyItem> getDebitParty(){
		return debitParty;
	}

	public void setDateRejected(String dateRejected){
		this.dateRejected = dateRejected;
	}

	public String getDateRejected(){
		return dateRejected;
	}

	public void setTransactionReference(String transactionReference){
		this.transactionReference = transactionReference;
	}

	public String getTransactionReference(){
		return transactionReference;
	}

	public void setRejectionDate(String rejectionDate){
		this.rejectionDate = rejectionDate;
	}

	public String getRejectionDate(){
		return rejectionDate;
	}

	public void setRequestingOrganisationTransactionReference(String requestingOrganisationTransactionReference){
		this.requestingOrganisationTransactionReference = requestingOrganisationTransactionReference;
	}

	public String getRequestingOrganisationTransactionReference(){
		return requestingOrganisationTransactionReference;
	}

	public void setCustomData(List<CustomDataItem> customData){
		this.customData = customData;
	}

	public List<CustomDataItem> getCustomData(){
		return customData;
	}

	public void setRejectionReason(String rejectionReason){
		this.rejectionReason = rejectionReason;
	}

	public String getRejectionReason(){
		return rejectionReason;
	}

	public void setCreditParty(List<CreditPartyItem> creditParty){
		this.creditParty = creditParty;
	}

	public List<CreditPartyItem> getCreditParty(){
		return creditParty;
	}
}
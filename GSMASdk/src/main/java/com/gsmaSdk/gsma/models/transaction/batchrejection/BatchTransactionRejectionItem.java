package com.gsmaSdk.gsma.models.transaction.batchrejection;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.common.CreditPartyItem;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.DebitPartyItem;

public class BatchTransactionRejectionItem{

	@SerializedName("transactionReference")
	@Expose
	private String transactionReference;

	@SerializedName("requestingOrganisationTransactionReference")
	@Expose
	private String requestingOrganisationTransactionReference;


	@SerializedName("creditParty")
	@Expose
	private List<CreditPartyItem> creditParty;

	@SerializedName("debitParty")
	@Expose
	private List<DebitPartyItem> debitParty;


	@SerializedName("rejectionReason")
	@Expose
	private String rejectionReason;


	@SerializedName("rejectionDate")
	@Expose
	private String rejectionDate;

	@SerializedName("dateRejected")
	@Expose
	private String dateRejected;

	@SerializedName("customData")
	@Expose
	private List<CustomDataItem> customData;


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
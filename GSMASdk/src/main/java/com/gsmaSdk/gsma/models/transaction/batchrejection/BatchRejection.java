package com.gsmaSdk.gsma.models.transaction.batchrejection;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.account.AccountIdentifier;
import com.gsmaSdk.gsma.models.common.CustomDataItem;

@SuppressWarnings("unused")
public class BatchRejection {

	@SerializedName("transactionReference")
	@Expose
	private String transactionReference;

	@SerializedName("requestingOrganisationTransactionReference")
	@Expose
	private String requestingOrganisationTransactionReference;


	@SerializedName("creditParty")
	@Expose
	private List<AccountIdentifier> creditParty;

	@SerializedName("debitParty")
	@Expose
	private List<AccountIdentifier> debitParty;


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


	@SuppressWarnings("unused")
    public void setDebitParty(List<AccountIdentifier> debitParty){
		this.debitParty = debitParty;
	}

	@SuppressWarnings("unused")
    public List<AccountIdentifier> getDebitParty(){
		return debitParty;
	}

	@SuppressWarnings("unused")
    public void setDateRejected(String dateRejected){
		this.dateRejected = dateRejected;
	}

	@SuppressWarnings("unused")
    public String getDateRejected(){
		return dateRejected;
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
    public void setRejectionDate(String rejectionDate){
		this.rejectionDate = rejectionDate;
	}

	@SuppressWarnings("unused")
    public String getRejectionDate(){
		return rejectionDate;
	}

	@SuppressWarnings("unused")
    public void setRequestingOrganisationTransactionReference(String requestingOrganisationTransactionReference){
		this.requestingOrganisationTransactionReference = requestingOrganisationTransactionReference;
	}

	@SuppressWarnings("unused")
    public String getRequestingOrganisationTransactionReference(){
		return requestingOrganisationTransactionReference;
	}

	@SuppressWarnings("unused")
    public void setCustomData(List<CustomDataItem> customData){
		this.customData = customData;
	}

	@SuppressWarnings("unused")
    public List<CustomDataItem> getCustomData(){
		return customData;
	}

	@SuppressWarnings("unused")
    public void setRejectionReason(String rejectionReason){
		this.rejectionReason = rejectionReason;
	}

	@SuppressWarnings("unused")
    public String getRejectionReason(){
		return rejectionReason;
	}

	@SuppressWarnings("unused")
    public void setCreditParty(List<AccountIdentifier> creditParty){
		this.creditParty = creditParty;
	}

	@SuppressWarnings("unused")
    public List<AccountIdentifier> getCreditParty(){
		return creditParty;
	}
}
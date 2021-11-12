package com.gsmaSdk.gsma.models.transaction;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BulkTransactionObject{

	@SerializedName("batchTitle")
	private String batchTitle;

	@SerializedName("transactions")
	private ArrayList<TransactionRequest> transactions;

	@SerializedName("batchDescription")
	private String batchDescription;

	@SerializedName("scheduledStartDate")
	private String scheduledStartDate;

	public void setBatchTitle(String batchTitle){
		this.batchTitle = batchTitle;
	}

	public String getBatchTitle(){
		return batchTitle;
	}

	public void setTransactions(ArrayList<TransactionRequest> transactions){
		this.transactions = transactions;
	}

	public List<TransactionRequest> getTransactions(){
		return transactions;
	}

	public void setBatchDescription(String batchDescription){
		this.batchDescription = batchDescription;
	}

	public String getBatchDescription(){
		return batchDescription;
	}

	public void setScheduledStartDate(String scheduledStartDate){
		this.scheduledStartDate = scheduledStartDate;
	}

	public String getScheduledStartDate(){
		return scheduledStartDate;
	}
}
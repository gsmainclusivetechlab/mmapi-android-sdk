package com.gsmaSdk.gsma.models.transaction.batchtransaction;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.RequestingOrganisation;
import com.gsmaSdk.gsma.models.transaction.transactions.TransactionRequest;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

public class BatchTransaction extends BaseResponse {

	@SerializedName("batchId")
	@Expose
	private String batchId;

	@SerializedName("batchStatus")
	@Expose
	private String batchStatus;

	@SerializedName("transactions")
	@Expose
	private ArrayList<TransactionRequest> transactions;

	@SerializedName("approvalDate")
	@Expose
	private String approvalDate;


	@SerializedName("completionDate")
	@Expose
	private String completionDate;

	@SerializedName("batchTitle")
	@Expose
	private String batchTitle;


	@SerializedName("batchDescription")
	private String batchDescription;


	@SerializedName("processingFlag")
	@Expose
	private boolean processingFlag;


	@SerializedName("completedCount")
	@Expose
	private int completedCount;


	@SerializedName("parsingSuccessCount")
	@Expose
	private int parsingSuccessCount;


	@SerializedName("rejectionCount")
	@Expose
	private int rejectionCount;


	@SerializedName("requestingOrganisation")
	@Expose
	private RequestingOrganisation requestingOrganisation;


	@SerializedName("scheduledStartDate")
    @Expose
	private String scheduledStartDate;



	@SerializedName("creationDate")
	@Expose
	private String creationDate;

	@SerializedName("modificationDate")
	@Expose
	private String modificationDate;


	@SerializedName("requestDate")
	@Expose
	private String requestDate;


	@SerializedName("customData")
	@Expose
	private List<CustomDataItem> customData;



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
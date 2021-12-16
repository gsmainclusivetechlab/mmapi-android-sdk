package com.gsmaSdk.gsma.models.transaction.batchtransaction;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.common.CustomDataItem;
import com.gsmaSdk.gsma.models.common.RequestingOrganisation;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings("ALL")
public class BatchTransaction extends BaseResponse {

	@SerializedName("batchId")
	@Expose
	private String batchId;

	@SerializedName("batchStatus")
	@Expose
	private String batchStatus;

	@SerializedName("transactions")
	@Expose
	private ArrayList<Transaction> transactions;

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

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public boolean isProcessingFlag() {
		return processingFlag;
	}

	public void setProcessingFlag(boolean processingFlag) {
		this.processingFlag = processingFlag;
	}

	public int getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(int completedCount) {
		this.completedCount = completedCount;
	}

	public int getParsingSuccessCount() {
		return parsingSuccessCount;
	}

	public void setParsingSuccessCount(int parsingSuccessCount) {
		this.parsingSuccessCount = parsingSuccessCount;
	}

	public int getRejectionCount() {
		return rejectionCount;
	}

	public void setRejectionCount(int rejectionCount) {
		this.rejectionCount = rejectionCount;
	}

	public RequestingOrganisation getRequestingOrganisation() {
		return requestingOrganisation;
	}

	public void setRequestingOrganisation(RequestingOrganisation requestingOrganisation) {
		this.requestingOrganisation = requestingOrganisation;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public List<CustomDataItem> getCustomData() {
		return customData;
	}

	public void setCustomData(List<CustomDataItem> customData) {
		this.customData = customData;
	}

	public void setBatchTitle(String batchTitle){
		this.batchTitle = batchTitle;
	}

	public String getBatchTitle(){
		return batchTitle;
	}

	public void setTransactions(ArrayList<Transaction> transactions){
		this.transactions = transactions;
	}

	public List<Transaction> getTransactions(){
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
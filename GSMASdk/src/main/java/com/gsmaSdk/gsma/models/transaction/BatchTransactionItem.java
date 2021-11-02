package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

import java.io.Serializable;

public class BatchTransactionItem  implements BaseResponse {


	@SerializedName("approvalDate")
	@Expose
	private String approvalDate;

	@SerializedName("rejectionCount")
	@Expose
	private int rejectionCount;


	@SerializedName("batchId")
	@Expose
	private String batchId;

	@SerializedName("completedCount")
	@Expose
	private int completedCount;

	@SerializedName("creationDate")
	@Expose
	private String creationDate;

	@SerializedName("batchDescription")
	@Expose
	private String batchDescription;

	@SerializedName("scheduledStartDate")
	@Expose
	private String scheduledStartDate;

	@SerializedName("modificationDate")
	@Expose
	private String modificationDate;

	@SerializedName("processingFlag")
	@Expose
	private boolean processingFlag;

	@SerializedName("requestDate")
	@Expose
	private String requestDate;

	@SerializedName("completionDate")
	@Expose
	private String completionDate;

	@SerializedName("batchTitle")
	@Expose
	private String batchTitle;

	@SerializedName("parsingSuccessCount")
	@Expose
	private int parsingSuccessCount;

	@SerializedName("batchStatus")
	@Expose
	private String batchStatus;

	public String getApprovalDate(){
		return approvalDate;
	}

	public int getRejectionCount(){
		return rejectionCount;
	}

	public String getBatchId(){
		return batchId;
	}

	public int getCompletedCount(){
		return completedCount;
	}

	public String getCreationDate(){
		return creationDate;
	}

	public String getBatchDescription(){
		return batchDescription;
	}

	public String getScheduledStartDate(){
		return scheduledStartDate;
	}

	public String getModificationDate(){
		return modificationDate;
	}

	public boolean isProcessingFlag(){
		return processingFlag;
	}

	public String getRequestDate(){
		return requestDate;
	}

	public String getCompletionDate(){
		return completionDate;
	}

	public String getBatchTitle(){
		return batchTitle;
	}

	public int getParsingSuccessCount(){
		return parsingSuccessCount;
	}

	public String getBatchStatus(){
		return batchStatus;
	}
}